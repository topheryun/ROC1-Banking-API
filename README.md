# ROC1 Banking API

## Description

The Banking API will manage the bank accounts of its users. It will be managed by the Bank's employees and admins. Employees and Admins count as Standard users with additional abilities. All users must be able to update their personal information, such as username, password, first and last names, as well as email. Accounts owned by users must support withdrawal, deposit, and transfer. Transfer of funds should be allowed between accounts owned by the same user, as well as between accounts owned by different users. Standard users should be able to register and login to see their account information. They can have either Checking or Savings accounts. Employees can view all customer information, but not modify in any way. Admins can both view all user information, as well as directly modify it.

## Features

* User
  * Login
  * Registration
* Customer
  * Create checking account
  * View balance of all accounts
  * Withdraw / deposit
  * Transfer money
  * Recieve transfer
* Employee
  * Approve / reject account
  * View customer account
  * View customer transaction log
* System rejects invalid transactions

## Technologies Used

* Java - version 1.8.0_271
* PostgreSQL - version 42.2.5
* Maven - version 2.22.1
* JUnit - version 5.4.2
* Log4j - version 1.2.17

## Getting Started

1. Copy the repository https url by pressing the green clone button, or
  copy the url and add .git to the end.
  
2. Clone the repository by opening Git Bash at the desired location and running the clone command:

> git clone (name-of-url).git
  
3. Open IDE (Spring Tool Suite) that can run a Java Maven project and import as an existing Maven project.

4. Run the Banking app as a Java Application.

## Usage

You begin in the User Dashboard where you may log in as a customer or an employee. 
A customer will have options to view their accounts, create a new account, deposit, withdraw, transfer money, or receive a transfer.
Employees can view all customer accounts and their transaction history as well as approve or reject pending accounts.
