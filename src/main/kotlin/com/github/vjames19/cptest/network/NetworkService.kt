package com.github.vjames19.cptest.network

import com.github.vjames19.cptest.User
import com.github.vjames19.cptest.UserId
import java.util.*

/**
 * Created by victor.reventos on 7/19/17.
 */
interface NetworkService {

    fun getUserWithMaxConnections(): Optional<User>

    fun getUserWithMinConnections(): Optional<User>

    fun whoCanIntroduce(userInQuestion: UserId, toUser: UserId): Set<User>

    fun numberOfCommonConnections(userA: UserId, userB: UserId): Int

    fun numberOfConnections(userId: UserId): Int

    fun getUser(userId: UserId): Optional<User>
}