package battleship

class Boat(val isHorizontal: Boolean, val positions: List[Position]){
    def isOn(x :Int, y :Int) : Boolean = positions.filter(pos => pos.x == x && pos.y == y).size != 0
    //def collide(boat: Boat): Boolean = boat.positions.filter
    def isDead(): Boolean = positions.filter(position => position.isHit).length == positions.length
    def collide(position: Position) : Boolean = positions.filter(pos => pos.x == position.x && pos.y == position.y).size != 0
}    
object Boat{
    def apply(isHorizontal: Boolean, positions: List[Position]) = new Boat(isHorizontal, positions)
}