# Create node app
[Quickstart](`https://console.cloud.google.com/appengine?project=klikkie-test&folder&organizationId=397235973324&tutorial=nodejs_mvms_quickstart')


## Demo App and config
```sh
# Clone Repo from gCloud console
TUTORIALDIR=src/klikkie-test/hello-world-node
git clone https://github.com/GoogleCloudPlatform/nodejs-getting-started.git $TUTORIALDIR
```

node `app.js`:
```js
'use strict';
const express = require('express');
const app = express();
// [START hello_world]
// Say hello!
app.get('/', (req, res) => {
  res.status(200).send('Hello, world!');
});
// [END hello_world]
if (module === require.main) {
  // [START server]
  // Start the server
  const server = app.listen(process.env.PORT || 8080, () => {
    const port = server.address().port;
    console.log(`App listening on port ${port}`);
  });
  // [END server]
}
module.exports = app;
```

node `app.yaml`
```yaml
#       Copyright 2018, Google LLC.
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#    http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#
# [START runtime]
runtime: nodejs8
# [END runtime]
```

## Run
```sh
npm intall && npm start
```

## Deploy
```sh
gcloud app deploy --project klikkie-test
```