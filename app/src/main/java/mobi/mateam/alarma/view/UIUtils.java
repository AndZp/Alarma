package mobi.mateam.alarma.view;

import android.content.res.Resources;
import android.util.DisplayMetrics;

public class UIUtils {
  public static float convertPixelsToDp(float px) {
    DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
    float dp = px / (metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    return Math.round(dp);
  }

  public static int convertDpToPixel(float dp) {
    DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
    float px = dp * (metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    return Math.round(px);
  }
}
