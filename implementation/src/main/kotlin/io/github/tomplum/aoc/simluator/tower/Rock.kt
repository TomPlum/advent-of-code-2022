package io.github.tomplum.aoc.simluator.tower

import io.github.tomplum.libs.math.point.Point2D

interface Rock {
    fun positions(start: Point2D): List<Point2D>

    fun xRightMost(xLeftMost: Int): Int

    fun height(): Int
}