# Book Library System RESTful API

This is a RESTful API for a book library system that allows users to perform CRUD (Create, Read, Update, Delete) operations on books. It has been built using Java and the Spring Boot framework, and uses a MySQL database to store book information. The API has been deployed to a cloud platform and is secured using JSON Web Tokens (JWT).
#### **Check out the project at the following link**:
<http://library-202303.ey.r.appspot.com/>

## Functional Requirements
The following are the functional requirements of the system:

FR1: A user should be able to add a new book to the library by providing the book title, author, ISBN, and publication date.

FR2: A user should be able to retrieve a list of all books in the library.

FR3: A user should be able to retrieve the details of a specific book by providing the book's ISBN.

FR4: A user should be able to update the details of a specific book by providing the book's ISBN.

FR5: A user should be able to delete a specific book by providing the ISBN.

FR6: The API should handle all errors and provide appropriate error messages.

## Technical Requirements

The following are the technical requirements of the system:

TR1: The API should be built using Java and the Spring Boot framework.

TR2: The API should use a MySQL database to store book information.

TR3: The API should be deployed to a cloud platform (e.g., AWS or Google Cloud).

TR4: The API should be secured using JSON Web Tokens (JWT).

TR5: The API should include a comprehensive set of unit tests.

## Installation and Setup
1. Clone the repository:
```shell
git clone https://github.com/Dan-Devai/book-library-system-api.git

```
2. Navigate to the project directory:
```shell
cd book-library-system-api
```

3. Configure the MySQL database by setting the connection details in the application.properties file:
```yaml
spring.datasource.url=jdbc:mysql://localhost:3306/book_library_system
spring.datasource.username=root
spring.datasource.password=password
```
4. Build and run the application:
```shell
mvn clean install
java -jar target/book-library-system-api.jar

```


### API Endpoints
## __SUBJECT TO CHANGE*__

The following are the API endpoints:
### POST /api/books

Add a new book to the library by providing the book title, author, ISBN, and publication date.

Example Request Body:

```json
{
  "title": "The Alchemist",
  "author": "Paulo Coelho",
  "isbn": "9780061122415",
  "publicationDate": "1988-04-25"
}
```

### GET /api/books
Retrieve a list of all books in the library.

Example Response Body:

```json
[
  {
    "title": "The Alchemist",
    "author": "Paulo Coelho",
    "isbn": "9780061122415",
    "publicationDate": "1988-04-25"
  },
  {
    "title": "The Da Vinci Code",
    "author": "Dan Brown",
    "isbn": "9780307474278",
    "publicationDate": "2003-03-18"
  }
]

```

### GET /api/books/{isbn}
Retrieve the details of a specific book by providing the book's ISBN.

Example Response Body:
```json
{
  "title": "The Alchemist",
  "author": "Paulo Coelho",
  "isbn": "9780061122415",
  "publicationDate": "1988-04-25"
}

```

### PUT /api/books/{isbn}
Update the details of a specific book by providing the book's ISBN.

Example Request Body:

```json
{
  "title": "The Alchemist",
  "author": "Paulo Coelho",
  "isbn": "9780061122415",
  "publicationDate": "1988-04-25"
}
```
### DELETE /api/books/{isbn}
Delete a specific book by providing the ISBN.

Example Response Body:

```json
{
  "message": "The book with ISBN 9780061122415 has been successfully deleted."
}
```
The API should handle all errors and provide appropriate error messages. Example error responses:

#### 400 Bad Request

```json
{
  "timestamp": "2023-03-13T08:54:24.104+00:00",
  "status": 400,
  "error": "Bad Request",
  "message": "The ISBN must be 13 digits.",
  "path": "/api/books"
}
```
#### 404 NOT FOUND

```json
{
  "timestamp": "2023-03-13T08:54:24.104+00:00",
  "status": 404,
  "error": "Not Found",
  "message": "The book with ISBN 9780061122414 was not found.",
  "path": "/api/books/9780061122414"
}
```

## Security
The API should be secured using JSON Web Tokens (JWT).


## Contributing
Contributions are what make the open-source community such an amazing place to learn, inspire, and create. Any contributions you make are greatly appreciated.

1. Fork the Project
2. Create your Feature Branch (git checkout -b feature/AmazingFeature)
3. Commit your Changes (git commit -m 'Add some AmazingFeature')
4. Push to the Branch (git push origin feature/AmazingFeature)
5. Open a Pull Request

## License
Distributed under the MIT License. See LICENSE for more information.





