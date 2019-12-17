package model;

public class TimeSeries {
  private String date;
  private double spends;

  public TimeSeries(String date, double spends) {
    this.date = date;
    this.spends = spends;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public double getSpends() {
    return spends;
  }

  public void setSpends(double spends) {
    this.spends = spends;
  }

  @Override
  public String toString() {
    return "model.TimeSeries{" + "date='" + date + '\'' + ", value=" + spends + '}';
  }
}
