#This class can be used to in a hangman game that doesnt pick a word until there
#is no other word within an inputted dictionary that it could be. Hence "Evil Hangman"


class HangmanManager:

    def __init__(self, dictionary, length, max):
        """Pre: Input a Collection of Strings which will be the available words, an integer
        "length" for the length of word desired, and a integer "max" for the maximum
        number of guesses allowed. If the maximum number of guesses is less than 0
        or the length of the word is less that 1 throws IllegalArgumentException
        Post:Stores the inputs appropriately and eleminates all the words from the
        dictionary that are not of the length inputed."""

        if max<0 or length <1:
            raise ValueError("guesses must be > 0 and word must be at least 1 letter long")
        else:
            self.word_length = length
            self.num_guesses_left = max
            self.guesses = set()
            sub_dictionary = set()
            for word in dictionary:
                if len(word)==length:
                    sub_dictionary.add(word.lower())
            self.sub_dictionary = sub_dictionary

    def words(self):
        """returns a set of the available words in the current dictionary"""
        return self.sub_dictionary

    def guesses_left(self):
        """returns the number of guesses remaining in the game"""
        return self.num_guesses_left
    
    def characters_guessed(self):
        """returns the letters that have already been guessed"""
        return self.guesses

    def pattern(self):
        """returns the pattern made from the available words in the dictionary
           with of blank spaces for letters not yet guessed and letters that have
           been guessed in their correct positions. If the dictionary is empty 
           throws IllegalStateException"""
        if len(self.sub_dictionary)==0:
            raise ValueError("Dictionary is empty")
        else:
            return self.pattern_calculation()

    

    def pattern_calculation(self):
        word = next(iter(self.sub_dictionary))
        guess_set = self.guesses
        pattern = ""
        if word[0] in guess_set:
            pattern += word[0]
        else:
            pattern += "-"
        for i in range(1,self.word_length):
                if word[0] in guess_set:
                    pattern += " " word[i]
                else:
                    pattern += " -"
        return pattern