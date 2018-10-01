package battleship

import scala.annotation.tailrec
import scala.util.Random
import scala.collection.mutable.ListBuffer

case class GameState(isPlayerOneTurn: Boolean, playerOne: Player, playerTwo: Player)

object BatailleNavale extends App {
    /*
    val boats = List[Boat](Boat(true, List(Position(5,5), Position(5,6))))
    val hitPosition = List[Position](Position(2,3), Position(5,5))
    val shotPosition = List[Position](Position(5,5),Position(6,3))
    val hitShotPosition = List[Position](Position(1,5),Position(2,3))
    val playerState = PlayerState(boats, hitPosition, shotPosition ,hitShotPosition)*/
    def promptForGamemode(): Int = {
        Input.select(List[String]("Human vs Human","Human vs Ai","Ai vs Ai (not playable)"))
    }
    def createPlayers(random : Random): (Player, Player) = {
        val gamemode = promptForGamemode()
        gamemode match{
            case 1 => {
                println("H v H") //Create two  player with prompt as input
                println("Player 1 enter your boats.")
                val player1 = Player("Joueur 1", true, (random) => Input.getPosition, BoatCreator.createAllBoats)
                println("Player 2 enter your boats.")
                val player2 = Player("Joueur 2", true, (random) => Input.getPosition, BoatCreator.createAllBoats)
                (player1, player2)
            }
            case 2 => {
                val AiDifficulty = Input.select(List[String]("easy","medium","hard"))
                println("Player 1 enter your boats.")
                val player1 = Player("Joueur 1", true, (random) => Input.getPosition, BoatCreator.createAllBoats)
                val player2 = Player("Joueur 2", false, (random) => Position(random.nextInt(10), random.nextInt(10)), List[Boat](Boat(true, List(Position(5,5), Position(5,6)))))
                player2.playerState.boats.foreach(boat => println(boat.positions))
                (player1, player2)
            }
            case 3 => {
                (Player("Joueur 1", false, (random) => Position(random.nextInt(10), random.nextInt(10)), List[Boat](Boat(true, List(Position(5,5), Position(5,6))))), Player("Joueur 2", false, (random) => Position(random.nextInt(10), random.nextInt(10)), List[Boat](Boat(true, List(Position(5,5), Position(5,6))))) )
            }
        }
    }
    @tailrec
    def play(player: Player, opponent: Player, random: Random): Player = {
        val position = player.input(random)
        println(player.name + " just shot at " + position.x + " " + position.y)
        val shotResult = player.shoot(position, opponent)
        shotResult match {
            case 0 => println("It's a miss")
            case 1 => println("It's a hit")
            case 2 => {
                println("It's a sink")
                if(opponent.isDead){
                    println(player.name + " just won !")
                    return player
                }
            }
        }
        //if(player.isHuman)
            //UI.displayGrid(10, 10, player.playerState)
        if(player.isHuman && opponent.isHuman){
            println("Update the display by cleaning it !")
        }
        play(opponent, player, random)
    }
    def main(gameState: GameState, random: Random) = {
        if(gameState.isPlayerOneTurn)
            play(gameState.playerOne, gameState.playerTwo, random)
        else
            play(gameState.playerTwo, gameState.playerOne, random)
        if(gameState.playerOne.isHuman || gameState.playerTwo.isHuman)
            println("Do you want to change gamemode ?")
            //If no
                //Change starting player
                //init random
                //main(newGameState, random)
            //If yes 
                //val players = createPlayers()
                //Init random
                //main(GameState(true,players._1, players._2), random)
        }
    val random = Random
    val players = createPlayers(random)
    main(GameState(random.nextInt(2) == 0, players._1, players._2), random)
    //val players = createPlayers()
    //Init random
    //main(GameState(true,players._1, players._2), random)

    //Get boats from playerOne
    /*println(BoatCreator.createAllBoats())
    println(BoatCreator.createBoat(BoatCreator.getStartingBoatPosition(3, true), 3, true))
    val playerOneState = Player(List[Boat](), List[Position](), List[Position]())
    //Get boats from playerTwo
    val playerTwoState = PlayerState(List[Boat](), List[Position](), List[Position]())
    val s = GameState(true, playerOneState, playerTwoState)
    println(s)
    println(Boat(true, List[Position](new Position(0, 0))))
    //mainLoop(s, userInput, playTest)
    //@tailrec
    def mainLoop(gameState: GameState, player1Play: (Player) => (Int,Int), player2Play: (PlayerState) => (Int,Int)) {
      if(gameState.isPlayerOneTurn){
        println(player1Play(gameState.playerOne))
        println(player2Play(gameState.playerTwo))
      }
      else{
        println(player2Play(gameState.playerTwo))
        println(player1Play(gameState.playerOne))
      }
      println("Hey")
      //mainLoop(gameState, userInput, playTest)
    }
    def playTest(currentState: PlayerState) = (1,3)
    def userInput(currentState: PlayerState): (Int, Int) = (scala.io.StdIn.readInt(), scala.io.StdIn.readInt())*/
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