package battleship.output

import battleship.model._
trait GUI{
    def display(message: String)
    def hideDisplay()
    def displayGrid(width: Int, height: Int, playerState: PlayerState)
}