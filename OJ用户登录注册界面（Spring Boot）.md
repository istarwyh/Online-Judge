

# OJ用户登录注册界面（Spring Boot）

## 代码

### Controller层

```java
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

```



### Dao层

```java
package com.test.dbtest.main.dao;

import com.test.dbtest.main.entity.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserDao {
    @Select("SELECT username FROM user WHERE userid = #{userid} and password = #{password}")
    public String login(@Param("userid")String userid, @Param("password")String password);

    @Insert("INSERT INTO user(userid, username, password) VALUES (#{userid}, #{username}, #{password})") //3
    void insert(User user);

    @Select("SELECT userid FROM user WHERE userid = #{userid}")
    public String findId(@Param("userid")String userid);

}

```



### 实体层

```java
package com.test.dbtest.main.entity;

public class User {
    private String userid;
    private String username;
    private String password;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
```



### Service层

```java
package com.test.dbtest.main.service;

import com.test.dbtest.main.dao.UserDao;
import com.test.dbtest.main.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}

```



## 运行示例

- 浏览器输入http://localhost:8080/user/

![](https://i.loli.net/2019/11/10/UPgfIDlah47FeyO.png)



- 点击注册

![](https://i.loli.net/2019/11/10/xqSM5H4NWnZVj3m.png)



- 登录后界面(题目列表)

![](https://i.loli.net/2019/11/10/XpCExRLIqna9sgz.png)

### 前端设计思路
依据作业要求简单设计,登录样式简单参考力扣.单页面跳转,页面重复以营造页面内跳转效果.html与css部分的示例代码如下:
userLogin
```html
    <main>
        <article class="css-3">
            <p></br></p>
            <form action="/user/userLogin" method="post">
                <p><strong>用户名：</strong><input type="text" name="userid" /><br>
                    <br>
                    <strong>密&nbsp;&nbsp;&nbsp;&nbsp;码：</strong><input type="password" name="password" /><br></p>
             
                    <input type="submit" class="css-1" value="登录" />
            </form>
            <p><a href="/user/registerpage" title= "第一次?赶快注册吧!" target="_blank"><button>注册</button></a>&nbsp;&nbsp;&nbsp;&nbsp;
                <button>忘记密码</button></p>
            <p></br></p>
            
        </article> 

    </main>
```
```css
article{
    width: 500px;
    margin:20px auto;
    padding:50px,50px,50px,50px;
    background-color: rgb(254, 254, 254);
    text-align:center;
    border: 1px solid rgb(253,253,253) ;
    /* border: 1px solid black ; */
    
}
.css-3{
    width: 500px;
    margin:160px auto;
    padding:50px,50px,50px,50px;
    background-color: rgb(254, 254, 254);
    text-align:center;
    border: 1px solid rgb(253,253,253) ;
    /* border: 1px solid black ; */
    
}
h1{
    margin: 0;
    font-size:60px;
    color: #5170FF;
    text-align: center;
    padding: 20px 0;
    /* background-color: #c5dbdabe; */
    text-shadow:1px 2px 1px black;

}
footer{
    width: 100%;
    margin:0;
    padding:2px;
    font-size: 12px;
    background-attachment: fixed;
    background-color: rgb(245, 243, 243);
    text-align: left;
}
.foot{width:100%; height:2px;background:#900;margin:0 auto}

.css-1 {
    display: inline-flex;
    vertical-align: middle;
    -webkit-box-pack: center;
    justify-content: center;
    -webkit-box-align: center;
    align-items: center;
    line-height: 20px;
    font-size: 14px;
    cursor: pointer;
    color: rgb(255, 255, 255);
    box-shadow: rgba(38, 50, 56, 0.2) 0px 0px 0px 1px inset;
    height: 40px;
    border-radius: 3px;
    transition: all 0.18s ease-in-out 0s;
    outline: 0px;
    border-width: 0px;
    border-style: initial;
    border-color: initial;
    border-image: initial;
    background: rgb(69, 90, 100);
    padding: 0px 106px;
    opacity: 1;
    text-overflow: ellipsis;
    white-space: nowrap;
    overflow: hidden;
}
```