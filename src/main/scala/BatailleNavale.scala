import scala.annotation.tailrec
import scala.util.Random
import scala.collection.mutable.ListBuffer

case class GameState(isPlayerOneTurn: Boolean, playerOneState: PlayerState, playerTwoState: PlayerState)
case class PlayerState()

object BatailleNavale extends App {
    val s = GameState(true, PlayerState(), PlayerState())
    println(s)
    mainLoop(s, userInput, playTest)
    @tailrec
    def mainLoop(gameState: GameState, player1Play: (PlayerState) => (Int,Int), player2Play: (PlayerState) => (Int,Int)) {
      if(GameState.isPlayerOneTurn){
        println(player1Play(gameState.playerOneState))
        println(player2Play(gameState.playerTwoState))
      }
      else{
        println(player2Play(gameState.playerTwoState))
        println(player1Play(gameState.playerOneState))
      }
      println("Hey")
      mainLoop(gameState, userInput, playTest)
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