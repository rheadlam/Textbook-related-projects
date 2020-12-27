import textio.TextIO;
import java.util.TreeSet;

/**
 * This file relates to a Javanotes exercise. It's a simple "set calculator". 
 * It computes the union, intersection and set difference denoted by operators.
 * User input is two sets of non-negative integers separated by an operator.
 */

public class SetCalculator {

   public static void main(String[] args) {

      System.out.println("This program computes union, intersection and set difference.");
      System.out.println("");
      System.out.println("Example: [a,b,..,c] 'operator' [d,e,..,f]");
      System.out.println("(a,b,..,f must be non-negative integers, ");
      System.out.println("operator must be '*', '+', '-' computing");
      System.out.println("respectively: intersection, union, set");
      System.out.println("difference.) Entering nothing ends program.");

      while (true) {
         
         System.out.print("\nPlease enter set computation: ");
         TextIO.skipBlanks();
         if (TextIO.peek() == '\n') {
               // Exits program if nothing is entered.
            break;
         }
         try {
            calc(); // Reads and processes one line of input.
         }
         catch (IllegalArgumentException e) {
               
            System.out.println("Error in input: " + e.getMessage());
         }
         TextIO.getln();  // Read and discards rest of line.
                          
      }

   } // end main()


   /**
    * Read line of input and performs operation then outputs value.
    * Syntax error throws an IllegalArgumentException
    */
   private static void calc() {

      TreeSet<Integer> A, B;  // The two sets.
      char op;                // The operator.

      A = readSet(); 

      TextIO.skipBlanks();
      if (TextIO.peek() != '*' && TextIO.peek() != '+' 
                                         && TextIO.peek() != '-')
         throw new IllegalArgumentException(
         "Expected *, +, or  - after first set.");
      op = TextIO.getAnyChar(); // Reads operator.

      B = readSet(); 

      TextIO.skipBlanks();
      if (TextIO.peek() != '\n')
         throw new IllegalArgumentException("Extra unexpected input.");

      // Performs operation, modifies set A to represent
      // answer and prints in same format as input.

      if (op == '+')
         A.addAll(B);     // Union.
      else if (op == '*')
         A.retainAll(B);  // Intersection.
      else
         A.removeAll(B);  // Set difference.
      
      System.out.print("Value:  " + A);

   } // end calc()


   /**
    * Reads set from standard input, stores in TreeSet.  
    * Spaces allowed and an IllegalArgumentException is thrown
    * if input is not correct format.
    */
   private static TreeSet<Integer> readSet() {

      TreeSet<Integer> set = new TreeSet<Integer>();  // The set that will be read.

      TextIO.skipBlanks();
      if (TextIO.peek() != '[')
         throw new IllegalArgumentException("Expected '[' at start of set.");
      TextIO.getAnyChar(); // Reads '['.

      TextIO.skipBlanks();
      if (TextIO.peek() == ']') {
            // Empty set, returns value ending programme.
         TextIO.getAnyChar(); // Reads']'.
         return set;
      }

      while (true) {
            // Reads next integer adding it to set.
         TextIO.skipBlanks();
         if (! Character.isDigit(TextIO.peek()))
            throw new IllegalArgumentException("Expected an integer.");
         int n = TextIO.getInt(); // Reads integer.
         set.add(n); 
         TextIO.skipBlanks();
         if (TextIO.peek() == ']')
            break;  
         else if (TextIO.peek() == ',')
            TextIO.getAnyChar(); // Reads ','.
         else
            throw new IllegalArgumentException("Expected ',' or ']'.");
      }

      TextIO.getAnyChar(); //reads final ']'.

      return set;

   } // end readSet()


} // end class SetCalculator
