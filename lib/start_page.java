package com.lib;

import javax.swing.JOptionPane;

public class start_page {
public static void main(String[] args) {
    JOptionPane.showMessageDialog(null, "Welcome to the Library System!");

    while (true) {
        String[] options = {"library Interface", "user (member) Interface", "Exit"};
        int choice = JOptionPane.showOptionDialog(null, "Choose an option:", "Library System",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

        if (choice == 0) {
            runLibrarianInterface();
        } else if (choice == 1) {
            runMemberInterface();
        } else if (choice == 2) {
            JOptionPane.showMessageDialog(null, "Exiting the Library System. Goodbye!");
            System.exit(0);
        } else {
            JOptionPane.showMessageDialog(null, "Invalid choice. Please enter a valid option.");
        }
    }
}


    private static void runLibrarianInterface() {
        String username = JOptionPane.showInputDialog("Enter username:");
        String password = JOptionPane.showInputDialog("Enter password:");

        // Check if the provided username and password match the expected values
        if ("admin".equals(username) && "0000".equals(password)) {
            // Credentials are correct, proceed to librarian interface
            librarian librarianUser = new librarian();
            librarianUser.runLibrarianInterface();
        } else {
            JOptionPane.showMessageDialog(null, "Invalid username or password. Access denied.");
        }
    }

    private static void runMemberInterface() {
        member memberUser = new member();
        memberUser.runMemberInterface();

        // Add logic for the member interface
    }
}
