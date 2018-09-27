package battleship

object Input{
    def getIntTuple(): (Int, Int) = { 
        print("Input the first int : ")
        System.out.flush
        val x = scala.io.StdIn.readInt()
        print("Input the second int : ")
        System.out.flush
        val y = scala.io.StdIn.readInt()
        (x,y)
    }
}