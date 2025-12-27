# 📊 Inventory Management System

A complete multi-user inventory management system built with Spring Boot, featuring JWT authentication, role-based access control, and a modern web interface.

## 🚀 Features

### Authentication & Security
- JWT-based authentication
- Role-based access control (ADMIN, MANAGER, WORKER)
- Password encryption with BCrypt
- Method-level security

### User Management
- User registration and login
- User CRUD operations
- Role assignment
- Account activation/deactivation

### Product Management
- Product CRUD operations
- SKU management
- Stock tracking
- Low stock alerts
- Category and supplier management

### Order Management
- Order creation and tracking
- Order status management
- Order history

### Reports & Analytics
- Dashboard statistics
- Inventory reports
- Order reports
- Low stock alerts

### Web Interface
- Responsive design
- Role-based UI
- Real-time data updates
- Modern dashboard

## 🛠️ Technology Stack

- **Backend:** Spring Boot 4.0.1, Java 25
- **Security:** Spring Security, JWT
- **Database:** H2 (in-memory)
- **Frontend:** HTML, CSS, JavaScript
- **Build Tool:** Maven
- **Testing:** JUnit 5, Mockito

## 📋 Prerequisites

- Java 25 or higher
- Maven 3.6+
- IDE (IntelliJ IDEA, Eclipse, VS Code)

## 🚀 Getting Started

### 1. Clone the Repository
```bash
git clone <repository-url>
cd inventory-management-system
```

### 2. Development Setup
```bash
# Build the project
mvn clean compile

# Run with H2 database (development)
mvn spring-boot:run
```

### 3. Production Setup
```bash
# Using Docker Compose (recommended)
docker-compose up -d

# Or manual deployment
./deploy.sh
```

### 4. Access the Application
- **Web Interface:** http://localhost:8080
- **API Documentation:** http://localhost:8080/swagger-ui.html
- **Health Check:** http://localhost:8080/actuator/health
- **Metrics:** http://localhost:8080/actuator/prometheus
- **H2 Console (dev only):** http://localhost:8080/h2-console

## 🔐 Default Credentials

The system creates a WORKER role automatically during first registration.

## 📚 API Endpoints

### Authentication
```\nPOST /api/auth/register - User registration\nPOST /api/auth/login - User login\nGET /api/auth/test - Test endpoint\n```

### User Management (ADMIN/MANAGER only)
```\nGET /api/users - List all users\nGET /api/users/{id} - Get user by ID\nPUT /api/users/{id} - Update user\nDELETE /api/users/{id} - Delete user\nPOST /api/users/{id}/toggle-status - Toggle user status\n```

### Product Management
```\nGET /api/products - List all products\nGET /api/products/{id} - Get product by ID\nPOST /api/products - Create product (ADMIN/MANAGER)\nPUT /api/products/{id} - Update product (ADMIN/MANAGER)\nDELETE /api/products/{id} - Delete product (ADMIN)\nGET /api/products/low-stock - Get low stock products\n```

### Order Management
```\nGET /api/orders - List all orders\nGET /api/orders/{id} - Get order by ID\nPOST /api/orders - Create order\nPUT /api/orders/{id}/status - Update order status\nGET /api/orders/user/{userId} - Get orders by user\n```

### Reports (ADMIN/MANAGER only)
```\nGET /api/reports/dashboard - Dashboard statistics\nGET /api/reports/inventory - Inventory report\nGET /api/reports/orders - Order report\n```

## 🗄️ Database Schema

### Users Table
- id, username, email, password, full_name, phone, role_id, is_active, created_at, updated_at

### Roles Table
- id, name, description, created_at

### Products Table
- id, name, sku, description, price, quantity, min_stock_level, category, supplier, is_active, created_at, updated_at

### Orders Table
- id, order_number, user_id, status, total_amount, created_at

### Order Items Table
- id, order_id, product_id, quantity, unit_price, total_price

### Inventory Transactions Table
- id, product_id, user_id, type, quantity, previous_quantity, new_quantity, notes, created_at

## 🧪 Testing

### Run Tests
```bash\nmvn test\n```

### Test Coverage
- Unit tests for services
- Integration tests for controllers
- Security tests

## 🔧 Configuration

### Application Properties
```properties\n# Database\nspring.datasource.url=jdbc:h2:mem:inventorydb\n\n# JWT\njwt.secret=myInventoryManagementSecretKey2024\njwt.expiration=86400000\n\n# H2 Console\nspring.h2.console.enabled=true\n```

## 👥 User Roles

### WORKER
- View products
- Create orders
- View own orders

### MANAGER
- All WORKER permissions
- Manage products
- View all orders
- View reports
- View users

### ADMIN
- All MANAGER permissions
- Manage users
- Delete products
- Full system access

## 🚀 Deployment

### Production Configuration
1. Replace H2 with production database (MySQL, PostgreSQL)
2. Update JWT secret key
3. Configure HTTPS
4. Set up monitoring

### Docker Deployment
```dockerfile\nFROM openjdk:25-jdk-slim\nCOPY target/*.jar app.jar\nEXPOSE 8080\nENTRYPOINT [\"java\", \"-jar\", \"/app.jar\"]\n```

## 📈 Future Enhancements

- Email notifications
- Barcode scanning
- Mobile app
- Advanced reporting
- Multi-warehouse support
- Integration with external systems

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## 📄 License

This project is licensed under the MIT License.

## 📞 Support

For support and questions, please create an issue in the repository.

---

**Built with ❤️ using Spring Boot**