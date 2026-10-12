/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lib;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
/**
 *
 * @author PC
 */
public class SortMembersbyName {
    
    
    public   List<user> getSortedUsersByUsername() {
        List<user> users = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/lib_project", "root", "")) {
            String selectQuery = "SELECT * FROM book_you_are_borrowed";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(selectQuery)) {
                while (resultSet.next()) {
                    int loanId = resultSet.getInt("id");
                    String username = resultSet.getString("username");
                    String email = resultSet.getString("email");
                    String password = resultSet.getString("password");
                    String mobile = resultSet.getString("mobile");

                    users.add(new user(loanId, username, email, password, mobile));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }

        // Sort the users by username
        Collections.sort(users, Comparator.comparing(user::getUsername));

        return users;
    }

 

  

}
