FROM gradle:jdk8-hotspot AS build-env
COPY . /dale
WORKDIR /dale
RUN ./gradlew --project-dir application installBootDist

FROM bellsoft/liberica-openjdk-alpine:8u292-10 as final
RUN adduser -S user
WORKDIR /app
COPY --from=build-env /dale/application/build/install/application-boot .
# Run under non-privileged user with minimal write permissions
USER user
ENTRYPOINT ["bin/application"]
# Expose dummy port to avoid Heroku errors
ENV PORT=8080
EXPOSE $PORT
