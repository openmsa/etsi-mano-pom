#!/bin/sh
./build.sh
zip package.zip *.csar
mvn deploy:deploy-file -DgroupId=com.ubiqube.mano \
        -DartifactId=package-demo \
        -Dversion=0.0.1-SNAPSHOT \
        -Dpackaging=jar \
        -Dfile=package.zip \
        -DrepositoryId=nexus-snapshots \
        -Durl=http://nexus.ubiqube.com/repository/maven-snapshots/
