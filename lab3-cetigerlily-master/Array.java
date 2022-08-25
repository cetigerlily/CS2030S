/**
 * The Array<T> for CS2030S 
 *
 * @author Celeste Cheah
 * @version CS2030S AY21/22 Semester 2
 */

class Array<T extends Comparable<T>> {
  private T[] array;

  Array(int size) {
    @SuppressWarnings("unchecked")
    /* For our generic array to work, we must add this SuppressWarning since type T
       won't be known at runtime, causing an unchecked error. However, we know that 
       we will be passing the correct type T into the only applicable function - 
       enq(). Since we know it will be type T, we are allowed to use this SuppressWarning.
     */
    T[] a = (T[]) new Object[size];
    this.array = a;
  }

  public void set(int index, T item) {
    this.array[index] = item;
  }

  public T get(int index) {
    return this.array[index];
  }

  public T min() {
    int compare = 0;
    int index = 0;

    for (int i = 0; i < array.length; i++) {
      index = i;
      compare = get(0).compareTo(get(i));
      if (compare < 0) { /* if it is smaller than the first element */
        break;
      }
    }
    return get(index); /* returns an item T which is smaller than the first */
  }

  @Override
  public String toString() {
    StringBuilder s = new StringBuilder("[ ");
    for (int i = 0; i < array.length; i++) {
      s.append(i + ":" + array[i]);
      if (i != array.length - 1) {
        s.append(", ");
      }
    }
    return s.append(" ]").toString();
  }
}
