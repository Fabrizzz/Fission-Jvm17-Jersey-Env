#!/bin/sh
set -eou pipefail
mvn clean package
rm -rf ${DEPLOY_PKG}
cp ${SRC_PKG}/target/*.war ${DEPLOY_PKG}
