

import random
import time
from abc import ABC, abstractmethod


# Abstract Base Class
class BaseGame(ABC):

    def __init__(self):
        self.value = None            # Shared value between games
        self.error_count = 0         # Track bad inputs
        self.start_time = 0          # Track time
        self.end_time = 0            # Track time
        self.userInput = None        # Shared user input
    # Start timer
    def startTimer(self):
        self.start_time = time.perf_counter()
    # End timer
    def endTimer(self):
        self.end_time = time.perf_counter()
    # Results of game
    def printResults(self):
        print("Errors:", self.error_count)
        print("Time:", (self.end_time - self.start_time), "seconds")

    # Play game via abstract method
    @abstractmethod
    def play(self):
        pass




# Number Guessing Game Class
class NumberGame(BaseGame):

    def calcRandNum(self):
        # Shared value becomes an int
        self.value = random.randint(1, 100)

    def getUserNum(self):
        try:
            self.userInput = int(input("Guess a number between 1 and 100: "))
            return self.userInput
        except:
            print("Invalid number. Try again.")
            self.error_count += 1
            return None

    def compareUserNum(self, userNum):
        if userNum < self.value:
            print("Your guess is lower than the secret number.")
            return False
        elif userNum > self.value:
            print("Your guess is higher than the secret number.")
            return False
        else:
            return True

    def play(self):
        print("\nNUMBER GUESSING GAME")

        self.startTimer()
        self.calcRandNum()
        self.endTimer()
        self.printResults()


        attempts = 0

        while self.userInput != self.value:
            self.userInput = self.getUserNum()

            if self.userInput is None:
                continue  # bad guess

            attempts += 1

            self.startTimer()
            if self.compareUserNum(self.userInput):
                break
            self.endTimer()
            self.printResults()

        print("\nGood job, you guessed the secret number!")
        print("It took you", attempts, "tries")

    # This function compares user number to random number without any IO for performance testing
    def compareUserNumNoIO(self, userNum):
        if userNum < self.value:
            return False
        elif userNum > self.value:
            return False
        else:
            return True




# Word Game Class
class WordGame(BaseGame):

    def __init__(self):
        super().__init__()
        self.points = 0
        self.vowels = ['a', 'e', 'i', 'o', 'u']
        self.consonants = [
            'b', 'c', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'm',
            'n', 'p', 'q', 'r', 's', 't', 'v', 'w', 'x', 'y', 'z'
        ]

    def generateRanChar(self):
        """Generate 15 characters:
           - First 4 vowels
           - Remaining consonants"""
        self.value = []

        # First 4 letters = vowels
        for _ in range(4):
            self.value.append(random.choice(self.vowels))
        # Remaining 11 = consonants
        for _ in range(11):
            self.value.append(random.choice(self.consonants))

        return self.value

    def playWordGame(self):
        self.play()

    def getUserWord(self):
        self.userInput = input("Enter your word using these characters: ").lower().strip()
        return self.userInput
    
    def compareUserWord(self, userword, available):
        isValid = True

        for ch in userword:
            if ch not in available:
                print("This word cannot be formed from the given characters. 0 points awarded.")
                isValid = False
                self.error_count += 1
                break
            else:
                available.remove(ch)  # remove used letter
        return isValid

    # Test-only version without I/O for benchmarking
    def compareUserWordNoIO(self, userword, available):
        isValid = True

        for ch in userword:
            if ch not in available:
                isValid = False
                break
            else:
                available.remove(ch)  # remove used letter
        return isValid

    # Test-only version of generateRanChar without I/O
    def generateRanCharNoIO(self):
        """Generate 15 characters without any printing"""
        value = []
        
        # First 4 letters = vowels
        for _ in range(4):
            value.append(random.choice(self.vowels))
        # Remaining 11 = consonants
        for _ in range(11):
            value.append(random.choice(self.consonants))
        
        return value

    def play(self):
        print("\nWORD SCRABBLE GAME")
        

        while self.points < 100:
            
            # Generate letters for this round
            self.startTimer()
            self.value = self.generateRanChar()
            self.endTimer()
            self.printResults()
            available = self.value.copy()  # Mutable list like Scala ListBuffer


            print("Generated characters:", ", ".join(self.value))

            self.userInput = self.getUserWord()

            if self.userInput == "":
                print("Empty word. Try again.")
                self.error_count += 1
                continue
            
            self.startTimer()
            isValid = self.compareUserWord(self.userInput, available)
            self.endTimer()
            self.printResults()

            if isValid:
                score = len(self.userInput) * 10
                self.points += score
                print(f"Valid word! You scored {score} points.")
                print(f"Total points: {self.points}")

            # Check win
            if self.points >= 100:
                print("\nCongratulations! You've reached 100 points and won the game!")




def main():
    while True:
        print("\nWhat game would you like to play?")
        print("1 - Number Guessing Game")
        print("2 - Word Game")
        print("3 - Quit")

        choice = input("Choose a game: ")

        if choice == "1":
            NumberGame().play()
            
             #The following is for performance testing only, put it in comments to fully play the game with no testing
 
            numGame = NumberGame()
            
            numGame.calcRandNum()  # Generate number to compare against
            numGame.startTimer()
            for i in range(100000000):
                numGame.compareUserNumNoIO(10)
            numGame.endTimer()

            numGame1 = NumberGame()
            numGame1.startTimer()
            for i in range(100000000):
                numGame1.calcRandNum()
            numGame1.endTimer()
            numGame.printResults()
            numGame1.printResults()
        elif choice == "2":
            WordGame().playWordGame()

            #The following is for performance testing only, put it in comments to fully play the game with no testing

            wordgame = WordGame()
            testLetters = ['t', 'e', 's', 't', 'a', 'b', 'c', 'd']
            wordgame.startTimer()
            for i in range(100000000):
                wordgame.compareUserWordNoIO("test", testLetters.copy())
            wordgame.endTimer()
            wordgame.printResults()
            
            wordgame2 = WordGame()
            wordgame2.startTimer()
            for i in range(100000000):
                wordgame2.generateRanCharNoIO()
            wordgame2.endTimer()
            wordgame2.printResults()

        elif choice == "3":
            print("Goodbye!")
            break
        else:
            print("Invalid selection. Try again.")



# Run program
if __name__ == "__main__":
    main()

