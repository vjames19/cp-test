package com.github.vjames19.cptest

import com.github.vjames19.cptest.network.InMemoryNetworkService
import com.github.vjames19.cptest.parser.UserParser

/**
 * Created by victor.reventos on 7/19/17.
 */
fun main(args: Array<String>) {
    val parser = UserParser()
    val usersReader = UserParser::class.java.getResourceAsStream("/Person.txt").bufferedReader()
    val connectionsReaders = UserParser::class.java.getResourceAsStream("/Relationship.txt").bufferedReader()
    val network = parser.parse(usersReader, connectionsReaders).map { it.id to it }.toMap()
    val networkService = InMemoryNetworkService(network)
    val degree = 2

    println("How many total connections  Lucas(id=4) has? ${networkService.numberOfConnections(4, degree)}")
    val whoCanIntroduce = networkService.whoCanIntroduce(4, 62, degree)
    println("Who can introduce Lucas(id=4) to Adam(id=62)?  $whoCanIntroduce for a total of ${whoCanIntroduce.size} connections")
    println("How many connections are there between Lucas(id=4) and Adam(id=62)? ${networkService.numberOfCommonConnections(4, 62, degree)}")
    println("Which is the user with the highest number of connections? ${networkService.getUserWithMaxConnections(degree)}")
    println("Which is the user with the lowest number of connections? ${networkService.getUserWithMinConnections(degree)}")
}