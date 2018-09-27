package battleship

import scala.collection.mutable.ListBuffer
import scala.annotation.tailrec

object BoatCreator{
    val boatSize = List[Int](1,1,1,1,1,2,2,2,2,3,3,3,4,4,5)

    def createAllBoats(): List[Boat] = {
        def createAllBoatsAux(boatSize: List[Int], boatList:List[Boat]): List[Boat] = {
            if(boatSize.size == 0){
                boatList
            }
            else{
                val direction = Input.getDirection
                val startingPosition = getStartingBoatPosition(boatSize.head, direction)
                val newBoat = createBoat(startingPosition, boatSize.head, direction)
                //boatList.filter(boat => boat.collide)
                createAllBoatsAux(boatSize.tail, boatList)
            }
        }
        createAllBoatsAux(boatSize, List[Boat]())
        println(boatSize.map(size => {
            val direction = Input.getDirection
            val startingPosition = getStartingBoatPosition(size, direction)
            createBoat(startingPosition, size, direction)
        }))        
        List[Boat]()
    }

    def getStartingBoatPosition(size: Int, isHorizontal: Boolean): Position = {
        var direction = ""
        if(isHorizontal) direction = "horizontal" else direction = "vertical"
        println("Enter a valid starting position for a " + direction + " " + size + " cell long boat.")
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
    def createBoat(startingPosition: Position, size: Int, isHorizontal: Boolean): Boat = {
        var positions = ListBuffer[Position]()
        @tailrec
        def createBoatAux(lastposition: Position, size: Int, isHorizontal: Boolean, positions: ListBuffer[Position]): Boat = {
            if(size == 0){
                Boat(isHorizontal, positions.toList)
            }
            else{
                if(isHorizontal) {
                    val newPos = Position(lastposition.x + 1, lastposition.y)
                    createBoatAux(newPos, size - 1, isHorizontal, positions += newPos) 
                }
                else {
                    val newPos = Position(lastposition.x, lastposition.y + 1)
                    createBoatAux(newPos, size - 1, isHorizontal, positions += newPos) 
                }
            }
        }
        createBoatAux(startingPosition, size, isHorizontal, positions)
    }
}