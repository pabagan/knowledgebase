# Ruby Sheet
Example code gracefully provided at Coursera's [Ruby on Rails Introduction - Johns Hopkins University](https://www.coursera.org/learn/ruby-on-rails-intro/) course.

## Table of content
- [Ruby Basics](#ruby-basics)
- [Control Flow](#control-flow)
  - [True/false](#truefalse)
  - [Triple Equals](#triple-equals)
  - [if/elsif/else and unless](#ifelsifelse-and-unless)
  - [Case Expresions](#case-expresions)
  - [While/until](#whileuntil)
  - [For](#for)
  - [Each](#each)
  - [Modifiers](#modifiers)
- [Functions and methods](#functions-and-methods)
  - [Parens](#parens)
  - [Default args](#default-args)
  - [Return optional](#return-optional)
  - [Prefixes params](#prefixes-params)
  - [Expressive methods](#expressive-methods)
- [Blocks](#blocks-pass-code-to-methods)
  - [Times](#times)
  - [Explicit block](#explicit-block)
  - [Implicit block](#implicit-block)
- [Files and environment variables](#files-and-evironment-variables)
  - [Environment Variables](#environment-variables)
  - [Read file handling exceptions](#read-file-handling-exceptions)
  - [Write to file](#write-to-file)
- [Strings](#strings)
  - [More strings](#more-strings)
- [Arrays](#arrays)
  - [Array processing](#array-processing)
  - [Arrays 1](#arrays-1)
  - [Arrays 2](#arrays-2)
- [Ranges](#ranges)
- [Hashes](#hashes)
  - [Hashes 1](#hashes-1)
  - [More hashes](#more-hashes)
  - [Block and hash confusion](#block-and-hash-confusion)
  - [Word Frequency example](#word-frequency-example)
- [Classes](#classes)
  - [Instance variables - getters/setters](#instance-variables---getterssetters)
  - [Attr Accessor](#attr-accessor)
  - [Self](#self)
- [Methods and Variables](#methods-and-variables)
  - [Double pipe](#double-pipe)
  - [Inheritance](#inheritance)
- [Modules](#modules)
  - [Namespaces](#namespaces)
  - [Mixins](#mixins)
  - [Enumerable Example](#enumerable-example)
- [Scope](#scope)
  - [Variables](#variables)
  - [Constants](#constants)
  - [Block scope](#block-scope)
  - [Block local](#block-local)
- [Access Control: public, protectec, private](#access-control-public-protectec-private)
  - [Encapsulation](#encapsulation)
  - [Specify Access](#specify-access)
  - [Private Access](#private-access)
- [Unit Testing](#unit-testing)
- [Dinamic Dispatch](#dinamic-dispatch)
- [RSpec](#rspec)
- [Style Guides](#style-guides)
- [Method Documentation](#method-documentation)



## Ruby Basics
```ruby
# Constants (upper)
CONSTANT = 77
PI = 3.1415926535

# Variables
camelCaseVar = "Ozu"

# Compile from terminal
$ ruby rubyFile.rb

# Print 
puts "Hello World"
p "Got it" # => Got it

# Init irb ruby shell
$ irb
$ "hello".methods.grep /case/

## Sintax
# ruby                          # Comments with #
# varsWithCamelCase             # variables
# ClassesWithFirstCapCamelCase  # Clases
```


##Control Flow

###True/false
```ruby
puts "0 is true" if 0                     # => 0 is true 
puts "false is true?" if "false"          # => false is true? 
puts "no way - false is false" if false   # => NOTHING PRINTED
puts "empty string is true" if ""         # => empty string is true 
puts "nil is true?" if "nil"              # => nil is true? 
puts "no way - nil is false" if nil       # => NOTHING PRINTED
```

###Triple Equals
```ruby
if /sera/ === "coursera"
  puts "Triple Equals"
end

# => Triple Equals
if "coursera" === "coursera"
  puts "also works"
end
# => also works

if Integer === 21
  puts "21 is an Integer"
end
# => 21 is an Integer
```

###if/elsif/else and unless
```ruby
#if/elsif/else
if "Wassup" == "Wassup" # => true
  # case ...
elsif "Wassup" == ""
  # case ...
else
  # case ...
end

# Unless
a = 7
unless a == 6
  puts "a is not 6"
end
```

### One line 
```ruby
# avoid 
if condition 
  task
end
# do
do_something if some_condition
# One line conditionals
var = condition ? true : false
```

###Case Expresions
```ruby
# Way 1
age = 21
case # 1ST FLAVOR
  when age >= 21
    puts "You can buy a drink" 
  when 1 == 0 
    puts "Written by a drunk programmer"  
  else 
    puts "Default condition"
end 
# => You can buy a drink

# Way 2
name = 'Fisher' 
case name # 2nd FLAVOR
  when /fish/i then puts "Something is fishy here"
  when 'Smith' then puts "Your name is Smith"
end
#=> Something is fishy here
```

###While/until
```ruby
# while
a = 10
while a > 9
  puts a -= 1
  # same as a = a - 1
end

# until
a = 9
until a >= 10
  puts a
  a += 1
end
```

###For
```ruby
# for is not too used in Ruby
for i in 0..2 # tipo rango from 0 to 2
  puts i 
end 
# => 0 
# => 1 
# => 2
```

###Each
```ruby
colors = ["blue", "green", "reed", "yellow"]
colors.each do |c| puts c end
```

###Modifiers
```ruby
a = 5
b = 0

puts "One liner" if a == 5 and b == 0
# => One liner

times_2 = 2
times_2 *= 2 while times_2 < 100
puts times_2 # => ?
```


## Functions and methods
All functions and method in Ruby inherits from a class like JavaScript.

###Parens
```ruby
# are optional
def simple
  puts "no parens"
end

def simple1()
  puts "yes parens"
end

simple() # => no parens
simple # => no parens
simple1 # => yes parens
```

###Default args
```ruby
def factorial (n) 
  n == 0? 1 : n * factorial(n - 1) 
end 

def factorial_with_default (n = 5) 
   n == 0? 1 : n * factorial_with_default(n - 1) 
end 

puts factorial 5 # => 120
puts factorial_with_default # => 120 
puts factorial_with_default(3) # => 6
```

###Return optional
```ruby
def add(one, two)
  one + two
end

def divide(one, two)
  return "I don't think so" if two == 0
  one / two
end

puts add(2, 2) # => 4
puts divide(2, 0) # => I don't think so 
puts divide(12, 4) # => 3
```

###Prefixes params
```ruby
def max(one_param, *numbers, another)
  # Variable length parameters passed in 
  # become an array
  numbers.max
end

puts max("something", 7, 32, -4, "more") # => 32
```

###Expressive methods
```ruby
# Method with ?
def can_divide_by?(number)
  return false if number.zero?
  true
end

puts can_divide_by? 3 # => true
puts can_divide_by? 0 # => false
```


##Blocks
Used to pass code to methods.
###Times
```ruby
1.times { puts "Hello World!" }   
# => Hello World! 

# |index| is the parameter
2.times do |index| 
  if index > 0 
    puts index 
  end 
end 
# => 1

# oneline way
2.times { |index| puts index if index > 0 }
# => 1
```

###Explicit block
Need to pass `&i_am_a_block`.
```ruby
def two_times_explicit (&i_am_a_block) 
  return "No block" if i_am_a_block.nil? 
  i_am_a_block.call # call 
  i_am_a_block.call 
end 

puts two_times_explicit # => No block 
two_times_explicit { puts "Hello"} # => Hello 
                                   # => Hello 
```

###Implicit block
Call a certain method if exists.
```ruby
def two_times_implicit 
  return "No block" unless block_given? # call the method if exist
  yield # yield call the method
  yield # yield call the method
end 

puts two_times_implicit { print "Hello "} 
# => Hello 
# => Hello 
puts two_times_implicit
# => No block
```


##Files and environment variables

###Environment Variables
```ruby
puts ENV["EDITOR"]
# => subl
```

###Read file
```ruby
if File.exist? 'test.txt' # checking exist
  File.foreach( 'test.txt' ) do |line|
    puts line
    p line
    p line.chomp # chops off newline character
    p line.split # array of words in line
  end
end
```

###Read file handling exceptions
```ruby
begin
  File.foreach( 'do_not_exist.txt' ) do |line|   
    puts line.chomp 
  end

rescue Exception => e
  puts e.message
  puts "Let's pretend this didn't happen..."
end
```

###Write to file
```ruby
File.open("test1.txt", "w") do |file|  
  file.puts "One line"
  file.puts "Another" 
end  
```


##Strings
* [Ruby strings](http://ruby-doc.org/core-2.3.0/String.html).

```ruby
single_quoted = 'ice cream \n followed by it\'s a party!''
double_quoted = "ice cream \n followed by it\'s a party!" 

puts single_quoted # => ice cream \n followed by it's a party!
puts double_quoted # => ice cream 
                   # =>   followed by it's a party! 

def multiply (one, two) 
  "#{one} multiplied by #{two} equals #{one * two}" 
end 
puts multiply(5, 3) 
# => 5 multiplied by 3 equals 15

 # Simbols (optimized strings)
 # must be unique and inmutable
 :foo-

# Sustitutions
string = "Rosae"
string.gsub("ae", "ä")
string.length # => 5

```
### Concatenate
```ruby
# double ""
name = "Bozhidar"
# Avoid '' + 
email_with_name = "#{user.name} <#{user.email}>"

# Avoid using String +
html = ""
html << "<h1>Page title</h1>"
```

###More strings
```ruby
my_name = " tim" 
puts my_name.lstrip.capitalize # => Tim 
p my_name # => " tim" 
my_name.lstrip! # (destructive) removes the leading space 
my_name[0] = 'K' # replace the fist character 
puts my_name # => Kim 

# %Q is Quote allow multiple iterables 
# lines
cur_weather = %Q{It's a hot day outside 
           Grab your umbrellas…} 

cur_weather.lines do |line| 
  line.sub! 'hot', 'rainy' # substitute 'hot' with 'rainy' 
  puts "#{line.strip}" 
end 
# => It's a rainy day outside 
# => Grab your umbrellas...
```


##Arrays
* [Ruby Arrays](http://ruby-doc.org/core-2.2.0/Array.html).
```ruby
# Ruby array
array = ["Ruby", "on", "Rails", 100]
array.size # => 4
# Access --> array[position]  
array[2] # => "Rails"
# Delete
array.delete_at(2) # array = ["Ruby", "on", 100]
```

###Array processing
```ruby
a = [1, 3, 4, 7, 8, 10] 
a.each { |num| print num } # => 1347810 (no new line) 
puts # => (print new line) 

new_arr = a.select { |num| num > 4 } 
p new_arr # => [7, 8, 10] 
new_arr = a.select { |num| num < 10 }
           .reject{ |num| num.even? } 
p new_arr # => [1, 3, 7] 

# Multiply each element by 3 producing new array
new_arr = a.map {|x| x * 3} 
p new_arr # => [3, 9, 12, 21, 24, 30] 
```

###Arrays 1
```ruby
het_arr = [1, "two", :three] # heterogeneous types 
puts het_arr[1] # => two (array indices start at 0) 

arr_words = %w{ what a great day today! } 
puts arr_words[-2] # => day
puts "#{arr_words.first} - #{arr_words.last}" # => what - today! 
p arr_words[-3, 2] # => ["great", "day"] (go back 3 and take 2) 

# (Range type covered later...)
p arr_words[2..4] # => ["great", "day", "today!"] 

# Make a String out of array elements separated by ‘,’
puts arr_words.join(',') # => what,a,great,day,today!
```

###Arrays 2
```ruby
# You want a stack (LIFO)? Sure 
stack = []; stack << "one"; stack.push ("two") 
puts stack.pop # => two 

# You need a queue (FIFO)? We have those too... 
queue = []; queue.push "one"; queue.push "two" 
puts queue.shift # => one 

a = [5,3,4,2].sort!.reverse! 
p a # => [5,4,3,2] (actually modifies the array) 
p a.sample(2) # => 2 random elements

a[6] = 33 
p a # => [5, 4, 3, 2, nil, nil, 33]
```


##Ranges
* [Ruby Range](http://ruby-doc.org/core-2.2.0/Range.html).
```ruby
puts some_range.max # => 3 
puts some_range.include? 2 # => true 

puts (1...10) === 5.3 # => true 
puts ('a'...'r') === "r" # => false (end-exclusive) 

p ('k'..'z').to_a.sample(2) # => ["k", "w"]
# or another random array with 2 letters in range

age = 55 
case age 
  when 0..12 then puts "Still a baby" 
  when 13..99 then puts "Teenager at heart!" 
  else puts "You are getting older..." 
end 
# => Teenager at heart!
```


##Hashes
* [Ruby Hashes](http://ruby-doc.org/core-2.2.0/Hash.html)

###Hashes 1
```ruby
editor_props = { "font" => "Arial", "size" => 12, "color" => "red"} 

# THE ABOVE IS NOT A BLOCK - IT'S A HASH 
puts editor_props.length # => 3 
puts editor_props["font"] # => Arial 

editor_props["background"] = "Blue" 
editor_props.each_pair do |key, value| 
  puts "Key: #{key} value: #{value}" 
end
# => Key: font value: Arial 
# => Key: size value: 12 
# => Key: color value: red 
# => Key: background value: Blue
```

###More hashes
```ruby
family_tree_19 = {oldest: "Jim", older: "Joe", younger: "Jack"} 
family_tree_19[:youngest] = "Jeremy" 
p family_tree_19 
# => {:oldest=>"Jim", :older=>"Joe", :younger=>"Jack“, :youngest => “Jeremy”}

# Named parameter "like" behavior... 
def adjust_colors (props = {foreground: "red", background: "white"}) 
  puts "Foreground: #{props[:foreground]}" if props[:foreground] 
  puts "Background: #{props[:background]}" if props[:background] 
end 

# No block passed
adjust_colors # => foreground: red 
              # => background: white 
# Passing
adjust_colors ({ :foreground => "green" }) # => foreground: green 
adjust_colors background: "yella" # => background: yella 
adjust_colors :background => "magenta" # => background: magenta
```

###Block and hash confusion
```ruby
# Let's say you have a Hash 
a_hash = { :one => "one" } 

# Then, you output it 
puts a_hash # => {:one=>"one"} 

# If you try to do it in one step - you get a SyntaxError 
# puts { :one => "one" } 

# RUBY GETS CONFUSED AND THINKS {} IS A BLOCK!!!

# To get around this - you can use parens 
puts ({ :one => "one" }) # => {:one=>"one"} 

# Or drop the {} altogether... 
puts one: "one"# => {:one=>"one"} 
```

###Word Frequency example
```ruby
# Init hash
word_frequency = Hash.new(0) 
# String
sentence = "Chicka chicka boom boom" 
# Pass string to array and loop through it
sentence.split.each do |word|
  # adding count to the hash
  word_frequency[word.downcase] += 1 
end 

p word_frequency # => {"chicka" => 2, "boom" => 2}
```


## Classes
* [Ruby Class](http://ruby-doc.org/core-2.2.0/Class.html)
```ruby
# Class definition
class Person 
  def initialize (name, age) # "CONSTRUCTOR" 
    @name = name 
    @age = age 
  end 
  def get_info 
    @additional_info = "Interesting" 
    "Name: #{@name}, age: #{@age}" 
  end 
end 

person1 = Person.new("Joe", 14) 
p person1.instance_variables # [:@name, :@age] 
puts person1.get_info # => Name: Joe, age: 14 
p person1.instance_variables # [:@name, :@age, :@additional_info] 
```

###Instance variables - getters/setters
```ruby
# are nedded to access instance vars
class Person 
  def initialize (name, age) # "CONSTRUCTOR" 
    @name = name # begin with @
    @age = age 
  end 
  # name getter (note same name)
  def name 
    @name 
  end 
  # name setter (note same name)
  def name= (new_name) 
    @name = new_name 
  end 
end 

# initialize class
person1 = Person.new("Joe", 14) 
puts person1.name # Joe 
person1.name = "Mike" 
puts person1.name # Mike 
# puts person1.age # undefined method `age' for #<Person:
```

###Attr Accessor 
`attr_accessor` builds automatic getters/setters,
```ruby
class Person 
  attr_accessor :name, :age   # getters and setters for name and age
  #attr_reader :name, :age    # only getters
  #attr_writer :name, :age    # only setters
end 

person1 = Person.new 
p person1.name # => nil 
person1.name = "Mike" 
person1.age = 15 
puts person1.name # => Mike 
puts person1.age # => 15 
person1.age = "fifteen" 
puts person1.age # => fifteen 
```

###Self
```ruby
class Person 
  attr_reader :age 
  attr_accessor :name 

  def initialize (name, ageVar) # CONSTRUCTOR 
    @name = name 
    self.age = ageVar # call the age= method 
    puts age 
  end 
  def age= (new_age) 
    @age = new_age unless new_age > 120 
  end 
end 

person1 = Person.new("Kim", 13) # => 13 
puts "My age is #{person1.age}" # => My age is 13 
person1.age = 130 # Try to change the age 
puts person1.age # => 13 (The setter didn't allow the change)
```

###Methods and Variables
Similar to static in Java. Are three ways to define them.
```ruby
class MathFunctions 
  # 1. Using self means it is a class variable
  def self.double(var)
    times_called; var * 2; 
  end 
  # 2. Using << self and @@
  class << self 
    def times_called 
      @@times_called ||= 0; @@times_called += 1 
    end 
  end 
end 
# 3. Outside of class 
def MathFunctions.triple(var) 
  times_called; var * 3 
end

# No instance created! 
puts MathFunctions.double 5 # => 10 
puts MathFunctions.triple(3) # => 9 
puts MathFunctions.times_called # => 3 
```

###Double pipe
```ruby
class Person 
  attr_reader :age 
  attr_accessor :name 
  
  def initialize (name, age) # CONSTRUCTOR 
    @name = name 
    self.age = age # call the age= method 
  end 
  def age= (new_age) 
    @age ||= 5 # @age ||= 5 # default 
    @age = new_age unless new_age > 120 
  end 
end 

# Test
person1 = Person.new("Kim", 130) 
puts person1.age # => 5 (default) 
person1.age = 10 # change to 10 
puts person1.age # => 10 
person1.age = 200 # Try to change to 200 
puts person1.age # => 10 (still) 
```

###Inheritance
Ruby do not support multiple heritance.
```ruby
class Dog # inherit form object class
  def to_s 
    "Dog" 
  end 
  def bark 
    "barks loudly" 
  end 
end 
class SmallDog < Dog # child class < parent class
  def bark # Override 
    "barks quietly" 
  end 
end 

dog = Dog.new # (btw, new is a class method) 
small_dog = SmallDog.new 
puts "#{dog}1 #{dog.bark}" # => Dog1 barks loudly 
puts "#{small_dog}2 #{small_dog.bark}" # => Dog2 barks quietly 
```


##Modules
Containers for classes, methods and constants. Are not instantiable. Main purposses: namespacing, mixins.

###Namespaces
```ruby
# Create module
module Sports 
  # Inner class
  class Match
    attr_accessor :score 
  end 
end 

# Create module
module Patterns 
  # Inner class
  class Match 
    attr_accessor :complete 
  end 
end 

# Use module Sports
match1 = Sports::Match.new
match1.score = 45; puts match1.score # => 45

# Use module Patterns
match2 = Patterns::Match.new
match2.complete = true; puts match2.complete # => true
```

###Mixins
A way to share code betweeen multiple classes.
```ruby
# Define module
module SayMyName 
  attr_accessor :name 
  def print_name 
    puts "Name: #{@name}" 
  end 
end 

# Outer class
class Person 
  # include module
  include SayMyName 
end 

# Outer class
class Company 
  # include module
  include SayMyName 
end 

# Test
person = Person.new 
person.name = "Joe" 
person.print_name # => Name: Joe 
company = Company.new 
company.name = "Google & Microsoft LLC" 
company.print_name # => Name: Google & Microsoft LLC 
```

###Enumerable Example
Adding Enumerable gives extra functionality to a class. The only thing we need to activate is to declare each method. See Team class above.
```ruby
# name of file - player.rb
class Player 

  attr_reader :name, :age, :skill_level

  def initialize (name, age, skill_level) 
    @name = name 
    @age = age
    @skill_level = skill_level 
  end

  def to_s 
    "<#{name}: #{skill_level}(SL), #{age}(AGE)>" 
  end 

end 

# team.rb 
class Team 
  include Enumerable # LOTS of functionality

  attr_accessor :name, :players 
  def initialize (name) 
    @name = name 
    @players = [] 
  end 
  def add_players (*players) # splat 
    @players += players
  end 
  def to_s 
    "#{@name} team: #{@players.join(", ")}" 
  end 
  def each 
    @players.each { |player| yield player }
  end 
end 

# picks.rb 
require_relative 'player' # include ruby
require_relative 'team'   # include ruby

player1 = Player.new("Bob", 13, 5); player2 = Player.new("Jim", 15, 4.5) 
player3 = Player.new("Mike", 21, 5) ; player4 = Player.new("Joe", 14, 5) 
player5 = Player.new("Scott", 16, 3)

red_team = Team.new("Red") 
red_team.add_players(player1, player2, player3, player4, player5) # (splat) 

# select only players between 14 and 20 and reject any player below 4.5 skill-level
# this functionallity is given by Enumerable
elig_players = red_team.select {|player| (14..20) === player.age } 
                       .reject {|player| player.skill_level < 4.5} 
puts elig_players # => <Jim: 4.5(SL), 15(AGE)>
          # => <Joe: 5(SL), 14(AGE)>
```


##Scope

###Variables
```ruby
# Ver definition
v1 = "outside"

# class
class MyClass 
  def my_method
    # p v1 EXCEPTION THROWN - no such variable exists
    v1 = "inside"
    p v1
    p local_variables 
  end 
end 

p v1 # => outside
obj = MyClass.new 
obj.my_method # => inside 
              # => [:v1] 
p local_variables # => [:v1, :obj]
p self # => main
```

###Constants
Constants has different scope rules.
```ruby
module Test 
  PI = 3.14 
  class Test2 
    def what_is_pi 
      puts PI 
    end 
  end 
end 
Test::Test2.new.what_is_pi # => 3.14 

module MyModule 
  MyConstant = 'Outer Constant' 
  class MyClass 
    puts MyConstant # => Outer Constant 
    MyConstant = 'Inner Constant' 
    puts MyConstant # => Inner Constant 
  end
  puts MyConstant # => Outer Constant 
end
```

###Block scope
Has inherit outer scope.
```ruby
class BankAccount 
  attr_accessor :id, :amount 
  def initialize(id, amount) 
    @id = id 
    @amount = amount 
  end 
end 

acct1 = BankAccount.new(123, 200) 
acct2 = BankAccount.new(321, 100) 
acct3 = BankAccount.new(421, -100) 
accts = [acct1, acct2, acct3] 

total_sum = 0 
accts.each do |eachAcct| 
  total_sum += eachAcct.amount 
end 

puts total_sum # => 200
```

###Block local
Variables created inside the block is only available to the block. Can explicit declare block-local vars after a semicolon.
```ruby
arr = [5, 4, 1] 
cur_number = 10 
arr.each do |cur_number| 
  some_var = 10 # NOT available outside the block 
  print cur_number.to_s + " " # => 5 4 1 
end 
puts # print a blank line 
puts cur_number # => 10 

adjustment = 5 
arr.each do |cur_number;adjustment| # affect change variable
  adjustment = 10 
  print "#{cur_number + adjustment} " # => 15 14 11 
end 
puts 
puts adjustment # => 5 (Not affected by the block)
```


##Access Control: public, protectec, private

###Encapsulation
```ruby
class Car
  def initialize(speed, comfort)
    @rating = speed * comfort
  end
   
  # Can't SET rating from outside
  def rating
    @rating
  end
end

puts Car.new(4, 5).rating # => 20
```

###Specify Access
All method in Ruby are public by default.
```ruby
class MyAlgorithm
  private     # all methods declared from there are private
    def test1
      "Private"
    end
  protected   # all methods declared from there are protected
    def test2
      "Protected"
    end
  public      # all methods declared from there are public
    def public_again
      "Public"
    end
end

class Another
  def test1
    "Private, as declated later on"
  end
  private :test1
end
```

###Private Access
```ruby
class Person
  def initialize(age)
    self.age = age # LEGAL - EXCEPTION
    puts my_age
    # puts self.my_age # ILLEGAL
                       # CANNOT USE self or any other receiver
  end

  private 
    def my_age
      @age
    end
    def age=(age)
      @age = age
    end
end

Person.new(25) # => 25
```


##Unit Testing
```ruby
class Calculator

  attr_reader :name

  def initialize(name)
    @name = name
  end

  def add(one, two)
    one - two
  end

  def subtract(one, two)
    one + two
  end

  def divide(one, two)
    one / two
  end
end

# Unit Test for calculator
require 'test/unit'
require_relative 'calculator'

class CalculatorTest < Test::Unit::TestCase
  def setup
    @calc = Calculator.new('test')
  end

  # Test if an Exception is thrown 
  def test_divide_by_zero
    assert_raise ZeroDivisionError do 
      @calc.divide(1, 0)
    end
  end
end
```


##Dinamic Dispatch
Methods don’t have to be predefined - they need to only be “found” when invoked

```ruby
class Store
  def get_piano_desc
    "Excellent piano"
  end
  def get_piano_price
    120.00
  end
  def get_violin_desc
    "Fantastic violin"
  end
  def get_violin_price
    110.00
  end
  # ...many other similar methods...
end
```

Example 
```bash
props = {name: "John", age: 15}

class Person; attr_accessor :name, :age; end

person = Person.new
props.each{|key,value| person.send("#{key}=", value)}
person
```

###Call Methods Dinamically
With `Class.send(:method_name, arg1, arg2, *)`.
```ruby
class Dog
  def bark
    puts "Woof, woof!"
  end
  def greet(greeting)
    puts greeting
  end
end

dog = Dog.new
dog.bark # => Woof, woof!
# Can decide at runtime which methods to call
dog.send("bark") # => Woof, woof!
dog.send(:bark) # => Woof, woof!
method_n ame = :bark
dog.send method_name # => Woof, woof!
dog.send(:greet, "hello") # => hello
```

###Define Methods Dinamically
`define_method :method_name` and a block which contains the method definition.

```ruby
# Simple
class Whatever
  define_method :make_it_up do
    puts "Whatever..."
  end
end
whatever = Whatever.new
whatever.make_it_up # => Whatever...

# Advaced method
require_relative 'store'
class ReportingSystem
  Extracts product name
  def initialize
    @store = Store.new
    @store.methods.grep(/^get_(.*)_desc/) { ReportingSystem.define_report_methods_for $1 }
  end
  def self.define_report_methods_for (item)
    define_method("get_#{item}_desc") { @store.send("get_#{item}_desc")}
    define_method("get_#{item}_price") { @store.send("get_#{item}_price")}
  end
end
rs = ReportingSystem.new
puts "#{rs.get_piano_desc} costs #{rs.get_piano_price.to_s.ljust(6, '0')}"
# => Excellent piano costs 120.00
```

###Ghost Methods
Ruby looks for the method invoked in the class to which it belongs. Then it goes up the ancestors tree (classes and modules). If it till doesn’t find the method, it calls method_missing method. The default method_missing implementation throws `NoMethodErrorOverriding` method_missing.

Since method_missing is just a method, you can easily override it:
* Name of the method called
* Any arguments passed in
* A block if it was passed inOverriding method_missing

Since the invocation is indirect, could be a little slower.

```ruby
class Mystery
  # no_methods defined
  def method_missing (method, *args)
    puts "Looking for..."
    puts "\"#{method}\" with params (#{args.join(',')}) ?"
    puts "Sorry... He is on vacation..."
    yield "Ended up in method_missing" if block_given?
  end
end
m = Mystery.new
m.solve_mystery("abc", 123123) do |answer|
  puts "And the answer is: #{answer}"
end
# => Looking for...
# => "solve_mystery" with params (abc,123123) ?
# => Sorry... He is on vacation...
# => And the answer is: Ended up in method_missingGhost Methods
```

Callback other class method:
```ruby
# instead of: 
require_relative 'store'
class ReportingSystem
  def initialize
    @store = Store.new
  end
  def get_piano_desc
    @store.get_piano_desc
  end
  def get_piano_price
    @store.get_piano_price
  end
  # ...many more simimlar methods...
end

rs = ReportingSystem.new
puts "#{rs.get_piano_desc} costs #{rs.get_piano_price.to_s.ljust(6, '0')}"
# => Excellent piano costs 120.00...We Can Do This!

# ... do this:
require_relative 'store'
class ReportingSystem
  def initialize
    @store = Store.new
  end
  def method_missing(name, *args)
    super unless @store.respond_to?(name)
    @store.send(name)
  end
end

rs = ReportingSystem.new
puts "#{rs.get_piano_desc} costs #{rs.get_piano_price.to_s.ljust(6, '0')}"
# => Excellent piano costs 120.00
```


### Struct and Open Struct
* Struct: Generator of specific classes, each one of which is defined to hold a set of variables and their accessors (“Dynamic Methods”).
* OpenStruct:  Object (similar to Struct) whose attributes are created
dynamically when first assigned (“Ghost methods”).

```ruby
Customer = Struct.new(:name, :address) do # block is optional
  def to_s
  "#{name} lives at #{address}"
  end
end
jim = Customer.new("Jim", "-1000 Wall Street")
puts jim # => Jim lives at -1000 Wall Street

require 'ostruct' # => need to require ostruct for OpenStruct
some_obj = OpenStruct.new(name: "Joe", age: 15)
some_obj.sure = "three"
some_obj.really = "yes, it is true"
some_obj.not_only_strings = 10
puts "#{some_obj.name} #{some_obj.age} #{some_obj.really}"
# => Joe 15 yes, it is true
```

##RSpec

Write test in a more descriptive manner.
* [RSpec](http://rspec.info/).
* [Better Specs](http://betterspecs.org/#describe).

```shell
# Installation 
gem install rspec
# init  spec folder
rspec --init
# run rspec
rspec
# 
rspec -e  rq01
```

```ruby
# ./spec/answer_spec.rb
require 'rspec'
require_relative '../answer'

describe Answer do
  before { @answer = Answer.new('constructorArg')}

  it "should add 2 numbers correctly" do
    expect(@answer.add(2, 2)).to eq 4 
  end
end
```
 
###RSpec filter test
####Run
```bash
# Test all block
rspec -e rq02
# Test inside block
rspec -e rq02.1
rspec -e rq01 -e rq02 # several test
```

####Spec file config
```ruby
# xxx_rspec.rb
    context "rq02" do
      context "Generate four(4) Model Classes and Properties" do
        context "rq02.1 User Model" do
          # ...
        end
      end
    end
```

###RSpec Matchers
* [RSpec Matchers](https://www.relishapp.com/rspec/rspec-expectations/v/2-2/docs/matchers/be-matchers#be-true-matcher).

```ruby
to/not_to
be_true / be_false
eq 3
raise_error(SomeError)
be_nil
# ex
it "should sum two odd numbers and become even" do
  expect(@calculator.add(3,3)).to be_even
  expect(@calculator.add(3,3)).not_to be_odd
end
```

##Style Guides
* [Ruby](https://github.com/styleguide/ruby)
* [Ruby AirBnB](https://github.com/airbnb/ruby)

## Method Documentation
* [TomDoc](http://tomdoc.org/)