package org.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.example.mapper.StudentMapper;
import org.example.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.google.gson.Gson;
import org.example.pojo.Student;
import org.example.pojo.User;
import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin(origins = {"*","null"})
public class UserController {
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private UserMapper userMapper;
    private Gson gson = new Gson();

    String successMsg = "{\"code\":200,\"msg\":\"操作成功!\"}";

    @PostMapping("/login")
    public String loginStudent(@RequestBody User user){
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.setEntity(user);
        User user_selected = userMapper.selectOne(userQueryWrapper);
        if(user_selected == null){
            return "0";
        }
        return "1";
    }

    @PostMapping("/register")
    public String register(@RequestBody User user){
        userMapper.insert(user);
        return successMsg;
    }

    @PostMapping("/search")
    public String search(@RequestBody HashMap<String,String> data){
        String name=data.get("name");
        QueryWrapper<Student> studentQueryWrapper=new QueryWrapper<>();
        studentQueryWrapper.like("name",name);
        List<Student> students=studentMapper.selectList(studentQueryWrapper);
        return gson.toJson(students);
    }

}
