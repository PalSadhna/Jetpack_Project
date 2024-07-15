package com.example.hreactivejetpack.files

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.hreactivejetpack.R
import com.example.hreactivejetpack.model.HomeModel
import com.example.hreactivejetpack.utils.Screens
import com.example.hreactivejetpack.utils.Utils
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp


@Composable
fun HomeScreen(navHostController: NavHostController){
    SetHomeUi(navHostController)
}

@Composable
fun SetHomeUi(navHostController: NavHostController) {
    val gradientBrush = Brush.horizontalGradient(colors = listOf( colorResource(id = R.color.start_color), // Start color
        colorResource(id = R.color.end_color)  // End color
    ))

    val greenColor = colorResource(R.color.green_color)
ConstraintLayout(modifier = Modifier.verticalScroll(rememberScrollState())) {
    val (txtTime,txtDate,iconChIn,punchIn,txtPunchIn,lytHour) = createRefs()

    Text(
        text = "04:40pm",
        modifier = Modifier
            .constrainAs(txtTime) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
            .padding(top = 10.dp),
        color = Color.Black,
        fontSize = 40.sp,
        fontFamily = FontFamily(Font(R.font.poppins_semibold))
    )
    Text(text = "Friday, May 10, 2024",
        modifier = Modifier.constrainAs(txtDate) {
        top.linkTo(txtTime.bottom)
        start.linkTo(parent.start)
        end.linkTo(parent.end)
        },
        color = Color.Gray,
        fontFamily = FontFamily(Font(R.font.poppins_medium))
    )
    Box(modifier = Modifier
        .padding(top = 20.dp, bottom = 20.dp)
        .constrainAs(iconChIn) {
            top.linkTo(txtDate.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }){
        OvalGradientDrawable()
    }

    Image(painter = painterResource(id = R.drawable.punch), contentDescription = "punch", modifier = Modifier
        .constrainAs(punchIn) {
            top.linkTo(txtDate.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }
        .padding(top = 50.dp)
        .width(50.dp)
        .height(50.dp))

    Text(text = "Check In", fontSize = 10.sp, color = Color.White, fontFamily = FontFamily(Font(R.font.poppins_medium)), modifier = Modifier.constrainAs(txtPunchIn) {
        top.linkTo(punchIn.bottom)
        start.linkTo(parent.start)
        end.linkTo(parent.end)
    })
    Box(modifier = Modifier.constrainAs(lytHour) {
        top.linkTo(iconChIn.bottom)
        start.linkTo(parent.start)
        end.linkTo(parent.end)
    }){

      Row(modifier = Modifier
          .fillMaxWidth()
          .padding(top = 20.dp)) {
                ConstraintLayout(modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()) {
                    val (imgCheckIn, txtCheckIn, dataCheckIn) = createRefs()
                    Image(painter = painterResource(id = R.drawable.check_in),
                        contentDescription = "checkIn",
                        modifier = Modifier
                            .width(36.dp)
                            .height(30.dp)
                            .aspectRatio(1f)
                            .constrainAs(imgCheckIn) {
                                top.linkTo(parent.top)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            })
                    Text(
                        text = "09:40am",
                        fontSize = 15.sp,
                        color = Color.Black,
                        fontFamily = FontFamily(Font(R.font.poppins_semibold)),
                        modifier = Modifier
                            .padding(top = 5.dp)
                            .constrainAs(dataCheckIn) {
                                top.linkTo(imgCheckIn.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            })
                    Text(
                        text = "CheckIn",
                        fontSize = 12.sp,
                        color = Color.Gray,
                        fontFamily = FontFamily(Font(R.font.poppins_medium)),
                        modifier = Modifier.constrainAs(txtCheckIn) {
                            top.linkTo(dataCheckIn.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        })
                }
                    ConstraintLayout(modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()) {
                        val (imgCheckIn, txtCheckIn, dataCheckIn) = createRefs()
                        Image(painter = painterResource(id = R.drawable.check_out),
                            contentDescription = "checkIn",
                            modifier = Modifier
                                .width(30.dp)
                                .height(30.dp)
                                .aspectRatio(1f)
                                .constrainAs(imgCheckIn) {
                                    top.linkTo(parent.top)
                                    start.linkTo(parent.start)
                                    end.linkTo(parent.end)
                                })
                        Text(
                            text = "06:40am",
                            fontSize = 15.sp,
                            color = Color.Black,
                            fontFamily = FontFamily(Font(R.font.poppins_semibold)),
                            modifier = Modifier
                                .padding(top = 5.dp)
                                .constrainAs(dataCheckIn) {
                                    top.linkTo(imgCheckIn.bottom)
                                    start.linkTo(parent.start)
                                    end.linkTo(parent.end)
                                })
                        Text(
                            text = "CheckOut",
                            fontSize = 12.sp,
                            color = Color.Gray,
                            fontFamily = FontFamily(Font(R.font.poppins_medium)),
                            modifier = Modifier.constrainAs(txtCheckIn) {
                                top.linkTo(dataCheckIn.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            })

                    }
                    ConstraintLayout(modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()) {
                        val (imgCheckIn, txtCheckIn, dataCheckIn) = createRefs()
                        Image(painter = painterResource(id = R.drawable.working_hrs),
                            contentDescription = "checkIn",
                            modifier = Modifier
                                .width(30.dp)
                                .height(30.dp)
                                .aspectRatio(1f)
                                .constrainAs(imgCheckIn) {
                                    top.linkTo(parent.top)
                                    start.linkTo(parent.start)
                                    end.linkTo(parent.end)
                                })
                        Text(
                            text = "09:00",
                            fontSize = 15.sp,
                            color = Color.Black,
                            fontFamily = FontFamily(Font(R.font.poppins_semibold)),
                            modifier = Modifier
                                .padding(top = 5.dp)
                                .constrainAs(dataCheckIn) {
                                    top.linkTo(imgCheckIn.bottom)
                                    start.linkTo(parent.start)
                                    end.linkTo(parent.end)
                                })
                        Text(
                            text = "Working Hours",
                            fontSize = 12.sp,
                            color = Color.Gray,
                            fontFamily = FontFamily(Font(R.font.poppins_medium)),
                            modifier = Modifier.constrainAs(txtCheckIn) {
                                top.linkTo(dataCheckIn.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            })
                    }
                }
            }

}
}



   /* Column {
        Row {
            Card(modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .padding(10.dp), shape = RoundedCornerShape(20.dp), colors = CardDefaults.cardColors(containerColor = colorResource(
                id = R.color.app_blue_color
            )
            )) {
                Box() {
                    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)) {
                        Column(verticalArrangement = Arrangement.SpaceBetween, modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(0.6f)) {
                            Row {
                                Text(text = "Total Employee In", fontSize = 25.sp, color = colorResource(id = R.color.white), fontFamily = FontFamily(
                                    Font(R.font.poppins_semibold)
                                )
                                )
                            }
                            Row(modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 10.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                                Row(verticalAlignment = Alignment.CenterVertically) {

                                    Canvas(modifier = Modifier
                                        .size(13.dp)
                                        .padding(1.dp), onDraw = {
                                        drawCircle(color = greenColor)
                                    })
                                    Text(text = "Working", modifier = Modifier.padding(start = 5.dp), fontSize = 13.sp, color = colorResource(id = R.color.white), maxLines = 1, fontFamily = FontFamily(
                                        Font(R.font.poppins_medium)
                                    )
                                    )
                                    Text(text = "01", modifier = Modifier.padding(start = 5.dp), fontSize = 13.sp, color = colorResource(id = R.color.white), maxLines = 1, fontFamily = FontFamily(
                                        Font(R.font.poppins_medium)
                                    )
                                    )
                                }
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Canvas(modifier = Modifier
                                        .size(13.dp)
                                        .padding(1.dp), onDraw = {
                                        drawCircle(color = Color.Red)
                                    })
                                    Text(text = "Absent", modifier = Modifier.padding(start = 5.dp, end = 5.dp), fontSize = 12.sp, color = colorResource(id = R.color.white), maxLines = 1, fontFamily = FontFamily(
                                        Font(R.font.poppins_semibold)
                                    )
                                    )
                                    Text(text = "10", modifier = Modifier.padding(start = 5.dp), fontSize = 13.sp, color = colorResource(id = R.color.white), maxLines = 1, fontFamily = FontFamily(
                                        Font(R.font.poppins_medium)
                                    )
                                    )
                                }
                            }

                        }
                        Column(modifier = Modifier
                            .fillMaxHeight()
                            .padding(5.dp)) {
                            Utils.CircularProgressBar(percentage = 0.8f, number = 100)
                        }
                    }
                }
            }
        }
        Row {
            Column(
                modifier = Modifier.fillMaxWidth().padding(bottom = 90.dp)
            ) {
                LazyVerticalGrid(columns = GridCells.Fixed(3), content = {
                    items(getHomeList()){item ->
                        SetItemLayout(text = item.Title)
                    }
                })
            }
        }
    }*/

       /* Row(modifier = Modifier
            .fillMaxWidth()
            .height(85.dp)) {
            Card(shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp, bottomEnd = 0.dp, bottomStart = 0.dp),
                modifier = Modifier.fillMaxHeight(), colors = CardDefaults.cardColors(Color.White),
                elevation = CardDefaults.cardElevation(15.dp)
            ) {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp, top = 5.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Bottom) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Image(painter = painterResource(id = R.drawable.home_icon), contentDescription = "Home", modifier = Modifier
                            .height(45.dp)
                            .width(45.dp))
                        Text(text = "Home", fontFamily = FontFamily(Font(R.font.poppins_medium)), color =  colorResource(id = R.color.black), fontSize = 10.sp, textAlign = TextAlign.Center)
                        Spacer(modifier = Modifier
                            .height(2.dp)
                            .width(25.dp)
                            .padding(top = 1.dp)
                            .background(Color.Black))
                    }
                    Column(modifier = Modifier.clickable {  },  horizontalAlignment = Alignment.CenterHorizontally,) {
                        Image(painter = painterResource(id = R.drawable.add_employee_icon), contentDescription = "Add",modifier = Modifier
                            .height(45.dp)
                            .width(45.dp))
                        Text(text = "Add Employee", fontFamily = FontFamily(Font(R.font.poppins_medium)), color =  colorResource(id = R.color.black), fontSize = 10.sp, textAlign = TextAlign.Center)
                        Spacer(modifier = Modifier
                            .height(2.dp)
                            .width(25.dp)
                            .padding(top = 1.dp)
                            .background(Color.Black))
                    }
                    Column(modifier = Modifier.clickable { navHostController.navigate("Mail") },  horizontalAlignment = Alignment.CenterHorizontally) {
                        Image(painter = painterResource(id = R.drawable.mail_icon), contentDescription = "Mail",modifier = Modifier
                            .height(45.dp)
                            .width(45.dp))
                        Text(text = "Mail", fontFamily = FontFamily(Font(R.font.poppins_medium)), color =  colorResource(id = R.color.black), fontSize = 10.sp, textAlign = TextAlign.Center)
                        Spacer(modifier = Modifier
                            .height(2.dp)
                            .width(25.dp)
                            .padding(top = 1.dp)
                            .background(Color.Black))
                    }
                }
            }
        }
    }*/

@Composable
fun OvalGradientDrawable(modifier: Modifier = Modifier) {
    val brush = Brush.verticalGradient(listOf(Color(0xff783199), Color(0xffea3674)))
    val strokeColor = Color(0xFFB8C3D9) // Define the stroke color using the color code
    val strokeWidth = 9.dp // Convert stroke width to pixels

    Canvas(modifier = Modifier
        .height(130.dp)
        .fillMaxWidth()
        .padding(top = 10.dp, start = 80.dp, end = 80.dp)) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        drawCircle(
            color = strokeColor,
            center = Offset(x = canvasWidth / 2, y = canvasHeight / 2),
            radius = size.minDimension/1.9F,
        )

        drawCircle(
            brush = brush,
            center = Offset(x = canvasWidth / 2, y = canvasHeight / 2),
            radius = size.minDimension/2,

        )
    }

    /*Canvas(modifier = modifier.fillMaxWidth().height(150.dp).padding(top = 10.dp, start = 120.dp, end = 120.dp)) {
        val gradientColors = listOf(Color(0xFF3D7FDD), Color(0xFFBD85E0))
        val topLeft = Offset(10f, 10f)
        val size = 20f
        drawOval(
            brush = Brush.linearGradient(
                colors = gradientColors,
                start = topLeft,
                end = Offset(topLeft.x + size, topLeft.y + size)
            ),
            style = Stroke(width = 9.dp.toPx(), cap = StrokeCap.Round)
        )
    }*/
}

@Composable
fun SetItemLayout(text: String){
    Row(modifier = Modifier.height(150.dp)) {
        Card(shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()) {
            Box(modifier = Modifier
                .background(Color.White)
                .fillMaxWidth()
                .fillMaxHeight()) {
                Column {
                    Image(painter = painterResource(id = R.drawable.employee_icon), contentDescription = "Test", modifier = Modifier
                        .padding(top = 10.dp)
                        .fillMaxWidth()
                        .fillMaxHeight(.4f))
                    Text(text = text, modifier = Modifier
                        .padding(top = 10.dp)
                        .fillMaxWidth(), textAlign = TextAlign.Center, fontSize = 12.sp, fontFamily = FontFamily(
                        Font(R.font.poppins_medium)
                    ), color = colorResource(
                        id = R.color.black
                    )
                    )
                }

            }

        }
    }

}

fun getHomeList(): MutableList<HomeModel>{
    val list: MutableList<HomeModel> = mutableListOf()
    list.add(HomeModel("Employee Directory"))
    list.add(HomeModel("Leave Management"))
    list.add(HomeModel("Employee Calender"))
    list.add(HomeModel("Salary Statement"))
    list.add(HomeModel("Document & Evaluation"))
    list.add(HomeModel("Document & Evaluation"))
    list.add(HomeModel("Document & Evaluation"))
    list.add(HomeModel("Document & Evaluation"))
    list.add(HomeModel("Document & Evaluation"))
    list.add(HomeModel("Document & Evaluation"))
    list.add(HomeModel("Document & Evaluation"))
    list.add(HomeModel("Teams"))
    return list
}

/*
@Composable
fun Drawer(modifier: Modifier = Modifier) {
    val screens = listOf(
        DrawerScreens.Home,
        DrawerScreens.Account,
        DrawerScreens.Help
    )
    Column(
        modifier
            .fillMaxSize()
            .padding(start = 24.dp, top = 48.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.mail_icon),
            contentDescription = "App icon"
        )
        screens.forEach { screen ->
            Spacer(Modifier.height(24.dp))
            Text(
                text = screen.title,
            )
        }
    }


}*/
