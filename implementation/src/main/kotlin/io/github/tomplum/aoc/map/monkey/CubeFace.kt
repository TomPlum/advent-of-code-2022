package io.github.tomplum.aoc.map.monkey

/**
 * Represents the face of a cube whose
 * net looks like the following.
 *   ╭╌╌╌┌───┬───┐
 *   ┊   │ A │ B │
 *   ┊╌╌╌├───┼───┘
 *   ┊   │ C │   ┊
 *   ┌───┼───┤╌╌╌┊
 *   │ E │ D │   ┊
 *   ├───┼───┘╌╌╌┊
 *   │ F │   ┊   ┊
 *   └───┘╌╌╌ ╌╌╌╯
 */
enum class CubeFace {
    A, B, C, D, E, F
}