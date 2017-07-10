#!/usr/bin/env bash
export DEBIAN_FRONTEND=noninteractive

# -------------------------------------
#
# Official Ubuntu 16.04 packages
#
# -------------------------------------
packagelist=(
  build-essential
  chromium-browser
  eclipse
  eclipse-cdt
  filezilla
  g++
  git
  gparted
  libssl-dev
  nautilus-dropbox
  mysql-workbench
  make
  psensor
  poedit
  pyrenamer
  python
  python-pip
  python-software-properties
  samba
  #skype
  #sqlitebrowser
  tree
  unrar
  vagrant
  virtualbox-dkms
  virtualbox
  vlc
)

sudo apt-get update -q
sudo apt-get install -y ${packagelist[@]}

# -------------------------------------
#
# Config GIT
#
# -------------------------------------
git config --global user.name "pabagan"
git config --global user.email "pabagan@gmail.com"

# -------------------------------------
#
# NodeJs and npm
#
# -------------------------------------
# Using Ubuntu 16.04 default repo
sudo apt-get install -y nodejs

# angular2 dependencies
npm update -g && sudo npm install -g


# Npm installs
npm install -g \
  concurrently \
  lite-server \
  typescript \
  angular-cli \
npm update -g && sudo npm install -g concurrently lite-server typescript angular-cli

# Npm installs
npm install -g \
  bower \
  ember \
  ember-cli \
  express \
  google-spreadsheet-to-json \
  grunt \
  grunt-cli \
  generator-meanjs \
  gulp \
  gulp-cli \
  node-gyp \
  karma-cli \
  mean-cli \
  protractor \
  sails \
  typescript \
  nodemon \
  webpack \
  webpack-dev-server \
  yo


# from NVM
#curl -sL https://raw.githubusercontent.com/creationix/nvm/v0.31.0/install.sh -o install_nvm.sh
#bash install_nvm.sh
#source ~/.profile



# -------------------------------------
#
# Install Pips
#
# -------------------------------------
sudo pip install Django

# -------------------------------------
#
# Sublime Text
#
# -------------------------------------
sudo add-apt-repository ppa:webupd8team/sublime-text-3 -y
sudo apt-get update
sudo apt-get -y install sublime-text-installer

# -------------------------------------
#
# Google Chrome Ubuntu 16.04
#
# -------------------------------------
sudo wget https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb
sudo apt-get update -q
sudo apt-get -y install libxss1 libgconf2-4 libappindicator1 libindicator7
sudo dpkg -i google-chrome-stable_current_amd64.deb

# -------------------------------------
#
# Spotify
#
# -------------------------------------
sudo apt-get update -q
sudo apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv-keys 94558F59 D2C19886
echo deb http://repository.spotify.com stable non-free | sudo tee /etc/apt/sources.list.d/spotify.list
sudo apt-get update -q
sudo apt-get -y install spotify-client

# -------------------------------------
#
# Install GIMP 2.8.14 on ubuntu 14.04
#
# -------------------------------------
sudo add-apt-repository ppa:otto-kesselgulasch/gimp -y
sudo apt-get update -q
sudo apt-get -y install gimp

# -------------------------------------
#
# UnetBootin
#
# -------------------------------------
sudo add-apt-repository ppa:gezakovacs/ppa -y
sudo apt-get update -q
sudo apt-get -y install unetbootin

# -------------------------------------
#
# Jdownloader
#
# -------------------------------------
sudo add-apt-repository ppa:jd-team/jdownloader -y
sudo apt-get update -q
sudo apt-get -y install jdownloader

# -------------------------------------
#
# Java 8
#
# -------------------------------------
sudo add-apt-repository ppa:webupd8team/java -y
sudo apt-get update
sudo apt-get -y install oracle-java8-installer

# To automatically set up the Java 8 environment variables
sudo apt-get -y install oracle-java8-set-default

# -------------------------------------
#
# QBitTorrent
#
# -------------------------------------
sudo add-apt-repository ppa:qbittorrent-team/qbittorrent-stable -y
sudo apt-get update
sudo apt-get -y install qbittorrent


# -------------------------------------
#
# Heroku
# https://devcenter.heroku.com/articles/getting-started-with-nodejs#set-up
#
# -------------------------------------
sudo add-apt-repository -y "deb https://cli-assets.heroku.com/branches/stable/apt ./"
curl -L https://cli-assets.heroku.com/apt/release.key | sudo apt-key add -
sudo apt-get -y update
sudo apt-get install -y heroku

# -------------------------------------
#
# SQLite Browser
# http://sqlitebrowser.org/
#
# -------------------------------------
sudo add-apt-repository -y ppa:linuxgndu/sqlitebrowser-testing
sudo apt-get update && apt-get -y install sqlitebrowser


# -------------------------------------
#
# Genymotion: Android Emulator
#
# Hay que ir manual:
# https://www.genymotion.com/download/
#
# -------------------------------------

# -------------------------------------
#
# Android Studio
#
# https://developer.android.com/studio/index.html
#
# -------------------------------------
sudo add-apt-repository ppa:paolorotolo/android-studio -y
sudo apt-get update
sudo apt-get -y install android-studio

# Manual
# Descargar: https://developer.android.com/studio/index.html
# https://developer.android.com/studio/install.html
# sudo apt-get -y install lib32z1 lib32ncurses5 lib32bz2-1.0 lib32stdc++6