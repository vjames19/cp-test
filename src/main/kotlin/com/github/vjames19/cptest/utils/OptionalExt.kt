package com.github.vjames19.cptest.utils

import java.util.*

/**
 * Created by victor.reventos on 7/19/17.
 */
fun <T> T?.toOptional(): Optional<T> = Optional.ofNullable(this)