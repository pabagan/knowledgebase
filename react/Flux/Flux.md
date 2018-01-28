 # Flux Architecture

* **Actions** Add methods to pass data to de Dispatcher.
* **Dispatcher** Receive actions and emit payloads
* **Stores** Container for states and logic with the callbacks registered at the dispatcher.
* **Controller Views** Grab states from Stores and pass via props to child components.


## Scaffolding

```bash
.
├── actions
│   └── AppActions.js
├── components
│   ├── App.js
│   ├── Movie.js
│   ├── MovieResults.js
│   └── SearchForm.js
├── constants
│   └── AppConstants.js
├── dispatcher
│   └── AppDispatcher.js
├── main.js
├── stores
│   └── AppStore.js
├── utils
│   └── appAPI.js
└── vendors
    ├── bootstrap.min.js
    └── jquery-1.12.0.min.js

```


## Actions

Add methods to pass data to de Dispatcher.

Action example:

```js
var AppDispatcher = require('../dispatcher/AppDispatcher');
var AppConstants = require('../constants/AppConstants');

var AppActions = {
	searchMovies: function(movie){
		AppDispatcher.handleViewAction({
			actionType: AppConstants.SEARCH_MOVIES,
			movie: movie
		});
	},
	receiveMovieResults: function(movies){
		AppDispatcher.handleViewAction({
			actionType: AppConstants.RECEIVE_MOVIE_RESULTS,
			movies: movies
		});
	}
}

module.exports = AppActions;
```


## Dispatcher

Receive actions and emit payloads

```js
var Dispatcher = require('flux').Dispatcher;
var assign = require('object-assign');

var AppDispatcher = assign(new Dispatcher(), {
	handleViewAction: function(action){
		var payload = {
			source: 'VIEW_ACTION',
			action: action
		}
		this.dispatch(payload);
	}
});

module.exports = AppDispatcher;
```

## Stores
Container for states and logic with the callbacks registered at the dispatcher.

```js
var AppDispatcher = require('../dispatcher/AppDispatcher');
var AppConstants = require('../constants/AppConstants');
var EventEmitter = require('events').EventEmitter;
var assign = require('object-assign');
var AppAPI = require('../utils/appAPI.js');

var CHANGE_EVENT = 'change';

var _movies = [];
var _selected = '';

var AppStore = assign({}, EventEmitter.prototype, {
	setMovieResults: function(movies){
		_movies = movies;
	},
	getMovieResults: function(){
		return _movies;
	},
	emitChange: function(){
		this.emit(CHANGE_EVENT);
	},
	addChangeListener: function(callback){
		this.on('change', callback);
	},
	removeChangeListener: function(callback){
		this.removeListener('change', callback);
	}
});

AppDispatcher.register(function(payload){
	var action = payload.action;

	switch(action.actionType){
		case AppConstants.SEARCH_MOVIES:
			console.log('Searching for movie '+ action.movie.title);
			AppAPI.searchMovies(action.movie);
			AppStore.emit(CHANGE_EVENT);
			break;
		case AppConstants.RECEIVE_MOVIE_RESULTS:
			AppStore.setMovieResults(action.movies);
			AppStore.emit(CHANGE_EVENT);
			break;
	}

	return true;
});

module.exports = AppStore;
```

## Controller Views
Grab states from Stores and pass via props to child components.

```js
var React = require('react');
var AppActions = require('../actions/AppActions');
var AppStore = require('../stores/AppStore');
var SearchForm = require('./SearchForm.js');
var MovieResults = require('./MovieResults.js');

function getAppState(){
	return {
		movies: AppStore.getMovieResults()
	}
}

var App = React.createClass({
	getInitialState: function(){
		return getAppState();
	},

	componentDidMount: function(){
		AppStore.addChangeListener(this._onChange);
	},

	componentWillUnmount: function(){
		AppStore.removeChangeListener(this._onChange);
	},

	render: function(){
		console.log(this.state.movies);
		if(this.state.movies == ''){
			var movieResults = '';
		} else {
			var movieResults = <MovieResults movies={this.state.movies} />
		}

		return(
			<div>
				<SearchForm />
				{movieResults}
			</div>
		)
	},

	_onChange: function(){
		this.setState(getAppState());
	}
});

module.exports = App;
```

## Flux big picture

So react:
- Works on virtual DOM
- Is component based
- And enforce composition over inheritance (subtle difference)

Since we have composition, we have a mechanism to make the components (parent-child) discuss together. Data can propagate from parent to children or the other way back (child to parent). A two way data flow: Problem: it become very messy at some point.

So Flux enforce a one way data flow (parent to child). Other way back is done through actions (child -> parent).

Oºne super component that receives the state updates from a store to which it has registered and propagate the data update to the child through props

So thhe children send actions that will be dispatch to the stores that needs to react to such action.

When the stores receive an action it can handle it update its data, held in its local data structure (can object, list, ....), and send an event to notify the super components that have register to him that there are new data to fetch.

The super components fetch the data and call setState with the data so data it re-render all the child with the new data propagated through props.