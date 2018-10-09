package battleship

import battleship.input._
import battleship.output._
import battleship.model._
import scala.annotation.tailrec

import java.io.{BufferedWriter, FileWriter}
import scala.collection.JavaConversions._
import scala.collection.mutable.ListBuffer
import au.com.bytecode.opencsv.CSVWriter

import scala.util.Random

case class GameState(isPlayerOneTurn: Boolean, playerOne: Player, playerTwo: Player, gamemode: (Int, Int))

object BatailleNavale extends App {
    //Initialisation of the GUI, Input and List of boat names and sizes
    val UI:GUI = TerminalGUI()
    val In:Input = TerminalInput()
    val boats:List[(String, Int)] = List[(String,Int)](("Destroyer", 2), ("Submarine", 3), ("Cruiser", 3), ("Battleship", 4), ("Carrier", 5))

    /** Return a new instance of a human player. The function prompts the user to enter all of it's boats
   *    the function clears the display at the beginning and when the user is done entering it's boat the function
   *    ask for confirmation before returning 
   *
   *  @param random The random instance for the application
   *  @param name The name of the player
   *  @return An instance of human player with the given name.
   */
    def createHumanPlayer(random: Random, name: String): Player = {
        UI.display(name + " enter your boats.")
        val player1 = Player(name, true, (random: Random, playerState:PlayerState) => In.getPosition, In.promptAllBoats(boats))
        In.pressAnyKey(name + " press enter when you've finished reading !")
        player1
    }

    /** Return a two new instances of player. Depending on the gamemode given as parameter ( i.e : 2 humans for pvp, 1 human-1 AI for pve and 2 AI for the benchmark)
   *  @param random The random instance for the application
   *  @param gamemode The selected gamemode (1=pvp, 2=pve, 3=benchmark , 1=easy, 2=medium, 3=hard)
   *  @return Two instances of players depending on the gamemode.
   */
    def createPlayers(random : Random, gamemode : (Int,Int)): (Player, Player) = {
        gamemode._1 match{
            //Case Human vs Human
            case 1 => {
                val player1 = createHumanPlayer(random, "Player 1")
                UI.hideDisplay()
                val player2 = createHumanPlayer(random, "Player 2")
                UI.hideDisplay()
                (player1, player2)
            }
            //Case Human vs AI
            case 2 => {
                val AiDifficulty = gamemode._2
                val player1 = createHumanPlayer(random, "Player 1")
                var input: (Random, PlayerState) => Position = null
                AiDifficulty match {
                    case 1 => input = AiAlgorithm.easy
                    case 2 => input = AiAlgorithm.medium
                    case _ => input = AiAlgorithm.hard
                }
                val player2 = Player("Joueur 2", false, input, AiAlgorithm.aiBoatPlacing(random, boats))
                (player1, player2)
            }
        }
    }

