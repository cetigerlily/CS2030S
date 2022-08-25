/**
 * A generic box storing an item.
 * CS2030S Lab 4
 * AY21/22 Semester 2
 * @author Celeste Cheah (08H)
 */

class Box<T> {
  private final T content;
  private static final Box<?> EMPTY_BOX = new Box<>(null);

  private Box(T content) {
    this.content = content;
  }
  
  @Override
  public boolean equals(Object otherBox) {
    if (this == otherBox) {
      return true;
    }

    if (otherBox instanceof Box<?>) {
      Box<?> other = (Box<?>) otherBox;
      if (this.content == other.content) {
        return true;
      }

      if (this.content == null || other.content == null) {
        return false;
      }

      return this.content.equals(other.content);
    }

    return false; 
  }
  
  @Override
  public String toString() {
    if (this.content == null) {
      return "[]";
    } else {
      return "[" + this.content + "]";
    }
  }

  public static <T> Box<T> of(T content) {
    if (content == null) {
      return null;
    } else {
      return new Box<T>(content);
    }
  }

  public static <T> Box<T> empty() {
    @SuppressWarnings("unchecked")
    /* I'm suppressing the warning here, because EMPTY_BOX can be applied
     * to any type. In this case, we need an EMPTY_BOX of type T 
     */
    Box<T> temp = (Box<T>) EMPTY_BOX;
    return temp;
  }

  public boolean isPresent() {
    return !(this.content == null);
  }

  public static <T> Box<T> ofNullable(T content) {
    if (content == null) {
      return empty();
    } else {
      return new Box<T>(content);
    }
  }
  
  public Box<T> filter(BooleanCondition<? super T> method) {
    if (this.content == null) {
      return empty();
    } else if (method.test(this.content)) {
      return of(this.content);
    } else {
      return empty();
    }
  }

  public <U> Box<U> map(Transformer<? super T, ? extends U> transformation) { 
    if (this.content == null) {
      @SuppressWarnings("unchecked")
      /* I'm suppressing the warning here again, since EMPTY_BOX can be applied
       * to any type - so, we need an EMPTY_BOX of type U this time
       */
      Box<U> temp = (Box<U>) EMPTY_BOX;
      return temp;
    } else {
      U result = transformation.transform(this.content);
      return new Box<U>(result);
    }
  }
}
