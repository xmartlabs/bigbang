#!/usr/bin/env bash

./gradlew ui:bintrayUpload
./gradlew core:bintrayUpload
./gradlew dbflow:bintrayUpload
./gradlew retrofit:bintrayUpload
./gradlew log-crashlytics:bintrayUpload