"""
Class HangmanMain is the driver program for the Hangman program.  It reads a
dictionary of words to be used during the game and then plays a game with
the user.  This is a cheating version of hangman that delays picking a word
to keep its options open.  You can change the setting for SHOW_COUNT to see
how many options are still left on each turn.
"""
from HangmanManager import HangmanManager
dictionary_path = "../"
dictionary_file_name = "dictionary.txt"

def main():
    print("Welcome to the hangman game")
    print()
    dict_file = open(dictionary_path+dictionary_file_name, "r")
    dictionary = dict_file.read().splitlines()
    print()
    length = int(input("What length word do you want to use? "))
    print()
    max_guesses = int(input("How many wrong answers allowed? "))
    print()
    hangman = HangmanManager(dictionary, length, max_guesses)
    if len(hangman.words()) == 0:
        print("No words of that length in the dictionary.")
    else:
        play_game(hangman)
        show_results(hangman)

def play_game(hangman):
    while hangman.guesses_left() > 0 and "-" in hangman.pattern():
        print("guesses : {}".format(hangman.guesses_left()))
        print("guessed : {}".format(hangman.characters_guessed()))
        print("current pattern : {}".format(hangman.pattern()))
        char = input("Your guess? ").lower()
        if char in hangman.characters_guessed():
            print("You already guessed that")
        else:
            count = hangman.record(char)
            if count == 0:
                print("Unlucky! There were no {}'s".format(char))
            elif count == 1:
                print("Yes! There is one " + char)
            else:
                print("Good guess! There are {} {}'s".format(count, char))
        print()

def show_results(hangman):
    """
    reports the results of the game, including showing the answer. 
    Input a HangmanManager
    """
    answer = next(iter(hangman.words()))
    print("answer = " + answer)
    if hangman.guesses_left()>0:
        print("YOU BEAT ME!")
    else:
        print("Sorry, you lose!")

if __name__ == "__main__":
    main()
