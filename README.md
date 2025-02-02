# 🚀 SWIFT Code API – Full Stack Application (Spring Boot & React)

## 📒 Table of Contents
* [General info](#-general-info)
* [Features](#-features)
* [Technologies](#-technologies)
* [Setup](#-setup)
* [Running Tests](#-running-tests)
* [API Endpoints](#-api-endpoints)
* [Frontend](#-frontend)


<br>


## 📦 General Info
**Swift API** is a lightweight and efficient RESTful backend built using **Spring Boot**.

The application parses SWIFT codes from a provided file, stores them in a database, and exposes four endpoints for retrieving and manipulating the data.

<br>


## ✨ Features  

### Backend  


- ✅ **SWIFT Code Parsing**: The application parses SWIFT codes from a provided file and organizes them by headquarters and branches.

- ✅ **Database Storage**: Data is stored in a fast, low-latency database (choose appropriate DB: relational/non-relational) for efficient retrieval.

- ✅ **REST API**: Four key endpoints expose the SWIFT code data, allowing for querying, adding, and deleting entries.

- ✅ **Error Handling**: The API gracefully handles edge cases with clear, informative error messages.

- ✅ **Containerized Application**: The application and database are containerized for easy setup and deployment.

### Frontend  
- ✅ **Interactive Web UI** – Built with **React**, providing an easy way to manage SWIFT codes.
  
- ✅ **User-Friendly Forms** – Add or delete SWIFT codes via simple forms.
  
- ✅ **Search Functionality** – Find SWIFT codes and banks by code or country.
  
- ✅ **Live Error Handling** – Displays error messages (e.g., invalid SWIFT codes).  



<br>

## 🛠 Technologies  
### Backend  
- **Programming Language**: Java  
- **Framework**: Spring Boot  
- **ORM**: Hibernate with JPA  
- **Database**: H2 (in-memory, for testing purposes)  
- **Testing**: JUnit, Mockito
  
### Frontend  
- **React**  
- **Axios** (for API requests)  
- **Bootstrap** (for styling)  

  
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

### 5️⃣ Run the Frontend

Navigate to the swift-api/swift-api-frontend directory:
```sh
cd swift-api-frontend
npm install
npm start
```
Now the web application is available at http://localhost:3000.
![image](https://github.com/user-attachments/assets/9a507f37-b92a-4a94-872e-44ce02fef7d9)

<br>

## 🧪 Running Tests
Navigate to the swift-api directory, then:
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

<br>

## 🍓 Frontend 

The web UI consists of four main sections:
### 1️⃣ Add SWIFT Code – Form to enter a new SWIFT code, including bank name, country, and headquarters status.
### 2️⃣ Delete SWIFT Code – Remove a SWIFT code by providing the bank name and country code.
### 3️⃣ Find SWIFT Code – Search for a bank by its SWIFT code and retrieve details.
### 4️⃣ Find Banks by Country – Search for all banks within a specific country.

