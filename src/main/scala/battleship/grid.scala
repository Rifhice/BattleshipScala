package battleship
object Grid{
    val width = 10
    val height = 10

    def isPositionInGrid(position: Position) : Boolean = position.x >= 0 && position.x <= width && position.y >= 0 && position.y <= height
    def doesBoatFitInGrid(position: Position, size: Int, isHorizontal: Boolean) = if(!isPositionInGrid(position)) false else if(isHorizontal) position.x + size <= width else position.y + size <= height
}