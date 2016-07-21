# Sass

* http://sass-lang.com/.

## Table of contents
- [Data structures](#data-structures)
  + [Lists](#lists)
  + [Maps](#maps)
- [Control structures](#control-structures)
  + [Conditionals](#conditionals)
  + [@while](#while)
  + [@for](#for)
  + [@each](#each)
- [New @at-root directive](#new-at-root-directive)
- [Debugging with: @error, @warn, and @debug](#debugging-with-error-warn-and-debug)


## Data structures
### Lists 
```sass
$list: (
  "item1", "item2", "item3", "item4"
);

$states: (
  "AL", "DC", "DE", "FL", "GA", "IA", "IL",
  "IN", "KY", "LA", "MD", "MI", "MO", "MS",
  "NC", "NJ", "NY", "OH", "PA", "SC", "TN",
  "VA", "WV"
);

// Multidimensional list
$regions: (
  ( "DC", "DE", "MD", "NJ", "NY", "PA" ),
  ( "AL", "FL", "GA", "KY", "LA", "MS", "NC", "SC", "TN", "VA", "WV" ),
  ( "IA", "IL", "IN", "MI", "MO", "OH" )
);

//  List Functions (indexes start at 1!)
length( $list )
nth( $list, $n )
join( $list1, $list2 [, $separator] )
append( $list, $val [, $separator] ) // merge 2 lists
zip($val) // append value t o the end
index( $list, $value )
```

### Maps
```sass
$social: (
  "facebook":       '\f204',
  "twitter":        '\f202',
  "linkedin":       '\f207',
  "pinterest":      '\f209',
  "github":         '\f200',
  "dribbble":       '\f201',
  "instagram":      '\f215',
  "email":          '\f410'
);

// Multidimensional map
$mega-social: (
  "facebook":       ( content: "\f204", coords: 0 0 ),
  "twitter":        ( content: "\f202", coords: 0 -64px ),
  "linkedin":       ( content: "\f207", coords: 0 -128px )
);

// Map Get
li.facebook:before {
  content: map-get($social, "facebook");
}

// Output
li.facebook:before {
  content: '\f204';
}

// Map Functions
map-merge( $map1, $map2 )
map-remove( $map, $key )
map-keys( $map )
map-values( $map )
map-has-key( $map, $key )

```

## Control structures
* @while
* @for
* @if / @else if / @else
* @each


### Conditionals

* @if / @else if / @else.
 
```sass
//
// Oneline
//
$param-2: if(length($args) > 1, nth($args, 2), ());

p {
  @if 1 + 1 == 2 { border: 1px solid;  }
  @if 5 < 3      { border: 2px dotted; }
  @if null       { border: 3px double; }
}

//
// Multiline
//
@mixin breakpoint($breakpoint) {
  @if $breakpoint == medium {
    @media only all and (min-width: 640px) {
      @content;
    }
  } @else if $breakpoint == large {
    @media only all and (min-width: 1024px) {
      @content;
    }
  }
}
// output
p {
  background-color: rgba(255, 0, 255, 0.2);

  @include breakpoint(medium) {
    background-color: rgba(255, 255, 0, 0.2);
  }
}
```

### @while
```sass
$types: 4
$type-width: 20px

@while $types > 0 {
  .while-#{$types} {
    width: $type-width + $types;
  }
  $types: $types - 1
}

// output
.while-4 {
  width: 24px;
}
.while-3 {
  width: 23px;
}
.while-2 {
  width: 22px;
}
.while-1 {
  width: 21px;
}
```


### @for
```sass
@mixin griddr($cols) {
  // do a math here
}

@for $i from 1 through $columns {
  .col-#{$i} {
    @include griddr($i);
  }
}

@for $i from 1 through 9 {
  .space-#{$i} {
    background-image: url("../images/space/space-#{$i}.jpg");
  }
}

// output
.space-1 {
  background-image: url("../images/space/space-1.jpg");
}
.space-2 {
  background-image: url("../images/space/space-2.jpg");
}

// 
// for variations
// 
@for $i from 1 through 10 {
  // includes 10
}

@for $i from 1 to 10 {
  // stops at 9
}

```


### @each

#### @each:lists
```sass
@each $state in $states {
  .#{$state} {
    background-image: url("../images/state_infographics/#{$state}.png");
  }
}

// output
.AL { background-image: url("../images/state_infographics/AL.png"); }
.DC { background-image: url("../images/state_infographics/DC.png"); }
.DE { background-image: url("../images/state_infographics/DE.png"); }
```


#### @each:maps
```sass
@each $network, $content in $social {
  .#{$network} a:before {
    content: $content;
  }
}

// output
.facebook a:before { content: '\f204'; }
.twitter a:before { content: '\f202'; }
.linkedin a:before { content: '\f207'; }
```


#### @each:multiassignment
```sass
$animals: (puma, black, default),
          (sea-slug, blue, pointer),
          (egret, white, move);

@each $animal, $color, $cursor in $animals {
  .#{$animal}-icon {
    background-image: url('/images/#{$animal}.png');
    border: 2px solid $color;
    cursor: $cursor;
  }
}
```


## New @at-root directive
Unwind nesting and insert at the highest level.

```sass
.message {
  @at-root .info { color: blue; }
  @at-root .error { color: red; }
}

// Output
.info { color: blue; }
.error { color: red; }
```


### Avoir of @media or @support blocks.

```sass
@media print {
  .page {
    width: 8in !important;
    @at-root (without: media) {
      width: 960px;
    }
  }
}

// Output
@media print {
  .page {
    width: 8in !important;
  }
}
.page {
  width: 960px;
}
```


## Debugging with: @error, @warn, and @debug
```sass
xxx {
  @error "Invalid color name: `#{$color}`.";
  @warn "The `border-radius()` mixin will be deprecated in version 2.0.";
  @debug $color-blue; // single value
}


```

## References
* http://sass-lang.com/documentation/Sass/Script/Functions.html
* http://taupecat.github.io/sass-102/ ... what a cool site!. 
* http://thesassway.com/news/sass-3-3-released
* http://sass-lang.com/documentation/file.SASS_REFERENCE.html#control_directives__expressions