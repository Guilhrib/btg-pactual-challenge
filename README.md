# BTG Pactual Challenge

This project was developed as part of the BTG Pactual challenge, aimed at improving my knowledge and gaining hands-on experience with key technologies. The primary goal was to create a RabbitMQ consumer that processes order events, stores them in a MongoDB collection, and provides an API layer for retrieving information based on customer ID.

## Project Overview

The system is designed to efficiently handle order events by:

- **Consuming Order Events**: The RabbitMQ consumer listens to an order queue, processes incoming order events, and saves the relevant data into a MongoDB collection.
  
- **Storing Data**: Each order event is persisted in a MongoDB collection for reliable and scalable storage.

- **API Layer**: A RESTful API is provided to query and retrieve order-related information based on customer ID. This allows users to easily access and manage order data.

## Technologies Used

- **Java**
- **Spring Boot**
- **MongoDB**
- **RabbitMQ**
- **SLF4J**

## How to Run the Project

1. **Clone the repository**:
   ```bash
   git clone https://github.com/your-repository-url.git
