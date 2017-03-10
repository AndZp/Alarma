package mobi.mateam.alarma.di.module;

import android.content.Context;
import android.net.Uri;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import mobi.mateam.alarma.network.WeatherAPI;
import mobi.mateam.alarma.network.WeatherService;
import mobi.mateam.alarma.weather.model.params.WeatherParamRange;
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
    gsonBuilder.registerTypeAdapter(Uri.class, new UriAdapter());
    gsonBuilder.registerTypeAdapter(WeatherParamRange.class, new InterfaceAdapter());
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

  @Provides @Singleton WeatherService provideWeatherService(Retrofit retrofit) {
    return new WeatherService(retrofit);
  }

  public final class UriAdapter implements JsonSerializer<Uri>, JsonDeserializer<Uri> {

    @Override public Uri deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
      return Uri.parse(json.getAsString());
    }

    @Override public JsonElement serialize(Uri src, Type typeOfSrc, JsonSerializationContext context) {
      return new JsonPrimitive(src.toString());
    }
  }

  public class InterfaceAdapter implements JsonSerializer, JsonDeserializer {

    private static final String CLASSNAME = "CLASSNAME";
    private static final String DATA = "DATA";

    public Object deserialize(JsonElement jsonElement, Type type,
                              JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {

      JsonObject jsonObject = jsonElement.getAsJsonObject();
      JsonPrimitive prim = (JsonPrimitive) jsonObject.get(CLASSNAME);
      String className = prim.getAsString();
      Class klass = getObjectClass(className);
      return jsonDeserializationContext.deserialize(jsonObject.get(DATA), klass);
    }

    public JsonElement serialize(Object jsonElement, Type type, JsonSerializationContext jsonSerializationContext) {
      JsonObject jsonObject = new JsonObject();
      jsonObject.addProperty(CLASSNAME, jsonElement.getClass().getName());
      jsonObject.add(DATA, jsonSerializationContext.serialize(jsonElement));
      return jsonObject;
    }

    /****** Helper method to get the className of the object to be deserialized *****/
    public Class getObjectClass(String className) {
      try {
        return Class.forName(className);
      } catch (ClassNotFoundException e) {
        //e.printStackTrace();
        throw new JsonParseException(e.getMessage());
      }

    }
  }
}