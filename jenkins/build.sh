#docker run --name myjenkins -p 8080:8080 -p 50001:50001 --env JENKINS_SLAVE_AGENT_PORT=50001 pabagan/jenkings
docker run -d \
  --name jibirijenkins \
  -v jenkins_home:/var/jenkins_home \
  -p 8080:8080 -p 50001:50001 \
  pabagan/jenkins