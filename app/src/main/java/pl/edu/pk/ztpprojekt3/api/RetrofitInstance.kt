package pl.edu.pk.ztpprojekt3.api

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory


object RetrofitInstance {
    private const val BASE_URL = "http://10.0.2.2:8080/api/v1/"

    var gson = GsonBuilder()
        .setLenient()
        .create()

    val productService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
        .create(ProductService::class.java)
}