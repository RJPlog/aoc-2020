// sudo apt-get update && sudo apt-get install kotlin
// kotlinc day2020_1_2.kt -include-runtime -d day2020_1_2.jar && java -jar day2020_1_2.jar


import java.io.File
import kotlin.math.*


// tag::image_2[]
fun image_2(input_1: MutableMap<Int, Tile<String, String, String, String, String>>, input_2: Int): Long {

	var tiles = input_1
	var part = input_2

	// tag::search_corners[]
	var sum: Int
	var first_corner: Int = 0
	var result: Long = 1

	tiles.forEach {
		var tile_id = it.key
		var borderA = it.value.up
		var borderB = it.value.left
		var borderC = it.value.right
		var borderD = it.value.down
		var texture = it.value.texture
		var sumA = 0
		var sumB = 0
		var sumC = 0
		var sumD = 0
		tiles.forEach {

			if (it.key != tile_id) {
				if (borderA == it.value.up || borderA == it.value.up.reversed()) {
					sumA++
				} else if (borderA == it.value.left || borderA == it.value.left.reversed()) {
					sumA++
				} else if (borderA == it.value.right || borderA == it.value.right.reversed()) {
					sumA++
				} else if (borderA == it.value.down || borderA == it.value.down.reversed()) {
					sumA++
				}
				if (borderB == it.value.up || borderB == it.value.up.reversed()) {
					sumB++
				} else if (borderB == it.value.left || borderB == it.value.left.reversed()) {
					sumB++
				} else if (borderB == it.value.right || borderB == it.value.right.reversed()) {
					sumB++
				} else if (borderB == it.value.down || borderB == it.value.down.reversed()) {
					sumB++
				}
				if (borderC == it.value.up || borderC == it.value.up.reversed()) {
					sumC++
				} else if (borderC == it.value.left || borderC == it.value.left.reversed()) {
					sumC++
				} else if (borderC == it.value.right || borderC == it.value.right.reversed()) {
					sumC++
				} else if (borderC == it.value.down || borderC == it.value.down.reversed()) {
					sumC++
				}
				if (borderD == it.value.up || borderD == it.value.up.reversed()) {
					sumD++
				} else if (borderD == it.value.left || borderD == it.value.left.reversed()) {
					sumD++
				} else if (borderD == it.value.right || borderD == it.value.right.reversed()) {
					sumD++
				} else if (borderD == it.value.down || borderD == it.value.down.reversed()) {
					sumD++
				}
			}
		}
		sum = sumA + sumB + sumC + sumD
		if (sumA == 0) {
			borderA = borderA.replaceRange(1, 9, "________")
		}
		if (sumB == 0) {
			borderB = borderB.replaceRange(1, 9, "________")
		}
		if (sumC == 0) {
			borderC = borderC.replaceRange(1, 9, "________")
		}
		if (sumD == 0) {
			borderD = borderD.replaceRange(1, 9, "________")
		}
		//println("tile_id $tile_id has $sum connections to ID ${it.key}")
		if (sum == 2) {
			//println("tile_id $tile_id has $sum connections to ID ${it.key}")
			result = result * tile_id.toLong()
			first_corner = tile_id
		}
		// replace all boarders which are not connected to an other tile
		tiles.put(tile_id, Tile(borderA, borderB, borderC, borderD, texture))
	}
	// end::search_corners[]
	if (part == 1) {
		return result
	}

	println()
	println("Tiles after removing unnecessary borders")
	tiles.forEach {
		println(it)
	}

	// up to now we have the tiles map, we have removed all unecessary boarders, and we have a corner to start.

	// tag::built_grid[]
	var grid = mutableMapOf<Pair<Int, Int>, Char>()
	var xoff: Int = 0
	var yoff: Int = 0
	var current_tile = tiles.getValue(first_corner)


	//************* hier wieder einsteigen mit neuem Wert für ein Eckmodul (id von downcorner) 

	println("Start Tile: $first_corner, ${tiles.getValue(first_corner)}")

	// turn/flip first_corner until left and up boarder contain '_'
	while (!current_tile.up.contains('_') || !current_tile.left.contains('_')) {
		current_tile = rotate_tile(tiles.getValue(first_corner))
		println("turn necessary")
	}
	var current_right = current_tile.right
	var current_down = current_tile.down

	println("new Tile: $current_tile")
	println()
	for (y in (0 + yoff)..(7 + yoff)) {
		for (x in (0 + xoff)..(7 + xoff)) {
			grid.put(Pair(x, y), current_tile.texture.get(x + 8 * y))
		}
	}




	for (y in 0 + yoff..7 + yoff) {
		for (x in 0 + xoff..7 + xoff) {
			print(grid.getValue(Pair(x, y)))
		}
		println()
	}
	println()
	println("current_tile right corner ${current_right}, down corner ${current_down}")

	xoff = xoff + 8

	tiles.remove(first_corner)  // die Kanten sind bekannt, also brauche ich diesen Eintrag nicht mehr?


	println()
	println("now searching for a corner fitting to right corner: $current_right ")
	println()
	tiles.forEach {
		println(it)

	}

	//******************************************
	//  20.12., 22:00: Beliebige Ecke identifiziert, und so gedreht, das links und oben die Ränder sind.
	// weiter geht es mit aufsetzen des Grids, xoffset und yoffset sind 0, ab hier jetzt die texture abspeichern, nach jedem 8. wert y erhöhen,
	// am Schluss den X Offset um 8 erhöhen,
	// weiter geht es mit suchen nach dem nächtsen element das an die rechte Kante passt, durch alle tiles gehen, jedes element drehen und flipen, solage bis eines passt.
	// dann neue rechte Kante ermitteln, und element ab XOFFSEt abspeichern.
	// das ganze 9 mal machen und dann den XOffset wieder auf null stellen, aber dafür den YOffset um 8 erhöhen.
	// jetzt im Prinzip anschlusspunkt an down suchen und als neue Ecke in den prozess einspeisen.


	// store texture into new xpos/ypos map

	// get down boarder and right boarder

	//for right boarder, search all tiles for match. When necessary, turn/flip until match.
	// store texture into xpos/ypos map

	// get rigth boarder, repeat search (9 times

	// search down boarder, get new down boarder and right boarder, repeat 9 times

	// remove boarders out of xpos/ypos map (100/100 --> 64/64 map
	// --> now it seems to have a xpos/ypos map,where we can search for the sea monster (necessary for all 8 turn/flip directions, at least until sea monsters are found
	// tag::built_grid[]

	return result
}
// end::image_2[]


