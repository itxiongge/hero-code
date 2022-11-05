package com.hero.service;

import com.hero.common.ShardingDataSource;
import com.hero.pojo.Order;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderService {

    private static Connection connection;

    static {
        try {
            DataSource dataSource = ShardingDataSource.getInstance();
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public boolean addOrderInfo(Order order) throws Exception{
        String sql = "insert into t_order(order_id, user_id, info) values (?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, order.getOrderId());
        preparedStatement.setInt(2, order.getUserId());
        preparedStatement.setString(3, order.getInfo());
        boolean result = preparedStatement.execute();
        connection.close();
        return result;
    }



    public List<Order> findAll() throws Exception {
        PreparedStatement preparedStatement = connection.prepareStatement("select * from t_order order by order_id");
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Order> orderList = new ArrayList<>();
        while(resultSet.next()) {
            Order order = new Order();
            order.setOrderId(resultSet.getInt("order_id"));
            order.setUserId(resultSet.getInt("user_id"));
            order.setInfo(resultSet.getString("info"));
            orderList.add(order);
        }
        connection.close();
        return orderList;
    }

    public List<Order> findByUserId(int userId) throws Exception {
        PreparedStatement preparedStatement = connection.prepareStatement("select * from t_order where user_id = ?");
        preparedStatement.setInt(1,userId);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Order> orderList = new ArrayList<>();
        while(resultSet.next()) {
            Order order = new Order();
            order.setOrderId(resultSet.getInt("order_id"));
            order.setUserId(resultSet.getInt("user_id"));
            order.setInfo(resultSet.getString("info"));
            orderList.add(order);
        }
        connection.close();
        return orderList;
    }

    public Order findById(int orderId) throws Exception {
        String sql = "select * from t_order where order_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1,orderId);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Order> orderList = new ArrayList<>();
        while(resultSet.next()) {
            Order order = new Order();
            order.setOrderId(resultSet.getInt("order_id"));
            order.setUserId(resultSet.getInt("user_id"));
            order.setInfo(resultSet.getString("info"));
            orderList.add(order);
        }
        if ( orderList.size() >= 1 ) {
            return orderList.get(0);
        }
        connection.close();
        return null;
    }

    public List<Order> findByPage() throws Exception{
        String sql = "select * from t_order order by order_id limit 0,5";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Order> orderList = new ArrayList<>();
        while(resultSet.next()) {
            Order order = new Order();
            order.setOrderId(resultSet.getInt("order_id"));
            order.setUserId(resultSet.getInt("user_id"));
            order.setInfo(resultSet.getString("info"));
            orderList.add(order);
        }
        connection.close();
        return orderList;
    }

    public void deleteAll() throws Exception{
        String sql = "delete from t_order";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.execute();
        connection.close();
    }
}
