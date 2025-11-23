import scala.io.StdIn._
import scala.util.Random

object NumGame {

    def play(randomNum : Int): Unit = {
        var found = false
        while found == false do {
            val usernum = getUserNum()
            found = compareUserNum(usernum, randomNum)
        }
    }

    def getUserNum(): Int = {
        val usernum = readLine("Enter a number between 0 and 100: ").toInt
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