fun main(args: Array<String>) {

// tag::part_1[]
	var t1 = System.currentTimeMillis()
	var solution1 = image_2(read_puzzle_input_20(), 1)
	t1 = System.currentTimeMillis() - t1
	println()
	println("part 1 solved in $t1 ms -> $solution1")
// end::part_1[]

// tag::part_2[]
	var t2 = System.currentTimeMillis()
//	var solution2 = image_2(read_puzzle_input_20(), 2)
	t2 = System.currentTimeMillis() - t2
	println()
//	println("part 2 solved in $t2 ms -> $solution2")
// end::part_2[]

// tag::output[]
// print solution for part 1
	println("*******************************")
	println("--- Day 20: Jurassic Jigsaw ---")
	println("*******************************")
	println("Solution for part1")
	println("   You get $solution1 if you multiply together the IDs of the four corner tiles")
	println()
// print solution for part 2
	println("****************************")
	println("Solution for part2")
//	println("   $solution2 # are not part of a sea monster")
	println()
// end::output[]
}

data class Tile<out A, out B, out C, out D, out E>(
	val up: A,
	val left: B,
	val right: C,
	val down: D,
	val texture: E
)

// tag::read_puzzle_input[]
fun read_puzzle_input_20(): MutableMap<Int, Tile<String, String, String, String, String>> {
	var tiles = mutableMapOf<Int, Tile<String, String, String, String, String>>()

	var segment: Int = 0
	var tile_id: Int = 0
	var borderA: String = ""
	var borderB: String = ""
	var borderC: String = ""
	var borderD: String
	var texture: String = ""

	File("day2020_puzzle_input.txt").forEachLine {
		when (segment) {
			0 -> {
				tile_id = it.dropLast(1).drop(5).toInt()
			}
			1 -> {
				borderA = it
				borderB = borderB + it.take(1)
				borderC = borderC + it.takeLast(1)

			}
			in 2..9 -> {
				borderB = borderB + it.take(1)
				borderC = borderC + it.takeLast(1)
				texture = texture + it.drop(1).dropLast(1)
			}
			10 -> {
				borderD = it
				borderB = borderB + it.take(1)
				borderC = borderC + it.takeLast(1)
				tiles.put(tile_id, Tile(borderA, borderB, borderC, borderD, texture))
			}
			11 -> {
				borderB = ""
				borderC = ""
				texture = ""
			}
		}
		segment++
		segment = segment % 12
	}
	
	// bereits hier alle ausrichten?
	// erzeuge tile_new mit erstem input
	// while tile_new.size < 100
	//	tile_new.forEach
	//			tile.forEach???
	
	return tiles
}
// end::read_puzzle_input[]

// tag::rotate[]
fun rotate_tile(input: Tile<String, String, String, String, String>): Tile<String, String, String, String, String> {
	var rotate: String = ""

	for (j in 0..7) {
		for (i in 0..7) {
			rotate = rotate + input.texture.get((i + (i + 1) * 7) % 64 - j).toString()
		}
	}
	return Tile(input.right, input.up.reversed(), input.down.reversed(), input.left, rotate)
}
// rotate[]
