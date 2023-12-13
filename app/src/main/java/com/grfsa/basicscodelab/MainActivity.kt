package com.grfsa.basicscodelab

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.grfsa.basicscodelab.ui.theme.BasicsCodelabTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BasicsCodelabTheme {
                // A surface container using the 'background' color from the theme
                MyApp(Modifier.fillMaxSize())

            }
        }
    }
}

@Composable
fun Greeting(name: String) {

    val expanded = rememberSaveable {
        mutableStateOf(false)
    }
    val extraPadding = animateDpAsState(targetValue = if (expanded.value) 48.dp else 0.dp,
        animationSpec = spring(Spring.DampingRatioMediumBouncy, Spring.StiffnessLow)
    )
    Surface(
        color = MaterialTheme.colors.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Row(modifier = Modifier.padding(24.dp)) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = extraPadding.value.coerceAtLeast(0.dp))
            ) {
                Text(text = "Hello, ")
                Text(text = name, style = MaterialTheme.typography.subtitle1.copy(
                    fontWeight = FontWeight.ExtraBold
                ))
            }
            Button(
                onClick = { expanded.value = !expanded.value }
            ) {
                Text(if (!expanded.value) "Show more" else "Show less")
            }
        }
    }
}

@Composable
fun MyApp(modifier: Modifier = Modifier) {

    var shouldShowOnboarding = remember { mutableStateOf(true) }

    Surface(modifier, color = MaterialTheme.colors.background) {
        if (shouldShowOnboarding.value) {
            OnboardingScreen(onContinueClicked = { shouldShowOnboarding.value = false })
        } else {
            Greetings()
        }
    }
}

@Composable
private fun Greetings(
    modifier: Modifier = Modifier,
    names: List<String> = List(1000) {"$it"}
) {
    LazyColumn(modifier = modifier.padding(vertical = 4.dp) ) {
        items(items = names) { name ->
            Greeting(name = name)
        }
    }
}

@Composable
fun OnboardingScreen(onContinueClicked: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(text = ("Welcome to the Basics Codelab!"))
        Button(
            modifier = Modifier.padding(vertical = 24.dp),
            onClick = onContinueClicked
        ) {
            Text("Continue")
        }
    }
}


@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun OnboardingPreview() {
    BasicsCodelabTheme {
        OnboardingScreen(onContinueClicked = {})
    }
}

@Preview(
    showBackground = true,
    widthDp = 320,
    uiMode = UI_MODE_NIGHT_YES,
    name = "Dark"
)
@Preview(showBackground = true, widthDp = 320)
@Composable
private fun GreetingsPreview() {
    BasicsCodelabTheme {
        Greetings()
    }
}

@Composable
fun MyAppPreview() {
    BasicsCodelabTheme {
        MyApp(Modifier.fillMaxSize())
    }
}