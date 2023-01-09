/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

public class HelloGoodbye {
    public static void main(String[] args) {
        System.out.print("Hello ");
        for (int i = 0; i < args.length; i = i + 1) {
            System.out.print(args[i]);
            if (i < args.length - 1) {
                System.out.print(" and ");
            }
        }
        System.out.print("\nGoodbye ");
        for (int i = args.length - 1; i >= 0; i = i - 1) {
            System.out.print(args[i]);
            if (i > 0) {
                System.out.print(" and ");
            }
        }
    }
}
