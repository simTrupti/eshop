# 🛒 eShop --- Spring Boot E-Commerce Backend

A practical **e-commerce backend application** built with **Java 21,
Spring Boot, Spring Data JPA, MySQL, Spring Security, JWT, Apache Kafka,
and AI-assisted natural-language product search**.

The project started with core REST APIs and database persistence and has
been extended with authentication, validation, centralized exception
handling, cart/order workflows, event publishing, and an AI search
layer.

------------------------------------------------------------------------

## 🚀 Features

### Core E-Commerce

-   User management
-   Product management
-   Cart and cart-item management
-   Order and order-item management
-   MySQL database persistence
-   JPA entity relationships
-   REST APIs

### Backend Engineering

-   Layered architecture
-   Spring Data JPA repositories
-   DTO-based request/response models
-   Jakarta Bean Validation
-   Centralized exception handling
-   Optional-based entity retrieval
-   Pagination/filtering/sorting support in the product layer
-   Postman API testing

### Security

-   User registration and login
-   BCrypt password hashing
-   JWT-based authentication
-   JWT request filtering
-   Spring Security configuration

### Event-Driven Processing

-   Apache Kafka configuration
-   Order-related events
-   Kafka order producer

## 🤖 AI Tool Calling — Phase 1

-   Natural-language product search
-   AI-assisted shopping intent extraction
-   Structured search intent using `ShoppingIntent`
-   AI-to-product-service communication through `ProductClient`
-   REST communication using `RestTemplate`

### AI Tool Calling — Phase 2

Implemented Gemini function calling to allow the AI to dynamically invoke backend product APIs instead of only extracting structured search criteria.

### Flow

User Query
→ Gemini
→ Function Call
→ ProductClient
→ Product Service
→ Product Results
→ Gemini
→ Final AI Response

### How it works

1. User sends a natural-language product request through `/ai/search`.

2. `AiServiceImpl` sends the request to Gemini along with a `searchProducts` tool definition.

3. Gemini decides whether to call the tool and generates structured arguments such as:

```json
{
  "category": "Electronics",
  "maxPrice": 2000
}

------------------------------------------------------------------------

# 🏗️ Architecture

The application follows a layered backend architecture:

``` text
Client / Postman
       │
       ▼
 Controller
       │
       ▼
 Service
       │
       ▼
 Repository
       │
       ▼
 MySQL
```

Additional integrations:

``` text
Authentication:

Client
  ↓
AuthController
  ↓
AuthService
  ↓
BCrypt / JWT
  ↓
Security Filter
  ↓
Protected APIs
```

``` text
AI Product Search:

User's natural-language query
          ↓
     AiController
          ↓
       AiService
          ↓
     AI interpretation
          ↓
    ShoppingIntent
          ↓
     ProductClient
          ↓
      Product API
```

``` text
Order Events:

Order processing
      ↓
OrderPlacedEvent
      ↓
OrderProducer
      ↓
Apache Kafka
```

### AI-Powered RAG Product Search

Implemented Retrieval-Augmented Generation (RAG) for semantic product search.

- Generated 768-dimensional product embeddings using Gemini Embeddings.
- Stored product embeddings and metadata in Qdrant Vector Database.
- Implemented semantic similarity search to retrieve relevant products.
- Passed retrieved product context to Gemini to generate natural-language shopping responses.
- Integrated the complete flow into the Spring Boot AI service.

**RAG Flow:**

User Query → Embedding → Qdrant Similarity Search → Retrieved Products → Gemini → Final Response

------------------------------------------------------------------------

# 📦 Project Modules

  -----------------------------------------------------------------------
  Module                              Purpose
  ----------------------------------- -----------------------------------
  **User**                            User creation, retrieval,
                                      registration and authentication

  **Product**                         Product management and product
                                      search/filter functionality

  **Cart**                            Shopping cart and cart-item
                                      management

  **Order**                           Order creation and retrieval

  **OrderItem**                       Products and quantities associated
                                      with orders

  **Authentication**                  Registration, login, BCrypt and JWT

  **AI Search**                       Natural-language product discovery

  **Kafka**                           Order-related event publishing

  **Validation**                      Request/entity validation

  **Exception Handling**              Centralized error handling
  -----------------------------------------------------------------------

------------------------------------------------------------------------

# 🧩 Entity Relationships

The core e-commerce domain is modeled using JPA relationships.

``` text
User
 │
 ├──────────< Order
 │              │
 │              └──────────< OrderItem >────────── Product
 │
 └────────── Cart
                 │
                 └──────────< CartItem >────────── Product
