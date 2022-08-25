/* @author Celeste Cheah (Group 8H) */
class EventBegin extends Event {
  // variables
  private Customer customer;
  private Shop shop;

  // constructor 
  public EventBegin(double time, Customer customer, Shop shop) {

    super(time);
    this.customer = customer;
    this.shop = shop;
  }

  @Override
  public String toString() {
    String str = "";
    str = String.format(": C%d service begin (by S%d)", customer.getCustomerId(), shop.getCounterId());
    return super.toString() + str;
  }

  @Override
  public Event[] simulate() {
    int currentCounter = shop.getAvailableCounter();
    shop.counters[currentCounter].counterAvailibility = false;

    double endTime = this.getTime() + customer.getServiceTime();
    return new Event[] {
      new EventEnd(endTime, this.customer, this.shop) };
  }
}
