# SMACSS - Scalable and Modular CSS

The four types of CSS categories are:
- [Base rules](#base-rules)
- [Layout rules](#layout-rules)
- [Component/Module rules](#componentmodule-rules)
- [State rules](#state-rules)


## Base rules
Applied to single element selectors.

**name convention:** no prexied since applied to html elements.

It doesn't include any class or ID selectors:
```css
body, form {
    margin: 0;
    padding: 0;
}
a {
    color: #039;
}
a:hover {
    color: #03F;
}
```

## Layout rules
Create sections of content. There is a distinction between layouts major/minor components of a page.

**name convention:** `l-`, `layout-`.

* **major components:** header, footer. Styled using ID selectors.
```css
#header, #article, #footer {
    /* ... */
}
#article {
    /* ... */
}
```
* **minor components:** login form, navigation item. They are **modules**.

When a **layout needs to respond to different factors** are used in combination with other Layout styles.
```css
#article { float: left }
#sidebar { float: right }

.l-flipped #article {
    float: right;
}
.l-flipped #sidebar {
    float: left;
}
```

Adding `.l-flipped` class to higher level element ex: `body` allow change sidebar from the right to the left.


### ID Selector
ID selectors aren't necessary: 
* Almost same performance between ID and class selectors
* make styling more complicated due to increasing specificity.

```html
<div>
    <h2>Featured</h2>
    <ul>
        <li><a href="...">...</a></li>
        ...
    </ul>
</div>
```

CSS options
```css
/* Worse aproach: apply id list */
div#featured ul {
    margin: 0;
    padding: 0;
    list-style-type: none;
}
div#featured li {
    float: left;
    height: 100px;
    margin-left: 10px;
}

/* Better aproach: create a class (reusable!) layout applicable to: OL or UL. */
.l-grid {
    margin: 0;
    padding: 0;
    list-style-type: none;
}
.l-grid > li {
    display: inline-block;
    margin: 0 0 10px 10px;
    /* IE7 hack to mimic inline-block */
    *display: inline;
    *zoom: 1;
}
```

Pros: 

1. reusability.
2. reduced the specificity.
3. decreased the depth of applicability by 1.
4. flexible height.

Cons

1. locked out IE6 (bye!).
2. higher class size and complexity.

## Component/Module rules
Reusable, modular parts of our design: navigation bars, sliders, widgets, ..

**name convention:**  use the name of the module itself. Avoid #id and element selectors.

Modules that are a **variation on another module** should also use the
base module name as a prefix and subclassing.

```css
/* Avoid element selector */
.module > h2 {
    padding: 5px;
}
.module span {
    padding: 5px;
}
```
```html
<!-- worse aproach -->
<div class="fld">
    <span>Folder Name</span>
    <span>(32 items)</span>
</div>

<!-- better aproach -->
<div class="fld">
    <span class="fld-name">Folder Name</span>
    <span class="fld-items">(32 items)</span>
</div>
```

### Subclassing Modules
First instinct is to use a parent element: 
```css
.pod {
    width: 100%;
}
.pod input[type=text] {
    width: 50%;
}
#sidebar .pod input[type=text] {
    width: 100%;
}
/*
... specificity issues, require adding even more selectors against it, need of !important.
*/
```

Now we need 2 sized input widths taht has a label beside it. Field should only be half the width. In the sideba will increase 100% and the label on top. All good!

Now, we need new component with same styling as a `.pod` and so we re-use it. However, has special constrained width (180px) no matter the where: ¡battling against specificity!
```css
.pod {
    width: 100%;
}
.pod input[type=text] {
    width: 50%;
}
#sidebar .pod input[type=text] {
    width: 100%;
}
.pod-callout {
    width: 200px;
}
#sidebar .pod-callout input[type=text],
.pod-callout input[type=text] {
    width: 180px;
}

/* We're doubling up on our selectors to be able to override the specificity
of #sidebar. */
```

We should create a constrained layout subclass of the pod and style it accordingly. Battling against specificity.

```css
.pod {
    width: 100%;
}
.pod input[type=text] {
    width: 50%;
}
.pod-constrained input[type=text] {
    width: 100%;
}
.pod-callout {
    width: 200px;
}
.pod-callout input[type=text] {
    width: 180px;
}
```

Try to **avoid conditional styling based on location** to rise usage elsewhere on the page or site. Sub-class the module instead.


## State rules
Module & layouts look when in a particular state override all other styles.

**name convention:** `is-`

```css
/* Example Module */
.example { }
/* Callout Module */
.callout { }
/* Callout Module with State */
.callout.is-collapsed { }
/* Form field module */
.field { }
/* Inline layout */
.l-inline { }
```

States are generally applied to the same element as a layout/module rule as a base module class.
```html
<div id="header" class="s-collapsed">
    <form>
        <div class="msg s-error">
            There is an error!
        </div>
        <label for="searchbox" class="s-hidden">
            Search
        </label>
        <input type="search" id="searchbox">
    </form>
</div>
```

The use of `!important` is allowed (if trully needed) and usefull in complex systems. You won't normally have two states applied to the same module

### complication when inheritance
We have calendar: 
```html
<table class="cal">
    <tr>
        <td>1</td>
        <td>2</td>
        <td>3</td>
        <td>4</td>
        <td>5</td>
        <td>6</td>
        <td>7</td>
    </tr>
<!-- repeated 3-4 times -->
</table>
```

Each cell is a single day. The default style:
```css
.cal td {
    background-color: #EFEFEF;
    color: #333;
}
```

To highlight today:
```css
.cal td.cal-today {
    background-color: #F33;
    color: #000;
}
```

The cal-today shows as a part of the cal module. We're also increasing the specificity overriding default style.
Alternative `td.cal-today` if declared after the default state rule.  Hadding just `.cal-today` resort to using !important.

Until here all is fine! 

Now, we need to show selected week:
```html
<tr class="is-selected">
    <td>1</td>
    <td class="cal-today">2</td>
    <td>3</td>
    ...
</tr>
```

The selected state is being used throughout the application and so it
only made sense to use it here:
```css
.is-selected {
    background-color: #FFD700; /* Yellow */
    color: #000;
}

/* blackground is not applied since .cal td  has greater specificiy */
```

Use of `!important` since **does not override inheritance, just specificity**.
Need to create new rules to reflected state:
```css
.is-selected td {
    background-color: #FFD700; /* Yellow */
    color: #000;
}
/* If this selector is defined after our calendar day selectors should render just as we'd expect.*/
```

### Where !important can go wrong
```css
.is-selected td {
    background-color: #FFD700 !important; /* Yellow */
    color: #000 !important;
}
/* ... suddenly our today cell would no longer show today */
```

To fix that create a new rule that **combines the state/module rule**: 
```css
.is-selected td {
    background-color: #FFD700 !important; /* Yellow */
    color: #000 !important;
}
.is-selected td.cal-today {
    background-color: #F33 !important;
    color: #000 !important;
}
/* ...this  add more !important to keep things working correctly. Not ideal! */
```

## Credit
This is a working version resume from [Scalable and Modular Architecture for CSS - Jonathan Snook](https://smacss.com/). Big hug from here Jonathan!