import scala.io.StdIn._
import scala.util.Random
import scala.collection.mutable.ListBuffer
import scala.util.control.Breaks.{break, breakable}

object WordGame {
    def main(args: Array[String]):Unit = {
        val listOfConsanants = List('b', 'c', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'm', 'n', 'p', 'q', 'r', 's', 't', 'v', 'w', 'x', 'y', 'z')
        val listOfVowels = List('a', 'e', 'i', 'o', 'u')
        var points = 0
        while (points < 100) {
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
                    val score = userword.length * 10
                    println(s"Valid word! You scored $score points.")
                    points += score
                    println(s"Total points: $points")
                }

                if (points >= 100) {
                    println("Congratulations! You've reached 100 points and won the game!") 
            }
        }
    }
}
