# Redux Advanced

## Async Actions
Async actions has a gap between invocation and responde (timeout) and this reflects in the APP state. Usually, for any API request you'll want to dispatch at least three different kinds of actions:

1. An action informing the reducers that the request began.
His way the UI knows it's time to show a spinner.
2. An action informing the reducers that the request finished successfully.
Merging the new data into the state they manage and resetting isFetching.
3. An action informing the reducers that the request failed.
Reducers may handle this action by resetting isFetching. Additionally, may want to store the error message so the UI can display it.


You may use a dedicated status field in your actions:

```js
{ type: 'FETCH_POSTS' }
{ type: 'FETCH_POSTS', status: 'error', error: 'Oops' }
{ type: 'FETCH_POSTS', status: 'success', response: { ... } }
```

Or you can define separate types for them:

```js
{ type: 'FETCH_POSTS_REQUEST' }
{ type: 'FETCH_POSTS_FAILURE', error: 'Oops' }
{ type: 'FETCH_POSTS_SUCCESS', response: { ... } }
```

Is a convention issue to decide where to stick into.. I would preffer the separate type.


## Credits

* [Redux Docs](http://redux.js.org/docs/introduction/).
