package cs2030s.fp;

import java.lang.NullPointerException;
import java.util.NoSuchElementException;

/**
 * A generic abstract class which makes an option type that is a wrapper around a value that may or
 * may not be missing. 
 *
 * @author Celeste Cheah (08H)
 * @version CS2030S Lab 5, AY21/22 Semester 2
 */

public abstract class Maybe<T> {

  static class None extends Maybe<Object> {
    private static final None n = new None();
    
    @Override
    public String toString() {
      return "[]";
    }

    @Override
    public Object get() {
      throw new NoSuchElementException();
    }

    @Override
    public Maybe<Object> filter(BooleanCondition<? super Object> condition) {
      return Maybe.none();
    }

    @Override
    public <U> Maybe<U> map(Transformer<? super Object, ? extends U> transformation) {
      return Maybe.none();
    }

    @Override
    public <U> Maybe<U> flatMap(Transformer<? super Object, 
        ? extends Maybe<? extends U>> transformation) {
      return Maybe.none();
    }

    @Override
    public Object orElse(Object value) {
      return value;
    }

    @Override
    public Object orElseGet(Producer<? extends Object> producer) {
      return producer.produce();
    }
  }

  static class Some<T> extends Maybe<T> {
    private final T t;
   
    private Some(T t) {
      this.t = t;
    }

    @Override
    public String toString() {
      if (this.t == null) {
        return "[null]";
      }
      return "[" + t.toString() + "]"; 
    }

    @Override
    protected T get() {
      return this.t;
    }

    @Override
    public Maybe<T> filter(BooleanCondition<? super T> condition) {
      if (get() == null) {
        return this;
      }

      if (get() != null && condition.test(get())) {
        return this;
      } 
      return none();
    }

    @Override
    public <U> Maybe<U> map(Transformer<? super T, ? extends U> transformation) {
      try {
        U result = transformation.transform(get());
        return some(result);
      } catch (NullPointerException exception) {
        throw exception;
      }
    }

    @Override
    public <U> Maybe<U> flatMap(Transformer<? super T, 
        ? extends Maybe<? extends U>> transformation) {
      try {
        T value = get();
        @SuppressWarnings("unchecked")
        /* I'm suppressing warnings here because the return type will be a Maybe<U>, where U is the
        * result of using the transform() method on the value T.
        */
        Maybe<U> result = (Maybe<U>) transformation.transform(value);
        return result; 
      } catch (NullPointerException exception) {
        throw exception;
      }
    }

    @Override
    public T orElse(T value) {
      return get();
    }

    @Override
    public T orElseGet(Producer<? extends T> producer) {
      return get();
    }
  }

  public static <T> Maybe<T> none() {
    @SuppressWarnings("unchecked")
    /* I'm suppressing the warning here because n can be applied to any type. In this case, 
     * we need a n of the type Maybe<T>.
     */
    Maybe<T> temp = (Maybe<T>) None.n;
    return temp;
  }

  public static <T> Some<T> some(T t) {
    return new Some<T>(t);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o instanceof Maybe<?>) {
      Maybe<?> other = (Maybe<?>) o;
      
      if (this instanceof None || other instanceof None) {
        return false;
      }

      return this.toString().equals(other.toString());
    }
    return false;
  }

  public static <T> Maybe<T> of(T content) {
    if (content == null) {
      return none();
    }
    return some(content);
  }

  protected abstract T get();

  public abstract Maybe<T> filter(BooleanCondition<? super T> condition);
  
  public abstract <U> Maybe<U> map(Transformer<? super T, ? extends U> transformation);
  
  public abstract <U> Maybe<U> flatMap(Transformer<? super T, 
      ? extends Maybe<? extends U>> transformation);
  
  public abstract T  orElse(T value);

  public abstract T orElseGet(Producer<? extends T> producer); 
}
