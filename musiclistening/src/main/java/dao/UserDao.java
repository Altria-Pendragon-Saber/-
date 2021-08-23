package dao;

import entity.User;
import util.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {
    public User login(User loginUser){
        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = null;

        try {
            con = DBUtils.getConn();
            String sql = "select * from user where username=? and password=?";
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1,loginUser.getUsername());
            preparedStatement.setString(2,loginUser.getPassword());

            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setAge(resultSet.getInt("age"));
                user.setGender(resultSet.getString("gender"));
                user.setEmail(resultSet.getString("email"));

            }else{
                System.out.println("登陆失败");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtils.getClose(con,preparedStatement,resultSet);
        }
        return user;
    }

    public void insertUser(User user){
        Connection con = null;
        PreparedStatement preparedStatement = null;

        try {
            con = DBUtils.getConn();
            String sql = "insert into user(username,password,age,gender,email) values(?,?,?,?,?)";
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1,user.getUsername());
            preparedStatement.setString(2,user.getPassword());
            preparedStatement.setInt(3,user.getAge());
            preparedStatement.setString(4,user.getGender());
            preparedStatement.setString(5,user.getEmail());

            int ret = preparedStatement.executeUpdate();
            if(ret == 1){
                System.out.println("注册成功");
            }else{
                System.out.println("注册失败");
            }

        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            DBUtils.getClose(con,preparedStatement,null);
        }
    }


}
