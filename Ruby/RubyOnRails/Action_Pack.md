# Action Pack
ActionController + ActionView = AP. Action Pack is Controller and View work together to let you interact with resources in the Model layer.

## Table of contents
- [REST (Representational State Transfer)](#rest-representational-state-transfer)
- [Rails REST Convention](#rails-rest-convention)
- [Examining the 7](#examining-the-7)
- [Validate params](#validate-params)
- [Partials](#partials)
- [Forms](#forms)
- [Layouts](#layouts)
- [Create iReviewed App](#create-ireviewed-app)
- [Nested Resources](#nested-resources)
- [Embed Nested Resources](#embed-nested-resources)
- [Authentication](#authentication)
- [Sessions and cookies](#sessions-and-cookies)
- [Lock APP](#lock-app)
- [Authorization](#authorization)
- [Pagination](#pagination)
- [Deploy to Heroku](#deploy-to-heroku)
- [SSL](#ssl)
- [Credit](#credit)


## REST (Representational State Transfer)
 * [REST Web Services](http://www.xfront.com/REST-Web-Services.html).

REST is all about resources. You should be to able to:
1. List available resources.
2. Show a specific resource.
3. Destroy an existing resource.
4. Provide a way to create a new resource.
5. Create a new resource.
6. Provide a way to update an existing resource.
7. Update an existing resource.

## Rails REST Convention

The 7 things to be done:

```ruby
class PostsController < ApplicationController
  # GET /posts
  def index

  # GET /posts/1
  def show

  # GET /posts/new
  def new
  
  # GET /posts/1/edit
  def edit
  
  # POST /posts
  def create

  # PATCH/PUT /posts/1.json
  def update
  
  # DELETE /posts/1
  def destroy
end

```

## Examining the 7

### Index

```ruby
# /app/controllers/posts_controller.rb

class PostsController < ApplicationController
  
  # ...

  # GET /posts
  # GET /posts.json
  def index
    @posts = Post.all
  end

  # ... 
end
```

```erb
<!-- at /app/views/posts/index.html.erb -->
<p id="notice"><%= notice %></p>

<h1>Listing Posts</h1>

<table>
  <thead>
    <tr>
      <th>Title</th>
      <th>Content</th>
      <th colspan="3"></th>
    </tr>
  </thead>

  <tbody>
    <% @posts.each do |post| %>
      <tr>
        <td><%= post.title %></td>
        <td><%= post.content %></td>
        <td><%= link_to 'Show', post %></td>
        <td><%= link_to 'Edit', edit_post_path(post) %></td>
        <td><%= link_to 'Destroy', post, method: :delete, data: { confirm: 'Are you sure?' } %></td>
      </tr>
    <% end %>
  </tbody>
</table>

<br>

<%= link_to 'New Post', new_post_path %>

```

### Show

```ruby
# /app/controllers/posts_controller.rb
class PostsController < ApplicationController
  
  # ...

  # GET /posts/1
  # GET /posts/1.json
  def show
    @post = Post.find(params[:id])
  end
  
  # ... 
end
```

```erb
<!-- at /app/views/posts/show.html.erb -->
<p id="notice"><%= notice %></p>

<p>
  <strong>Title:</strong>
  <%= @post.title %>
</p>

<p>
  <strong>Content:</strong>
  <%= @post.content %>
</p>

<%= link_to 'Edit', edit_post_path(@post) %> |
<%= link_to 'Back', posts_path %>
```

### New

```ruby
# /app/controllers/posts_controller.rb

class PostsController < ApplicationController
  
  # ...
  
  # GET /posts/new
  def new
    @post = Post.new
  end

  # ... 
end
```

```erb
<!-- at /app/views/posts/new.html.erb -->
<h1>New Post</h1>

<%= render 'form' %> <!-- partial -->

<%= link_to 'Back', posts_path %>
```

### Edit

```ruby
# /app/controllers/posts_controller.rb

class PostsController < ApplicationController
  
  # ...

  # GET /posts/1/edit
  def edit
  end

  # ... 
end
```

```erb
<!-- at /app/views/posts/edit.html.erb -->
<h1>Editing Post</h1>

<%= render 'form' %>

<%= link_to 'Show', @post %> |
<%= link_to 'Back', posts_path %>
```


Hint: You can obtain the model object’s persisted state using `object.persisted?` or `object.new_record?` to help determine if it is new or being edited.

### Create

```ruby
# /app/controllers/posts_controller.rb

class PostsController < ApplicationController
  # ...

  # POST /posts
  # POST /posts.json
  def create
    @post = Post.new(post_params)

    respond_to do |format|
      if @post.save
        format.html { redirect_to @post, notice: 'Post was successfully created.' }
        format.json { render :show, status: :created, location: @post }
      else
        format.html { render :new }
        format.json { render json: @post.errors, status: :unprocessable_entity }
      end
    end
  end

  # ... 
end
```

```erb
<!-- at /app/views/posts/ -->
```

### Update

```ruby
# /app/controllers/posts_controller.rb

class PostsController < ApplicationController
  
  # ...

  # PATCH/PUT /posts/1
  # PATCH/PUT /posts/1.json
  def update
    respond_to do |format|
    if @post.update(post_params)
      format.html { redirect_to @post, notice: 'Post was successfully updated.' }
      format.json { render :show, status: :ok, location: @post }
    else
      format.html { render :edit }
      format.json { render json: @post.errors, status: :unprocessable_entity }
    end
  end
  # ... 
end
```

* edit/update is very similar to new/create except there is an id of an existing resource.
* Strong parameters apply to updating a resource as well as creating one.

### Destroy
Destroy and redirect with a message? Ex: "Post created! You rock!".

Uses redirect_to posts_url with :notice (can be :alert too):

```ruby
# /app/controllers/posts_controller.rb

class PostsController < ApplicationController
  
  # ...
  
  # DELETE /posts/1
  # DELETE /posts/1.json
  def destroy
    @post.destroy
    respond_to do |format|
      format.html { redirect_to posts_url, notice: 'Post was successfully destroyed.' }
      format.json { head :no_content }
    end
  end

  # ... 
end
```

## Validate params
```ruby
# /app/controllers/posts_controller.rb
class PostsController < ApplicationController
  before_action :set_post, only: [:show, :edit, :update, :destroy]

  # ...
  private
  # Use callbacks to share common setup or constraints between actions.
  def set_post
    @post = Post.find(params[:id])
  end

  # Never trust parameters from the scary internet, only allow the white list through.
  def post_params
    params.require(:post).permit(:title, :content)
  end
  # ... 
end
```



## Partials
Snippets of code to use across multiple templates. 
* Name with underscore _.
* Rendered with render 'partialname' (no underscore). Accepts as argument a hash of local variables used in the partial.

###Rendering partials

<%= render @post %> will render a partial in app/views/posts/_post.html.erb and automatically assign a local variable post.
```erb
<%= render @posts %>

<!-- is equivalent to -->
<% @posts.each do |post| %>
  <%= render post %>
<% end %>
```


## Forms
```erb
<%= form_for(@post) do |f| %>
  <% if @post.errors.any? %>
    <div id="error_explanation">
      <h2><%= pluralize(@post.errors.count, "error") %> prohibited this post from being saved:</h2>

      <ul>
      <% @post.errors.full_messages.each do |message| %>
        <li><%= message %></li>
      <% end %>
      </ul>
    </div>
  <% end %>

  <div class="field">
    <%= f.label :title, "Heading" %><br>
    <%= f.text_field :title, placeholder: "Have a great title?" %>
  </div>
  <div class="field">
    <%= f.label :content %><br>
    <%= f.text_area :content, size: "10x3" %>
  </div>
  <div class="actions">
    <%= f.submit %>
  </div>
<% end %>
```

### Form Helpers

* [Helpers](http://api.rubyonrails.org/classes/ActionView/Helpers/).


* form_for: generates a form tag. Rails uses POST by default
  - Your password is not passed as part of your URL
  - Anything that will modify should be a POST and not GET
* f.label:
  - Outputs HTML label tag for the provided attribute.
  - To customize label description, pass in a string as a second parameterForm
* f.text_field
  - Generates input type=“text” field
  - Can Use :placeholder 
* f.text_area
  - Similar to f.text_field, but for a text area instead of a text
  - field input (default: 40 cols x 20 rows)
* f.date_select
  - Set of select tags (year, month, day) pre-selected for accessing an attribute in the DB. Many formatting options.
DateHelper.htmlForm Helpers
* Others
  - search_field
  - telephone_field
  - url_field
  - email_field
  - number_field
  - range_field
* f.submit: Submit button
  * Accepts the name of the submit button as its first argument(If not provided create one based on the model and type of action.

## Layouts
* Layout named `application.html.erb` is applied by default as a shell for any view template.

### Specific layout by Controller
* Layout that matches the name of a controller is applied if present (overriding 1. above)
* You can use `layout` method inside controller (outside any action) to set a layout for the entire controller.

### Specific layout by Action
Call to render inside the action:
```ruby
render layout: 'my_layout'
```

### Not to use layout
```ruby
render layout: false
```


## Create iReviewed App
```shell
rails new i_reviewed
```

### Model
1. Reviewers: name, password_digest
2. Books: name, author, reviewer_id
3. Notes: title, note, book_id

### Scaffold
```shell
rails g model reviewer name password_digest -q
rails g model book name author reviewer:references -q
rails g model note title note:text book:references -q
```

### Specify associations
```ruby
#app/models/reviewer.rb
class Reviewer < ActiveRecord::Base
  has_many :books
end

#app/models/book.rb
class Book < ActiveRecord::Base
  belongs_to :reviewer
  has_many :notes, dependent: :destroy
end

#app/models/note.rb
class Note < ActiveRecord::Base
  belongs_to :book
end
```

### Seeding data
```ruby
Reviewer.destroy_all
Book.destroy_all

Book.create! [
  { name: "Eloquent Ruby", author: "Russ Olsen" },
  { name: "Beginning Ruby", author: "Peter Cooper" },
  { name: "Metaprogramming Ruby 2", author: "Paolo Perrotta" },
  { name: "Design Patterns in Ruby", author: "Russ Olsen" },
  { name: "The Ruby Programming Language", author: "David Flanagan" }
]

100.times { |index| Book.create! name: "Book#{index}", author: "Author#{index}" }

eloquent = Book.find_by name: "Eloquent Ruby"
eloquent.notes.create! [
  { title: "Wow", note: "Great book to learn Ruby"},
  { title: "Funny", note: "Doesn't put you to sleep"}
]

reviewers = Reviewer.create! [
  { name: "Joe", password: "abc123" },
  { name: "Jim", password: "123abc" }
]

Book.all.each do |book|
  book.reviewer = reviewers.sample
  book.save!
end
```

```bash 
rake db:migrate
```

### Scaffold A Controller With Views
```bash
rails g scaffold_controller book name author
```

### Config Routes
```ruby
Rails.application.routes.draw do
  resources :books
  root to: "books#index" 
end
```


### Put flash messages into layout

```erb
<!DOCTYPE html>
<html>
<head>
  <title>IReviewed</title>
  <%= stylesheet_link_tag    'application', media: 'all', 'data-turbolinks-track' => true %>
  <%= javascript_include_tag 'application', 'data-turbolinks-track' => true %>
  <%= csrf_meta_tags %>
</head>
<body>

<% if logged_in? %>
  <div style='float: right;'>
    Logged in as <%= current_user.name %> |
    <%= link_to "Logout", logout_path, method: :delete %> 
  </div>
<% end %>

<!-- Flash messages -->
<% flash.each do |key, value| %>
  <p id='<%= key %>'><%= value %></p>
<% end %>

<%= yield %>

</body>
</html>

```


## Nested Resources
We can scaffold a regular RESTful controller for notes as well, but Note resource depends on the book resource. Rails calls such secondary resources "Nested resources".

### Create empty controller
```bash
rails g controller notes
```

### Config routes for nesting

```ruby
Rails.application.routes.draw do
  root to: "books#index" 

  resources :books do
    resources :notes
  end
end
```

Check Rake routes to see effects:
```bash
rake routes
```

### Actions
* Probably don’t need all seven actions in the notes controller.
* The form for creating a note (usually provided by the new action) will be provided on the “book show” page.
* Create and destroy actions is all we need.

#### Restrict routes
```ruby
Rails.application.routes.draw do
  root to: "books#index" 

  resources :books do
    resources :notes, only: [:create, :destroy]
  end
end

# now are available
# book_notes_path GET
# book_note_path GET
# new_book_note_path GET
# book_notes_path POST
# edit_book_note_path GET
# book_notes_path PUT/PATCH
# book_notes_path DELETE

# At views we could:
# <%= link_to 'New Note Item', new_book_note_path(@book) %>
```

#### Nested Controller
```ruby
class NotesController < ApplicationController
  before_action :set_book, only: [:create, :destroy]

  def create
    @note = @book.notes.new(note_params)
    if @note.save
      redirect_to @book, notice: "Note successfully added!"
    else
      redirect to @book, alert: "Unable to add note!"
    end
  end

  def destroy
    @note = @book.notes.find(params[:id])
    @note.destroy
    redirect_to @book, notice: "Note deleted!"
  end

  private
    def set_book
      @book = Book.find(params[:book_id])
    end

    def note_params
      params.require(:note).permit(:title, :note)
    end
end
```

## Embed Nested Resources

### Helpers: content_tag
[content_tag](http://api.rubyonrails.org/classes/ActionView/Helpers/TagHelper.html#method-i-content_tag) generate HTML content.

```bash
irb > helper.content_tag :p, "Hello there"
#=><p>Hello there<p>
irb > helper.content_tag(:div, helper.content_tag(:p, "Nested element"), class: "world")
#=><div class=\"world\"><p>Nested element</p></div>
```

```erb
<div class="book">
  <%= content_tag :span, "#{@book.name} (#{@book.author})", class: "book-title" %>
  <%= link_to 'Edit', edit_book_path(@book) %>
</div>

<%= link_to 'Back', books_path %>
```

### Add notes to books

#### ex 1
```erb
<div class="book">
  <%= content_tag :span, "#{@book.name} (#{@book.author})", class: "book-title" %>
  <%= link_to 'Edit', edit_book_path(@book) %>
</div>

<div><%= simple_format note.note %></div><!-- format new lines as <br> -->
<%= link_to 'Back', books_path %>
```
#### ex 2
```erb
<div class="book">
  <%= content_tag :span, "#{@book.name} (#{@book.author})", class: "book-title" %>
  <%= link_to 'Edit', edit_book_path(@book) %>
</div>

<div id="notes"><!-- using partials -->
  <%= render @book.notes %>
</div>

<div><!-- adding form to create notes -->
  <%= form_for([@book, @book.notes.new]) do |f| %>
    <div class="field">
      <%= f.label :title %>
      <%= f.text_field :title %>
    </div>
    <div class="field">
      <%= f.label :note %>
      <%= f.text_area :note, size: "25x5" %>
    </div>
    <div class="actions">
      <%= f.submit "Add New Note" %> 
    </div>
  <% end %> 
</div>

<%= link_to 'Back', books_path %>
```


## Authentication

`has_secure_password` to the rescue!
  
1.  Enable bcrypt-ruby (Gemfile) and run `bundle install`.
2.  Make sure password_digest is table column.
3.  Account for password (not password_digest) inside strong parameters list in the controller if you plan to use mass assignment when creating users.
4.  No need for password column in the table (virtual attribute).

```ruby
#app/models/reviewer.rb
class Reviewer < ActiveRecord::Base
  has_secure_password
  has_many :books
end
```

### How it work
```bash
rails c

irb> Reviewer.column_name
irb> Reviewer.create! name: "Joe", password: "abc123"

# See password
irb> joe = Reviewer.find_by name: "Joe"
irb> joe.authenticate("xcvxcvx")
#=> false
irb> joe.authenticate("abc123")
#=> #<Reviewer id:1, name "Joe", ...
```


## Sessions and cookies
HTTP is a stateless protocol... [Cookies and Sessions](http://guides.rubyonrails.org/security.html#what-are-sessions-questionmark) to the rescue (keep state).

### Sessions in Rails
Session is created and made available through a session hash.

#### Create a RESTful sessions controller
```bash
rails g controller sessions new create destroy -q
```

#### Config routes sessions controller
```ruby
Rails.application.routes.draw do
  root to: "books#index" 

  resources :books do
    resources :notes, only: [:create, :destroy]
  end
  # Sessions routes
  resources :sessions, only: [:new, :create, :destroy] 
  # We can think of new action as login page and destroy as a logout page
  # Let’s map login/logout routes to make this more clear
  get "/login" => "sessions#new", as: "login"
  delete "/logout" => "sessions#destroy", as: "logout"
end
```

```ruby
# Check routes to see result
rake routes
```

More at [Custom Routes](http://guides.rubyonrails.org/routing.html).

### Sessions controller and view
```ruby
# app/controllers/sessions_controller.rb
class SessionsController < ApplicationController
  skip_before_action :ensure_login, only: [:new, :create]
  def new
    # Login Page - new.html.erb
  end

  def create
    reviewer = Reviewer.find_by(name: params[:reviewer][:name])
    password = params[:reviewer][:password]

    if reviewer && reviewer.authenticate(password)
      session[:reviewer_id] = reviewer.id
      redirect_to root_path, notice: "Logged in successfully"
    else
      redirect_to login_path, alert: "Invalid username/password combination"
    end
  end

  def destroy
    reset_session # wipe out session and everything in it
    redirect_to login_path, notice: "You have been logged out"
  end
end
```

```erb
<!-- app/views/sessions/new.html.erb -->
<h1>Login</h1>

<%= form_for(:reviewer, url: sessions_path) do |f| %>

  <div class="field"><%= f.label :name %> <br/> <%= f.text_field :name %></div>

  <div class="field"><%= f.label :password %> <br/> <%= f.password_field :password %></div>

  <div class="actions"><%= f.submit "Login" %></div>
<% end %>
```

## Lock APP

We can have a before_action in the ApplicationController (from which all the other controllers inherit) that will make you login if you are not yet logged in.
If everything is blocked off we can override before_action with
skip_before_action.

```ruby
# app/controllers/aplication_controller.rb
class ApplicationController < ActionController::Base
  # Prevent CSRF attacks by raising an exception.
  # For APIs, you may want to use :null_session instead.
  protect_from_forgery with: :exception

  before_action :ensure_login
  helper_method :logged_in?, :current_user

  protected
    def ensure_login
      # Always go to login page unless session contains
      # reviewer_id
      redirect_to login_path unless session[:reviewer_id]
    end

    def logged_in?
      session[:reviewer_id] # nil is false
    end

    def current_user
      @current_user ||= Reviewer.find(session[:reviewer_id])
    end
end

```

```ruby
# app/controllers/sessions_controller.rb
class SessionsController < ApplicationController
  skip_before_action :ensure_login, only: [:new, :create]
  def new
    # Login Page - new.html.erb
  end

  def create
    #...
  end

  def destroy
    #...
  end
end
```

## Authorization

### Security Helpers
Add `logged_in?` and `current_user` ApplicationController and make them available as helper methods to all controllers and views via helper_method.


```ruby
# app/controllers/aplication_controller.rb
class ApplicationController < ActionController::Base
  # Prevent CSRF attacks by raising an exception.
  # For APIs, you may want to use :null_session instead.
  protect_from_forgery with: :exception

  before_action :ensure_login
  helper_method :logged_in?, :current_user

  protected
    def ensure_login
      # Always go to login page unless session contains
      # reviewer_id
      redirect_to login_path unless session[:reviewer_id]
    end

    def logged_in?
      session[:reviewer_id] # nil is false
    end

    def current_user
      @current_user ||= Reviewer.find(session[:reviewer_id])
    end
end
```

Then, we can add logic to application.html.erb for logging out and information about the user who is logged in.

```erb
<!DOCTYPE html>
<html>
<head>
  <title>IReviewed</title>
  <%= stylesheet_link_tag    'application', media: 'all', 'data-turbolinks-track' => true %>
  <%= javascript_include_tag 'application', 'data-turbolinks-track' => true %>
  <%= csrf_meta_tags %>
</head>
<body>

<% if logged_in? %>
  <div style='float: right;'>
    Logged in as <%= current_user.name %> |
    <%= link_to "Logout", logout_path, method: :delete %> 
  </div>
<% end %>

<% flash.each do |key, value| %>
  <p id='<%= key %>'><%= value %></p>
<% end %>

<%= yield %>

</body>
</html>
```

### Scope based on user

```ruby
# app/controllers/aplication_controller.rb
class BooksController < ApplicationController
  before_action :set_book, only: [:show, :edit, :update, :destroy]
  
  # ...

  private
    # Use callbacks to share common setup or constraints between actions.
    def set_book
      @book = current_user.books.find(params[:id])
    end
    
    # ...
end
```


## Pagination
1. Shut down your server.
2. Include `will_paginate gem`.
3. Run `$ bundle`.
4. One line of code in the controller.
5. One line of code in your view.
6. Restart your server.

```ruby
# app/controllers/books_controller.rb
class BooksController < ApplicationController
  before_action :set_book, only: [:show, :edit, :update, :destroy]

  def index
    @books = current_user.books.paginate(page: params[:page], per_page: 10)
  end
    
  # ...
end
```

```erb
<!-- at /app/views/books/index.html.erb -->
<h1>Listing Books</h1>
<!-- ... -->
<%= will_paginate @books %>
<!-- ... -->
```


## Deploy to Heroku

### App
```bash
bundle --without production
git st
heroku login
heroku create ireview-books
git push heroku master
```

### Database (migration and seed on Heroku)
```bash
heroku run rake db:migrate
heroku run rake db:seed
```

## SSL
At `config/environments/production.rb`:

```ruby
Rails.application.configure do
  #...
  
  # Force all access to the app over SSL, use Strict-Transport-Security, and use secure cookies.
  config.force_ssl = true
  
  #...
```

Commit and push to Heroku.

## Credit
Example code gracefully provided at Coursera's [Ruby on Rails with Active Record and Action Pack - Johns Hopkins University](https://www.coursera.org/learn/ruby-on-rails-intro/) course.