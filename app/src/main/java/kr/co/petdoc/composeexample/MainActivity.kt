package kr.co.petdoc.composeexample

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AttachMoney
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
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
            .fillMaxSize()
            .padding(6.dp),
        color = Color.White
    ) {
        var tab = remember { mutableStateOf(100) }
        Column {
            Row(modifier = Modifier.height(IntrinsicSize.Min)) {
                Button(onClick = {
                    tab.value.let {
                        tab.value = it - 1
                    }
                    Log.d("Previous", "tab: ${tab.value}")
                }, modifier = Modifier.weight(1f)) {
                    Text("Previous")
                }
                Spacer(modifier = Modifier.width(10.dp))
                Button(onClick = {
                    tab.value.let {
                        tab.value = it + 1
                    }
                    Log.d("Next", "tab: ${tab.value}")
                }, modifier = Modifier.weight(1f)) {
                    Text("Next")
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            when(tab.value % 2) {
                1 -> TipCalculator()
                else -> TapMoney()
            }
        }
    }
}


// TipCalculator ------------------------------------------------------------------------------------------------------------------------------------
@Composable
fun TipCalculator() {
    Column(modifier = Modifier.fillMaxSize()) {
        TopHeader()
        TipContent()
    }
}

@Composable
fun TopHeader(totalPerPerson: Double = 0.0) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .clip(shape = RoundedCornerShape(corner = CornerSize(12.dp))),
        color = Color(color = 0xFFE9D7F7)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val total = "%.2f".format(totalPerPerson)
            Text(text = "Total Per Person", style = MaterialTheme.typography.h5)
            Text(text = "$$total", style = MaterialTheme.typography.body2, fontWeight = FontWeight.ExtraBold)
        }
    }
}

@Composable
fun InputField(
    modifier: Modifier = Modifier,
    valueState: MutableState<String>,
    labelId: String,
    enabled: Boolean,
    isSingleLine: Boolean,
    keyboardType: KeyboardType = KeyboardType.Number,
    imeAction: ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default
) {
    OutlinedTextField(value = valueState.value, onValueChange = { valueState.value = it }, label = { Text(labelId) }, leadingIcon = { Icon(imageVector = Icons.Rounded.AttachMoney, contentDescription = "Money Icon")},
    singleLine = isSingleLine,
    textStyle = TextStyle(fontSize = 18.sp, color = MaterialTheme.colors.onBackground),
    modifier = modifier.padding(bottom = 10.dp, start = 10.dp, end = 10.dp),
    enabled = enabled,
    keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
    keyboardActions = onAction)
}

@Composable
fun TipContent() {
    Surface(modifier = Modifier
        .padding(2.dp)
        .fillMaxWidth(), shape = RoundedCornerShape(corner = CornerSize(8.dp)), border = BorderStroke(width = 1.dp, color = Color.LightGray)) {
        Column() {
            Text("Hello again1")
            Text("Hello again2")
            Text("Hello again3")
        }
    }
}






// TapMoney ------------------------------------------------------------------------------------------------------------------------------------
@Composable
fun TapMoney() {
    var moneyCounter = remember { mutableStateOf(0) }

    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center, modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()) {
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
