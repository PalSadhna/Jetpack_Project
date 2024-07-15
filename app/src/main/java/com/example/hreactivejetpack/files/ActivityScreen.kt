package com.example.hreactivejetpack.files

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.example.hreactivejetpack.R

@Composable
fun AddEmployeeScreen(navController: NavHostController){
    BackHandler {
        navController.navigate("Home") {
            popUpTo(navController.graph.startDestinationId) {
                inclusive = true
            }
        }
    }
    SetUi()
}

@Composable
fun SetUi(){

  ConstraintLayout(modifier = Modifier
      .verticalScroll(rememberScrollState())
      .padding(top = 10.dp)
      .fillMaxWidth()) {
   val (lytAttendance,lytLeave,lytPayslip) = createRefs()
      Row(modifier = Modifier.fillMaxSize().constrainAs(lytAttendance){
          top.linkTo(parent.top)
          start.linkTo(parent.start)
          end.linkTo(parent.end)
      }) {
          ConstraintLayout(modifier = Modifier.weight(1f).fillMaxWidth().align(Alignment.CenterVertically)) {
                     Card(shape = RoundedCornerShape(10.dp), elevation = CardDefaults.elevatedCardElevation(5.dp),modifier = Modifier.padding(10.dp)) {
                  Image(
                      painter = painterResource(id = R.drawable.attendance_xxhdpi),
                      contentDescription = "Attendance",
                      contentScale = ContentScale.FillBounds,
                      modifier = Modifier.fillMaxSize().width(100.dp).height(120.dp)
                  )
              }
          }
          ConstraintLayout(modifier = Modifier.weight(1f).fillMaxWidth().align(Alignment.CenterVertically)) {
              Card(shape = RoundedCornerShape(10.dp), elevation = CardDefaults.elevatedCardElevation(5.dp),modifier = Modifier.padding(10.dp)) {
                  Image(painter = painterResource(id = R.drawable.leave_xxhdpi), contentDescription = "leave", contentScale = ContentScale.FillBounds, modifier = Modifier.fillMaxSize().width(100.dp).height(120.dp))
              }
          }
          ConstraintLayout(modifier = Modifier.weight(1f).fillMaxWidth().align(Alignment.CenterVertically)) {
              Card(shape = RoundedCornerShape(10.dp), elevation = CardDefaults.elevatedCardElevation(5.dp),modifier = Modifier.padding(10.dp)) {
                  Image(painter = painterResource(id = R.drawable.payslip_xxhdpi), contentDescription = "Payslip", contentScale = ContentScale.FillBounds, modifier = Modifier.fillMaxSize().width(100.dp).height(120.dp))
              }
          }

      }
      Row(modifier = Modifier.fillMaxSize().constrainAs(lytLeave){
          top.linkTo(lytAttendance.bottom)
          start.linkTo(parent.start)
          end.linkTo(parent.end)
      }) {
          ConstraintLayout(modifier = Modifier.weight(1f).fillMaxWidth().align(Alignment.CenterVertically)) {
              Card(shape = RoundedCornerShape(10.dp), elevation = CardDefaults.elevatedCardElevation(5.dp),modifier = Modifier.padding(10.dp)) {
                  Image(
                      painter = painterResource(id = R.drawable.emp_directory_xxhdpi),
                      contentDescription = "Emp Directory",
                      contentScale = ContentScale.FillBounds,
                      modifier = Modifier.fillMaxSize().width(100.dp).height(120.dp)
                  )
              }
          }
          ConstraintLayout(modifier = Modifier.weight(1f).fillMaxWidth().align(Alignment.CenterVertically)) {
              Card(shape = RoundedCornerShape(10.dp), elevation = CardDefaults.elevatedCardElevation(5.dp),modifier = Modifier.padding(10.dp)) {
                  Image(painter = painterResource(id = R.drawable.holidays_xxhdpi), contentDescription = "Holidays", contentScale = ContentScale.FillBounds, modifier = Modifier.fillMaxSize().width(100.dp).height(120.dp))
              }
          }
          ConstraintLayout(modifier = Modifier.weight(1f).fillMaxWidth().align(Alignment.CenterVertically)) {
              Card(shape = RoundedCornerShape(10.dp), elevation = CardDefaults.elevatedCardElevation(5.dp),modifier = Modifier.padding(10.dp)) {
                  Image(painter = painterResource(id = R.drawable.my_assets), contentDescription = "Assets", contentScale = ContentScale.FillBounds, modifier = Modifier.fillMaxSize().width(100.dp).height(120.dp))
              }
          }

      }
  }
}