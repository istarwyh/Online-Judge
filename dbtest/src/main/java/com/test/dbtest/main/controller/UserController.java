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
import java.io.FileNotFoundException;
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
//        return "userLogin";
        return "login";
    }


    //    public ModelAndView login(@RequestParam("userid")String userid, @RequestParam("password")String password, HttpSession session){
    @RequestMapping(value = "/userLogin")
    public ModelAndView login(@RequestParam("userid") String userid, @RequestParam("password") String password, HttpSession session) {
        String name = userService.login(userid, password);
        ModelAndView list = new ModelAndView();
        if (name != null) {
            list.setViewName("redirect:/user/list");
            list.addObject("username", name);
            session.setAttribute("username", name);
            return list;
        } else return new ModelAndView("loginError");
    }

    @RequestMapping(value = "/signupPage")
    public String registerpage() {
        return "sign-up";
    }
    @ResponseBody
    @RequestMapping(value = "/signup")
    public ModelAndView signup(HttpSession session, @RequestParam("userid") String userid, @RequestParam("username") String username, @RequestParam("password") String password) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/user/");
        if (userService.findId(userid) != null) {
            session.setAttribute("message", "账户已存在！");
        } else {
            userService.register(userid, username, password);
            session.setAttribute("message", "注册成功！");
        }
        return modelAndView;
    }
    @ResponseBody
    @RequestMapping(value = "/register")
    public ModelAndView register(HttpSession session, @RequestParam("userid") String userid, @RequestParam("username") String username, @RequestParam("password") String password) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/user/");
        if (userService.findId(userid) != null) {
            session.setAttribute("message", "账户已存在！");
        } else {
            userService.register(userid, username, password);
            session.setAttribute("message", "注册成功！");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/list")
    /* 为什么这个是string? */
    public String index(Model model, @RequestParam("username") String username) {
        List<Problem> problems = new ArrayList<>();
        problems = userService.AllProblem();
        model.addAttribute("problems", problems);
        model.addAttribute("username", username);
        return "list2";
    }

    @RequestMapping(value = "/contextPage/{problemId}")
    public ModelAndView goToTitle(@PathVariable(value = "problemId", required = true) int problemid, HttpSession session) throws FileNotFoundException {
        ModelAndView modelAndView = new ModelAndView();
        String status = "等待判题";
        String answer = null;
        Problem problem = userService.ProblemContext(problemid);
        modelAndView.setViewName("/context");
        List<String> activproblemContext = userService.getcontext(problem.getId());
        problem.setProblemcontext(activproblemContext);
        modelAndView.addObject("problem", problem);
        modelAndView.addObject("useranswer",answer);
        modelAndView.addObject("result", status);
        session.setAttribute("message", status);
        pid=problemid;
        return modelAndView;
    }
    @ResponseBody
    @RequestMapping(value = "/context")
    public ModelAndView title(Model model,@RequestParam("answer") String answer, HttpSession session) throws FileNotFoundException {
        String status ="等待判题";
        File solution = new File("./repository/answer/Solution.java");

        try{
            Files.write(solution.toPath(), answer.getBytes(StandardCharsets.UTF_8));
            System.out.println("创建成功");
        }
        catch (IOException e){
            e.printStackTrace();
            System.out.println("创建失败");
        }
        long Runtime = 0;
        long startTime = System.currentTimeMillis();
        if(userService.compile(solution).equals("编译成功")){
            status= userService.run(pid);
            Runtime = System.currentTimeMillis() - startTime;
        }
        else{
            status= userService.compile(solution);
//            System.out.println(status);
        }
        ModelAndView modelAndView = new ModelAndView();
        Problem problem = userService.ProblemContext(pid);
        modelAndView.setViewName("/context");
        List<String> activproblemContext =userService.getcontext(problem.getId());
        problem.setProblemcontext(activproblemContext);
        modelAndView.addObject("problem",problem);
        modelAndView.addObject("useranswer",answer);
        session.setAttribute("message",status+ "\t\n"+"运行时间:" + Runtime+"ms");
        return modelAndView;
    }
}
