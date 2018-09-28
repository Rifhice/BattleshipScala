package battleship

import scala.util.Random

case class PlayerState(boats: List[Boat], hitPosition: List[Position], shotPosition: List[Position], hitShotPosition: List[Position])

class Player(name: String, isHuman: Boolean, input: (Random) => Position, playerState: PlayerState){
    def shoot(position: Position, player: Player): Boolean = {false}

    object Player{
        def apply(name: String, isHuman:Boolean, input: (Random) => Position, boats: List[Boat]) = new Player(name, isHuman, input, PlayerState(boats, List[Position](), List[Position](), List[Position]()))
    }
}