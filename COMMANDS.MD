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
