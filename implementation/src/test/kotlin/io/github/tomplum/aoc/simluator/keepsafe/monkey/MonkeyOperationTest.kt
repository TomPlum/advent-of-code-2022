package io.github.tomplum.aoc.simluator.keepsafe.monkey

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class MonkeyOperationTest {
    @Test
    fun multiplyByInteger() {
        val operation = MonkeyOperation("  Operation: new = old * 19")
        val newWorryLevel = operation.execute(2)
        assertThat(newWorryLevel).isEqualTo(38)
    }

    @Test
    fun addToInteger() {
        val operation = MonkeyOperation("  Operation: new = old + 6")
        val newWorryLevel = operation.execute(26)
        assertThat(newWorryLevel).isEqualTo(32)
    }

    @Test
    fun multiplyByOld() {
        val operation = MonkeyOperation("  Operation: new = old * old")
        val newWorryLevel = operation.execute(10)
        assertThat(newWorryLevel).isEqualTo(100)
    }

    @Test
    fun addToOld() {
        val operation = MonkeyOperation("  Operation: new = old + old")
        val newWorryLevel = operation.execute(10)
        assertThat(newWorryLevel).isEqualTo(20)
    }

    @Test
    fun unknownOperator() {
        val operation = MonkeyOperation("  Operation: new = old - 5")
        val e = assertThrows<IllegalArgumentException> {  operation.execute(10) }
        assertThat(e.message).isEqualTo("Unknown monkey operation operator value [-]")
    }
}