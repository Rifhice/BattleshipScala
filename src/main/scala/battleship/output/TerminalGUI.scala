package battleship.output

import scala.collection.mutable.ListBuffer
import battleship.model._
import scala.annotation.tailrec
case class TerminalGUI() extends GUI{
    def createBoatSelectionGrid(width: Int, height: Int, boats: List[Boat]): List[String] = {
        @tailrec
        def createGridAux(width: Int, height: Int, x: Int, y: Int, current: StringBuilder, list: ListBuffer[String]): List[String] = {
            var (newX, newY) = (x, y)
            if(!(x == width && y == height)){
                if(x == width){
                    newY = newY + 1
                    newX = 0
                    list += current.toString
                    current.delete(0 , current.size)
                    current += (newY + 48).toChar
                }
                val isABoatCell = boats.filter(boat => {
                    boat.isOn(newX, newY)
                }).size != 0
                if(isABoatCell){
                    current += 178.toChar
                    current += 178.toChar
                }
                else{
                    current += 176.toChar
                    current += 176.toChar
                }
                newX = newX + 1
                createGridAux(width, height, newX, newY, current, list)
            }
            else{
                list.toList
            }
        }
        val initialString = " 0 1 2 3 4 5 6 7 8 9"
        createGridAux(width, height, 0, 0, new StringBuilder("0"), ListBuffer[String](initialString))
    }

    private def createPlayerGrid(width: Int, height: Int, hitCells:List[Position], listBoat: List[Boat]): List[String] = {
        @tailrec
        def createGridAux(width: Int, height: Int, x: Int, y: Int, current: StringBuilder, list: ListBuffer[String]): List[String] = {
            var (newX, newY) = (x, y)
            if(!(x == width && y == height)){
                if(x == width){
                    newY = newY + 1
                    newX = 0
                    list += current.toString
                    current.delete(0 , current.size)
                    current += (newY + 48).toChar
                }
                val isABoatCell = listBoat.filter(boat => {
                    boat.isOn(newX, newY)
                }).size != 0
                val isAHitCell = hitCells.filter(cell => {
                    cell.x == newX && cell.y == newY
                }).size != 0
                if(isAHitCell){
                    current += 178.toChar
                    current += 178.toChar
                }
                else if(isABoatCell){
                    current += 177.toChar
                    current += 177.toChar
                }
                else{
                    current += 176.toChar
                    current += 176.toChar
                }
                newX = newX + 1
                createGridAux(width, height, newX, newY, current, list)
            }
            else{
                list.toList
            }
        }
        val initialString = " 0 1 2 3 4 5 6 7 8 9"
        createGridAux(width, height, 0, 0, new StringBuilder("0"), ListBuffer[String](initialString))
    }

    private def createPlayerShotGrid(width: Int, height: Int, shotCells:List[(Int, Position)]): List[String] = {
        @tailrec
        def createGridAux(width: Int, height: Int, x: Int, y: Int, current: StringBuilder, list: ListBuffer[String]): List[String] = {
            var (newX, newY) = (x, y)
            if(!(x == width && y == height)){
                if(x == width){
                    newY = newY + 1
                    newX = 0
                    list += current.toString
                    current.delete(0 , current.size)
                    current += (newY + 48).toChar
                }
                val shotCell = shotCells.filter(cell => {
                    cell._2.x == newX && cell._2.y == newY
                })
                if(shotCell.size == 1){
                    if(shotCell(0)._1 != 0){
                        current += 178.toChar
                        current += 178.toChar
                    }
                    else{
                        current += 177.toChar
                        current += 177.toChar
                    }
                }
                else{
                    current += 176.toChar
                    current += 176.toChar
                }
                newX = newX + 1
                createGridAux(width, height, newX, newY, current, list)
            }
            else{
                list.toList
            }
        }
        val initialString = "  0 1 2 3 4 5 6 7 8 9"
        createGridAux(width, height, 0, 0, new StringBuilder("0"), ListBuffer[String](initialString))
    }
    def hideDisplay() = {
        print("\033[1J")
        println("\033[17A")
    }

    def display(message: String) = {
        println(message)
    }

    def displayGrid(width: Int, height: Int, playerState: PlayerState) = {
        val gridPlayer = createPlayerGrid(width, height, playerState.hitPosition, playerState.boats)
        val gridShotPlayer = createPlayerShotGrid(width, height, playerState.lastShotResults)
        println("|     Your grid     |      |      Shot grid     |")
        (gridPlayer, gridShotPlayer).zipped.foreach((x,y) => println(x + "      " + y))
    }
}