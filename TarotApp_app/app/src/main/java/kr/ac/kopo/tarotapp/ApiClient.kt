package kr.ac.kopo.tarotapp

import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 * Java에서도 바로 구현 가능한 콜백 인터페이스
 */
interface ApiCallback {
    fun onResult(response: String?, error: Throwable?)
}

object ApiClient {
    private val client = OkHttpClient.Builder()
        .retryOnConnectionFailure(true)
        .connectTimeout(20, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .readTimeout(120, TimeUnit.SECONDS)
        .build()

    private const val BASE_URL = "http://192.168.24.183:5523"
    private var lastCall: Call? = null

    /**
     * @return 이 Call 객체를 통해 Activity 쪽에서
     *         취소(cancel)하거나 상태를 확인할 수 있습니다.
     */
    @JvmStatic
    fun infer(prompt: String, callback: ApiCallback): Call {
        val json = JSONObject().put("prompt", prompt).toString()
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val body = json.toRequestBody(mediaType)

        val request = Request.Builder()
            .url("$BASE_URL/api/infer")
            .header("Connection", "close")
            .post(body)
            .build()

        // 이전 호출 취소
        lastCall?.cancel()

        val call = client.newCall(request)
        lastCall = call

        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback.onResult(null, e)
            }

            override fun onResponse(call: Call, response: Response) {
                if (!response.isSuccessful) {
                    callback.onResult(null, IOException("Unexpected code $response"))
                } else {
                    val bodyText = response.body?.string().orEmpty()
                    // JSON 파싱
                    val text = try {
                        JSONObject(bodyText).optString("response", "")
                    } catch (e: Exception) {
                        null
                    }
                    callback.onResult(text, null)
                }
            }
        })
        return call
    }
}