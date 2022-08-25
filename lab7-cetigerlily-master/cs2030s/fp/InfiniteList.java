package cs2030s.fp;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/** A generic class which creates an infinite list which has elements of type T, the elements are
 * only evaluated when needed as they are implemented in a lazy manner.
 * 
 * @author Celeste Cheah (08H)
 * @version CS2030S Lab 7, AY21/22 Semester 2 
 */

public class InfiniteList<T> {
  /** The head of the InfiniteList, which is a Maybe wrapped in a Lazy. */
  private final Lazy<Maybe<T>> head;
  
  /** The tail of the InfiniteList, which is another InfiniteList wrapped in a Lazy. */
  private final Lazy<InfiniteList<T>> tail;
  
  /** A cached, single instance of a Sentinel, which represents an empty list. */
  private static final InfiniteList<?> SENTINEL = new Sentinel();

  /** A static nested class which represents a list containing no elements and is used to mark the
   * end of a list. 
   */
  private static class Sentinel extends InfiniteList<Object> {
    /** A method which returns the String represetation of a Sentinel.
     *
     * @return A String representation of a Sentinel.
     */
    @Override
    public String toString() {
      return "-";
    }

    /** A method which returns the head of a Sentinel, which doesn't exist because a Sentinel is an
     * empty list.
     * 
     * @throws NoSuchElementException Throws NoSuchElementException because there is no head of an
     *     empty list, a Sentinel.
     */
    @Override
    public Object head() {
      throw new NoSuchElementException();
    }

    /** A method which returns the tail of a Sentinel, which is a Sentinel.
     * 
     * @return Returns SENTINEL.
     */
    @Override
    public InfiniteList<Object> tail() {
      return InfiniteList.sentinel();
    }

    /** A method which takes in a Transformer, applies it to each element, and returns the
     * resulting InfinteList. Since this is applied to a Sentinel, which are empty and have no
     * elements, this method will return a Sentinel.
     * 
     * @param mapper The Transformer which will transform the elements in the InfiniteList. 
     * @param <R> The type of the transformed elements, and the resulting type of the returned
     *     Sentinel.
     * @return Returns SENTINEL.
     */
    @Override
    public <R> InfiniteList<R> map(Transformer<? super Object, ? extends R> mapper) {
      return InfiniteList.sentinel();
    }

    /** A method which filters out elements in the InfiniteList which fail to pass the given
     * BooleanCondition (it marks the elements which fail the predicate as a Maybe.none()) and 
     * then, returns the resulting lazily filtered InfiniteList. Since this is applied to a 
     * Sentinel, which are empty and have no elements, this method will return a Sentinel.
     *
     * @param predicate The BooleanCondition which elements will be tested with.
     * @return Returns SENTINEL.
     */
    @Override
    public InfiniteList<Object> filter(BooleanCondition<? super Object> predicate) {
      return InfiniteList.sentinel();
    }

    /** A method which truncates the InfiniteList into a finite list with at most, n elements.
     * A Sentinel has no elements, so limiting a Sentinel will return a Sentinel.
     * 
     * @param n A long which is the maximum length of the resulting InfiniteList.
     * @return Returns SENTINEL.
    */
    @Override
    public InfiniteList<Object> limit(long n) {
      return InfiniteList.sentinel();
    }

    /** A method which takes in a BooleanCondition, applies it to each element, and truncates the
     * list as soon as it comes across an element which evaluates the condition to false. Since
     * this is applied to a Sentinel, which are empty and have no elements, this method will
     * return a Sentinel.
     * 
     * @param predicate The BooleanCondition which elements will be tested with.
     * @return Returns SENTINEL. 
     */
    @Override
    public InfiniteList<Object> takeWhile(BooleanCondition<? super Object> predicate) {
      return InfiniteList.sentinel();
    }

    /** A method which determines the length of an InfiniteList. A Sentinel is empty and has no
     * elements, so it's length is 0.
     * 
     * @return Returns the length of a Sentinel, which is 0.
     */
    @Override
    public long count() {
      return 0L;
    }

    /** A method which implements a terminal operation such that it applies a binary operator to
     * each element in the InfiniteList - the first argument is the returned value from the
     * from the previous operation and the second argument is the current element, and returns the
     * result. A Sentinel is empty and has no elements, so applying reduce to it will only be the
     * given initial value.
     *
     * @param identity The initial value which is used as the first argument in the first 
     *     evaluation.
     * @param accumulator The Combiner which will combine the elements.
     * @param <U> The type of the resulting combination of elements.
     * @return Returns the given initial value. 
     */
    @Override
    public <U> U reduce(U identity, Combiner<U, ? super Object, U> accumulator) {
      return identity;
    }

