/* *****************************************************************************
 *  Name: Sohan Kolla
 *  Date: 08/10/2023
 *  Description: Takes an integer k as a command-line argument; reads a sequence
 *               of strings from standard input using StdIn.readString(); and
 *               prints exactly k of them, uniformly at random. Prints each item
 *               from the sequence at most once.
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        RandomizedQueue<String> permutation = new RandomizedQueue<>();
        // creating a randomized queue to store our strings

        while (!StdIn.isEmpty()) { // while there are still strings to add
            permutation.enqueue(StdIn.readString()); // add the string
        }

        int count = 0;
        for (String s : permutation) { // uses the random iterator we implemented
            if (count >= Integer.parseInt(args[0])) break;
            StdOut.println(s); // prints our strings in random order
            count++;
        }
    }
}
