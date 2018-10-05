package battleship
import battleship.input._
import battleship.model._
import scala.util.Random
import scala.annotation.tailrec
object AiAlgorithm{
    
    def aiBoatPlacing(random: Random, boatSize: List[(String, Int)]) : List[Boat] = {
        @tailrec
        def aiBoatPlacingAux(random: Random, boatSize: List[(String, Int)], boats: List[Boat]) : List[Boat] = {
            if(boatSize.size == 0){
                boats
            }
            else{
                val isHorizontal = random.nextInt(2) == 1
                @tailrec
                def generateStartingPosition(random:Random, size: Int, isHorizontal: Boolean): Position = {
                    var position = Position(random.nextInt(Grid.width), random.nextInt(Grid.height), false)
                    if(Grid.isPositionInGrid(position)){
                        if(Grid.doesBoatFitInGrid(position, size, isHorizontal)){
                            position
                        }
                        else{
                            generateStartingPosition(random, size, isHorizontal)
                        }
                    }
                    else{
                        generateStartingPosition(random, size, isHorizontal)
                    }
                }
                var position = generateStartingPosition(random, boatSize.head._2, isHorizontal)
                val newBoat = Boat.createBoat(boatSize.head._1, position, boatSize.head._2, isHorizontal)
                if(boats.filter(boat => newBoat.positions.filter(position => boat.collide(position)).length != 0).length != 0){
                    aiBoatPlacingAux(random, boatSize, boats)
                }
                else{
                    aiBoatPlacingAux(random, boatSize.tail, boats :+ newBoat)
                }
            }
        }
        aiBoatPlacingAux(random, boatSize, List[Boat]())
    }

    def easy(random: Random, playerState: PlayerState): Position = {
        Position(random.nextInt(Grid.width), random.nextInt(Grid.height))
    }

    def medium(random: Random, playerState: PlayerState): Position = { 
        getNewPosition(random, playerState)
    }

    def hard(random: Random, playerState: PlayerState): Position = {
        val lastNonMissShot = getLastNonMissShot(playerState.lastShotResults)
        if(playerState.lastShotResults.isEmpty  || lastNonMissShot.isEmpty || lastNonMissShot.get._1 == 2){
            getNewPosition(random, playerState)
        }
        else{
            getClosePosition(random, lastNonMissShot.get._2, playerState, 0)
        }
    }
    @tailrec
    def getLastNonMissShot(shots:List[(Int, Position)]): Option[(Int, Position)] = {
        if(shots.isEmpty){
            None
        }
        else{
            if(shots.last._1 != 0){
                Some(shots.last)
            }
            else{
                getLastNonMissShot(shots.dropRight(1))
            }
        }
    }

    @tailrec
    def getNewPosition(random: Random, playerState: PlayerState): Position = {
        val x = random.nextInt(Grid.width)
        val y = random.nextInt(Grid.height)
        if(playerState.lastShotResults.filter(shot => shot._2.x == x && shot._2.y == y).length != 0){
            getNewPosition(random, playerState)
        }
        else{
            Position(x, y)
        }
    }

    @tailrec
    def getClosePosition(random: Random, lastPos: Position, playerState: PlayerState, cpt: Int):Position = {
        if(cpt > 100){
            getNewPosition(random, playerState)
        }
        else{
            val direction = random.nextInt(4)
            var newPos = Position(0,0)
            direction match {
                case 0 => newPos = Position(lastPos.x + 1, lastPos.y)
                case 1 => newPos = Position(lastPos.x, lastPos.y + 1)
                case 2 => newPos = Position(lastPos.x, lastPos.y - 1)
                case 3 => newPos = Position(lastPos.x - 1, lastPos.y)
            }
            if(playerState.lastShotResults.filter(shot => shot._2.x == newPos.x && shot._2.y == newPos.y).length != 0){
                getClosePosition(random, lastPos, playerState, cpt + 1)
            }
            else{
                newPos
            }
        }
    }

}