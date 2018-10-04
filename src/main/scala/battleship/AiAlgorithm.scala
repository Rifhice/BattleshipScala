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
        Position(random.nextInt(Grid.width), random.nextInt(Grid.height))
    }

    def hard(random: Random, playerState: PlayerState): Position = {
        Position(random.nextInt(Grid.width), random.nextInt(Grid.height))
    }
}