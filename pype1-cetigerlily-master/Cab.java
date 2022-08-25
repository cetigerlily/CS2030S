class Cab extends Car {
  private String license;
  private int timeAvailable;

  public Cab(String license, int timeAvailable) {
    super(license);
    this.timeAvailable = timeAvailable;
  }

  @Override
  public String toString() {
    if (timeAvailable > 1) {
      return "Cab " + this.getLicense() + " (" + timeAvailable + " mins away)";
    } else {
      return "Cab " + this.getLicense() + " (" + timeAvailable + " min away)";
    }
  }
}
