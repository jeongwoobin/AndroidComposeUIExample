package kr.co.petdoc.composeexample

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dagger.hilt.android.AndroidEntryPoint
import kr.co.petdoc.composeexample.ui.theme.ComposeExampleTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeExampleTheme {
                // A surface container using the 'background' color from the theme
                MyApp()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMyApp() {
    ComposeExampleTheme {
        MyApp()
    }
}

@Composable
fun MyApp() {
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = Color.White
    ) {
        var tab = remember { mutableStateOf(0) }
        Button(onClick = {
            tab.value.minus(1)
        }) {
            Text("Previous Example")
        }
        Button(onClick = {
            tab.value.plus(1)
        }) {
            Text("Next Example")
        }
        when(tab.value % 10) {
            1 -> TipCalculator()
            else -> TapMoney()
        }
    }
}


// TipCalculator ------------------------------------------------------------------------------------------------------------------------------------
@Composable
fun TipCalculator() {
    TopHeader()
}

@Composable
fun TopHeader() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .clip(shape = RoundedCornerShape(corner = CornerSize(12.dp))),
        color = Color(color = 0xFFE9D7F7)
    ) {
        Column {
            Text(text = "Total Per Person")
            Text(text = "$134")
        }
    }
}








// TapMoney ------------------------------------------------------------------------------------------------------------------------------------
@Composable
fun TapMoney() {
    var moneyCounter = remember { mutableStateOf(0) }

    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Text(text = "$${moneyCounter.value}", style = TextStyle(color = Color.Black, fontSize = 35.sp, fontWeight = FontWeight.ExtraBold))
        Spacer(modifier = Modifier.height(130.dp))
        CreateCircle(moneyCounter = moneyCounter.value) { newValue -> moneyCounter.value = newValue + 1 }
        if(moneyCounter.value > 10) {
            Text("Over $10 !!")
        }
    }
}

@Composable
fun CreateCircle(moneyCounter: Int = 0, updateMoneyCounter: (Int) -> Unit) {
    Card(
        modifier = Modifier
            .padding(3.dp)
            .size(100.dp)
            .clickable {
                updateMoneyCounter(moneyCounter)
                Log.d("Tap", "CreateCircle : $moneyCounter")
            },
        shape = CircleShape
    ) {
        Box(contentAlignment = Alignment.Center,
        modifier = Modifier.background(color = Color.Black)) {
            Text(text = "Tap $moneyCounter", modifier = Modifier, color = Color.White)
        }
    }
}
