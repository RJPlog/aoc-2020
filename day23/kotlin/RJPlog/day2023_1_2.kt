import java.io.File

// tag::crab_cup_2[]
fun crab_cup_2(input: String): Long {
	var circle = mutableListOf<Int>()

	input.forEach {
		circle.add(it.toString().toInt())
	}
	for (i in 10..1000000) { //1.000.000
		circle.add(i)
	}

	var current_cup: Int = circle.get(0)
	var current_cup_init = current_cup
	var current_index: Int = circle.indexOf(current_cup)
	var pick_up = mutableListOf<Int>()
	var destination: Int

	for (k in 1..10000000) { // 10.000.000
		if (k % 10000 == 0) {
			println("-- move ${k / 10000}0.000 --")
		}
//		println("cups: current cup: $current_cup, circle: $circle")
		pick_up.add(circle.get((current_index + 1) % 1000000))
		pick_up.add(circle.get((current_index + 2) % 1000000))
		pick_up.add(circle.get((current_index + 3) % 1000000))
//		println("pick up: $pick_up")
		pick_up.forEach {
			circle.remove(it)
		}
//		println(" remaining circle: $circle")
		destination = current_cup
		do {
			destination--
			if (destination == 0) {
				destination = 1000000
			}
		} while (pick_up.contains(destination))
		//} while (!circle.contains(destination))

//		println("destination: $destination")
//		println()
		circle.addAll(circle.indexOf(destination) + 1, pick_up)
		pick_up.clear()
		//		pick_up = mutableListOf()
		current_cup = circle.get((circle.indexOf(current_cup) + 1) % 1000000)
		current_index = circle.indexOf(current_cup)

	}

	println("-- final --")
//	println("cups: current cup: $current_cup, circle: $circle")

	var index = circle.indexOf(1)
	var result: Long = 1
	for (j in 1..2) {
		println(circle.get((j + index) % 1000000))
		result = result * circle.get((j + index) % 1000000)
	}

	return result
}
// end::crap_cup_2[]

// tag::crab_cup[]
fun crab_cup(input: String): String {
	var circle = mutableListOf<Int>()

	input.forEach {
		circle.add(it.toString().toInt())
	}

	var current_cup: Int = circle.get(0)
	var current_index: Int = circle.indexOf(current_cup)
	var pick_up = mutableListOf<Int>()
	var destination: Int

	var k : Int = 1
	//for (k in 1..100) {
	while (k <= 100) {
		println("-- move $k --")
		println("cups: current cup: $current_cup, circle: $circle")
		pick_up.add(circle.get((current_index + 1) % 9))
		pick_up.add(circle.get((current_index + 2) % 9))
		pick_up.add(circle.get((current_index + 3) % 9))
		println("pick up: $pick_up")
		pick_up.forEach {
			circle.remove(it)
		}
		println(" remaining circle: $circle")
		destination = current_cup
		do {
			destination--
			if (destination == 0) {
				destination = 9
			}
		} while (pick_up.contains(destination))
		
		println("destination: $destination")
		println()
		circle.addAll(circle.indexOf(destination) + 1, pick_up)
		pick_up.clear()
		//pick_up = mutableListOf()
		current_cup = circle.get((circle.indexOf(current_cup) + 1) % 9)
		current_index = circle.indexOf(current_cup)
		
		k++
	}

	println("-- final --")
	println("cups: current cup: $current_cup, circle: $circle")

	var index = circle.indexOf(1)
	var result: String = ""
	for (j in 1..8) {
		result = result + circle.get((j + index) % 9)
	}

	return result
}
// end::crap_cup[]


fun main(args: Array<String>) {

	var puzzle_input: String = "362981754"

// tag::part_1[]
	var t1 = System.currentTimeMillis()
	var solution1 = crab_cup(puzzle_input)
	t1 = System.currentTimeMillis() - t1
	println()
	println("part 1 solved in $t1 ms -> $solution1")
// end::part_1[]

// tag::part_2[]
	var t2 = System.currentTimeMillis()
	var solution2 = crab_cup_2(puzzle_input)
	t2 = System.currentTimeMillis() - t2
	println()
	println("part 2 solved in $t2 ms -> $solution2")
// end::part_2[]

// tag::output[]
// print solution for part 1
	println("*************************")
	println("--- Day 23: Crab Cups ---")
	println("*************************")
	println("Solution for part1")
	println("   $solution1 are the labels on the cups after cup 1")
	println()
// print solution for part 2
	println("****************************")
	println("Solution for part2")
	println("   $solution2 do you get if you multiply their labels together")
	println()
// end::output[]
}	
