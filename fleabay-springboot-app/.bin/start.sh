#!/bin/bash

# DO NOT DELETE IT, IT'S MAKING THE SETUP FOR THE IDE BASED ON LOCAL .theia FOLDER
if [ -d "/projects/app/.theia" ]; then
  if [ ! -d "/projects/.theia" ]; then
    cp -R /projects/app/.theia /projects/.theia
    echo "cd /projects/app" >> /home/user/.bashrc
  fi
fi

. /projects/app/.hackajob

echo "[+] Starting the backend application..."
cd /projects/app
while true; do
    inotifywait -e create /projects/app/src/main/java/uk/co/fleabay/demo/ && supervisorctl reload;
done &

bash -c "$BUILD_AND_RUN"

sleep 10