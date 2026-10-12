package com.lib;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Objects;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class member  {
 String username, email,password,mobile;int bookId;
    LocalDate dateBorrowed ,dateReturned;
        Scanner scanner = new Scanner(System.in);
  long daysBorrowed;
        databaseConnection dbConnection = new databaseConnection();
        
        //        Connection connection = dbConnection.getConnection();

        
        
        public void runMemberInterface() {
    boolean exit = false;
do {
    System.out.println("Member Interface:");
    System.out.println("1. View Available Books");
    System.out.println("2. Borrow a Book");
    System.out.println("3. Return a Book");
    System.out.println("4. View Fine if found");
    System.out.println("5. Back to Main Menu");

    int choice = scanner.nextInt();

    if (choice == 1) {
        viewAvailableBooks();
    } else if (choice == 2) {
        borrowBook();
    } else if (choice == 3) {
        System.out.println("Enter your information:");
        scanner.nextLine(); // Consume the newline character

        System.out.print("Username: ");
        String user1 = scanner.nextLine();

        System.out.print("pass: ");
        String pass2 = scanner.nextLine();

        returnbook(user1, pass2);
    } else if (choice == 4) {
        System.out.println("Enter your information:");
        scanner.nextLine(); // Consume the newline character

        System.out.print("Username: ");
        String user = scanner.nextLine();

        System.out.print("pass: ");
        String pass = scanner.nextLine();

        viewFine(user, pass);
    } else if (choice == 5) {
        exit = true;
    } else {
        System.out.println("Invalid choice. Please enter a valid option.");
    }
} while (!exit);
        }
// Add methods for borrowBook, returnBook, and viewFine based on your requirements

  private void viewAvailableBooks() {
    try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/lib_project", "root", "")) {
    String selectQuery = "SELECT book_id, title_book, author_name, category, number_of_available_copies, price FROM books";
    Statement statement = connection.createStatement();
    ResultSet resultSet = statement.executeQuery(selectQuery);

    System.out.println("Available Books:");
    System.out.printf("%-5s %-30s %-30s %-20s %-10s %-10s%n", "ID", "Title", "Author", "Category", "Copies", "Price");
    System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------");

    while (resultSet.next()) {
        int bookId = resultSet.getInt("book_id");
        String title = resultSet.getString("title_book");
        String author = resultSet.getString("author_name");
        String category = resultSet.getString("category");
        int numberOfAvailableCopies = resultSet.getInt("number_of_available_copies");
        double price = resultSet.getDouble("price");

        System.out.printf("%-5d %-30s %-30s %-20s %-10d %-10.2f%n",
                bookId, title, author, category, numberOfAvailableCopies, price);
    }
} catch (SQLException e) {
    e.printStackTrace();
    // Handle the exception appropriately
}
  }


     private void borrowBook() {
System.out.println("Enter your information:");
    scanner.nextLine(); // Consume the newline character

System.out.print("Username: ");
  username = scanner.nextLine();
 
System.out.print("Email: ");
  email = scanner.nextLine();

System.out.print("Password: ");
  password = scanner.nextLine();

System.out.print("Mobile: ");
  mobile = scanner.nextLine();

System.out.println("Enter the book information:");
System.out.print("Book ID: ");
  bookId = Integer.parseInt(scanner.nextLine());  // Parse the input as an integer


// Get the dates from the user
System.out.print("Enter date borrowed (yyyy-MM-dd): ");
String dateBorrowedStr = scanner.nextLine();
  dateBorrowed = LocalDate.parse(dateBorrowedStr, DateTimeFormatter.ISO_DATE);

System.out.print("Enter date returned (yyyy-MM-dd): ");
String dateReturnedStr = scanner.nextLine();
  dateReturned = LocalDate.parse(dateReturnedStr, DateTimeFormatter.ISO_DATE);

// Calculate the number of days borrowed
  daysBorrowed = ChronoUnit.DAYS.between(dateBorrowed, dateReturned);
book book = fetchBookDetails(bookId);

         if (book ==null) {
                 System.out.println("Book  .");

         }
         else {
// Now, you can create a Loan object with the entered information
loan loan2 = new loan(username, email, password, mobile, bookId, book.getTitle(), book.getAuthorName(),
        book.getCategory(),  book.getPrice(), dateBorrowed, dateReturned, daysBorrowed);

    insertIntoBookBorrowed(loan2);

    System.out.println("Book borrowed successfully.");
         }
         
    }

