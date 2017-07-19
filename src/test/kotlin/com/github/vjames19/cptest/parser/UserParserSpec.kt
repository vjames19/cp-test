package com.github.vjames19.cptest.parser

import com.github.vjames19.cptest.User
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldEqual
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it

/**
 * Created by victor.reventos on 7/19/17.
 */
object UserParserSpec : Spek({

    val parser = UserParser()

    describe("parse") {
        given("empty content") {
            it("should return an empty list") {
                parser.parse("".reader(), "".reader()).isEmpty() shouldBe true
            }
        }

        given("a list of users and empty connections with mixed tabs and spaces") {
            it("should be able to parse them") {
                val users = """1    Victor
            |2 Ly
            |3      Josh


            """.trimMargin()

                parser.parse(users.reader(), "".reader()) shouldEqual listOf(
                        User(1, "Victor", emptySet()),
                        User(2, "Ly", emptySet()),
                        User(3, "Josh", emptySet()))
            }
        }

        given("a list of users and connections") {
            it("should be able to parse them") {
                val users = "1\t Victor\n" + """2 Ly
                                    |3      Josh
                                    |5 Name
                                    |6 SomeName


                                    """.trimMargin()

                val connections = "1: 1 \t ,\t 2,\t3\n" + """2: 1,3
                                    |3: 1, 3
                                    |4: 5,6,7
                                    |5:
                                    |6:

                                    """.trimMargin()

                parser.parse(users.reader(), connections.reader()) shouldEqual listOf(
                        User(1, "Victor", setOf(1, 2, 3)),
                        User(2, "Ly", setOf(1, 3)),
                        User(3, "Josh", setOf(1, 3)),
                        User(5, "Name", emptySet()),
                        User(6, "SomeName", emptySet())
                )
            }
        }
    }
})