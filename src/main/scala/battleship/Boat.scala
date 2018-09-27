package battleship

class Boat(isHorizontal: Boolean, position: List[Position]){
    def isOn(x :Int, y :Int) : Boolean = position.filter(pos => pos.x == x && pos.y == y).size != 0
}    
object Boat{
    def apply(isHorizontal: Boolean, position: List[Position]) = new Boat(isHorizontal, position)
}