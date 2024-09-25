package de.syntax.androidabschluss.data.remote

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import de.syntax.androidabschluss.data.model.PartyResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


//Basis-URL für die Verbindung zum Server
const val BASE_URL = "https://www.goabase.net/"
//HTTP-Logging-Interceptor zur Überwachung von Netzwerkanfragen und -antworten
private val logger: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
}
// HTTP-Client für die Kommunikation mit dem Server
private val httpClient = OkHttpClient.Builder()
    .addInterceptor(logger) // Hinzufügen des Logging-Interceptors
    .build()
// Moshi-Objekt für die JSON-Serialisierung und Deserialisierung
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory()) // Hinzufügen des KotlinJsonAdapterFactorys
    .build()

// Retrofit-Objekt für die Erstellung des API-Dienstes
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .client(httpClient)
    .build()
// Retrofit-Schnittstellen zur Definition der API-Endpunkte

interface EventsSyntaxApiService {
    @GET("api/party/json")
    suspend fun getPartylist(): PartyResponse // Methode zum Abrufen der Partyliste vom Server

    @GET("api/party/json")
    suspend fun searchPartys(@Query("name")name: String): PartyResponse // Methode zum Suchen von Partys anhand des Namens
}
// Objekt, das eine Singleton-Instanz des Retrofit-Services enthält
object EventSyntaxApi{
    val retrofitService: EventsSyntaxApiService by lazy { retrofit.create(EventsSyntaxApiService::class.java) }
}
