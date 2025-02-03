PROJECT_NAME = expense-tracker
DOCKER_COMPOSE_FILE = docker-compose.yml
DOCKER_APP_SERVICE = app
MAVEN = mvn
DOCKER = docker
COMPOSE = docker-compose

.PHONY: help build run test stop clean logs

help:
	@echo "Makefile for $(PROJECT_NAME)"
	@echo ""
	@echo "Available commands:"
	@echo "  make build         - Build the application and Docker image"
	@echo "  make run           - Run the application using Docker Compose"
	@echo "  make test          - Run all tests with Maven"
	@echo "  make stop          - Stop Docker Compose services"
	@echo "  make clean         - Clean project artifacts and Docker containers"
	@echo "  make logs          - View Docker Compose logs"

build:
	$(MAVEN) clean package -DskipTests
	$(COMPOSE) build

run:
	$(COMPOSE) up --build

test:
	$(MAVEN) test

stop:
	$(COMPOSE) down

clean:
	$(MAVEN) clean
	$(COMPOSE) down --volumes --remove-orphans

logs:
	$(COMPOSE) logs -f $(DOCKER_APP_SERVICE)
