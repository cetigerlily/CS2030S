/* @author Celeste Cheah (Group 8H) */
class EventArrive extends Event {
  // variables
  private Queue q; 
  private Customer customer;
  private Shop shop;

  // constructor 
  public EventArrive(double time, Customer customer, Shop shop) {
    super(time); 
    this.customer = customer;
    this.shop = shop; 
  } 
   
  // methods 
  @Override
  public String toString() {
    String str = "";
    str = String.format(": C%d arrived, %s", customer.getCustomerId(), q.toString());
    return super.toString() + str;
  }

  @Override
  public Event[] simulate() { 

    if (shop.getAvailableCounter() > 0) { /* there's an available counter  */ 
      return new Event[] {
        new EventBegin(this.getTime(), this.customer, this.shop) }; 

    } else if (shop.queueIsFull()) { /* queue full, then EventDepart */
      return new Event[] {
       new EventDepart(this.getTime(), this.customer, this.shop) };
    
    } else { /* queue not full, then EventJointQueue, and use hold()  */
      return new Event[] {
       new EventJoinQueue(this.getTime(), this.customer, this.shop) };
    }
  }
}
