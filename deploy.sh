#!/bin/bash

# Production Deployment Script for Inventory Management System

echo "🚀 Starting deployment process..."

# Build the application
echo "📦 Building application..."
mvn clean package -DskipTests -Pprod

# Build Docker image
echo "🐳 Building Docker image..."
docker build -t inventory-management-system:latest .

# Stop existing containers
echo "🛑 Stopping existing containers..."
docker-compose down

# Start new deployment
echo "▶️ Starting new deployment..."
docker-compose up -d

# Wait for health check
echo "🏥 Waiting for health check..."
sleep 30

# Check application health
echo "✅ Checking application health..."
curl -f http://localhost:8080/actuator/health || {
    echo "❌ Health check failed!"
    docker-compose logs app
    exit 1
}

echo "🎉 Deployment completed successfully!"
echo "📊 Application is running at: http://localhost:8080"
echo "📚 API Documentation: http://localhost:8080/swagger-ui.html"
echo "🔍 Metrics: http://localhost:8080/actuator/prometheus"