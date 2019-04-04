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
           throws IllegalStateException
        """
        if len(self.sub_dictionary)==0:
            raise ValueError("Dictionary is empty")
        else:
            return self.pattern_calculation(next(iter(self.sub_dictionary)))

    def pattern_calculation(self, word):
        """ Pre:Input a String representing a word to create the pattern from, and a set of 
            characters to use to make the new pattern
            Post:Returns a String of pattern, with letters from the set of input characters in their
            correct places in the word and dashes where there are letters that were not in the set
        """
        guess_set = self.guesses
        pattern = ""
        if word[0] in guess_set:
            pattern += word[0]
        else:
            pattern += "-"
        for i in range(1,self.word_length):
                if word[i] in guess_set:
                    pattern += " " + word[i]
                else:
                    pattern += " -"
        return pattern

        
    def record(self, guess):
        """ Pre:   Input a character of a letter to be used as a guess. Case insensitive.
            Post:  If there are no guesses left or if there are no possible words the 
            it thows an IllegalStateException. If the guess has already been guessed
            before then it throws IllegalArgumentExcetion. 
            The method records the guess and creates a new pattern for each word. With this new 
            pattern, it collates all the words with the same pattern and picks the group with the 
            largest number of possible words in. The dictionary of possible words is updated to only
            include words from this new group with the same pattern.  The method returns the number
            of times the letter is found in this new pattern and if it is present in the word then 
            the number of guesses is not decreased. If it is not in this new pattern then the 
            number of guesses is decreased by one.
        """
        if len(self.sub_dictionary)==0:
            raise ValueError("Dictionary is empty")
        elif self.num_guesses_left < 1:
            raise ValueError("No guesses left")
        else:
            self.guesses.add(guess)
            sorted_patterns = self.sort_pattern()
            self.select_new_dictionary(sorted_patterns)
            word = next(iter(self.sub_dictionary))
            num_occurences = 0
            for i in range (self.word_length):
                if word[i] == guess:
                    num_occurences += 1
            if num_occurences == 0:
                self.num_guesses_left -= 1
        return num_occurences

    def sort_pattern(self):
        """
        Pre:Input a character
        Post:returns a mapping from all possible words to their corresponding new patterns that
        are created from the character
        """
        pattern_dictionary = {}
        for word in self.sub_dictionary:
            pattern = self.pattern_calculation(word)
            if pattern in pattern_dictionary.keys():
                pattern_dictionary.get(pattern).add(word)
            else:
                temp_set = {word}
                pattern_dictionary[pattern]=temp_set
        return pattern_dictionary

    def select_new_dictionary(self, sorted_patterns):
        max_pattern = list(sorted_patterns)[0]
        max_size = len(sorted_patterns.get(max_pattern))
        for key in sorted_patterns.keys():
            if len(sorted_patterns.get(key))>max_size:
                max_pattern = key
                max_size = len(sorted_patterns.get(key))
        self.sub_dictionary.clear()
        self.sub_dictionary = sorted_patterns.get(max_pattern)


