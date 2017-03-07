package mobi.mateam.alarma.network;

public class NetworkError {
  private final Throwable error;

  public NetworkError(Throwable e) {
    this.error = e;
  }
}
