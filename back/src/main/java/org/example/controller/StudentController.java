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

import java.util.ArrayList;
import java.util.Arrays;
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

    @GetMapping("/dataExample")
    public void dataExample(){
        byte b=100;
        short s=1000;
        int i=152000;
        //长整型：需要后缀 L 或 l，否则会被认为是 int 类型
        long l=1500000L;

        float f=3.14f;
        double d=3.14;

        char c='A';
        boolean flag=true;

        String str="asd,asd";
        int[] nums={1,2,3};
        System.out.println(Arrays.toString(nums));

        List<String> list=new ArrayList<String>();
        list.add("A");
        list.add("B");
        list.add("C");
        System.out.println(list);

        Day day=Day.MONDAY;
        System.out.println(day);
        Day2 day2=Day2.MONDAY;
        System.out.println(day2.name());
        System.out.println(day2.getChineseName());
    }
    //枚举类型：用于定义一组固定的常量
    /*枚举常量的值 是枚举类型的实例，包含名称和可选的字段。
    如果没有显式定义字段，枚举常量的值就是它们的名称（通过 name() 方法获取）。
    枚举常量可以包含字段、方法和构造函数，这使得它们更加灵活和强大。
    枚举常量是唯一的对象，可以通过 == 或 equals() 进行比较。*/
    enum Day{
        MONDAY,TUESDAY,WEDNESDAY
    }
    enum Day2 {
        MONDAY("星期一", 1),
        TUESDAY("星期二", 2),
        WEDNESDAY("星期三", 3);

        private String chineseName;
        private int index;

        // 构造函数
        Day2(String chineseName, int index) {
            this.chineseName = chineseName;
            this.index = index;
        }

        // 方法
        public String getChineseName() {
            return chineseName;
        }

        public int getIndex() {
            return index;
        }
    }








}
