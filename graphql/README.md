R# GraphQL

## Core concepts

### Types

* Scalar: `Int`, `Float`, `String`, `Boolean`, `ID` (unique identifier for re-fetch or key for cache)
* Struct
* ENUMS:
```
enum language {
    ENGLISH
    SPANISH
}
```
* Query: `QUERY` represent what the client is querying for.
* Mutation: `Mutation` When updates in the database are performed.
* Non-nullable: adds a`!` to the type `type Author { id: ID!}`

### Queries

* [Github Graphql explorer](https://docs.github.com/en/graphql/overview/explorer)

**query fields**

```graphql
query {
  viewer {
    login
    bio
    isHireable
    name
  }
}
```

**arguments**
Combine API fetches.
```graphql
query {
  viewer {
    login
    bio
    name
    followers(first: 3) {
      nodes {
        name
        id
      }
    }
  }
}
```

**aliases**

```graphql
query {
  viewer {
    login
    bio
    name
    firstFollowers: followers(first: 3) {
      nodes {
        name
        id
      }
    }
    lastFollowers: followers(last: 3) {
      nodes {
        name
        id
      }
    }
  }
}
```

**fragments**
Reusable units function like in GraphQL

```graphql
query {
  viewer {
    login
    bio
    name
    firstFollowers: followers(first: 3) {
      nodes {
        name
        id
        bio
        bioHTML
        avatarUrl
      }
    }
    lastFollowers: followers(last: 3) {
      nodes {
        name
        id
        bio
        bioHTML
        avatarUrl
      }
    }
  }
}

To avoid the repetition fragment to the resque: fragment [Name] on [Entity]
```graphql
fragment userInfo on User {
    name
    id
    bio
    bioHTML
    avatarUrl
}

query {
  viewer {
    login
    bio
    name
    firstFollowers: followers(first: 3) {
      nodes {
        ...userInfo

      }
    }
    lastFollowers: followers(last: 3) {
      nodes {
        ...userInfo
      }
    }
  }
}
```

**Operation names**
Explicit name for operations.

```graphql
query ViewerInfo {
  viewer {
    login
    bio
    isHireable
    name
  }
}
```

**Variables**

```graphql
query ViewerInfo($isOwner: Boolean!) {
  viewer {
    id
    name
    starredRepositories(ownedByViewer: $isOwner, last: 5) {
      nodes {
        id
        name
      }
    }
  }
}
```

pass the variable in the query variables dashboard at: https://docs.github.com/en/graphql/overview/explorer

### Mutations
Make changes to the data. Mutations are run in series.

**query**
```graphql
mutation NewStatus ($input: ChangeUserStatusInput!) {
  changeUserStatus(input: $input) {
    clientMutationId
    status {
      message
    }
  }
}
query ViewerInfo {
  viewer {
    login
    name
    status {
      id
      message
    }
  }
}
```
**query variables**

```graphql
{
  "input": {
    "clientMutationId": "01010",
    "message": null
  }
}
```

## Tools

**client tools**
* [Apollo client](https://www.apollographql.com/docs/)
* [Relay](https://relay.dev/) (just suitable for React apps)

**server tools**
* Resolvers: function that resolves a vaule for a type/field GraphQL schema
* Network layer: transport queries form the client to the network.
* GraphQL execution language: parses query from client, validate schema and retuns JSON response.
* Batch resolving: just doing one query to the client.

This task a managed bu the server libraries. Most commons are:
* [Apollo server](https://www.apollographql.com/docs/apollo-server/)
* [GraphQL Express](https://graphql.org/graphql-js/express-graphql/)
* [GraphQL Yoga](https://github.com/dotansimha/graphql-yoga)

**database to graphql server**
* Prisma: layer between the database and the graphql server resolvers  (support SQL/non-SQL). Provides type-safe database access it adds graphql subscriptions and includes GUI called prisma admin.
* [Prisma ORM](https://www.prisma.io/)
* [GraphiQL](https://github.com/graphql/graphiql)
* [GraphQL Voyager](https://apis.guru/graphql-voyager/)
* [GraphQL Faker](https://github.com/APIs-guru/graphql-faker)
* [Graphql Visual Editor](https://graphqleditor.com/)
