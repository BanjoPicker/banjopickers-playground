package tschumacher.playground;

public class PermutationPrinter {

    public static void PrintPermutations(String acc, String s) {
        if(s.length() <= 1) {
            System.out.println(acc + s);
        } else {
            for(int i=0;i<s.length();i++) {
                String c = s.substring(i,i+1);
                String t = s.substring(0,i) + s.substring(i+1);
                PrintPermutations(acc + c, t);
            }
        }
    }


    public static void main(String args[]) {
        String word = args[0];
        PrintPermutations("", word);       
    }
};
