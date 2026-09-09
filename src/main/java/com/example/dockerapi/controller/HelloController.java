package com.example.dockerapi.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//課題① エンドポイントにGET通信をしたときに、レスポンスが返却されるようにする
//エンドポイント：http://localhost:8080/api/users　(DB接続はしない、モックデータを使用すること)
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

//課題② エンドポイントにGET通信をしたときに、レスポンスが返却されるようにする
//エンドポイント：http://localhost:8080/users/{user_id}(DB接続がしてあること)
class Employee{
    private int id;
    private String name;
    private String email;

    public Employee(){}

    public Employee(int id, String name, String email){
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}

@RestController
@RequestMapping("/api")
public class HelloController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    //応用課題①
    @GetMapping ("/users")
    public Users getUser(){
        Users user = new Users(1,"John Doe","john.doe@example.com");
        return user;
    }

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello, Docker World!1234";
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

    //応用課題②
    @GetMapping ("/employees/{id}")//GetMappingはURL検索でアクセスされた時に実行する
    public List<Employee> getAllEmployees(@PathVariable int id){//メソッドを宣言　処理が終わるとEmployeeのリストを返す PathVariableは{}の値を変数名に格納
        String sql = "SELECT id,name,email FROM employee WHERE id = ?";//DBに命令するSQL文を変数sqlに格納
        List<Employee> employees = jdbcTemplate.query(//jdbcTemplate.query()はDBに対してSQLを実行する
            sql,//第一引数：命令文を渡す　BeanPropertyRowMapperはDBから返ってきたデータを列名とクラスのプロパティを結びつける
            BeanPropertyRowMapper.newInstance(Employee.class),//第二引数：列名とフィールド名を対応付けてjavaのデータに変換
            id//第三引数以降：命令文の？(プレースホルダー)部分に順に代入　newInstanceメソッドはクラスを指定することでこのクラスの形に自動変換してくれるインスタンスを作る
        );
        return employees;//得られたデータリストをwebブラウザやcurlに返す
    }
}
