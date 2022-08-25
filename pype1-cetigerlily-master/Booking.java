class Booking implements Comparable<Booking> {
  private Car car;
  private Service service;
  private Request request;

  public Booking(Car car, Service service, Request request) throws IllegalArgumentException {
    this.car = car;
    this.service = service;
    this.request = request;
    
    if (this.car instanceof Cab && this.service instanceof ShareARide) {
      String message = car.toString() + " does not provide the ShareARide service.";
      throw new IllegalArgumentException(message);
    } else if (this.car instanceof PrivateCar && this.service instanceof TakeACab) {
      String message = car.toString() +  " does not provide the TakeACab service.";
      throw new IllegalArgumentException(message);
    }
  }

  @Override
  public int compareTo(Booking otherBooking) {
    int thisFare = this.service.computeFare(this.request);
    int otherFare = otherBooking.service.computeFare(otherBooking.request);
    int thisTime = this.request.getTime();
    int otherTime = otherBooking.request.getTime();

    if (thisFare > otherFare) {
      return 1;
    } 
    if (thisFare < otherFare) {
      return -1;
    }
    if ((thisFare  == otherFare) && (thisTime < otherTime)) {
      return -1;
    } else if ((thisFare == otherFare) && (thisTime > otherTime)) {
      return 1;
    }
    return 0;
  }
}

