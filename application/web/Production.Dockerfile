FROM gradle:jdk8-hotspot AS build-env
COPY . /app
WORKDIR /app
RUN gradle installBootDist

FROM bellsoft/liberica-openjdk-alpine:11 as final
RUN apk add --no-cache --upgrade bash
RUN adduser -S deployer -G wheel
WORKDIR /app
COPY --chown=deployer:wheel --from=build-env /app/build/install/app-boot .
# Run under non-privileged user with minimal write permissions
USER deployer
ENTRYPOINT ["bin/web"]
# Expose dummy port to avoid Heroku errors
ENV PORT=8080
EXPOSE $PORT
