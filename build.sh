#!/bin/bash
set -e

export DOCKER_BUILDKIT=0
APP_PORT=7011
DOCKER_RUN_IMAGE=c2b-stk-push-service-container


docker build  -t "${DOCKER_RUN_IMAGE}" .  > build.log

EXISTS=$(docker ps -a --filter name=$DOCKER_RUN_IMAGE --format {{.Names}})

# If the container exists, stop and remove it
if [ "$EXISTS" == "$DOCKER_RUN_IMAGE" ]; then
  docker stop $DOCKER_RUN_IMAGE
  docker rm $DOCKER_RUN_IMAGE
fi

docker run -d --restart unless-stopped -v /var/log/ncba-broker:/application/log --name  "${DOCKER_RUN_IMAGE}"  -p $APP_PORT:7011 -t "${DOCKER_RUN_IMAGE}"