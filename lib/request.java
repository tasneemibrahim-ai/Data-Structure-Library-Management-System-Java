package com.lib;

import java.time.LocalDate;

public class request {
  private String username, title;
  private LocalDate date;

  public request(String username, String title) {
    this.username = username;
    this.title = title;
    date = LocalDate.now();
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  @Override
  public String toString() {
    return  "\nrequest: "
            + "\nusername: "
            + username
            + "\nbook: "
            + title
            +"\ndate: "
            + date;
  }
}
