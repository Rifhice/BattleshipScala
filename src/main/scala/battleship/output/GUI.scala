package battleship.output

import battleship.model._
trait GUI{
    
   /** Display the string message given 
   *  @param message string
   */
    def display(message: String)

   /** Hides the display allowing to change players in a human v human case */
    def hideDisplay()

   /** Display the player grids on a width and height rectangle. Should display two grids, one with the players boats and
    *   where he got shot and an other one with his shots
    *  @param width the width of the rectangle 
    *  @param height the height of the rectangle
    *  @param playerState the state of the player holding the data to display 
    */
    def displayGrid(width: Int, height: Int, playerState: PlayerState)
}