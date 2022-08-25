class TakeACab extends Service {
  @Override
  public int computeFare(Request request) {
    int distance = request.getDistance();
    int farePerKM = 33;
    int fareExtra = 200;
    
    int fare = (farePerKM * distance) + fareExtra;
    return fare;
  }

  @Override
  public String toString() {
    return "TakeACab";
  }
}
