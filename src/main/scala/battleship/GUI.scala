package battleship

import scala.collection.mutable.ListBuffer
object UI {
    def createBoatSelectionGrid(width: Int, height: Int, boats: List[Boat]): List[String] = {
        def createGridAux(width: Int, height: Int, x: Int, y: Int, current: StringBuilder, list: ListBuffer[String]): List[String] = {
            var (newX, newY) = (x, y)
            if(!(x == width && y == height)){
                if(x == width){
                    newY = newY + 1
                    newX = 0
                    list += current.toString
                    current.delete(0 , current.size)
                    current += (newY + 65).toChar
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
        createGridAux(width, height, 0, 0, new StringBuilder("A"), ListBuffer[String](initialString))
    }

    private def createPlayerGrid(width: Int, height: Int, hitCells:List[Position], listBoat: List[Boat]): List[String] = {
        def createGridAux(width: Int, height: Int, x: Int, y: Int, current: StringBuilder, list: ListBuffer[String]): List[String] = {
            var (newX, newY) = (x, y)
            if(!(x == width && y == height)){
                if(x == width - 1){
                    newY = newY + 1
                    newX = 0
                    list += current.toString
                    current.delete(0 , current.size)
                    current += (newY + 65).toChar
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
        createGridAux(width, height, 0, 0, new StringBuilder("A"), ListBuffer[String](initialString))
    }

    private def createPlayerShotGrid(width: Int, height: Int, shotCells:List[Position], hitCells: List[Position]): List[String] = {
        def createGridAux(width: Int, height: Int, x: Int, y: Int, current: StringBuilder, list: ListBuffer[String]): List[String] = {
            var (newX, newY) = (x, y)
            if(!(x == width && y == height)){
                if(x == width){
                    newY = newY + 1
                    newX = 0
                    list += current.toString
                    current.delete(0 , current.size)
                    current += (newY + 65).toChar
                }
                val isAShotCell = shotCells.filter(cell => {
                    cell.x == newX && cell.y == newY
                }).size != 0
                val isAHitCell = hitCells.filter(cell => {
                    cell.x == newX && cell.y == newY
                }).size != 0
                if(isAHitCell){
                    current += 178.toChar
                    current += 178.toChar
                }
                else if(isAShotCell){
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
        createGridAux(width, height, 0, 0, new StringBuilder("A"), ListBuffer[String](initialString))
    }

    def displayGrid(width: Int, height: Int, playerState: PlayerState) = {
        print("\033[1J")
        print("\033[15A")
        System.out.flush
        val gridPlayer = createPlayerGrid(width, height, playerState.hitPosition, playerState.boats)
        val gridShotPlayer = createPlayerShotGrid(width, height, playerState.shotPosition, playerState.hitShotPosition)
        println("|     Your grid     |      |      Shot grid     |")
        (gridPlayer, gridShotPlayer).zipped.foreach((x,y) => println(x + "      " + y))
    }
}