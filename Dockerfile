FROM alpine AS build

ARG COMMIT_SHA=<not-specified>
ARG BUILD_FILE_NAME=pollinator

WORKDIR /build

COPY ./build/libs/* ./application.jar
COPY LICENSE .

RUN echo "pollinator: $COMMIT_SHA" >> ./commit.sha

FROM openjdk:16

LABEL maintainer="fabio.percivaldi@mia-platform.eu" \
      name="pollinator" \
      description="" \
      eu.mia-platform.url="https://www.mia-platform.eu" \
      eu.mia-platform.version="0.1.0"

# set deployment directory
WORKDIR /home/java/app

COPY --from=build /build .

USER 1000

CMD ["java", "-jar", "./application.jar"]