private book fetchBookDetails(int bookId) {
    book book2 = null;

    try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/lib_project", "root", "")) {
        // Your existing code here
        String selectQuery = "SELECT * FROM books WHERE book_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setInt(1, bookId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    // Retrieve book details from the result set
                    String title = resultSet.getString("title_book");
                    String author = resultSet.getString("author_name");
                    String category = resultSet.getString("category");
                     double price = resultSet.getDouble("price");
                    int numberOfAvailableCopies = resultSet.getInt("number_of_available_copies");

                    if (numberOfAvailableCopies > 0) {
                        // Update the database to decrement number_of_available_copies
                        String updateQuery = "UPDATE books SET number_of_available_copies = ? WHERE book_id = ?";
                        try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                            updateStatement.setInt(1, numberOfAvailableCopies - 1);
                            updateStatement.setInt(2, bookId);
                            updateStatement.executeUpdate();
                        }

                        // Create a new Book object with the retrieved details
                        book2 = new book(title, author, category ,numberOfAvailableCopies, price);
                        // ... set other fields
                    } else {
                System.out.println("Book is not available. Added to the order queue.");        
                              



                    }
                } else {
                    System.out.println("Book not found.");
                }
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        // Handle the exception appropriately
    }

    return book2;
}

    
    
    
    
    private void insertIntoBookBorrowed(loan loan) {
    String insertQuery = "INSERT INTO book_you_are_borrowed (username, email, password, mobile, book_id, title, author, category, price, date_borrowed, date_returned, days_borrowed) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/lib_project", "root", "");
         PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
        preparedStatement.setString(1, loan.getUsername());
        preparedStatement.setString(2, loan.getEmail());
        preparedStatement.setString(3, loan.getPassword());
        preparedStatement.setString(4, loan.getMobile());
        preparedStatement.setInt(5, loan.getBookId());
        preparedStatement.setString(6, loan.getTitle());
        preparedStatement.setString(7, loan.getAuthor());
        preparedStatement.setString(8, loan.getCategory());
         preparedStatement.setDouble(9, loan.getPrice());
        preparedStatement.setDate(10, java.sql.Date.valueOf(loan.getDateBorrowed()));
        preparedStatement.setDate(11, java.sql.Date.valueOf(loan.getDateReturned()));
        preparedStatement.setLong(12, loan.getDaysBorrowed());

        int rowsAffected = preparedStatement.executeUpdate();

        if (rowsAffected > 0) {
            System.out.println("Data inserted into book_borrowed table.");
        } else {
  
           
                System.out.println("Book is not available. Added to the order queue.");        }
    } catch (SQLException e) {
        e.printStackTrace();
        // Handle the exception appropriately
    }}

    private void viewFine(String username, String password) {
    String selectQuery = "SELECT * FROM book_you_are_borrowed WHERE username = ? AND password = ?";

    try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/lib_project", "root", "");
         PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);

        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            if (!resultSet.next()) {
                // No rows found for the given username and password
                System.out.println("Username and password combination not found.");
                return; // Exit the method
            }

            do {
                // Retrieve book details from the result set
                int loanId = resultSet.getInt("id");
                int bookId = resultSet.getInt("book_id");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                String category = resultSet.getString("category");
                 double price = resultSet.getDouble("price");
                LocalDate dateBorrowed = resultSet.getDate("date_borrowed").toLocalDate();
                LocalDate dateReturned = resultSet.getDate("date_returned").toLocalDate();
                long daysBorrowed = resultSet.getLong("days_borrowed");

                // Get the current date
                LocalDate currentDate = LocalDate.now();

                // Define a DateTimeFormatter for formatting the dates
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                // Format currentDate and dateReturned for display
                String formattedCurrentDate = currentDate.format(dateFormatter);
                String formattedDateReturned = dateReturned.format(dateFormatter);

                // Print the formatted dates for debugging
                System.out.println("Formatted Current Date: " + formattedCurrentDate);
                System.out.println("Formatted Date Returned: " + formattedDateReturned);

                // Calculate the difference in days between currentDate and dateReturned
                long daysDifference = ChronoUnit.DAYS.between(dateReturned, currentDate);
                    double fineAmount = 0.0;

                // Check if the difference is -2 or more
if (daysDifference >= 2 && daysDifference <= 5) {
        fineAmount = 50.0;
                        // Calculate the fine amount (assuming a fixed fine of 50 L.e)
                     // Display a message about the fine using JOptionPane
                    String message = "Your fine is " + fineAmount + " L.E. for returning the book " + daysDifference + " days late.\n"
            + "Current Date: " + formattedCurrentDate + "\n"
            + "Date Returned: " + formattedDateReturned;
    JOptionPane.showMessageDialog(null, message, "Late Return Fine", JOptionPane.WARNING_MESSAGE);
                }
else if (daysDifference >= 6 && daysDifference <= 10) {
        fineAmount = 100.0;
        String message = "Your fine is " + fineAmount + " L.E. for returning the book " + daysDifference + " days late.\n"
            + "Current Date: " + formattedCurrentDate + "\n"
            + "Date Returned: " + formattedDateReturned;
    JOptionPane.showMessageDialog(null, message, "Late Return Fine", JOptionPane.WARNING_MESSAGE);

}

else if (daysDifference > 10) {
        // You will bill the price of the book for more than 10 days late
        fineAmount = price;
          String message = "Your fine is " + fineAmount + " L.E. for returning the book " + daysDifference + " days late.\n"
            + "Current Date: " + formattedCurrentDate + "\n"
            + "Date Returned: " + formattedDateReturned;
    JOptionPane.showMessageDialog(null, message, "Late Return Fine", JOptionPane.WARNING_MESSAGE);
    }

else {
        // No fine for less than 2 days late
        fineAmount = 0.0;
    }



                // Display the information
                System.out.println("Loan ID: " + loanId);
                System.out.println("Book ID: " + bookId);
                System.out.println("Title: " + title);
                System.out.println("Author: " + author);
                System.out.println("Category: " + category);
                 System.out.println("Price: " + price);
                System.out.println("Date Borrowed: " + dateBorrowed);
                System.out.println("Date Returned: " + dateReturned);
                System.out.println("Days Borrowed: " + daysBorrowed);
                System.out.println("Your fine is : " + fineAmount + " L.E");
                System.out.println("------------------------");

            } while (resultSet.next());
        }
    } catch (SQLException e) {
        e.printStackTrace();
        // Handle the exception appropriately
    }
}


