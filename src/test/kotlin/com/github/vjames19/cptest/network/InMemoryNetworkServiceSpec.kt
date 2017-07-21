package com.github.vjames19.cptest.network

import com.github.vjames19.cptest.User
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldEqual
import org.amshove.kluent.shouldEqualTo
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it

/**
 * Created by victor.reventos on 7/19/17.
 */
object InMemoryNetworkServiceSpec : Spek({
    describe("InMemoryNetworkService") {

        val user1 = User(1, name = "user1", connections = setOf(2, 3, 4, 9))
        val user2 = User(2, name = "user2", connections = setOf(1, 3, 4))
        val user3 = User(3, name = "user3", connections = setOf(1, 2, 4))
        val user4 = User(4, name = "user4", connections = setOf(1, 2, 3))
        val user5 = User(5, name = "user5", connections = setOf(6))
        val user6 = User(6, name = "user6", connections = setOf(5))
        val user7 = User(7, name = "user7", connections = emptySet())
        val user8 = User(8, name = "user8", connections = setOf(9))
        val user9 = User(9, name = "user9", connections = setOf(1, 8))

        val network = listOf(user1, user2, user3, user4, user5, user6, user7, user8, user9)
                .map { it.id to it }
                .toMap()

        val service = InMemoryNetworkService(network)

        describe("numberOfConnections") {
            given("a user that doesn't exist") {
                it("should return 0") {
                    service.numberOfConnections(-1) shouldEqualTo 0
                }
            }

            given("a user that exists") {
                it("should return its respective number of connections") {
                    service.numberOfConnections(1) shouldEqualTo 5
                    service.numberOfConnections(2) shouldEqualTo 4
                    service.numberOfConnections(3) shouldEqualTo 4
                    service.numberOfConnections(4) shouldEqualTo 4
                    service.numberOfConnections(5) shouldEqualTo 1
                    service.numberOfConnections(6) shouldEqualTo 1
                    service.numberOfConnections(7) shouldEqualTo 0
                    service.numberOfConnections(8) shouldEqualTo 2
                    service.numberOfConnections(9) shouldEqualTo 5
                }
            }

            given("that the degree is set to 1") {
                it("should only return the first degree connections") {
                    network.values.forEach {
                        service.numberOfConnections(it.id, 1) shouldEqualTo (it.connections - it.id).size
                    }
                }
            }
        }

        describe("getUserWithMaxNumberOfConnections") {
            given("an empty network") {
                it("should return an empty optional") {
                    InMemoryNetworkService(emptyMap()).getUserWithMaxConnections().isPresent shouldBe false
                }
            }

            given("a non empty network") {
                it("should return the one with the max number of connections") {
                    service.getUserWithMaxConnections().get() shouldEqual user1
                }
            }
        }

        describe("getUserWithMinNumberOfConnections") {
            given("an empty network") {
                it("should return an empty optional") {
                    InMemoryNetworkService(emptyMap()).getUserWithMinConnections().isPresent shouldBe false
                }
            }

            given("a non empty network") {
                it("should return the one with the max number of connections") {
                    service.getUserWithMinConnections().get() shouldEqual user7
                }
            }
        }

        describe("numberOfCommonConnections") {
            given("that both users exist") {
                it("should return the right number of common connections") {
                    service.numberOfCommonConnections(1, 1) shouldEqualTo service.numberOfConnections(1)
                    service.numberOfCommonConnections(1, 2) shouldEqualTo 3
                    service.numberOfCommonConnections(1, 3) shouldEqualTo 3
                    service.numberOfCommonConnections(1, 4) shouldEqualTo 3
                    service.numberOfCommonConnections(1, 5) shouldEqualTo 0
                    service.numberOfCommonConnections(1, 6) shouldEqualTo 0
                    service.numberOfCommonConnections(1, 8) shouldEqualTo 1

                }

                given("that the degree is set two one") {
                    it("should only return the first degree connections") {
                        service.numberOfCommonConnections(1, 2, 1) shouldEqualTo 2
                        service.numberOfCommonConnections(1, 3, 1) shouldEqualTo 2
                        service.numberOfCommonConnections(1, 4, 1) shouldEqualTo 2
                        service.numberOfCommonConnections(1, 5, 1) shouldEqualTo 0
                        service.numberOfCommonConnections(1, 6, 1) shouldEqualTo 0
                        service.numberOfCommonConnections(1, 8, 1) shouldEqualTo 1
                    }
                }

                given("that they are both the same users") {
                    network.keys.forEach {
                        for (degree in 0..4) {
                            service.numberOfCommonConnections(it, it, degree) shouldEqualTo service.numberOfConnections(it, degree)
                        }
                    }
                }
            }

            given("that one of the users doesn't exist") {
                it("should return 0") {
                    network.keys.forEach {
                        service.numberOfCommonConnections(it, -1) shouldEqualTo 0
                        service.numberOfCommonConnections(-1, it) shouldEqualTo 0
                    }
                }
            }
        }

        describe("whoCanIntroduce") {
            given("that the user doesn't have any connections") {
                it("should return an empty set, since the user doesn't have any connections them") {
                    service.whoCanIntroduce(1, 7) shouldEqual emptySet()
                    service.whoCanIntroduce(2, 7) shouldEqual emptySet()
                    service.whoCanIntroduce(3, 7) shouldEqual emptySet()
                    service.whoCanIntroduce(5, 7) shouldEqual emptySet()
                    service.whoCanIntroduce(6, 7) shouldEqual emptySet()
                }
            }

            given("a user that has connections but doesn't have a direct connection that can introduce them") {
                it("should return an empty set") {
                    service.whoCanIntroduce(5, 1) shouldEqual emptySet()
                    service.whoCanIntroduce(6, 1) shouldEqual emptySet()
                }
            }

            given("a users that have connections in common") {
                it("should return the connections that can introduce them") {
                    service.whoCanIntroduce(9, 2) shouldEqual setOf(user1)

                    // when there is a directed path only from 9 to one's circle, then one should be the only one to introduce them
                    service.whoCanIntroduce(2, 9) shouldEqual setOf(user1)
                    service.whoCanIntroduce(3, 9) shouldEqual setOf(user1)
                    service.whoCanIntroduce(4, 9) shouldEqual setOf(user1)

                    service.whoCanIntroduce(2, 9, 1) shouldEqual emptySet()
                    service.whoCanIntroduce(3, 9, 1) shouldEqual emptySet()
                    service.whoCanIntroduce(4, 9, 1) shouldEqual emptySet()

                    service.whoCanIntroduce(2, 8) shouldEqual emptySet()
                    service.whoCanIntroduce(3, 8) shouldEqual emptySet()
                    service.whoCanIntroduce(4, 8) shouldEqual emptySet()

                    service.whoCanIntroduce(2, 8, 3) shouldEqual setOf(user1)
                    service.whoCanIntroduce(3, 8, 3) shouldEqual setOf(user1)
                    service.whoCanIntroduce(4, 8, 3) shouldEqual setOf(user1)

                    service.whoCanIntroduce(1, 8) shouldEqual setOf(user9)
                    service.whoCanIntroduce(8, 1) shouldEqual setOf(user9)

                    service.whoCanIntroduce(3, 1) shouldEqual setOf(user1, user2, user4)
                    service.whoCanIntroduce(1, 3) shouldEqual setOf(user2, user3, user4)

                    service.whoCanIntroduce(3, 8) shouldEqual emptySet()
                    service.whoCanIntroduce(3, 8, 3) shouldEqual setOf(user1)
                }
            }
        }
    }
})