# Web Services with MongoDB

## Table of contents
- [NoSQL](#nosql)
- [Types of NoSQL](#types-of-nosql)
- [MongoDB](#mongodb)
- [Ruby on Rails & MongoDB](#ruby-on-rails--mongodb)
- [Mongo CRUD](#mongo-crud)
- [DAO Rails & MongoDB](#dao-rails--mongodb)
- [Scaffolding with Rails](#scaffolding-with-rails)
- [Aggregation with MongoDB](#aggregation-with-mongodb)
- [$match](#$match)
- [$unwind](#$unwind)
- [Schema Design](#schema-design)
- [Relationships](#relationships)
- [Grid FS (storing and retrieving large files)](#grid-fs-storing-and-retrieving-large-files)
- [Geolocation](#geolocation)
- [Create Indexes ](#create-indexes-)
- [Mongoid (Object-Document-Mapper (ODM) for MongoDB)](#mongoid-object-document-mapper-odm-for-mongodb)
- [Mongoid querying: create, update, upsert, delete](#mongoid-querying-create-update-upsert-delete)
- [Relations definitions](#relations-definitions)
- [Constraint and Validation](#constraint-and-validation)
- [Queries (find)](#queries-find)
- [Queries (Where)](#queries-where)
- [Pluck and Scope](#pluck-and-scope)
- [Scaffolding](#scaffolding)
- [Scaffolding Demo APP](#scaffolding-demo-app)
- [Web Services](#web-services)
- [Resources in REST](#resources-in-rest)
- [URIs](#uris)
- [Access URI (HTTParty)](#access-uri-httparty)
- [Nested URIs](#nested-uris)
- [Query Parameters and Payload](#query-parameters-and-payload)
- [REST Methods](#rest-methods)
- [Idempotence](#idempotence)
- [Representations](#representations)
- [Versioning](#versioning)
- [Content Negotiation](#content-negotiation)
- [Headers and Status](#headers-and-status)
- [Cache](#cache)
- [OAuth2](#oauth2)
- [Assembly](#assembly)
- [Devise Integration](#devise-integration)
- [Integrated Authentication (with Doorkeeper)](#integrated-authentication-with-doorkeeper)
- [OAuth2 integration ](#oauth2-integration-)
- [Credit](#credit)


## NoSQL
* [Cassandra](http://cassandra.apache.org/).
* [Amazon DymanoDB](https://aws.amazon.com/es/dynamodb/).
* [Apache HBase](https://hbase.apache.org/).
* [Couchbase](http://www.couchbase.com/).
* [CouchDB](http://couchdb.apache.org/).
* [MemcaheDB](http://memcachedb.org/).
* [MongoDB](https://www.mongodb.com/).
* [Redis](http://redis.io/).

### Pros
* Performance and stability.
* Unstructured format (agile).

## Types of NoSQL
###Value can be String or JSON/ Key-value hash:
* Dynamo.
* Redis.
* Memcached.

```json
ID | Attributes
1234 John Doe
1235 {
    "Name": "Godfather",
    "Genre": "Drama",
    "Actor": "Robert DeNiro",
    "Director": "Francis Ford Coppola"
}
```

### Stores documents based up of tagged elements
Persistent and query-able:
* MongoDB.
* CouchDB.

### Uses flat structure, but with keys stored in columns rather than rows
* Cassandra.
* Hbase.

## MongoDB
MongoDB is an open source, document-oriented database designed with both scalability and developer agility in mind

### Terms
RDBMS | Mongo
--- | ---
Database | Database
Table, View | Collection
Row | JSON Document
Column | Field
Index | Index
Join | Embedded Document / Linking across Document
Foreign Key | Reference
Partition Key | Shard

### Sample Querys

#### SQL
```sql
# Create
CREATE TABLE movies( movieId int NOT NULL AUTO_INCREMENT, name VARCHAR(30), rating VARCHAR(6), PRIMARY KEY (movieId ) )
# Select
SELECT * FROM movies 
# Update
UPDATE movies SET rating = "NR" WHERE movieId = 101 
# Delete
DELETE FROM movies WHERE rating = "R"
```

#### Mongo
```json
// Create
db.movies.insert({
    "id": 10,
    "name": "Titanic",
    "rating": "R"
} )
// Select
db.movies.find()
// Update
db.movies.update( {"id": 101 }, { $set: { rating: "NR" } } )
// Delete
db.movies.remove( { "rating": "R" } )
```

## Ruby on Rails & MongoDB
* [Ruby Driver](http://docs.mongodb.org/ecosystem/tutorial/ruby-driver-tutorial/).
* [Mongoid](http://docs.mongodb.org/ecosystem/tutorial/ruby-mongoid-tutorial/).


### MongoDB Ruby Driver Setup
```bash
# install gems
gem update --system
gem install mongo
gem install bson_ext
```
```ruby
# Using gem
require mongo
```

### MongoDB shell
```bash
# download test db 
# http://media.mongodb.org/zips.json
irb
irb > require "mongo"
irb > Mongo::Logger.logger.level=::Logger::INFO
irb > db = Mongo::Client.new('mongodb://0.0.0.0:27017')
irb > db=db.use('test')
irb > db.database.name
irb > db.database.collection_names
irb > db[:zips].find.first
```


## Mongo CRUD

### Create/Insert
* insert_one: insert one document to collection.
* insert_many: insert multiple documents to the collection.
```ruby
# Insert One
db[:zips].insert_one(
    :_id => "100",
    :city => "city01",
    :loc => [ -76.05922700000001, 39.564894],
    :pop => 4678,:state => "MD")

# Insert Many
db[:zips].insert_many([
    {   
        :_id => "200", 
        :city => "city02",
        :loc => [ -74.05922700000001, 37.564894 ],
        :pop => 2000, :state => "CA" 
    },
    { 
        :_id => "201", :city => "city03",
        :loc => [ -75.05922700000001,35.564894 ],
        :pop => 3000, :state => "CA" }
    ])

# Tests
db[:zips].find(:city => "city01").count
db[:zips].find(:city => "city02").count
```

### Read

#### Find
Find documents by an array of criteria or hashes:
```ruby
basic find
db[:zips].find(:city => "BALTIMORE")
# find first
db[:zips].find.first
db[:zips].find(:state => "MD").first
# find distinct
db[:zips].find.distinct(:state)find by example
# basic find (hash)
db[:zips].find(:city => "GERMANTOWN").count
db[:zips].find(:city => "GERMANTOWN").first
pp db[:zips].find(:city => "GERMANTOWN",:state => "NY").first
pp db[:zips].find(:city => "GERMANTOWN",
:state => "MD").firstCursor
# print all
db[:zips].find().each { |r| puts r }
# pretty printing
require 'pp'
db[:zips].find().each { |r| pp r }
```

#### Find By - Regex
```ruby
# Will retrieve cities containing X in their names (5 documents only)
db[:zips].find(:city => {:$regex => 'X'}).limit(5).each {|r| pp r}
# Displays cities ending with X
db[:zips].find(:city => {:$regex => 'X$'}).limit(5).each {|r| pp r}
# Displays cities starting with XFind By - Regex
db[:zips].find(:city => {:$regex =>'^X'}).projection({:_id => false}).limit(5)
.to_a.each {|r| pp r}
# Displays cities that match the regex (A to E)
db[:zips].find(:city => {:$regex => '^[A-E]'}).projection({:_id => false}).limit(5).to_a.each {|r| pp r}
```

#### $exist
Will check to see of the document exists when the boolean is true.
```ruby
db[:zips].find(:city => {:$exists =>true}).projection({:_id =>false}).limit(3).to_a.each {|r| pp r}$not
```
#### $not
```ruby
# Selects the documents that do not match the <operator-expression>
db[:zips].find(:pop =>{'$not' => {'$gt' => 9500}}).projection({_id:false}).limit(20).to_a.each{|r| pp r}$type
```

#### $type
```ruby
#Selects the documents where the value of the field is an instance of the specified numeric BSON type.
db[:zips].find({:state=> {:$type => 2}}).first
```

### Edit

#### replace_one
```ruby
db[:zips].insert_one(:_id => "100", :city => "citytemp", :loc => [ -76.05922700000001,39.564894 ], :pop => 4678, :state => "MD" )
db[:zips].find(:_id =>"100").replace_one(:_id => "100", :city =>"city02", :loc => [ -78.22, 36.22 ], :pop =>2000, :state => "MD" )
db[:zips].find(:_id => "100").to_aupdate_one
```

#### update_one
```ruby
db[:zips].find(:_id => "100").update_one(:$set => {:city => "name2"})
db[:zips].find(:_id => "100").to_aupdate_many
```

#### update_many
```ruby
db[:zips].find(:state => 'MD').count
db[:zips].find(:state =>'MD').update_many(:$set => {:state =>'XX'})
db[:zips].find(:state => 'MD').count
db[:zips].find(:state => 'XX').countdelete_one
```


### Delete

#### delete_one
```ruby
db[:zips].find(:city => 'name2').count
db[:zips].find(:city =>'name2').delete_one()
db[:zips].find(:city => 'name2').countdelete_many
```

#### delete_many
```ruby
db[:zips].find(:state => 'MD').delete_many()upsert
```

#### Upsert
If upsert is true and no document matches the query criteria, update() inserts a single document.
```ruby
db[:zips].find(:city => "ODENVILLE1").count
db[:zips].find(:city => "ODENVILLE2").count
db[:zips].find(:city =>"ODENVILLE1").update_one({:$set => {:city =>"ODENVILLE2"}}, :upsert => true)
db[:zips].find(:city => "ODENVILLE1").count
db[:zips].find(:city => "ODENVILLE2").count
```

### Sort
```ruby
# 1 for Ascending
db[:zips].find.limit(3).sort({:city => 1 }).each { |r| pp r}
# -1 for Descending
db[:zips].find.limit(3).sort({:city => -1 }).each { |r| pp r}
```

### Pagination
* skip(n)- tells mongodb that it should skip ‘n’ results.
* limit(n)- instructs mongodb that it should limit the result length to ‘n’ .results
```ruby
# retrieves a list of the first three documents for us
db[:zips].find.limit(3).each { |r| pp r}
# Skips the first three and retrieves the next three
db[:zips].find.skip(3).limit(3).each { |r| pp r}
```

### Projections
Selecting only necessary data rather than selecting the whole data of a document.
```ruby
db[:zips].find({ :state => "MD"}).projection(state:true, _id:false).first
db[:zips].find({:state => "MD"}).projection(state:1, _id:0).first
```

## DAO Rails & MongoDB
The Data Object Class (DAO) is simulating a middleware ORM that is consistent with the Rails ActiveModel framework. Consistent with ORM operations find, insert, update, delete methods in the DAO class.

* connects to MongoDB.
* Access to the collection.

### ORM Mapping
* all - maps to find
* find – maps to find(hash)
* save - maps to insert_one
* update – maps to update_one
* destroy – maps to delete_one

#### all
Return all documents in zips collection

Paging and Sorting:
```ruby
self.all(prototype={}, sort={:population=>1}, offset=0,limit=100)
```

#### find id
Return a specific instance for a given id.

#### save
Save the state of the current instance.

#### Update(updates)
Accepts as hash and performs an update on those values after accounting for any name mappings.

#### destroy
Delete the document from the database that is associated with the instance's :id.

## Scaffolding with Rails
```bash
# rails g scaffold_controller ModelName fields1 field2 
#(if not provided :type is :string)
rails g scaffold_controller Zip id city state population:integer
```


```ruby
class ZipsController < ApplicationController
  before_action :set_zip, only: [:show, :edit, :update, :destroy]

  # GET /zips
  # GET /zips.json
  def index
    #@zips = Zip.all
    #@zips = Zip.paginate(params)

    args=params.clone                      #update a clone of params
    args[:sort]=get_sort_hash(args[:sort]) #replace sort with hash
    @zips = Zip.paginate(args)
    @locations = zip_markers @zips
  end

  # GET /zips/1
  # GET /zips/1.json
  def show
    near_zips=@zip.near(params[:max_miles], params[:min_miles] ,params[:limit])
    @locations=zip_markers near_zips
    p @locations
  end

  # GET /zips/new
  def new
    @zip = Zip.new
  end

  # GET /zips/1/edit
  def edit
  end

  # POST /zips
  # POST /zips.json
  def create
    @zip = Zip.new(zip_params)

    respond_to do |format|
      if @zip.save
        format.html { redirect_to @zip, notice: 'Zip was successfully created.' }
        format.json { render :show, status: :created, location: @zip }
      else
        format.html { render :new }
        format.json { render json: @zip.errors, status: :unprocessable_entity }
      end
    end
  end

  # PATCH/PUT /zips/1
  # PATCH/PUT /zips/1.json
  def update
    respond_to do |format|
      if @zip.update(zip_params)
        format.html { redirect_to @zip, notice: 'Zip was successfully updated.' }
        format.json { render :show, status: :ok, location: @zip }
      else
        format.html { render :edit }
        format.json { render json: @zip.errors, status: :unprocessable_entity }
      end
    end
  end

  # DELETE /zips/1
  # DELETE /zips/1.json
  def destroy
    @zip.destroy
    respond_to do |format|
      format.html { redirect_to zips_url, notice: 'Zip was successfully destroyed.' }
      format.json { head :no_content }
    end
  end

  private
    # Use callbacks to share common setup or constraints between actions.
    def set_zip
      @zip = Zip.find(params[:id])
    end

    # Never trust parameters from the scary internet, only allow the white list through.
    def zip_params
      params.require(:zip).permit(:id, :city, :state, :population)
    end

    #create a hash sort spec from query param
    #sort=state:1,city,population:-1
    #{state:1, city:1, population:-1}
    def get_sort_hash(sort)
      order={}
      if (!sort.nil?)
        sort.split(",").each do |term|
          args=term.split(":")
          dir = args.length<2 || args[1].to_i >= 0 ? 1 : -1
          order[args[0]] = dir
        end
      end
      return order
    end

    def zip_markers zips
      #build the marker for the center of the map
      if @zip
        center_marker = Gmaps4rails.build_markers(@zip) do |zip, marker|
          marker.lat zip.latitude
          marker.lng zip.longitude
          marker.infowindow zip.city
          marker.picture(:url=> "/images/marker32.png",
                         :width=>  32,
                         :height=> 32)
        end
      end

      #build markers for map
      marked_zip=@zip.nil?
      locations = Gmaps4rails.build_markers(zips) do |zip, marker|
        marker.lat zip.latitude
        marker.lng zip.longitude
        marker.infowindow zip.city
        #add special marker for target city
        if @zip && zip.id==@zip.id
          marker.picture center_marker[0][:picture]
          marked_zip=true
        end
      end

      #add target city of left out
      locations << center_marker[0]  if !marked_zip
      return locations
    end
end
```



### Paginate

Controller passes the value to model:
```ruby
# app/controllers/zips_controller.rb
def index
    @zips = Zip.paginate(:page => params[:page])
end
```


```ruby
# app/models/zip.rb

  #implememts the will_paginate paginate method that accepts
  # page - number >= 1 expressing offset in pages
  # per_page - row limit within a single page
  # also take in some custom parameters like
  # sort - order criteria for document
  # (terms) - used as a prototype for selection
  # This method uses the all() method as its implementation
  # and returns instantiated Zip classes within a will_paginate
  # page
  def self.paginate(params)
    Rails.logger.debug("paginate(#{params})")
    page=(params[:page] ||= 1).to_i
    limit=(params[:per_page] ||= 30).to_i
    offset=(page-1)*limit
    sort=params[:sort] ||= {}

    #get the associated page of Zips -- eagerly convert doc to Zip
    zips=[]
    all(params, sort, offset, limit).each do |doc|
      zips << Zip.new(doc)
    end

    #get a count of all documents in the collection
    total=all(params, sort, 0, 1).count
    
    WillPaginate::Collection.create(page, limit, total) do |pager|
      pager.replace(zips)
    end    
  end
```


## Aggregation with MongoDB
SQL | Mongo
--- | ---
WHERE | $match
GROUP BY | $group
SELECT | $project
ORDER BY | $sort
LIMIT | $limit
SUM() | $sum
COUNT() | $count

```ruby
db[:zips].find().aggregate([
    {:$match=>{:state =>'NY'}}, 
    {:$group=>{ :_id=>'NY',:population=>{:$sum=>'$pop'}}}
]).to_a
```


### $project
Reshapes a document stream by renaming, adding, or removing fields.
Commonly used to create computed values or sub- documents.

```ruby
# $project – Include Specific Fields
# Displays all four fields (_id, city, state, pop) :<field> => 1 OR :<field> => true - specifies the inclusion of the field
db[:zips].find.aggregate([{:$project=>{ :_id=>1, :city=>1, :state=>1,:pop=>1 }},{:$limit=>5}]).each {|r| pp r}

# $project – Exclude Specific Fields
# Displays two fields (state and population)
# _id is excluded
db[:zips].find.aggregate( [{:$project=>{ :_id=>0, :state=>1, :pop=>1 }},{:$limit=>5}]).each {|r| pp r}

# $project – Alter Data
# State in lower case
db[:zips].find.aggregate( [{:$project=>{:_id=>0, :state=>{:$toLower=>'$state'}, :pop=>1 }},{:$limit=>5}]).each {|r| pp r}
```

### $group
Similar to “GROUP BY” in RDBMS.

Command | Description
--- | ---
$sum | Returns a sum for each group.
$avg | Returns an average for each group.
$max | Returns the highest expression value for each group.
$min | Returns the lowest expression value for each group.
$push | Returns an array of expression values for each group.
$addToSet | Returns an array of unique expression values for each
group. Order of the array elements is undefined.

#### $sum
Returns a sum for each group. Ignores non-numeric values.
```ruby
db[:zips].find.aggregate([{ :$group=>{:_id=>'$state', :population=>{:$sum=>'$pop'}}}, :$limit=>5]).each {|r| pp r}
```

#### $avg
Returns an average for each group. Ignores non-numeric values.
```ruby
db[:zips].find.aggregate([{ :$group=>{ :_id=>'$state', :ave_population=>{:$avg=>'$pop'} }},{:$limit=>5}]).each {|r| pp r}
```

#### $max
Returns the highest expression value for each group.
```ruby
db[:zips].find().aggregate([{:$group=>{ :_id=>'$state', :max_zip=>{:$max=>'$pop'}}}, {:$limit=>5}]).each {|r| pp r}
```

#### $min
Returns the lowest expression value for each group.
```ruby
db[:zips].find().aggregate([{:$group=>{ :_id=>'$state', :min_zip=>{:$min=>'$pop'}}}, {:$limit=>5}]).each {|r| pp r }
```

#### $push
Returns an array of all values that result from applying an expression to each document in a group of documents that share the same group by key.
```ruby
db[:zips].find().aggregate( [{:$group=>{ :_id=>{:city=>'$city', :state=>'$state'}, :zips=>{:$push=>'$_id'}}}, {:$limit=>15}]).each {|r| pp r}
```

#### $addToSet
Returns an array of all unique values that result from applying an expression to each document in a group of documents that share the same group by key. Order of the elements in the output array is unspecified.
```ruby
db[:zips].find.aggregate([{:$group=>{:_id= 0,:zips=> {:$push=>"$state"}}} ]).first
db[:zips].find.aggregate([{:$group=>{:_id=>0,:zips => {:$addToSet=>"$state"}}}]).first
```


## $match
Pipes the documents that match its conditions to the next operator in the pipeline.
The $match query syntax is identical to the read operation query syntax (find).
Minimize the amount of processing down the pipe.

```ruby
# with find
db[:zips].find({:state=>'DE'}).first
# with match
db[:zips].find().aggregate([ {:$match=>{:state=>'DE'}}]).first

db[:zips].find().aggregate([
    {:$match=>{:sta te=>'NY'}}, 
    {:$group=>{ :_id=>'$city', :population=>{:$sum=>'$pop'}}},
    {:$project=>{ :_id=>0, :city=>'$_id', :population=>1}}, 
    {:$sort=>{ :population=>- 1 }},
    {:$limit=>5}]).each {|r| pp r}
```


## $unwind
Deconstructs an array field from the input documents to output a document for each element.

```ruby
# Ex 1
db[:zips].find().aggregate([{:$match=>{:city=>'ELMIRA'}}, {:$group=>{:_id=>{:city=>'$city',:state=>'$state'},:zips=>{:$addToSet=>'$_id'}}}]).each {|r|pp r}

# Ex 2
db[:zips].find().aggregate([{:$match=>{:city=>'ELMIRA'}}, {:$group=>{:_id=>{:city=>'$city',:state=>'$state'},:zips=>{:$addToSet=>'$_id'}}}, {:$unwind=>'$zips'}]).each {|r| pp r}
```

## Schema Design

### MongoDB – BSON Types

* Supports Rich Document Embedded/Linked data (joins) and no Foreign Key makes it very flexible.
* Schema-Less but there is still structure

Name | Type
--- | ---
String | Characters (UTF-8) 
Integer | Numeric value (32 or 62 bit) 
Boolean | True/False 
Double | Decimal numbers 
Min/Max | Can be used to compare against lowest and highest value 
Arrays | List of values 
Timestamp | Time (added/updated) 
Object | Embedded documents
Null | Null values
Symbol | Similar to String (“ef#12”)
Date | Date/Time (Unix format)
Object | Id Document’s id
Binary | Data Store binary data
Code | Java Script
Regular | Expression Store regular expression (/%path%)


### 3rd Normal Form Problem
Movie ID | Title | Genre | Actor | Director
--- | --- | --- | --- | --- | ---
101 | The Departed | Drama | Matt Damon | Martin Scorsese
102 | The Godfather | Drama | Marlon Brando | Francis Ford Coppola
103 | The Bourne Supremacy | Thriller | Matt Damon | Paul Greengrass

Modifying the Actor name ex: Matt Damon to Mathew Damon makes necessary change all the fields: more work for the DB and inconsistence danger. We have duplications.

#### Solution
Movie ID | Title | Genre | Actor | Director
--- | --- | --- | --- | --- | ---
101 | The Departed | Drama | 1 | 1
102 | The Godfather | Drama | 2 | 2
103 | The Bourne Supremacy | Thriller | 1 | 3

Actor ID | Name 
--- | ---
1 | Matt Damon 
2 | Marlon Brando 

Director ID | Name
--- | ---
1 | Martin Scorsese
2 | Francis Ford Coppola
3 | Paul Greengrass


## Relationships

### One to One
Employee --> Address
```json
{
    "id" : "jdoe",
    "name" : "John Doe"
    "address" : {
        "street" : "40 Named Street"
        "city" : "Some City"
        "state" : "CO"
        "zip" : "12345"
    }
}
```

### One to Many
```json
{
    "id" : "jdoe",
    "name" : "John Doe"
    "addresses" : [
        {
            "street" : "40 Named Street"
            "city" : "Some City"
            "state" : "CO"
            "zip" : "12345"
        },
        {
            "street" : "40 Other Named Street"
            "city" : "Some City"
            "state" : "CA"
            "zip" : "70707"
        },
    ]
}
```

### Many to Many
Movie --> Actor
* Movie can have many actors.
* Actor can be in many movies.

Two design approaches
* Embedding.
* Linking.

#### Many to Many Embeding
```json
{
    "simplePlot": "A prospector goes to the Klondike in search of gold and finds it and more.",
    "title": "The Gold Rush",
    "type": "Movie",
    "urlIMDB": "http://www.imdb.com/title/tt0015864",
    "urlPoster": "http://ia.media-imdb.com/images/M/MV5BMzYzMDQyNzA4NV5BMl5BanBnXkFtZTYwNDU5NDU5._V1_SY317_CR10,0,214,317_AL_.jpg",
    "votes": 61537,
    "year": 1925,
    "roles": [
        {
            "actorName": "Charles Chaplin",
            "character": "The Lone Prospector",
            "main": true,
            "urlCharacter": "http://www.imdb.com/character/ch0015243",
            "urlPhoto": "http://ia.media-imdb.com/images/M/MV5BNDcwMDc0ODAzOF5BMl5BanBnXkFtZTgwNTY2OTI1MDE@._V1_UX32_CR0,0,32,44_AL_.jpg",
            "urlProfile": "http://www.imdb.com/name/nm0000122",
            "_id": "nm0000122"
        },
        {
            "actorName": "Mack Swain",
            "character": "Big Jim McKay",
            "main": true,
            "urlCharacter": "http://www.imdb.com/character/ch0356007",
            "urlPhoto": "",
            "urlProfile": "http://www.imdb.com/name/nm0841501",
            "_id": "nm0841501"
        },
        {
            "actorName": "Tom Murray",
            "character": "Black Larsen",
            "main": true,
            "urlCharacter": "http://www.imdb.com/character/ch0499210",
            "urlPhoto": "",
            "urlProfile": "http://www.imdb.com/name/nm0615306",
            "_id": "nm0615306"
        }
    ]
}
```

#### Many to Many Linking
We have separates collections Movie/Actor
```json
{
    "simplePlot": "A prospector goes to the Klondike in search of gold and finds it and more.",
    "title": "The Gold Rush",
    "type": "Movie",
    "urlIMDB": "http://www.imdb.com/title/tt0015864",
    "urlPoster": "http://ia.media-imdb.com/images/M/MV5BMzYzMDQyNzA4NV5BMl5BanBnXkFtZTYwNDU5NDU5._V1_SY317_CR10,0,214,317_AL_.jpg",
    "votes": 61537,
    "year": 1925,
    "roles": [
        "nm0000122",
        "nm0841501",
        "nm0615306"
    ]
}
```


#### Linking vs. Embedding
* Embedded documents are easy to handle for clients
* Linking – more flexible but extra work at application level.

## Grid FS (storing and retrieving large files)

GridFS - specification for storing and retrieving large files: images, audio files, video files, etc.
* File system to store the chunks.
* Data is stored within MongoDB collections.
* File are broken in to multiple chunks and a metadata of 255KB.

Uses two collections:
* fs.files - files’s metadata
* fs.chunks – file chunks


* 10 MB document of query-able data with normalized types as embedded – embedded
* 18 MB document of query-able data with normalized types as embedded – embedded/linked
* 10 MB document of image blob plus query-able data – store as regular document
* 18 MB document of mostly an image blob and a small amount of query-able data - GridFS

### Chunk field
Name | Mission
--- | ----
_id | The unique Object ID of the chunk.
files_id | The _id of the “parent” document, as specified in the files collection.
n | The sequence number of the chunk. GridFS numbers all chunks, starting with 0.
data | The chunk’s payload as a BSON binary type.

### Files metadata

Name | Mission
--- | ----
_id | The unique Object ID of the document.
length | Size of the documents in bytes
chunkSize | The size of each chunk. Default size is 255KB
uploadDate | The date the document was first stored by GridFS.
md5 | MD5 hash
filename | A human-readable name for the document.
contentType | A valid MIME type for the document.
aliases | An array of alias string
metadata | Any additional information you want to store.

### Grid FS - Demo

```ruby
require 'mongo';
Mongo::Logger.logger.level = ::Logger::DEBUG

class GridfsLoader
  # performs the detailed work to create a new MongoDB connection
  def self.create_connection(mongo_url=nil, db_name=nil)
    mongo_url ||= "mongodb://192.168.32.2:27017"
    db_name ||= "test"
    STDERR.puts "creating connection #{mongo_url} #{db_name}"
    db = Mongo::Client.new(mongo_url)
    db.use(db_name)
  end

  #creates and/or returns a MongoDB connection cached in the class
  def self.mongo_client(mongo_url=nil, db_name=nil)
    @@db ||= create_connection(mongo_url, db_name)
  end

  #sets up the object instance with a MongoDB connection
  def initialize(mongo_url=nil, db_name=nil)
    @@db = self.class.mongo_client(mongo_url=nil, db_name=nil)
  end

  # reads the contents of the file and inserts into GridFS along
  # with some optional metadata. The :_id of the file is returned.
  def import_grid_file(file_path, name=nil, contentType=nil, metadata=nil)
    os_file=File.open(file_path,'rb')
    description = {}
    description[:filename]=name       if !name.nil?
    description[:contentType]=name    if !contentType.nil?
    description[:metadata] = metadata if !metadata.nil?

    grid_file = Mongo::Grid::File.new(os_file.read, description )
    @@db.database.fs.insert_one(grid_file)
  end

  # locates a file in GridFS based on a criteria. Use {} if you 
  # don't care which file you get. Use :_id=>id if you have the 
  # id of the file
  def find_grid_file(criteria) 
    @@db.database.fs.find_one(criteria)
  end

  # provide the grid_file object (from find_grid_file) and a path
  # to write the data to
  def export_grid_file(grid_file, file_path) 
    os_file=File.open(file_path,'wb')
    grid_file.chunks.reduce([]) { |x,chunk| os_file << chunk.data.data }
  end

  # provide the grid_file object (from find_grid_file) to delete 
  # that file
  def delete_grid_file(grid_file)
    @@db.database.fs.delete_one(grid_file)
  end
end
```

```shell
irb > require './gridfs_loader.rb'
#=> true
irb> GridfsLoader.mongo_client 
creating connection mongodb://192.168.32.2:27017 test
D, [2016-07-27T15:09:59.394932 #39] DEBUG -- : MONGODB | Adding 192.168.32.2:27017 to the cluster.
=> #<Mongo::Client:0x47313136197280 cluster=192.168.32.2:27017>
irb> os_file=File.open("./image3.jpg")
irb> os_file=File.open("./image2.jpg")
=> #<File:./image2.jpg>
irb> grid_file= Mongo::Grid::File.new(os_file.read)
=> #<Mongo::Grid::File:0x47313137537180 filename=>
irb> grid_file.methods
=> # Show methods
irb> grid_file.id
=> BSON::ObjectId('5798d3fd5b3f170027000000')
irb> grid_file.content_type
=> "binary/octet-stream"
irb> grid_file.filename
=> nil
irb(main):007:0> grid_file.id
=> BSON::ObjectId('5798d3fd5b3f170027000000')
irb(main):008:0> grid_file.content_type
=> "binary/octet-stream"
grid_file.chunks.count    
=> 29
# assign
irb> c=GridfsLoader.mongo_client
=> #<Mongo::Client:0x47313136197280 cluster=192.168.32.2:27017>
# insert
irb> r=c.database.fs.insert_one(grid_file)
# ... transaction data
# => BSON::ObjectId('5798d3fd5b3f170027000000')
irb> stored_file=c.database.fs.find_one(:_id => BSON::ObjectId("5798d3fd5b3f170027000000"))
irb> os_file2=File.open("./exported_copy.jpg", 'w')
# => <File:./exported_copy.jpg>
#irb> stored_file.chunks.reduce([]) { |x,chunk| os_file2 << chunk.data.data }
# => <File:./exported_copy.jpg>


# Adding data to images and find by this
# data. 
irb>description={}
# => {}
irb>description[:filename]="myfile.jpg"
irb>description[:content_type]="image/jpeg"
irb>description[:metadata]={:author=>"Pablo", :topic=>"nice spot"}}
irb> grid_file= Mongo::Grid::File.new(os_file.read, description)
irb> r=c.database.fs.insert_one(grid_file)
irb> c.database.fs.find_one(:content_type => "image/jpeg", filename=>'myfile.jpg')
irb> c.database.fs.find_one(:"metadata.author" => "Pablo", :"metadata.topic" => {:$regex => "spot"})
irb> pp c.database.fs.find_one(:content_type => "image/jpeg", filename=>'myfile.jpg').first

# Delete file
irb> id=c.database.fs.find(:"metadata.author" => "Pablo").first[:id]
irb> r=c.database.fs.find(:_id => id).delete_one
irb> r=c.database.fs.find.delete_many # carefully!!
```



## Geolocation

* Geo – refers to Earth.
* Location – refers to the position on the Earth

Allows create, index and query geospatial data.

### 2dsphere Index
Data stored in spherical surface (2dsphere index). Supports queries that calculate geometries on an earth-like sphere.

Default datum for an earth-like sphere is WGS84. Coordinate-axis order is longitude, latitude.

Supports all MongoDB geospatial queries:
* queries for inclusion, intersection, and proximity
 
Stored as [GeoJSON](http://geojson.org/):
```json
// object – always list coordinates as [longitude, latitude]
{ type: "Point", coordinates: [ 40, 5 ] }
```

GeoJSON Objects: Point, MultiPoint, LineString, MultiLineString, Polygon,
MultiPolygon, Geometry Collection

### 2dsphere Index Creation
```ruby
db[:zips].indexes.create_one({:loc=>Mongo::Index::GEO2DSPHERE})
```

A 2dsphere index was added to the MongoDB collection index: `:loc contains [latitude, longitude]`.

### 2dsphere Index Queries
```ruby
db[:zips].find(:city => 'BALTIMORE').first
#=> Output: {"_id"=>"21201", "city"=>"BALTIMORE", "loc"=>[-76.625203, 39.29463], "pop"=>16256, "state"=>"MD"}

# Let’s find some cities close to Baltimore using $near using $minDistance and $maxDistance
db[:zips].find(:loc =>
    {:$near=>{
            $geometry=>{:type=>"Point", :coordinates=>[-76.625203, 39.29463]},
            $maxDistance=>10000,
            $minDistance=>50000
        }
    }).limit(5).each{ |r| pp r}

```

### Example Zips & Gmaps
* [Github Repo](https://github.com/jhu-ep-coursera/fullstack-course3-module2-geozips).

## Create Indexes 
Special data structures that store a small portion of the collection’s data set in an easy to traverse form.
* Index has a pointer to document in the collection.
* Ordered set that allows searches – uses B-Tree.
* Supports indexes with multiple fields.

### Single Field Index
By default, all collections have an index (_id field).
MongoDB supports indexes that contain either a single field or multiple fields depending on the operations that this index-type supports.

#### explain Command
Provides information on the query plan and, optionally, the execution statistic.

```shell
pp db[:zips].find(:state => 'MD').explain
#=> Parsed Query
#=> Total documents scanned.
```

### Compound Indexes
create_many – will create multiple indexes on a collection.

```ruby
db[:zips].indexes.create_many([ 
    { :key => { state: 1 } },
    { :key => { city: 1 }}])
])
```

#### create_one Command
Creates indexes on collections.
```shell
db[:zips].indexes.create_one({ :state=>1})
#=> Above example creates an ascending index on the field state

pp db[:zips].find(:state => 'MD').explain
```


### List All Indexes
Helpful to fetch all indexes:
```ruby
db[:zips].indexes.each do |index| p index
```

### create_one command
Creates indexes on collections.
```ruby
# ascending index on the field statedrop_one command.
db[:zips].indexes.create_one({ :state => 1})
```

### drop_one command 
will delete the selected id.
```ruby
db[:zips].indexes.drop_one('state_1')
```

### drop_all command
Delete the indexes (user created).
```ruby
db[:zips].indexes.drop_all
```

### unique index
Reject duplicate values for the indexed field.
```ruby
db[:users].indexes.create_one( { "user_id":1 }, { unique: true } )
```

### TTL index
Remove documents from a collection after a time: machine generated event data, logs, and session, ...

```ruby
# removed from the collection after 3600 seconds
db[:zips].indexes.create_one({ :state => 1 }, {expireAfterSeconds: 3600})
```

### sparse index
Contains entries for document even if the index field contains a null value.

```ruby
db[:users].indexes.create_one( { "user_id":1 }, { sparse: true } )
```

Note: By default, unique is false on MongoDB indexes.

## Mongoid (Object-Document-Mapper (ODM) for MongoDB)
Object-Document-Mapper (ODM) for MongoDB written in Ruby.
Mix of Active Record and MongoDB's schema-less and performant document-based design.
Dynamic queries, and atomic modifier operations.

### Installation
Add Mongoid to your Gemfile:
```ruby
gem 'mongoid', '~> 5.0.1'
```

### Mongoid Configuration
```bash
rails g mongoid:config
# generates a config file
# Edit - appname/config/mongoid.yml
```

Example mongoid.yml
```yml
development:
  # Configure available database clients. (required)
  clients:
    # Defines the default client. (required)
    default:
      # Defines the name of the default database that Mongoid can connect to.
      # (required).
      database: movies_development
      # Provides the hosts the default client can connect to. Must be an array
      # of host:port pairs. (required)
      hosts:
        - localhost:27017
      options:
        # Change the default write concern. (default = { w: 1 })
        # write:
        #   w: 1

        # Change the default read preference. Valid options for mode are: :secondary,
        # :secondary_preferred, :primary, :primary_preferred, :nearest
        # (default: primary)
        # read:
        #   mode: :secondary_preferred
        #   tag_sets:
        #     - use: web

        # The name of the user for authentication.
        # user: 'user'

        # The password of the user for authentication.
        # password: 'password'

        # The user's database roles.
        # roles:
        #   - 'dbOwner'

        # Change the default authentication mechanism. Valid options are: :scram,
        # :mongodb_cr, :mongodb_x509, and :plain. (default on 3.0 is :scram, default
        # on 2.4 and 2.6 is :plain)
        # auth_mech: :scram

        # The database or source to authenticate the user against. (default: admin)
        # auth_source: admin

        # Force a the driver cluster to behave in a certain manner instead of auto-
        # discovering. Can be one of: :direct, :replica_set, :sharded. Set to :direct
        # when connecting to hidden members of a replica set.
        # connect: :direct

        # Changes the default time in seconds the server monitors refresh their status
        # via ismaster commands. (default: 10)
        # heartbeat_frequency: 10

        # The time in seconds for selecting servers for a near read preference. (default: 5)
        # local_threshold: 5

        # The timeout in seconds for selecting a server for an operation. (default: 30)
        # server_selection_timeout: 30

        # The maximum number of connections in the connection pool. (default: 5)
        # max_pool_size: 5

        # The minimum number of connections in the connection pool. (default: 1)
        # min_pool_size: 1

        # The time to wait, in seconds, in the connection pool for a connection
        # to be checked in before timing out. (default: 5)
        # wait_queue_timeout: 5

        # The time to wait to establish a connection before timing out, in seconds.
        # (default: 5)
        # connect_timeout: 5

        # The timeout to wait to execute operations on a socket before raising an error.
        # (default: 5)
        # socket_timeout: 5

        # The name of the replica set to connect to. Servers provided as seeds that do
        # not belong to this replica set will be ignored.
        # replica_set: name

        # Whether to connect to the servers via ssl. (default: false)
        # ssl: true

        # The certificate file used to identify the connection against MongoDB.
        # ssl_cert: /path/to/my.cert

        # The private keyfile used to identify the connection against MongoDB.
        # Note that even if the key is stored in the same file as the certificate,
        # both need to be explicitly specified.
        # ssl_key: /path/to/my.key

        # A passphrase for the private key.
        # ssl_key_pass_phrase: password

        # Whether or not to do peer certification validation. (default: false)
        # ssl_verify: true

        # The file containing a set of concatenated certification authority certifications
        # used to validate certs passed from the other end of the connection.
        # ssl_ca_cert: /path/to/ca.cert


  # Configure Mongoid specific options. (optional)
  options:
    # Includes the root model name in json serialization. (default: false)
    # include_root_in_json: false

    # Include the _type field in serialization. (default: false)
    # include_type_for_serialization: false

    # Preload all models in development, needed when models use
    # inheritance. (default: false)
    # preload_models: false

    # Raise an error when performing a #find and the document is not found.
    # (default: true)
    # raise_not_found_error: true

    # Raise an error when defining a scope with the same name as an
    # existing method. (default: false)
    # scope_overwrite_exception: false

    # Use Active Support's time zone in conversions. (default: true)
    # use_activesupport_time_zone: true

    # Ensure all times are UTC in the app side. (default: false)
    # use_utc: false
test:
  clients:
    default:
      database: movies_test
      hosts:
        - localhost:27017
      options:
        read:
          mode: :primary
        max_pool_size: 1

```

Hooks at `aplication.rb`
```ruby
require File.expand_path('../boot', __FILE__)

require 'rails/all'

# Require the gems listed in Gemfile, including any gems
# you've limited to :test, :development, or :production.
Bundler.require(*Rails.groups)

module Movies
  class Application < Rails::Application
    # Settings in config/environments/* take precedence over those specified here.
    # Application configuration should go into files in config/initializers
    # -- all .rb files in that directory are automatically loaded.

    # Set Time.zone default to the specified zone and make Active Record auto-convert to this zone.
    # Run "rake -D time" for a list of tasks for finding time zone names. Default is UTC.
    # config.time_zone = 'Central Time (US & Canada)'

    # The default locale is :en and all translations from config/locales/*.rb,yml are auto loaded.
    # config.i18n.load_path += Dir[Rails.root.join('my', 'locales', '*.{rb,yml}').to_s]
    # config.i18n.default_locale = :de

    #bootstraps mongoid within applications -- like rails console
    Mongoid.load!('./config/mongoid.yml')

    #mongoid gem configures mongoid as default model generator
    #this can make it explicit or switch back to active_record default
    #config.generators {|g| g.orm :mongoid}
    #config.generators {|g| g.orm :active_record}

    # Do not swallow errors in after_commit/after_rollback callbacks.
    config.active_record.raise_in_transactional_callbacks = true
  end
end
```

### Documents
Documents are the core objects in Mongoid `Mongoid::Document`. Documents can be stored in a collection or embedded in other documents.

```ruby
class Movie
  include Mongoid::Document
end
```

### Fields

```ruby
class Movie
  include Mongoid::Document
  include Mongoid::Timestamps

  field :title, type: String
  field :type, type: String
  field :rated, type: String
  field :year, type: Integer
  field :release_date, type: Date
  field :runtime, type: Measurement
  field :votes, type: Integer
  field :countries, type: Array
  field :languages, type: Array
  field :genres, type: Array
  # field with alias
  field :filmingLocations, as: :filming_locations, type: Array
  field :metascore, type: String
  field :simplePlot, as: :simple_plot,  type: String
  field :plot, type: String
  field :urlIMDB, as: :url_imdb,  type: String
  field :urlPoster, as: :url_poster, type: String
  field :directors, type: Array
  field :actors, type: Array

  ...
end
```

#### Scaffold
```shell
rails g model - command
rails g model Actor name birth_name data_of_birth:Date height:Measurement bio:text
```

#### Fields Types
--- | --- | --- | --- | ---
Array | Boolean | DateTime | Hash
BigDecimal | Date | Float | Integer
BSON | Range | Regexp | String
Symbol | Time | TimeWithZone

#### Timestamps
Timestamp information is not added by default in Mongoid -- as it is within ActiveRecord. created_at and updated_at fields done via:

```ruby
Mongoid::Timestamps mixin.
```

##### Update timestamp
```bash
rocky26=Movie.create(:_id=>"tt9000031", :title=>"Rocky XXVI")
rocky26.created_at
# => Thu, 03 Dec 2015 22:28:23 UTC + 00:00
rocky26.updated_at
# => Thu, 03 Dec 2015 22:28:23 UTC + 00:00
rocky26.year=2015
rocky26.updated_at
# => Thu, 03 Dec 2015 22:28:23 UTC + 00:00 (show same bc still not saved)
rocky26.save
rocky26.updated_at
# => Thu, 10 Dec 2015 23:28:23 UTC + 00:00 (show same bc still not saved)
rocky26.touch
# => will update created_at

```


#### Field Aliases
filmingLocations mapped as filming_locations.
```ruby
class Movie
  # ...
    # Comply with rails naming convention
  field :filmingLocations, as: :filming_locations, type: Array
  # ...
end
```

#### Custom Fields
You can define custom types in Mongoid and determine how they are serialized and deserialized. Are 5 methods in total:
* initialize
* mongoize (instance method)
* mongoize, demongoize, evolve (class methods)
Example: `:runtime=>{:amount=>60, :units=> "min"}`

```ruby
class Measurement
  attr_reader :amount, :units

  def initialize(amount, units=nil)
    @amount=amount
    @units = units
    #normalize
    case 
    when @units == "meters" then @amount=(@amount/0.3048); @units="feet"
    end
  end

  def to_s
    case
    when @amount && @units then "#{@amount} (#{@units})"
    when @amount && !@units then "#{@amount}"
    else nil
    end
  end

  #creates a DB-form of the instance
  def mongoize
    @units ? {:amount => @amount, :units => @units} : {:amount => @amount}
  end

  #creates an instance of the class from the DB-form of the data
  def self.demongoize(object)
    case object
    when Hash then Measurement.new(object[:amount], object[:units])
    else nil
    end
  end

  #takes in all forms of the object and produces a DB-friendly form
  def self.mongoize(object) 
    case object
    when Measurement then object.mongoize
    else object
    end
  end

  #used by criteria to convert object to DB-friendly form
  def self.evolve(object)
    case object
    when Measurement then object.mongoize
    else object
    end
  end
end

```

#### store_in
Application type to Document type mapping. Location gets stored in to “places” collection.

```ruby
class Location
    include Mongoid::Document
    store_in collection "places"
    field :city, type: String
    field :state, type: String
    field :country, type: String
end
```

```shell
Place.count
#=> 388
loc = location.create( .... )
Place.count
#=> 389
```

## Mongoid querying: create, update, upsert, delete

### Create
Create a collection called movies
```ruby
movie = Movie.create(
    title: "Rocky",
    type: "Action",
    rated: "R",
    year: 1975
)
# save
movie.save
```

### Update
```ruby
movie.year = 1986
movie.save
```

Update attributes
```ruby
movie = Movie.new(
  title: "Rocky31",
  rated: "PG-13"
)
movie.update_attributes(:rated => "R")
```

### Upsert
If document exists is overwritten. If document does not exists is inserted.
```ruby
movie = Movie.new(
    title: "Rocky31",
    rated: "PG-13"
)

Movie.new(:_id=>"<ID>",:title=>"Rocky31", :rated=>"R").upsert
```

### Delete
```ruby
# delete a document
movie.delete
# delete all documents
Movie.delete_all
```

## Relations definitions

### 1:1 Embedded
Children are embedded in the parent document.

Parent of the relation must declare **embeds_one** macro to indicate it has one embedded child.

Document that is embedded uses **embedded_in**.

```ruby
class Place
  include Mongoid::Document
  # ....
  embedded_in :locatable, polymorphic: true 
  # ....
end
```

```ruby
class Writer
  include Mongoid::Document
  # ....
  embeds_one :hometown, as: :locatable, class_name: 'Place'
end
```


```ruby
class Actor
  include Mongoid::Document
  # ....
  embeds_one :place_of_birth, class_name: 'Place' , as: :locatable
end
```

#### Assignment
Example using Movies project.
```shell
# definition
actor=Actor.where(:place_of_birth =>{:$exist=>0}).first
oakland=Place.where(:city =>"Oakland"}).first
# assignment (create_xxx_)
actor.create_place_of_birth(oakland.attributes)
```

#### Polymorphic Relationships
Embedding the same document type in to several different parent type.

```ruby
class Place
  # ....
  embedded_in :locatable, polymorphic: true 
  # ....
end
```


#### Assignment
Example using Movies project.
```shell
# definition
writer=Writer.where(:hometown =>{:$exist=>0}).first
oakland=Place.where(:city =>"Oakland"}).first

# assignment
actor.create_hometown(oakland.attributes)

# show methods by field
writer.methods.grep /hometown/
# access
writer.hometown.id
writer.hometown?

# create nil values
place=writer.build_hometown
writer.has_hometown?
#=> true

# Unassign
writer.hometown = nil
writer.has_hometown?
#=> false

# Simple assignment doesn't embed the transient association 
writer.hometown
#=> nil
writer.hometown=oakland
writer.hometown
#=> oakland
pp Writer.collection.find(:_id=> writer.id).first # doesn't show hometown embed until save
writer.save
pp Writer.collection.find(:_id=> writer.id).first # shows embed
```

### *:1 Linked Relationhship
Child (class using the FK) uses **belongs_to** and parent optionally uses **has_many**. Without the `has_many` macro, the relationship becomes a uni-
directional.

Multiple directors (child) can have the same place of residence (parent).
```ruby
class Director
  include Mongoid::Document

  # ...

  belongs_to :residence, class_name: 'Place'
end

```

Example: 
```ruby
# director with no residence yet
director=Director.where(:residence=>{:$exists=>0}).first # =>..., residence_id: nil, ...
# print director ID
pp Direcor.collection.find(:id=>director.id).first
# assign Oakland to build relation
oakland=Place.where(:city =>"Oakland"}).first
director.residence=oakland
# save
director.save
pp Direcor.collection.find(:id=>director.id).first # =>..., residence_id: "Oakland, CA, USA", ...

# makes a JOIN to residence table
director=Director.where(director.id)
director.residence.state 
director.residence_id
director.residence_id
```

### 1:* Embedded
One to many relationships - children are embedded in the parent document are defined using Mongoid's **embeds_many** and **embedded_in**.
```ruby
class Movie
  include Mongoid::Document
  field :title, type: String
  
  # ... 
  embeds_many :roles, class_name:"MovieRole"
  # ... 
end
```

```ruby
class MovieRole
  include Mongoid::Document
  field :character, type: String
  # ... 
  embedded_in :movie
  # ... 
end
```

Example: 
```ruby
# create a movie
rocky25=Movie.create(:_id=>"tt9000000", :title=>"Rocky XXV")
# Add Roles to this movie. Create actor
stallone=Actor.where(:name=>{:$regex=>"Stallone"}).first
# Add Roles to this movie. Create rol
rocky=rocky25.roles.create(:_id=>stallone.id, :character=>"Rocky", :actorName=>"Sly", :main=>true)
#test
pp Movie.collection.find(:title=>"Rocky XXV").first

# Create second role
actor=Actor.first
role=MovieRole.new # create bland
role.id = actor.id
role.character="Challenger"
role.main=false
role.actor_name=actor.name
rocky25.roles << role # assignment

# test
pp Movie.collection.find(:title=>"Rocky XXV").first
```

Example all before save: 
```ruby
# create a movie
rocky26=Movie.create(:_id=>"tt9000001", :title=>"Rocky XXVI")

# Add Roles to this movie. Create rol
rocky=rocky26.roles.build(:_id=>stallone.id, :character=>"Rocky", :actorName=>"Sly", :main=>true)
# assign previous example role
rocky26.roles << role # assignment
#test
pp Movie.collection.find(:title=>"Rocky XXVI").first
# nil (need to save)
rocky26.save
# test
pp Movie.collection.find(:title=>"Rocky XXVI").first
# doc
```

### *:1 Embedded linked (annotated link)
(:roles._id=>damon.id).first # search nested rules ID 
1 side has a primary key and typically has no reference to the child within  he document. M side will typically host the foreign key.

```ruby
class MovieRole
  include Mongoid::Document
  field :character, type: String
  # ... 
  embedded_in :movie
  belongs_to :actor, foreign_key: :_id, touch: true 
  # ... 
end
```

```ruby
class Actor
  include Mongoid::Document
  field :name, type: String
  # ....
  #sort-of has_many roles:, class_name: 'MovieRole`
  def roles
    Movie.where(:"roles._id"=>self.id).map {|m| m.roles.where(:_id=>self.id).first}
  end  
  # ....
end
```


Example: 
```ruby
# find actor
damon=Actor.where(:name=>{$regex=>"Matt Da"}).first
# Get Damon acting by
movie=Movie.where(:roles._id=>damon.id).first # search nested rules ID matching actor ID
role=movie.roles.where(:id=>damon.id).first

# test
pp role.attributes
damon.roles.map{|role| "#{role.movie.title} => #{role.character}"}
```

Example fake role in a fake new movie: 
```ruby
# find actor
damon=Actor.where(:name=>{$regex=>"Matt Da"}).first
# fake movie
rocky26=Movie.create(:_id=>"tt9000015", :title=>"Rocky 26")
# add role
rocky=rocky26.roles.build(:_id=>damon.id, :character=>"Rocky", :actorName=>"Matt", :main=>true)
# test
pp Movie.collection.find(:title=>"Rocky 26").first

damon.roles.map{|role| "#{role.movie.title} => #{role.character}"}
# => shows Rocky 26
```

### 1:1 Linked (Recursive Relationship)
References are defined using Mongoid's **has_one** and **belongs_to** macros (ex: Movie -> sequel_to:Movie).

The parent document uses the has_one macro to indicate is has 1 referenced child. The document that is referenced in it uses belongs_to.

```ruby
class Movie
  include Mongoid::Document
  field :title, type: String
  #...
  # Using same collection
  has_one :sequel, foreign_key: :sequel_of, class_name:"Movie"
  belongs_to :sequel_to, foreign_key: :sequel_of, class_name:"Movie"
  #...
end
```


Example with Rocke 25 and 26: 
```ruby
# find actor
rocky25=Movie.where(:title=>"Rocky XXV").first
rocky26=Movie.where(:title=>"Rocky XXVI").first
# establish relation
rocky26.sequel_to=rocky25
rocky26.save

pp Movie.collection.find(:title=>"Rocky XXVI").first
#=> show sequel_id

# Navigate between relation elements
rocky26.sequel_to.title # show rocky 25
```


### M:M (Bi-Directional Relationship)
Many to many relationships where the inverse documents are stored in a separate collection. Both parent and child use Mongoid's **has_and_belongs_to_many** macro.
Foreign key IDs are stored as arrays on either side of the relation.

```ruby
class Movie
  include Mongoid::Document
  field :title, type: String
  # ...
  has_and_belongs_to_many :writers
  # ...
end
```

```ruby
class Writer
  include Mongoid::Document
  field :name, type: String
  # ...
  has_and_belongs_to_many :movies
  # ...
end
```

Example:
```ruby
# find writer
stone=Writer.where(:name=>{:$regex=>"Stone"}).first
# find writer movies
stone.movies.map{|m| m.tite}
# =>["Scarface", "Platoon"]
# Get hometown

```

Create hometown from reference in a movie
```ruby
stone=Writer.where(:name=>{:$regex=>"Stone"}).first
nyc=Place.where(:_id=>{:$regex=>"^New York, NY"}).first
stone.create_hometown(nyc.attributes)

platoon=Movies.where(:title=>"Platoon").first

# Navigate to writers info throught
platoon.writers.first.hometown.id
```

Create new movie:
```ruby
# create movie
rocky26=Movie.create(:_id=>"tt9000001", :title=>"Rocky XXVI")
pp Movie.collection.find(:title=>"Rocky 26").first
stone=Writer.where(:name=>{:$regex=>"Stone"}).first

# add writer to the collection
rocky26.writers << stone
pp Movie.collection.find(:_id=>rocky26.id).first
# "movie_ids" => [id,...]
pp Writer.collection.find(:_id=>stone.id).first
# "writer_ids" => [id,...]

# see all films Stone writed
stone.movies.map{|m| m.title}
# =>["Scarface", "Platoon", "Rocky XXVI"]
rocky26.writers.map{|w| w.name}
# =>["Oliver Stone"]

# delete movie
stone.movies.delete rocky26 # delete with cascade at joins
pp Movie.collection.find(:_id=>rocky26.id).first
# "movie_ids" => [id,removed rocky26]
pp Writer.collection.find(:_id=>stone.id).first
# "writer_ids" => [id,removed rocky26]
```


## Constraint and Validation
Constraints, Validations and Dependent Behavior – key in maintaining the state of your application data.
```ruby
class Director
  include Mongoid::Document
  # ...
  field :name, type: String
  # ...
  validates_presence_of :name # name is mandatory!
end

```

Mongoid supports **dependent options** to manage referenced associations.
Will instruct Mongoid to handle delete situations: `:delete, :destroy, :nullify, :restrict`.

### :default
Orphans the child document
* 1:1 and 1:M leaves the child with stale reference to the removed parent.
* M:M clears the child of the parent reference (acts like b:nullify)

### :nullify
Orphans the child document after setting the child foreign key to nil.

### :destroy
Remove the child document after running model callbacks on the child.
has_and_belongs_to_many :writers, dependent: :destroy
```ruby
class Movie
  #...
  has_and_belongs_to_many :writers, dependent: :destroy
  has_one :sequel, foreign_key: :sequel_of, class_name:"Movie", dependent: :destroy
  #...
end
```

### :delete
Remove the child document without running model callbacks on the child.
* M:M does not remove the child document from database (acts like :nullify)
 
### :restrict
Raise an error if a child references the parent being removed.

### delete vs destroy
* :delete – will only delete current object – straight delete in the database.
* :destroy- will delete current object and associated children record - analyzes the class you're deleting, determines what it should do for dependencies, runs through validations (callbacks - :before_destroy,
:after_destroy.)

### Triggers
```ruby
class Movie
  #...
  before_destroy do |doc|
    puts "before_destroy Movie callback for #{doc.id}, "\
         "sequel_to=#{doc.sequel_to}, writers=#{doc.writer_ids}"
  end
  after_destroy do |doc|
    puts "after_destroy Movie callback for #{doc.id}, "\
         "sequel_to=#{doc.sequel_to}, writers=#{doc.writer_ids}"
  end
  #...
end
```

```ruby
class Writer
  #...
  before_destroy do |doc|
    puts "before_destroy Writer callback for #{doc.id}, "\
        "movies=#{doc.movie_ids}"
  end
  after_destroy do |doc|
    puts "after_destroy Writer callback for #{doc.id}, "\
        "movies=#{doc.movie_ids}"
  end
  #...
end
```

### Constraints and Validation: Demo
[See video demo](https://www.coursera.org/learn/ruby-on-rails-web-services-mongodb/lecture/pgxBh/constraints-and-validation-demo).

```ruby
# Demo set-up
reload!
rocky30=Movie.create(:title=>"Rocky 30")
rocky31=Movie.create(:title=>"Rocky 31",:sequel_to=>rocky30)
writer=rocky30.writers.create(:name=>"A Writer")


rocky30.destroy
rocky31.destroy

# Data Cleanup
pp Movie.where(:title=>{:$regex=>"Rocky 3[0-1]"}).delete_all;
Writer.where(:name=>{:$regex=>"Writer"}).delete_all
```


## Queries (find)
### find
Find document/s by ID. Raise an error by default not ID matchs.
```ruby
Actor.find("nm0993498").birth_name
# multiple id query
Actor.find("nm0000006", "nm0000008")
```

### find_by
Find a document by the provided attributes
```ruby
# find_by: find by attributes
Movie.find_by(rated: "R")
Movie.find_by(title: "Rocky")
```

### find_or_create_by
Find a document by attributes. If not found, create and return a newly persisted one.
```ruby
Movie.find_or_create_by(title: "Titanic",year: "1997")
```

### find_or_initialize_by

Find a document by the provided attributes. If not found, initialize and return a new one (does not persist unless save).
```ruby
Movie.find_or_initialize_by(title: "Prisoners", year: "2013")
```

## Queries (Where)
### where (count, distinct)
```ruby
Movie.where(:title => "Rocky").count
# .gt greater than
Movie.where(:year.gt => 2000).distinct(:title)
# .lt less than
Movie.where(:year.lt => 2000).distinct(:title)
```

### where (first)
Get the first document. If no sort ascending _id sort.
```ruby
Movie.first
Movie.where(:rated=> "R").first
```

### where (exists?)
Will return true if 1 or more exist.
```ruby
Movie.exists?
Movie.where(:title => "Titanic").exists?
```

### where - :$exists and :$regex 
```ruby
writer=Writer.where(:hometown=>{ :$exists=>0}).first
damon=Actor.where(:name=>{ :$regex=>"Matt Da"}).firstwhere
```

### where – Geolocation query
Mongoid built-in ability to express and execute a Geolocation query.
```ruby
# 5 actors near to Silver Spring
silver_spring=Place.where(:city=>"Silver Spring", :state=>"MD").first
# query
Actor.near(:"place_of_birth.geolocation"=>silver_spring.geolocation).limit(5).each{|actor| "#{actor_name}, pob=#{actor.place_of_birth.id}"}
```

### where - first_or_create
Create and return a newly persisted one if not finded.
```ruby
Movie.where(:title => "Rocky20").first_or_create
```

### where - first_or_initialize
If not found, instantiate and return a new one (don't persist).
```ruby
Movie.where(:title =>"Rocky21").first_or_initialize
```

## Pluck and Scope
### pluck
Get all the non nil values for the provided field. Selected fields using a projection. Entire document is not returned.

```ruby
Movie.all.pluck(:title)

# Entire document is returned, grab 2 fields, rest is discarded
Movie.where(:rated=>"PG").map {|movie| [movie.title, movie.release_date]}

# Projection 2 fields only, entire document is not returned
Movie.where(:rated=>"PG").pluck(:title,:release_date)
Movie.where(:title.lt=>"A").pluck(:title)
```

### scope
Scopes provide a convenient way to reuse common criteria with more business domain style syntax.

#### Named scope
Named scopes are simply criteria defined at class load that are referenced by a provided name.

At model class:
```ruby
class Movie
  include Mongoid::Document
  field :title, type: String
  # ...
  scope :current, ->{ where(:year.gt=>Date.current.year-2) }
  # ...
end
```

Use scope:
```ruby
Movie.current.where(:rated=>"R").pluck(:title, :year)
```

#### Default scope
Default scopes can be useful when applying the same criteria to most queries, and you want something to be there by default.

```ruby
class Movie
  include Mongoid::Document
  field :title, type: String
  # ...
  default_scope ->{ where(:year => Date.current.year) }
  # ...
end
```


Using scope:
```ruby
Movie.all.count # => count just year=current_year
Movie.unscoped.all.count # => count all years
```

### OR and in
```ruby
# Union example with in:
Movie.where(:year.gt => 2014).in(title:["The Martian"]).pluck(:plot)
# or conditional operator
Movie.or({id: "tt3659388"},{title: "The Martian"}).pluck(:plot)
```


## Scaffolding
Mongoid is the default model generator. Movie Rails Application DemoBasic Steps
```shell
# Create project
$ rails new movies 
# enter project and add mongoid gem
$ cd movies
$ gem 'mongoid', '~> 5.0.0'
# bundle install 
$ bundle
# make mongoid default ORM/model generator 
$ rails g mongoid:config config/mongoid.yml
# start server
$ rails s
```

### Custom Classes and methods
* Measurement (represent anonymous block of information within the application): example: a movie runs 2 hours.
* Point (represent anonymous block of information within the application).
* initialize - normalized form -- independent of source formats.
* to_s - useful in producing formatted output.
* mongoize – creates a DB form of the instanceCustom.
* self.demongoize(object)- creates an instance of the class from the DB-form of the data.
* self.mongoize(object)- takes in all forms of the object and produces a DB-friendly form.
* self.evolve(object)- used by criteria to convert object to DB-friendly form.

```ruby
class Measurement
  attr_reader :amount, :units

  def initialize(amount, units=nil)
    @amount=amount
    @units = units
    #normalize
    case 
    when @units == "meters" then @amount=(@amount/0.3048); @units="feet"
    end
  end

  def to_s
    case
    when @amount && @units then "#{@amount} (#{@units})"
    when @amount && !@units then "#{@amount}"
    else nil
    end
  end

  #creates a DB-form of the instance
  def mongoize
    @units ? {:amount => @amount, :units => @units} : {:amount => @amount}
  end

  #creates an instance of the class from the DB-form of the data
  def self.demongoize(object)
    case object
    when Hash then Measurement.new(object[:amount], object[:units])
    else nil
    end
  end

  #takes in all forms of the object and produces a DB-friendly form
  def self.mongoize(object) 
    case object
    when Measurement then object.mongoize
    else object
    end
  end

  #used by criteria to convert object to DB-friendly form
  def self.evolve(object)
    case object
    when Measurement then object.mongoize
    else object
    end
  end
end

```


## Scaffolding Demo APP

### Place
Place models a point and its descriptive address information.
```bash
$ rails g model Place formatted_address geolocation:Point street_number street_name city postal_code counte state country
```

###  Director
Director models the detailed information of a movie director.
```bash
$ rails g model Director name
```

### DirectorRef 
Annotated reference to a director that gets embedded into the Movie.
```bash
$ rails g model DirectorRef name
```

### Writer 
Holds the detailed information about the writer of a movie. Is directly associated with the movie without an annotated link.
```bash
$ rails g model Writer name
```

### Actor 
Contains the information details of an actor in a Movie.
```bash
# Using custom type Measurement
$ rails g model Actor name birth_name date_of_birth:Date height:Measurement bio:text
```
 
###  MovieRole 
Holds the role-specific information and relation between the Movie and Actor.
```bash
$ rails g model MovieRole character actor_name main:boolean url_character url_photo url_profile
```

### Movie 
Holds the core information about the movie, its properties, and supporting members.
```bash
$ rails g model Movie title type rated year:integer release_date:date runtime:Measurement votes:integer countries:array languages:array genres:array filming_locations:array metascore simple_plot:text plot:text url_imdb url_poster directors:array actors:array
```

### Controller/ViewController and View - Assembly
```bash
rails g scaffold_controller Movie title type rated year:integer release_date:date runtime:integer votes:integer countries:array languages:array genres:array filming_locations:array metascore simple_plot:text plot:text url_imdb url_poster directors:array actors:array
```

### Add routes
```ruby
Rails.application.routes.draw do
  resources :movies
end
```

Now at app_name/app/movies/index.html.erb
```erb
<p id="notice"><%= notice %></p>

<h1>Listing Movies</h1>

<table>
  <thead>
    <tr>
      <th>Poster</th>
      <th>Title</th>
      <th>Type</th>
      <th>Rated</th>
      <th>Year</th>
      <th>Release date</th>
      <th>Runtime</th>
      <th>Votes</th>
      <th>Countries</th>
      <th>Languages</th>
      <th>Genres</th>
      <th>Filming locations</th>
      <th>Metascore</th>
      <th>Simple plot</th>
      <th colspan="3"></th>
    </tr>
  </thead>

  <tbody>
    <% @movies.each do |movie| %>
      <tr>
        <td><img height="100px" width="80px" src= <%= movie.url_poster %>/></td>
        <td><%= link_to movie.title, movie.url_imdb %></td>
        <td><%= movie.type %></td>
        <td><%= movie.rated %></td>
        <td><%= movie.year %></td>
        <td><%= movie.release_date %></td>
        <td><%= movie.runtime %></td>
        <td><%= movie.votes %></td>
        <td><%= movie.countries %></td>
        <td><%= movie.languages %></td>
        <td><%= movie.genres %></td>
        <td><%= movie.filming_locations %></td>
        <td><%= movie.metascore %></td>
        <td><%= movie.simple_plot %></td>
        <td><%= link_to 'Show', movie %></td>
        <td><%= link_to 'Edit', edit_movie_path(movie) %></td>
        <td><%= link_to 'Destroy', movie, method: :delete, data: { confirm: 'Are you sure?' } %></td>
      </tr>
    <% end %>
  </tbody>
</table>

<br>

<%= link_to 'New Movie', new_movie_path %>

```

## Web Services
Software that makes services available on a network using technologies such as XML/JSON and HTTP.

### Web Services VS Web application

Web Services | Web Application
--- | ---
XML/JSON | HTML
Program to program interaction | User to program interaction
CRUD based API | User Interface
Possibility of service integration | Monolithic services

### REST (REpresentational State Transfer)
Resource Instance(s) are identified by URI (Uniform Resource Indicator):
* http://www.movieservice.com/movie/:id
* http://www.movieservice.com/movie/12345
Introduced by Roy Fielding in 2000

*[“Glory of REST”: Richardson Maturity Model](http://martinfowler.com/articles/richardsonMaturityModel.html).

REST: Web services:
* Stateless
* Expose directory structure-like URIs
* Supports multiple formats but JSON/XML – most popular formats.

### Representations
* Represents a resource (Movie).
* A resource can contain other resources (Movie --> Roles).
* Representation does not restrict representation format – XML/JSON.
* JSON is ideal for web pages (RoR/Ajax)

### HTTP Protocol
* GET - retrieve a resource
* POST - create a resource
* PATCH – update partial resource
* PUT - change the state of a source or to update it
* DELETE - remove a resource
* HEAD – similar to GET but no message body

### Stateless
* Stateful
```
/movies/getNextPage
```
server needs to store previous page
* Stateless
```
/movies?offset=25&limit=4
/movies?page=3Uniform
```

### Uniform Resource Indicator (URI)
http://www.movieservice.com/movies/12345
http://www.movieservice.com/movies/12345/roles
http://www.movieservice.com/movies/12345/roles/100

### Common MIME Types
MIME-Type  | Content-Type
--- | ---
JSON | application/json
XML | application/xml
HTML (XHTML) | application/xhtml

* Custom Type - application/vnd+company.category+xmlSummary.

## Resources in REST
### Resource Scope
Resource - fundamental concept in any RESTful API. Is an object with a type, associated data, relationships to other resources, and a set of methods that operate on it.

#### Example Resources
* Movies
* Actors
* MovieRolesResources

##### Standalone Resources
* Movies – can exist without Actors or MovieRoles.
* Actors – can exist without Movies or MovieRoles

##### Dependent Resources
* MovieRole - Depends on Movies to exist. Related to Actor, but can exist if relationship is severed.

#### Rails - Resources
```shell
# build templated code for CRUD operations
# Mongoid or ActiveModel – additional implementation
# rails g scaffold command
rails g model Movie title
rails g model Actor name
rails g model MovieRole character
```


```ruby
class Movie
  include Mongoid::Document
  include Mongoid::Timestamps

  field :title, type: String
  # ...
  embeds_many :roles, class_name:"MovieRole"
  # ...
end
```

```ruby
class MovieRole
  include Mongoid::Document
  field :character, type: String
  # ...
  embedded_in :movie
  belongs_to :actor, foreign_key: :_id, touch: true 
end
```

```ruby
class Actor
  include Mongoid::Document
  include Mongoid::Timestamps

  field :name, type: String
  # ...
  
  #sort-of has_many roles:, class_name: 'MovieRole`
  def roles
    Movie.where(:"roles._id"=>self.id).map {|m| m.roles.where(:_id=>self.id).first}
  end 
  # ...
end
```

Example:
```ruby
create actor 
actor=Actor.find("100")
rocky.actor=actor
rocky.save 
Movie.find("12345").roles.where(:id=>"0").first.actor.name
actors.roles.map {|r| "#{r.movie.title}, #{r.character}"}
```


## URIs
### URI vs. URL vs. URN
* Uniform Resource Identifier (URI): ww.coursera.org.
* Uniform Resource Locator(URL): http://www.coursera.org.
* A Uniform Resource Name (URN): urn:isbn:0-619-0125356-5 (used in xml schemas)

Expose the resources using standard **URIs automatically created**. Will register the resource in config/routes.rb:

```bash
$ rails g scaffold_controller Movie title
$ rails g scaffold_controller Actor name
```

```ruby
# config/routes.rb
Rails.application.routes.draw do 
  resource :movies
  resource :actors
```

```bash
$ rake routes
```


## Access URI (HTTParty)

```bash
gem ‘httparty’Access
```

```bash
HTTParty.get("http://localhost:3000/roles.json").reponse.code
# => "404"
HTTParty.get("http://localhost:3000/movies.json").reponse.code
# => "200"
```


### Access URI – Actors and Movies
```bash
pp HTTParty.get("http://localhost:3000/roles.json").reponse.code
# => JSON list movies [{}]
HTTParty.get("http://localhost:3000/actors.json").reponse.code
# => JSON list actors [{}]
```

### Access URI – Specific resource
`/movies/:id and /actors/:idController`
```bash
reponse=HTTParty.get("http://localhost:3000/movies/1234.json").reponse
# => #<Net::HTTPOK 200 OK readbody=true>
reponse=HTTParty.get("http://localhost:3000/movies/1234.json").parsed_reponse
# => JSON object pardes {"key=>value", ...}]
```


### Controller
```ruby
# Use callbacks to share common setup or constraints between actions.
def set_movie
  @movie = Movie.find(params[:id])
end
```

## Nested URIs
```bash
$ rails g scaffold_controller MovieRole
```

```bash
Rails.application.routes.draw do
  resources :movies do
    resources :movie_roles, as: :role, path: "roles"
  end
  resources :actors
```

Check `rake_routes` to see new nested URI.

### Controller
```ruby
# app/controllers/movie_roles_controller.rb
class MovieRolesController < ApplicationController
  before_action :set_movie
  before_action :set_movie_role, only: [:show, :edit, :update, :destroy]

  #...
  private
    #...
    def movie_role_params
      params.require(:movie_role).permit(:character, :actor_id)
    end
  #...
end
```

### JSON Marshaller 
Default JSON marshaller definition expects timestamp
```ruby
# app/views/movie_roles/show.json.jbuilder
json.extract! @movie_role, :id, :character, :actor_id, :created_at, :updated_at
# remove field for display
json.extract! @movie_role, :id, :character, :actor_id
```

### Access URI – MovieRole
MovieRole as a nested resource (Single) below Movie
```bash
# get single
$ HTTParty.get("http://localhost:3000/movies/1234/roles/0.json").parsed_reponse

# get collection
$ HTTParty.get("http://localhost:3000/movies/1234/roles.json").parsed_reponse
```

### Nested Resource - Collection
```ruby
movie.roles.create(:id=>"1", :character=>"challenger")
Movie.find("12345").roles
# =>[roles_list, ...]
```

Controller and index changes. Define before_action and update set_movie_role. 
```ruby
class MovieRolesController < ApplicationController
  before_action :set_movie
  before_action :set_movie_role, only: [:show, :edit, :update, :destroy]
  #...
  def index
    @movie_roles=@movie.roles
    fresh_when(@movie)
  end
  #...
end
```


### Update JSON Marshaller
Add the @movie as a parameter to the movie_role_url:
```ruby
json.array!(@movie_roles) do |movie_role|
  json.extract! movie_role, :id, :character, :actor_id
  json.url movie_role_url(movie_role, format: :json)
end
json.array!(@movie_roles) do |movie_role|
  json.extract! movie_role, :id, :character, :actor_id
  json.url movie_role_url(@movie, movie_role, format: :json)
end
```

## Query Parameters and Payload

### HTTParty Client class
```ruby
# Helper class - app/services/movies_ws.rb
class MoviesWS
  include HTTParty
  debug_output $stdout
  base_uri "http://localhost:3000"
end
```

Usage:
```shell
MoviesWS.get("/movies/12345.json").parsed_response
```


### Parameter Types
* URI elements (e.g., :movie_id, :id)
* Query String - part of the URI, uses "?", and contains individual query parameters.
* POST Data - in the payload body.

#### Parameter Types – Example
```bash
MoviesWS.get("/movies.json?title=rocky&foo=...").parsed_response
```

####  Post Data – Example
```bash
MoviesWS.post("/movies.json",
  :body=>{:movie=>{:id=>"123246", :title=>...}}.to_json,
  :headers=>{"Content-Type"=>"application/json"})
```

### White Listing Parameters
Rails has built in features based on parameters. Controller has a “white list” of acceptable parameters:
```ruby
# White list with 2 fields
class MovieRolesController < ApplicationController
  # POST /movies
  # POST /movies.json
  def create
    @movie = Movie.new(movie_params)
  end

  private
    def movie_params
      params.require(:movie).permit(:id,:title)
    end
# UsageWhite 
```


### Cross Site Scripting (XSS)
Browsers can run scripts (JavaScript). If a user trusts a website, might allow the scripts to run:

```erb
<script type="text/javascript"> 
  alert("Hard Disk Error. Click OK."); 
</script>
```

It is possible to inject malicious scripts into content from trusted sites. Scripts can hijack user sessions, redirect user to other sites
* POST request by default will fail. Can't verify CSRF (Cross Site Request Forgery) token authenticity - message.
* Relax Security
```ruby
# app/controllers/application_controller.rb
class ApplicationController < ActionController::Base
  # Prevent CSRF attacks by raising an exception.
  # For APIs, you may want to use :null_session instead.
  #protect_from_forgery with: :exception
  protect_from_forgery with: :null_session
end
```

### Query Parameters - Demo

Demo

### Other Parameter - options

#### Arrays
```bash
MoviesWS.get("/movies.json?id[]=12345&id[]=12346&foo[]=1&foo[]=2")
```
 
#### Hash
```bash
MoviesWS.get("/movies.json?movie[id]=12345&movie[title]=rocky27&movie[year]=2016")
```

## REST Methods

### HTTP Methods - POST
Creating new resource instances
* POST to a resource URI.
* Provide JSON payload (but optional).
* Provide MIME type of the payload in the Content-Type header.

```bash
MoviesWS.post("/movies.json", :body=>{:movie=>{:id=>"123246", :title=>"Rocky 27"}}.to_json)
```

#### POST (Update) - Action
Builds a white-list version of parameter hash. Builds a new instance of the Movie class with the hash passed. Saves the resultant Movie to the database. 
Renders a result back to the caller based on the format requested in the response and the status of the save.

### PUT
PUT is for **replacing the data** (Update)
The Client
* issues a PUT request
* issues the request to /movies/12347 URI
* provides a JSON payload for update
* provides application/json MIME type

#### HTTP Methods - PUT
```bash
reponse=MoviesWS.put("/movies/12347.json", :body=>{:movie=>{:title=>"Rocky 2700", "foo"=>"bar"}}.to_json)
```

#### PUT(Update) - Action
PUT expects the primary key to be in the :id parameter. If the movie is found, processing continues. Builds a white-list-checked set of parameters. Supplies the values to the update method. Returns the result document.

### HTTP Methods - PATCH
PATCH is for partially updating a resource. Update a field vs. entire resource.
```bash
reponse=MoviesWS.put("/movies/12347.json", :body=>{:movie=>{:title=>"Rocky 2702", "foo"=>"bar"}}.to_json)
```

### HTTP Methods - HEAD
HEAD is basically GET without the response body. Useful to retrieve meta-information written in response headers. 
Issue GET and store Etag for comparison later
```bash
reponse=MoviesWS.get("/movies/12347.json")
reponse.header["etag"]
#=> "W/\"4cff...
doc=reponse.parsed_reponse

# Now with HEAD
reponse=MoviesWS.head("/movies/12347.json")
reponse.header["etag"]
#=> "W/\"4cff...
doc=reponse.parsed_reponse
#=>nil
```


### HTTP Methods - DELETE
DELETE is for deleting a resource. It accepts an :id parameter from the URI and removes that document from the database.
No request bodyDELETE - Example
```bash
reponse=MoviesWS.delete("/movies/12347.json")
reponse.reponse
#=> no content
reponse.reponse.code
#=> 204
doc=reponse.parsed_reponse
#=>nil
```

### HTTP Methods - GET
GET is for data retrieval only. Free of side effects(idempotence)
```bash
MoviesWS.get("/movies.json?title=rocky25&foo=1").parsed_response
```

## Idempotence
Idempotence is the property of certain operations in mathematics and computer science, that can be applied multiple times without changing the result beyond the initial application.
### GET
Can be applied multiple times without changing the result. Idempotent
###DELETE
Can be applied multiple times without changing the result. Idempotent
###PUT
Can be applied multiple times without changing the result. Idempotent
###POST
Can not be applied multiple times without changing the result.  No Idempotent
(Multiple calls – problem)



## Representations
### Representations - Rendering
Default Rendering
* Controller derives data to render.
* View defaults to action requested.
* Content form based on client specification.
Controller Based Rendering
* Controller derives data to render.
* Controller makes decision on action to render.
* Controller makes decision on the format to render supplying default format or overriding client specification.

#### Example
```bash
# Create an Hello example with one hello action/view
rails g controller Hello sayhello
find app | grep hello
# => returns routes with hello in it
```

Changes:
* hello_controller.rb: add sayhello method
* routes.rb: add get hello/sayello route (check `rake routes`.


### Default Rendering
*(action).(format).(handler suffix)
  - sayhello.json.jbuilder (JSON)
  - sayhello.xml.builder (XML)
  - sayhello.text.raw (RAW)Default Rendering
* #app/views/hello
  - hello.json.builder json.msg @msg
  - hello.xml.builder xml.msg @msg
  - raw text message from: hello.text.raw

#### Default Rendering - Demo
```bash
# json
response=MovieWS.get("/hello/sayhello.json")
response.header.content_type # => "application/json"
reponse.body
# xml
response=MovieWS.get("/hello/sayhello.xml")
response.header.content_type # => "application/xml"
reponse.body
# text
response=MovieWS.get("/hello/sayhello.txt")
response.header.content_type # => "text/plain
reponse.body
```

### Controller Derived Data
Derive data from model. Store in controller instance variable:
```ruby
class HelloController < AplicationController
  def sayhello
    @msg="Hello World"
  end
end
```

```bash
# json
MovieWS.get("/hello/sayhello.json").reponse.body
#=> {\"msg\" : \"hello world\"}
# xml
MovieWS.get("/hello/sayhello.xml").reponse.body
#=> <msg>hello world</msg>\n"}
```

### Controller Action Rendering
Controller can have more active say in action rendered.
Define a route and action that takes a parameter.
```ruby
# config/routes.rb
  get 'hello/say/:something' 
```

Controller action method with decision logic
```ruby
def say
  case params[:something]
  when "hello" then @msg="saying hello"; render action: :sayhello
  when "goodbye" then @msg="saying goodbye"; render action: :saygoodbye
  when "badword" then render nothing: true
  else
    render plain "what do you want me to say?"
  end
end
```


Supported View Format
```ruby
# app/views/hello/saygoodbye.json.jbuilder
json.msg1 @msg
json.msg2 "so long!"


# app/views/hello/saygoodbye.xml.jbuilder
xml.msg do 
  xml.msg1 @msg
  xml.msg2 "so long!"
end
```

#### Demo
Request
something=hello:
* supplied by caller
Resulting View: sayhello:
* determined by the controller
Format json format:
* Specified by caller

Partials allow you to easily organize and reuse your view code in a Rails application
Filename starts with an underscore
Action-independent
Default path: _(partial).(format).(handler suffix)
* app/views/actors/_actor.json.jbuilder
* app/views/actors/_actor.xml.builder

## Versioning
Versioning – common practice in the industry
* name -> first_name and last_name
Backward compatible
```ruby
class Actor
  field :first_name, type: String
  field :last_name, type: String

  #backwards-compatible reader
  def name
    "#{first_name} #{last_name}"
  end
end
```

### Usage
Legacy Users can still use the service
```ruby
pp MoviesWS.get("/actors/100.json").parsed_response
#=>"name"=>"Silvester Stallone"
```

Problem – Not able to get the new additions via JSON (without breaking the legacy users)

Define a different media type format. ex: v2json)
```ruby
# config/initializers/mime_types.rb
# Be sure to restart your server when you modify this file.
# Add new mime types for use in respond_to blocks:
# Mime::Type.register "text/richtext", :rtf
Mime::Type.register "application/vnd.myorgmovies.v2+json", :v2json

```

#### Demo – V2
Access to v2 of the model
```ruby
reponse=MoviesWS.get("/actors/100.v2json")
reponse.content type
pp JSON.parse(reponse).body
#=>"first_name"=>"Silvester"
#=>"second_name"=>"Stallone"
```

#### Demo – V1
```ruby
reponse=MoviesWS.get("/actors/100.json")
reponse.content type
pp JSON.parse(reponse).body
#=>"name"=>"Silvester Stallone"
```

## Content Negotiation
Leave content type out of the URI
Use **HTTP Headers** ("Accept" and "Content-Type") to express formats and encodings
* Content-Type: what we are sending in
* Accept: what we are willing to accept
```bash
reponse = MoviesWS.post("/movies",
  :body=>{:movie=>{:id=>"123246", :title=>...}}.to_json,
  :headers=>{"Content-Type"=>"application/json", "Accept"=>"application/json"})

# try to see response
reponse.reponse
reponse.header["location"] # uri without format
reponse.header.content_type
pp JSON.parse(response.body)
```


### Headers
Accept: end-format we are willing to accept
Accept-Encoding: intermediate form encoded on wire
```ruby
#config/application.rb
config.middleware.use Rack::Deflater
```

```bash
reponse = MoviesWS.post("/directors",
  :headers=>{"Accept"=>"application/json", "Accept-Encoding"=>"gzip"})
# now in HTTP Request --> Accept-Encoding=>gzip\r\n
```


## Headers and Status
* Headers – used for concurrency checks or idempotence.
* Cache Management - process if things are out of date.
* Concurrency Management - process if things are up to date

### State.

#### Etag (Entity Tags)
Mechanism used to determine whether the entity or component (images, scripts, stylesheets, page content etc) in the browser’s cache matches the one on the origin server.

#### Last-Modified TimeStamp
* Indicates the most recent modification date/timeConditions

#### Conditions
* If-Match: Etag
* If-None-Match: Etag
* If-UnModified-Since: Last-Modified Timestamp
* If-Modified-Since: Last-Modified Timestamp

### Headers – fresh_when
```ruby
class MoviesController < ApplicationController
  # GET /movies/1.json
  def show
    Rails.logger.debug("movies#show")
    #add last_modified header and conditionally send 304 reponse
    fresh_when(@movie)
    Rails.logger.debug("response.etag=#{response.etag}")
  end

  # ....
  # POST /movies
  # POST /movies.json
  def create
    Rails.logger.debug("create.params=#{params}")
    Rails.logger.debug("create.movie_params=#{movie_params}")
    @movie = Movie.new(movie_params)

    respond_to do |format|
      if @movie.save
        fresh_when(@movie) 
        format.html { redirect_to @movie, notice: 'Movie was successfully created.' }
        format.json { render :show, status: :created, location: @movie }
        format.v2json { render :show, status: :created, location: @movie }
    # ....
  # PATCH/PUT /movies/1
  # PATCH/PUT /movies/1.json
  def update
    respond_to do |format|
      if @movie.update(movie_params)
        fresh_when(@movie) # fresh
        @movie.movie_accesses.create(:action=>"update")
        format.html { redirect_to @movie, notice: 'Movie was successfully updated.' }
        #....
    end
  end

end
```


Changes via PUT
```bash
reponse=MoviesWS.put("/movies/12347", 
  :body=>{:movie=>{:title=>"Rocky 2702", "foo"=>"bar"}}.to_json
  :headers=>{"Content"=>"application/json", "Accept"=>"application/json"})
```

```bash
reponse.header["last-modified"]
reponse.header["etag"]
# => Etag and Last-Modified have changed
```

### Concurrent Update Issue
Nested Resource Update
* Update Movie
* Add Role to Movie
* Add same Role to Movie (second time)
* Etag is updated but Last-Modified will not
```bash
# Get state of a movie
reponse=MoviesWS.head("/movies/12347",
  :headers=>{"Accept"=>"application/json"})

# Get data
reponse.header["last-modified"]
reponse.header["etag"]

# Add a role to the movie
reponse=MoviesWS.post("/movies/12347/roles", 
  :body=>{::movie_role=>{:character => "Challenger"}}.to_json
  :headers=>{"Content"=>"application/json", "Accept"=>"application/json"})

# Get data (etag changed but last not last modified)
reponse.header["last-modified"]
reponse.header["etag"]

# Add same role
reponse=MoviesWS.post("/movies/12347/roles", 
  :body=>{::movie_role=>{:character => "Challenger"}}.to_json
  :headers=>{"Content"=>"application/json", "Accept"=>"application/json"})

# Get data (no change from last)
reponse.header["last-modified"]
reponse.header["etag"]

# Duplicated character 
pp JSON.parse(response.body)
```

* Can be fixed by using touch
```ruby
#app/models/movie_role.rb
class MovieRole
  include Mongoid::Document
  field :character, type: String

  embedded_in :movie, touch: true # take care of the problem
  belongs_to :actor
end
```



#### IF_UNMODIFIED_SINCE
If HTTP_IF_UNMODIFIED_SINCE is supplied
* Use fresh_when to populate response with current Etag and Last-Modified.
* Continue if request date later than or equal to current state.
* Else report conflict and make no changes.
```bash
# create new movie
reponse=MoviesWS.post("/movies", 
  :body=>{::movie=>{:id => "12347", :name=>"Rocky 27"}}.to_json
  :headers=>{"Content"=>"application/json", "Accept"=>"application/vnd.myorgmovies.v2+json"})

# Test
response.response
# Store last modifies
last_modified=reponse.header["last-modified"]

# Add role
reponse=MoviesWS.post("/movies/12347/roles", 
  :body=>{::movie_role=>{:character => "Challenger"}}.to_json
  :headers=>{"Content"=>"application/json", 
              "Accept"=>"application/json",
              "If-Unmodified-Since" =>last_modified})

# Add same role
reponse=MoviesWS.post("/movies/12347/roles", 
  :body=>{::movie_role=>{:character => "Challenger"}}.to_json
  :headers=>{"Content"=>"application/json", 
              "Accept"=>"application/json",
              "If-Unmodified-Since" =>last_modified})
# => we get a 409 confict in the response

# Add a diferent role 
reponse=MoviesWS.post("/movies/12347/roles", 
  :body=>{::movie_role=>{:character => "Adrian"}}.to_json
  :headers=>{"Content"=>"application/json", 
              "Accept"=>"application/json",
              "If-Unmodified-Since" =>last_modified})
# => we get a 200 ok call

# We got both characters 
pp JSON.parse(response.body)
```

## Cache
Temporary storage of documents to reduce bandwidth usage and server load, and improve performance. Improve perceived performance by doing less and not repeating ourselves.

### Client Caching
Caching Headers:
* ETag
* Last-Modified
* cache-control
```bash
# Look for important headers
reponse=HTTParty.head("http://localhost:300/movies/12345")
pp ["cache-control", "etag", "last-modified"].map{|h| {h=>reponse.header[h]}}

# Other way
Movie.find("12345").cache_key
MD5.hexdigest(Movie.find("12345").cache_key)

# oher way
Movie.find("12345").updated_at.httpdate
```

Include in controller 
```ruby
def show
  headers["etag"]= MD5.hexdigest(@movie.cache_key)
  headers["Last-Modified"]= @movie.updated_at.httpdate
end
```

### fresh_when
Rails provides convenient methods that perform the roles discussed
* fresh_whenfresh_when
```ruby
def show
  #headers["etag"]= MD5.hexdigest(@movie.cache_key)
  #headers["Last-Modified"]= @movie.updated_at.httpdate
  fresh_when(@movie) # add etag and last modifies bases on de document update
end
```

```bash
# Look for important headers
reponse=HTTParty.head("http://localhost:300/movies/12345")
pp ["cache-control", "etag", "last-modified"].map{|h| {h=>reponse.header[h]}}

[{"cache-control" => "max-age=0, public"}, {"etag"=> "..."}, {"last-modified"=> "Fri, 15 Jan"}]
pp ["cache-control", "etag", "last-modified"].map{|h| {h=>reponse.header[h]}}

```

### Cache Revalidation Headers
Validate if what we have is current or stale using conditional cache validation headers:
* If-Not-Match : (Etag)
* If-Modified-Since : (Timestamp)

If the **resource has not changed**:
* enable server-side to do less processing because client does not need a new copy.
* report the resource has not changed to the client.
* enable client-side to do less processing because nothing has changed.

```bash
# Look for important headers
# Get a current copy of ETag and Last-Modified and store
reponse=HTTParty.get("http://localhost:3000/movies/12345", headers:{"Accept"="application/json"} )

response.response
etag=response.header["etag"]
last_modified=response.header["last-modified"]

# same reques/same result as nothing has changed
# GET: Same Action
# GET request, same payload
# 200 ID - returnedWith Headers
reponse=HTTParty.get("http://localhost:3000/movies/12345", headers:{"Accept"="application/json"} )

response.response
etag=response.header["etag"]
last_modified=response.header["last-modified"]

# SEnd with If-Not-Match
reponse=HTTParty.get("http://localhost:3000/movies/12345", headers:{"Accept"="application/json", "If-Not-Match"=>etag} )
#=> we get "304 Not Modified" in the response
response.response

# SEnd with If-Modified-Since
reponse=HTTParty.get("http://localhost:3000/movies/12345", headers:{"Accept"="application/json", "If-Modified-Since"=>last_modified} )
#=> we get "304 Not Modified" in the response
response.response
```

#### Conditional Logic
Note: stale? calls fresh_when under the covers
Fire only if the caller is not getting 304/NOT_MODIFIED
```ruby
# app/controllers
class MoviesController < ApplicationController
  #....
  # GET /movies/1
  # GET /movies/1.json
  def show
    # headers["ETag"]=Digest::MD5.hexdigest(@movie.cache_key) 
    # headers["Last-Modified"]=@movie.updated_at.httpdate
    # fresh_when(@movie)
    @movie.movie_accesses.create(:action=>"show")
    if stale? @movie # stale? calls fresh_when under the covers
      @movie.movie_accesses.create(:action=>"show-stale")
      #do some additional, expensive work here
      #...
    end
  end
end
```

```shell
# SEnd with If-Modified-Since
reponse=HTTParty.get("http://localhost:3000/movies/12345", headers:{"Accept"="application/json", "If-Modified-Since"=>last_modified} )
response.response
#=> we get "304 Not Modified" in the response
```

* last_modified value is being set to the most recent  Change in the collection:
```ruby
# app/controllers
class MoviesController < ApplicationController
  #....
  # GET /movies
  # GET /movies.json
  def index
    @movies = Movie.all
    fresh_when last_modified: @movies.max(:updated_at)
  end
  #....
end
```

Provide both If-Modified-Since and If-None-Match in the header. If either fires, our conditional logic will get triggered:
```shell
# SEnd with If-Modified-Since
reponse=HTTParty.get("http://localhost:3000/movies/12345", headers:{"Accept"="application/json", "If-Modified-Since"=>last_modified, "If-None-Match"=>"123"} )
response.response
#=> we get "200 OK" in the response

pp Movie.find("1234").movie_accesses.pluck(:created_at,:action).to_a
```

### Browser Test
* Chrome --> Developer Tools --> Network (Preserve Logs)
  * http://localhost:3000/movies/12345.json (Status --> 200/OK)
* Hit refresh (Status --> 304/Not Modified)
  * If-Modified-Since and If-None-Match headers were supplied.
* Click disable-cache at the top of the Network tab and hit refresh
  * The conditional headers are not sent to the Rails server and the full response is returned using a 200/OK


### Cache Control
Used to specify directives that must be obeyed by all caching mechanisms along the request-response chain.
Provide better hints to the client as to how long the information is good.

#### Demo
```shell
# Clean all
Movie.find("12345").movie_accesses.delete_all
# Request Movie 10 times (rapid fire)
# Each call results in a database access (no headers)
10.times.each{ HTTParty.head("http://localhost:3000/movies/12345")}

pp MovieAccess.where(:movie_id=>"12345", :action=>"show-stale").pluck(:created_at,:action)
```

#### Delegate Responsibility
Update the show method to include two caching headers:
* Expires and Cache-Control
* Overlap in meaning and if they ever conflict, Cache-Control is supposed to take precedence

#### How it works
Document will expire at a certain time. Document is not specific to an individual caller:
* You may cache this document for other callers as well
* If this information was specific to the caller (e.g., a personal bank statement), then Cache-Control would either be set to `nocache` or private to keep the resource from being served to other clients.

```ruby
# app/controllers
class MoviesController < ApplicationController
  #....
  # GET /movies/1
  # GET /movies/1.json
  def show
    # headers["ETag"]=Digest::MD5.hexdigest(@movie.cache_key) 
    # headers["Last-Modified"]=@movie.updated_at.httpdate
    # fresh_when(@movie)
    @movie.movie_accesses.create(:action=>"show")
    
    if stale? @movie # stale? calls fresh_when under the covers
      @movie.movie_accesses.create(:action=>"show-stale")
      #do some additional, expensive work here

      secs=10
      response.headers["Expires"] = secs.seconds.from_now.httpdate
      # response.headers["Cache-Control"] = "public, max-age=#{secs}"
      expires_in secs.seconds, :public=>true
      #...
    end
  end
end
```

```bash
# The maximum time to cache = 10 secondsexpires
reponse=HTTParty.get("http://localhost:3000/movies/12345")
pp ["cache-control", "etag", "last-modified"].map{|h| {h=>reponse.header[h]}}
# => [cache-control] => "max-age=10"
```

##### Changes
Add gems
```ruby
gem 'httparty'
gem 'dry_ice'
```

app/services/
```ruby
class CachedWS
  include HTTParty
  include HTTParty::DryIce
  
  debug_output $stdout
  base_uri "http://localhost:3000"
  
  cache Rails.cache # internal rails API cache
end
```

##### Demo
```bash
# Script – DB is polled every 9 to 12 seconds
# 3 second sleep and 10 second cache timeout
10.times.each do |x|
  p "look-#{x},  accesses-#{Movie.find(\"12345\").movie_accesses.where(:action => "show").count}"
  CacheWs.get("/movies/12345.json").parsed_response
  sleep(3.seconds)
end
```

### Server Caching
How to make it efficient. Various types of caching on the server side
Can be turned on or off – globally:
```ruby
# config/environnents/development.rb
config.action_controller.perform_caching = true
```

#### Server Caching - Types
Rails several levels of caching: 
* Page Caching
* Action Caching
* Fragment Caching
* Low Level Caching

#### Page Caching
* writes static files to directory.
* lazily updates files only when accessed.
* invalidates/removes files on events like updates.
* directory cleared of stale content using sweeper. 

Web Server role in caching:
* Serves a public single URI.
* Looks for content first in static content directory.
* Makes request to Rails server if static content is missing
 

##### Page Caching - Properties.
Fast - pre-rendered views being served. Use gem:
```ruby
gem 'actionpack-page_caching'
```

**Good for:**
* dynamic content that stays stable for periods of time.
* content served without regard to caller.
**Not appropriate for:**
* content that varies per user (e.g., login, preferences)
* content that is very dynamic

### Caching Setup
#### Turn on caching
```ruby
# config/environnents/development.rb
config.action_controller.perform_caching = true
config.action_controller.page_caching_directory = "#{Rails.root.to_s}/public/page_cache"
```

#### Add caches_page
```ruby
class MoviePagesController < ApplicationController
  before_action :set_movie, only: [:edit, :update, :destroy]
  caches_page :index, :show # define pages to be cached
  #...
end
```

#### Caching Setup - expiration
Page Expiration
```ruby
class MoviePagesController < ApplicationController
  #...

  # PATCH/PUT /movies/1
  # PATCH/PUT /movies/1.json
  def update
    respond_to do |format|
      if @movie.update(movie_params)
        expire_page action: "show", id:@movie, format: request.format.symbol
        expire_page action: "index", format: request.format.symbol
    # ...
       
  # DELETE /movies/1
  # DELETE /movies/1.json
  def destroy
    @movie.movie_accesses.create(:action=>"destroy")
    @movie.destroy
    expire_page action: "show", id:@movie, format: request.format.symbol
    expire_page action: "index", format: request.format.symbol
    # ...
# ...
```

#### caches folder
The rendered content is written to files in the public directory based on the URI.
* Result of calling index and show methods

```bash
# The maximum time to cache = 10 secondsexpires
reponse=HTTParty.get("http://localhost:3000/movie_pages/12345.json")
response.response # 200 ok
# Now if we acces cache folder we will find /movie_pages/12345 cached file

# Lets do a put
reponse=HTTParty.put("http://localhost:3000/movie_pages/12345.json", :body =>{:movie=> {:title=> "rocky26"}})
response.response # 200 ok
# Now if we acces cache folder we will find nothing since new created doc still have no cache file
```


## OAuth2
* OAuth stands for "Open Authorization".
* Open standard protocol that provides simple and secure authorization for different types of applications.
* Allows providers to give access to users without any exchange of credentials.
* Authorization framework that enables applications to obtain **limited access** to user accounts on an HTTP service like Facebook, GitHub, Twitter etc.....

### Password Approach: Problems
* Users have to share credentials
* Not secure and intrusive
* Hard to maintain when you authorize many apps
  * Change password at provider (Facebook) – clients need to be updated

### OAuth 2 Approach
* Secure as no passwords are exchanged.
* Uses tokens (next slide).
* Allows providers to give access to users without any exchange of credentials.


### OAuth 2 Movie Service to Movie Editor WebApp – Workflow
1. Register application at Movie Service (MS). Get credentials.
2. MS send temporary code to WebApp.
3. WebApp send credentials to MS (code, app, secret_id).
4. MS send acces_token which replace name and password for login.


## Assembly
Core Setup
* Create new Rails application
```bash
$ rails new oauth_movies
$ cd oauth_movies
```
* Add gems: mongoid and httparty
* Integrate Mongoid
```bash
$ rails g mongoid:config
```
* Define root URL
```bash
$ rails g controller pages index
```

```ruby
# config/routes.rb
Rails.application.routes.draw do
  #get 'pages/index'
  root to: 'pages#index'
```

### Resource Access
HTML Controller --> Movies
```bash
* $ rails g model Movie id title
```
Add Timestamp

### Resource Controller
Movies Controller
```bash
$ rails g scaffold_controller Movies id title
```

```ruby
# config/routes.rb
Rails.application.routes.draw do
  #...
  resources :movies
  #...
```

### API Controller
* Update the Gemfile with responders gem
  -  Automatic marshalling
  -  gem 'responders', '~> 2.1', '>= 2.1.1 '
Add Controller (app/controller/api) and update routes.rb

#### Example API queries
```bash
# Lets do a put
reponse=HTTParty.post("http://localhost:3000/api/movies/12345.json", :body =>{:movie=> {:id=>"12345", :title=> "rocky26"}})

reponse.reponse #201 Created OK

pp  JSON.parse(reponse.body)

# Make update
reponse=HTTParty.put("http://localhost:3000/api/movies/12345", :body =>{:movie=> {:title=> "rocky25.5"}})
# Make get to the API to look everything is OK
reponse=HTTParty.get("http://localhost:3000/api/movies/12345")

# Make get to no exist page
reponse=HTTParty.get("http://localhost:3000/api/movies/123")
reponse.reponse # => 404
```


## Devise Integration
Devise is a popular authentication solution for Rails applications. A full-featured authentication solution which handles all of the controller logic and form views for you 

### Configuration 
```ruby
gem 'devise', '~> 3.5', '>= 3.5.3'
```
Generate the Device configuration file using rails g
```bash
rails g devise:install
```

#### Configuration - URL
Define a URL for generated e-mail messages to reference back to the server. Set to localhost:3000 (in development demo)
```ruby
Rails.application.configure do
  # ...
  
  #devise options
  config.action_mailer.default_url_options = { host: 'localhost', port: 3000 }
end
```

### User Model Class
* hold accounts in your service
```bash
rails g devise user
```

Set devise and modules for device:
```ruby
class User
  include Mongoid::Document
  # Include default devise modules. Others available are:
  # :confirmable, :lockable, :timeoutable and :omniauthable
  devise :database_authenticatable, :registerable,
         :recoverable, :rememberable, :trackable, :validatable

  ## Database authenticatable
  field :email,              type: String, default: ""
  field :encrypted_password, type: String, default: ""

  # ...
```

### Devise Management
Devise manages three (3) primary resources for our user:
* login sessions - login/logout
* passwords, and
* registration data - email, optional fields

```ruby
class User
  include Mongoid::Document
  # Include default devise modules. Others available are:
  # :confirmable, :lockable, :timeoutable and :omniauthable
  devise :database_authenticatable, :registerable,
         :recoverable, :rememberable, :trackable, :validatable

  ## Database authenticatable
  field :email,              type: String, default: ""
  field :encrypted_password, type: String, default: ""

  ## Recoverable
  field :reset_password_token,   type: String
  field :reset_password_sent_at, type: Time

  ## Rememberable
  field :remember_created_at, type: Time

  ## Trackable
  field :sign_in_count,      type: Integer, default: 0
  field :current_sign_in_at, type: Time
  field :last_sign_in_at,    type: Time
  field :current_sign_in_ip, type: String
  field :last_sign_in_ip,    type: String

  ## Confirmable
  # field :confirmation_token,   type: String
  # field :confirmed_at,         type: Time
  # field :confirmation_sent_at, type: Time
  # field :unconfirmed_email,    type: String # Only if using reconfirmable

  ## Lockable
  # field :failed_attempts, type: Integer, default: 0 # Only if lock strategy is :failed_attempts
  # field :unlock_token,    type: String # Only if unlock strategy is :email or :both
  # field :locked_at,       type: Time
end
```

At `app/views/pages/index.html.erb`:
```erb
<% if user_signed_in? %>
    <h2>Logged In As: <%= current_user.email %></h2>
    <ul>
      <li><%= link_to 'Log out', destroy_user_session_path, method: :delete %></li>
      <li><%= link_to 'Profile', edit_user_registration_path %></li>
<% else %>
    <h2>Welcome!</h2>
    <ul>
      <li><%= link_to 'Log In', new_user_session_path %></li>
      <li><%= link_to 'Sign Up', new_user_registration_path %></li>
<% end %>
</ul>
```

Check in irb:
```bash
User.mongo_client.database.collection_names
#=> ["movies", "users"]
User.collection.name
#=> "users"
pp User.first.attributes
#=>user attrs
```

### Integrate Sign-In and Authentication
* API Base Controller (Write actions)
It still can edit. Let's fix.
```ruby
module Api
  class BaseController < ApplicationController
    before_action :authenticate_user!, except: [:index, :show ]
    before_action :user_signed_in?, except: [:index, :show ]
    #...
  end
end
```


#### Verify Access
DEMO (/api methods)
* Verify access is still available to non-writable methods
* Verify access is denied for writable methods
```bash
# Make get to the API to look everything is OK
reponse=HTTParty.get("http://localhost:3000/api/movies")
#=> 200 OK!
reponse.reponse #200 OK
pp  JSON.parse(reponse.body)
#=> object OK

reponse=HTTParty.get("http://localhost:3000/api/movies/12345")
#=> object OK
pp  JSON.parse(reponse.body)
#=> object OK


# Lets do a post
reponse=HTTParty.post("http://localhost:3000/api/movies/12345.json", :body =>{:movie=> {:id=>"12346", :title=> "rocky26"}})
#=> error! you need to sign.. fine!!
#=> 401 error (no redirect) ... make it better with door keeper
```

## Integrated Authentication (with Doorkeeper)
Doorkeeper is an OAuth 2 provider for Rails. It's built on top of Rails engines. So far it supports all protocol flows
Doorkeeper: Gems
```ruby
gem 'doorkeeper', '~> 3.1'
gem "doorkeeper-mongodb", github: "doorkeeper-gem/doorkeeper-mongodb"
gem 'oauth2', '~> 1.0'
```

#### Doorkeeper: Configuration
Install Doorkeeper
```bash
* rails g doorkeeper:install
```

Produces the following URI
* config/routes.rb
  -  use_doorkeeper

##### Doorkeeper: Database Configuration
Configure the ORM and prepare the database
```ruby
Doorkeeper.configure do
  # Change the ORM that doorkeeper will use (needs plugins)
  #orm :active_record
  orm :mongoid5
  # ...
end
```

Install indexes (Mongoid)
```bash
$ rake db:mongoid:create_indexes
```

Update `resource_owner_authenticator` to resolve User object based on what is stored in the session:
```ruby
Doorkeeper.configure do
  # Change the ORM that doorkeeper will use (needs plugins)
  #orm :active_record
  orm :mongoid5

  # This block will be called to check whether the resource owner is authenticated or not.
  resource_owner_authenticator do
    #fail "Please configure doorkeeper resource_owner_authenticator block located in #{__FILE__}"
    # Put your resource owner authentication logic here.
    # Example implementation:
    #   User.find_by_id(session[:user_id]) || redirect_to(new_user_session_url)
    user_key=session["warden.user.user.key"]
    user_id=user_key[0][0]  if user_key
    User.where(:id=>user_id).first || redirect_to(new_user_session_url)
  end
end
```


## OAuth2 integration 
Integrating Authentication
Setup Registration between acme.com (Movie Editor App) and Movie Service and OAuth provider (embedded in Movie Service)
MovieEditor (Acme.com) --> signup for a new account
```bash
# http://localhost:3000/oauth/applications/newRegistration
Name: Acme Client
Redirect URL: http://acme.com/auth/movies/callback
Scopes: (blank)Registration 
```

```bash
# Results
Application Id: XXXXXX
Secret: XXXXXXX
Callback URLs: http://acme.com/auth/movies/callback
# Comiamos los datos de resspuesta
irb> app_id=xxx
irb> secret=xxx
irb> callback=xxx
irb> client=OAuth::Client.new(app_id, secret, site:"http://xxxx.com")
irb> client.auth_code.authorize_url(redirect_uri: callback)

# => paste result url into the browser -> get logging!
# => paste same url into the browser -> Authorization required --> authorize returs a code!
irb> code="xxxxxxxxxxxxxxxxxx" 
irb> access = client.auth_code.get_token(code, redirect_uri: callback)
irb> access.token 
#=> token!! 
irb> 
```

Replace previous authentication with doorkeeper:
```ruby
# app/controllers/api/base_controller.rb
module Api
  class BaseController < ApplicationController
    # before_action :authenticate_user!, except: [:index, :show ]
    # before_action :user_signed_in?, except: [:index, :show ]
    before_action :doorkeeper_authorize! , except: [:index, :show ]
    protect_from_forgery with: :null_session
    respond_to :json
  end
end
```


## Credit
Work sheet for Coursera's [Ruby on Rails Webservices and MongoDB - Johns Hopkins University](https://www.coursera.org/learn/ruby-on-rails-intro/) course. A big hug for such an amazing course!