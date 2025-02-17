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
		if (sum == 2) {
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

    // ***************************************
    //               part 2
    //****************************************

    // what is the size of the grid?
    var gridSize = sqrt(tiles.size.toDouble()).toInt()

    // starting with adusting the first corner:
    var current_tile = tiles.getValue(first_corner)

    // turn/flip first_corner until left and up boarder contain '_'  -> do I have flip / do I need a flip???
    var j = 0
	while ((!current_tile.up.contains('_') || !current_tile.left.contains('_')) && j < 7) {
		current_tile = rotate_tile(current_tile)
        j += 1
	}

    // find next tiles (first row) and already build up picture
	var tilesLeft = mutableMapOf<Int, Tile<String, String, String, String, String>>()
	tiles.forEach {
		tilesLeft.put(it.key, it.value)
	}
	tilesLeft.remove(first_corner)

    var picture = mutableListOf<String>()
	current_tile.texture.chunked(8).forEach {
		picture.add(it)
	}

	// assemble first row 
	var Id2Search = current_tile.right

	for (i in 0.. gridSize-2) {
		// look which of the remaining tiles fits
		var matchID = 0
		var matchFound = false
		tilesLeft.forEach {
			//matchID = it.key
			var tile2compare = it.value
			if (tile2compare.left == Id2Search && !matchFound) {
                Id2Search = tile2compare.right
				matchID = it.key
				matchFound = true
				var j = 0
				tile2compare.texture.chunked(8) {
					picture[j] = picture[j] + it
					j += 1
				}
			}
			tile2compare = rotate_tile(tile2compare)
			if (tile2compare.left == Id2Search && !matchFound) {
                Id2Search = tile2compare.right
				matchID = it.key
				matchFound = true
				var j = 0
				tile2compare.texture.chunked(8) {
					picture[j] = picture[j] + it
					j += 1
				}
			}	
			tile2compare = rotate_tile(tile2compare)
			if (tile2compare.left == Id2Search && !matchFound) {
                Id2Search = tile2compare.right
				matchID = it.key
				matchFound = true
				var j = 0
				tile2compare.texture.chunked(8) {
					picture[j] = picture[j] + it
					j += 1
				}
			}
			tile2compare = rotate_tile(tile2compare)
			if (tile2compare.left == Id2Search && !matchFound) {
                Id2Search = tile2compare.right
				matchID = it.key
				matchFound = true
				var j = 0
				tile2compare.texture.chunked(8) {
					picture[j] = picture[j] + it
					j += 1
				}
			}
			tile2compare = flip_tile(tile2compare)
			if (tile2compare.left == Id2Search && !matchFound) {
				Id2Search = tile2compare.right
				matchID = it.key
				matchFound = true
				var j = 0
				tile2compare.texture.chunked(8) {
					picture[j] = picture[j] + it
					j += 1
				}	
			}
			tile2compare = rotate_tile(tile2compare)
			if (tile2compare.left == Id2Search && !matchFound) {
                Id2Search = tile2compare.right
				matchID = it.key
				matchFound = true
				var j = 0
				tile2compare.texture.chunked(8) {
					picture[j] = picture[j] + it
					j += 1
				}
			}
			tile2compare = rotate_tile(tile2compare)
			if (tile2compare.left == Id2Search && !matchFound) {
                Id2Search = tile2compare.right
				matchID = it.key
				matchFound = true
				var j = 0
				tile2compare.texture.chunked(8) {
					picture[j] = picture[j] + it
					j += 1
				}	
			}
			tile2compare = rotate_tile(tile2compare)
			if (tile2compare.left == Id2Search && !matchFound) {
				Id2Search = tile2compare.right
				matchID = it.key
				matchFound = true
				var j = 0
				tile2compare.texture.chunked(8) {
					picture[j] = picture[j] + it
					j += 1
				}
			}		
		}
		tilesLeft.remove(matchID)
	}

	// so, jetzt alles noch für gridSize - 1 Reihen, erstes Glied nach oben orientieren, weitere nach Links
	var Id2SearchFirstRow = current_tile.down

	for (i in 1..gridSize-1) {
		var matchID = 0
		var matchFound = false
		// find first and update Id2SearchFirstRow
		tilesLeft.forEach {
			//matchID = it.key
			var tile2compare = it.value
			if (tile2compare.up == Id2SearchFirstRow && !matchFound) {
                Id2Search = tile2compare.right
				Id2SearchFirstRow = tile2compare.down
				matchID = it.key
				matchFound = true
				tile2compare.texture.chunked(8) {
					picture.add(it.toString())
				}	
			}
			tile2compare = rotate_tile(tile2compare)
			if (tile2compare.up == Id2SearchFirstRow && !matchFound) {
                Id2Search = tile2compare.right
				Id2SearchFirstRow = tile2compare.down
				matchID = it.key
				matchFound = true
				tile2compare.texture.chunked(8) {
					picture.add(it.toString())
				}	
			}	
			tile2compare = rotate_tile(tile2compare)
			if (tile2compare.up == Id2SearchFirstRow && !matchFound) {
				Id2Search = tile2compare.right
				Id2SearchFirstRow = tile2compare.down
				matchID = it.key
				matchFound = true
				tile2compare.texture.chunked(8) {
					picture.add(it.toString())
				}	
			}
			tile2compare = rotate_tile(tile2compare)
			if (tile2compare.up == Id2SearchFirstRow && !matchFound) {
                Id2Search = tile2compare.right
				Id2SearchFirstRow = tile2compare.down
				matchID = it.key
				matchFound = true
				tile2compare.texture.chunked(8) {
					picture.add(it.toString())
				}	
			}
			tile2compare = flip_tile(tile2compare)
			if (tile2compare.up == Id2SearchFirstRow && !matchFound) {
				Id2Search = tile2compare.right
				Id2SearchFirstRow = tile2compare.down
				matchID = it.key
				matchFound = true
				tile2compare.texture.chunked(8) {
					picture.add(it.toString())
				}	
			}
			tile2compare = rotate_tile(tile2compare)
			if (tile2compare.up == Id2SearchFirstRow && !matchFound) {
                Id2Search = tile2compare.right
				Id2SearchFirstRow = tile2compare.down
				matchID = it.key
				matchFound = true
				tile2compare.texture.chunked(8) {
					picture.add(it.toString())
				}	
			}
			tile2compare = rotate_tile(tile2compare)
			if (tile2compare.up == Id2SearchFirstRow && !matchFound) {
				Id2Search = tile2compare.right
				Id2SearchFirstRow = tile2compare.down
				matchID = it.key
				matchFound = true
				tile2compare.texture.chunked(8) {
					picture.add(it.toString())
				}
			}
			tile2compare = rotate_tile(tile2compare)
			if (tile2compare.up == Id2SearchFirstRow && !matchFound) {
				Id2Search = tile2compare.right
				Id2SearchFirstRow = tile2compare.down
				matchID = it.key
				matchFound = true
				tile2compare.texture.chunked(8) {
					picture.add(it.toString())
				}
			}		
		}
		tilesLeft.remove(matchID)

		// moving to the left, insert loop for left -> 1 to grid size-2
		for (k in 1.. gridSize-1) {

		// look which of the remaining tiles fits
		var matchID = 0
		var matchFound = false
		tilesLeft.forEach {
			//matchID = it.key
			var tile2compare = it.value
			if (tile2compare.left == Id2Search && !matchFound) {
                Id2Search = tile2compare.right
				matchID = it.key
				matchFound = true
				var j = 0
				tile2compare.texture.chunked(8) {
					picture[j+i*8] = picture[j+i*8] + it
					j += 1
				}
			}
			tile2compare = rotate_tile(tile2compare)
			if (tile2compare.left == Id2Search && !matchFound) {
                Id2Search = tile2compare.right
				matchID = it.key
				matchFound = true
				var j = 0
				tile2compare.texture.chunked(8) {
					picture[j+i*8] = picture[j+i*8] + it
					j += 1
				}
			}	
			tile2compare = rotate_tile(tile2compare)
			if (tile2compare.left == Id2Search && !matchFound) {
                Id2Search = tile2compare.right
				matchID = it.key
				matchFound = true
				var j = 0
				tile2compare.texture.chunked(8) {
					picture[j+i*8] = picture[j+i*8] + it
					j += 1
				}
			}
			tile2compare = rotate_tile(tile2compare)
			if (tile2compare.left == Id2Search && !matchFound) {
                Id2Search = tile2compare.right
				matchID = it.key
				matchFound = true
				var j = 0
				tile2compare.texture.chunked(8) {
					picture[j+i*8] = picture[j+i*8] + it
					j += 1
				}
			}
			tile2compare = flip_tile(tile2compare)
			if (tile2compare.left == Id2Search && !matchFound) {
				Id2Search = tile2compare.right
				matchID = it.key
				matchFound = true
				var j = 0
				tile2compare.texture.chunked(8) {
					picture[j+i*8] = picture[j+i*8] + it
					j += 1
				}	
			}
			tile2compare = rotate_tile(tile2compare)
			if (tile2compare.left == Id2Search && !matchFound) {
                Id2Search = tile2compare.right
				matchID = it.key
				matchFound = true
				var j = 0
				tile2compare.texture.chunked(8) {
					picture[j+i*8] = picture[j+i*8] + it
					j += 1
				}
			}
			tile2compare = rotate_tile(tile2compare)
			if (tile2compare.left == Id2Search && !matchFound) {
				Id2Search = tile2compare.right
				matchID = it.key
				matchFound = true
				var j = 0
				tile2compare.texture.chunked(8) {
					picture[j+i*8] = picture[j + i*8] + it
					j += 1
				}
			}
			tile2compare = rotate_tile(tile2compare)
			if (tile2compare.left == Id2Search && !matchFound) {
				Id2Search = tile2compare.right
				matchID = it.key
				matchFound = true
				var j = 0
				tile2compare.texture.chunked(8) {
					picture[j+i*8] = picture[j + i*8] + it
					j += 1
				}
			}		
		}
		tilesLeft.remove(matchID)	
	    }		
	}

	// now check for seemonster with flips and rots....
    var picture2test = picture.joinToString("")

    gridSize = sqrt(picture2test.length.toDouble()).toInt()

    var monstersAlt = 0

    for (m in 0..7) {
        when (m) {
            0 -> picture2test = picture2test
            1,2,3 -> picture2test = rotate_picture(picture2test)
            4 -> picture2test = flip_picture(picture2test)
            5,6,7 -> picture2test = rotate_picture(picture2test)
        }
        
        var numbersAlt = 0
        for (y in 1..gridSize-2) {
            for (x in 0..gridSize-1-20) {
                if (picture2test[x+gridSize*y] == '#' && picture2test[x+5+gridSize*y] == '#' && picture2test[x+6+gridSize*y] == '#') {
                    if (picture2test[x+11+gridSize*y] == '#' && picture2test[x+12+gridSize*y] == '#' && picture2test[x+17+gridSize*y] == '#') {
                        if (picture2test[x+18+gridSize*y] == '#' && picture2test[x+19+gridSize*y] == '#') {
                            if (picture2test[x+18+gridSize*(y-1)] == '#' && picture2test[x+1+gridSize*(y+1)] == '#' && picture2test[x+4+gridSize*(y+1)] == '#') {
                                if (picture2test[x+7+gridSize*(y+1)] == '#' && picture2test[x+10+gridSize*(y+1)] == '#') {
                                    if (picture2test[x+13+gridSize*(y+1)] == '#' && picture2test[x+16+gridSize*(y+1)] == '#') {
                                        numbersAlt += 1
                                    }   
                                }
                                
                            }
                        }
                    }
                } 
            }
        }
        if (numbersAlt > 0) monstersAlt = numbersAlt
    }
    val numbers = picture2test.count {it == '#'}

	return (numbers - monstersAlt * 15).toLong()
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
	var solution2 = image_2(read_puzzle_input_20(), 2)
	t2 = System.currentTimeMillis() - t2
	println("part 2 solved in $t2 ms -> $solution2")
    println()
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
	println("   $solution2 # are not part of a sea monster") 
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

fun flip_tile(input: Tile<String, String, String, String, String>): Tile<String, String, String, String, String> {
	var flip: String = ""
	input.texture.chunked(8).forEach {
        flip += it.reversed()
    }
	return Tile(input.up.reversed(), input.right, input.left, input.down.reversed(), flip)
}

// tag::rotate[]
fun rotate_picture(in1: String): String{
	var rotate: String = ""

    var gridSize = sqrt(in1.length.toDouble()).toInt()
	for (j in 0..gridSize-1) {
		for (i in 0..gridSize-1) {
			rotate = rotate + in1.get((i + (i + 1) * (gridSize-1)) % (in1.length) - j).toString()
		}
	}
	return rotate
}
// rotate[]

fun flip_picture(in1: String): String {
	var flip: String = ""
    var gridSize = sqrt(in1.length.toDouble()).toInt()
	in1.chunked(gridSize).forEach {
        flip += it.reversed()
    }
	return flip
}
