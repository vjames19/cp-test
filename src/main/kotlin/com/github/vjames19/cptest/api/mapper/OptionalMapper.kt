package com.github.vjames19.cptest.api.mapper

import org.jooby.Result
import org.jooby.Results
import org.jooby.Route
import org.jooby.Status
import java.util.*

/**
 * Created by victor.reventos on 7/19/17.
 */
class OptionalMapper : Route.Mapper<Any> {

    override fun map(value: Any?): Any? {
        return when (value) {
            is Optional<*> -> map(value)
            else -> value
        }
    }

    fun map(value: Optional<*>): Result {
        return if (value.isPresent) {
            Results.with(value.get())
        } else {
            Results.with(Status.NOT_FOUND)
        }
    }
}
