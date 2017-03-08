package mobi.mateam.alarma.di.module;

import android.content.Context;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import mobi.mateam.alarma.db.DatabaseHelper;
import mobi.mateam.alarma.network.WeatherAPI;
import mobi.mateam.alarma.network.WeatherService;
import okhttp3.Cache;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Singleton @Module public class NetModule {

  @Provides @Singleton Cache provideHttpCache(Context context) {
    int cacheSize = 10 * 1024 * 1024;
    return new Cache(context.getCacheDir(), cacheSize);
  }

  @Provides @Singleton Gson provideGson() {
    GsonBuilder gsonBuilder = new GsonBuilder();
    gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
    return gsonBuilder.create();
  }

  @Provides @Singleton OkHttpClient provideOkhttpClient(Cache cache) {
    OkHttpClient.Builder client = new OkHttpClient.Builder();
    client.cache(cache);
    client.addInterceptor(chain -> {
      Request original = chain.request();
      HttpUrl originalHttpUrl = original.url();

      HttpUrl url = originalHttpUrl.newBuilder().addQueryParameter(WeatherAPI.APPID_QUERY_PARAM_NAME, WeatherAPI.WEATHER_API_ID).build();

      // Request customization: add request headers
      Request.Builder requestBuilder = original.newBuilder().url(url);

      Request request = requestBuilder.build();
      return chain.proceed(request);
    });
    return client.build();
  }

  @Provides @Singleton Retrofit provideRetrofitForWeather(Gson gson, OkHttpClient okHttpClient) {
    return new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .baseUrl(WeatherAPI.WEATHER_BASE_URL)
        .client(okHttpClient)
        .build();
  }

  @Provides @Singleton WeatherService provideWeatherService(Retrofit retrofit, DatabaseHelper databaseHelper) {
    return new WeatherService(retrofit, databaseHelper);
  }
}