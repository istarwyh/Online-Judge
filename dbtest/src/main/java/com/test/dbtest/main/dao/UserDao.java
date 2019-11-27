package com.test.dbtest.main.dao;

import com.test.dbtest.main.entity.Problem;
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
    List<Problem> getTitle();

//    @Select("SELECT * FROM problem")
    @Select("SELECT * FROM problem WHERE ID = #{id}")
    List<Problem> getContext(int id);
//    List<Problem> getContext(/*@Param("id")int id*/);
}
