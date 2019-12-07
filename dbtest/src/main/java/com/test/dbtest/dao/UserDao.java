package com.test.dbtest.main.dao;

import com.test.dbtest.main.entity.Problem;
import com.test.dbtest.main.entity.Result;
import com.test.dbtest.main.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserDao {
    @Select("SELECT username FROM user WHERE userid = #{userid} and password = #{password}")
    public String login(@Param("userid")String userid, @Param("password")String password);

    @Insert("INSERT INTO user(userid, username, password) VALUES (#{userid}, #{username}, #{password})") //3
    void insert(User user);


    @Select("SELECT userid FROM user WHERE userid = #{userid}")
    public String findId(@Param("userid")String userid);

    @Select("SELECT * FROM problem")
//    List<Problem> getTitle();
    List<Problem> getProblems();

//    @Select("SELECT * FROM problem")
    @Select("SELECT * FROM problem WHERE id = #{id}")
    Problem getContext(@Param("id")int id);
//    List<Problem> getContext(/*@Param("id")int id*/);
    @Insert("INSERT INTO log(problemid, userid, time,result) VALUES (#{userid}, #{username}, #{password})") //3
    void insertResult(Result result);
}
