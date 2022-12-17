package io.github.tomplum.aoc.simluator.tower

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.github.tomplum.aoc.simluator.tower.RockType.*
import io.github.tomplum.libs.math.Direction.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class PyroclasticFlowTest {
    @Nested
    inner class GetJetStreamDirection {
        @Test
        fun first() {
            val flow = PyroclasticFlow(">><>")
            val direction = flow.getNextJetPatternDirection()
            assertThat(direction).isEqualTo(RIGHT)
        }

        @Test
        fun second() {
            val flow = PyroclasticFlow("><<>")
            flow.getNextJetPatternDirection()
            val second = flow.getNextJetPatternDirection()
            assertThat(second).isEqualTo(LEFT)
        }

        @Test
        fun last() {
            val flow = PyroclasticFlow("><<")
            flow.getNextJetPatternDirection()
            flow.getNextJetPatternDirection()
            val last = flow.getNextJetPatternDirection()
            assertThat(last).isEqualTo(LEFT)
        }

        @Test
        fun afterLast_shouldLoopBackRound() {
            val flow = PyroclasticFlow("><<")
            flow.getNextJetPatternDirection() // First
            flow.getNextJetPatternDirection() // Second
            flow.getNextJetPatternDirection() // Last
            val first = flow.getNextJetPatternDirection() // First again
            assertThat(first).isEqualTo(RIGHT)
        }
    }

    @Nested
    inner class GetNextRockType {
        @Test
        fun shouldLoopThroughEnum() {
            val flow = PyroclasticFlow("><><>>>>><<")

            val first = flow.getNextRock()
            assertThat(first).isEqualTo(HORIZONTAL)

            val second = flow.getNextRock()
            assertThat(second).isEqualTo(PLUS)

            val third = flow.getNextRock()
            assertThat(third).isEqualTo(L)

            val fourth = flow.getNextRock()
            assertThat(fourth).isEqualTo(VERTICAL)

            val fifth = flow.getNextRock()
            assertThat(fifth).isEqualTo(SQUARE)

            val firstAgain = flow.getNextRock()
            assertThat(firstAgain).isEqualTo(HORIZONTAL)
        }
    }
}