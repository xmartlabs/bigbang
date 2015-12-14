#!/usr/bin/env bash

if [ $# -eq 1 ]
then
    TARGET_DIR="." # This script is supposed to be run in the repo folder
    ASSETS_DIR="${1%/}"

    cp "${ASSETS_DIR}"/*.keystore "${ASSETS_DIR}"/*.properties ${TARGET_DIR}/app
    mkdir -p ${TARGET_DIR}/app/src/production/res/values
    cp "${ASSETS_DIR}"/production-keys.xml ${TARGET_DIR}/app/src/production/res/values/keys.xml
    cp "${ASSETS_DIR}"/production-google-services.json ${TARGET_DIR}/app/src/production/google-services.json
    mkdir -p ${TARGET_DIR}/app/src/staging/res/values
    cp "${ASSETS_DIR}"/staging-keys.xml ${TARGET_DIR}/app/src/staging/res/values/keys.xml
    cp "${ASSETS_DIR}"/staging-google-services.json ${TARGET_DIR}/app/src/staging/google-services.json
else
    echo "Wrong number of arguments."
    echo "The valid syntax is: $0 path-of-assets-folder"
fi
