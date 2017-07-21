package com.github.vjames19.cptest.utils

import java.util.*

/**
 * Created by victor.reventos on 7/19/17.
 */
fun <T> T?.toOptional(): Optional<T> = Optional.ofNullable(this)

fun <A, B, C> Optional<A>.zip(other: Optional<B>, f: (A, B) -> C): Optional<C> =
        flatMap { a -> other.map { b -> f(a, b) } }