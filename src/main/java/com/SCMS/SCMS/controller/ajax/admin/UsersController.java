package com.SCMS.SCMS.controller.ajax.admin;

import java.util.Map;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.SCMS.SCMS.entities.Users;
import com.SCMS.SCMS.hepler.ObjResponseEntity;
import com.SCMS.SCMS.hepler.ReqDatatableParam;
import com.SCMS.SCMS.hepler.ResDatatableParam;
import com.SCMS.SCMS.model.request.admin.ReqSaveUsers;
import com.SCMS.SCMS.model.request.admin.ReqUdateUsers;
import com.SCMS.SCMS.model.response.DataResponse;
import com.SCMS.SCMS.model.response.admin.ResEditUsers;
import com.SCMS.SCMS.model.response.admin.ResListUsers;
import com.SCMS.SCMS.repository.admin.UsersRepository;
import com.SCMS.SCMS.service.admin.UsersService;

import jakarta.transaction.Transactional;

@RestController
@CrossOrigin(origins = "*")
@Transactional
@RequestMapping("/scms/admin-users")
public class UsersController {

    private static final Logger logger = LoggerFactory.getLogger(UsersController.class);

    @Autowired
    private UsersService usersService;

    @Autowired
    private UsersRepository usersRepository;

    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> addUsers(@RequestBody ReqSaveUsers data) {
        System.out.println("data: " + data);
        return usersService.addUsers(data);
    }

    @PostMapping("/datatable")
    @ResponseBody
    public ResDatatableParam<ResListUsers> getUsersList(@RequestBody ReqDatatableParam data) {
        System.out.println("working");
        try {
            return usersService.getUsersList(data);
        } catch (Exception e) {
            System.err.println("Error in /scms/admin-users/datatable: " + e.getMessage());
            return null;
        }
    }

    @GetMapping("/edit/{id}")
    public ResponseEntity<ResEditUsers> index(@PathVariable("id") String id) {
        return usersService.editUsers(id);
    }

    @PostMapping("/update")
    public ResponseEntity<?> update(@RequestBody ReqUdateUsers data) {
        Users users = usersService.updateUsers(data);
        return ResponseEntity.ok(DataResponse.success(users, "updated"));
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<?> delete (@PathVariable("id") Long id) {
        Users users = usersService.deleteUser(id);
        return ResponseEntity.ok(DataResponse.success(users, "deleted"));
    }

    

}
