import $ from 'jquery';
import Rx from 'rxjs/Rx';

const numbers = [23, 44, 55, 66, 77];
const numbers$ = Rx.Observable.from(numbers);

numbers$.subscribe(
  v => console.log(v),
  err => console.log(err),
  complete => console.log('Completed'),
)

const postOutput = $('#posts');
const posts = [
  {title: 'Post 1', body: 'This is the post body'},
  {title: 'Post 2', body: 'This is the post body'},
  {title: 'Post 3', body: 'This is the post body'},
];

const posts$ = Rx.Observable.from(posts);

posts$.subscribe(
  post => {
    let output = '';
    output += `<h1>${post.title}</h1>`;
    output += `<p>${post.body}</p>`;

    postOutput.append(output);
  },
  err => console.log(err),
  complete => console.log('Completed'),
);

const set = new Set(['Hello', 44, {title: 'My title'}])
const set$ = Rx.Observable.fromEvent(set);


set$.subscribe(
  set => console.log(set),
  err => console.log(err),
  complete => console.log('Completed'),
);

console.log('RxJS Boiler Running...');