## Install MongoDB
1. Download [MongoDB](https://www.mongodb.com/download-center#community) according to your OS.
2. Create a directory for data, can be any, it will be provided while starting MongoDB.

### Relational VS MongoDB
RDBMS | Mongo
--- | ---
Database | 
Table, View | Collection
Row | JSON Document
Column | Field
Index | Index
Join | Embedded Document / Linking across Document
Foreign Key | Reference
Partition Key | Shard

## Shell
```bash
mongo

db  #shows db
show dbs  #shows db size
```

## Start MongoDB
```bash
# access mongo folder
cd 'mongo_install'/bin folder
# start mongo database
mongod
# start mongo shell
mongo
# Note: If your db path is not the default, make sure to launch with this command - mongod –dbpath/<path>(needs to have write permission)
```

### Download test
```bash
# download json from: 
# http://media.mongodb.org/zips.json

# Import
mongoimport --db test --collection zips --drop --file zips.json

# Start mongo shell
mongo
# Switch to test database
mongo > use test
mongo > db.zips.findOne()
```


## CRUD
https://docs.mongodb.com/manual/crud/

## Aggregation

SQL | Mongo
--- | ---
WHERE | $match
GROUP BY | $group
SELECT | $project
ORDER BY | $sort
LIMIT | $limit
SUM() | $sum
COUNT() | $count

```sql
db[:zips].find().aggregate([
    {:$match=>{:state =>'NY'}}, 
    {:$group=>{ :_id=>'NY',:population=>{:$sum=>'$pop'}}}
]).to_a
```

## Ex

SQL | Mongo
--- | ---
SELECT COUNT(*) AS count FROM zips | db[:zips].find.aggregate([{:$group => {
:_id => 0,count:{:$sum => 1}}}])
SELECT SUM(pop) AS total FROM zips | db[:zips].find.aggregate([{ :$group => {
:_id =>0, total: {:$sum => "$pop" }}}])