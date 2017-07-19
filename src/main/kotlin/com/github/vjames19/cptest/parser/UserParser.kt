package com.github.vjames19.cptest.parser

import com.github.vjames19.cptest.User
import com.github.vjames19.cptest.UserId
import java.io.Reader

/**
 * Created by victor.reventos on 7/19/17.
 */
class UserParser {
    val spaceRegex = Regex("\\s+")

    fun parse(usersReader: Reader, connectionsReader: Reader): List<User> {
        val users = parseUsers(usersReader)
        val connections = parseConnections(connectionsReader)

        return users.map {
            val id = it.key
            val name = it.value
            val userConnections = connections[id] ?: emptySet()
            User(id, name, userConnections)
        }
    }

    internal fun parseUsers(reader: Reader): Map<UserId, String> {
        return reader.useLines {
            it.filter { it.isNotBlank() }.map { parseUserLine(it) }.toMap()
        }
    }

    private fun parseUserLine(line: String): Pair<Int, String> {
        val tokens = line.trim().split(spaceRegex).filter { it.isNotBlank() }
        val id = tokens[0].trim().toInt()
        val name = tokens[1].trim()
        return id to name
    }

    internal fun parseConnections(reader: Reader): Map<UserId, Set<UserId>> {
        return reader.useLines {
            it.filter { it.isNotBlank() }.map { parseConnectionsLine(it) }.toMap()
        }
    }

    private fun parseConnectionsLine(line: String): Pair<UserId, Set<UserId>> {
        val tokens = line.trim().split(':')

        val userId = tokens[0].trim().toInt()
        val connections = tokens[1].trim()
                .split(',')
                .filter { it.isNotBlank() }
                .map { it.trim().toInt() }
                .toSet()

        return userId to connections
    }

}