    /** Loop allowing for 2 player to play one game. returns when one of the player lost, it return the winner and
   *    and the number of turns it took. The function will prompt the playing player to give a position to shoot and then 
   *    it will check the result of the shot and act accordingly.
   *  @param player The player that has to play for this turn
   *  @param opponent The opponent of the playing player
   *  @param random The random instance for the application
   *  @param turn Current turn count
   *  @return A tuple containing the winner and the number of turn it took.
   */
    @tailrec
    def play(player: Player, opponent: Player, random: Random, turn: Int): (Player, Int) = {
        //Function that checks if there is at least one human playing. (If there is no human playing no need to print)
        def toDisplayOrNotToDisplay(message:String) = {
            if(player.isHuman || opponent.isHuman) UI.display(message)
        }
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
        //Get the position the current player wants to shoot at
        val position = player.input(random, player.playerState)
        toDisplayOrNotToDisplay(player.name + " just shot at " + position.x + " " + position.y)
        //Shoot the opponent at the given position
        val shotResult = player.shoot(position, opponent)
        val newPlayer = shotResult._1
        val newOpponent = shotResult._2
        val result = shotResult._3
        val boat = shotResult._4
        result match {
            case 0 => toDisplayOrNotToDisplay("It's a miss")
            case 1 => toDisplayOrNotToDisplay("It's a hit on a " + boat.name)
            case 2 => {
                toDisplayOrNotToDisplay("A " + boat.name + " just sank !")
                //Check if the player just killed his opponent
                if(newOpponent.isDead){
                    return (newPlayer, turn)
                }
            }
        }
        //Next turn by inversing player and opponent
        play(newOpponent, newPlayer, random, turn + 1)
    }
    /** This function defines a game session, it uses the play function to let the two players from the 
   *    gamestate play, once it's over if one of the player was human it prompts the human if he wants to 
   *    play again or to change gamemode
   *  @param gamestate The state of the game that should be played
   *  @param random The random instance for the application
   */
    @tailrec
    def game(gameState: GameState, random: Random): Unit = {
        var resultGame:(Player, Int) = null
        //Depending on which player is starting the order of the parameters of play changes
        if(gameState.isPlayerOneTurn)
            resultGame = play(gameState.playerOne, gameState.playerTwo, random, 1)
        else
            resultGame = play(gameState.playerTwo, gameState.playerOne, random, 1)
        //Outputs the result of the game
        UI.display(resultGame._1.name + " just won in " + resultGame._2 + " turn !")
        //If there is at least one human, we prompt the user if he wants to play again
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

        /** Function allowing to play a given number of games between two players. It returns the number of won games for each player.
    *  @param gamestate The state of the game that should be played
    *  @param random The random instance for the application
    *  @param nbGames The number of games to be done
    *  @return A tuple containing two tuple containing the winner and the number of turn it took for both player.
    */
    def multipleGames(gameState: GameState, random: Random, nbGames: Int): ((String, Int), (String, Int)) = {
        def multipleGamesAux(gameState: GameState, random: Random, cpt: Int, record : ((String, Int), (String, Int)) ): ((String, Int), (String, Int)) = {
            if(cpt == nbGames){
                record
            }
            else{
                var resultGame:(Player, Int) = null
                if(gameState.isPlayerOneTurn)
                    resultGame = play(gameState.playerOne, gameState.playerTwo, random, 1)
                else
                    resultGame = play(gameState.playerTwo, gameState.playerOne, random, 1)
                UI.display("\033[2A")
                UI.display("Game #" + cpt + " : " + resultGame._1.name + " just won in " + resultGame._2 + " turn !")
                var newRecord: ((String, Int), (String, Int)) = null
                if(resultGame._1.name == record._1._1)
                    newRecord = ( (record._1._1, record._1._2 + 1), record._2 )
                else if(resultGame._1.name == record._2._1) 
                    newRecord = (  record._1 , (record._2._1, record._2._2 + 1) )
                multipleGamesAux(GameState(!gameState.isPlayerOneTurn, Player(gameState.playerOne.name, gameState.playerOne.isHuman, gameState.playerOne.input, AiAlgorithm.aiBoatPlacing(random, boats)), Player(gameState.playerTwo.name, gameState.playerTwo.isHuman, gameState.playerTwo.input, AiAlgorithm.aiBoatPlacing(random, boats)), (0,0)), random, cpt + 1, newRecord)
            }
        }
        multipleGamesAux(gameState, random, 0, ((gameState.playerOne.name, 0), (gameState.playerTwo.name, 0)))
    } 

    /** Main loop, it prompts the user for a gamemode and start the game depending on the gamemode.
    *  @param random The random instance for the application
    */
    @tailrec
    def main(random: Random): Unit = {
        val gamemode = In.promptForGamemode()
        //If the game mode is not AI vs AI we create the players and start the game
        if(gamemode._1 != 3){
            val players = createPlayers(random, gamemode)
            //val players = (Player("Joueur 1", false, (random) => Position(random.nextInt(10), random.nextInt(10)), List[Boat](Boat(true, List(Position(5,5), Position(5,6))))),Player("Joueur 2", false, (random) => Position(random.nextInt(10), random.nextInt(10)), List[Boat](Boat(true, List(Position(5,5), Position(5,6))))))
            game(GameState(random.nextInt(2) == 0, players._1, players._2, gamemode), random)
            main(random)
        }
        //If the gamemode if AI v AI
        else{
            //Initialisation of the different AI
            val easy = Player("Ai level easy", false, AiAlgorithm.easy, AiAlgorithm.aiBoatPlacing(Random, boats))
            val medium = Player("Ai level medium", false, AiAlgorithm.medium, AiAlgorithm.aiBoatPlacing(Random, boats))
            val hard = Player("Ai level hard", false, AiAlgorithm.hard, AiAlgorithm.aiBoatPlacing(Random, boats))
            val expert = Player("Ai level expert", false, AiAlgorithm.expert, AiAlgorithm.aiBoatPlacing(Random, boats))
            val nbGames = In.getInt("How many games do you want : ")
            val resEasyMedium = multipleGames(GameState(true, easy, medium, null), random, nbGames)
            val resEasyHard = multipleGames(GameState(true, easy, hard, null), random, nbGames)
            val resMediumHard = multipleGames(GameState(true, medium, hard, null), random, nbGames)
            saveRecords(List[((String, Int), (String, Int))](resEasyMedium, resEasyHard, resMediumHard))
            UI.display("A csv file with the results has been created !")
        }
    }
    /** Utilitary function to write the results of games in a csv file
    *  @param records A list containing the results of the games
    */
    def saveRecords(records: List[((String, Int), (String, Int))]) = {
        val outputFile = new BufferedWriter(new FileWriter("./output.csv"))
        val csvWriter = new CSVWriter(outputFile)
        val csvSchema = Array("AI name", "score", "AI Name2", "score2")
        csvWriter.writeAll(csvSchema :: records.map(record => Array[String](record._1._1, record._1._2.toString, record._2._1, record._2._2.toString)))
        outputFile.close()
    }
    main(Random)
}