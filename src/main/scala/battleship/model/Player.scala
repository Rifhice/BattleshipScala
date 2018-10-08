package battleship.model

import scala.util.Random

/** A playerstate represent the state of a player during the game, it hold the list of boats, the list of position the player
 * got shot at and the list of position the player shot and the result of those shots i.e 0 = miss, 1 = hit, 2 = sink.
 *
 *  @constructor create a new playerstate with a list of boats, a list of hit position and a list of shots.
 *  @param boats the boat list
 *  @param hitPosition where the player got shot
 *  @param lastShotResults the player's shot
 */
case class PlayerState(boats: List[Boat], hitPosition: List[Position], lastShotResults: List[(Int, Position)])

/** A player during the game is defined by it's name, if he is human and a function taking a random and a playerstate as arguments
 * and returning a position. This function is used to get the player shot depending on the state and a playerstate.
 *
 *  @constructor create a new player with a name, a boolean is human, a function and a playerstate.
 *  @param name the player's name
 *  @param isHuman is the player is human
 *  @param input a function taking a random and a playerstate as arguments and returning a position
 *  @param playerState the player's state
 */
class Player(val name: String, val isHuman: Boolean, val input: (Random, PlayerState) => Position, val playerState: PlayerState){
    
    /** Shoots the player given in parameter at the given position. It return a 2 new player with their state
   * updated, an int for the result of the shot i.e 0 = miss, 1 = hit, 2 = sink and the boat hit if there is any
   *
   *  @param position the position to shoot
   *  @param player the player to shoot
   *  @return Two new player with their state updated, the result of the shot (i.e 0 = miss, 1 = hit, 2 = sink) and the boat hit if there is any.
   */
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

    /** Check if the player has all of it's boats dead and therefore is dead.
   *  @return a boolean, true if the player has all it's boats dead.
   */
    def isDead(): Boolean = playerState.boats.filter(boat => boat.isDead).length == playerState.boats.length
}    
object Player{

    /** Creates a player with a given name, if it is human, an input function and a list of boat.
   *
   *  @param name the player's name
   *  @param isHuman is the player is human
   *  @param input a function taking a random and a playerstate as arguments and returning a position
   *  @param boats the list of boats
   *  @return a new Player instance with the given parameters and an initial playerstate.
   */
    def apply(name: String, isHuman:Boolean, input: (Random, PlayerState) => Position, boats: List[Boat]) = new Player(name, isHuman, input, PlayerState(boats, List[Position](), List[(Int,Position)]()))
}