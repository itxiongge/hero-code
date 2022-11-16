package com.hero;

import com.hero.pojo.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCExample {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        //数据库连接地址
        String url = "jdbc:mysql://localhost:3306/hello";
        String user = "root";//用户名
        String password = "root";//密码
        //1.注册数据库驱动，SPI
        Class.forName("com.mysql.jdbc.Driver");
        //2.获取数据库连接对象Connection。
        Connection conn = DriverManager.getConnection(url, user, password);
        //3.创建Sql语句对象Statement，填写SQL语句
        PreparedStatement preparedStatement = conn.prepareStatement("select * from t_user where name=?;");
        //传入查询参数
        preparedStatement.setString(1,"刘备【1】");
        //4.执行SQL查询，返回结果集对象ResultSet
        ResultSet resultSet = preparedStatement.executeQuery();
        List<User> users = new ArrayList<User>();
        //5.循环解析结果集，获取查询用户list集合
        while (resultSet.next()){
            User u = User.builder().id(resultSet.getInt("id"))
                    .name(resultSet.getString("name"))
                    .age(resultSet.getInt("age"))
                    .address(resultSet.getString("address")).build();
            users.add(u);
        }
        //打印查询结果
        System.out.println(users);
        //6.关闭连接，释放资源
        resultSet.close();//关闭结果集对象
        preparedStatement.close();//关闭Sql语句对象
        conn.close();//关闭数据库连接对象
    }
}
