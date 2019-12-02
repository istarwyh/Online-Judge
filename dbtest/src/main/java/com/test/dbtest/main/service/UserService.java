package com.test.dbtest.main.service;

import com.sun.tools.jdeprscan.scan.Scan;
import com.test.dbtest.main.dao.UserDao;
import com.test.dbtest.main.entity.Problem;
import com.test.dbtest.main.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.ast.NullLiteral;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.*;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;
import java.util.Scanner;

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

    public String ProblemContext(int id){
        return userDao.getContext(id);
    }

    public String compile(File solution){
        try {
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            if(compiler == null){
                System.out.println("JDK required");
                return "编译错误";
            }else{
                System.out.println("got it");
            }
            int compilationResult = compiler.run(null, null, null, solution.getPath());
            if (compilationResult == 0) {
                System.out.println("Compilation is successful");
            } else {
                System.out.println("Compilation Failed");
                return "编译错误";
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return "编译成功";
    }

    public String run(int problemid){
        File folder = new File("./repository/answer");
        try{
            URL[] urls = new URL[]{folder.toURI().toURL()};
            URLClassLoader loader = new URLClassLoader(urls);
            Class c = loader.loadClass("Solution");
            Method m = c.getDeclaredMethod("main", String[].class);

            File inAndout = new File("./repository/in_out/" + problemid);
            int length = inAndout.listFiles().length;
            int count = 0;
            for (int i = 1; i <= length / 2; i++){
                File in = new File("./repository/in_out/" + problemid + "/" + i + ".in");
                StringBuffer intemp = new StringBuffer();
                Scanner input = new Scanner(in);
                while(input.hasNext()){
                    String s = input.nextLine();
                    intemp.append(s + "\n");
                }
                String data = intemp.toString();
                InputStream stdin = System.in;
                System.setIn(new ByteArrayInputStream(data.getBytes()));
                final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
                System.setOut(new PrintStream(outContent));
                m.invoke(null, (Object)new String[]{});

                File out = new File("./repository/in_out/" + problemid + "/"  + i + ".out");
                Scanner output = new Scanner(out);
                StringBuffer outtemp = new StringBuffer();
                while(output.hasNext()){
                    String s = output.nextLine();
                    outtemp.append(s);
                }
                String answer = outtemp.toString();

                input.close();
                output.close();

                if(outContent.toString().trim().equals(answer)) count++;
                outContent.close();

                }
            if(count == length / 2) return "答案正确";
            else return "答案错误";
        }
        catch (Exception e){
            e.printStackTrace();
            return "运行错误";
        }
    }
}
