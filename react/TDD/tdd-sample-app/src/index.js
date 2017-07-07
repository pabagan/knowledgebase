import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';

import {
    App
} from './components/index.js';

import registerServiceWorker from './registerServiceWorker';

ReactDOM.render(<App />, document.getElementById('root'));
registerServiceWorker();
