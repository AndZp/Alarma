/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package mobi.mateam.alarma.alarm;

import android.annotation.TargetApi;
import android.content.Context;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import android.os.Vibrator;
import mobi.mateam.alarma.alarm.model.Alarm;
import mobi.mateam.alarma.utils.AsyncRingtonePlayer;
import timber.log.Timber;

/**
 * Manages playing alarm ringtones and vibrating the device.
 */
final class AlarmKlaxon {

  private static final long[] VIBRATE_PATTERN = { 500, 500 };
  public static final int DURATION = 60 * 1000;

  private static boolean sStarted = false;
  private static AsyncRingtonePlayer sAsyncRingtonePlayer;

  private AlarmKlaxon() {
  }

  public static void stop(Context context) {
    if (sStarted) {
      Timber.v("AlarmKlaxon.stop()");
      sStarted = false;
      getAsyncRingtonePlayer(context).stop();
      ((Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE)).cancel();
    }
  }

  public static void start(Context context, Alarm alarm) {
    // Make sure we are stopped before starting
    stop(context);
    Timber.v("AlarmKlaxon.start()");

    if (!Uri.EMPTY.equals(alarm.mRingtone)) {
      final long crescendoDuration = DURATION;
      getAsyncRingtonePlayer(context).play(alarm.mRingtone, crescendoDuration);
    }

    if (alarm.mVibrate) {
      final Vibrator vibrator = getVibrator(context);
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        vibrateLOrLater(vibrator);
      } else {
        vibrator.vibrate(VIBRATE_PATTERN, 0);
      }
    }

    sStarted = true;
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP) private static void vibrateLOrLater(Vibrator vibrator) {
    vibrator.vibrate(VIBRATE_PATTERN, 0,
        new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_ALARM).setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build());
  }

  private static Vibrator getVibrator(Context context) {
    return ((Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE));
  }

  private static synchronized AsyncRingtonePlayer getAsyncRingtonePlayer(Context context) {
    if (sAsyncRingtonePlayer == null) {
      sAsyncRingtonePlayer = new AsyncRingtonePlayer(context.getApplicationContext());
    }

    return sAsyncRingtonePlayer;
  }
}