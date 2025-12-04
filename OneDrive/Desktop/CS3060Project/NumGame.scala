import scala.io.StdIn._
import scala.util.Random
import scala.caps.use


// Number Guessing Game where user tries to guess a randomly generated number between 0 and 100
// This object contains methods to play the game, get user input, compare the user's guess to the random number, and generate the random number.
object NumGame {

    // This is the main game loop
    def play(): Unit = {
        // generate random number to guess and get the time taken to make it
        val start = System.nanoTime()
        val randomNum = calcRandNumber()
        val end = System.nanoTime()
        val duration = (end - start) / 1e9d
        println(s"Random number generated in $duration seconds.")
        var found = false

        // Continue until the user guesses the correct number
        while found == false do {
            // Get user input number using the function made below
            val usernum = getUserNum()

            // Compare user number to random number and update found status
            found = compareUserNum(usernum, randomNum)
        }
    }

    // Function to get the user's input number
    def getUserNum(): Int = {

        // Prompt user for a number between 0 and 100
        val usernum = readLine("Enter a number between 0 and 100: ").toInt

        // Validate input is within range 0-100, otherwise prompt again
        if (usernum < 0 || usernum > 100) {
            println("Number out of range. Please try again.")
            return getUserNum()
        }

        // Display the entered number
        println(s"You entered: $usernum")
        return usernum
    }

    // Function to compare the user's number to the random number
    def compareUserNum(usernum: Int, randomNum: Int): Boolean = {
       // If the number is too low then try again
        if (usernum < 0 || usernum > 100) {
            println("Number out of range. Please try again.")
            return false
        } else
        if (usernum < randomNum) {
            println("Too low! Try again.")
            return false
        } else if (usernum > randomNum) { // If the number is too high then try again
            println("Too high! Try again.")
            return false
        } else { // If the number is exactly correct, then print the player wins message
            println("Congratulations! You guessed the number!")
            return true
        }
    }

    // This function generates a random number between 0 and 100
    def calcRandNumber(): Int = {
        Random.nextInt(101)
    }

    // This is where scala enters, main generates the number to guess and then starts the play function
    def main(args: Array[String]):Unit = {
        play()
    }
}