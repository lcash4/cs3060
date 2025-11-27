import scala.io.StdIn._
import scala.util.Random

object NumGame {

    def play(randomNum : Int): Unit = {
        var found = false
        while found == false do {
            val usernum = getUserNum()
            val start = System.nanoTime()
            found = compareUserNum(usernum, randomNum)
            val end = System.nanoTime()
            val duration = (end - start) / 1e9d
            println(s"Game completed in $duration seconds.")
        }
    }

    def getUserNum(): Int = {
        val usernum = readLine("Enter a number between 0 and 100: ").toInt
        if (usernum < 0 || usernum > 100) {
            println("Number out of range. Please try again.")
            return getUserNum()
        }
        println(s"You entered: $usernum")
        return usernum
    }

    def compareUserNum(usernum: Int, randomNum: Int): Boolean = {
            if (usernum < randomNum) {
                println("Too low! Try again.")
                return false
            } else if (usernum > randomNum) {
                println("Too high! Try again.")
                return false
            } else {
                println("Congratulations! You guessed the number!")
                return true
            }
    }
    def calcRandNumber(): Int = {
        Random.nextInt(101)
    }

    def main(args: Array[String]):Unit = {
        val randomNum = calcRandNumber()
        play(randomNum)
    }
}