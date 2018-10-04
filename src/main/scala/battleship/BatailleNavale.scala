package battleship

import battleship.input._
import battleship.output._
import battleship.model._
import scala.annotation.tailrec
import scala.util.Random

case class GameState(isPlayerOneTurn: Boolean, playerOne: Player, playerTwo: Player, gamemode: (Int, Int))

object BatailleNavale extends App {

    val UI:GUI = TerminalGUI()
    val In:Input = TerminalInput()

    def createOneHumanPlayer(random: Random, name: String): Player = {
        UI.display(name + " enter your boats.")
        val player1 = Player(name, true, (random) => In.getPosition, In.promptAllBoats)
        In.pressAnyKey(name + " press enter when you've finished reading !")
        player1
    }

    def createPlayers(random : Random, gamemode : (Int,Int)): (Player, Player) = {
        gamemode._1 match{
            case 1 => {
                val player1 = createOneHumanPlayer(random, "Player 1")
                UI.hideDisplay()
                val player2 = createOneHumanPlayer(random, "Player 2")
                UI.hideDisplay()
                (player1, player2)
            }
            case 2 => {
                val AiDifficulty = gamemode._2
                val player1 = createOneHumanPlayer(random, "Player 1")
                val player2 = Player("Joueur 2", false, (random) => Position(random.nextInt(Grid.width), random.nextInt(Grid.height)), List[Boat](Boat(true, List(Position(5,5)))))
                (player1, player2)
            }
        }
    }
    @tailrec
    def play(player: Player, opponent: Player, random: Random, turn: Int): (Player, Int) = {
        if(opponent.isHuman && turn != 1){
            In.pressAnyKey(opponent.name + " press enter when you've finished reading !")
        }
        if(player.isHuman && opponent.isHuman){
            UI.hideDisplay()
            In.pressAnyKey("Press enter when " + player.name + " is ready !")
        }
        if(player.isHuman){
            UI.displayGrid(Grid.width, Grid.height, player.playerState)
            UI.display(player.name + " please enter a position to shoot !")
        }
        val position = player.input(random)
        UI.display(player.name + " just shot at " + position.x + " " + position.y)
        val shotResult = player.shoot(position, opponent)
        val newPlayer = shotResult._1
        val newOpponent = shotResult._2
        val result = shotResult._3
        result match {
            case 0 => UI.display("It's a miss")
            case 1 => UI.display("It's a hit")
            case 2 => {
                UI.display("It's a sink")
                if(newOpponent.isDead){
                    return (newPlayer, turn)
                }
            }
        }
        play(newOpponent, newPlayer, random, turn + 1)
    }
    @tailrec
    def game(gameState: GameState, random: Random): Unit = {
        var resultGame:(Player, Int) = null
        if(gameState.isPlayerOneTurn)
            resultGame = play(gameState.playerOne, gameState.playerTwo, random, 1)
        else
            resultGame = play(gameState.playerTwo, gameState.playerOne, random, 1)
        UI.display(resultGame._1.name + " just won in " + resultGame._2 + " turn !")
        if(gameState.playerOne.isHuman || gameState.playerTwo.isHuman){
            UI.display("Do you want to change gamemode ?")
            if(In.promptForYesNo("Play again ?")){
                val players = createPlayers(random, gameState.gamemode)
                game(GameState(!gameState.isPlayerOneTurn, players._1 , players._2, gameState.gamemode), random)
            }
            else{
                return
            }
        }
    }
    @tailrec
    def main(random: Random): Unit = {
        val gamemode = In.promptForGamemode()
        if(gamemode._1 != 3){
            val players = createPlayers(random, gamemode)
            //val players = (Player("Joueur 1", false, (random) => Position(random.nextInt(10), random.nextInt(10)), List[Boat](Boat(true, List(Position(5,5), Position(5,6))))),Player("Joueur 2", false, (random) => Position(random.nextInt(10), random.nextInt(10)), List[Boat](Boat(true, List(Position(5,5), Position(5,6))))))
            game(GameState(random.nextInt(2) == 0, players._1, players._2, gamemode), random)
            main(random)
        }
        else{
            UI.display("Benchmark")
            //create easy AI
            //create medium AI
            //create hard AI
            //100 games easy-medium
            //100 games medium-hard
            //100 games easy-hard
        }
    }
    main(Random)
}