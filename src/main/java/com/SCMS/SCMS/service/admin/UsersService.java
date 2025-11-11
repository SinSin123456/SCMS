package com.SCMS.SCMS.service.admin;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.TimeZone;
import java.util.stream.Collectors;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.SCMS.SCMS.controller.ajax.admin.UsersController;
import com.SCMS.SCMS.entities.StudentMangement;
import com.SCMS.SCMS.entities.Teacher;
import com.SCMS.SCMS.entities.Users;
import com.SCMS.SCMS.hepler.LogHelper;
import com.SCMS.SCMS.hepler.ObjResponseEntity;
import com.SCMS.SCMS.hepler.ReqDatatableParam;

import com.SCMS.SCMS.hepler.ResDatatableParam;
import com.SCMS.SCMS.model.request.admin.ReqSaveUsers;
import com.SCMS.SCMS.model.request.admin.ReqUdateUsers;
import com.SCMS.SCMS.model.response.admin.ResEditUsers;
import com.SCMS.SCMS.model.response.admin.ResListUsers;
import com.SCMS.SCMS.repository.admin.UsersRepository;
import com.SCMS.SCMS.repository.student.StudentMangementRepository;
import com.SCMS.SCMS.repository.student.TeacherRepository;

import jakarta.persistence.OptimisticLockException;

import org.slf4j.Logger;

@Service
@Transactional
public class UsersService {
    private static final Logger logger = LoggerFactory.getLogger(UsersController.class);

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private StudentMangementRepository studentMangementRepository;  

    @Autowired
    private TeacherRepository teacherRepository;


    public ResponseEntity<Map<String, Object>> addUsers(@RequestBody ReqSaveUsers
    data) {
    String authUsername =
    SecurityContextHolder.getContext().getAuthentication().getName();
    Timestamp now = new Timestamp(System.currentTimeMillis());

    Timestamp joinDateTimestamp;
    try {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Date parsedDate = sdf.parse(data.getJoinDate());
    joinDateTimestamp = new Timestamp(parsedDate.getTime());
    } catch (Exception e) {
    joinDateTimestamp = now;
    }

    Set<String> rolesSet = Arrays.stream(data.getRoles().split(","))
    .map(String::trim)
    .collect(Collectors.toSet());

    Users users = new Users();
    users.setUsername(data.getUsername());
    users.setEmail(data.getEmail());
    users.setFullname(data.getFullname());
    users.setRoles(rolesSet);
    users.setJoinDate(joinDateTimestamp);
    users.setCreatedAt(now);
    users.setCreatedBy(authUsername);
    users.setStatus(true);

    usersRepository.save(users);

    Map<String, Object> response = new HashMap<>();
    response.put("success", true);
    response.put("message", "User added successfully");
    return ResponseEntity.ok(response);
    }

   
    public ResDatatableParam<ResListUsers> getUsersList(ReqDatatableParam data) {
        try {
            String searchValue = data.getSearch() != null ? data.getSearch().getValue() : null;
            int start = data.getStart();
            int length = data.getLength();
            if (length < 1) {
                length = 10;
            }
            int page = start / length;

            logger.debug("DataTable Request: draw={}, start={}, length={}, search={}", data.getDraw(), start, length,
                    searchValue);

            PageRequest pageRequest = PageRequest.of(page, length);

            List<Users> allUsers = usersRepository.findAllUsers(searchValue, pageRequest);
            int countAllUsers = usersRepository.countAllUsers(searchValue);
            logger.debug("Users Found: size={}, Total Count: {}", allUsers.size(), countAllUsers);

            List<ResListUsers> userList = new ArrayList<>();
            for (Users user : allUsers) {
                logger.debug("Processing User: id={}, username={}", user.getId(), user.getUsername());
                Set<String> roles = user.getRoles() != null ? user.getRoles() : new HashSet<>();
                userList.add(new ResListUsers(
                        user.getId(),
                        user.getFullname(),
                        user.getUsername(),
                        user.getEmail(),
                        roles,
                        user.getJoinDate()));
            }
            logger.debug("Processed User List Size: {}", userList.size());

            return new ResDatatableParam<>(data.getDraw(), countAllUsers, countAllUsers, userList);
        } catch (Exception e) {
            logger.error("Error in getUsersList: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to fetch users: " + e.getMessage(), e);
        }
    }

    public ResponseEntity<ResEditUsers> editUsers(String id) {
        Long usersId = Long.parseLong(id);
        Optional<Users> usersOptional = usersRepository.findById(usersId);

        if (usersOptional.isPresent()) {
            Users users = usersOptional.get();

            ResEditUsers getUsers = new ResEditUsers();
            getUsers.setId(users.getId());
            getUsers.setFullname(users.getFullname());
            getUsers.setUsername(users.getUsername());
            getUsers.setEmail(users.getEmail());
            getUsers.setRoles(String.join(",", users.getRoles()));
            getUsers.setJoinDate(users.getJoinDate() != null ? users.getJoinDate().toString() : null); // <-- fix here

            return ResponseEntity.ok(getUsers);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public Users updateUsers(ReqUdateUsers data) {
        Timestamp joinDateTimestamp;
        String authUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        // --- Null-safe roles handling ---
        Set<String> rolesSet = new HashSet<>();
        if (data.getRoles() != null && !data.getRoles().isEmpty()) {
            rolesSet = Arrays.stream(data.getRoles().split(","))
                    .map(String::trim)
                    .collect(Collectors.toSet());
        }

        // --- Parse joinDate safely ---
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.setTimeZone(TimeZone.getTimeZone("Asia/Phnom_Penh"));
            Date parsedDate = sdf.parse(data.getJoinDate());
            joinDateTimestamp = new Timestamp(parsedDate.getTime());
        } catch (Exception e) {
            ZonedDateTime fallbackZoned = ZonedDateTime.now(ZoneId.of("Asia/Phnom_Penh"));
            joinDateTimestamp = Timestamp.valueOf(fallbackZoned.toLocalDateTime());
        }

        try {
            ZonedDateTime zonedNow = ZonedDateTime.now(ZoneId.of("Asia/Phnom_Penh"));
            Timestamp now = Timestamp.valueOf(zonedNow.toLocalDateTime());

            // --- Find existing user ---
            Users users = usersRepository.findById(data.getId())
                    .orElseThrow(() -> new NoSuchElementException("User not found"));

            // --- Update fields ---
            users.setFullname(data.getFullname());
            users.setUsername(data.getUsername());
            users.setEmail(data.getEmail());
            users.setRoles(rolesSet); // now safe even if rolesSet is empty
            users.setJoinDate(joinDateTimestamp);
            users.setUpdatedAt(now);
            users.setUpdatedBy(authUsername);

            return usersRepository.save(users);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error updating user", e);
        }
    }

    public Users deleteUser(Long id) {
        try {
            String authUsername = SecurityContextHolder.getContext().getAuthentication().getName();
            Timestamp now = new Timestamp(System.currentTimeMillis());

            Users users = usersRepository.findById(id)
                    .orElseThrow(() -> new NoSuchElementException("User with ID " + id + " not found"));

            users.setStatus(false);
            users.setDeletedAt(now);
            users.setDeletedBy(authUsername);

            Users savedUser = usersRepository.save(users);
            System.out.println("Saved user status: " + savedUser.getStatus()); // Debug log
            return savedUser;
        } catch (Exception e) {
            System.err.println("Error saving user: " + e.getMessage());
            throw e;
        }
    }

}
