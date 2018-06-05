#!/usr/bin/env bash
export DEBIAN_FRONTEND=noninteractive

# -------------------------------------
#
# NodeJs NVM
# https://github.com/nvm-sh/nvm
# -------------------------------------
curl -o- https://raw.githubusercontent.com/nvm-sh/nvm/v0.34.0/install.sh | bash

# Add extra npm deps
npmGlobalDeps=(
  typescript
  nodemon
  env-cmd
  ts-node
  tslint
  firebase-tools
  testcafe
  typescript-json-schema
  depcheck
  firebase-bolt
  npm-check-updates
)

. ~/.nvm/nvm.sh
. ~/.profile
. ~/.bashrc

# Last version
nvm install node
npm i -g ${npmGlobalDeps[@]}

# Node 6
nvm install lts/boron
npm i -g ${npmGlobalDeps[@]}

# Node 8
nvm install lts/carbon
npm i -g ${npmGlobalDeps[@]}

# Node 10 (version used by klikkie webapp)
nvm install v8.16.3
npm i -g ${npmGlobalDeps[@]}

# Node 10
nvm install lts/dubnium
npm i -g ${npmGlobalDeps[@]}

# Node 12
nvm install lts/erbium
npm i -g ${npmGlobalDeps[@]}

# back to default node version 
nvm use default
