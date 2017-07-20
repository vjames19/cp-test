package com.github.vjames19.cptest.network

import com.github.vjames19.cptest.User
import com.github.vjames19.cptest.UserId
import com.github.vjames19.cptest.utils.toOptional
import java.util.*

/**
 * Created by victor.reventos on 7/19/17.
 */
class InMemoryNetworkService(private val network: Map<UserId, User>) : NetworkService {

    // Given that the network is immutable, I'm caching the values for users with max and min connections
    private val userWithMaxConnections = network.maxBy { it.value.connections.size }?.value.toOptional()
    private val userWithMinConnections = network.minBy { it.value.connections.size }?.value.toOptional()

    override fun getUserWithMaxConnections(): Optional<User> {
        return userWithMaxConnections
    }

    override fun getUserWithMinConnections(): Optional<User> {
        return userWithMinConnections
    }

    override fun whoCanIntroduce(userInQuestion: UserId, toUser: UserId): Set<User> {
        return commonConnections(userInQuestion, toUser)
                .filter { exists(it) }
                .map { network[it]!! }
                .toSet()
    }

    override fun numberOfCommonConnections(userA: UserId, userB: UserId): Int {
        if (!exists(userA) || !exists(userB)) return 0

        return network[userA]!!.connections.intersect(network[userB]!!.connections).size
    }

    private fun commonConnections(userA: UserId, userB: UserId): Set<UserId> {
        if (!exists(userA) || !exists(userB)) return emptySet()

        return network[userA]!!.connections.intersect(network[userB]!!.connections)
    }


    override fun numberOfConnections(userId: UserId): Int {
        return network[userId]?.connections?.size ?: 0
    }

    override fun getUser(userId: UserId): Optional<User> {
        return network[userId].toOptional()
    }

    private fun exists(userId: UserId): Boolean = network.containsKey(userId)
}