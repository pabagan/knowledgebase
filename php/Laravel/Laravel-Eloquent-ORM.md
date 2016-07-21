# Laravel ORM

* [Laravel](https://laravel.com/docs/5.0/eloquent).

## Table of contents
- [Defining models](#defining-models)
- [Retrieving results](#retrieving-results)
- [Defining attributes](#defining-attributes)
- [Insert, Update, Delete](#insert-update-delete)
- [Timestamps](#timestamps)
- [Query Scopes](#query-scopes)
- [Relationships](#relationships)
- [Querying Relations](#querying-relations)
- [Eager Loading](#eager-loading)
- [Inserting Related Models](#inserting-related-models)
- [Touching Parent Timestamps](#touching-parent-timestamps)
- [Working With Pivot Tables](#working-with-pivot-tables)
- [Collections](#collections)
- [Accessors & Mutators](#accessors--mutators)
- [Attribute casting](#attribute-casting)
- [Model Events](#model-events)
- [Model Observers](#model-observers)
- [Model URL Generation](#model-url-generation)
- [Converting To Arrays / JSON](#converting-to-arrays--json)

## Defining models
All Eloquent models extend `Illuminate\Database\Eloquent\Model`

Defining An Eloquent Model:

```php
class User extends Model {}
```

Generate with

```bash
php artisan make:model User
```

Tell Eloquent which **table to use** for our User model. The "snake case", plural name of the class will be used as the table name unless another name is explicitly specified.

```php
class User extends Model {
    // if this is not specified the default
    // table would be users
    protected $table = 'my_users';
}
```

By default `updated_at` and `created_at` are created by default. Could change this with set the $timestamps.

## Retrieving results

```php
// find all
$users = User::all();
// find one
$user = User::find(1);
var_dump($user->name);
// throw exception
$model = User::findOrFail(1);
// agregation
$model = User::where('votes', '>', 100)->firstOrFail();
$users = User::whereRaw('age > ? and votes = 100', [25])->get();
// chunking resultto not eat all the RAM
User::chunk(200, function($users){
    foreach ($users as $user){
    }
});
// specifying connection
$user = User::on('connection-name')->find(1);
//using read / write connections
$user = User::onWriteConnection()->find(1);
```

## Defining attributes

```php
class User extends Model {
    // The fillable property specifies which attributes should be mass-assignable. This can be set at the class or instance level.
    protected $fillable = ['first_name', 'last_name', 'email'];
    // The inverse of fillable is guarded, and serves as a "black-list" instead of a "white-list":
    protected $guarded = ['id', 'password'];
    // Blocking All Attributes From Mass Assignment
    protected $guarded = ['*'];
}
```

## Insert, Update, Delete

create a new model instance and call the save method.

```php
$user = new User;
$user->name = 'John';
$user->save();

// to read the auto-increment created key
// access the property like this
$insertedId = $user->id;
```

### The Model Create Method

```php
// Create a new user in the database...
$user = User::create(['name' => 'John']);
// Retrieve the user by the attributes, or create it if it doesn't exist...
$user = User::firstOrCreate(['name' => 'John']);
// Retrieve the user by the attributes, or instantiate a new instance...
$user = User::firstOrNew(['name' => 'John']);
```

### Updating A Retrieved Model

```php
$user = User::find(1);
$user->email = 'john@foo.com';
$user->save();

// Saving A Model And Relationships
$user->push();

// Updating Only The Model's Timestamps
$user->touch();

// Update same time as querying
$affectedRows = User::where('votes', '>', 100)->update(['status' => 2]);
```

### Deleting An Existing Model
```php
$user = User::find(1);
$user->delete();

// Deleting An Existing Model By Key
User::destroy(1);
User::destroy([1, 2, 3]);
User::destroy(1, 2, 3);

// delete on a set of models
$affectedRows = User::where('votes', '>', 100)->delete();
```

### Soft Deleting

It is not actually removed from your database. Instead, a `deleted_at` timestamp is set on the record.

```php
// loading here
use Illuminate\Database\Eloquent\SoftDeletes;

class User extends Model {
    // using here
    use SoftDeletes;
    protected $dates = ['deleted_at'];
}
```

May use the softDeletes method from a migration:
```php
$table->softDeletes();
```

(*) The "deleted" models will not be included in query results. If you wnant them to appair use `withTrashed`:

```php
$users = User::withTrashed()->where('account_id', 1)->get();
$user->posts()->withTrashed()->get();
```

To restore a SoftDelete use

```php
$user->restore();
// restore method on a query:
User::withTrashed()->where('account_id', 1)->restore();
// restore method on a relationship
$user->posts()->restore();
```

If you wish to truly remove a model from the database, you may use the forceDelete method:

```php
$user->forceDelete();

// The forceDelete method also works on relationships:
$user->posts()->forceDelete();

// check for soft deleting
if ($user->trashed()){
    //
}
```

## Timestamps
Eloquent add automaitcally the `created_at` and `updated_at`

Disabling Auto Timestamps

```php
class User extends Model {

    protected $table = 'users';

    public $timestamps = false;

}
```

Providing A Custom Timestamp Format

```php
class User extends Model {
    protected function getDateFormat(){
        return 'U';
    }
}
```

## Query Scopes

Defining A Query Scope by  simply prefix a model method with `scope*`:

```php
class User extends Model {

    public function scopePopular($query) {
        return $query->where('votes', '>', 100);
    }

    public function scopeWomen($query) {
        return $query->whereGender('W');
    }

    // Dynamic scope
    public function scopeOfType($query, $type) {
        return $query->whereType($type);
    }
}
```

Using a scope:
```php
$users = User::popular()->women()->orderBy('created_at')->get();
```

### Global Scope
Global scopes are defined using a combination of PHP traits and an implementation of `Illuminate\Database\Eloquent\ScopeInterface`.

```php
trait SoftDeletes {

    /**
    * Boot the soft deleting trait for a model.
    *
    * @return void
    */
    public static function bootSoftDeletes()
    {
        static::addGlobalScope(new SoftDeletingScope);
    }

}
```

A scope must implement `ScopeInterface`, which specifies two methods: `apply` and `remove`.

```php
/**
* Apply the scope to a given Eloquent query builder.
*
* @param  \Illuminate\Database\Eloquent\Builder  $builder
* @param  \Illuminate\Database\Eloquent\Model  $model
* @return void
*/
public function apply(Builder $builder, Model $model)
{
    $builder->whereNull($model->getQualifiedDeletedAtColumn());

    $this->extend($builder);
}

/**
* Remove the scope from the given Eloquent query builder.
*
* @param  \Illuminate\Database\Eloquent\Builder  $builder
* @param  \Illuminate\Database\Eloquent\Model  $model
* @return void
*/
public function remove(Builder $builder, Model $model)
{
    $column = $model->getQualifiedDeletedAtColumn();

    $query = $builder->getQuery();

    foreach ((array) $query->wheres as $key => $where)
    {
        // If the where clause is a soft delete date constraint, we will remove it from
        // the query and reset the keys on the wheres. This allows this developer to
        // include deleted model in a relationship result set that is lazy loaded.
        if ($this->isSoftDeleteConstraint($where, $column))
        {
            unset($query->wheres[$key]);

            $query->wheres = array_values($query->wheres);
        }
    }
}
```

## Relationships

### One To One
For example, a User model might have one Phone. We can define this relation in Eloquent:

```php
class User extends Model {

    public function phone() {
        // the argument passed is corresponding with
        // the model name related to the relation.
        return $this->hasOne('App\Phone');
    }
}
```

```php
$phone = User::find(1)->phone;
```
The SQL performed by this statement will be as follows:

```sql
select * from users where id = 1
select * from phones where user_id = 1
```

Phone model is assumed to use a user_id foreign key. If you wish to override this convention, you may pass a second argument to the hasOne method. Furthermore, you may pass a third argument to the method to specify which local column that should be used for the association:

```php
return $this->hasOne('App\Phone', 'foreign_key');
return $this->hasOne('App\Phone', 'foreign_key', 'local_key');
```

#### Defining inversing relation
Eloquent will look for a `user_id` column on the phones table. If like to overwrite you may pass it as the second argument to the belongsTo method:

```php
class Phone extends Model {

    public function user() {
        return $this->belongsTo('App\User', 'local_key');
    }
}
```

A third parameter which specifies the name of the associated column on the parent table:

```php
class Phone extends Model {
    public function user(){
        return $this->belongsTo('App\User', 'local_key', 'parent_key');
    }
}
```

### One To Many
Eg: blog post that "has many" comments.

```php
class Post extends Model {
    public function comments() {
        return $this->hasMany('App\Comment');
    }
}
```

the post's comments through the dynamic property:

```php
$comments = Post::find(1)->comments;
```

Could continue chaining conditions:

```php
$comments = Post::find(1)->comments()->where('title', '=', 'foo')->first();
```

May override the conventional foreign key by passing a second argument.

```php
return $this->hasMany('App\Comment', 'foreign_key');
return $this->hasMany('App\Comment', 'foreign_key', 'local_key');
```

#### Defining inversing relation

```php
class Comment extends Model {

    public function post(){
        return $this->belongsTo('App\Post');
    }
}
```

### Many To Many
User with many roles, where the roles are also shared by other users.

For example, many users may have the role of "Admin". Three database tables are needed for this relationship: users, roles, and role_user.

We can define a many-to-many relation using the belongsToMany method:

```php
class User extends Model {

    public function roles(){
        return $this->belongsToMany('App\Role');
    }
}
```

Now, we can retrieve the roles through the User model:

```php
$roles = User::find(1)->roles;
```

For unconventional table name for your pivot table:

```php
return $this->belongsToMany('App\Role', 'user_roles');
```

You may also override the conventional associated keys:
```php
return $this->belongsToMany('App\Role', 'user_roles', 'user_id', 'foo_id');
```

#### Defining inversing relation

```php
class Role extends Model {

    public function users() {
        return $this->belongsToMany('App\User');
    }
}
```

### Has Many Through
Distant relations via an intermediate relation. Eg: a Country model might have many Post through a User model.

Even though the posts table does not contain a `country_id` column, the `hasManyThrough` relation will allow us to access a country's posts via `$country->posts`.

```php
class Country extends Model {
    public function posts(){
        return $this->hasManyThrough('App\Post', 'App\User');
    }
}
```

If you would like to manually specify the keys of the relationship, you may pass them as the third and fourth arguments to the method:

```php
class Country extends Model {
    public function posts() {
        return $this->hasManyThrough('App\Post', 'App\User', 'country_id', 'user_id');
    }
}
```

### Polymorphic Relations
A model to belong to more than one other model, on a single association.

```php
class Photo extends Model {

    public function imageable()
    {
        return $this->morphTo();
    }

}

class Staff extends Model {

    public function photos()
    {
        return $this->morphMany('App\Photo', 'imageable');
    }

}

class Order extends Model {

    public function photos()
    {
        return $this->morphMany('App\Photo', 'imageable');
    }

}
```

#### Retrieving a Poly

```php
$staff = Staff::find(1);

foreach ($staff->photos as $photo)
{
    //
}

// Retrieving The Owner Of A Polymorphic Relation
// However, the true "polymorphic" magic is when you access the staff or order from the Photo model:
$photo = Photo::find(1);
$imageable = $photo->imageable;
//The imageable relation on the Photo model will return either a Staff or Order instance, depending on which type of model owns the photo.
```

#### Polymorphic Table structure
```php
staff
    id - integer
    name - string

orders
    id - integer
    price - integer

photos
    id - integer
    path - string
    imageable_id - integer
    imageable_type - string
```

### Many To Many Polymorphic Relations
Eg: A blog Post and Video model could share a polymorphic relation to a Tag model.


#### Many to many polymorphic Table structure
```php
posts
    id - integer
    name - string

videos
    id - integer
    name - string

tags
    id - integer
    name - string

taggables
    tag_id - integer
    taggable_id - integer
    taggable_type - string
```

Now `Post` and `Video` would gave a `morphToMany` relationship via a `tags` method:

```php
class Post extends Model {

    public function tags()
    {
        return $this->morphToMany('App\Tag', 'taggable');
    }
}
```

The Tag model may define a method for each of its relationships:

```php
class Tag extends Model {
    public function posts(){
        return $this->morphedByMany('App\Post', 'taggable');
    }

    public function videos(){
        return $this->morphedByMany('App\Video', 'taggable');
    }
}
```

## Querying Relations

### Querying Relations When Selecting

```php
// post with at least 2 comments
$posts = Post::has('comments')->get();
// with operators
$posts = Post::has('comments', '>=', 3)->get();
// Nested statements using "dot" notation:
$posts = Post::has('comments.votes')->get();
// where consts with whereHas and orWhereHas
$posts = Post::whereHas('comments', function($q){
    $q->where('content', 'like', 'foo%');
})->get();
```

### Dynamic Properties

```php
class Phone extends Model {

    public function user()
    {
        return $this->belongsTo('App\User');
    }

}

$phone = Phone::find(1);
// Instead of echoing the user's email like this:
echo $phone->user()->first()->email;
// It may be shortened to simply:
echo $phone->user->email;
```

## Eager Loading
Consider a Book related to Author. The relationship is defined like so:

```php
class Book extends Model {
    public function author(){
        return $this->belongsTo('App\Author');
    }
}
```

Now, consider the following code:

```php
foreach (Book::all() as $book) {
    echo $book->author->name;
}

// This loop will execute 1 query to retrieve all of the books on the table, then another query for each book to retrieve the author. So, if we have 25 books, this loop would run 26 queries.
```

Eager login will reduce the amount of queries with the `with` method:

```php
foreach (Book::with('author')->get() as $book) {
    echo $book->author->name;
}
```

Which is doing under the hood just be doing:

```sql
select * from books
select * from authors where id in (1, 2, 3, 4, 5, ...)
```

```php
// load multiple relationships at one time:
$books = Book::with('author', 'publisher')->get();
// You may even eager load nested relationships:
$books = Book::with('author.contacts')->get();
```

### Eager Load Constraints

```php
// eg1
$users = User::with(['posts' => function($query) {
    $query->where('title', 'like', '%first%');
}])->get();

// eg2
$users = User::with(['posts' => function($query){
    $query->orderBy('created_at', 'desc');
}])->get();
```

### Lazy Eager Loading

Load related models directly from an already existing model collection.

```php
$books = Book::all();
$books->load('author', 'publisher');

// could also pass a Closure to set constraints on the query:
$books->load(['author' => function($query) {
    $query->orderBy('published_date', 'asc');
}]);
```

## Inserting Related Models

To insert new related models. Instead of manually setting the post_id foreign key on the model, you may insert the new comment from its parent:

```php
// In this example, the post_id field will automatically be set on the inserted comment.
$comment = new Comment(['message' => 'A new comment.']);
$post = Post::find(1);
$comment = $post->comments()->save($comment);
```

If you need to save multiple related models:

```php
$comments = [
    new Comment(['message' => 'A new comment.']),
    new Comment(['message' => 'Another comment.']),
    new Comment(['message' => 'The latest comment.'])
];

$post = Post::find(1);
$post->comments()->saveMany($comments);
```
### Associating Models (Belongs To)

Updating a belongsTo need the use the `associate` method. This method will set the foreign key on the child model:

```php
$account = Account::find(10);

$user->account()->associate($account);
$user->save();
```

### Inserting Related Models (Many To Many)

#### Attaching Many To Many Models

```php
$user = User::find(1);
$user->roles()->attach(1);
```

Couyld also pass an an array of attributes that should be stored on the pivot table for the relation:

```php
// add
$user->roles()->attach(1, ['expires' => $expires]);
// remove
$user->roles()->detach(1);

// Both are accepting an arrays of IDs as input:
$user = User::find(1);
$user->roles()->detach([1, 2, 3]);
$user->roles()->attach([1 => ['attribute1' => 'value1'], 2, 3]);
```

#### Using Sync To Attach Many To Many Models
Attach related models. Accepts an array of IDs to place on the pivot table. After this operation is complete, only the IDs in the array will be on the intermediate table for the model:

```php
$user->roles()->sync([1, 2, 3]);

// You may also associate other pivot table values with the given IDs:
$user->roles()->sync([1 => ['expires' => true]]);

// Sometimes you may wish to create a new related model and attach. Use:
$role = new Role(['name' => 'Editor']);
User::find(1)->roles()->save($role);

// pass an array of attributes to place on the joining table for this operation:
User::find(1)->roles()->save($role, ['expires' => $expires]);
```

## Touching Parent Timestamps

Eg: when a Comment model is updated, you may want to automatically touch the `updated_at` timestamp of the owning Post. Use  `touches` property containing the names of the relationships to the child model:

```php
class Comment extends Model {

    protected $touches = ['post'];

    public function post(){
        return $this->belongsTo('App\Post');
    }
}
```

Now, when you update a Comment, the owning Post will have its updated_at column updated:

```php
$comment = Comment::find(1);
$comment->text = 'Edit to this comment!';
$comment->save();
```

## Working With Pivot Tables
Eg: Our User object has many Role objects that it is related to.:

```php
$user = User::find(1);

foreach ($user->roles as $role) {
    echo $role->pivot->created_at;
}
```

If your pivot table contains extra attributes apart from external keys, you must specify them when defining the relationship:

```php
return $this->belongsToMany('App\Role')->withPivot('foo', 'bar');
// Now the foo and bar attributes will be accessible on our pivot object for the Role model.
```

Automatically maintained created_at and updated_at timestampss use the `withTimestamps`:

```php
return $this->belongsToMany('App\Role')->withTimestamps();
```

### Deleting Records On A Pivot Table
```php
User::find(1)->roles()->detach();
//Note that this operation does not delete records from the roles table, but only from the pivot table.
```

### Updating A Record On A Pivot Table
```php
User::find(1)->roles()->updateExistingPivot($roleId, $attributes);
```

### Defining A Custom Pivot Model
First create your own "Base" model class that extends Eloquent.

```php
public function newPivot(Model $parent, array $attributes, $table, $exists) {
    return new YourCustomPivot($parent, $attributes, $table, $exists);
}
```

## Collections

### Checking If A Collection Contains A Key


```php
// determine if a result set contains a given primary key
$roles = User::find(1)->roles;

if ($roles->contains(2)){
    //
}
```

### Convert collections
```php
$roles = User::find(1)->roles->toArray();
$roles = User::find(1)->roles->toJson();
// is cast to a string, it will be returned as JSON:
$roles = (string) User::find(1)->roles;
```

### Iterating Collections

```php
$roles = $user->roles->each(function($role){
    //
});
```

### Filtering Collections
```php
$users = $users->filter(function($user) {
    return $user->isAdmin();
});
// (*)When filtering a collection and converting it to JSON, try calling the values function first to reset the array's keys.
```

### Applying A Callback To Each Collection Object
```php
$roles = User::find(1)->roles;

$roles->each(function($role){
    //
});
```

### Sorting A Collection By A Value
```php
$roles = $roles->sortBy(function($role){
    return $role->created_at;
});

$roles = $roles->sortByDesc(function($role){
    return $role->created_at;
});
```

### Sorting A Collection By A Value
```php
$roles = $roles->sortBy('created_at');
$roles = $roles->sortByDesc('created_at');
```

### Returning A Custom Collection Type
```php
class User extends Model {

    public function newCollection(array $models = [])
    {
        return new CustomCollection($models);
    }

}
```
## Accessors & Mutators

### Defining An Accessor

Eloquent way to  `getting or setting`. Simply define camel-casing, even though your database columns are snake-case:

```php
class User extends Model {

    public function getFirstNameAttribute($value){
        return ucfirst($value);
    }
}
```
In the example above, the first_name column has an accessor. Note that the value of the attribute is passed to the accessor.

### Defining An Mutator

Mutators are declared in a similar fashion:

```php
class User extends Model {
    public function setFirstNameAttribute($value){
        $this->attributes['first_name'] = strtolower($value);
    }
}
```

### Date Mutators
Eloquent will convert the `created_at` and `updated_at` to instances of Carbon.

May customize fields automatically mutated, and even disable, by overriding the getDates method of the model:

```php
public function getDates() {
    return ['created_at'];
}
```

## Attribute casting
```php
/**
 * The attributes that should be casted to native types.
 *
 * @var array
 */
protected $casts = [
    'is_admin' => 'boolean',
];
```
Supported types: `integer`, `real`, `float`, `double`, `string`, `boolean`, `object` and `array`.

## Model Events
Eloquent models fire several events, allowing you to hook into: `creating`, `created`, `updating`, `updated`, `saving`, `saved`, `deleting`, `deleted`, `restoring`, `restored`.

### Cancelling Save Operations Via Events
```php
User::creating(function($user){
    if ( ! $user->isValid()) return false;
});
```

### Where To Register Event Listeners

Your EventServiceProvider serves as a convenient place to register your model event bindings. For example:

```php
/**
 * Register any other events for your application.
 *
 * @param  \Illuminate\Contracts\Events\Dispatcher  $events
 * @return void
 */
public function boot(DispatcherContract $events){
    parent::boot($events);

    User::creating(function($user){
        //
    });
}
```

## Model Observers

Declare observer:

```php
class UserObserver {
    public function saving($model){
        //
    }

    public function saved($model){
        //
    }
}
```

Register an observer instance using the observe method:

```php
User::observe(new UserObserver);
```

## Model URL Generation

When you pass a model to the route or action methods, it's primary key is inserted. In this example the $user->id property will be inserted into the {user} place-holder of the generated URL.
```php
Route::get('user/{user}', 'UserController@show');

action('UserController@show', [$user]);
```

To use another property instead of the ID, you may override the getRouteKey method on your model:

```php
public function getRouteKey(){
    return $this->slug;
}
```

## Converting To Arrays / JSON
```php
// To array
$user = User::with('roles')->first();
return $user->toArray();
// To JSON
return User::find(1)->toJson();
```

### Returning A Model From A Route

```php
Route::get('users', function()
{
    return User::all();
});
```

### Hiding Attributes From Array Or JSON Conversion

Add a hidden property definition to your model:

```php
class User extends Model {
    protected $hidden = ['password'];
}
```

Note: When hiding relationships, use the relationship's method name, not the dynamic accessor name.

May use instead the visible property to define a white-list:

```php
protected $visible = ['first_name', 'last_name'];
```

Add array attributes that do not have a corresponding column:

```php
public function getIsAdminAttribute(){
    return $this->attributes['admin'] == 'yes';
}
```

Once you have created the accessor add the value to the appends property on the model:

```php
protected $appends = ['is_admin'];
```