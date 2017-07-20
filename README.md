# cp-test

For the exercise I decided to create both a Server and a Console application.

The server allows you to execute dynamic queries.

The console application answers the static questions.

Main logic is implemented: https://github.com/vjames19/cp-test/blob/master/src/main/kotlin/com/github/vjames19/cptest/network/InMemoryNetworkService.kt

The file parser is implemented: https://github.com/vjames19/cp-test/blob/master/src/main/kotlin/com/github/vjames19/cptest/parser/UserParser.kt

The server is implemented: https://github.com/vjames19/cp-test/blob/master/src/main/kotlin/com/github/vjames19/cptest/api/Server.kt

# How to run

## Console
```
./gradlew execute -PmainClass="com.github.vjames19.cptest.ConsoleApplicationKt"
```

## Server
The following starts the server
```sh
./gradlew run
```

### Commands

Get a user by id
```
curl -X GET http://localhost:8080/network/1
```

Get the user with min number of connections
```
curl -X GET http://localhost:8080/network/minConnections
```

Get the user with max number of connections
```
curl -X GET http://localhost:8080/network/maxConnections
```

Get the size of connections for a given user
```
curl -X GET http://localhost:8080/network/1/connections/size
```

Get the number of connections in common between two users
```
curl -X GET http://localhost:8080/network/1/connections/common/2
```

Get the users that can introduce two users
```
curl -X GET http://localhost:8080/network/5/introduce/9
```

# Test

```
./gradlew test
```