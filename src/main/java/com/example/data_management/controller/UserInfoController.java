package com.example.data_management.controller;

import com.example.data_management.bean.DataSourceInfo;
import com.example.data_management.dao.UserRepository;
import com.example.data_management.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
public class UserInfoController {
    @Autowired
    private UserRepository userRepository;
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public User login(@RequestParam String username,@RequestParam String password){
        return userRepository.findByUserNameAndUserPassword(username,password);
    }

    @RequestMapping(value = "/getTableUserInfo",method = RequestMethod.POST)
    public List<User> findTableUser(){
        return userRepository.findAllBy();
    }

    @Transactional
    @RequestMapping(value = "/delByUserId",method = RequestMethod.POST)
    public void delUserById(@RequestParam int userId){
        userRepository.deleteByUserId(userId);
    }

    @RequestMapping(value = "/addUserInfo",method = RequestMethod.POST)
    public void addUserInfo(@RequestBody User userInfo){
        userRepository.save(userInfo);
    }
}
