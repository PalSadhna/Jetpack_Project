package com.example.hreactivejetpack.activity

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.content.res.AppCompatResources
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hreactivejetpack.R
import com.example.hreactivejetpack.model.EmployeeDetailResposne
import com.example.hreactivejetpack.model.LoginModel
import com.example.hreactivejetpack.model.LoginResponse
import com.example.hreactivejetpack.ui.theme.HreactiveJetpackTheme
import com.example.hreactivejetpack.utils.ApiState
import com.example.hreactivejetpack.utils.UserDataPref
import com.example.hreactivejetpack.utils.Utils
import com.example.hreactivejetpack.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var userDataPref: UserDataPref
    private val viewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            HreactiveJetpackTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    img(viewModel)
                }
            }
        }
    }
    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        HreactiveJetpackTheme {
             img(viewModel)
        }
    }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun img(viewModel: UserViewModel){
    val drawable = AppCompatResources.getDrawable(LocalContext.current,
        R.drawable.back_img_drawable
    )

    val context = LocalContext.current
    val email = remember {
        mutableStateOf(TextFieldValue())
    }
    var isCallApi by remember {
        mutableStateOf(false)
    }
    val password = remember {
        mutableStateOf(TextFieldValue())
    }
    val gradientBrush = Brush.horizontalGradient(
        colors = listOf( colorResource(id = R.color.start_color), // Start color
            colorResource(id = R.color.end_color)  // End color
        ),
    )
    if (isCallApi){
        println("sadhna Api Call")
        if (email.value.text.isEmpty()){
            Utils.showToast(context,"Email field can't be empty")
        }else if (password.value.text.isEmpty()){
            Utils.showToast(context,"Password field can't be empty")
        }else{
            callApi(context,viewModel,email,password)
        }
        isCallApi = false
    }
    Column(modifier = Modifier.background(Color.White)) {
        Row(Modifier
                .fillMaxWidth()) {
            Box(Modifier
                .fillMaxWidth()) {
                Column(
                    Modifier
                        .width(100.dp)
                        .height(100.dp)
                        .align(Alignment.CenterStart)) {
                    Row(Modifier.paint(painterResource(id = R.drawable.back_button_transperent_icon)), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painterResource(id = R.drawable.back), contentDescription = "Back Image", modifier = Modifier
                                .padding(10.dp)
                                .height(20.dp)
                                .width(20.dp))
                    }
                }
                Column(modifier = Modifier.align(Alignment.CenterEnd)) {
                    Text(text = "Login",
                        Modifier
                            .padding(end = 20.dp, bottom = 20.dp)
                            .align(Alignment.End),
                        style = TextStyle(textAlign = TextAlign.End,
                            fontFamily = FontFamily(Font(R.font.poppins_semibold)), color = colorResource(
                                id = R.color.app_blue_color
                            ), fontSize = 20.sp))
                }
            }

        }
        Row(horizontalArrangement = Arrangement.Center) { Image(painter = painterResource(id = R.drawable.login_icon), contentDescription = "Login Image", modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .padding(10.dp))
        }
        Row {
            TextField(
                value = email.value,
                onValueChange = { email.value = it },
                label = { Text(text = "Official mail id",
                    style = TextStyle(fontFamily = FontFamily(Font(R.font.poppins_medium)), color = Color.Black, fontSize = 12.sp)
                )},
                placeholder = { Text(text = "Enter your email id")},
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done),
                textStyle = TextStyle(fontFamily = FontFamily(Font(R.font.poppins_medium)), color = Gray, fontSize = 14.sp),
                trailingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.email),
                        contentDescription = "Person Icon",
                        tint = Color.Unspecified,
                        modifier = Modifier.size(20.dp)
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = colorResource(id = R.color.sky_color)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp)
                    .border(
                        2.dp,
                        color = colorResource(id = R.color.login_edit_stroke_color),
                        shape = CircleShape
                    )
                    .background(
                        color = colorResource(id = R.color.login_edit_background),
                        shape = CircleShape
                    )
                    .shadow(4.dp, CircleShape)
            )
        }

        Row() {
            TextField(value = password.value,
                onValueChange = {
                    password.value = it
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done),
                trailingIcon = {
                               Icon(painter = painterResource(id = R.drawable.password), contentDescription = "Password Icon",
                                   tint = Color.Unspecified,
                                   modifier = Modifier.size(20.dp)
                               )
                },
                label = { Text(text = "Password",
                    style = TextStyle(fontFamily = FontFamily(Font(R.font.poppins_medium)), color = Color.Black, fontSize = 12.sp)
                )},
                placeholder = { Text(text = "Enter your password")},
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = colorResource(id = R.color.sky_color),
                ),
                textStyle = TextStyle(fontFamily = FontFamily(Font(R.font.poppins_medium)), color = Gray, fontSize = 14.sp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, top = 10.dp)
                    .border(
                        2.dp,
                        color = colorResource(id = R.color.login_edit_stroke_color),
                        shape = CircleShape
                    )
                    .shadow(4.dp, CircleShape)
            )
        }

        Row(modifier = Modifier
            .padding(top = 10.dp)
            .fillMaxWidth()
            .height(70.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween) {
            Button(
                onClick = {
                    isCallApi = true
                          },
                colors = ButtonDefaults.buttonColors(containerColor = Color.White)
            ) {
               Box(modifier = Modifier
                   .height(70.dp)
                   .fillMaxWidth()
                   .background(gradientBrush, RoundedCornerShape(20.dp))
                   ){
                        Text(text = "LOGIN", fontSize = 20.sp, modifier = Modifier.align(Alignment.Center), color = White)
                        Icon(
                            painterResource(id = R.drawable.next_arrow),
                            contentDescription = "Forward Icon",
                            tint = White,
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .height(20.dp)
                                .width(20.dp)
                                .align(Alignment.CenterEnd)
                        )
                }
            }
        }

        Row(modifier = Modifier
            .align(Alignment.End)
            .padding(end = 30.dp)) {
            Text(
                text = "Forgot Password",
                fontSize = 12.sp,
                color = Color.Red,
                fontFamily = FontFamily(Font(R.font.poppins_semibold))
            )
        }

        Row(modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .padding(top = 20.dp, start = 10.dp, end = 10.dp)) {
            Column( modifier = Modifier.width(50.dp)) {
              Divider(thickness = 1.dp, color = Color.Blue)
            }
            Column() {
                Text(text = "OR", fontFamily = FontFamily(Font(R.font.poppins_semibold)), color = Blue)
            }
            Column( modifier = Modifier.width(50.dp)) {
                Divider(thickness = 1.dp, color = Color.Blue)
            }
        }


        Row(modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .weight(1f)) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()) {
                Image(painter = painterResource(id = R.drawable.login_bottom_left_icon), contentDescription = "Bottom", modifier = Modifier
                    .align(Alignment.CenterStart)
                    .width(100.dp)
                    .height(100.dp))
                Row(modifier = Modifier
                    .padding(top = 20.dp, start = 100.dp, end = 100.dp)
                    .align(Alignment.Center)
                    .fillMaxWidth()) {
                    Column {
                        Text(text = "Don't have an account?", fontSize = 12.sp, color = Black, fontFamily = FontFamily(Font(
                            R.font.josefin_sans_medium
                        )))
                    }
                    Column {
                        Text(text = "Register", fontSize = 12.sp, color = colorResource(id = R.color.app_blue_color), fontFamily = FontFamily(Font(
                            R.font.poppins_semibold
                        )))
                    }
                }
                Image(painter = painterResource(id = R.drawable.login_right_icon), contentDescription = "Bottomnew", modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .width(100.dp))
            }
        }

    }

}

@Composable
fun callApi(context: Context, viewModel: UserViewModel, email: MutableState<TextFieldValue>, password: MutableState<TextFieldValue>) {
    println("sadhna Call Api")
    val apiResponse by viewModel.loginResponse.observeAsState(emptyList<>())
    LaunchedEffect(key1 = Unit) {
        val userModel = LoginModel(email.value.text,password.value.text,"296177D1-FFF1-4AF5-AECB-CB2C4D595474","Delhi","","")
        viewModel.userLogin(context,userModel)
    }
/*    viewModel.loginResponse.observe(this) { response ->
        when (response) {
            is ApiState.Loading -> {
                println("sadhna Api Load")
                Utils.showToast(context, "Loading")
            }

            is ApiState.Success -> {
                println("sadhna Api Sucess")
               // Utils.showToast(context, "Login Success")
                context.startActivity(Intent(context, HomePage::class.java))
            }

            is ApiState.Error -> {
                println("sadhna Api Error")
                // Show error message
               // Utils.showToast(context, "Error")
            }

            else -> {}
        }
    }*/
}


}




