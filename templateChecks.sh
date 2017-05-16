#!/usr/bin/env bash
TEMPLATE_BIG_BANG_VERSION=$(sed -n -e "s/\.*final BIGBANG_VERSION = '\s*\(.*\)[\"\']\s*/\1/p" template/app/build.gradle)
BIG_BANG_VERSION=$(sed -n -e "s/\.*librariesVersion = '\s*\(.*\)[\"\']\s*/\1/p" versions.gradle)

if [ ${TEMPLATE_BIG_BANG_VERSION} = ${BIG_BANG_VERSION} ]; then
    echo "BIGBANG_VERSION"
    echo ${TEMPLATE_BIG_BANG_VERSION}
else
    echo "BigBang version: "${BIG_BANG_VERSION}
    echo "Version used in template: "${TEMPLATE_BIG_BANG_VERSION} 1>&2
    echo "Template's BigBang library version and BigBang librariesVersion should be exactly the same."
    exit 1
fi

cd template/;

./gradlew clean;
./gradlew generateAllVersionsStagingDebugSources;
./gradlew generateAllVersionsStagingDebugAndroidTestSources;
./gradlew mockableAndroidJar;
./gradlew prepareAllVersionsStagingDebugUnitTestDependencies;
./gradlew compileAllVersionsStagingDebugSources;
./gradlew compileAllVersionsStagingDebugAndroidTestSources;
./gradlew compileAllVersionsStagingDebugUnitTestSources;

cd ..