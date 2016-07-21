# Lifecycle methods

We have 3 main states in the component's lifecycle: **initial**, **update**, **unmount**.

## Mount/Inizialization

| Method | Use |
| --- | --- | 
| `componentWillMount()` | called before mounting/render method is executed. Avoid settings prop or state here since trigger a re-rendering |
| `componentDidMount()` |  Execute after render method. Initialization that requires DOM or data fetching from network request operations |
| `render()` |   method returns the needed component markup |


## Updates

Update is caused by changes to props or state and component is re-rendered.


| Method | Use |
| --- | --- | 
| `shouldComponentUpdate(nextProps, nextState)` | called before the render and enables to define if re-rendering is needed or not (boolean must be returned) when new props or state is received. Returning False make  `componentWillUpdate()`, `render()`, and `componentDidUpdate()` will not be invoked.  | 
| `componentWillUpdate(nextProps, nextState)` | called immediately before rendering when new props or state are received. It is used to prepare an update, not called for initial `render()`.  Cannot call  `this.setState()`. If you need to update state in response to a prop change, use componentWillReceiveProps()  | 
| `render()` | --- | 
| `componentDidUpdate()` | is called after updating occurs. Used to perform DOM operations after data has been updated. | 


Updating `Props` adds a new one extra method:

| Method | Use |
| --- | --- | 
| `componentWillReceiveProps(nextProps)` | Executed before mounted component receive new props. Used when update the state in response to prop changes comparing `this.props` and `nextProps()` and perform state transitions using `this.setState()`. Is not called during mounting | 


## Unmount
Component is removed from DOM,

| Method | Use |
| --- | --- | 
| `componentWillUnmount()` | called before the component is removed from the DOM. Used to perform clean up operations, f.e. removing any timers defined in componentDidMount.


## General APIs

| Method | Use |
| --- | --- | 
| `setState()` | | 
| `forceUpdate()` | | 


## constructor()

```js
export default class DemoClass extends React.Component {
	constructor(props) {
	  	super(props);
		this.state = {
			color: props.initialColor
	  	};
	}
}
```

(*) If you "fork" props by using them for state, use componentWillReceiveProps(nextProps) 

```js
componentWillReceiveProps(nextProps) {
	this.setState({
		selectProductTypesOptions: nextProps.selectProductTypesOptions,
		selectProductTypesKeys: nextProps.selectProductTypesKeys,
		selectProductSortOptions: nextProps.selectProductSortOptions,
		selectProductSortKeys: nextProps.selectProductSortKeys,
		underlyings: nextProps.underlyings,
		currencies: nextProps.currencies,
		expirationOptions: nextProps.expirationOptions,
		expirationKeys: nextProps.expirationKeys,
		minMonth: nextProps.minMonth,
		maxMonth: nextProps.maxMonth,
		securityTypes: nextProps.securityTypes,
		marketTypes: nextProps.marketTypes,
		market: nextProps.market,
		userLanguage: nextProps.userLanguage
	}, function(){
	  	this.setMarketTypes(this.state);
	});
}
```

## setState()

Method to inform react a component, at UI, needs to be updated `setState(stateChange, [callback])`.


```js
// changes should be represented by building a new state object 
this.setState((prevState, props) => {
  return {counter: prevState.counter + props.step};
});
```

### State depending on same cycle states

Subsequent calls will override values from previous calls in the same cycle

```js
Object.assign(
  previousState,
  {quantity: state.quantity + 1},
  {quantity: state.quantity + 1},
  ...
)
```

To avoid that use:

```js
this.setState((prevState) => {
  return {counter: prevState.quantity + 1};
});
```

## forceUpdate()
If update depends on any other factor different than updating props or states can use this method: 

```js
component.forceUpdate(callback);
```

## Class Properties

### defaultProps
Set default props for a class:

```js
class CustomButton extends React.Component {
  // ...
}

CustomButton.defaultProps = {
  color: 'blue'
};
```

If props.color is not provided, it will be set by default to 'blue':

```js
render() {
	return <CustomButton /> ; // props.color will be set to blue
}
```

## Instance Properties

### Props

Properties defined by the caller of the component.

### State

Data specific to the component that may change after time. Is user defined and should be a plain JS object.