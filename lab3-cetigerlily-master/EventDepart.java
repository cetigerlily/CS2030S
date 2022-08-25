/* @author Celeste Cheah (Group 8H) */
class EventDepart extends Event {
  public Queue q;
  private Customer customer;
  private Shop shop;

  public EventDepart(double time, Customer customer, Shop shop) {
    super(time);
    this.customer = customer;
    this.shop = shop;
  }

  @Override
  public String toString() {
    String str = "";
    str = String.format(": C%d departed", customer.getCustomerId());
    return super.toString() + str;
  }  

  @Override
  public Event[] simulate() { 
    if (q.isEmpty()) { /* no queue, so just return event */
      return new Event[] { };
    } else { /* there's a queue, first person in queue goes to counter */
        return new Event[] {
          new EventBegin(this.getTime(), this.customer, this.shop) };
    }
  }
}
