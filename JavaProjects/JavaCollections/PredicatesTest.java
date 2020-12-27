import java.util.*;
import java.util.function.Predicate;

/**
 * Tests Predicates class.
 */
public class PredicatesTest {

   /**
    * Makes TreeSet containing integers.
    */
   static Collection<Integer> makeSet() {
      Collection<Integer> set = new TreeSet<Integer>();
      set.add(Integer.valueOf(45));
      set.add(Integer.valueOf(12));
      set.add(Integer.valueOf(7));
      set.add(Integer.valueOf(674));
      set.add(Integer.valueOf(322));
      set.add(Integer.valueOf(19));
      set.add(Integer.valueOf(1024));
      set.add(Integer.valueOf(126));
      set.add(Integer.valueOf(345));
      set.add(Integer.valueOf(89));
      return set;
   } // end makeSet()

   /**
    * Main routine tests the Predicates class by making Collections of Integers 
    * and applying the methods from the Predicates class to them.
    */
   public static void main(String[] args) {

      Collection<Integer> coll;    

      List<Integer> result;        

      Predicate<Integer> pred = i -> (i % 2 == 0);  // Tests if even.

      coll = makeSet();
      System.out.println("Original collection of Intergers: " + coll);

      Predicates.remove(coll,pred);
      System.out.println("Remove even numbers: " + coll);

      coll = makeSet();
      Predicates.retain(coll,pred);
      System.out.println("Remove odd numbers: " + coll);

      coll = makeSet();
      result = Predicates.collect(coll,pred);
      System.out.println("Collect even numbers: " + result);

      ArrayList<Integer> list = new ArrayList<Integer>(coll);
      int i = Predicates.find(list,pred);
      System.out.println("First even number in list at index: " + i);


      pred = n -> (n > 200);        // Tests if integer is bigger than 200.

      coll = makeSet();
      System.out.println("Original collection of Intergers: " + coll);

      Predicates.remove(coll,pred);
      System.out.println("Removes numbers bigger than 200: " + coll);

      coll = makeSet();
      Predicates.retain(coll,pred);
      System.out.println("Removes numbers smaller than 201: " + coll);

      coll = makeSet();
      result = Predicates.collect(coll,pred);
      System.out.println("Collects numbers bigger than 200: " + result);

      list = new ArrayList<Integer>(coll);
      i = Predicates.find(list,pred);
      System.out.println("First number larger than 200 at index: " + i);

   } // end main()

} // end class PredicatesTest
