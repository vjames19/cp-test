package com.github.vjames19.cptest.network

import com.github.vjames19.cptest.User
import com.github.vjames19.cptest.UserId
import com.github.vjames19.cptest.utils.toOptional
import com.github.vjames19.cptest.utils.zip
import java.util.*

/**
 * Created by victor.reventos on 7/19/17.
 */
class InMemoryNetworkService(private val network: Map<UserId, User>) : NetworkService {

    override fun getUserWithMaxConnections(degree: Int): Optional<User> {
        return network.maxBy { connections(it.value, degree).size }?.value.toOptional()
    }

    override fun getUserWithMinConnections(degree: Int): Optional<User> {
        return network.minBy { connections(it.value, degree).size }?.value.toOptional()
    }

    override fun whoCanIntroduce(userInQuestionId: UserId, toUserId: UserId, degree: Int): Set<User> {
        return getUser(userInQuestionId).zip(getUser(toUserId)) { _, toUser ->
            val result = mutableSetOf<User>()
            toUser.connections
                    .forEach { whoCanIntroduce(userInQuestionId, toUser, degree, mutableSetOf(), result) }
            result.toSet()
        }.orElseGet { emptySet() }
    }

    private fun whoCanIntroduce(userInQuestion: UserId, toUser: User, degree: Int, visited: MutableSet<UserId>, result: MutableSet<User>) {
        if (degree <= 0) return

        if (userInQuestion == toUser.id) return

        if (visited.contains(toUser.id)) return
        visited.add(toUser.id)

        if (toUser.connections.contains(userInQuestion)) result.add(toUser)

        toUser.connections.forEach {
            getUser(it).ifPresent { whoCanIntroduce(userInQuestion, it, degree - 1, visited, result) }
        }
    }

    override fun numberOfCommonConnections(userA: UserId, userB: UserId, degree: Int): Int {
        return commonConnections(userA, userB, degree).size
    }

    private fun commonConnections(userA: UserId, userB: UserId, degree: Int): Set<UserId> {
        return getUser(userA)
                .zip(getUser(userB)) { a, b ->
                    connections(a, degree).intersect(connections(b, degree))
                }.orElseGet { emptySet() }
    }

    override fun numberOfConnections(userId: UserId, degree: Int): Int {
        return getUser(userId)
                .map { connections(it, degree).size }
                .orElseGet { 0 }
    }

    fun connections(user: User, degree: Int): Set<UserId> {
        return mutableSetOf<UserId>().apply {
            connections(user, degree, this)
            remove(user.id)
        }.toSet()
    }

    private fun connections(user: User, degree: Int, result: MutableSet<UserId>): MutableSet<UserId> {
        if (degree < 0) return result

        if (result.contains(user.id)) return result
        result.add(user.id)

        user.connections.forEach {
            getUser(it).ifPresent {
                connections(it, degree - 1, result)
            }
        }

        return result
    }

    override fun getUser(userId: UserId): Optional<User> {
        return network[userId].toOptional()
    }
}