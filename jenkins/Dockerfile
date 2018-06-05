FROM jenkins/jenkins:lts
# set envs
ENV JENKINS_SLAVE_AGENT_PORT 50001

# if we want to install via apt
USER root
#RUN apt-get update && apt-get install -y ruby make more-thing-here

WORKDIR /var/jenkins_home

VOLUME /var/jenkins_home jenkins_home

EXPOSE 8080

# # preinstall plugins
# RUN /usr/local/bin/install-plugins.sh docker-slaves github-branch-source:1.8

# # pass a list of plugins to a file
# COPY plugins.txt /usr/share/jenkins/ref/plugins.txt
# RUN /usr/local/bin/install-plugins.sh < /usr/share/jenkins/ref/plugins.txt
# drop back to the regular jenkins user - good practice
USER jenkins