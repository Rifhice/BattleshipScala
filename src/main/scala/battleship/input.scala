package battleship

object Input{
    def getIntTuple(): (Int, Int) = { 
        try{
            print("Input the first int : ")
            System.out.flush
            val x = scala.io.StdIn.readInt()
            print("Input the second int : ")
            System.out.flush
            val y = scala.io.StdIn.readInt()
            (x,y)
        }
        catch{
            case err: NumberFormatException => getIntTuple()
        }
    }

    def getDirection: Boolean = {
        try{
            print("Type V for vertical or H for horizontal : ")
            System.out.flush
            val dir = scala.io.StdIn.readLine()(0).toUpper
            dir match{
                case 'V' => false
                case 'H' => true
                case _ => getDirection
            }
        }
        catch{
            case err: StringIndexOutOfBoundsException => getDirection
        }
    }
}