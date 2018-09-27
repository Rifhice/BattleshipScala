package battleship
import java.awt.Color
import scala.swing._

class UI extends MainFrame {
    title = "GUI Program #4"

    def displayGrid(width: Int, height: Int/*, shotCells:List[Position], listBoat: List[Boat], hitCells:List[Position]*/): Unit = {
        def displayGridAux(width: Int, height: Int, x: Int, y: Int, grid: GridPanel): GridPanel = {
            println(x + " " + y)
            if(!(x == width && y == height)){
                grid.contents += Button("X") { println("Hey") }
                var (newX, newY) = (x , y)
                if(x + 1 > width){
                    newX = 0
                    newY = y + 1
                }else{
                    newX = x + 1
                    newY = y
                }
                displayGridAux(width, height, newX, newY, grid)
            }
            else{
                grid
            }
        }
        val grid = displayGridAux(width, height, 0, 0, new GridPanel(width, height))
        contents = new BorderPanel {
            border = Swing.MatteBorder(8, 8, 8, 8, Color.white)
            add(new Label("Hello"), BorderPanel.Position.North )
            add(grid, BorderPanel.Position.Center)
        }
    }
}

object GuiProgramOne extends App{
    val ui = new UI
    ui.visible = true
    ui.displayGrid(10, 10)
    println("End of main function")
}