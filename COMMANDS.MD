## Project Requirements
> - JDK 21 (LTS)
> - Postgresql 18
> - Node 20+

## Install system 
> - Install JDK 21 (LTS) [Here](https://www.oracle.com/br/java/technologies/downloads/#java21)
> - Install PostgreSQL [Here](https://www.enterprisedb.com/downloads/postgres-postgresql-downloads)
> - Copy .env.example to .env
>   - ``` cp .env.example .env ```
> - Run Command to copy .env to backend and frontend
>   - ```make env```

> - Run App Locally using make
>   - ```make up-dev```

> - Running App on Docker
>   - Build App:
>       - ``` docker compose build ``` 
>   - Start containers detached (without logs)
>       - ``` docker compose up -d ```
>   - Start containers attached (with logs)
>       - ``` docker compose up ```

> - Runnins App Manually
>   - Open terminal on `./backend` folder 
>   - Run backend
>       - ./mvnw spring-boot:run
>   - Open terminal on `./frontend` folder 
>       - npm install
>       - npm run dev


# Running on Docker
```
docker compose build
docker compose up -d
```

# Database

## Run postgres container
```
// setup container
docker run -d --name unibook-db -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=unibook_db -p 5432:5432 postgres:16
// start container
docker container start unibook-db
```

## Install postgresql locally on Ubuntu
```
sudo apt install postgresql postgresql-contrib
sudo systemctl status postgresql
sudo -i -u postgres
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

# Swagger available at
```
http://localhost:8080/swagger-ui/index.html
```

## Kill processes
```pkill -f spring```
```pkill -f node```

## Known errors

> ### Error: Cannot find module 'node:path'
> - It happens when running ```npm run dev```
> - Fix:
>   - ```nvm use 20 && npm run dev```

<hr><br>

> ### Frontend works but backend doesn't
> - It might happens when running this app on Docker
> - Fix:
>   - Stop project containers
>       - ```docker compose -p unibook stop```
>   - Run only backend on docker
>       - ```docker container start unibook-db```
>   - Go to backend folder and run the app backend
>       - ```cd backend```
>       - ```./mvnw spring-boot:run```
>   - Go to frontend folder and run the app frontend
>       - ```cd frontend```
>       - ```npm run dev```

> ### Database connection error after installing postgres locally
> - It happens if you have installed postgres recently and running ```psql -h localhost -U postgres -d unibook_db``` doesn't works
> - Fix: 
>   - Run psql on terminal:
>       - ```sudo -u postgres psql```
>   - Update password to match env password
>       - ```ALTER USER postgres WITH PASSWORD 'postgres';```
>   - Test trying to connect to postgres with these new credentials
>       - ```psql -h localhost -U postgres -d unibook_db```
>       - ``` ```