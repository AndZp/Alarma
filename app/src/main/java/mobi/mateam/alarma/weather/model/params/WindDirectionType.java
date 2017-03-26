package mobi.mateam.alarma.weather.model.params;

public enum WindDirectionType {

  N(348.76, 11.24), NNE(11.25, 33.75), NE(33.76, 56.25), ENE(56.26, 78.75), E(78.76, 101.25), ESE(101.26, 123.75), SE(123.76, 146.25), SSE(146.25,
      168.75), S(168.76, 191.25), SSW(191.26, 213.75), SW(213.76, 236.25), WSW(236.26, 258.75), W(258.76, 281.25), WNW(281.26, 303.75), NW(303.76,
      326.26), NNW(326.26, 348.75);

  private final double startDegree;
  private final double endDegree;

  WindDirectionType(double startDegree, double endDegree) {
    this.startDegree = startDegree;
    this.endDegree = endDegree;
  }

  /**
   * Creates the direction from the azimuth degrees.
   */
  public static WindDirectionType getDirection(int deg) {
    int degPositive = deg;
    if (deg < 0) {
      degPositive += (-deg / 360 + 1) * 360;
    }
    int degNormalized = degPositive % 360;
    int degRotated = degNormalized + (360 / 16 / 2);
    int sector = degRotated / (360 / 16);
    switch (sector) {
      case 0:
        return WindDirectionType.N;
      case 1:
        return WindDirectionType.NNE;
      case 2:
        return WindDirectionType.NE;
      case 3:
        return WindDirectionType.ENE;
      case 4:
        return WindDirectionType.E;
      case 5:
        return WindDirectionType.ESE;
      case 6:
        return WindDirectionType.SE;
      case 7:
        return WindDirectionType.SSE;
      case 8:
        return WindDirectionType.S;
      case 9:
        return WindDirectionType.SSW;
      case 10:
        return WindDirectionType.SW;
      case 11:
        return WindDirectionType.WSW;
      case 12:
        return WindDirectionType.W;
      case 13:
        return WindDirectionType.WNW;
      case 14:
        return WindDirectionType.NW;
      case 15:
        return WindDirectionType.NNW;
      case 16:
        return WindDirectionType.N;
    }
    return WindDirectionType.N;
  }

  public double getStartDegree() {
    return startDegree;
  }

  public double getEndDegree() {
    return endDegree;
  }

  public double getAverageDegree() {
    if (this == N) {
      return 0.0;
    }
    return (getStartDegree() + getEndDegree()) / 2;
  }

}
