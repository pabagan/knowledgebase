#!/usr/bin/env bash
export DEBIAN_FRONTEND=noninteractive

# -------------------------------------
#
# Official Ubuntu 16.04 packages
#
# -------------------------------------
packagelist=(
  apt-transport-https 
  build-essential
  eclipse
  eclipse-cdt
  filezilla
  g++
  git
  gparted
  libssl-dev
  poedit
  python
  python-pip
  software-properties-common
  apt-transport-https
  ca-certificates
  curl
  tree
  unrar
  vagrant
  virtualbox-dkms
  virtualbox
  vlc
  wget
)

sudo apt update -q
sudo apt -y install ${packagelist[@]}

# -------------------------------------
#
# Config GIT
#
# -------------------------------------
git config --global user.name "pabagan"
git config --global user.email "pabagan@gmail.com"

# -------------------------------------
#
# Install Pips
#
# -------------------------------------
sudo pip install Django

# -------------------------------------
#
# Visual Studio Code 
#
# -------------------------------------
wget -q https://packages.microsoft.com/keys/microsoft.asc -O- | sudo apt-key add -
sudo add-apt-repository "deb [arch=amd64] https://packages.microsoft.com/repos/vscode stable main"
sudo apt update -q
sudo apt install code

# -------------------------------------
#
# Spotify
#
# -------------------------------------
curl -sS https://download.spotify.com/debian/pubkey.gpg | sudo apt-key add -
echo "deb http://repository.spotify.com stable non-free" | sudo tee /etc/apt/sources.list.d/spotify.list        
sudo apt update -q && sudo apt -y install spotify-client

# -------------------------------------
#
# Java 8
#
# -------------------------------------
sudo add-apt-repository ppa:webupd8team/java -y
sudo apt update
sudo apt -y install oracle-java8-installer

# To automatically set up the Java 8 environment variables
sudo apt -y install oracle-java8-set-default

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
  ts-node
  tslint
  firebase-tools
  testcafe
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
nvm install v8.16.2
npm i -g ${npmGlobalDeps[@]}

# Node 10
nvm install lts/dubnium
npm i -g ${npmGlobalDeps[@]}

# Node 12
nvm install lts/erbium
npm i -g ${npmGlobalDeps[@]}

# back to default node version 
nvm use default

# -------------------------------------
#
# Yarn
# https://yarnpkg.com/lang/en/docs/install/#debian-stable
#
# -------------------------------------
curl -sS https://dl.yarnpkg.com/debian/pubkey.gpg | sudo apt-key add -
echo "deb https://dl.yarnpkg.com/debian/ stable main" | sudo tee /etc/apt/sources.list.d/yarn.list
sudo apt-get update -q 
sudo apt-get -q install yarn

# -------------------------------------
#
# MySQL Workbench: test si va bien
#
# -------------------------------------
sudo apt update
sudo apt -y install mysql-workbench


# -------------------------------------
#
# Gcloud login
#
# -------------------------------------
firebase login

sudo snap install google-cloud-sdk --classic

# -------------------------------------
#
# Docker: https://docs.docker.com/install/linux/docker-ce/ubuntu/
# TODO: not tested yet. Debug or remove this when checking.
# -------------------------------------
sudo apt-get update -q 
sudo apt-get -q install \
    apt-transport-https \
    ca-certificates \
    curl \
    gnupg-agent \
    software-properties-common
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -
sudo apt-key fingerprint 0EBFCD88
sudo add-apt-repository \
   "deb [arch=amd64] https://download.docker.com/linux/ubuntu \
   $(lsb_release -cs) \
   stable"
sudo apt-get update
sudo apt-get install docker-ce docker-ce-cli containerd.io
sudo docker run hello-world

# Avoid sudo
sudo groupadd docker
sudo usermod -aG docker $USER
newgrp docker 
docker run hello-world

sudo systemctl enable docker
# sudo systemctl disable docker

# initi docker swarm
 docker swarm init


# -------------------------------------
#
# SDKMan
#
# -------------------------------------
curl -s "https://get.sdkman.io" | bash
sdk install sbt