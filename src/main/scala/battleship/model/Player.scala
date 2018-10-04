package battleship.model

import scala.util.Random

//ShotPosition is where the player got shot
case class PlayerState(boats: List[Boat], hitPosition: List[Position], lastShotResults: List[(Int, Position)])

class Player(val name: String, val isHuman: Boolean, val input: (Random, PlayerState) => Position, val playerState: PlayerState){
    def shoot(position: Position, player: Player): (Player, Player, Int, Boat) = {
        var result = 0
        val hittedBoat = player.playerState.boats.filter(boat => boat.collide(position))
        val newOpponent = new Player(player.name, player.isHuman, player.input, PlayerState(player.playerState.boats, player.playerState.hitPosition :+ position, player.playerState.lastShotResults))
        if(hittedBoat.length != 0){
            hittedBoat(0).positions.foreach(pos => if(pos.x == position.x && pos.y == position.y) pos.isHit = true)
            if(hittedBoat(0).isDead) result = 2 else result = 1
            val newPlayer = new Player(name, isHuman, input, PlayerState(playerState.boats, playerState.hitPosition, playerState.lastShotResults :+ (result,position)))
            (newPlayer, newOpponent, result, hittedBoat(0))
        }
        else{
            (new Player(name, isHuman, input, PlayerState(playerState.boats, playerState.hitPosition, playerState.lastShotResults :+ (result,position))), newOpponent, result, null)
        }
    }
    def isDead(): Boolean = playerState.boats.filter(boat => boat.isDead).length == playerState.boats.length
}    
object Player{
    def apply(name: String, isHuman:Boolean, input: (Random, PlayerState) => Position, boats: List[Boat]) = new Player(name, isHuman, input, PlayerState(boats, List[Position](), List[(Int,Position)]()))
}