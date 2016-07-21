# -------------------------------------
#
# Docker Compose
#
# -------------------------------------
#pip install --upgrade pip
#pip install docker-compose
sudo chmod +x /usr/local/bin/docker-compose
sudo curl -L "https://github.com/docker/compose/releases/download/1.9.0/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose

#sudo sh -c "$(curl -L https://github.com/docker/compose/releases/download/1.7.1/docker-compose-`uname -s`-`uname -m` > /usr/local/bin/docker-compose)"
#sudo chmod +x /usr/local/bin/docker-compose

# Check install
#docker-compose --version

#curl -L https://raw.githubusercontent.com/docker/compose/$(docker-compose version --short)/contrib/completion/bash/docker-compose -o /etc/bash_completion.d/docker-compose
sudo docker-compose --version
