package com.hero.jvm.classloader;

import java.sql.*;

public class JDBC {
    public static void main(String[] args) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        try {
            // 加载数据库驱动
            Class.forName("com.mysql.jdbc.Driver");
            // 通过驱动管理类获取数据库链接connection = DriverManager
            connection = DriverManager.getConnection("jdbc:mysql:///hero-mall?useUnicode=true&characterEncoding=UTF-8&serverTimezone=GMT%2B8", "root", "root");
            // 定义sql语句 ?表示占位符
            String sql = "select * from tb_user where id = ?";
            // 获取预处理 statement
            preparedStatement = connection.prepareStatement(sql);
            // 设置参数，第一个参数为 sql 语句中参数的序号（从 1 开始），第二个参数为设置的
            preparedStatement.setInt(1, 16);
            // 向数据库发出 sql 执行查询，查询出结果集
            rs = preparedStatement.executeQuery();
            // 遍历查询结果集
            while (rs.next()) {
                System.out.println("id = " + rs.getInt("id"));
                System.out.println("real_name = " + rs.getString("real_name"));
                System.out.println("gender = " + rs.getString("gender"));
                System.out.println("profession = " + rs.getString("profession"));
                System.out.println("nick_name = " + rs.getString("nick_name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 释放资源
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
    }
}
