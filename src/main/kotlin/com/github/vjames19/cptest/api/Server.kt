package com.github.vjames19.cptest.api

import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.github.vjames19.cptest.api.di.ServiceModule
import com.github.vjames19.cptest.api.endpoint.NetworkEndpoint
import com.github.vjames19.cptest.api.mapper.OptionalMapper
import org.jooby.Kooby
import org.jooby.json.Jackson
import org.jooby.run

/**
 * Created by victor.reventos on 7/19/17.
 */
class Server : Kooby({
    use(ServiceModule)
    use(Jackson().module(KotlinModule()))

    map(OptionalMapper())

    use(NetworkEndpoint::class)
})

fun main(args: Array<String>) {
    run(::Server, *args)
}