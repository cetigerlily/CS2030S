/**
 * Takes an item and return the item in a box.
 * CS2030S Lab 4
 * AY21/22 Semester 2
 *
 * @author Celeste Cheah (08H)
 */

class BoxIt<T> implements Transformer<T, Box<T>> { 
  public BoxIt() {
  }

  public Box<T> transform(T content) {
    Box<T> box = (Box<T>) Box.of(content);
    return box;
  }
}
