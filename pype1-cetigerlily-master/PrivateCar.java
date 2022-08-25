class PrivateCar extends Car {
  private String license;
  private int timeAvailable;

  public PrivateCar(String license, int timeAvailable) {
    super(license);
    this.timeAvailable = timeAvailable;
  }

  @Override
  public String toString() {
    if (timeAvailable > 1) {
      return "PrivateCar " + this.getLicense() + " (" + timeAvailable + " mins away)";
    } else {
      return "PrivateCar " + this.getLicense() + " (" + timeAvailable + " min away)";
    }
  }
}

