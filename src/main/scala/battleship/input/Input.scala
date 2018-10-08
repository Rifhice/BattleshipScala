package battleship.input

import battleship.model._

trait Input{
    /** Prompt for an integer by printing the message before
    *  @param message string
    */
    def getInt(message: String): (Int)

    /** Prompt for an integer tuple */
    def getIntTuple(): (Int, Int)

    /** Prompt a position */
    def getPosition(): Position

    /** Prompt the user to press a key to continue 
    *  @param message string
    */
    def pressAnyKey(message: String)

    /** Prompt the user for a direction (isHorizontal) */
    def getDirection: Boolean

    /** Prompt the user to chose one value between values in the string list */
    def select(message: String, options: List[String]): Int

    /** Prompt the user to enter all of it's boat
    *  @param boatSize list of boat size
    */
    def promptAllBoats(boatSize: List[(String, Int)]): List[Boat]

    /** Prompt the user for a starting position for a boat of given size and given direction 
    *  @param size size of the boat to be entered
    *  @param isHorizontal direction of the boat
    */
    def promptStartingBoatPosition(size: Int, isHorizontal: Boolean): Position

    /** Prompt the user to chose the game mode.  */
    def promptForGamemode(): (Int, Int) 

    /** Prompt the user for yes or no. 
    *  @param message string
    */
    def promptForYesNo(message: String): Boolean
}