package com.test.dbtest.main.controller;

import com.test.dbtest.main.dao.UserDao;
import com.test.dbtest.main.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public String show() {
        return "userLogin";
    }

    @RequestMapping(value = "/userLogin")
    public String login(@RequestParam("userid")String userid, @RequestParam("password")String password, HttpServletRequest request){
        String name = userService.login(userid, password);
        if(name != null){
            return "index";
        }
        else return "loginError";
    }

    @RequestMapping(value = {"/registerpage"})
    public String registerpage(){
        return "register";
    }

    @ResponseBody
    @RequestMapping(value = "/register")
    public String register(@RequestParam("userid") String userid, @RequestParam("username") String username, @RequestParam("password") String password){
        if(userService.findId(userid) != null){
            return "账户已存在！";
        }
        else{
            userService.register(userid, username, password);
            return "注册成功！";
        }
    }

    @RequestMapping(value = "/twosum")
    public String twosum(){
        return "twosum";
    }
}
