# Continuous integration with Travis

* [Travis CI](https://travis-ci.org/).
* [Travis Docs](https://docs.travis-ci.com/).

## Travis CI configuration steps

1. Signin with Travis and give Travis permissions at Github account.
2. From Travis dashboard and activate build for the repository.
3. Configure `.travis.yml` (example from [Travis get started](https://docs.travis-ci.com/user/for-beginners)).
```yaml
language: php
php:
  - 5.5
  - 5.4
  - hhvm
script: phpunit Test.php
```
4. Each time Pushing to Github will trigger Travis build. Can see build at [Travis Dashboard](https://travis-ci.org/).


## Travis file example

#### Node

```yaml
language: node_js
node_js:
  - "iojs"
  - "6"
```

#### Ruby 
```yaml
language: ruby
rvm:
- 1.9.3
- 2.0.0
- 2.1.0
env:
  - DB=mongodb
  - DB=redis
  - DB=mysql
gemfile:
  - Gemfile
  - gemfiles/rails4.gemfile
  - gemfiles/rails31.gemfile
  - gemfiles/rails32.gemfile
```

#### Python
```yaml
language: python
python:
  - '3.5'
  - '3.4'
  - '2.7'
matrix:
  include:
    - python: '3.5'
      env: EXTRA_TESTS=true
    - python: '3.4'
      env: EXTRA_TESTS=true
```

#### Travis and Shell
To build with complicate steps.

Need a bash file in the project. We assume this is at `/project/run-test.sh`.

```bash
#!/bin/bash
set -ev
bundle exec rake:units
if [ "${TRAVIS_PULL_REQUEST}" = "false" ]; then
    bundle exec rake test:integration
fi
```

Now Travis need to call bash file.
```yaml
// ...
  script: ./scripts/run-tests.sh
// ...
```


## With Docker

See Docs [here](https://docs.travis-ci.com/user/docker/).

```yaml
sudo: required

language: ruby

services:
  - docker

before_install:
  - docker pull carlad/sinatra
  - docker run -d -p 127.0.0.1:80:4567 carlad/sinatra /bin/sh -c "cd /root/sinatra; bundle exec foreman start;"
  - docker ps -a
  - docker run carlad/sinatra /bin/sh -c "cd /root/sinatra; bundle exec rake test"

script:
  - bundle exec rake test
```

### Adding Docker image from repo to Travis

```yaml
sudo: required

language: ruby

services:
  - docker

before_install:
  - docker build -t carlad/sinatra .
  - docker run -d -p 127.0.0.1:80:4567 carlad/sinatra /bin/sh -c "cd /root/sinatra; bundle exec foreman start;"
  - docker ps -a
  - docker run carlad/sinatra /bin/sh -c "cd /root/sinatra; bundle exec rake test"

script:
  - bundle exec rake test
```

### Docker Compose

```yaml
env:
  DOCKER_COMPOSE_VERSION: 1.4.2

before_install:
  - sudo rm /usr/local/bin/docker-compose
  - curl -L https://github.com/docker/compose/releases/download/${DOCKER_COMPOSE_VERSION}/docker-compose-`uname -s`-`uname -m` > docker-compose
  - chmod +x docker-compose
  - sudo mv docker-compose /usr/local/bin
```

## GUI and Headless Browser Testing
[See Docs](https://docs.travis-ci.com/user/gui-and-headless-browsers/).

### With Phantom
```yaml
//...
script: phantomjs testrunner.js
//...
```

## Cron Jobs
[See Docs](https://docs.travis-ci.com/user/cron-jobs/).

## Integrations

### Sonarqube
[See docs](https://docs.travis-ci.com/user/sonarqube/).
