package com.github.vjames19.cptest.network

import com.github.vjames19.cptest.User
import com.github.vjames19.cptest.UserId
import java.util.*

/**
 * Created by victor.reventos on 7/19/17.
 */
interface NetworkService {

    companion object {
        const val DEFAULT_DEGREE = 2
    }

    fun getUserWithMaxConnections(degree: Int = DEFAULT_DEGREE): Optional<User>

    fun getUserWithMinConnections(degree: Int = DEFAULT_DEGREE): Optional<User>

    fun whoCanIntroduce(userInQuestionId: UserId, toUserId: UserId, degree: Int = DEFAULT_DEGREE): Set<User>

    fun numberOfCommonConnections(userA: UserId, userB: UserId, degree: Int = DEFAULT_DEGREE): Int

    fun numberOfConnections(userId: UserId, degree: Int = DEFAULT_DEGREE): Int

    fun getUser(userId: UserId): Optional<User>
}