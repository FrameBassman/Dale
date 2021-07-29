start-dev:
	docker-compose \
		--project-directory=${PWD} \
		--project-name=dale \
		-f deploy/docker-compose.yml \
		-f deploy/docker-compose.development.yml \
		up --build
