package battleship

import scala.util.Random

case class PlayerState(boats: List[Boat], hitPosition: List[Position], shotPosition: List[Position], hitShotPosition: List[Position])

class Player(val name: String, val isHuman: Boolean,val input: (Random) => Position,val playerState: PlayerState){
    def shoot(position: Position, player: Player): Int = {
        val hittedBoat = player.playerState.boats.filter(boat => boat.collide(position))
        if(hittedBoat.length != 0){
            hittedBoat(0).positions.foreach(pos => if(pos.x == position.x && pos.y == position.y) pos.isHit = true)
            if(hittedBoat(0).isDead) 2 else 1
        }
        else 0
    }
    def isDead(): Boolean = playerState.boats.filter(boat => boat.isDead).length == playerState.boats.length
}    
object Player{
    def apply(name: String, isHuman:Boolean, input: (Random) => Position, boats: List[Boat]) = new Player(name, isHuman, input, PlayerState(boats, List[Position](), List[Position](), List[Position]()))
}