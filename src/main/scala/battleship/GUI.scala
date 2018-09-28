package battleship

import scala.collection.mutable.ListBuffer
object UI {
    private def createPlayerGrid(width: Int, height: Int, hitCells:List[Position], listBoat: List[Boat]): List[String] = {
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
                val isABoatCell = listBoat.filter(boat => {
                    boat.isOn(x, y)
                }).size != 0
                val isAHitCell = hitCells.filter(cell => {
                    cell.x == x && cell.y == y
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
                    cell.x == x && cell.y == y
                }).size != 0
                val isAHitCell = hitCells.filter(cell => {
                    cell.x == x && cell.y == y
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
        val gridPlayer = createPlayerGrid(width, height, playerState.hitPosition, playerState.boats)
        val gridShotPlayer = createPlayerShotGrid(width, height, playerState.shotPosition, playerState.hitShotPosition)
        println("|     Your grid     |      |      Shot grid     |")
        (gridPlayer, gridShotPlayer).zipped.foreach((x,y) => println(x + "      " + y))
    }

    def select(options: List[String]): Int = {
        println("Please select :")
        options.indices.foreach(i => println("[" + (i+1) + "]" + " " + options(i)))
        System.out.flush
        try{
            val x = scala.io.StdIn.readInt()
            if(x > options.size || x < 1){
                select(options)
            }
            else{
                x
            }
        }
        catch{
            case err: NumberFormatException => select(options)
        }
    }
}

object GuiProgramOne extends App{
    val gamemode = UI.select(List[String]("Human vs Human","Human vs Ai","Ai vs Ai (not playable)"))
    gamemode match{
        case 1 => println("H v H") //Create two  player with prompt as input
        case 2 => {
            val AiDifficulty = UI.select(List[String]("easy","medium","hard"))
            println(AiDifficulty) // Create a human player with prompt as input and an AI with the appopriate level as input
        }
        case 3 => println("AI v AI")
    }
    val boats = List[Boat](Boat(true, List(Position(5,5), Position(5,6))))
    val hitPosition = List[Position](Position(2,3), Position(5,5))
    val shotPosition = List[Position](Position(5,5),Position(6,3))
    val hitShotPosition = List[Position](Position(1,5),Position(2,3))
    val playerState = PlayerState(boats, hitPosition, shotPosition ,hitShotPosition)
    UI.displayGrid(10, 10, playerState)
}