package battleship.model
/** A grid is a virtual representation of a playing grid.*/
object Grid{

    /** Width defines the width size of the grid.*/
    val width = 10
    /** Height defines the height size of the grid.*/
    val height = 10

    /** Check if the given position is within the grid i.e > 0 and < width && height
   *
   *  @param position to position to check the assertion against
   *  @return a boolean, true if the position is within the grid
   */
    def isPositionInGrid(position: Position) : Boolean = position.x >= 0 && position.x <= width && position.y >= 0 && position.y <= height
    
    /** Check if an hypothetical boat would fit in the grid i.e the starting position and end position are > 0 and < width && height
   *  @param position the starting position of the boat
   *  @param size the size of the boat
   *  @param isHorizontal whether the boat is horizontal or not. 
   *  @return a boolean, true if the boat would fit in the grid
   */
    def doesBoatFitInGrid(position: Position, size: Int, isHorizontal: Boolean) = if(!isPositionInGrid(position)) false else if(isHorizontal) position.x + size <= width else position.y + size <= height
}