package cs2030s.fp;

/**
 * A generic class which simulates the Lazy implementation, such that we can make "lazy" values of
 * type T which are not evaluated until they are needed.
 * 
 * @author Celeste Cheah (08H)
 * @version CS2030S Lab 6, AY21/22 Semester 2
 */

public class Lazy<T> {
  /** The producer which produces a value for the Lazy object. */
  private Producer<T> producer;

  /** The value of the Lazy object. */
  private Maybe<T> value;

  /** A private constructor which intializes this producer to the given producer and the value of 
   * the Lazy object to be None. Every Lazy object starts off with a value of None because no value
   * has been calculated yet.
   * 
   * @param producer The given producer for the Lazy object.
   */
  private Lazy(Producer<T> producer) {
    this.producer = producer;
    this.value = Maybe.none();
  }
  
  /** A method which intializes the Lazy object with the given value. It also "forces" the 
   *
   * @param v The given value, which is a value that is already calculated. 
   * @param <T> The type of the Lazy object.
   * @return Returns a new Lazy of type T which has a delayed evaluation.
   */
  public static <T> Lazy<T> of(T v) {
    Lazy<T> temp = new Lazy<>(() -> v);
    T tempValue = temp.get();
    return temp;
  }

  /** A method which takes in a Producer that produces the value when it is needed. 
   *
   * @param s The Producer which will produce a value of type T when it is needed.
   * @param <T> The type of the Lazy object.
   * @return Returns a new Lazy of type T which takes in the Producer s.
   */
  public static <T> Lazy<T> of(Producer<T> s) {
    return new Lazy<T>(s);
  }

  /** A method which gets the value of the Lazy object when needed. If the value has not been
   * calculated yet, it will compute the value. This computation will only by done once for the
   * same value.
   *
   * @return Returns the value of the Lazy object.
   */
  public T get() {
    if (this.value.equals(Maybe.none())) {
      T result = producer.produce();
      this.value = Maybe.some(result);
    }
    return this.value.get();
  }

  /** A method which takes in a Transformer, transforms the value of the Lazy object when
   * needed, and then returns a new Lazy object with the updated value.
   *
   * @param transformation The Transformer which will transform the value.
   * @param <U> The type of the transformed value, and the type of the returned Lazy object.
   * @return Returns a Lazy of type U which will compute the transformed value only when get()
   *     is called.
   */
  public <U> Lazy<U> map(Transformer<? super T, ? extends U> transformation) {
    Lazy<U> temp = Lazy.of(() -> transformation.transform(this.get()));
    return temp;
  }
  
  public <U> Lazy<U> flatMap(Transformer<? super T, ? extends Lazy<? extends U>> transformation) {
    Lazy<U> temp = Lazy.of(() -> transformation.transform(this.get()).get());
    return temp;
  }
  
  /** A method which takes in a BooleanCondition, tests to see if the value of the Lazy object
   * satisfies the condition, and then returns a new Lazy object with a boolean value. The boolean
   * value indicates whether or not the value of the Lazy object satisfies the condition.
   * 
   * @param condition The BooleanCondition which will see if the value satisfies it's condition.
   * @return Returns a Lazy which has the value of true, if the condition is satisfied, and
   *     false otherwise.
   */
  public Lazy<Boolean> filter(BooleanCondition<? super T> condition) {
    Lazy<Boolean> temp = Lazy.of(() -> condition.test(this.get())); 
    return temp;
  }
  
  /** Returns the string representation of the Lazy object.
   *
   * @return "?" if the value is not available yet;
   *         "null" if the value if null;
   *         A string of the value of the Lazy object.
   */
  @Override
  public String toString() {
    if (this.value.equals(Maybe.none())) {
      return "?";
    }

    if (this.get() == null) {
      return "null";
    }
    return String.valueOf(this.get());
  }

  /** Checks if two Lazy objects are equal with each other or not.
   * 
   * @param o The second Lazy object that we are comparing the first with.
   * @return true if they are equal;
   *         false if they are not equal.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o instanceof Lazy<?>) {
      Lazy<?> other = (Lazy<?>) o;
      if (this.get() == null || other.get() == null) {
        return false;
      }
      return this.get().equals(other.get());
    }    
    return false;
  }
    
  /** A method which combines two Lazy objects with each other using a Combiner.
   *
   * @param lazy The second lazy object which the first will be combined with.
   * @param combiner The Combiner which will combine the two Lazy objects together.
   * @param <S> The type of the second value/Lazy object to be combined.
   * @param <R> The type of the result of combining the first and second values/Lazy objects.
   * @return Returns a new Lazy of type R which will compute the combined value only when get()
   *     is called.
   */
  public <R, S> Lazy<R> combine(Lazy<S> lazy, 
      Combiner<? super T, ? super S, ? extends R> combiner) {
    Lazy<R> temp = Lazy.of(() -> combiner.combine(this.get(), lazy.get()));
    return temp;
  }
}
