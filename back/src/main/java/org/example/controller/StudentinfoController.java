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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    //使用logback记录日志
    private static final Logger logger = LoggerFactory.getLogger(StudentinfoController.class);

    String successMsg = "{\"code\":200,\"msg\":\"操作成功!\"}";
    //查新班级信息(自行写sql)
    @GetMapping("/classInfo")
    public String getClassInfo(@RequestParam(required = false) String className){
        ArrayList<Object> classInfo=classinfoMapper.selectClassinfo(className);
        return gson.toJson(classInfo);
    }
    //查询学生信息
    @GetMapping("/studentInfo")
    public String getStudentInfo(@RequestParam(required = false) String name,@RequestParam(required = false) String classid){
        QueryWrapper<Studentinfo> queryWrapper=new QueryWrapper<>();
        queryWrapper.like("name",name)
                .eq("classId",classid)
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
    @GetMapping("/studentInfo2")
    public String getStudentInfo2(@RequestParam(required = false) String name,@RequestParam(required = false) String classid){
        ArrayList<Object> studentInfo=studentinfoMapper.selectStudentinfo(name,classid);
        return gson.toJson(studentInfo);
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

        /* 选择合适的日志级别来记录信息至关重要。
        一般来说，开发阶段使用 `TRACE` 和 `DEBUG` 级别的信息帮助调试，
        生产环境则可以主要依赖 `INFO`、`WARN` 和 `ERROR` 级别的日志来监控应用状态和运营健康。
        在配置日志时，需考虑不同环境的需求，合理设置记录级别，以避免日志文件过大或遗漏关键信息。*/
        logger.trace("This is a TRACE message.");
        logger.debug("This is a DEBUG message.");
        logger.info("This is an INFO message.");
        logger.warn("This is a WARN message.");
        logger.error("This is an ERROR message.");

        return one;
    }



}
