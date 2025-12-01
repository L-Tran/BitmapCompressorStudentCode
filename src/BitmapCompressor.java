/******************************************************************************
 *  Compilation:  javac BitmapCompressor.java
 *  Execution:    java BitmapCompressor - < input.bin   (compress)
 *  Execution:    java BitmapCompressor + < input.bin   (expand)
 *  Dependencies: BinaryIn.java BinaryOut.java
 *  Data files:   q32x48.bin
 *                q64x96.bin
 *                mystery.bin
 *
 *  Compress or expand binary input from standard input.
 *
 *  % java DumpBinary 0 < mystery.bin
 *  8000 bits
 *
 *  % java BitmapCompressor - < mystery.bin | java DumpBinary 0
 *  1240 bits
 ******************************************************************************/

/**
 *  The {@code BitmapCompressor} class provides static methods for compressing
 *  and expanding a binary bitmap input.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 *  @author Zach Blick
 *  @author Logan Tran
 */
public class BitmapCompressor {

    /**
     * Reads a sequence of bits from standard input, compresses them,
     * and writes the results to standard output.
     */
    public static void compress() {

        String s = BinaryStdIn.readString();
        int n = s.length();
        BinaryStdOut.write(s.charAt(0));
        int count = 1;
        for(int i = 1; i < n; i++) {
            if(s.charAt(i) == s.charAt(i - 1)) {
                count++;
                if (count == 255) {
                    BinaryStdOut.write(count);
                    BinaryStdOut.write(0);
                }
            }
            else {
                BinaryStdOut.write(count);
                count = 0;
            }
        }

        BinaryStdOut.close();
    }

    /**
     * Reads a sequence of bits from standard input, decodes it,
     * and writes the results to standard output.
     */
    public static void expand() {
        int bit = BinaryStdIn.readInt();
        while (BinaryStdIn.isEmpty()) {
            int count = BinaryStdIn.readInt();
            for(int i = 0; i < count; i++) {
                BinaryStdOut.write(bit);
            }
            bit = (bit + 1) % 2;
        }
        BinaryStdOut.close();
    }

    /**
     * When executed at the command-line, run {@code compress()} if the command-line
     * argument is "-" and {@code expand()} if it is "+".
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        if      (args[0].equals("-")) compress();
        else if (args[0].equals("+")) expand();
        else throw new IllegalArgumentException("Illegal command line argument");
    }
}