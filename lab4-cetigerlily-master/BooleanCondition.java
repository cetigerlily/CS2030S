/**
 * A conditional statement that returns either true of false.
 * CS2030S Lab 4
 * AY21/22 Semester 2
 *
 * @author Celeste Cheah (08 H)
 */

public interface BooleanCondition<T> {
  boolean test(T t);
  /* Since all methods inside an interface are public and abstract,
   * I did not specify this for my test method.
   */
} 
