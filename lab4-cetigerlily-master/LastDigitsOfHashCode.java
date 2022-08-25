/**
 * A transformer with a parameter k that takes in an object x 
 * and outputs the last k digits of the hash value of x.
 * CS2030S Lab 4
 * AY21/22 Semester 2
 *
 * @author Celeste Cheah (08H)
 */

class LastDigitsOfHashCode implements Transformer<Object, Integer> {
  private int k;

  public LastDigitsOfHashCode(int k) {
    this.k = k;
  }

  public Integer transform(Object t) {
    int hashCode = t.hashCode();
    int digit = 0;
    int lastDigits = 0;

    for (int i = 0; i < k; i++) {
      digit = hashCode % 10;
      lastDigits = lastDigits + (digit * (int) Math.pow(10, i));
      hashCode = hashCode / 10;
    }

    return Math.abs(lastDigits);
  }
}
