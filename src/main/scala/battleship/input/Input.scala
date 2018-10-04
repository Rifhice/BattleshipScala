package battleship.input

import battleship.model._

trait Input{
    def getIntTuple(): (Int, Int)
    def getPosition(): Position
    def pressAnyKey(message: String)
    def getDirection: Boolean
    def select(message: String, options: List[String]): Int
    def promptAllBoats(boatSize: List[(String, Int)]): List[Boat]
    def promptStartingBoatPosition(size: Int, isHorizontal: Boolean): Position
    def promptForGamemode(): (Int, Int) 
    def promptForYesNo(message: String): Boolean
}