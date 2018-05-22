#!/bin/bash

if [ -z "$1" ]
  then
    echo "No domain supplied"
    echo "usage ./letsEncrypt.sh domain.org"
    exit
fi



sudo docker run -it --rm --name certbot \
  -p 80:80 \
  -v "/etc/letsencrypt:/etc/letsencrypt" \
  -v "/var/lib/letsencrypt:/var/lib/letsencrypt" \
  certbot/certbot certonly --standalone -d $1 \
  --text --agree-tos \
  --email admin@example.com \
  --server https://acme-v01.api.letsencrypt.org/directory \
  --rsa-key-size 4096 --verbose --renew-by-default \
  --preferred-challenges http
