import scala.io.StdIn._
import scala.util.Random
import scala.collection.mutable.ListBuffer
import scala.util.control.Breaks.{break, breakable}


// Word Game where user tries to form words from random characters to earn points
// This object contains methods to play the game, generate random characters, get user input, and compare the user's word to the generated characters.
object WordGame {

    // Main game loop
    def play(): Unit = {
       
        // Initialize points
        var points = 0
        println("Welcome to the Word Game! Reach at least 100 points to win.")
        
        // Continue until user reaches 100 points
        while points < 100 do {
            
            // Generate random characters using the function made below
            // We also time how long the generation takes
            val start1 = System.nanoTime()
            val randomString = calcRanChars()
            val end1 = System.nanoTime()
            val duration1 = (end1 - start1) / 1e9d
            println(s"Comparison took $duration1 seconds.")
            
            // Display total points
            println(s"Total points: $points")
            println("")

            // Get user input word using the function made below
            val inputWord = userWord()

            // Compare user word to random characters and update points
            // We also time how long the comparison takes
            val start2 = System.nanoTime()
            points += compareUserWord(inputWord, randomString)
            val end2 = System.nanoTime()
            val duration2 = (end2 - start2) / 1e9d
            println(s"Comparison took $duration2 seconds.")


        }
        println("Congratulations! You've at least reached 100 points and won the game!") 
    }

    // Function to generate a list of random characters (vowels and consonants)
    def calcRanChars(): ListBuffer[Char] = {
        // Lists of consonants and vowels
        val listOfConsanants = List('b', 'c', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'm', 'n', 'p', 'q', 'r', 's', 't', 'v', 'w', 'x', 'y', 'z')
        val listOfVowels = List('a', 'e', 'i', 'o', 'u')
            

        // Generate a list of 15 random characters (with 4 vowels)    
        var randomString = ListBuffer[Char]()
        for (i <- 1 to 15) yield {
            if (i < 5) {
                randomString += listOfVowels(Random.nextInt(listOfVowels.length))
            } else {
                randomString += listOfConsanants(Random.nextInt(listOfConsanants.length))
            }
        }

        // This prints out the generated characters as a string for the user to see
        val ranWordString = randomString.mkString(", ")
        println(s"Generated characters: ${ranWordString}")
        return randomString
    }

    // Function to get the user's input word
    def userWord(): String = {
        val userword = readLine("Enter your word using these characters: ").toLowerCase()
        return userword
    }

    // Function to compare the user's word to the generated characters and calculate score
    def compareUserWord(userword: String, randomString: ListBuffer[Char]): Int = {
        var score = 0
        var isValid = true

        // This breakable block checks if the user's word can be formed from the generated characters, if it detects an invalid character it breaks out and awards 0 points
        // If the word is valid, it calculates the score based on word length
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

    // Test-only version without I/O for benchmarking
    def compareUserWordNoIO(userword: String, randomString: ListBuffer[Char]): Int = {
        var score = 0
        var isValid = true

        breakable {
            for (char <- userword) {
                if (!randomString.contains(char)) {
                    isValid = false
                    break
                } else {
                    randomString.remove(randomString.indexOf(char))
                }
            }

            if (isValid) {
                score = userword.length * 10
            }
        }
        return score
    }

    // Test-only version of calcRanChars without I/O
    def calcRanCharsNoIO(): ListBuffer[Char] = {
        val listOfConsanants = List('b', 'c', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'm', 'n', 'p', 'q', 'r', 's', 't', 'v', 'w', 'x', 'y', 'z')
        val listOfVowels = List('a', 'e', 'i', 'o', 'u')
        
        var randomString = ListBuffer[Char]()
        for (i <- 1 to 15) {
            if (i < 5) {
                randomString += listOfVowels(Random.nextInt(listOfVowels.length))
            } else {
                randomString += listOfConsanants(Random.nextInt(listOfConsanants.length))
            }
        }
        return randomString
    }

    // Main method enters to simplystart the game
    def main(args: Array[String]):Unit = {
        play()
        
        // The following is for performance testing only, put it in comments to fully play the game with no testing
        val start = System.nanoTime()
        for i <- 1 to 100000 do{
            compareUserWordNoIO("test", ListBuffer('t', 'e', 's', 't', 'a', 'b', 'c', 'd'))
        }
         val end = System.nanoTime()
         val duration = (end - start) / 1e9d

         val start2 = System.nanoTime()
         for i <- 1 to 100000 do{
            calcRanCharsNoIO()
         }
         val end2 = System.nanoTime()
         val duration2 = (end2 - start2) / 1e9d
         println(s"Comparison took $duration seconds.")
         println(s"Generation took $duration2 seconds.")

    }
}