package com.lib;

 import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import java.util.Scanner;
import javax.swing.JOptionPane;
 
public class librarian {
    // Assuming catalog, book, and member classes are available
    private catalog lib = new catalog(); // Assuming catalog class is available
    private Scanner scanner = new Scanner(System.in);
    private static final String URL = "jdbc:mysql://localhost:3306/lib_project";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    Connection connection,connection2;
        databaseConnection dbConnection = new databaseConnection();
   
public void runLibrarianInterface() {
    boolean exit = false;

  do {
    System.out.println("Librarian Interface:");
    System.out.println("1. Add Book");
    System.out.println("2. Update Book Details");
    System.out.println("3. Delete Book");
    System.out.println("4. View Books");
    System.out.println("5. View list of borrowers");
    System.out.println("6. sort members by name");
    System.out.println("7. Sort Books by price");
     System.out.println("8. Search in books list");
    System.out.println("9. Back to Main Menu");

    int choice = scanner.nextInt();

    if (choice == 1) {
        addBook();
    } else if (choice == 2) {
        updateBookDetails();
    } else if (choice == 3) {
        deleteBook();
    } else if (choice == 4) {
        displayAllBooks();
    } else if (choice == 5) {
        viewLoans();
    } else if (choice == 6) {
        viewClientInformation();
    } else if (choice == 7) {
        sortBooksByPrice();
    }  else if (choice == 8) {
         searchBooksByName();
    } else if (choice == 9) {
        exit = true;
    } else {
        System.out.println("Invalid choice. Please enter a valid option.");
    }
} while (!exit);

closeConnection();
}

    private void closeConnection() {
        // Close the connection when the program exits
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }
    }

    public static void main(String[] args) {
        // Create a librarian instance
        librarian lib = new librarian();

        // Run the librarian interface
        lib.runLibrarianInterface();
    }
    
    
    

private void updateBookDetails() {
    displayAllBooks(); // Display all books

    System.out.println("Enter the book_id of the book you want to update:");
    int bookIdToUpdate = scanner.nextInt();

    System.out.println("Enter the new price:");
    double newPrice = scanner.nextDouble();

    System.out.println("Enter the new number of available copies:");
    int newAvailableCopies = scanner.nextInt();

    // Update the database
    databaseConnection   c=new databaseConnection();
            c.updateBookDetailsInDatabase(bookIdToUpdate, newPrice, newAvailableCopies);

    System.out.println("Book details updated successfully!");
}

// Example method to display all books





//delete from table 

