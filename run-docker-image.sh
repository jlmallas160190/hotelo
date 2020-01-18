#!/usr/bin/env bash
VERSION=1.0.0
MICROSERVICE="avalith_hotelo"
REPO_URL="gcr.io/avalith_hotelo/${MICROSERVICE}"
DOCKER_IMAGE="${MICROSERVICE}-img:${VERSION}"
REMOTE_IMAGE_TAG="${REPO_URL}:${VERSION}"

echo "==> Cleaning build... "
./gradlew clean
echo "==> Creating new build... "
./gradlew build
echo "==> Stopping microservice ${MICROSERVICE}... "
docker stop ${MICROSERVICE}
echo "==> Deleting microservice ${MICROSERVICE}... "
docker rm ${MICROSERVICE}
echo "==> Deleting image tag ${REMOTE_IMAGE_TAG}... "
docker rmi ${REMOTE_IMAGE_TAG}
echo "==> Deleting image ${DOCKER_IMAGE}... "
docker rmi ${DOCKER_IMAGE}
echo "==> Building docker image ${DOCKER_IMAGE}... "
docker build -f Dockerfile -t ${DOCKER_IMAGE} .
# Comment this if you don't want to deploy to kubernetes
echo "==> Creating tag ${REMOTE_IMAGE_TAG}... "
docker tag ${DOCKER_IMAGE} ${REMOTE_IMAGE_TAG}
# Uncomment this if you want to push to google container registry
#echo "==> Pushing image ${REMOTE_IMAGE_TAG}... "
#docker push ${REMOTE_IMAGE_TAG}
docker-compose up -d