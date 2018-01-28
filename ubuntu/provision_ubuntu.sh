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
  curl
  #eclipse
  #eclipse-cdt
  filezilla
  g++
  git
  gparted
  libssl-dev
  dropbox
  nautilus-dropbox
  mysql-workbench
  make
  psensor
  poedit
  pyrenamer
  python
  python-pip
  python-software-properties
  rbenv
  samba
  #skype
  #sqlitebrowser
  tree
  unrar
  vagrant
  virtualbox-dkms
  virtualbox
  vlc
  wget
  xdotool
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
# see the file at this folder

# -------------------------------------
#
# NVM
#
# -------------------------------------
curl -o- https://raw.githubusercontent.com/creationix/nvm/v0.33.8/install.sh | bash
# -------------------------------------
export NVM_DIR="$HOME/.nvm"
[ -s "$NVM_DIR/nvm.sh" ] && \. "$NVM_DIR/nvm.sh" # This loads nvm
# verify
command -v nvm

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
sudo apt-add-repository -y "deb http://repository.spotify.com stable non-free" &&
sudo apt-key adv --keyserver keyserver.ubuntu.com --recv-keys D2C19886 &&
sudo apt-get update -qq &&
sudo apt-get install spotify-client

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
sud 	o apt-get -y install oracle-java8-set-default

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
# Android Studio --> TODO: test to aprove
#
# https://developer.android.com/studio/index.html
#
# -------------------------------------
#sudo add-apt-repository ppa:paolorotolo/android-studio -y
#sudo apt-get update
#sudo apt-get -y install android-studio

# Manual
# Descargar: https://developer.android.com/studio/index.html
# https://developer.android.com/studio/install.html
# sudo apt-get -y install lib32z1 lib32ncurses5 lib32bz2-1.0 lib32stdc++6

sudo apt-get autoremove
