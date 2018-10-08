package battleship.model

import scala.annotation.tailrec

/** A boat.
 *
 *  @constructor create a new boat with a name, if it is horizontal and a list of positions.
 *  @param name the boat's name
 *  @param isHorizontal if the boat is horizontal
 *  @param positions the list of positions
 */
class Boat(val name: String, val isHorizontal: Boolean, val positions: List[Position]){

    /** Check if the boat has a position on the given x and y
   *
   *  @param x x value 
   *  @param y y value
   *  @return a boolean, true if the boat has a position on the x and y given.
   */
    def isOn(x :Int, y :Int) : Boolean = positions.filter(pos => pos.x == x && pos.y == y).size != 0
    
    /** Check if the boat has all of it's positions hit and therefore is dead.
   *  @return a boolean, true if the boat has all it's positions hit.
   */
    def isDead(): Boolean = positions.filter(position => position.isHit).length == positions.length

    /** Check if the boat collides with a position
   *
   *  @param position the position to check the assertion with
   *  @return a boolean, true if the boat has a position colliding with the given position.
   */
    def collide(position: Position) : Boolean = positions.filter(pos => pos.x == position.x && pos.y == position.y).size != 0
}    
object Boat{
  /** Creates a boat with a given name, if it is horizontal and a list of positions.
   *
   *  @param name the boat's name
   *  @param isHorizontal if the boat is horizontal
   *  @param positions the list of positions
   *  @return a new Boat instance.
   */
    def apply(name:String, isHorizontal: Boolean, positions: List[Position]) = new Boat(name, isHorizontal, positions)


  /** Creates a boat with a given name, starting position, the size and it's orientation.
   *
   *  @param name the boat's name
   *  @param startingPosition initial position of the boat
   *  @param size size of the boat
   *  @param isHorizontal if the boat is horizontal
   *  @return a new Boat instance.
   */
    def createBoat(name: String, startingPosition: Position, size: Int, isHorizontal: Boolean): Boat = {
        var positions = List[Position](Position(startingPosition.x, startingPosition.y))
        @tailrec
        def createBoatAux(lastposition: Position, size: Int, isHorizontal: Boolean, positions: List[Position]): Boat = {
            if(size == 0){
                Boat(name, isHorizontal, positions.toList)
            }
            else{
                if(isHorizontal) {
                    val newPos = Position(lastposition.x + 1, lastposition.y)
                    createBoatAux(newPos, size - 1, isHorizontal, positions :+ newPos) 
                }
                else {
                    val newPos = Position(lastposition.x, lastposition.y + 1)
                    createBoatAux(newPos, size - 1, isHorizontal, positions :+ newPos) 
                }
            }
        }
        createBoatAux(startingPosition, size - 1, isHorizontal, positions)
    }
}