# Microsoft Microservices Hotel Rating System Project
## Overview
In this exciting microservices project, I embarked on a comprehensive journey of building a sophisticated hotel rating system that showcases the power of distributed systems and modern cloud-native architecture.

The project is a testament to the complexity and elegance of microservices design, demonstrating how multiple independent services can seamlessly communicate and provide a robust, scalable solution for hotel ratings and user interactions.

## Project Motivation
The core motivation behind this project was to create a real-world application that:
- Demonstrates advanced microservices architectural patterns
- Showcases service-to-service communication
- Implements robust authentication and gateway mechanisms
- Provides a flexible, scalable system for hotel ratings

## Architectural Highlights
### Microservices Landscape
The system is composed of three critical microservices:
1. [**Hotel Service**](https://github.com/faizeraza/HotelService): 
   - Responsible for storing and managing hotel information
   - Maintains a comprehensive database of hotel details
   - Provides endpoints for hotel-related operations

2. [**User Service**](https://github.com/faizeraza/user):
   - Manages user profiles and authentication
   - Tracks user interactions and rating history
   - Communicates with other services to compile user-specific data

4. [**Rating Service**](https://github.com/faizeraza/RatingService):
   - Handles the core rating and review mechanisms
   - Stores and processes user ratings for hotels
   - Enables complex rating queries and aggregations

## Technical Architecture
### Key Components
- [**API Gateway**](https://github.com/faizeraza/ApiGateWay): Central entry point for all client requests
- [**Service Registry (Eureka)**](https://github.com/faizeraza/ServiceRegistry): Dynamic service discovery and management
- **Spring Security**: Robust authentication framework
- [**Distributed Configuration Management**](https://github.com/faizeraza/micro-configuration-service): Centralized configuration server

## Technical Challenges Solved
- Inter-service communication without direct IP dependencies
- Dynamic service scaling and load balancing
- Secure authentication across distributed services
- Flexible database management for each microservice

## Technology Stack
- **Languages**: Java, Spring Boot
- **Service Discovery**: Netflix Eureka
- **Authentication**: Spring Security, Okta
- **Database**: Flexible (supports multiple database technologies like, PostgreSQL, MySQL, MongoDB)
- **API Management**: Spring Cloud Gateway

## Workflow Demonstration
1. Client initiates a request through API Gateway
2. Authentication verified by Spring Security
3. Request routed to appropriate microservice
4. Services communicate via Service Registry
5. Consolidated response returned to client

## Key Learning Outcomes
- Advanced microservices architecture implementation
- Service communication strategies
- Authentication in distributed systems
- Dynamic service discovery techniques

## Future Roadmap
- Implement advanced caching mechanisms
- Enhanced error handling and logging
- Performance optimization
- Additional security layers

## Installation and Setup
```bash
# Clone the repository
git clone https://github.com/yourusername/hotel-rating-microservices

# Setup Service Registry
./mvnw spring-boot:run -pl service-registry

# Start API Gateway
./mvnw spring-boot:run -pl api-gateway

# Launch Individual Services
./mvnw spring-boot:run -pl hotel-service
./mvnw spring-boot:run -pl user-service
./mvnw spring-boot:run -pl rating-service
```

## Contributing
Contributions are welcome! Please follow these steps:
1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a pull request

## License
[Specify your license - MIT/Apache/etc.]

## Acknowledgements
Inspired by the comprehensive microservices training series that provided foundational insights into distributed system design.
