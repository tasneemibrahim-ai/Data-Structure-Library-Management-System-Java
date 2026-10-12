package com.lib;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedList;

public class catalog {
    private LinkedList<book> books;
    private static final String URL = "jdbc:mysql://localhost:3306/librarysystem";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public catalog() {
        this.books = new LinkedList<>();
    }

    public void addBook(book newBook) {
        books.add(newBook);
    }

        public void updateBookDetails(book updatedBook) {
        
        for (book book : books) {
            if (book.getISBN().equals(updatedBook.getISBN())) {
               
                
                book.setTitle(updatedBook.getTitle());
                book.setAuthorName(updatedBook.getAuthorName());
                book.setCategory(updatedBook.getCategory());
                book.setNumberOfCopies(updatedBook.getNumberOfCopies());
                book.setNumberOfAvailableCopies(updatedBook.getNumberOfAvailableCopies());
                book.setPublicationYear(updatedBook.getPublicationYear());
                book.setBorrowable(updatedBook.isBorrowable());
                book.setPrice(updatedBook.getPrice());
                break;
            }
        }

        // Update database
        try {
            Connection databaseConnection = DriverManager.getConnection(URL, USER, PASSWORD);

            String updateQuery = "UPDATE books SET   number_of_copies=?, number_of_available_copies=?, " +
                                  " is_borrowable=?, price=? WHERE ISBN=?";
            PreparedStatement preparedStatement = databaseConnection.prepareStatement(updateQuery);
          
            preparedStatement.setInt(1, updatedBook.getNumberOfCopies());
            preparedStatement.setInt(2, updatedBook.getNumberOfAvailableCopies());
             preparedStatement.setString(3, updatedBook.isBorrowable());
            preparedStatement.setDouble(4, updatedBook.getPrice());
            preparedStatement.setString(5, updatedBook.getISBN());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Book details updated in the database.");
            } else {
                System.out.println("Failed to update book details in the database.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            
        }
    }

    public book searchBook(String ISBN) {
        for (book book : books) {
            if (book.getISBN().equals(ISBN)) {
                return book; // Book found
            }
        }
        return null; // Book not found
    }

    public LinkedList<book> getBooks() {
        return books;
    }
}
