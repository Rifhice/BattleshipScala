package battleship

import org.scalatest._
import battleship.model._

class GridTest extends FunSuite with DiagrammedAssertions {
    test("Is position in the grid : Negative x") {
        assert(Grid.isPositionInGrid(Position(-1, Grid.height / 2)) == false)
    }
    test("Is position in the grid : Both negative") {
        assert(Grid.isPositionInGrid(Position(-1, -1)) == false)
    }
    test("Is position in the grid : Negative y") {
        assert(Grid.isPositionInGrid(Position(Grid.width / 2, -1)) == false)
    }
    test("Is position in the grid : In the grid") {
        assert(Grid.isPositionInGrid(Position(Grid.width / 2, Grid.height / 2)))
    }
    test("Is position in the grid : Both out of the grid") {
        assert(Grid.isPositionInGrid(Position(Grid.width + 1, Grid.height + 1)) == false)
    }
    test("Is position in the grid : x out of the grid") {
        assert(Grid.isPositionInGrid(Position(Grid.width + 1, Grid.height / 2)) == false)
    }
    test("Is position in the grid : y out of the grid") {
        assert(Grid.isPositionInGrid(Position(Grid.width / 2, Grid.height + 1)) == false)
    }
  
    test("Would a boat fit in the grid : Fits in horizontal") {
        assert(Grid.doesBoatFitInGrid(Position(3,3), 3, true))
    }
    test("Would a boat fit in the grid : Fits in vertical") {
        assert(Grid.doesBoatFitInGrid(Position(3,3), 3, false))
    }
    test("Would a boat fit in the grid : Doesn't fit in horizontal") {
        assert(Grid.doesBoatFitInGrid(Position(8,3), 3, true) == false)
    }
     test("Would a boat fit in the grid : Doesn't fit in vertical") {
        assert(Grid.doesBoatFitInGrid(Position(3,8), 3, false) == false)
    }
}