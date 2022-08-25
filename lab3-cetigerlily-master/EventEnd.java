/* @author Celeste Cheah (Group 8H) */
class EventEnd extends Event {
  // variables
  private Customer customer;
  private Shop shop; 

  // constructor
  public EventEnd(double time, Customer customer, Shop shop) {
    super(time);
    this.customer = customer;
    this.shop = shop;
  }

  @Override
  public String toString() {
    String str = "";
    str = String.format(": C%d service done (by S%d)", customer.getCustomerId(), shop.getCounterId());
    return super.toString() + str;
  }

  @Override
  public Event[] simulate() {
    int currentCounter = shop.getAvailableCounter();
    shop.counters[currentCounter].counterAvailibility = true;
    
    return new Event[] {
      new EventDepart(this.getTime(), this.customer, this.shop) }; 
  }
}
