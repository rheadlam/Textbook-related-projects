import java.util.Collection;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * This file relates to a Javanotes exercise. The class works with
 * Collections and Predicates. 
 */
public class Predicates {

   
   /**
    * Every object in collection testing true is removed.
    */
   public static <T> void remove(Collection<T> coll, Predicate<T> pred) {
      Iterator<T> iter = coll.iterator();
      while (iter.hasNext()) {
         T item = iter.next();
         if (pred.test(item))
            iter.remove();
      }
   } // end remove()

   
   /**
    * Every object testing false is removed.
    */
   public static <T> void retain(Collection<T> coll, Predicate<T> pred){
      Iterator<T> iter = coll.iterator();
      while (iter.hasNext()) {
         T item = iter.next();
         if ( ! pred.test(item) )
            iter.remove();
      }
   } // end retain()
   
   
   /**
    *Returns list of objects testing true.
    */
   public static <T> List<T> collect(Collection<T> coll, Predicate<T> pred) {
      List<T> list = new ArrayList<T>();
      for ( T item : coll ) {
         if (pred.test(item))
            list.add(item);
      }
      return list;
   } // end collect()

   
   /**
    * Returns first item in list testing true, returns -1 if no item in list tests true.
    */
   public static <T> int find(ArrayList<T> list, Predicate<T> pred) {
      // 
      for (int i = 0; i < list.size(); i++) {
         T item = list.get(i);
         if (pred.test(item))
            return i;
      }
      return -1;
   } // end find()

   
} // end class Predicates
