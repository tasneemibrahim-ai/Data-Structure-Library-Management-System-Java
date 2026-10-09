<div align="center">

# 📚 Library Management System
### A Java Desktop Application with Custom Data Structures

![Java](https://img.shields.io/badge/Java-JDK_8+-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![Swing](https://img.shields.io/badge/Java_Swing-GUI-007396?style=for-the-badge&logo=java&logoColor=white)
![NetBeans](https://img.shields.io/badge/NetBeans_IDE-1B6AC6?style=for-the-badge&logo=apache-netbeans-ide&logoColor=white)
![XAMPP](https://img.shields.io/badge/XAMPP-FB7A24?style=for-the-badge&logo=xampp&logoColor=white)

*A full-featured library management desktop app built as a Data Structures course project — demonstrating custom linked lists, merge sort variants, and real-world CRUD operations with a MySQL backend.*

</div>

---

## 📋 Table of Contents

- [Overview](#-overview)
- [Tech Stack](#️-tech-stack)
- [Features](#-features)
- [Data Structures & Algorithms](#-data-structures--algorithms)
- [Project Structure](#️-project-structure)
- [Database Schema](#️-database-schema)
- [Setup & Installation](#️-setup--installation)
- [Default Credentials](#-default-credentials)
- [Demo](#-demo)
- [Future Improvements](#-future-improvements)
- [License](#-license)

---

## 🔍 Overview

This **Library Management System** is a Java Swing desktop application connected to a **MySQL** database hosted via **XAMPP**. It was developed as a hands-on Data Structures project to apply core concepts — custom linked lists, merge sort on different data types, and the slow/fast pointer technique — inside a fully functional GUI application.

The system supports two user roles:
- **Admin (Librarian)** — full CRUD over books, member management, fine tracking
- **Member** — browse catalog, borrow/return books, view their own borrowing history

---

## 🖥️ Tech Stack

| Layer | Technology |
|---|---|
| Language | Java (JDK 8+) |
| GUI Framework | Java Swing (NetBeans Form Editor) |
| Database | MySQL via XAMPP / phpMyAdmin |
| Build Tool | Apache Ant (`build.xml`) |
| IDE | NetBeans |
| DB Connector | JDBC (MySQL Connector/J) |

---

## ✨ Features

### 🔐 Authentication
- Librarian login with admin credentials
- Role-based navigation: **Admin** menu vs **Member** menu

### 📖 Book Management *(Admin)*
- Add new books (title, author, category, copies, price)
- Delete books by ID
- View the complete book catalog
- Search books by title, author, or category

### 👤 Member Management
- Borrow books with **automatic copy-count tracking**
- Return books with **due-date and fine calculation**
- View all active borrowings per member
- Sort members by name using **Merge Sort on a Linked List**

### 📊 Sorting & Search

| Operation | Algorithm | Data Structure |
|---|---|---|
| Sort books by title | Merge Sort | Custom `linkedList<book>` |
| Sort books by ISBN | `mergeSortISBN` variant | Linked List |
| Sort books by price | Merge Sort | `ArrayList<book>` |
| Sort members by name | Merge Sort | Linked List of users |
| Search books | Linear scan | Linked List |

### 💰 Fine Management
- Automatically calculates overdue fines based on return date
- Dedicated **"View Fine"** screen for librarians

---

## 🧠 Data Structures & Algorithms

| Feature | Data Structure / Algorithm |
|---|---|
| Book catalog (in-memory) | Custom **Singly Linked List** (`linkedList.java`) |
| Linked list node | Generic `Node<T>` (`Node.java`) |
| Sort books by title | **Merge Sort** on Linked List nodes |
| Sort books by ISBN | **Merge Sort** variant (`mergeSortISBN`) |
| Sort books by price | **Merge Sort** on `ArrayList<book>` |
| Sort members by name | **Merge Sort** on Linked List of users |
| Middle-node detection | **Slow/Fast Pointer** technique (`getMiddle`) |

> All sorting implementations are custom-written — no `Collections.sort()` shortcut — to demonstrate core algorithm understanding.

---

## 🗂️ Project Structure

```
src/com/lib/
│
├── Main.java                     # Entry point
├── start_page.java               # Welcome / start screen
├── login.java / .form            # Librarian login screen
│
├── menu_for_admin.java / .form   # Admin dashboard
├── menu_for_member.java / .form  # Member dashboard
│
├── add_book.java / .form         # Add book UI
├── delete_book_with_id.java      # Delete book UI
├── view_list_books.java          # View all books (admin)
├── view_list_books_member.java   # View all books (member)
├── search_in_books.java          # Book search UI
│
├── borrow_book.java / .form      # Borrow book UI
├── return_book.java / .form      # Return book UI
├── view_fine_if_found.java       # Fine calculation UI
├── view_list_of_borrowers.java   # All active borrowers
│
├── sort_books_by_price.java      # Sort UI (by price)
├── sort_member_by_name.java      # Sort UI (members by name)
│
├── book.java                     # Book entity / model
├── user.java                     # User entity / model
├── loan.java                     # Loan entity / model
├── catalog.java                  # Book catalog logic
├── request.java                  # Borrow request model
│
├── linkedList.java               # ⭐ Custom generic Linked List
├── Node.java                     # Linked List Node
├── mege_sort_books_price.java    # Merge Sort (by price)
├── SortMembersbyName.java        # Merge Sort (by member name)
├── search.java                   # Search utility
│
├── databaseConnection.java       # JDBC MySQL connection helper
├── librarian.java                # Librarian-specific operations
└── member.java                   # Member-specific operations
```

---

## 🗄️ Database Schema

**Database name:** `lib_project`  
Hosted locally via **XAMPP / phpMyAdmin**.

### `books`

| Column | Type | Description |
|---|---|---|
| `book_id` | INT (PK, AUTO) | Unique book identifier |
| `title_book` | VARCHAR(20) | Book title |
| `author_name` | VARCHAR(255) | Author |
| `category` | VARCHAR(50) | Book category |
| `number_of_available_copies` | INT | Stock count |
| `price` | DECIMAL(10,2) | Book price |

### `book_you_are_borrowed`

| Column | Type | Description |
|---|---|---|
| `id` | INT (PK, AUTO) | Loan record ID |
| `username` | VARCHAR(255) | Borrower username |
| `book_id` | INT | Borrowed book ID |
| `title` | VARCHAR(255) | Book title |
| `date_borrowed` | DATE | Borrow date |
| `date_returned` | DATE | Expected return date |
| `days_borrowed` | INT | Duration (days) |

### `return_book`

| Column | Type | Description |
|---|---|---|
| `id` | INT (PK, AUTO) | Return record ID |
| `loan_id` | INT | Reference to loan record |
| `book_id` | INT | Returned book ID |
| `date_borrowed` | DATE | Original borrow date |
| `date_returned` | DATE | Actual return date |

---

## ⚙️ Setup & Installation

### Prerequisites

- ✅ Java JDK 8 or higher
- ✅ [XAMPP](https://www.apachefriends.org/) (Apache + MySQL)
- ✅ [NetBeans IDE](https://netbeans.apache.org/) (recommended) or any Java IDE
- ✅ MySQL Connector/J JAR — already included in `lib/`

---

### Step 1 — Start XAMPP

Launch the **XAMPP Control Panel** and start both **Apache** and **MySQL**.

### Step 2 — Import the Database

1. Open `http://localhost/phpmyadmin` in your browser
2. Create a new database named **`lib_project`**
3. Select the database → click **Import**
4. Upload the file: `lib_project.sql`

> 💡 A video walkthrough is included: `how import database file.mp4`

### Step 3 — Open the Project

In **NetBeans IDE**:
```
File → Open Project → select this project folder
```

### Step 4 — Verify DB Connection

Open `src/com/lib/databaseConnection.java` and confirm the settings match your XAMPP setup:

```java
String url      = "jdbc:mysql://localhost:3306/lib_project";
String user     = "root";
String password = "";   // XAMPP default: no password
```

### Step 5 — Run

Right-click the project → **Run** (or press `F6`).

The app starts at the **Start Page** → navigate to **Login**.

---

## 🔑 Default Credentials

| Role | Username | Password |
|---|---|---|
| Admin (Librarian) | `admin` | `1234` |

---

## 🎬 Demo

> A full walkthrough video is included in the repository: **`running video.mp4`**

---

## 🚀 Future Improvements

- [ ] Replace hardcoded credentials with a hashed, DB-backed login system
- [ ] Add member self-registration
- [ ] Implement **Binary Search** for O(log n) book lookups
- [ ] Export reports to PDF / CSV
- [ ] Add email notifications for overdue books
- [ ] Migrate UI from Swing to **JavaFX** for a modern look
- [ ] Add pagination for large book catalogs

---

## 📄 License

This project was developed as an academic **Data Structures** assignment.  
Feel free to use, fork, and build upon it — attribution appreciated. 🙏

---

<div align="center">

**Author:** Tasneem &nbsp;|&nbsp; **Course:** Data Structures &nbsp;|&nbsp; **Year:** 2023

</div>
