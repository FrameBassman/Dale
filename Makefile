start-dev:
	docker-compose \
		--project-directory=${PWD} \
		--project-name=dale \
		-f deploy/docker-compose.yml \
		-f deploy/docker-compose.development.yml \
		up --build

start-db:
	docker-compose \
    		--project-directory=${PWD} \
    		--project-name=dale \
    		-f deploy/docker-compose.yml \
    		-f deploy/docker-compose.development.yml \
    		up -d postgres

psql:
	docker exec -it dale_postgres psql postgresql://postgres:postgres@localhost:5432/postgres

logs-prod:
	heroku logs --tail --app romashovtech-dale

