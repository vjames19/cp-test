package com.github.vjames19.cptest.api.di

import com.github.vjames19.cptest.network.InMemoryNetworkService
import com.github.vjames19.cptest.network.NetworkService
import com.github.vjames19.cptest.parser.UserParser
import com.google.inject.AbstractModule
import com.google.inject.Binder
import com.google.inject.Provides
import com.google.inject.Singleton
import com.typesafe.config.Config
import org.jooby.Env
import org.jooby.Jooby

/**
 * Created by victor.reventos on 7/19/17.
 */
object ServiceModule : Jooby.Module, AbstractModule() {
    override fun configure() {
    }

    override fun configure(env: Env, conf: Config, binder: Binder) {
        binder.install(this)
    }

    @Provides
    @Singleton
    fun providesNetworkService(): NetworkService {
        val parser = UserParser()
        val usersReader = UserParser::class.java.getResourceAsStream("/Person.txt").bufferedReader()
        val connectionsReaders = UserParser::class.java.getResourceAsStream("/Relationship.txt").bufferedReader()
        val network = parser.parse(usersReader, connectionsReaders).map { it.id to it }.toMap()
        return InMemoryNetworkService(network)
    }
}