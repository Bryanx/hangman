import scala.io.{Source, StdIn}

/**
  * @author Bryan de Ridder, Jarne Van Aerde
  */
object HangMan {

  // Main method
  def main(args: Array[String]): Unit = {

    val filename = "resources/words.txt"
    // 1. Get the words from file and convert them to a list.
    val words = Source.fromFile(filename).getLines.toList

    // 2. Get the random word in the list (check your boundaries).
    val r = scala.util.Random
    val word = words(r.nextInt(words.length))

    // 3. Make a template of underscores to show to the player, in order to let him know how many letters the word has.
    // Use 'map' on the target String.
    val template = word.map(_ => '_')

    // 4. Fix the number of possible tries and save it in a val.
    // Tell the player how many characters there are in the word.
    val tries = word.length * 2
    val charCount = word.length

    println(s"The word has $charCount characters.")
    // 5. Start the game by calling the function 'play'.
    play(tries, word, template)
  }

  // The function 'play'.
  //
  // 1. Read the word, by calling 'readWord'.
  // 2. Test the word, by calling 'testWord'. 'testWord' returns a code. Use this code to continue or stop.
  def play(numberCurrentTry: Int, target: String, word: String): Unit = {
    if (numberCurrentTry < 0) {
      println(s"You lost... the word was $target")
      System.exit(0)
    }
    println(s"Attempt $numberCurrentTry, give a character:")
    val charInput = readNewChar()
    val newWord = testWord(word, target, charInput)
    play(numberCurrentTry - 1, target, newWord)
  }

  // The function 'testWord'
  //
  // 1. Zip word and target, calling the function 'zipWords'.
  // 2. Show the current solution.
  // 3. If the current try still contains an _ , return the code to continue.
  //    If not, return the code for success.
  def testWord(word: String, target: String, charInput: Char): String = {
    val zippedWord = zipWords(word, target, charInput)
    println(zippedWord)
    if (zippedWord == target) {
      println("Congratulations, you guessed the word!")
      System.exit(0)
    }
    zippedWord
  }

  // The function 'zipWords'
  //
  // Find examples of the 'zip' function on the internet.
  //
  // Use zip to merge the target with the currentTry.
  // Use recursion, head and tail. Remember that a String is a list of characters.
  def zipWords(word: String, target: String, charInput: Char): String = {
    val zipped = word.zip(target)
    val mapped = zipped.map {
      case (wordChar, targetChar) =>
        if (targetChar == charInput) {
          charInput
        } else {
          wordChar
        }
    }
    mapped.mkString.replace(",", "")
  }

  // Utility function 'readWord'
  //
  // The input should be of the same length as the target, so check that here.
  // Only return a word if it has the same length as the target, otherwise return a message.
  def readNewChar(): Char = {
    val char = StdIn.readChar()
    if (char < 97 || char > 122) {
      println("Please enter a char between (a-z)")
      readNewChar()
    } else {
      char
    }
  }
}
