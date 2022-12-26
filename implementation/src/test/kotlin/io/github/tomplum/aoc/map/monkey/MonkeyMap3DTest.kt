package io.github.tomplum.aoc.map.monkey

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.github.tomplum.aoc.input.TestInputReader
import io.github.tomplum.aoc.map.monkey.CubeFace.*
import io.github.tomplum.libs.math.Direction
import io.github.tomplum.libs.math.Direction.*
import io.github.tomplum.libs.math.point.Point2D
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class MonkeyMap3DTest {

    @Test
    @Disabled("Until MonkeyMap3D can support the smaller cube faces from the example")
    fun examplePartTwo() {
        val notes = TestInputReader.read<String>("day22/example.txt").value
        val map = MonkeyMap3D(notes)
        assertThat(map.findFinalPassword()).isEqualTo(3051)
    }

    @Nested
    inner class StepRoundEdge {

        private val notes = TestInputReader.read<String>("day22/input.txt").value
        private val map = MonkeyMap3D(notes)

        @Nested
        inner class CurrentFaceA {
            @Nested
            inner class FacingRight {
                @Test
                fun `When stepping off the TOP of the edge of Face A while facing RIGHT`() {
                    val (newFace, newPosition, newFacing) = map.stepRoundEdge(Point2D(99, 0), A, RIGHT)
                    assertThat(newFace).isEqualTo(B)
                    assertThat(newPosition).isEqualTo(Point2D(100, 0))
                    assertThat(newFacing).isEqualTo(RIGHT)
                }

                @Test
                fun `When stepping off the MIDDLE of the edge of Face A while facing RIGHT`() {
                    val (newFace, newPosition, newFacing) = map.stepRoundEdge(Point2D(99, 25), A, RIGHT)
                    assertThat(newFace).isEqualTo(B)
                    assertThat(newPosition).isEqualTo(Point2D(100, 25))
                    assertThat(newFacing).isEqualTo(RIGHT)
                }

                @Test
                fun `When stepping off the BOTTOM of the edge of Face A while facing RIGHT`() {
                    val (newFace, newPosition, newFacing) = map.stepRoundEdge(Point2D(99, 49), A, RIGHT)
                    assertThat(newFace).isEqualTo(B)
                    assertThat(newPosition).isEqualTo(Point2D(100, 49))
                    assertThat(newFacing).isEqualTo(RIGHT)
                }
            }

            @Nested
            inner class FacingUp {
                @Test
                fun `When stepping off the RIGHT of the edge of Face A while facing UP`() {
                    val (newFace, newPosition, newFacing) = map.stepRoundEdge(Point2D(99, 49), A, UP)
                    assertThat(newFace).isEqualTo(C)
                    assertThat(newPosition).isEqualTo(Point2D(99, 50))
                    assertThat(newFacing).isEqualTo(UP)
                }

                @Test
                fun `When stepping off the MIDDLE of the edge of Face A while facing UP`() {
                    val (newFace, newPosition, newFacing) = map.stepRoundEdge(Point2D(75, 49), A, UP)
                    assertThat(newFace).isEqualTo(C)
                    assertThat(newPosition).isEqualTo(Point2D(75, 50))
                    assertThat(newFacing).isEqualTo(UP)
                }

                @Test
                fun `When stepping off the LEFT of the edge of Face A while facing UP`() {
                    val (newFace, newPosition, newFacing) = map.stepRoundEdge(Point2D(50, 49), A, UP)
                    assertThat(newFace).isEqualTo(C)
                    assertThat(newPosition).isEqualTo(Point2D(50, 50))
                    assertThat(newFacing).isEqualTo(UP)
                }
            }

            @Nested
            inner class FacingLeft {
                @Test
                fun `When stepping off the TOP of the edge of Face A while facing LEFT`() {
                    val (newFace, newPosition, newFacing) = map.stepRoundEdge(Point2D(50, 0), A, LEFT)
                    assertThat(newFace).isEqualTo(E)
                    assertThat(newPosition).isEqualTo(Point2D(0, 149))
                    assertThat(newFacing).isEqualTo(RIGHT)
                }

                @Test
                fun `When stepping off the MIDDLE of the edge of Face A while facing LEFT`() {
                    val (newFace, newPosition, newFacing) = map.stepRoundEdge(Point2D(50, 25), A, LEFT)
                    assertThat(newFace).isEqualTo(E)
                    assertThat(newPosition).isEqualTo(Point2D(0, 124))
                    assertThat(newFacing).isEqualTo(RIGHT)
                }

                @Test
                fun `When stepping off the BOTTOM of the edge of Face A while facing LEFT`() {
                    val (newFace, newPosition, newFacing) = map.stepRoundEdge(Point2D(50, 49), A, LEFT)
                    assertThat(newFace).isEqualTo(E)
                    assertThat(newPosition).isEqualTo(Point2D(0, 100))
                    assertThat(newFacing).isEqualTo(RIGHT)
                }
            }
        }
    }
}