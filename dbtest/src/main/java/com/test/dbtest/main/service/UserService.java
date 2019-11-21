package com.test.dbtest.main.service;

import com.test.dbtest.main.dao.UserDao;
import com.test.dbtest.main.entity.Problem;
import com.test.dbtest.main.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    public String login(String userid, String password){
        return userDao.login(userid, password);
    }

    public void register(String userid, String username, String password){
        User user = new User();
        user.setUserid(userid);
        user.setUsername(username);
        user.setPassword(password);
        userDao.insert(user);
    }

    public String findId(String userid){
        return userDao.findId(userid);
    }

    public List<Problem> AllProblem(){
        return userDao.getTitle();
    }
}
