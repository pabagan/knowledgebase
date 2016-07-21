sudo add-apt-repository -y "deb https://cli-assets.heroku.com/branches/stable/apt ./"

curl -L https://cli-assets.heroku.com/apt/release.key | sudo apt-key add -

sudo apt-get -y update

sudo apt-get install -y heroku 