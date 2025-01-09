package com.emanuela.quoteApi

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import com.google.gson.Gson
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

data class Quote(
    val text: String,
    val author: String
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuoteApp()
        }
    }
}

@Composable
fun QuoteApp() {
    var currentQuote by remember { mutableStateOf<Quote?>(null) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        scope.launch {
            currentQuote = fetchRandomQuote()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (currentQuote == null) {
            CircularProgressIndicator()
        } else {
            QuoteCard(
                quote = currentQuote!!,
                modifier = Modifier
                    .fillMaxWidth()
                    .animateContentSize()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    scope.launch {
                        currentQuote = fetchRandomQuote()
                    }
                }
            ) {
                Text("New Quote Of The Day")
            }
        }
    }
}

@Composable
fun QuoteCard(quote: Quote, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "\"${quote.text}\"",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "- ${quote.author}",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End
            )
        }
    }
}

suspend fun fetchRandomQuote(): Quote? {
    val apiKey = BuildConfig.OPENAI_API_KEY
    Log.d("API_KEY", apiKey)
    val client = OkHttpClient()
    val jsonBody = """
        {
            "model": "gpt-3.5-turbo",
            "messages": [
                {
                    "role": "system",
                    "content": "You are an assistant that provides inspirational quotes."
                },
                {
                    "role": "user",
                    "content": "Give me a random inspirational quote with author."
                }
            ],
            "max_tokens": 50
        }
    """.trimIndent().toRequestBody("application/json".toMediaTypeOrNull())

    val request = Request.Builder()
        .url("https://api.openai.com/v1/chat/completions")
        .addHeader("Authorization", "Bearer $apiKey")
        .post(jsonBody)
        .build()

    return withContext(Dispatchers.IO) {
        try {
            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) throw Exception("Unexpected code $response")
                val responseBody = response.body?.string()
                Log.d("FetchRandomQuote", "Response Body: $responseBody")

                // Parse the response
                val json = Gson().fromJson(responseBody, OpenAIChatResponse::class.java)
                val quoteContent = json.choices.firstOrNull()?.message?.content?.trim() ?: "No quote found."
                val parts = quoteContent.split(" - ")
                Quote(
                    text = parts.getOrElse(0) { "Unknown quote" },
                    author = parts.getOrElse(1) { "Unknown author" }
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("FetchRandomQuote", "Error fetching quote", e)
            null
        }
    }
}
data class OpenAIChatResponse(
    val choices: List<Choice>
)

data class Choice(
    val message: Message
)

data class Message(
    val role: String,
    val content: String
)
