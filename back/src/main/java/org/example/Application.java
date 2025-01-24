//定义了当前类所在的包为 `org.example`。包是用来组织类的，使得类名不会冲突
package org.example;
//这是用于启动 Spring Boot 应用的主要类
import org.springframework.boot.SpringApplication;
//标识这是一个 Spring Boot 应用程序
import org.springframework.boot.autoconfigure.SpringBootApplication;
//`@SpringBootApplication` 注解表示这是一个 Spring Boot 应用程序的启动类
@SpringBootApplication

//这个类是 Spring Boot 应用的入口
public class Application {
    //Java 应用程序的入口点。这个方法是程序启动时首先被调用的
    public static void main(String[] args){
        //调用 `SpringApplication.run()` 方法来启动 Spring Boot 应用
        SpringApplication.run(Application.class,args);
    }
}