private void deleteBook() {
    displayAllBooks(); // Display all books

    // Get book_id from user using JOptionPane
    String bookIdToDeleteInput = JOptionPane.showInputDialog("Enter the book_id of the book you want to delete:");

    // Validate if the input is not empty and is a valid integer
    if (bookIdToDeleteInput != null && !bookIdToDeleteInput.isEmpty()) {
        try {
            int bookIdToDelete = Integer.parseInt(bookIdToDeleteInput);

            // Check if the book exists before deletion
            if (bookExists(bookIdToDelete)) {
                // Delete the book from the database
                databaseConnection aa = new databaseConnection();
                aa.deleteBookFromDatabase(bookIdToDelete);

                JOptionPane.showMessageDialog(null, "Book deleted successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "Book not found. Please enter a valid book_id.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid book_id (numeric value).", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } else {
        JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid book_id.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}


// Inside the DatabaseConnection class
public boolean bookExists(int bookId) {
    // Implement your logic to check if the book with the given book_id exists in the database
    // Return true if the book exists, otherwise return false
    try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/lib_project", "root", "") ;
         PreparedStatement preparedStatement = connection.prepareStatement("SELECT 1 FROM books WHERE book_id = ?")) {

        preparedStatement.setInt(1, bookId);
        ResultSet resultSet = preparedStatement.executeQuery();

        return resultSet.next(); // Returns true if the book exists, false otherwise

    } catch (SQLException e) {
        e.printStackTrace();
        return false; // Handle the exception and return false in case of an error
    }
}






private void displayAllBooks() {
    // Assuming you have a database connection
    try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/lib_project", "root", "");
         Statement statement = connection.createStatement();
         ResultSet resultSet = statement.executeQuery("SELECT * FROM books")) {

        System.out.println("All Books:");
        while (resultSet.next()) {
            int bookId = resultSet.getInt("book_id");
            String title = resultSet.getString("title_book");
            String authorName = resultSet.getString("author_name");
            String category = resultSet.getString("category");
            int availableCopies = resultSet.getInt("number_of_available_copies");
            double price = resultSet.getDouble("price");

            System.out.println("Book ID: " + bookId + ", Title: " + title +
                    ", Author: " + authorName + ", Category: " + category +
                    ", Available Copies: " + availableCopies + ", Price: " + price);
        }
    } catch (SQLException e) {
        e.printStackTrace();
        // Handle the exception as needed
    }
}




// Example method to display all books
 
    private void sortBooksByPrice() {
        List<book> booksList = getBooksListFromDatabase();

        if (booksList.isEmpty()) {
            System.out.println("No books data available for sorting.");
        } else {
            mergeSort(booksList, 0, booksList.size() - 1);

            // Display the sorted books data
            System.out.printf("%-10s%-30s%-30s%-20s%-30s%-10s\n",
                    "Book ID", "Title", "Author Name", "Category", "Number of Available Copies", "Price");
            System.out.println("----------------------------------------------------------------------------------------------------------------------");

            for (book book : booksList) {
                System.out.printf("%-10s%-30s%-30s%-20s%-30s%-10s\n",
                        book.getid(), book.getTitle(), book.getAuthorName(), book.getCategory(),
                        book.getNumberOfAvailableCopies(), book.getPrice());
            }
        }
    }

    
    
    
     public List<book> getBooksListFromDatabase() {
        List<book> booksList = new ArrayList<>();

    try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/lib_project", "root", "");
            
            Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM books")) {

            while (resultSet.next()) {
                int bookId = resultSet.getInt("book_id");
                String title = resultSet.getString("title_book");
                String authorName = resultSet.getString("author_name");
                String category = resultSet.getString("category");
                int numberOfAvailableCopies = resultSet.getInt("number_of_available_copies");
                double price = resultSet.getDouble("price");

                book newBook = new book(bookId, title, authorName, category, numberOfAvailableCopies, price);
                booksList.add(newBook);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return booksList;
    }

    

    // Merge Sort algorithm for sorting books by price
    private void mergeSort(List<book> booksList, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSort(booksList, left, mid);
            mergeSort(booksList, mid + 1, right);
            
            mege_sort_books_price dd=new mege_sort_books_price();
            dd.merge(booksList, left, mid, right);
        }
    }

  
    
    
    
    
    
    
    
    
    

    private void searchBooksByName() {
        List<book> booksList = getBooksListFromDatabase();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the book title to search:");
        String searchTitle = scanner.nextLine();

        List<book> searchResults = new ArrayList<>();

        for (book book : booksList) {
            if (book.getTitle().equalsIgnoreCase(searchTitle)) {
                searchResults.add(book);
            }
        }

        if (searchResults.isEmpty()) {
            System.out.println("No matching books found.");
        } else {
            System.out.println("Matching books:");
            displayBooks(searchResults);
        }
    }
  private void displayBooks(List<book> booksList) {
        for (book book : booksList) {
            System.out.println(book);
        }}









    
    private void viewLoans() {

 displayBookBorrowedData();

    }

    private void viewClientInformation() {
        
            SortMembersbyName membersbyName = new SortMembersbyName();
        List<user> sortedUsers = membersbyName.getSortedUsersByUsername();

        // Display the sorted users information
        displaySortedUsers(sortedUsers);




    }
    
    private static void displaySortedUsers(List<user> users) {
                System.out.println(" Members information and sort by name");

    System.out.printf("%-8s %-20s %-30s %-20s %-15s%n", "Loan ID", "Username", "Email", "Password", "Mobile");
    System.out.println("----------------------------------------------------------------------------------------------");

    for (user user : users) {
        System.out.printf("%-8d %-20s %-30s %-20s %-15s%n",
                user.getLoanId(), user.getUsername(), user.getEmail(), user.getPassword(), user.getMobile());
    }
}


public void sortBooksByTitle() {
    try (Connection connection2 = DriverManager.getConnection("jdbc:mysql://localhost:3306/lib_project", "root","" )) {
        // Retrieve books from the database and sort by title
        String selectQuery = "SELECT * FROM books ORDER BY title";

        try (PreparedStatement preparedStatement = connection2.prepareStatement(selectQuery);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            List<book> books = new ArrayList<>();

            // Populate books list
            System.out.printf("%-5s %-20s %-20s %-15s %-10s %-10s %-15s %-15s %-10s%n",
                    "ID", "Title", "Author", "ISBN", "Copies", "Available", "Publication Year", "Borrowable", "Price");

            while (resultSet.next()) {
                int id = resultSet.getInt("book_id");
                String title = resultSet.getString("title");
                String authorName = resultSet.getString("author_name");
                String isbn = resultSet.getString("ISBN");
                int copies = resultSet.getInt("number_of_copies");
                int availableCopies = resultSet.getInt("number_of_available_copies");
                Date publicationYear = resultSet.getDate("publication_year");
                String isBorrowable = resultSet.getString("is_borrowable");
                double price = resultSet.getDouble("price");

                System.out.printf("%-5d %-20s %-20s %-15s %-10d %-10d %-15s %-15s %-10.2f%n",
                        id, title, authorName, isbn, copies, availableCopies, publicationYear, isBorrowable, price);
            }

            // No need to explicitly close resources within the try-with-resources block

        } // The Connection 'connection2' will be closed here automatically
    } catch (SQLException e) {
        e.printStackTrace();
        // Handle the exception appropriately
    }
}

      







 


    private void searchMember() {
        // Implementation for searching a member
    }

   

      private void addBook() {
        System.out.println("Enter title:");
        String title = scanner.next();

        System.out.println("Enter author name:");
        String authorName = scanner.next();

        System.out.println("Enter category:");
        String category = scanner.next();

         

        System.out.println("Enter number of available copies:");
        int numberOfAvailableCopies = scanner.nextInt();

         

        // Parse the input string to a Date object
        

        // Parse the input string as a Date
 

       
        System.out.println("Enter price:");
        double price = scanner.nextDouble();

        // Create a new book object
        book newBook = new book(title, authorName, category,  numberOfAvailableCopies,
                 price);

     
       databaseConnection c=new databaseConnection();
        c.Insert_into_books_table(newBook);

        System.out.println("Book added successfully!");
    }
      
        
          
          
          private static Date parseDateString(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
          
          
              private static void displayBooks() {
        // Logic to display books from the database
        // Use a SQL SELECT query to retrieve books from the database and print them
        try {
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            Statement statement = connection.createStatement();
            String selectQuery = "SELECT * FROM books";
            ResultSet resultSet = statement.executeQuery(selectQuery);

            System.out.println("Existing Books:");
            while (resultSet.next()) {
                // Extract book details from the ResultSet and print them
              String title = resultSet.getString("title");
    String authorName = resultSet.getString("author_name");
    String category = resultSet.getString("category");
    String ISBN = resultSet.getString("ISBN");
    int numberOfCopies = resultSet.getInt("number_of_copies");
    int numberOfAvailableCopies = resultSet.getInt("number_of_available_copies");
    Date publicationYear = resultSet.getDate("publication_year");
    String isBorrowable = resultSet.getString("is_borrowable");
    double price = resultSet.getDouble("price");

    System.out.println("Title: " + title + ", Author: " + authorName + ", ISBN: " + ISBN +
            ", Category: " + category + ", Number of Copies: " + numberOfCopies +
            ", Number of Available Copies: " + numberOfAvailableCopies +
            ", Publication Year: " + publicationYear + ", Borrowable: " + isBorrowable +
            ", Price: " + price);

            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }
    }

          
       
          
          
          
          
          
          
          
          
               public void displayBookBorrowedData() {
    try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/lib_project", "root", "")) {
        String selectQuery = "SELECT * FROM book_you_are_borrowed";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(selectQuery)) {
         System.out.printf("%-8s %-20s %-30s %-20s %-15s %-10s %-30s %-30s %-20s   %-10s %-15s %-15s %-10s%n",
        "Loan ID", "Username", "Email", "Password", "Mobile",
        "Book ID", "Title", "Author", "Category",
        "Price", "Date Borrowed", "Date Returned", "Days Borrowed");

            System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

            while (resultSet.next()) {
                int loanId = resultSet.getInt("id");
                String username = resultSet.getString("username");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                String mobile = resultSet.getString("mobile");
                int bookId = resultSet.getInt("book_id");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                String category = resultSet.getString("category");
                 double price = resultSet.getDouble("price");
                Date dateBorrowed = resultSet.getDate("date_borrowed");
                Date dateReturned = resultSet.getDate("date_returned");
                long daysBorrowed = resultSet.getLong("days_borrowed");

System.out.printf("%-8s %-20s %-30s %-20s %-15s %-10s %-30s %-30s %-20s  %-10s %-15s %-15s %-10s%n",
                        loanId, username, email, password, mobile, bookId, title, author,
                        category, price, dateBorrowed, dateReturned, daysBorrowed);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        // Handle the exception appropriately
    }
}

          
          
         



}
