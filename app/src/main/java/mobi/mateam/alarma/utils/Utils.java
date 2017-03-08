package mobi.mateam.alarma.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.AnyRes;

public class Utils {
  /**
   * @param resourceId identifies an application resource
   * @return the Uri by which the application resource is accessed
   */
  public static Uri getResourceUri(Context context, @AnyRes int resourceId) {
    return new Uri.Builder().scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
        .authority(context.getPackageName())
        .path(String.valueOf(resourceId))
        .build();
  }
}
