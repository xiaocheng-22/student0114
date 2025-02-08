package org.example.controller;

import org.example.pojo.Student;
import org.example.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.example.service.RedisService;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RedisController {

    @Autowired
    private RedisService redisService;

    @Autowired
    private StudentService studentService;

    @GetMapping("/set")
    public String set(@RequestParam String key, @RequestParam String value) {
        redisService.setValue(key, value);
        redisService.syncData(key);
        return "Value set successfully!";
    }

    @GetMapping("/get")
    public Object get(@RequestParam String key) {
        return redisService.getValue(key);
    }

    @GetMapping("/getAllStudent")
    public List<Student> getAllStudent(){
        return studentService.getAllStudents();
    }
    @GetMapping("/getList")
    public List<String> testList(){
        List<String> list=new ArrayList<>();
        list.add("A");
        list.add("B");
        list.add(1,"C");
        list.remove(1);
        return list;
    }
    @GetMapping("/testRedis")
    public void testRedis(){
        redisService.testRedis();
    }


}
