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
import org.example.service.ExternalApiService;
import reactor.core.publisher.Mono;

@RestController
@CrossOrigin(origins = {"*","null"})
public class StudentController {
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private UserMapper userMapper;
    private Gson gson = new Gson();
    @Autowired
    private ExternalApiService externalApiService;

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

    @GetMapping("/call-external-api")
    public String callExternalApi(@RequestParam String id) {
        //异步方式获取结果
        Mono<String> mono =externalApiService.getData(id);
        //通过subscribe处理最终结果或错误
        mono.subscribe(
            result -> {
                System.out.println("Received data: " + result);
            }, // 处理成功的结果
            error -> {
                System.err.println("Error occurred: " + error.getMessage());
            }, // 处理错误
            () -> {
                System.out.println("Request completed");
            } // 处理完成事件
        );

        //同步方式获取结果
        //String res=mono.block();
        //System.out.println("同步："+res);

        return "处理完成";
    }

    @GetMapping("/call-external-api2")
    public Mono<String> callExternalApi2(@RequestParam String id) {
        return externalApiService.getData2(id);

        /*Mono<String> mono =externalApiService.getData2(id);
        mono.subscribe(
                result -> {
                    System.out.println("Received data: " + result);
                }, // 处理成功的结果
                error -> {
                    System.err.println("Error occurred: " + error.getMessage());
                }, // 处理错误
                () -> {
                    System.out.println("Request completed");
                } // 处理完成事件
        );*/
    }






}
