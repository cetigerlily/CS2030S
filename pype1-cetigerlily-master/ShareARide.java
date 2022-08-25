class ShareARide extends Service {
  @Override
  public int computeFare(Request request) {
    int distance = request.getDistance();
    int passenger = request.getPassenger();
    int time = request.getTime();
    
    int startInterval = 600;
    int endInterval = 900;

    int fareExtra = 500;
    int farePerKM = 50;
    int fare = 0;

    if (time >= startInterval && time <= endInterval) {
      fare = ((farePerKM * distance) / passenger) + fareExtra;
    } else {
      fare = (farePerKM * distance) / passenger; 
    }
    return fare;
  }

  @Override
  public String toString() {
    return "ShareARide";
  }
}
