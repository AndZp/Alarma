package mobi.mateam.alarma.di.module;

import android.content.Context;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dagger.Module;
import dagger.Provides;
import mobi.mateam.alarma.db.DatabaseHelper;
import mobi.mateam.alarma.di.anotation.PerActivity;
import mobi.mateam.alarma.network.WeatherAPI;
import mobi.mateam.alarma.network.WeatherService;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@PerActivity @Module public class NetModule {

  @Provides @PerActivity Cache provideHttpCache(Context context) {
    int cacheSize = 10 * 1024 * 1024;
    return new Cache(context.getCacheDir(), cacheSize);
  }

  @Provides @PerActivity Gson provideGson() {
    GsonBuilder gsonBuilder = new GsonBuilder();
    gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
    return gsonBuilder.create();
  }

  @Provides @PerActivity OkHttpClient provideOkhttpClient(Cache cache) {
    OkHttpClient.Builder client = new OkHttpClient.Builder();
    client.cache(cache);
    return client.build();
  }

  @Provides @PerActivity Retrofit provideRetrofitForWeather(Gson gson, OkHttpClient okHttpClient) {
    return new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .baseUrl(WeatherAPI.WEATHER_BASE_URL)
        .client(okHttpClient)
        .build();
  }

  @Provides @PerActivity WeatherService provideWeatherService(Retrofit retrofit, DatabaseHelper databaseHelper) {
    return new WeatherService(retrofit, databaseHelper);
  }
}