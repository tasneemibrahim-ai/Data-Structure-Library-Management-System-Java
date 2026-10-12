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
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

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
                        JOptionPane.showMessageDialog(null,"book added successfully.");

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
      public boolean checkIfNameExists(int nameToCheck) {
    try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
         PreparedStatement selectStatement = connection.prepareStatement("SELECT * FROM books WHERE book_id = ?");
    ) {
        selectStatement.setInt(1, nameToCheck);

        try (ResultSet resultSet = selectStatement.executeQuery()) {
            // If there is at least one result, the name exists in the "books" table
            return resultSet.next();
        }
    } catch (SQLException e) {
        e.printStackTrace();
        return false; // Handle the exception based on your needs
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
      
      public void loan_borrowers(DefaultTableModel tableModel) {
    try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
         PreparedStatement selectStatement = connection.prepareStatement("SELECT * FROM book_you_are_borrowed");
         ResultSet resultSet = selectStatement.executeQuery()) {

        // Clear existing data in the table model
        tableModel.setRowCount(0);

        // Retrieve data from the result set and add it to the table model
        while (resultSet.next()) {
            Object[] rowData = {
                    resultSet.getObject("id"),
                    resultSet.getObject("username"),
                    resultSet.getObject("email"),
                    resultSet.getObject("password"),
                    resultSet.getObject("mobile"),
                    resultSet.getObject("book_id"),
                    resultSet.getObject("title_book"),
                    resultSet.getObject("author_name"),
                    resultSet.getObject("category"),
                    resultSet.getObject("price"),
                    resultSet.getObject("date_borrowed"),
                    resultSet.getObject("date_returned"),
                    resultSet.getObject("days_borrowed"),
            };
            tableModel.addRow(rowData);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
}
 
      
      
      
      
      
      
      
      
        public void loadDataIntoTableModel(DefaultTableModel tableModel) {
    try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
         PreparedStatement selectStatement = connection.prepareStatement("SELECT * FROM books");
         ResultSet resultSet = selectStatement.executeQuery()) {

        // Clear existing data in the table model
        tableModel.setRowCount(0);

        // Retrieve data from the result set and add it to the table model
        while (resultSet.next()) {
            Object[] rowData = {
                    resultSet.getObject("book_id"),
                    resultSet.getObject("title_book"),
                    resultSet.getObject("author_name"),
                    resultSet.getObject("category"),
                    resultSet.getObject("number_of_available_copies"),
                    resultSet.getObject("price"),
            };
            tableModel.addRow(rowData);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
}

        
        
        
             public void loadDataIntoTableModel_sort_by_price(DefaultTableModel tableModel) {
    try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
         PreparedStatement selectStatement = connection.prepareStatement("SELECT * FROM books order by price");
         ResultSet resultSet = selectStatement.executeQuery()) {

        // Clear existing data in the table model
        tableModel.setRowCount(0);

        // Retrieve data from the result set and add it to the table model
        while (resultSet.next()) {
            Object[] rowData = {
                    resultSet.getObject("book_id"),
                    resultSet.getObject("title_book"),
                    resultSet.getObject("author_name"),
                    resultSet.getObject("category"),
                    resultSet.getObject("number_of_available_copies"),
                    resultSet.getObject("price"),
            };
            tableModel.addRow(rowData);
        }

    } catch (SQLException e) {
        e.printStackTrace();
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
