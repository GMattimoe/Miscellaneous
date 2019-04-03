//This class can be used to create a binary tree that is used to compress a file
//and then decode it. To construct the tree it is necessary to have an array of 
//ints corresponing to the counts of the characters. Where the position in the 
//array is the int value of the character


import java.util.*;
import java.io.*;

public class HuffmanTree{
   private HuffmanNode overallRoot;
   
   //Pre:Input an array of integers greater than or equal to zero, that represent character
   //    counts for ascii characters.
   //Post:Creates a huffman tree from the non zero characters in the input count.
   //     The characters with the largest counts will appear higher in the tree and
   //     the ones which are less frequent appear lower down.
   public HuffmanTree(int[] count){
      Queue<HuffmanNode> huffTree = new PriorityQueue<HuffmanNode>();
      for (int i = 0; i < count.length; i++){
         if (count[i] != 0){
            HuffmanNode temp = new HuffmanNode(count[i], i);
            huffTree.add(temp);
         }
      }
      HuffmanNode pseudo = new HuffmanNode(1, count.length);
      huffTree.add(pseudo);
      while (huffTree.size() > 1){
         HuffmanNode first = huffTree.poll();
         HuffmanNode second = huffTree.poll();
         HuffmanNode newNode = new HuffmanNode(first.freq + second.freq, first, second);
         huffTree.add(newNode);
      } 
      overallRoot = huffTree.poll();     
   }
   
   //Pre: Input a PrintStream to print the tree to.
   //Post:prints out all the character(leaf) nodes in the huffman tree in the standard format.
   //     This is a sequence of line pairs, the first line corresponding to the 8 bit binary 
   //     integer value of the character, the second line corresponds to the the position in 
   //     the tree of this leaf node. The leaf nodes are printed out from left to right. 
   //     When traversing the tree the left branch is assigned "0" and the right branch is 
   //     assigned "1" this sequence of 1's and 0's corresponds to the position in the tree 
   //     relative to the main root and is read left to right. The leafs are printed out
   //     in order from left most node to right most.
   public void write(PrintStream output){
      write(output, overallRoot, "");
   }
   
   //Pre: Input a PrintStream to print the tree to, and a HuffmanNode corresponding to the 
   //     current node being examined in the tree, the String "s" is the current sequence
   //     of 1's and 0's that correspond to the current position in the tree of the node
   //Post:prints out a HuffmanTree in the standard format. The values are printed from leftmost
   //     node to the right most node
   private void write(PrintStream output, HuffmanNode node, String s){
      if(node.left == null){ //could be left or right, either mean it is a leaf
         output.println(node.value);
         output.println(s);
         
      }
      else{
         write(output, node.left, s + "0");
         write(output, node.right, s + "1");
      }
   }
   
   //--------------------------------------//
   //          ---- PART TWO ----          //
   //--------------------------------------//

   //Pre: Input a Scanner to text that is in the standard format for a HuffmanNode
   //Post:Constructs a huffman tree from all the line pairs from the input
   public HuffmanTree(Scanner input){
      overallRoot = new HuffmanNode(-1, null, null);
      //in this huffman tree the value for fequency is irrelevant 
      //so the value -1 is given to all
      while (input.hasNextLine()){
         int n = Integer.parseInt(input.nextLine());//character value
         String code = input.nextLine();//direction to correct place in tree
         overallRoot = treeHelper(overallRoot, n, code);
      }
   }
   
   //Pre: Input a HuffmanNode corresponding to the current root in the HuffmanTree,
   //     an integer corresponding to the 8 bit binary integer of the character
   //     and a String consisting of 1's and 0's. Where 0 corresponds to a left
   //     and 1 to a right branch in the huffman tree. This String represents the 
   //     "direction" of where to place this character node in the huffman tree.
   //Post:Navigates through the huffman tree, creating new branches as necessary 
   //     and places the character in a leaf node dictated by the directions
   //     given in the String code.
   private HuffmanNode treeHelper(HuffmanNode root, int n, String code){
      if (code.length() == 0){
         root = new HuffmanNode(-1, n);//frequency is irrelevant so is set to -1
      }
      else{
         char direction = code.charAt(0);
         code = code.substring(1);
         if (direction == '0'){
            if (root.left == null){
               root.left = new HuffmanNode(-1, null, null);
            }
            root.left = treeHelper(root.left, n, code);
         }
         else{
            if (root.right == null){
               root.right = new HuffmanNode(-1, null, null);
            }
            root.right = treeHelper(root.right, n, code);
         }
      }
      return root;
   }
   
   //Pre: Input a BitInputStream of bits, and a PrintStream to be printed to, and an integer 
   //     which is the character code value of a character that signifies the end of the 
   //     document.
   //Post:decompresses the input file into text that can be read and prints it into the 
   //     output
   public void decode(BitInputStream input, PrintStream output, int eof){
      boolean end = false;
      while (!end){
         end = decodeHelper(overallRoot, input, output, eof);
      }
   }
   
   //Pre: Input HuffmanNode correspondng to the current root being examined, a BitInputStream 
   //     that consists of the remaining code to be decoded, a PrintStream to print the 
   //     decoded material, and an integer which corresponds to the end character.
   //Post:decompresses the input file into text that is printed into the output
   private boolean decodeHelper(HuffmanNode root, BitInputStream input, 
                                                            PrintStream output, int eof){
      boolean end;
      if(root.left == null){
         if (root.value == eof){
            return true;         }
         else{
            output.write(root.value);
            return false;
         }
      }
      else{
         int bit = input.readBit();
         if (bit == 0) {
            end = decodeHelper(root.left, input, output, eof);
         }
         else{
            end = decodeHelper(root.right, input, output, eof);
         }
      return end;
      }
   }

}
