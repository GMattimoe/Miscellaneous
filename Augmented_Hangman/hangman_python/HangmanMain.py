"""
Class HangmanMain is the driver program for the Hangman program.  It reads a
dictionary of words to be used during the game and then plays a game with
the user.  This is a cheating version of hangman that delays picking a word
to keep its options open.  You can change the setting for SHOW_COUNT to see
how many options are still left on each turn.
"""
from HangmanManager import HangmanManager
dictionary_path = "../"
dictionary_file_name = "dictionary2.txt"

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
        print("need to code the game")
if __name__ == "__main__":
    main()
