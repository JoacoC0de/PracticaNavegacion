import com.network.LoginRequest
import com.network.RegisterRequest
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Response
import com.example.practicanavegacion.model.LoginResponse
import com.example.practicanavegacion.model.Category
import com.example.practicanavegacion.model.ChatResponse
import com.example.practicanavegacion.model.Message
import com.example.practicanavegacion.model.Trabajador
import com.example.practicanavegacion.model.WorkerDetailResponse
import com.model.AppointmentResponse

import retrofit2.http.*

interface ApiService {
    @GET("client/chats")
    suspend fun getUserChats(): Response<List<ChatResponse>>
    @GET("workers/{id}")
    suspend fun getWorkerDetail(@Path("id") id: Int): WorkerDetailResponse

    @POST("client/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @POST("client/register")
    suspend fun register(@Body request: RegisterRequest): Response<Any>

    @GET("categories")
    suspend fun getCategories(): Response<List<Category>>

    @GET("categories/{id}/workers")
    suspend fun getTrabajadoresByCategory(@Path("id") categoryId: Int): Response<List<Trabajador>>

    @GET("appointments/{id}/chats")
    suspend fun getChat(@Path("id") appointmentId: Int): Response<List<Message>>

    @POST("appointments/{id}/chats")
    suspend fun sendMessage(
        @Path("id") appointmentId: Int,
        @Body message: Map<String, String>
    ): Response<Message>
    @POST("appointments")
    suspend fun createAppointment(@Body body: Map<String, Int>): Response<AppointmentResponse>
}