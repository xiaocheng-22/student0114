package org.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.gson.Gson;
import org.example.mapper.StudentinfoMapper;
import org.example.mapper.ClassinfoMapper;
import org.example.pojo.Classinfo;
import org.example.pojo.Studentinfo;
import org.example.service.StudentinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin(origins = {"*","null"})
public class StudentinfoController {
    @Autowired
    private StudentinfoMapper studentinfoMapper;
    @Autowired
    private ClassinfoMapper classinfoMapper;
    private Gson gson = new Gson();
    @Autowired
    private StudentinfoService studentinfoService;

    String successMsg = "{\"code\":200,\"msg\":\"操作成功!\"}";
    //查新班级信息(自行写sql)
    @GetMapping("/classInfo")
    public String getClassInfo(@RequestParam(required = false) String className){
        ArrayList<Object> classInfo=classinfoMapper.selectClassinfo(className);
        return gson.toJson(classInfo);
    }
    //查询学生信息
    @GetMapping("/studentInfo")
    public String getStudentInfo(@RequestBody HashMap<String,String> data){
        String name=data.get("name");
        QueryWrapper<Studentinfo> queryWrapper=new QueryWrapper<>();
        queryWrapper.like("name",name)
                .orderByAsc("number");

        List<Studentinfo> studentInfo=studentinfoMapper.selectList(queryWrapper);
        return gson.toJson(studentInfo);
    }
    //测试xml中的resultMap
    @GetMapping("/classInfoFF")
    public String getClassInfoFF(){
        ArrayList<Object> classInfo=classinfoMapper.selectClassData();
        return gson.toJson(classInfo);
    }
    @GetMapping("/testMyBatis")
    public Studentinfo testMyBatis(){
        Studentinfo student=new Studentinfo();
        student.setName("A");
        student.setNumber("1111");
        //studentinfoService.saveData(student);

        List<Studentinfo> da01=studentinfoService.getData();

        Studentinfo da02=studentinfoService.getDataById("634565456");

        List<Studentinfo> da03=studentinfoService.getDataByWrapper();

        studentinfoService.updateData();
        studentinfoService.deleteData();
        studentinfoService.deleteDatas();
        IPage<Studentinfo> pageData = studentinfoService.getDataPage();
        long count=studentinfoService.getDataCount();

        Studentinfo one=studentinfoService.getDataOne();

        return one;
    }



}
