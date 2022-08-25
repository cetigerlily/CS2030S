class Request {
  private int distance;
  private int passenger;
  private int time;

  public Request(int distance, int passenger, int time) {
    this.distance = distance;
    this.passenger = passenger;
    this.time = time;
  }

  public int getDistance() {
    return this.distance;
  }

  public int getPassenger() {
    return this.passenger;
  }

  public int getTime() {
    return this.time;
  }
}
