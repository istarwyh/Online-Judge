package com.test.dbtest.main.controller;

import com.test.dbtest.main.entity.Problem;
import com.test.dbtest.main.entity.User;
import com.test.dbtest.main.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    private UserService userService;

    private int pid = 1;

    @RequestMapping("/")
    public String show() {
        return "userLogin";
    }

    @RequestMapping(value = "/userLogin")
    public ModelAndView login(@RequestParam("userid")String userid, @RequestParam("password")String password, HttpSession session){
        String name = userService.login(userid, password);
        ModelAndView index = new ModelAndView();
        if(name != null){
            index.setViewName("redirect:/user/index");
            index.addObject("username", name);
            session.setAttribute("username", name);
            return index;
        }
        else return  new ModelAndView("loginError");
    }

    @RequestMapping(value = {"/registerpage"})
    public String registerpage(){
        return "register";
    }

    @ResponseBody
    @RequestMapping(value = "/register")
    public ModelAndView register(HttpSession session, @RequestParam("userid") String userid, @RequestParam("username") String username, @RequestParam("password") String password){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/user/registerpage");
        if(userService.findId(userid) != null){
            session.setAttribute("message", "账户已存在！");
        }
        else{
            userService.register(userid, username, password);
            session.setAttribute("message", "注册成功！");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/index")
    public String index(Model model, @RequestParam("username") String username){
        List<Problem> problems = new ArrayList<>();
        problems = userService.AllProblem();
        model.addAttribute("titles", problems);
        model.addAttribute("username", username);
        return "index";
    }

    @RequestMapping(value = "/contextPage/{problemId}")
    public ModelAndView goToTitle( @PathVariable(value = "problemId",required = true)int problemid){
        ModelAndView modelAndView = new ModelAndView();
        String context = userService.ProblemContext(problemid);
        modelAndView.setViewName("/context");
        modelAndView.addObject("context", context);
        pid = problemid;
        return modelAndView;
    }

    @ResponseBody
    @RequestMapping(value = "/context")
    public String title(Model model,@RequestParam("answer") String answer){

        File solution = new File("./repository/answer/Solution.java");
        try{
            Files.write(solution.toPath(), answer.getBytes(StandardCharsets.UTF_8));
            System.out.println("创建成功");
        }
        catch (IOException e){
            e.printStackTrace();
            System.out.println("创建失败");
        }

        if(userService.compile(solution).equals("编译成功")){
            return userService.run(pid);
        }
        else{
            return userService.compile(solution);
        }
    }
}
