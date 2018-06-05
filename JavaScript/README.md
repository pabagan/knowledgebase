# Javascript

## Patterns
* https://addyosmani.com/resources/essentialjsdesignpatterns/book/
* http://shichuan.github.com/javascript-patterns

## Handlebars
```html
<!-- if -->
{{#if author}}
    <h1>{{firstName}} {{lastName}}</h1>
{{/if}}
{{else}}
    <h1>???</h1>
{{/if}}

<!-- unless -->
{{#unless license}}
    <h3>WARNING!!</h3>
{{/unless}}

<!-- each loop  -->
{{#each doe}}
<li>{{this}}</li>
{{/each}}

```




## Crear listas a partir de keys de objetos
```js
var fields = {
    id: {type: Number, max: 11},
    name: {type: String, max: 100 },
    token: {type: String, max: 32 },
    is_active: {type: Number, max: 4 },
};


var keys = Object.keys(fields);
// Array [ "id", "name", "token", "is_active" ]
```