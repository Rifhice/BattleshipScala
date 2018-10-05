package battleship

import org.scalatest._
import battleship.model._
import battleship._
import scala.util.Random

class AiAlgorithmTest extends FunSuite with DiagrammedAssertions {

    val player = Player("Player 1", true, (random: Random, playerState:PlayerState) => Position(2,2), List[Boat](Boat("TestBoat", true, List[Position](Position(5,5), Position(6,5)))))
    val boats:List[(String, Int)] = List[(String,Int)](("Destroyer", 2), ("Submarine", 3), ("Cruiser", 3), ("Battleship", 4), ("Carrier", 5))

    test("Place boats : is number of boat correct") {
        assert(AiAlgorithm.aiBoatPlacing(Random, boats).length == boats.length)
    }
    test("Get last non miss shot : should return none") {
        val shots:List[(Int, Position)] = List[(Int, Position)]((0,Position(0,0)),(1,Position(0,0)),(2,Position(0,0)),(0,Position(0,0)))
        assert(!AiAlgorithm.getLastNonMissShot(shots).isEmpty)
    }
    test("Get last non miss shot : should return a shot") {
        val shots:List[(Int, Position)] = List[(Int, Position)]((0,Position(0,0)))
        assert(AiAlgorithm.getLastNonMissShot(shots).isEmpty)
    }
    test("Get last non miss shot : empty list") {
        val shots:List[(Int, Position)] = List[(Int, Position)]()
        assert(AiAlgorithm.getLastNonMissShot(shots).isEmpty)
    }
}