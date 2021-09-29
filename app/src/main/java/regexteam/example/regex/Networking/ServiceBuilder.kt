package regexteam.regex.Networking

import regexteam.example.regex.Utils.Utils
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {
    val interceptor = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)
    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()

    val retrofitWeather = Retrofit.Builder()
        .baseUrl(regexteam.example.regex.Utils.Utils.BaseUrlWeather)
        .client(okHttpClient).addConverterFactory(GsonConverterFactory.create())
        .build()
    val retrofitPoly = Retrofit.Builder().baseUrl(regexteam.example.regex.Utils.Utils.BaseUrlMaps).client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create()).build()

}