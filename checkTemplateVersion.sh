#!/usr/bin/env bash
TEMPLATE_BIG_BANG_VERSION=$(sed -n -e "s/\.*final BIGBANG_VERSION = '\s*\(.*\)[\"\']\s*/\1/p" template/app/build.gradle)
BIG_BANG_VERSION=$(sed -n -e "s/\.*librariesVersion = '\s*\(.*\)[\"\']\s*/\1/p" versions.gradle)

if [ $TEMPLATE_BIG_BANG_VERSION = $BIG_BANG_VERSION ]; then
    echo "BIGBANG_VERSION"
    echo $TEMPLATE_BIG_BANG_VERSION
else
    echo $TEMPLATE_BIG_BANG_VERSION
    echo "Not same version"
    echo $BIG_BANG_VERSION 1>&2
    exit 1
fi