package com.lib;


import java.time.*;
import java.time.chrono.ChronoLocalDate;
import java.util.Date;
import java.util.Objects;

public class book {
      private String title, authorName, category, ISBN;
    private int id;
    private int numberOfAvailableCopies;
    private Date publicationYear;
    private String isBorrowable;
    private double price;
    private LocalDate dueDate;


    public book(String title, String authorName, String category,   int numberOfAvailableCopies,
                 double price) {
        this.title = title;
        this.authorName = authorName;
        this.category = category;
         
         this.numberOfAvailableCopies = numberOfAvailableCopies;
         this.price = price;
     }

    book(String title) {
                this.title = title;

     }
    
  public   book(String title, String authorName, String category, String ISBN,double price) {
  this.title = title;
        this.authorName = authorName;
        this.category = category;
        this.ISBN = ISBN;
        
         this.price = price;
     }

    book(int bookId, String title, String authorName, String category, int numberOfAvailableCopies, double price) {
this.id = id;
 this.title = title;
        this.authorName = authorName;
        this.category = category;
         
         this.numberOfAvailableCopies = numberOfAvailableCopies;
         this.price = price;
    }

       
public Date getPublicationYear() {
        return publicationYear;
    }

    // Setter for publicationYear
    public void setPublicationYear(Date publicationYear) {
        this.publicationYear = publicationYear;
    }

    public int getid() {
        return id;
    }

    public int getNumberOfAvailableCopies() {
        return numberOfAvailableCopies;
    }

    public void setNumberOfAvailableCopies(int numberOfAvailableCopies) {
        this.numberOfAvailableCopies = numberOfAvailableCopies;
    }

   

   

public String  getIsBorrowable() {
        return isBorrowable;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthorName() {
        return authorName;
    }
  public void setid(int id) {
        this.id = id;
    }
  
  
    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getISBN() {
        return  ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String  isBorrowable() {
        return isBorrowable;
    }

    public void setBorrowable(String borrowable) {
        isBorrowable = borrowable;
    }
    
    
     
    public double getPrice() {
        return price;
    }

  @Override
  public String toString() {
      
    return "\ntitle: " + title +
            "\nauthor Name: " + authorName+
            "\ncategory: " + category +
             "\nnumber Of Available Copies: " + numberOfAvailableCopies +
             "\nprice: " + price  
            ;
  }

  public String DisplayBook() {
        return "\ntitle: " + title +
                "\nauthor Name: " + authorName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }



  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    book book = (book) o;
    return numberOfAvailableCopies == book.numberOfAvailableCopies && publicationYear == book.publicationYear && isBorrowable == book.isBorrowable && Double.compare(book.price, price) == 0 && Objects.equals(title, book.title) && Objects.equals(authorName, book.authorName) && Objects.equals(category, book.category) && Objects.equals(ISBN, book.ISBN);
  }

   
  
    void setPrice(double price) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}