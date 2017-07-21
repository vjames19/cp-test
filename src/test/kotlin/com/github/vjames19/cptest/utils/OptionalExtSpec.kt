package com.github.vjames19.cptest.utils

import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldEqual
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import java.util.*

/**
 * Created by victor.reventos on 7/19/17.
 */
object OptionalExtSpec : Spek({

    describe("toOptional") {
        given("a null value") {
            it("should return an empty optional") {
                null.toOptional().isPresent shouldBe false
            }
        }

        given("a non null value") {
            it("should return an optional of the given type") {
                listOf(1).toOptional().get() shouldEqual listOf(1)
                (1 to "one").toOptional().get() shouldEqual (1 to "one")
            }
        }
    }

    describe("zip") {
        given("that one of them is not present") {
            it("should return an empty optional") {
                Optional.empty<String>().zip(1.toOptional()) { _, _ -> 10 }.isPresent shouldBe false

                1.toOptional().zip(null.toOptional()) { _, _ -> 10 }.isPresent shouldBe false

                null.toOptional().zip(null.toOptional()) { _, _ -> 10}.isPresent shouldBe false

            }
        }

        given("that they are both present") {
            it("should apply the transformation") {
                "10".toOptional().zip(10.toOptional()) { a, b -> a.toInt() + b }.get() shouldEqual 20
            }
        }
    }
})