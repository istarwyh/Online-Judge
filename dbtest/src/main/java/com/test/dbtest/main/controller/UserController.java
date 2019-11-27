package com.test.dbtest.main.controller;

import com.sun.media.jfxmedia.logging.Logger;
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
import java.util.ArrayList;
import java.util.List;

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

    @RequestMapping(value = "/contextPage/{problemId}")
    public String title(Model model,
    /*@RequestParam(value="problemid")Integer problemid*/
    @PathVariable(value = "problemId",required = true)Integer problemid
    ){
        List<Problem> context = new ArrayList<>();
        problemid++;
        context = userService.ProblemContext(problemid);
        model.addAttribute("context", context);

//        ModelAndView title = new ModelAndView();
//        title.addObject("id", context);

        return "context";

    }

    @RequestMapping(value = "/index")
    public String index(Model model){
        List<Problem> problems = new ArrayList<>();
        problems = userService.AllProblem();
        model.addAttribute("titles", problems);
        return "index";
    }
}
