package com.example.quoteoftheday

import android.os.Bundle
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
import androidx.compose.runtime.Composable
import android.content.Context

data class Quote(
    val text: String,
    val author: String
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuoteApp(this) // Pass the context to the composable function
        }
    }
}

@Composable
fun QuoteApp(context: Context) { // Receive context as a parameter
    var currentQuote by remember { mutableStateOf(getRandomQuote()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        QuoteCard(
            quote = currentQuote,
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { currentQuote = getRandomQuote() }
        ) {
            Text("New Quote Of The Day")
        }
        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { (context as? ComponentActivity)?.finish() }, // Cast context to ComponentActivity
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.error
            )
        ) {
            Text("Exit")
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

private val quotes = listOf(
    Quote("Be yourself; everyone else is already taken.", "Oscar Wilde"),
    Quote("Two things are infinite: the universe and human stupidity; and I'm not sure about the universe.", "Albert Einstein"),
    Quote("Be the change that you wish to see in the world.", "Mahatma Gandhi"),
    Quote("Live as if you were to die tomorrow. Learn as if you were to live forever.", "Mahatma Gandhi")
)

private fun getRandomQuote(): Quote = quotes.random()
