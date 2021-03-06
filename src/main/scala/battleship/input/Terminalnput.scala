package battleship.input

import battleship.model._
import battleship.output._
import scala.annotation.tailrec

case class TerminalInput() extends Input{

    val UI:TerminalGUI = TerminalGUI()

    def getInt(message: String): Int = {
        try{
            print(message)
            System.out.flush
            scala.io.StdIn.readInt()
        }
        catch{
            case err: NumberFormatException => getInt(message)
        }
    }

    def getIntTuple(): (Int, Int) = { 
        val x = getInt("Input the x : ")
        val y = getInt("Input the y : ")
        (x,y)
    }

    def getPosition(): Position = { 
        val tuple = getIntTuple()
        Position(tuple._1, tuple._2, false)
    }

    def pressAnyKey(message: String) = {
        UI.display(message)
        try{
            readChar()
        }
        catch{
            case err:StringIndexOutOfBoundsException => {}
        }
    }

    def getDirection: Boolean = {
        try{
            print("Type V for vertical or H for horizontal : ")
            System.out.flush
            val dir = scala.io.StdIn.readLine()(0).toUpper
            dir match{
                case 'V' => false
                case 'H' => true
                case _ => getDirection
            }
        }
        catch{
            case err: StringIndexOutOfBoundsException => getDirection
        }
    }

    def select(message: String, options: List[String]): Int = {
        UI.display(message)
        options.indices.foreach(i => UI.display("[" + (i+1) + "]" + " " + options(i)))
        try{
            val x = scala.io.StdIn.readInt()
            if(x > options.size || x < 1){
                select(message, options)
            }
            else{
                x
            }
        }
        catch{
            case err: NumberFormatException => select(message, options)
        }
    }

    def promptAllBoats(boatSize: List[(String, Int)]): List[Boat] = {
        @tailrec
        def createAllBoatsAux(boatSize: List[(String, Int)], boatList:List[Boat]): List[Boat] = {
            if(boatSize.size == 0){
                boatList
            }
            else{
                UI.display("Enter values for a " + boatSize.head._1 + " of size " + boatSize.head._2 + ".")
                val direction = getDirection
                val startingPosition = promptStartingBoatPosition(boatSize.head._2, direction)
                val newBoat = Boat.createBoat(boatSize.head._1, startingPosition, boatSize.head._2, direction)
                if(boatList.filter(boat => newBoat.positions.filter(position => boat.collide(position)).length != 0).length != 0){
                    UI.display("The boat intersect with an existing one !")
                    createAllBoatsAux(boatSize, boatList)
                }
                else{
                    UI.createBoatSelectionGrid(Grid.width, Grid.height, boatList :+ newBoat).foreach(line => UI.display(line))
                    createAllBoatsAux(boatSize.tail, boatList :+ newBoat)
                }
            }
        }
        createAllBoatsAux(boatSize, List[Boat]())     
    }

    def promptStartingBoatPosition(size: Int, isHorizontal: Boolean): Position = {
        var direction = ""
        if(isHorizontal) direction = "horizontal" else direction = "vertical"
        UI.display("Enter a valid starting position for a " + direction + " " + size + " cell long boat.")
        var tuple = getIntTuple()
        var position = Position(tuple._1, tuple._2, false)
        if(Grid.isPositionInGrid(position)){
            if(Grid.doesBoatFitInGrid(position, size, isHorizontal)){
                position
            }
            else{
                promptStartingBoatPosition(size, isHorizontal)
            }
        }
        else{
            promptStartingBoatPosition(size, isHorizontal)
        }
    }

    def promptForGamemode(): (Int, Int) = {
        var difficulty = 0
        val gamemode = select("Please select :", List[String]("Human vs Human","Human vs Ai","Ai vs Ai (not playable)"))
        if(gamemode == 2)
            difficulty = select("Please select the difficulty :", List[String]("easy","medium","hard"))
        (gamemode, difficulty)
    }

    def promptForYesNo(message: String): Boolean = {
        UI.display(message + " Y/N")
        try{
            val char = readChar().toUpper
            char match{
                case 'Y' => true
                case 'N' => false
                case _ => promptForYesNo(message)
            }
        }
        catch{
            case err:StringIndexOutOfBoundsException => promptForYesNo(message)
        }
    }
}