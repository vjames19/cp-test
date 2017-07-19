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
    val networkService = InMemoryNetworkService(parser.parse(usersReader, connectionsReaders).map { it.id to it }.toMap())

    println("How many total connections  Lucas(id=4) has? ${networkService.numberOfConnections(4)}")
    println("Who can introduce Lucas(id=4) to Adam(id=62)? ${networkService.whoCanIntroduce(4, 62)}")
    println("Which is the user with the highest number of connections? ${networkService.getUserWithMaxConnections()}")
    println("Which is the user with the lowest number of connections? ${networkService.getUserWithMinConnections()}")
}