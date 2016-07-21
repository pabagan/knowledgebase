# Redis 101s

* [Redis](https://redis.io/).
* [Redis Docs](https://redis.io/documentation).

## Data types
* Strings
* Hashes
* Lists
* Sets
* Sorted Sets
* Bitmaps
* Hyperlogs
* Geospatial Indexes

## Redis install
```bash
#install 
sudo apt-get install redis-server

# copy default config
cp /etc/redis/redis.conf /etc/redis/redis.conf.default 

# test installation
redis-cli
 
> ping
```

## Redis config
Config is at `redis.conf`.

```bash
# redis.conf
Bind 127.0.0.1

# rename command
rename-command CONFIG b840fc02d524045429941cc15f59e41cb7be6c52
rename-command CONFIG "" # remove command
```

## Redis CLI

Start by `redis-cli`.

```bash
PING
ECHO hello

# set/get key/value pair
SET foo 100
GET foo

# increment/decrement by 1
INCR foo
DECR foo

# check existance
EXISTS bar
EXISTS bar2

# delete
DEL bar
EXISTS bar # return 0 if not exist

# write output to a file
$ redis-cli ECHO Hello
$ redis-cli GET foo
$ redis-cli INCR foo > commands.txt

# monitor redis
monitor

# cache clear for redis
FLUSHALL

# use nameyspaces
SET server:name myserver
GET server:name
SET server:port 6379
GET server:port
SET resource:foo hello

# expiration date (seconds)
EXPIRE resource:foo 120
TTL resource:foo # shows the time remaining to expire
                 # will return -1 if not defined a expire
                 # will return -2 if defined and expired
```

## Key-Value Pair Commands
```bash
# set/get multiple values to multiple keys
MSET key1 "Hello" key2 "World"
MGET key1 key2
# same but not perform if a single key exist
MSETNX key3 "hi"
MSETNX key3 "hello" key4 "there"
SET greeting "Hello"
GET greeting

# append value
APPEND greeting " World"
APPEND foo "bar"

# rename key
RENAME greeting greet
GET greet
# same but not perform if a single key exist
RENAMENX greet greeting
RENAMENX key1 greting

# get key value slicing the result
SET mystring "This is my string"
GETRANGE mystr 0 -1 # this would return all result
GETRANGE mystr 0 5

# get the previously declared value
GETSET foo
GET foo

# set with expire 1-command (seconds)
SETEX mykey 10 "hello"
TTL mykey # time remaining (seconds)
# set with expire 1-command (milliseconds)
PSETEX mykey 1000 "hello"
PTTL mykey # time remaining (milliseconds)

# remove timeout setted
PERSIST mykey

# if the keys exist does nothing
SETNX newkey "foobar"
GET foobar
SETNX newkey "barfoo"
```

### SCAN
Iterates the set of keys in the database
Returns only a small amount per call
Can be used in production
Takes cursor / position as a parameter

```bash
MSET key1 "1" key2 "2" key3 "3" key4 "4" key5 "5" key6 "6" key7 "7" key8 "8" key9 "9" key10 "10" key11 "11" key12 "12" key13 "13"
SCAN 0
SCAN 13
SCAN 0 COUNT 5
# Used with sets. Returns list of set members
SSCAN
#Used with hashes. Returns array of elements with a field and value
HSCAN
#Used with sorted sets. Returns array of elements with associated score
ZSCAN
SCAN COUNT 100

# wildcards *
SCAN 0 MATCH key1*
SCAN 0 MATCH key1* COUNT 2
KEYS *
KEYS key1*

# keys patern
h?ello # matches any single char 
h*ello # matches multiple chars
h[io]llo # matches selection
h[^io]llo # exclusion
h[i-o]llo # exclusion
# \ scape special

# return ramdom key from db
RANDOMKEY
```

## Config & Client

```bash
# CONFIG
# Used to read the configuration parameters of a running Redis server
# get config value
CONFIG GET <value>
CONFIG GET port

# get all config
CONFIG GET *
# get with wild cards
CONFIG GET *max-*-entries*

# set config
CONFIG SET lua-time-limit 6000
CONFIG RESETSTAT

# INFO
# Returns information and statistics about a server
server | clients | memory | persistence | stats |replication |cpu
commandstats | cluster | keyspace | all | default
INFO
INFO cpu


# COMMAND
# Returns details about all Redis commands
COMMAND

# Returns details for a specific command
COMMAND INFO GET
# Returns number of available commands
COMMAND COUNT

# CLIENT
# Returns info and stats on the clients connected to a server
CLIENT LIST
# Assigns a name to a current client connection
CLIENT SETNAME foo
CLIENT SET NAME bar
# returns the name of the client
CLIENT GETNAME

# close client connection
CLIENT KILL <ip|id>
CLIENT KILL 127.0.0.1:xxxxx
```

## Lists

Same common ones :)

```sh
# push left/right
LPUSH friends "Bob"
RPUSH friends "Tony"

# get list values
LRANGE friends 0 -1 # return every item from 0
LRANGE friends 1 2

LLEN friends
# pop left/right
LPOP friends
RPOP friends

# Insert
LINSERT friends BEFORE "Bob" "Kevin"
LRANGE friends 0 -1
```

## Sets

Unorder collection of string. Not doubles. Supporting commands to compute.

```sh
# add keys to a set
SADD carmakes "Toyota"
SADD carmakes "Ford"
SADD carmakes "Chevy"
SADD carmakes "Honda"

# Remove
SREM carmakes "Honda"

# checker: return 1 if it is or 0 if not.
SISMEMBER carmakes "Honda"
SISMEMBER carmakes "Hondas"

# return a list with the members of the set
SMEMBERS carmakes

# count members in a set
SCARD carmakes
SADD mycars "Acura"

# rename key
SMOVE carmakes mycars "Toyota"

# return a list with a set
SUNION key1 key2 ...

# SDIFF
# returns member different to the first and its succesion.
SDIFF key1 key2

# SMEMBERS
# print values of the set
SMEMBERS mycars
SMEMBERS carmakes

# SRANDMEMBER
# Returns a random member
SRANDMEMBER carmakes
# return a specified count
SRANDMEMBER carmakes 3
# Removes and returns a random member
SPOP carmakes
```

## Sorted Sets
Each element has a score to sort with. Formately is a float value since `1970 == 1970.0

```sh
# add
ZADD people 1970 "John Doe"
ZADD people 1985 "Sam Smith"
ZADD people 1990 "Jen Williams"
# rem
ZREM people"Jen Williams" 
# get range
ZRANGE people 0 -1
# get range by score
ZRANGEBYSCORE people 1970
# get score by value
ZRANK people "Sam Smith"
ZRANK people "John Doe"
ZRANK people "Jen Williams"
# sort in the oposite direction
ZREVRANK people "Jen Williams"
# count members
ZCARD people
# count membert within a min and a max.
ZCOUNT people(1,3)

# increment score
# if not provided create and add the score
ZINCRBY people 1 "John Doe"
ZINCRBY people -1 "John Doe"
ZSCORE peope "John Doe"
```

## Hashes

```sh
HSET user:john name "John Doe"
HGET user:john name

# set/get multiple fields
HMSET user:kate name "Kate Smith" email "kate@gmail.com" age "30"
HMGET user:kate name age

HGETALL user:kate
# return all the fields
HKEYS user:kate
# return all the values
HVALS user:kate

# increment key or create if not exists
HINCRBY user:kate age
# check existence
HEXIST name user3 # returns 0/1
# delete special fields from a hash
HDEL user:kate age
HGETALL user:kate
# get lenghs
HLEN user:kate # 0/1 if exist or not

# return string lenght of the value in the field hash
HSTRLEN user:kate name
```

## Data Persistence

options:

* RDB – Point-in-time snapshots
* AOF – Write operation logging
* Disabled
* Both RDB & AOF

### RDB (Redis Database File) – Point-in-time snapshots
* Simplest persistence mode
* Enabled by default
* Single-file point-in-time representation
* Uses snapshots

#### Snapshoting
* Controlled by the user
* Can be modified at runtime
* Snapshots are produced as .rdb files
* SAVE & BGSAVE Commands

#### SAVE & BGSAVE
```bash
# Performs a synchronous save of the dataset producing a 
# point-in-time snapshot of all data inside of the Redis 
# instance in the form of an .rdb file.
# Should not be called in production it will block all other clients. Instead, use BGSAVE
SAVE
# Dump dataset to disk every 60 seconds if at least 1000 keys changed
SAVE 60 100
# Saves the DB in the background and the parent continues to serve clients
BGSAVE
```

#### Pros
* Easy to use.
* Very compact.
* Perfect for backup & recovery.
* Maximizes Redis performance.
* Allows faster restarts with big datasets compared to AOF.

#### Const
* Limited to save points.
* Not good if you want to minimize the chance of data loss if Redis stops working.
* Needs to fork() often which can be time consuming and can wear on CPU performance

### AOF – Append Only File
* Main persistence option
* Every operation gets logged
* Log is the same format used by clients
* Can be piped to another instance
* Dataset can be reconstructed

#### AOF Rewrite
* Used when AOF file gets too big
* Rewrite database from scratch
* Directly access data in memory
* No need for disk access
* Once written, the temp file is synched on to disk

#### fsync Policies
* No fsync – Done by OS. Usually every 30s or so
* fsync every second (default)
* fsync at every query (slow)

#### Pros

* Much more durable
* Single file with no corruption
* Automatically rewrites in the background if it gets too big
* Easy to understand log / instructions

#### Cons

* Takes longer to load in memory on server restart
* Usually bigger than the equivalent RDB files
* Can be slower than RDB depending on the fsync policy
* More possible bugs

### Commands
```bash

# locate where Redis save data
redis-cli config get dir

# create and save
redis-cli
SET user:john "John Doe"
SET user:mary "Mary Williams"
SET user:jill "Jill Jackson"
SET user:paul "Paul Harris"
SET user:mike "Mike SMith"
SET user:derek "Derek Hanlon"
SAVE
exit

sudo cp /data/dump.rdb /home/YOURUSER/redis-backup
sudo nano /home/brad/redis/redis-backup

sudo apt-get install -y rdiff-backup
sudo rdiff-backup --preserve-numerical-ids /data/redis /home/YOURUSER/redis

# adding a cron job
sudo crontab -e
# -> will ask for the editor to set the cron

# cronfile
# SETUP DAILY BACKUP
0 0 * * * rdiff-backup --preserve-numerical-ids --no-file-statistics /var/lib/redis /home/YOURUSER/redis

redis-cli
SET user:mike "Mike Wells"
exit
sudo rdiff-backup --preserve-numerical-ids /var/lib/redis /home/YOURUSER/redis
sudo nano /home/brad/redis/dump.rdb

#
# ENABLE AOF
# better for contact save/writting
#
BGREWRITEAOF
info
# Scroll to the Persistence section, and check that the aof entries match what's shown here. If aof_rewrite_in_progress is 0, then the recreation of the AOF file has completed
exit

# change Redis conf file 
sudo nano /etc/redis/redis.conf
appendonly yes
sudo service redis-server start
sudo rdiff-backup --preserve-numerical-ids /var/lib/redis /home/YOURUSER/redis
redis-cli
SET user:tom "Tom Doe"
exit
sudo rdiff-backup --preserve-numerical-ids /var/lib/redis /home/YOURUSER/redis
```


## What is Redis?
## Redis Commands
## Data Structures / Types
## Redis Clients
## Node.js / Redis Application

## Cretid
* [Brad Traversy Redis Course](https://twitter.com/traversymedia/status/843461312403456000).