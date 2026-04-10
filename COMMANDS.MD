# Running on Docker
```
docker compose build
docker compose up -d
```

# Database

## Run postgres container
```
docker run -d --name postgres -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=unibook_db -p 5432:5432 postgres:16
```

## Create database if your postgres is running locally
```
psql -U postgres
CREATE DATABASE unibook_db;
```

## Create database if your postgres is running on docker
```
docker exec -it postgres psql -U postgres
CREATE DATABASE unibook_db;
```

## Run Backend
```
cd backend
./mvnw spring-boot:run
```

## Run Frontend
```
cd frontend 
npm run dev 
```

## User Node 20
```
nvm use 20
```