```

### Main relationships

-   A **User** can have multiple orders.
-   An **Order** contains multiple order items.
-   Each **OrderItem** belongs to one order.
-   Each **OrderItem** references a product.
-   A **Cart** contains cart items.
-   Cart items reference products.

These relationships are mapped using JPA annotations such as:

``` java
@ManyToOne
@OneToMany
@JoinColumn
```

------------------------------------------------------------------------

# 👤 User & Authentication

The application includes user registration and authentication
functionality.

### Implemented concepts

-   User registration
-   Login
-   Password hashing with BCrypt
-   JWT token generation
-   JWT request filtering
-   Spring Security configuration

### Authentication flow

``` text
Registration
     ↓
Password
     ↓
BCrypt hashing
     ↓
Database
```

``` text
Login
  ↓
Credentials
  ↓
Authentication
  ↓
JWT
  ↓
Client
  ↓
JWT Filter
  ↓
Protected endpoint
```

Passwords are stored using BCrypt hashing rather than plain-text
storage.

------------------------------------------------------------------------

# 🛍️ Product Module

The Product module manages the application's product catalog.

### Current functionality

-   Create products
-   Retrieve products
-   Retrieve product by ID
-   Product filtering/search-related functionality
-   Pagination/sorting support
-   DTO-based product responses
-   Validation
-   Database persistence

The product layer also provides the data source used by the AI-powered
search flow.

------------------------------------------------------------------------

# 🛒 Cart Module

The Cart module represents the user's current shopping selection.

``` text
Cart
 │
 ├── CartItem → Product
 ├── CartItem → Product
 └── CartItem → Product
```

The cart is kept separate from the final order so that shopping activity
and completed orders remain different concepts.

------------------------------------------------------------------------

# 📦 Order & OrderItem Modules

Orders represent a user's purchase.

``` text
User
 ↓
Order
 ↓
OrderItem
 ↓
Product
```

JPA relationships connect the entities instead of manually maintaining
only foreign-key IDs.

The project also publishes order-related events through Kafka.

------------------------------------------------------------------------

# ⚡ Kafka & Event Publishing

The project contains Kafka infrastructure for order-related event
publishing.

Current event-related components include:

``` text
OrderPlacedEvent
OrderItemEvent
OrderProducer
KafkaProducerConfig
```

The current architecture is:

``` text
Order processing
      ↓
Create event
      ↓
Kafka Producer
      ↓
Kafka topic
```

This establishes a foundation for asynchronous features such as
notifications, inventory processing, analytics, and other downstream
processing.

------------------------------------------------------------------------

# 🤖 AI-Powered Natural-Language Product Search

The project includes an AI layer that allows users to search for
products using natural language instead of requiring them to construct
exact search parameters.

Example requests:

``` text
"Show me electronics under 5000"

"Find running shoes below 3000"

"I need a laptop under 80000"
```

### AI search architecture

``` text
Natural-language query
          ↓
     AiController
          ↓
       AiService
          ↓
    AI interpretation
          ↓
   ShoppingIntent
          ↓
     ProductClient
          ↓
      Product API
          ↓
    ProductResponse
```

The important architectural separation is:

> **AI understands the user's intent; the product service remains
> responsible for retrieving product data.**

This keeps the AI layer separated from direct database access.

------------------------------------------------------------------------

# 🌐 ProductClient & REST Communication

The project contains a `ProductClient` and `RestTemplateConfig`.

The AI service can communicate with the product API through HTTP:

``` text
AiService
   ↓
ProductClient
   ↓
HTTP / REST
   ↓
Product API
```

This demonstrates service-to-service REST communication rather than
coupling the AI layer directly to the product repository.

------------------------------------------------------------------------

# 🧾 DTO Layer

The project uses DTOs to define API request and response models.

Current DTO examples include:

``` text
AddToCartRequest
AiSearchRequest
LoginRequest
OrderItemRequest
PlaceOrderRequest
ProductResponse
RegisterRequest
ShoppingIntent
```

DTOs help keep API contracts separate from database entities.

------------------------------------------------------------------------

# ✅ Validation

The project uses Jakarta Bean Validation for validating application
data.

Examples include:

``` java
@NotBlank
@NotNull
@Positive
@Min
```

Validation is used to prevent invalid input from reaching business
logic.

Examples include:

-   Required fields
-   Valid product values
-   Positive prices
-   Valid quantities
-   Required request fields

------------------------------------------------------------------------

# 🚨 Global Exception Handling

The application contains a centralized:

``` text
GlobalExceptionHandler
```

Instead of putting exception-handling logic inside every controller:

``` text
Controller
    ↓
Service
    ↓
Exception
    ↓
GlobalExceptionHandler
    ↓
