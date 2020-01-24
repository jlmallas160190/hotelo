# hotelo
# Installation
1. run the script `docker network create --driver=bridge --subnet=172.19.0.0/16  --ip-range=172.19.0.0/24  --gateway=172.19.0.254 kerno-network`
2. run the script `docker-compose -f postgresql-docker.yml up -d`
3. enter to the db running the following script `docker exec -it kerno-postgres /bin/bash`
4. run the script `psql -U kerno postgres;`
5. create the database `avalith_hotelo`
6. go to the project path and run the script `sh run-docker-image.sh`.
## Swagger
See the API documentation `http://localhost:8080/swagger-ui.html`
## Postman
1. Install postman
2. Import the postman files located in the `postman` folder within the project.
# Services
1. Create a user via postman
2. Login
3. Copy the `sessionId` obtained it in the login service in the `AUTHTOKEN` variable within `LOCALHOTELO` environment.
4. Run the remaining services.
