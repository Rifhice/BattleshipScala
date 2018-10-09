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

    def expert(random: Random, playerState: PlayerState): Position = {
        def checkSide(firstSide: List[Position], secondSide: List[Position], playerState: PlayerState): Option[Position] = {
            if(!(firstSide.isEmpty || secondSide.isEmpty)){
                val firstPos = checkPositions(firstSide, playerState)
                if(firstPos.isEmpty){
                    val secondPos = checkPositions(secondSide, playerState)
                    if(secondPos.isEmpty){
                        None
                    }
                    else{
                        secondPos
                    }
                }
                else{
                    firstPos
                }
            }
            else{
                None
            }
        }
        @tailrec
        def checkPositions(positions: List[Position], playerState: PlayerState): Option[Position] = {
            if(!positions.isEmpty){
                val position = positions.head
                val potentialShot = playerState.lastShotResults.find(lastShot => lastShot._2.x == position.x && lastShot._2.y == position.y)
                if(!potentialShot.isEmpty){
                    potentialShot.get._1 match {
                        case 0|2 => None
                        case 1 => checkPositions(positions.tail, playerState)
                    }
                }
                else{
                    Some(position)
                }           
            }
            else{
                None
            }             
        }
        val lastsNonMissShot = getLastsNonMissShot(playerState.lastShotResults)
        val bothLasts = lastsNonMissShot.dropRight(lastsNonMissShot.length - 2)
        if(bothLasts.length == 2 && bothLasts(0)._1 == 1 && bothLasts(1)._1 == 1){
            val xDistance = bothLasts(0)._2.x - bothLasts(1)._2.x
            val yDistance = bothLasts(0)._2.y - bothLasts(1)._2.y
            var firstPos: List[Position] = null
            var secondPos: List[Position] = null
            if(Math.abs(xDistance) == 1 && yDistance == 0 || Math.abs(yDistance) == 1 && xDistance == 0){
                if(Math.abs(xDistance) == 1 && yDistance == 0){
                    if(xDistance < 0){
                        firstPos = List[Position](Position(bothLasts(1)._2.x + 1, bothLasts(1)._2.y), Position(bothLasts(1)._2.x + 2, bothLasts(1)._2.y), Position(bothLasts(1)._2.x + 3, bothLasts(1)._2.y))
                        secondPos = List[Position](Position(bothLasts(0)._2.x - 1, bothLasts(1)._2.y), Position(bothLasts(0)._2.x - 2, bothLasts(1)._2.y), Position(bothLasts(0)._2.x - 3, bothLasts(1)._2.y))
                    }
                    else{
                        firstPos = List[Position](Position(bothLasts(0)._2.x + 1, bothLasts(1)._2.y), Position(bothLasts(0)._2.x + 2, bothLasts(1)._2.y), Position(bothLasts(0)._2.x + 3, bothLasts(1)._2.y))
                        secondPos = List[Position](Position(bothLasts(1)._2.x - 1, bothLasts(1)._2.y), Position(bothLasts(1)._2.x - 2, bothLasts(1)._2.y), Position(bothLasts(1)._2.x - 3, bothLasts(1)._2.y))
                    }
                }
                else if(Math.abs(yDistance) == 1 && xDistance == 0){
                    if(yDistance < 0){
                        firstPos = List[Position](Position(bothLasts(1)._2.x, bothLasts(1)._2.y + 1), Position(bothLasts(1)._2.x, bothLasts(1)._2.y + 2), Position(bothLasts(1)._2.x, bothLasts(1)._2.y + 3))
                        secondPos = List[Position](Position(bothLasts(0)._2.x, bothLasts(1)._2.y - 1), Position(bothLasts(0)._2.x, bothLasts(1)._2.y - 2), Position(bothLasts(0)._2.x, bothLasts(1)._2.y - 3))
                    }
                    else{
                        firstPos = List[Position](Position(bothLasts(0)._2.x, bothLasts(1)._2.y + 1), Position(bothLasts(0)._2.x, bothLasts(1)._2.y + 2), Position(bothLasts(0)._2.x, bothLasts(1)._2.y + 3))
                        secondPos = List[Position](Position(bothLasts(1)._2.x, bothLasts(1)._2.y - 1), Position(bothLasts(1)._2.x, bothLasts(1)._2.y - 2), Position(bothLasts(1)._2.x, bothLasts(1)._2.y - 3))
                    }
                }
                val firstSide = random.nextInt(2) == 1
                var pos: Option[Position] = null
                if(firstSide){
                    pos = checkSide(firstPos, secondPos, playerState)
                }
                else{
                    pos = checkSide(secondPos, firstPos, playerState)
                }
                if(pos.isEmpty){
                    getNewPosition(random, playerState)
                }
                else{
                    pos.get
                }
            }
        }
        val lastNonMissShot = getLastNonMissShot(playerState.lastShotResults)
        if(playerState.lastShotResults.isEmpty  || lastNonMissShot.isEmpty || lastNonMissShot.get._1 == 2){
            getNewPosition(random, playerState)
        }
        else{
            getClosePosition(random, lastNonMissShot.get._2, playerState, 0)
        }
    }

    
    def getLastsNonMissShot(shots:List[(Int, Position)]): List[(Int, Position)] = {
        @tailrec
        def getLastsNonMissShotAux(shots:List[(Int, Position)], nonMissedShot: List[(Int, Position)] ): List[(Int, Position)] = {
            if(shots.isEmpty){
                nonMissedShot
            }
            else{
                if(shots.last._1 != 0){
                    getLastsNonMissShotAux(shots.dropRight(1), nonMissedShot :+ shots.last)
                }
                else{
                    getLastsNonMissShotAux(shots.dropRight(1), nonMissedShot)
                }
            }
        }
        getLastsNonMissShotAux(shots, List[(Int, Position)]())        
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