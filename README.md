# cp-test

# How to run

The following starts the server
```sh
./gradlew run
```

## commands

Get a user by id
```
curl -X GET \
  http://localhost:8080/network/1
```

Get the user with min number of connections
```
curl -X GET \
  http://localhost:8080/network/minConnections
```

Get the user with max number of connections
```
curl -X GET \
  http://localhost:8080/network/maxConnections
```

Get the size of connections for a given user
```
curl -X GET \
  http://localhost:8080/network/1/connections/size
```

Get the number of connections in common between two users
```
curl -X GET \
  http://localhost:8080/network/1/connections/common/2
```

Get the users that can introduce two users
```
curl -X GET \
  http://localhost:8080/network/5/introduce/9
```

# Test

```
./gradlew test
```