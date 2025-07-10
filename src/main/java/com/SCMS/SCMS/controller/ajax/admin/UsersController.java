package com.SCMS.SCMS.controller.ajax.admin;

import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.SCMS.SCMS.entities.Users;
import com.SCMS.SCMS.hepler.ObjResponseEntity;
import com.SCMS.SCMS.hepler.ReqDatatableParam;
import com.SCMS.SCMS.hepler.ResDatatableParam;
import com.SCMS.SCMS.model.request.admin.ReqSaveUsers;
import com.SCMS.SCMS.model.response.admin.ResListUsers;
import com.SCMS.SCMS.repository.LoginRepository;
import com.SCMS.SCMS.repository.admin.UsersRepository;
import com.SCMS.SCMS.service.admin.UsersService;

@Controller
@Transactional
@RequestMapping("/scms/admin-users")
public class UsersController {
    private final UsersRepository usersRepository;
    private static final Logger logger = LoggerFactory.getLogger(UsersController.class);

    @Autowired
    private UsersService usersService;

    UsersController(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @PostMapping("/datatable")
    @ResponseBody
    public ResDatatableParam<ResListUsers> index(@RequestBody ReqDatatableParam data) {
        return usersService.getUsersList(data);
    }

    @PostMapping("/create")
    public ObjResponseEntity<String> index(@RequestBody ReqSaveUsers data) {
        return usersService.createUsers(data);
    }

   

}
