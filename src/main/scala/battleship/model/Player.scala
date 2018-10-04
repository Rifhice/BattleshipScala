package battleship.model

import scala.util.Random

case class PlayerState(boats: List[Boat], hitPosition: List[Position], shotPosition: List[Position], hitShotPosition: List[Position])

class Player(val name: String, val isHuman: Boolean, val input: (Random) => Position, val playerState: PlayerState){
    def shoot(position: Position, player: Player): (Player, Player, Int, Boat) = {
        val hittedBoat = player.playerState.boats.filter(boat => boat.collide(position))
        val newOpponent = new Player(player.name, player.isHuman, player.input, PlayerState(player.playerState.boats, player.playerState.hitPosition :+ position, player.playerState.shotPosition, player.playerState.hitShotPosition))
        if(hittedBoat.length != 0){
            hittedBoat(0).positions.foreach(pos => if(pos.x == position.x && pos.y == position.y) pos.isHit = true)
            val newPlayer = new Player(name, isHuman, input, PlayerState(playerState.boats, playerState.hitPosition, playerState.shotPosition, playerState.hitShotPosition  :+ position))
            if(hittedBoat(0).isDead) (newPlayer, newOpponent, 2, hittedBoat(0)) else (newPlayer, newOpponent, 1, hittedBoat(0))
        }
        else{
            (new Player(name, isHuman, input, PlayerState(playerState.boats, playerState.hitPosition, playerState.shotPosition :+ position, playerState.hitShotPosition)), newOpponent, 0, null)
        }
    }
    def isDead(): Boolean = playerState.boats.filter(boat => boat.isDead).length == playerState.boats.length
}    
object Player{
    def apply(name: String, isHuman:Boolean, input: (Random) => Position, boats: List[Boat]) = new Player(name, isHuman, input, PlayerState(boats, List[Position](), List[Position](), List[Position]()))
}