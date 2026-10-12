package com.lib;
import com.sun.istack.internal.NotNull;
import java.util.ArrayList;
import java.util.Objects;

public class user {

    private int loanId;
    private String username;
    private String email;
    private String password;
    private String mobile;

    // Constructor
    public user(String username, String email, String password, String mobile) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.mobile = mobile;
    }

    public user(int loanId, String username, String email, String password, String mobile) {
            this.loanId = loanId;
            this.username = username;
            this.email = email;
            this.password = password;
            this.mobile = mobile;
    }

    // Getter and Setter methods

    public int getLoanId() {
        return loanId;
    }

    public void setLoanId(int loanId) {
        this.loanId = loanId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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
}
