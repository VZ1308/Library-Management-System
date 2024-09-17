###Library Management System###
##Overview
This project is a Library Management System that helps libraries manage their operations, such as tracking books, users, employees, and borrowing transactions. It provides both command-line and (optional) graphical user interface (GUI) modes to allow users to interact with the system in different ways.

##The system supports the following core features:

#Add/View Employees: Manage employee records such as name, contact details, and job position.
#Add/View Users: Manage users who borrow books from the library.
#Borrow and Return Books: Handle borrowing and returning of books, including fee calculation based on the borrowing period.
#Books Management: View available books, add new books, and track borrowed books.
#Late Fee Calculation: Automatically calculate fees based on the duration a book is borrowed (5€ per day).

##Features
#Employee and User Management: Store and view detailed information about employees and users.
#Books Database: Manage books in the library (book details, available status).
#Borrow/Return System: Track when users borrow and return books, with fee calculation for overdue returns.
#Payment Options: Support for Visa, cash, and credit card payments upon book return.
#Command Line Interface (CLI): Text-based menu system for interacting with the library functions.
#Database-Driven: Utilizes a relational database to store all data related to users, employees, books, and transactions.

##Prerequisites
Before running the project, ensure you have the following installed on your system:

#Java Development Kit (JDK) (version 8 or higher)
#MySQL Server
#Maven (for dependency management)
