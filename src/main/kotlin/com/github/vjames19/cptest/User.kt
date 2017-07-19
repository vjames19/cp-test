package com.github.vjames19.cptest

/**
 * Created by victor.reventos on 7/19/17.
 */
data class User(val id: Int, val name: String, val connections: Set<Int>)

typealias UserId = Int