//This class can be used to create nodes for a binary tree to be used in the compression
//of files. This is a node within the huffman tree


public class HuffmanNode implements Comparable<HuffmanNode>{
   public HuffmanNode left;//HuffmanNode for the left branch
   public HuffmanNode right;//HuffmanNode for the right branch
   public int freq;//frequency of occurence of the branch/node
   public int value;////integer value of the 8bit character code
   
   //Pre:input a frequency of occurence of the character with 8 bit integer of value
   //Post:Creates a huffmanNode with nothing in the left or right branches, but with the 
   //     value and frequency
   public HuffmanNode(int freq, int value){
      this(freq, null, null);
      this.value = value;
   }
   
   //Pre:input the total frequency of the occurence of the characters in the branches
   //    of the node, and input Huffman nodes for the left and right branches 
   //Post:Creates a HuffmanNode linked to two other nodes to the left and right
   public HuffmanNode(int freq, HuffmanNode left, HuffmanNode right){
      this.freq = freq;
      this.left = left;
      this.right = right;
   }
   
   //Pre:Input a HuffmanNode which is to be compared to the existing node
   //Post:Returns the difference in frequencys
   public int compareTo(HuffmanNode other){
      return freq - other.freq;
   }
}

