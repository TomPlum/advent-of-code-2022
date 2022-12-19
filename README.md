# :christmas_tree: Advent of Code 2022

![GitHub](https://img.shields.io/badge/stars-37%2F50-yellow)

## What is Advent of Code?

_Excerpt from the Advent of Code [website](https://adventofcode.com/2022/about);_

    "Advent of Code is an Advent calendar of small programming puzzles for a variety of skill sets
    and skill levels that can be solved in any programming language you like. People use them as a
    speed contest, interview prep, company training, university coursework, practice problems, or
    to challenge each other."

## Contents
* [Getting Started](#getting-started)
* [The Days](#the-days)
    * [The Most Fun](#the-most-fun)
    * [The Most Interesting](#the-most-interesting)
    * [The Most Challenging](#the-most-challenging)
* [Answer Table](#answer-table)
* [Advent Calendar](#advent-calendar)

## Getting Started
Simply clone or download the repository into your local environment and import it as a Gradle project in your IDE.
Running [Solutions.kt](https://git.io/JII6v) will run the parts from all the completed days, reporting all the
answers and runtimes in the console.

### Gradle Tasks
| Task      | Description                                                                                        |
|-----------|----------------------------------------------------------------------------------------------------|
| `test`    | Runs the unit tests with coverage for the `implementation`, `solutions` and `common` sub-projects. |
| `detekt`  | Runs DeteKT with the custom configuration rules defined in detekt-config.yml.                      |

### IntelliJ Run Configurations
The `.run` directory contains XML configuration files from IntelliJ. Included are configurations for running the unit
tests in the `common`, `implementation` and `solutions` Gradle sub-projects as well as for each specific day.

## The Days

### The Most Fun
### The Most Interesting
### The Most Challenging

## Answer Table

| Day | Part 1 Answer | Avg Time | Part 2 Answer | Avg Time | Documentation                            |
|-----|---------------|----------|---------------|----------|------------------------------------------|
| 01  | 67633         | 32ms     | 199628        | 1ms      | [Calorie Counting](docs/DAY01.MD)        |
| 02  | 11449         | 3ms      | 11449         | 1ms      | [Rock Paper Scissors](docs/DAY02.MD)     |
| 03  | 7581          | 4ms      | 2525          | 10ms     | [Rucksack Reorganization](docs/DAY03.MD) |
| 04  | 413           | 13ms     | 806           | 12ms     | [Camp Cleanup](docs/DAY04.MD)            |
| 05  | GRTSWNJHH     | 10ms     | QLFQDBBHM     | 5ms      | [Supply Stacks](docs/DAY05.MD)           |
| 06  | 1238          | 7ms      | 3037          | 15ms     | [Tuning Trouble](docs/DAY06.MD)          |
| 07  | 1477771       | 7ms      | 3579501       | 3ms      | [No Space Left On Device](docs/DAY07.MD) |
| 08  | 1801          | 169ms    | 209880        | 111ms    | [Treetop Tree House](docs/DAY08.MD)      |
| 09  | 6081          | 100ms    | 2487          | 125ms    | [Rope Bridge](docs/DAY09.MD)             |
| 10  | 13480         | 6ms      | EGJBGCFK      | 549ms    | [Cathode-Ray Tube](docs/DAY10.MD)        |
| 11  | 111210        | 3ms      | 15447387620   | 110ms    | [Monkey in the Middle](docs/DAY11.MD)    |
| 12  | 484           | 169ms    | 478           | 37ms     | [Hill Climbing Algorithm](docs/DAY12.MD) |
| 13  | 6072          | 3ms      | 22184         | 12ms     | [Distress Signal](docs/DAY13.MD)         |
| 14  | 692           | 37ms     | 31706         | 2s 401ms | [Regolith Reservoir](docs/DAY14.MD)      |
| 15  | -             | -        | -             | -        | [](docs/DAY15.MD)                        |
| 16  | 1767          | 344ms    | 2528          | 7s 93ms  | [Proboscidea Volcanium](docs/DAY16.MD)   |
| 17  | -             | -        | -             | -        | [](docs/DAY17.MD)                        |
| 18  | 3576          | 5ms      | 2066          | 37ms     | [Boiling Boulders](docs/DAY18.MD)        |
| 19  | 1365          | 2s 353ms | 4864          | 971ms    | [Not Enough Minerals](docs/DAY19.MD)     |
| 20  | -             | -        | -             | -        | [](docs/DAY20.MD)                        |
| 21  | -             | -        | -             | -        | [](docs/DAY21.MD)                        |
| 22  | -             | -        | -             | -        | [](docs/DAY22.MD)                        |
| 23  | -             | -        | -             | -        | [](docs/DAY23.MD)                        |
| 24  | -             | -        | -             | -        | [](docs/DAY24.MD)                        |
| 25  | -             | -        | -             | -        | [](docs/DAY25.MD)                        |

Average Execution Time: 1s 53ms \
Total Execution Time: 18s 965ms \
i7 5820K - OpenJDK 17.0.4 - Ubuntu 22.04
