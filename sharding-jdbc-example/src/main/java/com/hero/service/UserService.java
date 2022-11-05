package com.hero.service;

import com.hero.common.MasterSlaveDataSource;
import com.hero.pojo.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserService {
    public boolean addUser(User user) throws Exception{
        DataSource dataSource = MasterSlaveDataSource.getInstance();
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("insert into t_user(name, age, address) values (?,?,?)");
        preparedStatement.setString(1, user.getName());
        preparedStatement.setInt(2, user.getAge());
        preparedStatement.setString(3, user.getAddress());
        boolean result = preparedStatement.execute();
        return result;
    }

    public List<User> getUserList() throws Exception {
        DataSource dataSource = MasterSlaveDataSource.getInstance();
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("Select * from t_user");
        ResultSet resultSet = preparedStatement.executeQuery();
        List<User> userList = new ArrayList<>();
        while (resultSet.next()) {
            User user = new User();
            user.setId(resultSet.getInt("id"));
            user.setName(resultSet.getString("name"));
            user.setAge(resultSet.getInt("age"));
            user.setAddress(resultSet.getString("address"));
            userList.add(user);
        }
        return userList;
    }

}
