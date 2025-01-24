/*
controller 层，主要负责接收和处理来自客户端的请求，并决定如何响应式这些请求
*/
package org.example.controller;

import com.google.gson.Gson;
import org.example.pojo.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.example.mapper.StudentMapper;
import org.example.mapper.UserMapper;
import java.util.List;

@RestController
@CrossOrigin(origins = {"*","null"})
public class StudentController {
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private UserMapper userMapper;
    private Gson gson = new Gson();

    String successMsg = "{\"code\":200,\"msg\":\"操作成功!\"}";

    @GetMapping("/students")
    public String getStudents(){
        List<Student> students = studentMapper.selectList(null);
        return gson.toJson(students);
    }

    @PostMapping("/add")
    public String addStudents(@RequestBody Student student){
        studentMapper.insert(student);
        return successMsg;
    }

    @PostMapping("/delete")
    public String removeStudents(@RequestBody Student student){
        studentMapper.deleteById(student.getId());
        //若主键字段不叫id:
        //deleteById(@Param("sid") Integer sid)
        return successMsg;
    }

    @PostMapping("/update")
    public String updateStudent(@RequestBody Student student){
        studentMapper.updateById(student);
        return successMsg;
    }





}
