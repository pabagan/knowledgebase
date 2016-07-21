## Python 101s

* [Python](https://www.python.org)
* [PEP 8 -- Style Guide for Python Code](https://www.python.org/dev/peps/pep-0008/#function-names)

## Table of contents
- [Variables, expresions y statements](#variables-expresions-y-statements)
- [Strings](#strings)
- [Conditionals](#conditionals)
- [Functions](#functions)
- [Loops](#loops)
- [Lists](#lists)
- [Dictionaries](#dictionaries)
- [Tuples](#tuples)
- [Exceptions](#exceptions)
- [Objects](#objects)
- [Files](#files)

## Variables, expresions y statements

```py
# execute python file from terminal
python 'nombre de archivo'
```

### Variables
Empieza por letra o _ y respetando palabras reservadas.

```py
x = 12.2
```

### Numeric Expressions
+ Addition
- Subtraction
* Multiplication
/ Division
** Power
% Remainder

### Types

```py
type(xxx) -> devuelve el typo

# Conversions
```py
i = 42
type(i)
f = float(i)
42.0
```


## Strings

```py
sval = '123'
type(sval)

ival = int(sval)
type ival

# Read User
nam = raw_input('Who are you?')
print 'Welcome',nam

# Converting user input
inp = raw_input('Europe floor?')
usf = int(inp) + 1
print "US floor", usf

# + -> Concatenate
# * -> Multipier (strings to)
'Hi' * 3 = HiHiHi

# Access
fruit = 'banana'
letter = fruit[1]
print letter
>>> a

n = 3
letter = fruit[n - 1]
print letter
>>> n

# len
fruit = 'banana'
x = len(fruit)
print x
>>> 6

# loop string
fruit = 'banana'
i = 0
x = len(fruit)
```


### Slicing string

```py
s= 'Monty Python'
print s[0:4]
>>> Mont

print s[8:]
>>> thon

print s[:]
>>> Monty Python
```

(*) second number of the slice is not included

### String concatenation 
```py
a = 'Hello'
b = a + 'There'

print b
>>> HelloThere

a = 'Hello'
b = a + ' ' + 'There'

print b
>>> Hello There
```


### String comparasion

```py
if word == 'banana':
    print 'All right, bananas.'

if word < 'banana':
    print 'Your word,' + word + ', comes before banana.' 
elif word > 'banana':
    print 'Your word,' + word + ', comes after banana.'
else:
    print 'All right, bananas.'
```


### String Operations

```py
# lower()
greet = 'Hello Bob'
zap = greet.lower()
print zap
>>> hello bob

print 'Hi there'.lower()
>>> he there

# dir() - sirve para preguntar a Python qué podemos hacer con determinado tipo
e.x. string
stuff = "Hello world"
type(stuff)
dir(stuff)
>>> ['capitalize', 'center', 'count', 'decode',...]

# find()
fruit = 'banana'
pos = fruit.find('na')
print pos
>>> 2

if not find return 
>>> -1

# find x starting at
find('search string', 'starting at')

# replace()
greet = 'Hello Bob'
nstr = greet.replace('Bob', 'Jane')
print nstr
>> Hello Jane

# stripping
greet = ' Hello Bob '
greet.lstrip()
>>> 'Hello Bob '
greet.rstrip()
>>> ' Hello Bob'
greet.strip()
>>> 'Hello Bob'

# prefixes
line = 'Please have a nice day'
line.startswith('Please')
>>> True
line.startswith('p')
>>> False


# parsing text
ex. find host
data = 'Hola este es un mensaje de pabagan@gmail.com escrito desde...'

atpos = data.find('@')
print atpos

sppos = data.find(' ', atpos)
print sppos

host = data[atpos+1 : sppos]

print host


# find number
text = "X-DSPAM-Confidence:    0.8475";

atpos = text.find(':')
print float(text[atpos+1:])
```


## Conditionals

### Operators

```py
< 
<=
==
>=
>
!=
```

### if
```py
X = 5
if x > 10:
    print 'Smaller'

print 'Finis'
```

### One line
```py
if x > 20: print 'Bigger'
```

### if / else
```py
if x > 20:
    print 'Bigger'
else:
    print 'Smaller'
print 'All done'
```
    
### if / else / elif
```py
if x < 2:
    print 'small'
elif x < 4:
    print 'Medium'
elif x < 6:
    print 'large'
elif x < 8:
    print 'extra large'
print 'All done'
```

### is / is not
```py
smallest = None
print 'Before'
for value in [5,4,3,2,1]:
    if smallest is None
        smallest = value
    elif value < smallest:
        smallest = value
    print smallest, value
print 'after smallest'
```


## Functions

```py
# Define
def hello():
    print 'Hello'
    print 'Fun'

# Use
hello()

# With arguments
def hello(x):
    print x;
```

### Built in

* [Built in Python 2](https://docs.python.org/2/library/functions.html)
* [Built in Python 3](https://docs.python.org/3/library/functions.html)


## Loops

### While

```py
n = 5
while n > 0:
    print n
    n = n -1
print "Sacabó"
print n

# loop controllers
break
continue
```

### For
```py
# ex 1 
for i in [5,4,3,2,1]:
    print i


# ex 2 
friends = ['Jose', 'Chucha', 'Petuso']

for friend in friends : 
    print 'Hello ', friend

print 'Done'
```

## Lists
```py
friends = ['Joseph', 'Glenn', 'Sally']
carryon = [ 'socks', 'shirt', 'perfume' ]
elems = [ 'socks', 24.77, 'perfume' ]
elems = [ 'socks', ['Joseph', 'Glenn', 'Sally'], 24.77, 'perfume' ]

# Accessing
print elems[1]

# list are mutable
elems[2] = 77
>>> [1,77,3,4]

# count
print len(elems)

# range - create new list
print range(4)

# array
elems = ['Joseph', 'Glenn', 'Sally']
print 'List:',elems

# array len
elems_len = len(elems)

# convert to numeric
elems_numeric = range(elems_len)
print 'Numerico:',elems_numeric

# Concatenate
friends = ['Joseph', 'Glenn', 'Sally']
carryon = [ 'socks', 'shirt', 'perfume' ]
print friends + carryon
```


### List slice

```py
l = [9,41,12,3,74,15]
print l[1:3]
>>> [41:12]

print s[1:]
>>> [41,12,3,74,15]

print s[:]
>>> [9,41,12,3,74,15]
```

### Loop list
```py
friends = ['Joseph', 'Glenn', 'Sally']

for friend in friends: 
    print 'Zurmano', friend
```


### Better perform loop
```py
for i in range(len(friends)):
    friend = friends[i]
    print 'Zurmano', friend
```


### List methods

```py
x = list()
type(x)
dir(x) # devuelve los metodos que se le pueden aplicar

    # methor append
    l = [9,41,12,3,74,15]
    l.append(77)
    l.append(90)
    print l
```

### List item check

```py
l = [9,41,12,3,74,15]

9 in l
True
16 in l
False

16 not in list
True
```


### List Ordering
```py
l = [9,41,12,3,74,15]
l.sort()
>>> [3,9,12,15,41,74]
```

### min / max /  sum /  average

```py
l = [9,41,12,3,74,15]
print l
print 'Size',len(l)
print 'Max', max(l)
print 'Min', min(l)
print 'Sum', sum(l)
print 'Average', sum(l)/len(l)
```


### List from string 

```py
abc = 'With three words'

stuff = abc.split()
print stuff;
print stuff[0];

abc = 'With,three,words'
stuff = abc.split(',')
print stuff;

abc = 'With;three;words'
stuff = abc.split(';')
print stuff;
```


## Dictionaries
Key/value pairs.

```py
purse = dict()
purse = {} # también sirve para crear diccitionaries
purse['money'] = 12
purse['candy'] = 3
purse['tisues'] =75

print purse
{'money': 12,'tisues': 75, 'candy': 3}

# Change value
purse['candy'] = purse['candy'] + 2
print purse
{'money': 12,'tisues': 75, 'candy': 5}

print purse['candy']
>>> 5

# Create value

purse['fruit'] = 100
print purse
{'money': 12,'tisues': 75, 'candy': 5,'fruit':100}

# Check value

print 'tisszzies' in ccc
>>> False

# get(key,val) comprueba la key y si no existe devuelve val -> dict.get(value,0)
print counts.get(name,0)
```

### ex Counting

```py
counts = dict()
names = ['csev','cwen','csev','zqjan','cwen']

for name in names:
    if name not in counts:
        counts[name] = 1
    else:
        counts[name] = counts[name] + 1
print counts


### Counting get()
counts = dict()
names = ['csev','cwen','csev','zqjan','cwen']

for name in names:
    counts[name] = counts.get(name,0) + 1
print counts
```

### loop
```py

# 1 variable
counts = {'money': 12,'tisues': 75, 'candy': 5,'fruit':100}

for key in counts:
    print key, counts[key]


# 2 variables
counts = {'money': 12,'tisues': 75, 'candy': 5,'fruit':100}

for key,value in counts.items():
    print key, value
```

### Dictionaries to list -converts

```py
jjj = {'money': 12,'tisues': 75, 'candy': 5}

print list(jjj)
>>> ['money', 'tisues', 'candy']

print jjj.keys()
>>> ['money', 'tisues', 'candy']

print jjj.values()
>>> [12,75,5]

print jjj.items();
[('money',12), ('tisues',75), ('candy',5)]
```


## Tuples

List and are witten with `()` are more eficient because of inmutable property.

```py
x = ('Glen', 'Sally', 'Joseph')
print x[2]
>>> Joseph

# ver qué podemos hacer con Tuples
x = tupple()
dir(x)

x = (3,2,1,)

# asignment
(x,y) = (4,'fred')
print y
    
# tuple assignment no parenthesis
x,y = (4,'fred')
print x
>>> 4
```

### Tuples and dictionaries
The items() method in dictionaries returns a list of (key,value) tuples. Ex:

```py
d = dict()
d['csev'] = 2
d['cwen'] = 4

for (k,v) in d.items():
    print k,v
>>> csev 2
>>> cwen 4

tups = d.items()
print tups
>>> [('csev',2),('cwen',4)]
```

### Tuples compare
```py
(0,1,2) < (5,1,2)
>>> True
```

### Tuples sort
```py
c = {'a':10,'b':1,'c':22}
tupple = c.items()
print tupple
>>> [('a',10),('c',22),('b',1)]

tupple.sort()

print tupple
>>> [('a',10),('b',1),('c',22)]


# sorted() - hace lo mismo que el anterior pero elimina un paso (*) +clever!

c = {'a':10,'b':1,'c':22}
tupple = sorted(c.items())
print tupple
>>> [('a',10),('b',1),('c',22)]
```


#### sort() - by values
```py
c = {'a':10,'b':1,'c':22}
tmp = list()

# hago for añadiendo a la lista v,k en vez de k,v
for k, v in c.items() :
    tmp.append( (v,k) )

print tmp
>>> [(10,'a'),(22,'c'),(1,'b')]

print tmp.sort(reverse=True) # reverse=True say highest to smallest
>>> [(22,'c'),(10,'a'),(1,'b')]

    # sorted() - hace lo mismo que el anterior pero elimina un paso (*) +clever!
    
    c = {'a':10,'b':1,'c':22}
    print sorted( [ (v, k) for k,v in c.items() ] ) # creating a list from tupple v,f and a for to reverse
    print tupple
    >>> [(1,'b'), (10,'a'), (22,'c')]

    # Tuples may be nested:
    u = t, (1, 2, 3, 4, 5)
```

### Tuples nesting:
```py
u = t, (1, 2, 3, 4, 5)
```


## Exceptions

```py
#!/usr/bin/env python
##*- coding: utf-8 -*-
rawstr = raw_input('Introduce un número mayor que 0: ');

try:
    istr = int(rawstr)
    
    if istr > 5:
        output = 'Qué máquina eres!!'
    else:
        output = '¡¡Te dije mayor que 0 truán!!'
        
except:
    output = 'Fuera de rango!!'

print output
```


## Objects

```py
class Character:
    # constructor
    # ::= __init__(self, arg1, arg2, ...)
    def __init__(self, name, initial_health): 
        # init variable referenced in the same object and a field
        # ::= self.arg
        self.name = name
        self.health = initial_health
        # create no related argument field
        self.inventory = []
        
    # to string 
    # ::= __str__(self)
    def __str__(self):
        s  = "Name: " + self.name
        s += " Health: " + str(self.health)
        s += " Inventory: " + str(self.inventory)
        return s
    

    # behaviors
    # a program can add infinite behaviours inside the object
    # ::= name(self, arg1, arg2, ...)
    def grab(self, item):

        self.inventory.append(item)
        
    def get_health(self):
        return self.health

# 
# Using objects
# 
def example():

    # inicio el objeto pasando la clase
    # ::= varname = ClassName(args)
    # args ::= Class -> def __init__
    me = Character("Bob", 20)
    print str(me)

    # add to a list
    me.grab("pencil")
    me.grab("paper")
    print str(me)
    print "Health:", me.get_health()
    print str(me)

example()
```

### Inheritance
```py
class Parent_class(Child_class):
```

## Files

### ejemplo contar lineas
```py
fhand = open('files/text.txt')
count = 0
for line in fhand:
    count = count + 1
print 'Hay ' + str(count) + ' lineas en el documento'
```

### read whole files
```py
fhand = open('files/text.txt')
inp = fhand.read()
print len(inp)
```

### skipping with continue
```py
fhand = open('files/text.txt')
for line in fhand:
    line = line.rstrip()

    # skip uninteresting lines
    if not line.startswith('From:'):
        continue
    # process interesting
    print line

# Prompt for a file
fname = raw_input('Enter file name:')
try:
    fhand = open(fname)    
except:
    print 'hey, eso no tiene pinta de nombre de archivo.'
    exit()

for line in fhand:
    line = line.rstrip()

    # skip uninteresting lines
    if not line.startswith('From:'):
        continue
    # process interesting
    print line
```
