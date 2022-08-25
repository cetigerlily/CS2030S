/**
 * A boolean condition with an integer parameter y that can be 
 * apply to another integer x.  Returns true if x is divisible 
 * by y, false otherwise.
 * CS2030S Lab 4
 * AY21/22 Semester 2
 *
 * @author Celeste Cheah (08H)
 */

class DivisibleBy implements BooleanCondition<Integer> {
  private int a;

  public DivisibleBy(int a) {
    this.a = a;
  }

  public boolean test(Integer t) {
    return (t % a == 0);
  }
} 
