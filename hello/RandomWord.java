/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {
        int count = 0;
        String res = "";
        while (!StdIn.isEmpty()) {
            String temp = StdIn.readString();
            count = count + 1;
            if (StdRandom.bernoulli((double) 1 / count)) {
                res = temp;
            }
        }
        System.out.println(res);
    }
}
