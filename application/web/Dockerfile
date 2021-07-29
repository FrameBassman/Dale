FROM gradle:jdk8-hotspot AS build-env
COPY . /app
WORKDIR /app
RUN gradle installBootDist

FROM bellsoft/liberica-openjdk-alpine:11 as final
RUN apk add --no-cache --upgrade bash
RUN adduser -S user
WORKDIR /app
COPY --from=build-env /app/build/install/web-boot .
COPY --from=build-env /app/wait-for-it.sh .
COPY --from=build-env /app/start.sh .
# Run under non-privileged user with minimal write permissions
#USER user
#ENTRYPOINT ["./start.sh && bin/web"]
CMD ./start.sh && bin/web
# Expose dummy port to avoid Heroku errors
ENV PORT=8080
EXPOSE $PORT
