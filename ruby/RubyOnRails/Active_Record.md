# Active Record
Define and Scaffold Model and Relations.

## Table of contents
- [Scaffolding](#scaffolding)
- [SQLite](#sqlite)
- [Migrations](#migrations)
- [CRUD](#crud)
- [Database Seed](#database-seed)
- [SQL Fragments](#sql-fragments)
- [Relations](#relations)
- [Scope](#scope)
- [Validation](#validation)
- [N+1 Queries Issue](#n1-queries-issue)
- [DB Transaction](#db-transaction)
- [Active Record Resources](#active-record-resources)
- [Credit](#credit)



## Scaffolding
Generate CRUD stuffs via terminal:
1. Migration.
2. Model.
3. Routes.
4. Controller with actions.
5. Views.
6. Json response.

```bash
# specify entity and column. If not specified 
# would be a string.
rails g scaffold car make color year:integer

# Applying to DB
rake db:migrate

# Create all CRUD stuffs: Migration, Model, 
# Routes, Controllers, Views

localhost:3000/cars
localhost:3000/cars/new
localhost:3000/cars.json
```

## SQLite
SQLite for database by default. 
* [SQLite Browser](http://sqlitebrowser.org/) (SQLite Admin).

Add database at `config/database.yml`.

```yml
# SQLite version 3.x
#   gem install sqlite3
#
#   Ensure the SQLite 3 gem is defined in your Gemfile
#   gem 'sqlite3'
#
default: &default
  adapter: sqlite3
  pool: 5
  timeout: 5000

development:
  <<: *default
  database: db/development.sqlite3
```

### Log
```bash
# terminal 
rails db

# inside db
.help 
# Most used
sqlite > .tables
sqlite > .headers on
sqlite > .mode columns
sqlite > .schema cars # Create tables
sqlite > select *from cars
```


## Migrations
* [Ruby Migrations](http://guides.rubyonrails.org/migrations.html).
* [Edge Guides](http://edgeguides.rubyonrails.org/active_record_migrations.html).

Create SQL Ruby definitions under `db/migrate`.
```ruby
class CreateCars < ActiveRecord::Migration
  def change
    create_table :cars do |t|
      t.string :make
      t.string :color
      t.integer :year

      t.timestamps null: false
    end
  end
end
```

### File definition
```ruby
# Data Types
:binary
:boolean
:date
:datetime
:decimal
:float
:integer
:string
:text
:time

# Constraint
null: true or false
limit: size # Sets a limit on the size of the field
default: value # Default value for the column (Calculated once!)

# Decimal columns (optionally) take two more options
precision: value # Total number of digits stored
scale: value # Where to put the decimal point

# ex: precision 5 and scale 2 can store the range
-999.99 to 999.99
```

### Apply
```bash
# Migrate needs to run to apply changes
rake db:migrate # Maintains a table called schema_migrations 
                # to keep versions timestamp.
```

### Operations 
* Add: add_column :table_name, :column_name, :column_type
* Remove: remove_column :table_name, :column_name
* Rename: rename_column :table_name, :old_column_name, :new_column_name

```bash
# Adding extra attribute to entity( price to cars table) 
rails g migration add_price_to_cars 'price:decimal{10,2}' # ... migration created at db/migrate/xxx_add_price_to_cars.rb
# Rename
rails g migration rename_make_to_company

rake db:migrate
Car.column_names
# => ["id", "company"]
# Rollback
rake db:rollback

# Reload Rails console
irb> reload!
```


### Active Record
Rails’ default ORM. Three Prerequisites:
1. ActiveRecord has to know how to find your database (when Rails is loaded, this info is read from config/database.yml file).
2. (Convention) There is a table with a plural name that corresponds to ActiveRecord::Base subclass with a singular name.
3. (Convention) Expects the table to have a primary key named id

##Inflactions
Define rules for scaffolding name creation. Located at `config/initializers/inflections.rb`.


## CRUD
### Create
Three ways to create a record in the database

```bash
irb
irb > Person.column_names
# => ["field1", "field2"]
# 1. Use an empty constructor and (ghost) attributes to set the values and then call save.
irb > p1 = Person.new; p1.field1 = "value 1"; p1.field2 = "value 2"
# 2. Pass a hash of attributes into the constructor and then call save.
irb > p2 = Person.new(field1: "value 1", field2: "value 2"); p2.save
# 3. Use create method with a hash to create an object and save it to the database in one step.
irb > p3 = Person.create(field1: "value 1", field2: "value 2")
```

### Read
* find(id) or find(id1, id2): Throws a RecordNotFound exception if not found
* first, last, take, all: Return the results you expect or nil if nothing is found.
* order(:column) or order(column: :desc).
* pluck: Allows to narrow down which fields are coming back (need to call at the end).
* were(hash): conditions for your search. Returns ActiveRecord::Relation (same as all), but you can always narrow it down with first or treat it like an Array...
* find_by(conditions_hash): same as where, but returns a single result or nil (better performance).
* find_by!(conditions_hash): Same as find_by, but throws an exception if cannot find the result.
* limit(n): limit how many records come back.
* offset(n): skip n records.


```bash
irb
irb > Person.all.order(firs_name: :desc)        # return collection
irb > Person.all.order(firs_name: :desc).to_a   # returns array
irb > Person.first                              # last person registry
irb > Person.take                               # same as LIMIT 1
irb > Person.take 2                             # same as LIMIT 2
irb > Person.all.map{|person| person.first_name} # bad idea! use pluck
irb > Person.pluck(:first_name)                 # just retrieve :first_name
irb > Person.where(last_name: "Doe")            # AR relation
irb > Person.where(last_name: "Doe").first      # result
irb > Person.where(last_name: "Doe")[0]         # treat like an array
irb > Person.find_by(last_name: "Doe")          # AR relation
irb > Person.all.map{|person| "#{person.first_name} #{person.last_name}" } # base idea! use pluck
irb > Person.offset(1).limit(1).map{|person| "#{person.first_name} #{person.last_name}" } idea! use pluck
```

### Update
Two ways to update a record in the database:

```bash
irb
# 1. Retrieve a record, modify the values and then call save.
irb > jane = Person.find_by last_name: "Jane"
irb > jane.last_name = "New"
irb > jane.save

# 2. Retrieve a record and then call update method passing in a hash of attributes with new values.
irb > Person.find(3)   # returns array
irb > Person.find_by(last_name: "Jane").update(last_name: "New")
```

There is also update_all for batch updates
* You can chain this to the end of where.

### Delete
* destroy(id) or destroy: Removes a particular instance from the DB. Instantiates an object first and performs callbacks before removing. See [Active Records Callbacks](http://guides.rubyonrails.org/active_record_callbacks.html).
* delete(id): Removes the row from DB

```bash
irb
# 1. Retrieve a record, modify the values and then call save.
irb > jane = Person.find_by last_name: "Jane"
irb > jane.destroy
irb > jane = Person.find_by last_name: "Jane"
irb > Person.delete(jane.id)
```

There is also a delete_all.


## Database Seed
Add some preliminary data to the DB. At `db/seeds.rb`.
```ruby
#db/seeds.rb
Person.destroy_all

Person.create! [
  { first_name: "Kalman", last_name: "Smith", age: 33, login: "kman", pass: "abc123" },
  { first_name: "John", last_name: "Whatever", age: 27, login: "john1", pass: "123abc" },
  { first_name: "Michael", last_name: "Smith", age: 15, login: "mike", pass: "not_telling" },
  { first_name: "Josh", last_name: "Oreck", age: 57, login: "josh", pass: "password1" },
  { first_name: "John", last_name: "Smith", age: 27, login: "john2", pass: "no_idea" },
  { first_name: "Bill", last_name: "Gates", age: 75, login: "bill", pass: "windows3.1" },
  { first_name: "LeBron", last_name: "James", age: 30, login: "bron", pass: "need more rings" }
]
```

```bash
# Run seed
rake db:seed
```

## SQL Fragments
### Array Syntax
* Uses ? followed by values (parameters). 
* "Automagically" performs conversions on the input values and escapes strings in the SQL.
* Immune to SQL injection.
* Similar to a PreparedStatement in Java.

```bash
irb
# 1. Retrieve a record, modify the values and then call save.
irb > Person.where("age BETWEEN ? AND ?", 28, 34).to_a
irb > Person.where("first_name LIKE ? OR last_name LIKE ?", %J%, %J%)
```

### Hash Condition Syntax
Instead of "?", you specify symbols which map to the values in the hash passed in as a second parameter.
```bash
irb
irb > Person.where("age BETWEEN :min_age AND :max_age", min_age: 28, :max_age: 34).to_a
irb > Person.where("first_name LIKE :pattern OR last_name LIKE :pattern", pattern: %J%)
```

## Relations

### One-to-One
* One person has exactly one personal_info entry.
* One personal_info entry belongs to exactly one person.
* The “belongs to” side is the one with a foreign key.

Convention: Default name for the foreign key is {master_table_singular} _id, e.g. person_id,

```bash
rails g model personal_info height_float weight: float person:references
rake db:migrate
```

Makes at `db/migrate/XXXXX_create_personal_infos.rb`.

```ruby
class CreatePersonalInfos < ActiveRecord::Migration
  def change
    create_table :personal_infos do |t|
      t.float :height
      t.float :weight
      t.references :person, index: true, foreign_key: true

      t.timestamps null: false
    end
  end
end
```

Automatically creates the `belongs_to`:

```ruby
# app/model/personal_info.rb: 
class PersonalInfo < ActiveRecord::Base
  belongs_to :person
end
```

Need to create manually the `has_one` side:

```ruby
# app/model/person.rb: 
class Person < ActiveRecord::Base
    has_one :personal_info
end
```


See with
```bash
rails db
sqlite> .schema personal_infos
```

#### Definition
```bash
rails c
irb> bill = Person.find_by first_name: "Bill"
irb> bill.personal_info 
# => nil
irb> p1 = PersonalInfo.create height: 6.5, weight: 220
irb> bill.personal_info = p1
```

* In addition, you now also have build_personal_info(hash) and create_personal_info(hash) methods on a person instance.
* Create_personal_info creates a record in the DB right away, while build_personal_info does not.
* Both remove the previous reference in the DB.

```bash
rails c
irb> bill.Person.find_by first_name: "Bill"
irb> bill.personal_info 
# => nil
irb> bill.build_personal_info height: 6.5, weight: 220
irb> bill.personal_info = p1
# oneline
irb> bill.Person.find_by first_name: "Bill"; bill.build_personal_info height: 6.5, weight: 220
```

### One-to-Many
* One person has one or more jobs.
* One job entry belongs to exactly one person.
* The “belongs to” side is the one with a foreign key.

Convention: Default name for the foreign key is {master_table_singular}_id, e.g. person_id.


```bash
rails g model job title company position_id person:references
rake db:migrate
```

Makes at `db/migrate/XXXXX_create_jobs.rb`.
```ruby
class CreateJobs < ActiveRecord::Migration
  def change
    create_table :jobs do |t|
      t.string :title
      t.string :company
      t.string :position_id
      t.references :person, index: true, foreign_key: true

      t.timestamps null: false
    end
  end
end
```

At `app/model/person.rb`: 
```ruby
# app/model/person.rb: 
class Person < ActiveRecord::Base
    has_one :personal_info
    has_many :jobs
    has_many :my_jobs, class_name: "Job"
end
```

Action:
```bash
# Silent sql
rails c
irb > ActiveRecord::Base.logger = nil
irb > Job.create company:"MS", title: "Developer", postion_id: "#1234"
irb > p1 = person.first
irb > p1.jobs
irb > p1.jobs << Job.first
irb > Job.first.person
```


#### More Methods
* person.jobs = jobs: Replaces existing jobs with a new array. As opposed to person.jobs << job(s) where the jobs are appended.
* person.jobs.clear: Disassociates jobs from this person by setting the foreign key to NULL.
* create and where methods for jobs become scoped to the person.
```bash
# Silent sql
rails c
irb > ActiveRecord::Base.logger = nil
irb > Person.first.jobs.where(company: "MS").count
# => 2
irb > Person.last.jobs.where(company: "MS").count
# => 2
irb > Person.last.jobs.where(company: "MS").to_a
# => [...]
```

#### Options for has_many - :class_name:

```ruby
class Person < ActiveRecord::Base
    has_one :personal_info
    has_many :jobs
    # Saying my_jobs is an alias for model Job
    has_many :my_jobs, class_name: "Job"
end
```

#### Dependent option 
Specify the fate of the association when the parent gets destroyed:
* :delete – remove associated object(s)
* :destroy – same as above, but remove the association by calling destroy on it.
* :nullify – set the FK to NULL (leave the associated entity alone – just disassociate).
* 
```ruby
class Person < ActiveRecord::Base
    has_one :personal_info, dependent: :destroy
    has_many :jobs
    # Saying my_jobs is an alias for model Job
    has_many :my_jobs, class_name: "Job"
end
```


### Many-to-Many
* One person can have many hobbies.
* One hobby can be shared by many people.
* habtm (has_and_belongs_to_many).
* Need to create an extra (a.k.a. join) table (without a model, i.e. just a migration).

```bash
# Create model 
rails g model hobby name

# Important!! Convention alphabetical order of the 
# mixin table's names: people hobbies
# => create_hobbies_people
rails g migration create_hobbies_people person:references hobby:references
```

Creates: 
```ruby
class CreateHobbiesPeople < ActiveRecord::Migration
  def change
    # id: false remove id attr
    create_table :hobbies_people, id: false do |t|
      t.references :person, index: true, foreign_key: true
      t.references :hobby, index: true, foreign_key: true
    end
  end
end
```


Add manually `has_and_belongs_to_many :people`:
```ruby
# app/model/hobby.rb: 
class Hobby < ActiveRecord::Base
  has_and_belongs_to_many :people
end

```

Add manually `has_and_belongs_to_many :hobbies`:
At `app/model/person.rb`: 
```ruby
# app/model/person.rb: 
class Person < ActiveRecord::Base
    has_one :personal_info, dependent: :destroy
    has_many :jobs
    # Saying my_jobs is an alias for model Job
    has_many :my_jobs, class_name: "Job"
    has_and_belongs_to_many :hobbies
end
```


### Rich Many-to-Many
* Sometimes, you need to keep some data on the join table.
* Grandchild relationships on a model, like user ➔ articles ➔ comments. In our case – all salary ranges for a particular person.
* ActiveRecord provides a :through option for this purpose

First create a regular parent-child relationship and then use the child model as a "join" between the parent and grandchild.

```bash
rails g model salary_range min_salary:float max_salary:float job:references
rake db:migrate.
```


```ruby
# db/migrate/xxx_create_salary_ranges.rb 
class CreateSalaryRanges < ActiveRecord::Migration
  def change
    create_table :salary_ranges do |t|
      t.float :min_salary
      t.float :max_salary
      t.references :job, index: true, foreign_key: true

      t.timestamps null: false
    end
  end
end

# db/models/job.rb 
class Job < ActiveRecord::Base
  belongs_to :person
  has_one :salary_range
end

# db/models/salary_range.rb 
class SalaryRange < ActiveRecord::Base
  belongs_to :job
end

# db/models/person.rb 
class Person < ActiveRecord::Base
    has_one :personal_info, dependent: :destroy
    has_many :my_jobs, class_name: "Job"
    has_and_belongs_to_many :hobbies
    has_many :jobs
    # through: :jobs, source: :salary_range
    has_many :approx_salaries, through: :jobs, source: :salary_range 

    def max_salary
      approx_salaries.maximum(:max_salary)
    end
end
```

* [Active Record Calculations](http://api.rubyonrails.org/classes/ActiveRecord/Calculations.html)
```bash
irb > lebron = Person.find_by last_name:"James"
irb > lebron.max_salary
#=> 35000.00
```


## Scope
### Default Scope
Class method for specifying how the records are retrieved by default from the db.
```ruby
class Hobby < ActiveRecord::Base
  has_and_belongs_to_many :people

  default_scope { order :name }
end
```

### Named Scope
```ruby
class Person < ActiveRecord::Base
    has_one :personal_info, dependent: :destroy
    has_many :my_jobs, class_name: "Job"
    has_and_belongs_to_many :hobbies
    has_many :jobs
    has_many :approx_salaries, through: :jobs, source: :salary_range

    def max_salary
      approx_salaries.maximum(:max_salary)
    end

    scope :ordered_by_age, -> { order age: :desc}
    scope :starts_with, -> (starting_string){ where("first_name LIKE ?", "#{starting_string}%")}
end
```

```bash
irb > Person.ordered_by_age.pluck :age
irb > Person.ordered_by_age.starts_with("Jo").pluck :age, :first_name
```

## Validation
* [ActiveRecord Validations](http://guides.rubyonrails.org/active_record_validations.html#inclusion).
* 
```ruby
# presence: true: Make sure the field contains some data.
# uniqueness: true.
# :numericality – validates numeric input.
# :length – validates value is a certain length.
# :format – validates value complies with some regular expression format.
# :inclusion – validates value is inside specified range.
# :exclusion – validates value is out of the specified range.
class Job < ActiveRecord::Base
  belongs_to :person
  has_one :salary_range

  # Validate not empty
  validates :title, :company, presence: true
  # Validate range of vales
  validates :gender, inclusion: { in: %w(male female),
    message: "%{value} is not a valid size" }
end
```


### Own Validators
1. Write a method that does some validation and calls errors.add(columnname, error)when it encounters an error condition.
2. Specify it as a symbol for the validate method.
```ruby
# example 1
class SalaryRange < ActiveRecord::Base
  belongs_to :job

  validate :min_is_less_than_max

  def min_is_less_than_max
    if min_salary > max_salary
      errors.add(:min_salary, "cannot be greater than maximum salary!")
    end
  end
end

# example 2
class Profile < ActiveRecord::Base
  belongs_to :user
  # 
  # Validations
  # 
  validate :fname_and_lname_undefined
  validate :male_person_named_sue

  def fname_and_lname_undefined
    if first_name.nil? and last_name.nil?
      errors.add(:first_name, "Define first name")
      errors.add(:last_name, "Define second name")
    end
  end

  def male_person_named_sue
    if gender == "male" and first_name == "Sue"
      errors.add(:gender, "Male person named Sue?")
    end
  end

end

```

## N+1 Queries Issue
```bash
rails c
irb > Person.first.personal_info.weight # Makes 2 queries: 1 person and +1 personal info.
irb > Person.all.each{ |person| puts person.personal_info.weight } # # Makes 2  this way not scale to good --> resolves n+1

# N+1: in the query includes personal_info data (join)
irb > Person.includes(:personal_info).all.each{ |person| puts person.personal_info.weight } # this way not scale to good --> resolves n+1

```

## DB Transaction
* [ActiveRecord Transaction](http://api.rubyonrails.org/classes/ActiveRecord/Transactions/ClassMethods.html).

## Active Record Resources
See the guides for more information on Active Record
* [AR Basics](http://guides.rubyonrails.org/active_record_basics.html).
* [AR Querying](http://guides.rubyonrails.orgactive_record_querying.html).
* [AR Association basics](http://guides.rubyonrails.org/association_basics.html).
* [AR Callbacks](http://guides.rubyonrails.org/active_record_callbacks.html).

## Credit
Example code gracefully provided at Coursera's [Ruby on Rails with Active Record and Action Pack - Johns Hopkins University](https://www.coursera.org/learn/ruby-on-rails-intro/) course.
