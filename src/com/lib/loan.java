
package com.lib;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import java.time.LocalDate;
import java.util.Date;

public class loan extends user {
    private int bookId;
    private String title;
    private String author;
    private String category;
    private String ISBN;
    private double price;
    private LocalDate dateBorrowed;        private int loanId;
         private String username;
        private String email;
        private String password;
        private String mobile;

    private LocalDate dateReturned;
    
    
    private Date dateBorrowed1;
    private Date dateReturned2;
    
    
    
    private long daysBorrowed;

    // Constructor
    public loan(String username, String email, String password, String mobile,
                int bookId, String title, String author, String category,  double price,
                LocalDate dateBorrowed, LocalDate dateReturned, long daysBorrowed) {
        super(username, email, password, mobile);
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.category = category;
         this.price = price;
        this.dateBorrowed = dateBorrowed;
        this.dateReturned = dateReturned;
        this.daysBorrowed = daysBorrowed;
    }
    public loan(int loanId,String username, String email, String password, String mobile,
                int bookId, String title, String author, String category, String ISBN, double price,
                Date dateBorrowed1, Date dateReturned2, long daysBorrowed) {
        super(username, email, password, mobile);
        this.loanId = loanId;
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.category = category;
        this.ISBN = ISBN;
        this.price = price;
        this.dateBorrowed1 = dateBorrowed1;
        this.dateReturned2 = dateReturned2;
        this.daysBorrowed = daysBorrowed;
    }
    
    
    
 
 

    // Getter and Setter methods

    public int getBookId() {
        return bookId;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getCategory() {
        return category;
    }

    public String getISBN() {
        return ISBN;
    }

    public double getPrice() {
        return price;
    }

    public LocalDate getDateBorrowed() {
        return dateBorrowed;
    }

    public LocalDate getDateReturned() {
        return dateReturned;
    }
public Date getDateBorrowed2() {
        return dateBorrowed1;
    }

    public Date getDateReturned2() {
        return dateReturned2;
    }
    public long getDaysBorrowed() {
        return daysBorrowed;
    }
    
      @Override
        public String toString() {
            return "Loan{" +
                    "loanId=" + loanId +
                    ", username='" + username + '\'' +
                    ", email='" + email + '\'' +
                    ", password='" + password + '\'' +
                    ", mobile='" + mobile + '\'' +
                    '}';
        }
        
        
        
    public void displayLoanDetails() {
        System.out.println("------------------------------");
        System.out.println("Loan Details:");
        System.out.println("Loan ID: " + getLoanId());
        System.out.println("Username: " + getUsername());
        System.out.println("Email: " + getEmail());
        System.out.println("Password: " + getPassword());
        System.out.println("Mobile: " + getMobile());
        System.out.println("Book ID: " + getBookId());
        System.out.println("Title: " + getTitle());
        System.out.println("Author: " + getAuthor());
        System.out.println("Category: " + getCategory());
        System.out.println("ISBN: " + getISBN());
        System.out.println("Price: " + getPrice());
        System.out.println("Date Borrowed: " + getDateBorrowed2());
        System.out.println("Date Returned: " + getDateReturned2());
        System.out.println("Days Borrowed: " + getDaysBorrowed());
                System.out.println("------------------------------");

    }
        
        
        
}
