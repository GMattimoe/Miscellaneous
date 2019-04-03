Huffman README

This Huffman project was originally a coursework set while at university. This was coded in Java. The BitInputStream and BitOutputStream were provided by the course instructor but the rest are original. 

The Python files are a translation of this code that was done as a personal project.

Run Encode to encode a text file into a compressed format. The file prompts for the text file, the output text file name and output code file. The code file is used to recreate the huffman tree when decoding.

Decode converts the compressed file into the original file using the code file.

HuffmanTree contains a class that for the huffman tree data structure

Huffman node contains a class of the node for the huffman tree

BitInputStream is used to read the bits and convert to characters

BitOutputStream is used to convert the characters into bit format