/* @author Celeste Cheah (Group 8H) */

class EventJoinQueue extends Event {
  // variables
  private Queue q;
  private Customer customer;
  private Shop shop;

  // constructor
  public EventJoinQueue(double time, Customer customer, Shop shop) {
    super(time);
    this.customer = customer;
    this.shop = shop;
  }

  // methods
  @Override
  public String toString() {
    String str = "";
    str = String.format("%s", q.toString());
    return super.toString() + str;
  }

  @Override
  public Event[] simulate() {
    return new Event[] { };
  }
}
