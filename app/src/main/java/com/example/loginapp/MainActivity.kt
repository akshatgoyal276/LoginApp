package com.example.loginapp

import android.app.Application
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.example.loginapp.ui.theme.LoginAppTheme
import kotlin.coroutines.coroutineContext

class MainActivity : ComponentActivity() {

    companion object {
        lateinit var activity: MainActivity
    }

    val str = "aks@123ggoyaal780^&!"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val freqMap = mutableMapOf<Char,Int>()

        str.forEach {
            if((Regex("[a-zA-Z]").matches(it.toString()))){
                if(freqMap[it]!=null) freqMap[it] = freqMap[it]!! + 1
                else freqMap[it]=1
            }
        }

        Log.d("akg", freqMap.toSortedMap(Comparator { o1, o2 ->
            val val1 = freqMap[o1]!!
            val val2 = freqMap[o2]!!
            if(val1 == val2) return@Comparator 1
            return@Comparator val2.compareTo(val1)
        }).toString())

        activity = this
        setContent {
            LoginAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .fillMaxWidth(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val loginId = remember {
                        mutableStateOf("")
                    }
                    if (loginId.value.isNotEmpty()) {
                        WelcomePage(id = loginId.value)
                    } else {
                        Greeting() {
                            loginId.value = it
                        }
                    }

                }
            }
        }
    }
}

fun showToast(text: String) {
    Toast.makeText(MainActivity.activity, text, Toast.LENGTH_SHORT).show()
}

@Composable
fun Greeting(modifier: Modifier = Modifier, onButtonClick: (String) -> Unit) {
    val loginId = remember {
        mutableStateOf("")
    }
    val password = remember {
        mutableStateOf("")
    }

    fun getPasswordHiddenString(): String {
        var str = ""
        password.value.forEach { str += "*" }
        return str
    }

    fun setPassword(text:String){
        password.value = text
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(Dp(50f), Dp(120f))
    ) {
        TextField(
            value = loginId.value,
            label = { Text(text = "Login Id")},
            onValueChange = {
            loginId.value = it
        }, modifier = modifier.padding(top = Dp(20f)))

        TextField(
            value = password.value,
            singleLine = true,
            label = { Text(text = "Password")},
            visualTransformation = PasswordVisualTransformation() ,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            onValueChange = {
                setPassword(it)
            },
            modifier = modifier.padding(top = Dp(20f))
        )

        Button(
            onClick = {
                if (loginId.value == password.value) onButtonClick(loginId.value)
                else showToast("Please Enter valid Login id and Password")
            }, modifier = modifier
                .fillMaxWidth()
                .padding(Dp(60f))
        ) {
            Text(text = "Login", modifier = modifier.fillMaxWidth())
        }
    }


}

@Composable
fun WelcomePage(id: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = Dp(240f))
    ) {
        Text(text = "Welcome $id", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LoginAppTheme {
        Greeting() {

        }
    }
}