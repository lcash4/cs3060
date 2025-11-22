import scala.io.StdIn._
import scala.util.Random

object NumGame {

    def game(randomNum : Int): Unit = {
        var found = false
        while found == false do {
            val usernum = readLine("Enter a number between 0 and 100: ").toInt
            println(s"You entered: $usernum")

            if (usernum < randomNum) {
                println("Too low! Try again.")
            } else if (usernum > randomNum) {
                println("Too high! Try again.")
            } else {
                println("Congratulations! You guessed the number!")
                found = true
            }
        }
    }



    def main(args: Array[String]):Unit = {

        
        val randomNum = Random.nextInt(101)
        game(randomNum)
    }
}