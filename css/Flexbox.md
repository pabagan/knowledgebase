# Flexbox basics

* [WW3 Schools](https://www.w3schools.com/css/css3_flexbox.asp)

(prefixes done via autoprefixer, omitted in the demos)

## Flexbox properties
We operate having a container and item inside it.

```html
<div class="mv-md flex">
  <div class="flex-item">flex item 1</div>
  <div class="flex-item">flex item 2</div>
  <div class="flex-item">flex item 3</div>
</div>
```

#### Flexbox container

* **display:** the type of box.
* **flex-direction:** direction of the items inside a flex container.
* **justify-content:** horizontally aligns.
* **align-items:** vertically aligns.
* **align-content:** instead of aligning flex items, it aligns flex lines.
* **flex-wrap:** wrap or not if not enough space.
* **flex-flow:** shorthand for flex-direction and flex-wrap.

#### Flexbox item

* **order:** order of a flexible item.
* **align-self:** flex items align-items property.
* **flex:**  length of a flex item relative to the rest.

## Container properties

* **flex-direction:** `row-reverse, column, column-reverse: row-reverse;`
* **justify-content:** `stretch, flex-start, flex-end, center, space-between, space-around`
* **align-items:** `stretch, flex-start,  flex-end, center, baseline`
* **flex-wrap:** `nowrap, wrap, wrap-reverse`
* **flex-flow:** shorthand for flex-direction and flex-wrap
* **align-content:** `stretch, flex-start, flex-end, center, space-between, space-around`

```sass
.flex {
  display: flex;
  background-color: lightgrey;
  width: 400px;
  height: 250px;  
  // The align-items Property
  // stretch, flex-start,  flex-end, center, baseline
  align-items: stretch; 
  // The flex-wrap Property
  // nowrap, wrap, wrap-reverse
  flex-wrap: wrap;
  // Align content property has: 
  // stretch, flex-start, flex-end, center, space-between, space-around
  align-content: center;
  // The justify-content Property
  // stretch, flex-start, flex-end, center, space-between, space-around
  justify-content: space-around;
  // Flex Direction: 
  // row-reverse, column, column-reverse
  flex-direction: row-reverse;
  // 
  flex-flow: row-reverse wrap;

  &__item{
    background-color: cornflowerblue;
    width: 100px;
    height: 100px;
    //margin: 10px;
  }
}
```

## Item properties

* **order:** number.
* **margin: auto** absorb the extra space.
* **align-self:** `stretch, flex-start,  flex-end, center, baseline`
* **flex:** `n` size of the flex item

```sass
// centering
.flex-item{
  width: 75px;
  height: 75px;
  background-color: cornflowerblue;
  margin: auto;  
  // Order number
  order: -1;
  // stretch, flex-start,  flex-end, center, baseline
  align-self: flex-end;
  // flex number size
  flex: 2;
}
```