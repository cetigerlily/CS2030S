class JustRide extends Service {
  @Override
  public int computeFare(Request request) {
    int distance = request.getDistance();
    int time = request.getTime();

    int startInterval = 600;
    int endInterval = 900;

    int fareExtra = 500;
    int farePerKM = 22;
    int fare = 0;

    if (time >= startInterval && time <= endInterval) {
      fare = (farePerKM * distance) + fareExtra;
    } else {
      fare = farePerKM * distance;
    }
    return fare;
  }

  @Override
  public String toString() {
    return "JustRide";
  }
}

