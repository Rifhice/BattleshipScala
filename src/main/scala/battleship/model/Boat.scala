package battleship.model

import scala.annotation.tailrec

class Boat(val name: String, val isHorizontal: Boolean, val positions: List[Position]){
    def isOn(x :Int, y :Int) : Boolean = positions.filter(pos => pos.x == x && pos.y == y).size != 0
    //def collide(boat: Boat): Boolean = boat.positions.filter
    def isDead(): Boolean = positions.filter(position => position.isHit).length == positions.length
    def collide(position: Position) : Boolean = positions.filter(pos => pos.x == position.x && pos.y == position.y).size != 0
}    
object Boat{

    def apply(name:String, isHorizontal: Boolean, positions: List[Position]) = new Boat(name, isHorizontal, positions)

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