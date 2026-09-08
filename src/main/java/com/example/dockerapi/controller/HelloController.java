package com.example.dockerapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

class Users{
    private int id;
    private String name;
    private String email;

    public Users(int id,String name,String email){
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public int getId(){return id;}
    public String getName(){return name;}
    public String getEmail(){return email;}
}

@RestController
@RequestMapping("/api")
public class HelloController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping ("/users")
    public Users getUser(){
        Users user = new Users(1,"John Doe","john.doe@example.com");
        return user;
    }

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello, Docker World!";
    }

    @GetMapping("/hoge")
    public String sayHoge() {
        return "hogehogehoge";
    }

    @GetMapping("/check-db")
    public String checkDbConnection() {
        try {
            jdbcTemplate.queryForObject("SELECT 1", Integer.class); // MySQLへの接続確認
            return "Database connection is successful!";
        } catch (Exception e) {
            return "Database connection failed!";
        }
    }
}
