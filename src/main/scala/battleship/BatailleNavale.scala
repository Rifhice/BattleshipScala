package battleship

import scala.annotation.tailrec
import scala.util.Random
import scala.collection.mutable.ListBuffer

case class GameState(isPlayerOneTurn: Boolean, playerOneState: PlayerState, playerTwoState: PlayerState)

object BatailleNavale extends App {
    //Get boats from playerOne
    println(BoatCreator.createAllBoats())
    println(BoatCreator.createBoat(BoatCreator.getStartingBoatPosition(3, true), 3, true))
    val playerOneState = PlayerState(List[Boat](), List[Position](), List[Position]())
    //Get boats from playerTwo
    val playerTwoState = PlayerState(List[Boat](), List[Position](), List[Position]())
    val s = GameState(true, playerOneState, playerTwoState)
    println(s)
    println(Boat(true, List[Position](new Position(0, 0, false))))
    //mainLoop(s, userInput, playTest)
    //@tailrec
    def mainLoop(gameState: GameState, player1Play: (PlayerState) => (Int,Int), player2Play: (PlayerState) => (Int,Int)) {
      if(gameState.isPlayerOneTurn){
        println(player1Play(gameState.playerOneState))
        println(player2Play(gameState.playerTwoState))
      }
      else{
        println(player2Play(gameState.playerTwoState))
        println(player1Play(gameState.playerOneState))
      }
      println("Hey")
      //mainLoop(gameState, userInput, playTest)
    }
    def playTest(currentState: PlayerState) = (1,3)
    def userInput(currentState: PlayerState): (Int, Int) = (scala.io.StdIn.readInt(), scala.io.StdIn.readInt())
}
/*
        println("Enter H for head  | T for tail | N to restart | I to see past game | q to quit : ")
        val flips = gameState.numFlips + 1
        var correct = gameState.numCorrect
        val input = userInput
        input match{
            case 'H' | 'T' => {
                val toss = tossCoin(r)
                toss match {
                    case 0 => if(input == 'T') correct = correct + 1
                    case 1 => if(input == 'H') correct = correct + 1
                }
                val newGameState = GameState(flips, correct)
                printGameState(newGameState)
                mainLoop(newGameState, random)
            }
            case 'N' => {
                savedGames = saveGame(savedGames, gameState)
                mainLoop(GameState(0,0), random)
            }
            case 'I' => {
                savedGames.map(save => printGameState(save))
                mainLoop(gameState, random)
            }
            case _ => {
                println("Game Over !")

            }
        }*/