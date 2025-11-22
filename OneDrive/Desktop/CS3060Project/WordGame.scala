import scala.io.StdIn._
import scala.util.Random
import scala.collection.mutable.ListBuffer
import scala.util.control.Breaks.{break, breakable}

object WordGame {

    def game(): Unit = {
        var points = 0

        
        while points < 100 do {
            val randomString = calcChars()
            points += userGuess(randomString)
            println(s"Total points: $points")
            println("")
        }
        println("Congratulations! You've at least reached 100 points and won the game!") 
    }

    def calcChars(): ListBuffer[Char] = {
        val listOfConsanants = List('b', 'c', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'm', 'n', 'p', 'q', 'r', 's', 't', 'v', 'w', 'x', 'y', 'z')
        val listOfVowels = List('a', 'e', 'i', 'o', 'u')
            
            
        var randomString = ListBuffer[Char]()
        for (i <- 1 to 15) yield {
            if (i < 5) {
                randomString += listOfVowels(Random.nextInt(listOfVowels.length))
            } else {
                randomString += listOfConsanants(Random.nextInt(listOfConsanants.length))
            }
        }
        val ranWordString = randomString.mkString(", ")
        println(s"Generated characters: ${ranWordString}")
        return randomString
    }

    def userGuess(randomString: ListBuffer[Char]): Int = {
        var score = 0
        val userword = readLine("Enter your word using these characters: ").toLowerCase()
            
        var isValid = true

        breakable {
            for (char <- userword) {
                if (!randomString.contains(char)) {
                    isValid = false
                    println("This word cannot be formed from the given characters. 0 points awarded.")
                    break
                } else {
                    randomString.remove(randomString.indexOf(char))
                }
            }

            if (isValid) {
                score = userword.length * 10
                println(s"Valid word! You scored $score points.")
            }
        }
        return score
    }

    def main(args: Array[String]):Unit = {
        game()
    }
}