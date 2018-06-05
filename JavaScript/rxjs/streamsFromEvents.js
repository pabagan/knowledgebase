import $ from 'jquery';
import Rx from 'rxjs/Rx';

const output = $('#output');

//
// Button
//
const btn = $('#btn');
const btnStream$ = Rx.Observable.fromEvent(btn, 'click');

btnStream$.subscribe(
  (event) => console.log('Clicked!', event),
  (error) => console.log(error),
  () => console.log('Completed'),
);

//
// Input event
//
const input = $('#input');
const inputStream$ = Rx.Observable.fromEvent(input, 'keyup')  ;

inputStream$.subscribe(
  (event) => {
    console.log(event.target.value)
    output.html(event.target.value)
  },
  (error) => console.log(error),
  () => console.log('Completed'),
);

//
// Mouse event
//
const mouseStream$ = Rx.Observable.fromEvent(document, 'mousemove')  ;

mouseStream$.subscribe(
  (event) => {
    console.log(event.target.value)
    output.html(`X: ${event.clientX}, y: ${event.clientY}`)
  },
  (error) => console.log(error),
  () => console.log('Completed'),
);

console.log('RxJS Boiler Running...');