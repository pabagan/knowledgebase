# Ruby on Rails (RoR)
* [Ruby on Rails](http://rubyonrails.org/).

## Table of contents
- [Why use Rails](#why-use-rails)
- [Database](#database)
- [Create and run Rails application](#create-and-run-rails-application)
- [Generate static content](#generate-static-content)
- [Controllers](#controllers)
- [Views with ERB](#views-with-erb)
- [Routes](#routes)
- [Rake (Ruby's make)](#rake-rubys-make)
- [Controller to Views](#controller-to-views)
- [Helpers](#helpers)
- [HTTP Party](#http-party)
- [Create Model with the API](#create-model-with-the-api)
- [Bundler](#bundler)
- [Gemfile](#gemfile)
- [CSS](#css)
- [Parameters](#parameters)
- [Routing with root path](#routing-with-root-path)
- [Deploy to Heroku](#deploy-to-heroku)
- [Blackbox Testing](#blackbox-testing)
- [Debugging](#debugging)
- [Events on files](#events-on-files)
- [Learning resources](#learning-resources)
- [Credit](#credit)



## Why use Rails
* Convention Over Configuration (COC).
* Database Abstraction Layer.
* Agilar-friendly.
* Don't Repeat Yourself (DRY).
* Cross-platform.
* Open Source.
* Modular.
* MVC oriented.
* Built-in web server.

## Database
Rails use SQLite by default: relational database, no configuration, no server, transactional.

### Check db from console
```bash
rails db

# Most used
sqlite > .tables
sqlite > .headers on
sqlite > .mode columns
sqlite > .schema cars # Create tables
sqlite > select *from cars
```



## Create and run Rails application
Go to the folder where application lives and run:
```bash
# Default
rails new app-name
# With extra options
rails new -h

# Gem install manager is auto run
bundle install

# Rails automatic add .gitignore. Init GIT if desired
git init
git add .
git commit -m "Initial commit"

# Run application
rails server/rails s
```

## Generate static content
Static pages lives inside `public` folder. 

Ex: create a file static and add to public: hello_static.html
```erb
<!DOCTYPE html>
<html>
    <head>
        <title>100% Static</title>
    </head>
    <body>
        <h2>Will never be dinamic</h2>
    </body>
</html>
```

Visit http://0.0.0.0:3000/hello_static.html

## Controllers
By convention are named in plural.

```bash
# Generate controller and 0 or more actions
rails generate controller controller_name action1 action2
# or rails g ...
rails g controller controller_name [action1 action2]
```

This generates a folder under `app/controllers/concerns/controller_name_controller.rb` and `app/views/controller_name/xxx.html.erb`.


### How controllers look like
```ruby
class GreeterController < ApplicationController
    def hello
    end
end
```

#### Create manually extra controller goodbye: 
```ruby
class GreeterController < ApplicationController
    def hello
    end
    def goodbye
    end
end
```

At `views/controller_name/` create `goodbye.html.erb`. Create routes as shown 
below.


## Views with ERB
* [ERB](http://ruby-doc.org/stdlib-2.3.1/libdoc/erb/rdoc/ERB.html): templating library thats embed Ruby into the HTML.

Tag patterns:
```erb
<% ...ruby code... %> <!-- evaluate Ruby code -->
<%= ...ruby code... %> <!-- output evaluated Ruby code -->

<!-- file: hello.html.erb -->
<% random_names =["Jane", "Gina", "Anne"] %>
<h1>Greetings, <%= random_names.sample %></h1>
<p>The time now is <%= Time.now %></p>

<!-- loop -->
<% for @i in @obj %>
  <%= @i %>
<% end %>

<!-- comments -->
<%# This is just a comment %>

<!-- transform -->
URL encoded: <%= u(this & that) %>
HTML escaped: <%= h(this & that) %>
JSON encoded: <%= j(this & that) %>
Textile markup: <%= t(this & that) %>
```

### ERB in Ruby files
```ruby
require 'erb'

hello = "Hey <%= any %>."

renderer = ERB.new(hello)
puts output = renderer.result()
```


## Routes
Created by Rails genrator or manually at `config/routes.rb`
```ruby
Rails.application.routes.draw do
    # url greeter/hello matches controller#action
    get 'greeter/hello' => "greeter#hello" 
end
```

## Rake (Ruby's make)
Ruby's build languag used to automate app-related tasks.
* Ruby's make.
* No XML - 100% Ruby.

```bash
# See all rake tasks
rake --tasks
# >>
# rake about                              # List versions of all Rails framew...
# rake assets:clean[keep]                 # Remove old compiled assets
# rake assets:clobber                     # Remove compiled assets
# rake assets:environment                 # Load asset compile environment
# rake assets:precompile                  # Compile all the assets named in c...
# rake cache_digests:dependencies         # Lookup first-level dependencies f...
# rake cache_digests:nested_dependencies  # Lookup nested dependencies for TE...
# rake db:create                          # Creates the database from DATABAS...
# rake db:drop                            # Drops the database from DATABASE_...
# rake db:fixtures:load                   # Load fixtures into the current en...
# rake db:migrate                         # Migrate the database (options: VE...
# rake db:migrate:status                  # Display status of migrations
# rake db:rollback        

# Describe
rake --describe task_name
rake --describe db:rollback
# ex: define current configured routes
rake routes

```

## Controller to Views
Instante variables from controllers are available inside views.

```ruby
# URL localhost:3000/greeter/hello runs method hello
class GreeterController < ApplicationController
    def hello
        random_names =["Jane", "Gina", "Anne"]
        @name = random_names.sample
        @time = Time.now
        @times_displayed ||= 0
        @times_displayed += 1
    end
    def goodbye
    end
end
```

Now at `Views/Greeter/hello.html.erb`

```erb
<h1>Greetings, <%= @name %></h1>
<p>The time now is <%= @time %></p>
<p>The page has been viewed <%= @times_displayed %> time(s)</p><!-- this ramains at 1 since we cannot store values in controllers. Use session or DB alternative -->
```

## Helpers
Work as "macros/formatters for the views. At `app/helpers/helper_name.rb`

```ruby
# URL localhost:3000/greeter/hello runs method hello
class GreeterHelper
    # this method is available at any view of the application
    def formatted_line(time)
        # time AM/PM format
        time.strftime("%I:%M%p")
    end
end
```

### Built-in helpers

* See [built-in helpers](http://api.rubyonrails.org/classes/ActionView/Helpers/AssetTagHelper.html)
```erb
# link_to name, path
<%= link_to "Google", "http://www.google.com" %>

<%= link_to "Food2Fork.com", "http://food2fork.com/", :title => 'Food2Fork.com', :rel => 'nofollow' %>

image_tag("rails.png") # generate image element
# => <img alt="Rails" src="/assets/rails.png" />

audio_tag("sound")
# => <audio src="/audios/sound"></audio>
```

## HTTP Party
Client to deal with restful web services. REST principles:
1. Have a bse URI.
2. Support data exchange with XML, JSON, ...
3. Support ser of HTTP operations (GET, POST, etc).

HTTParty automatic parses JSON or XML into Ruby Hashes

```shell
# Check if it is installed
gem list httparty
# Check if it is installed
gem install httparty
```

### Usage
* Include HTTParty module => 
* Can specify:
    - base_uri for request.
    - default_params (API developar key for example).
    - format
Build a test with [Coursera API](https://api.coursera.org/api/catalog.v1/courses):

```ruby
require 'httparty'
require 'pp'

class Coursera
  include HTTParty

  # main API URI
  base_uri 'https://api.coursera.org/api/catalog.v1/courses'
  # fields returned. q: as perameter for search
  # fields: filter returned fields
  default_params fields: "smallIcon,shortDescription", q: "search"
  # exchange format
  format :json

  # define method to query by a parameter term
  def self.for term
    get("", query: { query: term})["elements"]
  end
end
# Call class method passing term
pp Coursera.for "python"
```

## Create Model with the API
Add to Gemfile.
```ruby
# Gemfile
gem 'httparty', '0.13.5'
```

```bash
# Run bundle
bundle install

# install controller
rails g controller courses index
```

At `app/models` create `coursera.rb`

```ruby
#require 'httparty' # No need to require thanks to Bundler
class Coursera
  include HTTParty
  
  base_uri 'https://api.coursera.org/api/catalog.v1/courses'
  default_params fields: "smallIcon,shortDescription", q: "search"
  format :json

  def self.for term
    get("", query: { query: term})["elements"]
  end
end
```


## Bundler
[Bundler](http://bundler.io/) manage Gems dependencies in Rails inside Gemfile at `/app` root folder.

```shell
# install
bundle install
# ...or...
bundle
# update
bundle update
# call the bundled gems as if they were installed into the systemwide RubyGems repository.
bundle exec
```


## Gemfile
```shell
#install gem (without version installs the latest)
gem "nokogiri"
#install gem specific version
gem "rails", "3.0.0.beta3"
gem "rack", ">=1.0"             # up than
gem "rack", ">=1.0", "< 2.0"    # between
gem "rack", "~>1.0"             # pessimistic version constraint
                                # up the firt number to the next so is equivalent to previous ">=1.0", "< 2.0"

# Some times require and Gem has different names
gem 'sqlite3-ruby', require: 'sqlite3'
```

### Group Gem
```ruby
group :development, :test do
  gem 'byebug'
  gem 'web-console', '~> 2.0'
  gem 'spring'
end

group :production do
  gem 'pg'
  gem 'rails_12factor'
end
```

### Gemfile.lock
Actual gem versions the app is using.

## CSS
Main app layout lives at `app/views/layout`. App assets lives at `app/assets/`

```erb
<!DOCTYPE html>
<html>
<head>
  <title>App</title>
  <%= stylesheet_link_tag    'application', media: 'all', 'data-turbolinks-track' => true %>
  <%= javascript_include_tag 'application', 'data-turbolinks-track' => true %>
  <%= csrf_meta_tags %>
</head>
<body>
  <%= yield %>
</body>
</html>
```

Alternate class with cycle modifier
```erb
<table border="1">
  <tr>
    <th>Image</th>
    <th>Name</th>
    <th>Description</th>
  </tr>
  <% @courses.each do |course| %>
    <tr class=<%= cycle('even', 'odd') %>>
      <td><%= image_tag(course["smallIcon"])%></td>
      <td><%= course["name"] %></td>
      <td><%= course["shortDescription"] %></td>
    </tr>
  <% end %>
</table>
```

## Parameters
Use params to retrieve a value.
```ruby
class CoursesController < ApplicationController
  def index
    # Get param looking_for
    @search_term = params[:looking_for] || 'jhu'
    @courses = Coursera.for(@search_term)
  end
end
```

## Routing with root path
```ruby
Rails.application.routes.draw do
  get 'courses/index'

  # Specify root path
  root 'courses#index'

  #...
```

## Deploy to Heroku
PaaS - Platform as a Service. It needs: 
* [Heroku](https://www.heroku.com/)
* [Heroku toolbelt](https://toolbelt.heroku.com/)

```bash
# Install Toolbelt
wget -O- https://toolbelt.heroku.com/install-ubuntu.sh | sh

# Go to app dir
cd /app/dir
# login 
heroku login

# Create APP and automatically Git remote
heroku create app-name

# Push to Heroku
push heroku master
```

Heroku and sqlite dependencies 
```ruby
source 'https://rubygems.org'
# Bundle edge Rails instead: gem 'rails', github: 'rails/rails'
gem 'rails', '4.2.0'
# Use postgresql as the database for Active Record
# Use SCSS for stylesheets
gem 'sass-rails', '~> 5.0'
# Use Uglifier as compressor for JavaScript assets
gem 'uglifier', '>= 1.3.0'
# Use CoffeeScript for .coffee assets and views
gem 'coffee-rails', '~> 4.1.0'
# See https://github.com/sstephenson/execjs#readme for more supported runtimes
gem 'therubyracer', platforms: :ruby

# Use jquery as the JavaScript library
gem 'jquery-rails'
# Turbolinks makes following links in your web application faster. Read more: https://github.com/rails/turbolinks
gem 'turbolinks'
# Build JSON APIs with ease. Read more: https://github.com/rails/jbuilder
gem 'jbuilder', '~> 2.0'
# bundle exec rake doc:rails generates the API under doc/api.
gem 'sdoc', '~> 0.4.0', group: :doc
gem 'sqlite3', group: :development

# Use ActiveModel has_secure_password
# gem 'bcrypt', '~> 3.1.7'

# Use Unicorn as the app server
# gem 'unicorn'

# Use Capistrano for deployment
# gem 'capistrano-rails', group: :development

group :production do
    gem 'pg'
    gem 'rails_12factor'
end

group :development, :test do
  # Call 'byebug' anywhere in the code to stop execution and get a debugger console
  gem 'byebug'

  # Access an IRB console on exception pages or by using <%= console %> in views
  gem 'web-console', '~> 2.0'

  # Spring speeds up development by keeping your application running in the background. Read more: https://github.com/rails/spring
  gem 'spring'
end

gem 'httparty', '0.13.5'

```

## Blackbox Testing
* [Repo](https://github.com/jhu-ep-coursera/fullstack-course1-module3-blackbox-testing) for bootstraping blackbox test.
* [Capybara](https://github.com/jnicklas/capybara)
* [PhantonJS](http://phantomjs.org/)

## Debugging
* [Byebug](https://github.com/deivid-rodriguez/byebug)
* [Web Console](https://github.com/rails/web-console)
```ruby
# Gemfile
gem 'byebug'
gem 'web-console', '~> 2.0'
```

## Events on files
* [Guard](https://github.com/guard/guard): watch files
* [Guard LiveReload](https://github.com/guard/guard-livereload): browser notification
* [Rack LiveReload](https://github.com/johnbintz/rack-livereload): livereloda without addons (not working!!).
```bash
# Init Guard through Bundler 
bundle exec guard
```

## Learning resources
* [Learn Rails - Tutorial Point](http://www.tutorialspoint.com/ruby-on-rails/)rails-scaffolding.htm

## Credit
Example code gracefully provided at Coursera's [Ruby on Rails Introduction - Johns Hopkins University](https://www.coursera.org/learn/ruby-on-rails-intro/) course.