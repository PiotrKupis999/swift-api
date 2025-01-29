# 🚀 Swift API – RESTful Backend in Spring Boot  

## 📒 Table of Contents
* [General info](#-general-info)
* [Features](#-features)
* [Technologies](#-technologies)
* [Setup](#🧰setup)
* [Running Tests](#-running-tests)
* [API Endpoints](#-api-endpoints)

<br>


## 📦 General Info
**Swift API** is a lightweight and efficient RESTful backend built using **Spring Boot**.

The application parses SWIFT codes from a provided file, stores them in a database, and exposes four endpoints for retrieving and manipulating the data.

<br>


## ✨ Features  
- ✅ **SWIFT Code Parsing**: The application parses SWIFT codes from a provided file and organizes them by headquarters and branches.

- ✅ **Database Storage**: Data is stored in a fast, low-latency database (choose appropriate DB: relational/non-relational) for efficient retrieval.

- ✅ **REST API**: Four key endpoints expose the SWIFT code data, allowing for querying, adding, and deleting entries.

- ✅ **Error Handling**: The API gracefully handles edge cases with clear, informative error messages.

- ✅ **Containerized Application**: The application and database are containerized for easy setup and deployment.



<br>

## 🛠 Technologies  
- **Programming Language**: Java  
- **Framework**: Spring Boot  
- **ORM**: Hibernate with JPA  
- **Database**: H2 (in-memory, for testing purposes)  
- **Testing**: JUnit, Mockito
  
<br>

## 🧰 Setup 
### 1️⃣ Clone the Repository  
```sh
git clone https://github.com/PiotrKupis999/swift-api
cd swift-api
```
### 2️⃣ Build the Project
Make sure you have Java 21 installed, then run:

```sh
mvn clean install
```
### 3️⃣ Run the Application
```sh
mvn spring-boot:run
```
Now the API is available at http://localhost:8080

### 4️⃣ Log to H2 database ***(optional)***

The database is now available at http://localhost:8080/h2-console
| Login           |                        |
|-----------------|------------------------|
| Saved Settings: | Generic H2 (Embedded)  |         
| Setting Name:	  | Generic H2 (Embedded)  |         
| Driver Class:	  | org.h2.Driver          |      
| JDBC URL:   	  | jdbc:h2:mem:swiftdb    |      
| User Name:	    | user                   |
| Password:	      |                        |

### 5️⃣ Upload *the spreadsheet* ***(optional)***

http://localhost:8080/v1/swift-codes/import/import-database

<br>

## 🧪 Running Tests
```sh
mvn test
```

<br>

## 📡 API Endpoints  
### 🔹 Task Endpoints  
| Method | Endpoint                                    | Description                                              |
|--------|---------------------------------------------|----------------------------------------------------------|
| GET    | `/v1/swift-codes/{swift-code}`              | Get details of a single SWIFT code                       |
| GET    | `/v1/swift-codes/country/{countryISO2code}` | Get all SWIFT codes with details for a specific country  |
| POST   | `/v1/swift-codes/`                          | Add new SWIFT code entries                               |
| DELETE | `/v1/swift-codes/{swift-code}`              | Delete swift-code data                                   |

### 🔹 Additional Endpoints  
| Method | Endpoint                                 | Description                                              |
|--------|------------------------------------------|----------------------------------------------------------|
| GET    | `/v1/swift-codes/import/import-database` | Add all SWIFT codes from well-known /spreadsheet/        |
| POST   | `/v1/swift-codes/import/import-csv-from` | Add all SWIFT codes from local CSV file (specified path) |