    /** A method which returns a Sentinel as a List. 
     *
     * @return An empty list.
     */
    @Override
    public List<Object> toList() {
      List<Object> empty = List.of();
      return empty;
    }
  }

  /** A private constructor which sets the initial values of an InfiniteList as null. */
  private InfiniteList() { 
    this.head = null; 
    this.tail = null;
  }

  /** A method which initializes and creates the InfiniteList, by taking in a single Producer.
   * 
   * @param producer The Producer which will produce the values, when needed, of the elements in
   *     the InfiniteList.
   * @param <T> The type of elements in the InfiniteList.
   * @return Returns a new InfiniteList, whose elements are only evaluated when head() or tail()
   *     is called. 
   */
  public static <T> InfiniteList<T> generate(Producer<T> producer) {
    return new InfiniteList<T>(Lazy.of(() -> Maybe.some(producer.produce())),
        Lazy.of(() -> generate(producer)));
  }

  /** A method which intiializes and creates the InfiniteList, by taking in an initial value and
   * a Transformer.
   * 
   * @param seed The initial value of the InfiniteList.
   * @param next The Transformer which will transform the initial value in order to produce the
   *     following elements.
   * @param <T> The type of elements in the InfiniteList.
   * @return Returns a new InfiniteList, whose elements are only evaluated when head() or tail()
   *     is called and are determined by the previous element and the Transformer.
   */
  public static <T> InfiniteList<T> iterate(T seed, Transformer<T, T> next) {
    return new InfiniteList<T>(Lazy.of(() -> Maybe.some(seed)), 
        Lazy.of(() -> iterate(next.transform(seed), next)));
  }

  /** A private constructor which sets the value of the head and tail of an
   * InfiniteList, given the input parameters.
   * 
   * @param head The head value of the InfiniteList, it is of type T.
   * @param tail The tail value of the InfiniteList, it is an InfiniteList wrapped in a producer.
   */
  private InfiniteList(T head, Producer<InfiniteList<T>> tail) {
    this.head = Lazy.of(() -> Maybe.some(head));
    this.tail = Lazy.of(() -> tail.produce());
  }
  
  /** A private constructor which sets the value of the head and tail of an InfiniteList as the
   * corresponding input parameters.
   * 
   * @param head The head value of the InfiniteList, it is a Maybe wrapped in a Lazy.
   * @param tail The tail value of the InfiniteList, it is another InfiniteList but wrapped in a
   *     Lazy.
   */
  private InfiniteList(Lazy<Maybe<T>> head, Lazy<InfiniteList<T>> tail) {
    this.head = head;
    this.tail = tail;
  }

  /** A method which returns the value of the head of the InfiniteList. If the Maybe is a Some,
   * it simply will return the value inside the Some. If the Maybe is a None, it will produce
   * a value.
   *
   * @return Returns the head of the InfiniteList, which is of type T.
   */
  public T head() {
    return this.head.get().orElseGet(() -> this.tail.get().head());
  }

  /** A method which returns the value of the tail of the InfiniteList.
   * 
   * @return Returns the tail of the InfiniteList, which is another InfiniteList.
   */
  public InfiniteList<T> tail() {
    return this.head.get().map(x -> this.tail.get()).orElseGet(() -> this.tail.get().tail());
  }

  /** A method which takes in a Transformer, applies it to each element in the InfiniteList, and
   * returns the resulting InfiniteList. 
   * 
   * @param mapper The Transformer which will transform the elements in the InfiniteList.
   * @param <R> The type of the transformed elements, and the resulting type of the returned
   *     InfiniteList.
   * @return Returns an InfiniteList whose elements are transformed. 
   */
  public <R> InfiniteList<R> map(Transformer<? super T, ? extends R> mapper) {
    return new InfiniteList<R>(Lazy.of(() -> Maybe.some(mapper.transform(this.head()))),
        Lazy.of(() -> this.tail().map(mapper)));        
  }

  /** A method which filters out elements in the InfiniteList which fail to pass the given
   * BooleanCondition (it marks the elements which fail the predicate as a Maybe.none()) and
   * then, returns the resulting lazily filtered InfiniteList.
   *
   * @param predicate The BooleanCondition which elements will be tested with.
   * @return Returns an InfiniteList whose elements have been filtered.
   */
  public InfiniteList<T> filter(BooleanCondition<? super T> predicate) {
    return new InfiniteList<T>(Lazy.of(() -> this.head.get().filter(predicate)), 
        Lazy.of(() -> this.tail.get().filter(predicate)));
  }

