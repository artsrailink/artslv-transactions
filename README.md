# Transaction Service

Untuk Development Transaction service di deploy di ip:172.16.8.35


How to run this example :

```sh
# Run local
pake intellij import project dan run seperti biasa

# Run di Docker

## Install docker dulu

https://docs.docker.com/compose/install/

## build docker images

sudo mvn clean package docker:build

## Di local kita akan ada dua image transaction service dan transaction service dengan tag gitlab.kereta-api.co.id

docker images

## Start Image

docker run -p 9098:9098 --name trans-service gitlab.kereta-api.co.id:5000/arts-transaction

atau run sebagai daemon


docker run -p 9098:9098 -d --name trans-service gitlab.kereta-api.co.id:5000/arts-transaction

## Tail LOG

docker logs trans-service

```

Once all the services are up, the following URLs will be available
