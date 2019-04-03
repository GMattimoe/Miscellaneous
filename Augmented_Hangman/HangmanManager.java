//This class can be used to in a hangman game that doesnt pick a word until there
//is no other word within an inputted dictionary that it could be. Hence "Evil Hangman"

import java.util.*;

public class HangmanManager{
   //Set of strings that will hold the possible words available
   private SortedSet<String> subDictionary;
   private SortedSet<Character> guesses;//Set of characters to hold the letter guesses
   private int wordLength;//intiger of the target length of the word
   private int numGuessesLeft;//integer of the number of guesses remaining
   
   //Pre: Input a Collection of Strings which will be the available words, an integer
   //     "length" for the length of word desired, and a integer "max" for the maximum
   //     number of guesses allowed. If the maximum number of guesses is less than 0
   //     or the length of the word is less that 1 throws IllegalArgumentException
   //Post:Stores the inputs appropriately and eleminates all the words from the
   //dictionary that are not of the length inputed.
   public HangmanManager(Collection<String> dictionary, int length, int max){
      if (max < 0 || length < 1){
         throw new IllegalArgumentException();
      }
      else{
         guesses = new TreeSet<Character>();
         subDictionary = new TreeSet<String>();
         wordLength = length;
         numGuessesLeft = max;
         for(String word: dictionary){
            if(word.length() == wordLength){
               subDictionary.add(word.toLowerCase());
            }
         }
      }
   }
   
   //Post: reports the available words in the current dictionary
   public Set<String> words(){
      return subDictionary;
   }  
   
   //Post: reports the number of guesses remaining in the game
   public int guessesLeft(){
      return numGuessesLeft;
   }
   
   //Post: reports the letters that have already been guessed
   public SortedSet<Character> guesses(){
      return guesses;
   }
   
   //Post: returns the pattern made from the available words in the dictionary
   //      with of blank spaces for letters not yet guessed and letters that have
   //      been guessed in their correct positions. If the dictionary is empty 
   //      throws IllegalStateException
   public String pattern(){
      if (subDictionary.isEmpty()){
         throw new IllegalStateException();
      }
      else{
         return patternCalc(subDictionary.first(), guesses);
      }
   }
      
   //Pre:   Input a character of a letter to be used as a guess. Case insensitive.
   //Post:  If there are no guesses left or if there are no possible words the 
   //it thows an IllegalStateException. If the guess has already been guessed
   //before then it throws IllegalArgumentExcetion. 
   //The method records the guess and creates a new pattern for each word. With this new 
   //pattern, it collates all the words with the same pattern and picks the group with the 
   //largest number of possible words in. The dictionary of possible words is updated to only
   //include words from this new group with the same pattern.  The method returns the number
   //of times the letter is found in this new pattern and if it is present in the word then 
   //the number of guesses is not decreased. If it is not in this new pattern then the 
   //number of guesses is decreased by one.
   public int record(char guess){
      if ((guessesLeft() < 1) || subDictionary.isEmpty()){
         throw new IllegalStateException();
      }
      else if(guesses.contains(guess)){
         throw new IllegalArgumentException();     
      }
      else{
         guess = Character.toLowerCase(guess);
         guesses.add(guess);
         TreeMap<String, TreeSet<String>> sorted = sortPattern(guess);
         selectNewDictionary(sorted); //deciding the new set of words
         //as all the words in this set have the same pattern it doesnt matter
         //which word is used to count the number of occurences of the guessed letter
         String word = subDictionary.first();//so the first word is used
         int numOccur = 0;
         for (int i = 0; i < wordLength; i++){
            if (word.charAt(i) == guess){
               numOccur++;
            }
         }
         if (numOccur == 0){
            numGuessesLeft--;
         }
         return numOccur;
      }
   }
   
   //Pre:Input a String representing a word to create the pattern from, and a set of 
   //    characters to use to make the new pattern
   //Post:Returns a String of pattern, with letters from the set of input characters in their
   //  correct places in the word and dashes where there are letters that were not in the set
   private String patternCalc(String word, SortedSet<Character> guessSet){
      String pattern = "";
      if (guessSet.contains(word.charAt(0))){
         pattern += word.charAt(0);
      }
      else{
         pattern += "-";
      }
      for (int i = 1; i < wordLength; i++){
         if (guessSet.contains(word.charAt(i))){
            pattern += " " + word.charAt(i);
         }
         else{
            pattern += " -";
         }
      }   
      return pattern;                  
   }
   
   //Pre:Input a character
   //Post:returns a mapping from all possible words to their corresponding new patterns that
   //     are created from the character  
   private TreeMap<String, TreeSet<String>> sortPattern(char guess){
      TreeMap<String, TreeSet<String>> map = new TreeMap<String, TreeSet<String>>();
      TreeSet<Character> guessSet = new TreeSet<Character>();
      guessSet.add(guess); 
      String pattern;
      for (String word: subDictionary){
            pattern = patternCalc(word, guessSet);
         if (map.containsKey(pattern)){
            map.get(pattern).add(word);
         }
         else{
            TreeSet<String> temp = new TreeSet<String>();
            temp.add(word);
            map.put(pattern, temp);
         }   
      }
      return map;     
   }
   
   //Pre: input a TreeMap with the pattern mapping to a set of possible words
   //Post: chooses the pattern with the most ammount of words associated with it
   //and changes the dictionary of words to now only include words from this set
   private void selectNewDictionary(TreeMap<String, TreeSet<String>> map){ 
      String pattern = map.firstKey();
      TreeSet<String> tempDictionary = map.get(pattern);
      int maxSize = tempDictionary.size();
      map.remove(pattern);
      for (String key : map.keySet()){
         if (map.get(key).size() > maxSize){
            pattern = key;
            tempDictionary = map.get(key);
            maxSize = tempDictionary.size();
         }  
      }
      subDictionary = tempDictionary;      
   }


}