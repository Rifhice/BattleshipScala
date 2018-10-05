package battleship

import org.scalatest._
import battleship.model._
import scala.util.Random

class PlayerTest extends FunSuite with DiagrammedAssertions {

    val player = Player("Player 1", true, (random: Random, playerState:PlayerState) => Position(2,2), List[Boat](Boat("TestBoat", true, List[Position](Position(5,5), Position(6,5)))))

    test("Is player dead : Player should not be dead") {
        assert(!player.isDead)
    }
    test("Is player dead : Player should be dead") {
        player.playerState.boats.foreach(boat => boat.positions.map(position => position.isHit = true))
        assert(player.isDead)
    }
}