private void returnbook(String user, String pass) {

    String selectQuery = "SELECT * FROM book_you_are_borrowed WHERE username = ? AND password = ?";

    try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/lib_project", "root", "");
         PreparedStatement preparedStatement = connection.prepareStatement(selectQuery, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
        preparedStatement.setString(1, user);
        preparedStatement.setString(2, pass);

        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            if (!resultSet.next()) {
                // No rows found for the given username and password
                System.out.println("Username and password combination not found.");
                return; // Exit the method
            }

            // Print header
        System.out.printf("| %-8s | %-8s | %-30s | %-20s | %-15s |   %-10s | %-12s | %-12s |\n",
                    "Loan ID", "Book ID", "Title", "Author", "Category", "Price", "Date Borrowed", "Date Returned");

            System.out.println("|----------|----------|--------------------------------|--------------------|-----------------|-----------------|------------|--------------|--------------|");

            do {
                // Retrieve book details from the result set
                int loanId = resultSet.getInt("id");
                int bookId = resultSet.getInt("book_id");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                String category = resultSet.getString("category");
                 double price = resultSet.getDouble("price");
                LocalDate dateBorrowed = resultSet.getDate("date_borrowed").toLocalDate();
                LocalDate dateReturned = resultSet.getDate("date_returned").toLocalDate();
                long daysBorrowed = resultSet.getLong("days_borrowed");

                // Print book details
               System.out.printf("| %-8d | %-8d | %-30s | %-20s | %-15s |  %-10.2f | %-12s | %-12s |\n",
                        loanId, bookId, title, author, category, price, dateBorrowed, dateReturned);


                // Ask for confirmation
                System.out.print("Are you sure you want to return this book? (y/n): ");
                Scanner scanner = new Scanner(System.in);
                String confirmation = scanner.nextLine();

                if (confirmation.equalsIgnoreCase("y")) {
                    // Delete the row from the result set and update the database
                    resultSet.deleteRow();
                    
                    
                    
                     String returnBookQuery = "INSERT INTO return_book (loan_id, book_id, title, author, category, price, date_borrowed, date_returned) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    try (PreparedStatement returnBookStatement = connection.prepareStatement(returnBookQuery)) {
        returnBookStatement.setInt(1, loanId);
        returnBookStatement.setInt(2, bookId);
        returnBookStatement.setString(3, title);
        returnBookStatement.setString(4, author);
        returnBookStatement.setString(5, category);
        returnBookStatement.setDouble(6, price);
returnBookStatement.setDate(7, java.sql.Date.valueOf(dateBorrowed));
returnBookStatement.setDate(8, java.sql.Date.valueOf(dateReturned));
        returnBookStatement.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
        // Handle the exception appropriately
    }
                    System.out.println("Book returned successfully!");

                    // Increment number_of_available_copies in the books table
                    incrementAvailableCopies(connection, bookId);
                } else {
                    System.out.println("Book return cancelled.");
                }

            } while (resultSet.next());

            System.out.println("------------------------");
        }
    } catch (SQLException ex) {
        Logger.getLogger(member.class.getName()).log(Level.SEVERE, null, ex);
    }
}

private void incrementAvailableCopies(Connection connection, int bookId) throws SQLException {
    String updateQuery = "UPDATE books SET number_of_available_copies = number_of_available_copies + 1 WHERE book_id = ?";
    try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
        updateStatement.setInt(1, bookId);
        updateStatement.executeUpdate();
    }
}


}