HTTP error response
```

This keeps controllers cleaner and provides a consistent error-response
mechanism.

------------------------------------------------------------------------

# 🗄️ Database & Persistence

### Database

**MySQL**

### Persistence

**Spring Data JPA + Hibernate**

The project uses JPA entities and repositories to persist application
data.

Main domain entities include:

``` text
User
Product
Cart
CartItem
Order
OrderItem
```

Hibernate manages the mapping between the Java entity model and MySQL
tables.

------------------------------------------------------------------------

# 📁 Project Structure

The current project follows a modular layered structure:

``` text
src/main/java/com/eshop/eshop
│
├── client
│   ├── ProductClient
│   └── RestTemplateConfig
│
├── config
│   ├── AppConfig
│   ├── JwtFilter
│   ├── KafkaProducerConfig
│   └── SecurityConfig
│
├── controller
│   ├── AiController
│   ├── AuthController
│   ├── CartController
│   ├── HomeController
│   ├── OrderController
│   ├── OrderItemController
│   ├── ProductController
│   └── UserController
│
├── dto
│   ├── AddToCartRequest
│   ├── AiSearchRequest
│   ├── LoginRequest
│   ├── OrderItemRequest
│   ├── PlaceOrderRequest
│   ├── ProductResponse
│   ├── RegisterRequest
│   └── ShoppingIntent
│
├── event
│   ├── OrderItemEvent
│   └── OrderPlacedEvent
│
├── exception
│   └── GlobalExceptionHandler
│
├── kafka
│   └── OrderProducer
│
├── model
│   └── entity
│       ├── Cart
│       ├── CartItem
│       ├── Order
│       ├── OrderItem
│       ├── Product
│       └── User
│
├── repository
│
└── service
    ├── AiService
    ├── AuthService
    ├── CartService
    ├── OrderItemService
    ├── OrderService
    ├── ProductService
    ├── UserService
    │
    └── impl
        ├── AiServiceImpl
        ├── AuthServiceImpl
        ├── CartServiceImpl
        ├── OrderItemServiceImpl
        ├── OrderServiceImpl
        └── ProductServiceImpl
```

------------------------------------------------------------------------

# 🧪 API Testing

The APIs are tested using **Postman**.

Core API groups include:

``` text
/api/users
/api/products
/api/orders
/api/orderitems
/api/cart
/api/auth
/api/ai
```

The project follows REST conventions for request handling and HTTP
responses.

------------------------------------------------------------------------

# ⚙️ Running the Project

## Prerequisites

-   Java 21
-   Maven
-   MySQL
-   Apache Kafka for Kafka-dependent functionality

## 1. Create the database

``` sql
CREATE DATABASE eshop_db;
```

## 2. Configure MySQL

Example:

``` properties
spring.application.name=eshop

spring.datasource.url=jdbc:mysql://localhost:3306/eshop_db
spring.datasource.username=root
spring.datasource.password=${DB_PASSWORD}

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

Keep passwords, API keys and JWT secrets outside the repository.

## 3. Build

``` bash
mvn clean install -DskipTests
```

## 4. Run

``` bash
mvn spring-boot:run -DskipTests
```

The application uses port `8080` by default.

------------------------------------------------------------------------

# 🔒 Security Configuration

Sensitive values should be provided through environment variables rather
than committed to source control.

For example:

``` properties
spring.datasource.password=${DB_PASSWORD}
```

Similarly, AI credentials and JWT secrets should be externalized.

Do **not** commit:

``` text
database passwords
API keys
JWT secrets
private credentials
```

to GitHub.

------------------------------------------------------------------------

# 🧠 Technical Concepts Practiced

This project provided practical experience with:

-   Java 21
-   Spring Boot
-   REST API design
-   Dependency Injection
-   Layered architecture
-   Spring Data JPA
-   Hibernate
-   MySQL
-   JPA entity relationships
-   DTO design
-   Bean Validation
-   Global exception handling
-   `Optional`
-   Spring Security
-   BCrypt
-   JWT authentication
-   Kafka producers and events
-   REST client communication
-   AI API integration
-   Natural-language intent extraction
-   Git/GitHub
-   Postman API testing

------------------------------------------------------------------------

# 🔮 Future Enhancements

The following areas can be developed further:

-   Role-based authorization
-   Complete product/order CRUD operations
-   Inventory and stock management
-   Order status tracking
-   Payment integration
-   Product reviews and ratings
-   Wishlist
-   Redis caching
-   Kafka consumers
-   Swagger/OpenAPI documentation
-   JUnit and Mockito test coverage
-   Docker / Docker Compose
-   CI/CD
-   Frontend integration
-   More advanced AI recommendations

------------------------------------------------------------------------

# 🎯 Project Objective

The objective of eShop is to build a realistic Java/Spring Boot backend
while progressively introducing concepts used in modern backend systems.

The project combines:

``` text
Traditional Backend Engineering
            +
Database Persistence
            +
Security
            +
Event-Driven Architecture
            +
AI Integration
```

rather than treating AI as a separate standalone feature.

------------------------------------------------------------------------

## 📌 Current Status

**Active development / learning project**

The core backend architecture, database persistence, entity
relationships, authentication/security layer, validation and exception
handling, cart/order modules, Kafka event infrastructure, and
AI-assisted product search have been developed. Further
production-oriented features and testing can be added incrementally.
