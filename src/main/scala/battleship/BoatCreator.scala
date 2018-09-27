package battleship
import scala.collection.mutable.ListBuffer
object BoatCreator{
    def getStartingBoatPosition(size: Int, isHorizontal: Boolean): Position = {
        var tuple = Input.getIntTuple()
        var position = Position(tuple._1, tuple._2)
        if(Grid.isPositionInGrid(position)){
            if(Grid.doesBoatFitInGrid(position, size, isHorizontal)){
                position
            }
            else{
                getStartingBoatPosition(size, isHorizontal)
            }
        }
        else{
            getStartingBoatPosition(size, isHorizontal)
        }
    }
    def createBoat(startingPosition: Position, size: Int, isHorizontal: Boolean){
        var positions = ListBuffer[Position]()
        if(isHorizontal){
            for (i <- 0 to size) positions += Position(startingPosition.x + i, startingPosition.y)
        }
        else{
            for (i <- 0 to size) positions += Position(startingPosition.x, startingPosition.y + i)
        }
        Boat(isHorizontal, positions.toList)
    }
}