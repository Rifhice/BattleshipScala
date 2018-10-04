package battleship

import org.scalatest._
import battleship.model._

class BoatTest extends FunSuite with DiagrammedAssertions {

    val boat = Boat("TestBoat", true, List[Position](Position(5,5), Position(6,5)))

    test("Is position on the boat : Position on the boat") {
        assert(boat.isOn(5,5))
    }
    test("Is position on the boat : Position not on the boat") {
        assert(!boat.isOn(4,5))
    }
    test("Does boat collide : should collide") {
        assert(boat.collide(Position(5,5)))
    }
    test("Does boat collide : should not collide") {
        assert(!boat.collide(Position(4,5)))
    }
    test("Is boat dead : is not dead") {
        assert(!boat.isDead)
    }
    test("Is boat dead : is dead") {
        boat.positions.map(position => position.isHit = true)
        assert(boat.isDead)
    }
    test("Create a boat : should be equal to the initial boat") {
        val newBoat = Boat.createBoat("TestBoat", Position(5,5), 2, true)
        val positionComparison = (boat.positions.length == newBoat.positions.length  && boat.positions.length == (boat.positions, newBoat.positions).zipped.filter((x,y) => x.x == y.x && x.y == y.y)._1.length)
        assert(boat.name == newBoat.name && boat.isHorizontal == newBoat.isHorizontal && positionComparison )
    }
}