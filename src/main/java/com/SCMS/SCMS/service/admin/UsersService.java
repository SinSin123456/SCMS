package com.SCMS.SCMS.service.admin;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.SCMS.SCMS.controller.ajax.admin.UsersController;
import com.SCMS.SCMS.entities.Users;
import com.SCMS.SCMS.hepler.LogHelper;
import com.SCMS.SCMS.hepler.ObjResponseEntity;
import com.SCMS.SCMS.hepler.ReqDatatableParam;

import com.SCMS.SCMS.hepler.ResDatatableParam;
import com.SCMS.SCMS.model.request.admin.ReqSaveUsers;
import com.SCMS.SCMS.model.response.admin.ResListUsers;
import com.SCMS.SCMS.repository.admin.UsersRepository;
import org.slf4j.Logger;

@Service
@Transactional
public class UsersService {
    private static final Logger logger = LoggerFactory.getLogger(UsersController.class);

    @Autowired
    private UsersRepository usersRepository;

    public ResDatatableParam<ResListUsers> getUsersList(ReqDatatableParam data) {
        try {
            String joinDate = data.getColumns().get(0).getSearch().getValue();
            if (joinDate.isEmpty()) {
                joinDate = null;
            }
            List<ResListUsers> users = new ArrayList<>();
            List<Users> allUsers = usersRepository.findAllUsers(data.getStart(), data.getLength(),
                    data.getSearch().getValue(), joinDate);
            int countAllUsers = usersRepository.countAllUsers(data.getSearch().getValue(), joinDate);

            for (Users user : allUsers) {

                users.add(new ResListUsers(
                        user.getId(),
                        user.getFullname(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getJoinDate() != null ? user.getJoinDate().toString() : null,
                        user.getRoles() != null ? String.join(",", user.getRoles()) : null));
            }
            return new ResDatatableParam<>(data.getDraw(), countAllUsers, countAllUsers, users);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error fetching users list data: " + e.getMessage());
        }
        return null;
    }

    public ObjResponseEntity<String> createUsers(@Valid @RequestBody ReqSaveUsers data) {
        try {
            LocalDateTime now = LocalDateTime.now();
            Timestamp timestampNow = Timestamp.valueOf(now);
            String authorUsername = SecurityContextHolder.getContext().getAuthentication().getName();

            // Date conversion
            String rawDate = data.getJoinDate();
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH);
            LocalDate parsedDate = LocalDate.parse(rawDate, inputFormatter);
            LocalDateTime dateTime = parsedDate.atStartOfDay();
            Timestamp joinTimestamp = Timestamp.valueOf(dateTime);
            String roles = data.getRoles();
            Set<String> roleSet = Arrays.stream(roles.split(","))
                    .map(String::trim)
                    .collect(Collectors.toSet());

            // Construct new user
            Users newUsers = new Users();
            newUsers.setFullname(data.getFullname());
            newUsers.setEmail(data.getEmail());
            newUsers.setUsername(data.getUsername());
            newUsers.setJoinDate(joinTimestamp);
            newUsers.setCreatedBy(authorUsername);
            newUsers.setCreatedAt(timestampNow);
            newUsers.setIsActive(1);
            newUsers.setRoles(roleSet);

            Users savedUser = usersRepository.save(newUsers);
            logger.info("User saved successfully: {}", savedUser.getUsername());
            System.out.println("User saved successfully: " + savedUser.getUsername());

            return new ObjResponseEntity<String>().setSuccess("User created");

        } catch (Exception e) {
            logger.error("Error creating user: ", e);
            return new ObjResponseEntity<String>().setError("Failed to create user: " + e.getMessage());
        }
    }

}