  /** A method which returns a Sentinel.
   *
   * @param <T> The type of the Sentinel.
   * @return Returns a sentinel.
   */
  public static <T> InfiniteList<T> sentinel() {
    @SuppressWarnings("unchecked")
    /* I am suppressing warnings here, because SENTINEL can be applied to any type. In this case,
     * we need a SENTINEL of type InfiniteList<T>.
     */
    InfiniteList<T> temp = (InfiniteList<T>) SENTINEL;
    return temp;
  }

  /** A method which truncates the InfiniteList into a finite list with at most, the given
   * n elements. It does not count elements which were filtered out by the filter method.
   *
   * @param n A long which is the maximum length of the new finite list.
   * @return Returns a finite list of maximum length of n, from the InfiniteList.
   */
  public InfiniteList<T> limit(long n) {
    if (n <= 0) {
      return sentinel();
    }
    return new InfiniteList<T>(Lazy.of(() -> Maybe.some(this.head())),
        Lazy.of(() -> this.tail().limit(n - 1)));
  }

  /** A method which takes in a BooleanCondition, applies it to each element in the InfiniteList,
   * and truncates the list as soon as it comes across an element which evaluates the condition to
   * false. It ignores elements which have been filtered out by filter. 
   *  
   * @param predicate A BooleanCondition which evaluates whether or not an element in the list
   *     fufills the condition.
   * @return Returns an InfiniteList which consists of elements which pass the BooleanCondition's
   *     test.
   */
  public InfiniteList<T> takeWhile(BooleanCondition<? super T> predicate) { 
    Lazy<Maybe<T>> whileHead = Lazy.of(() -> Maybe.some(this.head()).filter(predicate));
    Lazy<InfiniteList<T>> whileTail = Lazy.of(() ->  whileHead.get().map(x -> 
          this.tail().takeWhile(predicate)).orElseGet(() -> sentinel()));

    return new InfiniteList<T>(whileHead, whileTail);
  }
  
  /** A method which deteremines whether a list is a Sentinel or not.
   * 
   * @return Returns true if the list is an instance of a Sentinel.
   *                 false if the list is not an instance of a Sentinel.
   */
  public boolean isSentinel() {
    return (this == SENTINEL);
  }

  /** A method which implements a terminal operation such that it applies a binary operator to
   * each element in the InfiniteList - the first argument is the returned value from the previous 
   * operation and the second argument is the current element, and returns the result.
   * 
   * @param identity The initial value which is used as the first argument in the first evalution.
   * @param accumulator The Combiner which will combine the elements.
   * @param <U> The type of resulting combination of elements.
   * @return Returns the resulting combination of elements.
   */
  public <U> U reduce(U identity, Combiner<U, ? super T, U> accumulator) {
    U initial = accumulator.combine(identity, this.head());
    return this.tail().reduce(initial, accumulator);
  }

  /** A method which determines the length of an InfiniteList.
   * 
   * @return Returns a long which is the length of the InfiniteList.
   */
  public long count() {
    InfiniteList<T> currentList = this;
    long count = 0L;

    while (!(currentList.isSentinel())) {
      count = count + 1L;
      currentList = currentList.tail();
    }
    return count;
  }

  /** A method which collects the elements in the InfiniteList and returns them in a List. 
   * It continues to add elements - using a Transformer - until a Sentinel is found, which 
   * indicates the end of the InfiniteList.
   * 
   * @return Returns a List with the elements from the InfiniteList.
   */
  public List<T> toList() {
    List<T> newList = new ArrayList<T>();
    InfiniteList<T> currentList = this;

    while (!(currentList.isSentinel())) {
      Maybe<T> currentMaybe = currentList.head.get();
      
      if (!(currentMaybe == Maybe.none())) {
        currentMaybe.map(x -> newList.add(x));
      }
      currentList = currentList.tail();
    }

    return newList;
  }

  /** A method which returns the String representation of an InfiniteList.
   * 
   * @return Returns a String representation of an InfiniteList.
   */
  @Override
  public String toString() {
    String stringHead = this.head.toString();
    String stringTail = this.tail.toString();

    return "[" + stringHead + " " + stringTail + "]";
  }
}
