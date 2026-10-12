/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lib;

/**
 *
 * @author PC
 */
 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class databaseConnection {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/lib_project";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private static Connection connection;

    // Private constructor to prevent instantiation
    public databaseConnection() {
    }

    
    
    
      public static void Insert_into_books_table(book newBook) {
        // Using try-with-resources to automatically close resources
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, "")) {
            // SQL query to insert a new book
            String sqlQuery = "INSERT INTO books (title_book, author_name, category,   " +
                    "number_of_available_copies, price) " +
                    "VALUES ( ?, ?,  ?, ?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
                // Set values for each parameter in the prepared statement
                preparedStatement.setString(1, newBook.getTitle());
                preparedStatement.setString(2, newBook.getAuthorName());
                preparedStatement.setString(3, newBook.getCategory());
                 preparedStatement.setInt(4, newBook.getNumberOfAvailableCopies());
                 preparedStatement.setDouble(5, newBook.getPrice());

                // Execute the update
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
      
      
      
      
      
      public void updateBookDetailsInDatabase(int bookId, double newPrice, int newAvailableCopies) {
    // Assuming you have a database connection
    try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/lib_project", "root", "")) {
        // Create a PreparedStatement to update data in the books table
        String query = "UPDATE books SET price = ?, number_of_available_copies = ? WHERE book_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setDouble(1, newPrice);
            preparedStatement.setInt(2, newAvailableCopies);
            preparedStatement.setInt(3, bookId);

            // Execute the update
            preparedStatement.executeUpdate();
        }
    } catch (SQLException e) {
        e.printStackTrace();
        // Handle the exception as needed
    }
}
      
      
      
      
      
      
      
      
      
      // Example method to delete a book from the database by book_id
public void deleteBookFromDatabase(int bookId) {
    // Assuming you have a database connection
    try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/lib_project", "root", "")) {
        // Create a PreparedStatement to delete a row from the books table
        String query = "DELETE FROM books WHERE book_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, bookId);

            // Execute the delete
            preparedStatement.executeUpdate();
        }
    }  catch (SQLException e) {
        e.printStackTrace();
        // Handle the exception as needed
    }
}
    
    
    
    // Method to get or create a connection
    public static Connection getConnection() {
        if (connection == null) {
            try {
                // Load the JDBC driver
                Class.forName("com.mysql.cj.jdbc.Driver");

                // Establish the database connection
                connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
                System.out.println("Connected to the database");
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
                // Handle the exception appropriately
            }
        }
        return connection;
    }

    // Method to close the connection (optional)
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Connection closed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }
    }
}
