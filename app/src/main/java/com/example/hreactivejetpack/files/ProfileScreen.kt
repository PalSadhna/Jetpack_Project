package com.example.hreactivejetpack.files

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.hreactivejetpack.R
import com.example.hreactivejetpack.model.EmployeeDetailResposne
import com.example.hreactivejetpack.model.Response.EditProfileResponse
import com.example.hreactivejetpack.utils.ApiState
import com.example.hreactivejetpack.utils.CallDatePicker
import com.example.hreactivejetpack.utils.GlobalVaribles
import com.example.hreactivejetpack.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

@Composable
fun ProfileScreen(viewModel: UserViewModel, navController: NavHostController){
    BackHandler {
        navController.navigate("Home") {
            popUpTo(navController.graph.startDestinationId) {
                inclusive = true
            }
        }
    }
    val context = LocalContext.current
     val activePersonal = CardDefaults.cardColors(containerColor = colorResource(id = R.color.active_personal_color))
     val disablePersonal = CardDefaults.cardColors(containerColor = colorResource(id = R.color.disable_personal_color))
     val activeWork = CardDefaults.cardColors(containerColor = colorResource(id = R.color.active_work_color))
     val disableWork = CardDefaults.cardColors(containerColor = colorResource(id = R.color.disable_work_color))
     val activeBank = CardDefaults.cardColors(containerColor = colorResource(id = R.color.active_bank_color))
     val disableBank = CardDefaults.cardColors(containerColor = colorResource(id = R.color.disable_bank_color))
    var isPersonalClicked by remember { mutableStateOf(true) }
    var isWorkClicked by remember { mutableStateOf(false) }
    var isBankClicked by remember { mutableStateOf(false) }
    ConstraintLayout {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, bottom = 30.dp)){
            Card(shape = RoundedCornerShape(20.dp), colors = if (isPersonalClicked) activePersonal else disablePersonal,
                onClick = {
                    isPersonalClicked = true
                    isWorkClicked = false
                    isBankClicked = false
                },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(start = 5.dp, end = 5.dp)) {
                ConstraintLayout(modifier = Modifier
                    .padding(5.dp)
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()){
                    val (profile, txtProfile) = createRefs()
                    Image(painter = painterResource(id = R.drawable.profile_personal),
                        contentDescription = "Profile",
                        modifier = Modifier
                            .width(20.dp)
                            .height(20.dp)
                            .constrainAs(profile) {
                                top.linkTo(parent.top)
                                start.linkTo(parent.start)
                                bottom.linkTo(parent.bottom)
                            })
                    Text(text = "Personal", fontSize = 15.sp, fontFamily = FontFamily(Font(R.font.poppins_medium)),color = androidx.compose.ui.graphics.Color.White, modifier = Modifier.constrainAs(txtProfile){
                        top.linkTo(parent.top)
                        start.linkTo(profile.end)
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end)
                    })
                }
            }
            Card(shape = RoundedCornerShape(20.dp),
                colors = if (isWorkClicked) activeWork else disableWork,
                onClick = {
                    isWorkClicked = true
                    isPersonalClicked = false
                    isBankClicked = false
                },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(start = 5.dp, end = 5.dp)) {
                ConstraintLayout(modifier = Modifier
                    .padding(5.dp)
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()){
                    val (profile, txtProfile) = createRefs()
                    Image(painter = painterResource(id = R.drawable.work_details),
                        contentDescription = "Profile",
                        modifier = Modifier
                            .padding(end = 5.dp)
                            .width(20.dp)
                            .height(20.dp)
                            .constrainAs(profile) {
                                top.linkTo(parent.top)
                                start.linkTo(parent.start)
                                bottom.linkTo(parent.bottom)
                            })
                    Text(text = "Work", fontSize = 15.sp,fontFamily = FontFamily(Font(R.font.poppins_medium)),color = androidx.compose.ui.graphics.Color.White, modifier = Modifier.constrainAs(txtProfile){
                        top.linkTo(parent.top)
                        start.linkTo(profile.end)
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end)
                    })
                }
            }
            Card(shape = RoundedCornerShape(20.dp),
                colors = if (isBankClicked) activeBank else disableBank,
                onClick = {
                    isBankClicked = true
                    isPersonalClicked = false
                    isWorkClicked = false
                },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(start = 5.dp, end = 5.dp)) {
                ConstraintLayout(modifier = Modifier
                    .padding(5.dp)
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()){
                    val (profile, txtProfile) = createRefs()
                    Image(painter = painterResource(id = R.drawable.bank_details),
                        contentDescription = "Profile",
                        modifier = Modifier
                            .width(20.dp)
                            .height(20.dp)
                            .constrainAs(profile) {
                                top.linkTo(parent.top)
                                start.linkTo(parent.start)
                                bottom.linkTo(parent.bottom)
                            })
                    Text(text = "Bank", fontSize = 15.sp,fontFamily = FontFamily(Font(R.font.poppins_medium)),color = Color.White, modifier = Modifier.constrainAs(txtProfile){
                        top.linkTo(parent.top)
                        start.linkTo(profile.end)
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end)
                    })
                }
            }

        }
    }
    if (isPersonalClicked){
        val usersState by viewModel.users
        LaunchedEffect(key1 = Unit) {
            viewModel.fetchUsers()
        }
        when(usersState){
            is ApiState.Loading ->{
                CircularProgressIndicator()
            }
            is ApiState.Success->{
                val response = (usersState as ApiState.Success<EmployeeDetailResposne>)
                PersonalLayout(response.data!!.result,viewModel)
            }
            is ApiState.Error ->{
                    Toast.makeText(context, "Api Fail", Toast.LENGTH_LONG).show()
            }
            else ->{
                Toast.makeText(context, "Not Success", Toast.LENGTH_LONG).show()

            }
        }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonalLayout(response: EmployeeDetailResposne.Result?,userViewModel: UserViewModel = viewModel()) {
    val context = LocalContext.current
    var firstName by remember { mutableStateOf(TextFieldValue(response!!.firstName)) }
    var lastName by remember { mutableStateOf(TextFieldValue(response!!.lastName)) }
    var userName by remember { mutableStateOf(TextFieldValue(response!!.userName)) }
    var emailId by remember { mutableStateOf(TextFieldValue(response!!.email)) }
    var mobileNo by remember { mutableStateOf(TextFieldValue(response!!.mobileNumber)) }
    var address by remember { mutableStateOf(TextFieldValue(response!!.currentAddress)) }
    var edGraduation by remember { mutableStateOf(TextFieldValue(response!!.degreeEducation)) }
    var edIntermediate by remember { mutableStateOf(TextFieldValue(response!!.intermediate)) }
    var edHigh by remember { mutableStateOf(TextFieldValue(response!!.highSchool)) }
    var isDobOpen by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf("Select Date") }
    val genderItems = listOf("Male", "Female")
    var genderSelectedItem by remember { mutableStateOf(genderItems[0]) }
    val maritalItems = listOf("Married", "Unmarried")
    var maritalSelectedItem by remember { mutableStateOf(maritalItems[0]) }
    val nationalityItems = listOf("Indian", "Foreigner")
    var nationalitySelectedItem by remember { mutableStateOf(nationalityItems[0]) }
    val editProfileResponse by userViewModel.editProfileResponse
    val responseData = editProfileResponse.data?.result
    if (responseData != null){
        firstName = TextFieldValue(responseData.firstName)
        lastName = TextFieldValue(responseData.lastName)
        emailId = TextFieldValue(responseData.email)
        mobileNo = TextFieldValue(responseData.mobileNumber)
        address = TextFieldValue(responseData.currentAddress)
        edGraduation = TextFieldValue(responseData.degreeEducation)
        edIntermediate = TextFieldValue(responseData.intermediate)
        edHigh = TextFieldValue(responseData.highSchool)
        selectedDate = responseData.dob
        if (responseData.gender == "Male"){
             genderSelectedItem = genderItems[0]
        }else{
            genderSelectedItem = genderItems[1]
        }
        if (responseData.maritalStatus == "Married"){
            maritalSelectedItem = maritalItems[0]
        }else{
            maritalSelectedItem = maritalItems[1]
        }
        if (responseData.nationality == "Indian"){
            nationalitySelectedItem = nationalityItems[0]
        }else{
            nationalitySelectedItem = nationalityItems[1]
        }
    }

   /* val apiState by userViewModel.editProfileResponse
    when(apiState) {
        is ApiState.Loading -> {
            androidx.compose.material3.CircularProgressIndicator()
        }

        is ApiState.Success -> {
            val response = (apiState as ApiState.Success<EditProfileResponse>).data
            if (response != null) {
                firstName = TextFieldValue(response.result.firstName)
            }
        }

        is ApiState.Error -> {
            val error = (apiState as ApiState.Error).message
            // Show error message
            Text(
                text = "Failed to fetch data: $error",
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            )
        }

        else -> {

        }
    }*/
    Row(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .padding(top = 70.dp, start = 20.dp, end = 20.dp, bottom = 100.dp)
        .verticalScroll(rememberScrollState())) {
        Column(
            Modifier.fillMaxSize()) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    text = "General Information's",
                    textAlign = TextAlign.Start,
                    fontSize = 16.sp,
                    color = Color.Gray,
                    fontFamily = FontFamily(Font(R.font.poppins_medium)),
                    modifier = Modifier.padding(top = 10.dp)
                )
                Card(
                    onClick = {
                        val image = GlobalVaribles.globalProfilePicture
                        println("sadhna name ${firstName.text}")
                        val file = File(image)
                        val requestFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
                        val body =
                            MultipartBody.Part.createFormData("image", file.name, requestFile)
                        val MultiFirstName =
                            RequestBody.create("text/plain".toMediaTypeOrNull(), firstName.text)
                        val MultilastName =
                            RequestBody.create("text/plain".toMediaTypeOrNull(), lastName.text)
                        val MultiselectedDate =
                            RequestBody.create("text/plain".toMediaTypeOrNull(), selectedDate)
                        val Multigender =
                            RequestBody.create("text/plain".toMediaTypeOrNull(), genderSelectedItem)
                        val MultiMarital = RequestBody.create(
                            "text/plain".toMediaTypeOrNull(),
                            maritalSelectedItem
                        )
                        val MultiNationality = RequestBody.create(
                            "text/plain".toMediaTypeOrNull(),
                            nationalitySelectedItem
                        )
                        val MultiUserName =
                            RequestBody.create("text/plain".toMediaTypeOrNull(), userName.text)
                        val MultiEmailId =
                            RequestBody.create("text/plain".toMediaTypeOrNull(), emailId.text)
                        val MultiMobileNo =
                            RequestBody.create("text/plain".toMediaTypeOrNull(), mobileNo.text)
                        val MultiAddress =
                            RequestBody.create("text/plain".toMediaTypeOrNull(), address.text)
                        val MultiGraducation =
                            RequestBody.create("text/plain".toMediaTypeOrNull(), edGraduation.text)
                        val MultiIntermidiate = RequestBody.create(
                            "text/plain".toMediaTypeOrNull(),
                            edIntermediate.text
                        )
                        val multiHigh =
                            RequestBody.create("text/plain".toMediaTypeOrNull(), edHigh.text)
                        if (image != "") {
                            userViewModel.editProfileWithImage(
                                context,
                                body,
                                MultiFirstName,
                                MultilastName,
                                MultiselectedDate,
                                Multigender,
                                MultiMarital,
                                MultiNationality,
                                MultiUserName,
                                MultiEmailId,
                                "91",
                                MultiMobileNo,
                                MultiAddress,
                                MultiGraducation,
                                MultiIntermidiate,
                                multiHigh
                            )
                        }else{
                            userViewModel.editProfile(context,firstName.text,lastName.text,selectedDate,genderSelectedItem,maritalSelectedItem,nationalitySelectedItem,userName.text,emailId.text,"91",mobileNo.text,address.text, edGraduation.text,edIntermediate.text,edHigh.text)
                        }
                    },
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier,
                    colors = CardDefaults.cardColors(
                        containerColor = colorResource(
                            id = R.color.black
                        )
                    )
                ) {
                    Text(
                        text = "Edit",
                        textAlign = TextAlign.End,
                        color = Color.White,
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.poppins_medium)),
                        modifier = Modifier
                            .padding(start = 20.dp, end = 20.dp,top = 3.dp,bottom = 3.dp)
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                ConstraintLayout(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {


                    val (lytFName, spacerFName, lytLName, spacerLName, lytDob, spacerDob, lytGender, spacerGender, lytMarital, spacerMarital,lytNationality,spacerNationality,txtContact) = createRefs()
                    val(lytUserName, spacerUName,lytEmailId,spaceEmail,lytMobile, spacerMobile,lytAddress,spacerAddress,lytEducation,lytGraduation,spacerGraduation,lytInter, spacerInter,lytHigh,spacerHigh) = createRefs()

                    Column(modifier = Modifier.constrainAs(lytFName) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    }) {
                        ConstraintLayout {
                            val (image, FirstName, txtFirstName) = createRefs()
                            Image(painter = painterResource(id = R.drawable.name_profile),
                                contentDescription = "First Name",
                                modifier = Modifier
                                    .padding(end = 20.dp)
                                    .size(30.dp)
                                    .constrainAs(image) {
                                        top.linkTo(parent.top)
                                        start.linkTo(parent.start)
                                        bottom.linkTo(parent.bottom)
                                    })
                            Column(
                                modifier = Modifier.constrainAs(FirstName) {
                                    start.linkTo(image.end, margin = 8.dp)
                                    top.linkTo(parent.top)
                                }
                            ) {
                                Text(
                                    text = "First Name",
                                    fontSize = 13.sp,
                                    color = Color.Gray,
                                    modifier = Modifier,
                                    fontFamily = FontFamily(Font(R.font.poppins_medium))
                                )
                                BasicTextField(
                                    value = firstName,
                                    onValueChange = { firstName = it },
                                    textStyle = TextStyle(
                                        fontSize = 17.sp,
                                        fontFamily = FontFamily(Font(R.font.poppins_semibold)),
                                        color = Color.Black
                                    )
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier
                        .height(0.5.dp)
                        .fillMaxWidth()
                        .background(color = Color.Gray)
                        .padding(start = 20.dp, end = 20.dp)
                        .constrainAs(spacerFName) {
                            top.linkTo(lytFName.bottom)
                            start.linkTo(parent.start)
                        })
                    Column(modifier = Modifier
                        .padding(top = 10.dp, bottom = 10.dp)
                        .constrainAs(lytLName) {
                            top.linkTo(spacerFName.bottom)
                            start.linkTo(parent.start)
                        }) {
                        ConstraintLayout {
                            val (image, FirstName) = createRefs()
                            Image(
                                painter = painterResource(id = R.drawable.name_profile),
                                contentDescription = "Last Name",
                                modifier = Modifier
                                    .padding(end = 20.dp)
                                    .size(30.dp)
                                    .constrainAs(image) {
                                        top.linkTo(parent.top)
                                        start.linkTo(parent.start)
                                        bottom.linkTo(parent.bottom)
                                    })
                            Column(
                                modifier = Modifier.constrainAs(FirstName) {
                                    start.linkTo(image.end, margin = 8.dp)
                                    top.linkTo(parent.top)
                                }
                            ) {
                                Text(
                                    text = "Last Name",
                                    fontSize = 13.sp,
                                    color = Color.Gray,
                                    modifier = Modifier,
                                    fontFamily = FontFamily(Font(R.font.poppins_medium))
                                )
                                BasicTextField(
                                    value = lastName,
                                    onValueChange = { lastName = it },
                                    textStyle = TextStyle(
                                        fontSize = 17.sp,
                                        fontFamily = FontFamily(Font(R.font.poppins_semibold)),
                                        color = Color.Black
                                    )
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier
                        .height(0.5.dp)
                        .fillMaxWidth()
                        .background(color = Color.Gray)
                        .padding(start = 20.dp, end = 20.dp)
                        .constrainAs(spacerLName) {
                            top.linkTo(lytLName.bottom)
                            start.linkTo(parent.start)
                        })
                    // start DOB
                    Column(modifier = Modifier
                        .padding(top = 10.dp, bottom = 10.dp)
                        .constrainAs(lytDob) {
                            top.linkTo(spacerLName.bottom)
                            start.linkTo(parent.start)
                        }) {
                        ConstraintLayout {
                            val (image, FirstName) = createRefs()
                            Image(
                                painter = painterResource(id = R.drawable.date_profile_icon),
                                contentDescription = "Dob",
                                modifier = Modifier
                                    .padding(end = 20.dp)
                                    .size(30.dp)
                                    .constrainAs(image) {
                                        top.linkTo(parent.top)
                                        start.linkTo(parent.start)
                                        bottom.linkTo(parent.bottom)
                                    })
                            Column(
                                modifier = Modifier.constrainAs(FirstName) {
                                    start.linkTo(image.end, margin = 8.dp)
                                    top.linkTo(parent.top)
                                }
                            ) {
                                Text(
                                    text = "DOB",
                                    fontSize = 13.sp,
                                    color = Color.Gray,
                                    modifier = Modifier.padding(bottom = 0.dp),
                                    fontFamily = FontFamily(Font(R.font.poppins_medium))
                                )

                                Text(
                                    text = selectedDate, fontSize = 17.sp, modifier = Modifier
                                        .clickable {
                                            isDobOpen = true
                                        },
                                    fontFamily = FontFamily(Font(R.font.poppins_semibold))
                                )
                            }
                            if (isDobOpen) {
                               /*  Call your date picker dialog here
                                 Example:*/
                                 CallDatePicker().CustomDatePickerDialog { userSelectedDate ->
                                     selectedDate = userSelectedDate
                                     isDobOpen = false
                                 }
                            }
                        }
                    }
                    Spacer(modifier = Modifier
                        .height(0.5.dp)
                        .fillMaxWidth()
                        .background(color = Color.Gray)
                        .padding(start = 20.dp, end = 20.dp)
                        .constrainAs(spacerDob) {
                            top.linkTo(lytDob.bottom)
                            start.linkTo(parent.start)
                        })
                    //Start Gender
                    Column(modifier = Modifier
                        .padding(top = 10.dp, bottom = 10.dp)
                        .constrainAs(lytGender) {
                            top.linkTo(spacerDob.bottom)
                            start.linkTo(parent.start)
                        }) {
                        ConstraintLayout {
                            val (image, FirstName) = createRefs()
                            Image(
                                painter = painterResource(id = R.drawable.gender),
                                contentDescription = "Gender",
                                modifier = Modifier
                                    .padding(end = 20.dp)
                                    .size(30.dp)
                                    .constrainAs(image) {
                                        top.linkTo(parent.top)
                                        start.linkTo(parent.start)
                                        bottom.linkTo(parent.bottom)
                                    })
                            Column(
                                modifier = Modifier.constrainAs(FirstName) {
                                    start.linkTo(image.end, margin = 8.dp)
                                    top.linkTo(parent.top)
                                }
                            ) {
                                Text(
                                    text = "Gender",
                                    fontSize = 13.sp,
                                    color = Color.Gray,
                                    modifier = Modifier.padding(bottom = 0.dp),
                                    fontFamily = FontFamily(Font(R.font.poppins_medium))
                                )

                                var expanded by remember { mutableStateOf(false) }

                                // Dropdown menu
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row {
                                        Text(
                                            text = genderSelectedItem,
                                            fontFamily = FontFamily(Font(R.font.poppins_semibold)),
                                            color = Color.Black,
                                            fontSize = 17.sp,
                                            modifier = Modifier.clickable {
                                                expanded = true
                                            }
                                        )
                                        DropdownMenu(
                                            expanded = expanded,
                                            onDismissRequest = { expanded = false },
                                            modifier = Modifier.width(IntrinsicSize.Min)
                                        ) {
                                            genderItems.forEach { item ->
                                                DropdownMenuItem(
                                                    onClick = {
                                                        genderSelectedItem = item
                                                        expanded = false
                                                    }
                                                ) {
                                                    Text(text = item)
                                                }
                                            }
                                        }
                                        Row {
                                            Icon(
                                                Icons.Default.ArrowDropDown, // You can replace this with your desired icon
                                                contentDescription = "Dropdown Icon",
                                                modifier = Modifier
                                                    .clickable {
                                                        expanded = true
                                                    }
                                                    .padding(horizontal = 4.dp)
                                            )
                                        }
                                    }

                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier
                        .height(0.5.dp)
                        .fillMaxWidth()
                        .background(color = Color.Gray)
                        .padding(start = 20.dp, end = 20.dp)
                        .constrainAs(spacerGender) {
                            top.linkTo(lytGender.bottom)
                            start.linkTo(parent.start)
                        })

                    // start Marital
                    Column(modifier = Modifier
                        .padding(top = 10.dp, bottom = 10.dp)
                        .constrainAs(lytMarital) {
                            top.linkTo(spacerGender.bottom)
                            start.linkTo(parent.start)
                        }) {
                        ConstraintLayout {
                            val (image, FirstName) = createRefs()
                            Image(
                                painter = painterResource(id = R.drawable.marital_status),
                                contentDescription = "Marital Status Icon",
                                modifier = Modifier
                                    .padding(end = 20.dp)
                                    .size(30.dp)
                                    .constrainAs(image) {
                                        top.linkTo(parent.top)
                                        start.linkTo(parent.start)
                                        bottom.linkTo(parent.bottom)
                                    })
                            Column(
                                modifier = Modifier.constrainAs(FirstName) {
                                    start.linkTo(image.end, margin = 8.dp)
                                    top.linkTo(parent.top)
                                }
                            ) {
                                Text(
                                    text = "Marital Status",
                                    fontSize = 13.sp,
                                    color = Color.Gray,
                                    modifier = Modifier.padding(bottom = 0.dp),
                                    fontFamily = FontFamily(Font(R.font.poppins_medium))
                                )

                                // MutableState to keep track of whether the dropdown menu is expanded
                                var expanded by remember { mutableStateOf(false) }

                                // Dropdown menu
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row {
                                        Text(
                                            text = maritalSelectedItem,
                                            fontFamily = FontFamily(Font(R.font.poppins_semibold)),
                                            color = Color.Black,
                                            fontSize = 17.sp,
                                            modifier = Modifier.clickable {
                                                expanded = true
                                            }
                                        )
                                        DropdownMenu(
                                            expanded = expanded,
                                            onDismissRequest = { expanded = false },
                                            modifier = Modifier.width(IntrinsicSize.Min)
                                        ) {
                                            maritalItems.forEach { item ->
                                                DropdownMenuItem(
                                                    onClick = {
                                                        maritalSelectedItem = item
                                                        expanded = false
                                                    }
                                                ) {
                                                    Text(text = item)
                                                }
                                            }
                                        }
                                        Row {
                                            Icon(
                                                Icons.Default.ArrowDropDown, // You can replace this with your desired icon
                                                contentDescription = "Dropdown Icon",
                                                modifier = Modifier
                                                    .clickable {
                                                        expanded = true
                                                    }
                                                    .padding(horizontal = 4.dp)
                                            )
                                        }
                                    }

                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier
                        .height(0.5.dp)
                        .fillMaxWidth()
                        .background(color = Color.Gray)
                        .padding(start = 20.dp, end = 20.dp)
                        .constrainAs(spacerMarital) {
                            top.linkTo(lytMarital.bottom)
                            start.linkTo(parent.start)
                        })

                    // Start Nationality
                    Column(modifier = Modifier
                        .padding(top = 10.dp, bottom = 10.dp)
                        .constrainAs(lytNationality) {
                            top.linkTo(spacerMarital.bottom)
                            start.linkTo(parent.start)
                        }) {
                        ConstraintLayout {
                            val (image, FirstName) = createRefs()
                            Image(
                                painter = painterResource(id = R.drawable.nationality),
                                contentDescription = "Nationality Icon",
                                modifier = Modifier
                                    .padding(end = 20.dp)
                                    .size(30.dp)
                                    .constrainAs(image) {
                                        top.linkTo(parent.top)
                                        start.linkTo(parent.start)
                                        bottom.linkTo(parent.bottom)
                                    })
                            Column(
                                modifier = Modifier.constrainAs(FirstName) {
                                    start.linkTo(image.end, margin = 8.dp)
                                    top.linkTo(parent.top)
                                }
                            ) {
                                Text(
                                    text = "Nationality",
                                    fontSize = 13.sp,
                                    color = Color.Gray,
                                    modifier = Modifier.padding(bottom = 0.dp),
                                    fontFamily = FontFamily(Font(R.font.poppins_medium))
                                )
                                var expanded by remember { mutableStateOf(false) }
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row {
                                        Text(
                                            text = nationalitySelectedItem,
                                            fontFamily = FontFamily(Font(R.font.poppins_semibold)),
                                            color = Color.Black,
                                            fontSize = 17.sp,
                                            modifier = Modifier.clickable {
                                                expanded = true
                                            }
                                        )
                                        DropdownMenu(
                                            expanded = expanded,
                                            onDismissRequest = { expanded = false },
                                            modifier = Modifier.width(IntrinsicSize.Min)
                                        ) {
                                            nationalityItems.forEach { item ->
                                                DropdownMenuItem(
                                                    onClick = {
                                                        nationalitySelectedItem = item
                                                        expanded = false
                                                    }
                                                ) {
                                                    Text(text = item)
                                                }
                                            }
                                        }
                                        Row {
                                            Icon(
                                                Icons.Default.ArrowDropDown, // You can replace this with your desired icon
                                                contentDescription = "Dropdown Icon",
                                                modifier = Modifier
                                                    .clickable {
                                                        expanded = true
                                                    }
                                                    .padding(horizontal = 4.dp)
                                            )
                                        }
                                    }

                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier
                        .height(0.5.dp)
                        .fillMaxWidth()
                        .background(color = Color.Gray)
                        .padding(start = 20.dp, end = 20.dp)
                        .constrainAs(spacerNationality) {
                            top.linkTo(lytNationality.bottom)
                            start.linkTo(parent.start)
                        })

                    // Start UserName
                    Column(modifier = Modifier
                        .padding(top = 10.dp, bottom = 10.dp)
                        .constrainAs(txtContact) {
                            top.linkTo(spacerNationality.bottom)
                            start.linkTo(parent.start)
                        }) {
                        Text(text = "Contact information", color = Color.Gray, fontFamily = FontFamily(Font(R.font.poppins_medium)))
                    }

                    Column(modifier = Modifier
                        .padding(top = 10.dp, bottom = 10.dp)
                        .constrainAs(lytUserName) {
                            top.linkTo(txtContact.bottom)
                            start.linkTo(parent.start)
                        }) {
                        ConstraintLayout {
                            val (image, UserName) = createRefs()
                            Image(
                                painter = painterResource(id = R.drawable.name_profile),
                                contentDescription = "User Name",
                                modifier = Modifier
                                    .padding(end = 20.dp)
                                    .size(30.dp)
                                    .constrainAs(image) {
                                        top.linkTo(parent.top)
                                        start.linkTo(parent.start)
                                        bottom.linkTo(parent.bottom)
                                    })
                            Column(
                                modifier = Modifier.constrainAs(UserName) {
                                    start.linkTo(image.end, margin = 8.dp)
                                    top.linkTo(parent.top)
                                }
                            ) {
                                Text(
                                    text = "User Name",
                                    fontSize = 13.sp,
                                    color = Color.Gray,
                                    modifier = Modifier.padding(bottom = 0.dp),
                                    fontFamily = FontFamily(Font(R.font.poppins_medium))
                                )
                                BasicTextField(
                                    value = userName,
                                    onValueChange = { userName = it },
                                    textStyle = TextStyle(
                                        fontSize = 17.sp,
                                        fontFamily = FontFamily(Font(R.font.poppins_semibold)),
                                        color = Color.Black
                                    )
                                )
                            }
                        }
                    }
                        Spacer(modifier = Modifier
                            .height(0.5.dp)
                            .fillMaxWidth()
                            .background(color = Color.Gray)
                            .padding(start = 20.dp, end = 20.dp)
                            .constrainAs(spacerUName) {
                                top.linkTo(lytUserName.bottom)
                                start.linkTo(parent.start)
                            })

                        // start EmailID
                        Column(modifier = Modifier
                            .padding(top = 10.dp, bottom = 10.dp)
                            .constrainAs(lytEmailId) {
                                top.linkTo(spacerUName.bottom)
                                start.linkTo(parent.start)
                            }) {
                            ConstraintLayout {
                                val (image, EmailId) = createRefs()
                                Image(
                                    painter = painterResource(id = R.drawable.email_id),
                                    contentDescription = "Email Id",
                                    modifier = Modifier
                                        .padding(end = 20.dp)
                                        .size(30.dp)
                                        .constrainAs(image) {
                                            top.linkTo(parent.top)
                                            start.linkTo(parent.start)
                                            bottom.linkTo(parent.bottom)
                                        })
                                Column(
                                    modifier = Modifier.constrainAs(EmailId) {
                                        start.linkTo(image.end, margin = 8.dp)
                                        top.linkTo(parent.top)
                                    }
                                ) {
                                    Text(
                                        text = "Email Id",
                                        fontSize = 13.sp,
                                        color = Color.Gray,
                                        modifier = Modifier.padding(bottom = 0.dp),
                                        fontFamily = FontFamily(Font(R.font.poppins_medium))
                                    )
                                    BasicTextField(
                                        value = emailId,
                                        onValueChange = { emailId = it },
                                        textStyle = TextStyle(
                                            fontSize = 17.sp,
                                            fontFamily = FontFamily(Font(R.font.poppins_semibold)),
                                            color = Color.Black
                                        )
                                    )
                                }
                            }
                    }
                    Spacer(modifier = Modifier
                        .height(0.5.dp)
                        .fillMaxWidth()
                        .background(color = Color.Gray)
                        .padding(start = 20.dp, end = 20.dp)
                        .constrainAs(spaceEmail) {
                            top.linkTo(lytEmailId.bottom)
                            start.linkTo(parent.start)
                        })

                    // start Mobile
                    Column(modifier = Modifier
                        .padding(top = 10.dp, bottom = 10.dp)
                        .constrainAs(lytMobile) {
                            top.linkTo(spaceEmail.bottom)
                            start.linkTo(parent.start)
                        }) {
                        ConstraintLayout {
                            val (image, mobile) = createRefs()
                            Image(
                                painter = painterResource(id = R.drawable.mobile_icon),
                                contentDescription = "Mobile Number",
                                modifier = Modifier
                                    .padding(end = 20.dp)
                                    .size(30.dp)
                                    .constrainAs(image) {
                                        top.linkTo(parent.top)
                                        start.linkTo(parent.start)
                                        bottom.linkTo(parent.bottom)
                                    })
                            Column(
                                modifier = Modifier.constrainAs(mobile) {
                                    start.linkTo(image.end, margin = 8.dp)
                                    top.linkTo(parent.top)
                                }
                            ) {
                                Text(
                                    text = "Mobile Number",
                                    fontSize = 13.sp,
                                    color = Color.Gray,
                                    modifier = Modifier.padding(bottom = 0.dp),
                                    fontFamily = FontFamily(Font(R.font.poppins_medium))
                                )
                                BasicTextField(
                                    value = mobileNo,
                                    onValueChange = { mobileNo = it },
                                    textStyle = TextStyle(
                                        fontSize = 17.sp,
                                        fontFamily = FontFamily(Font(R.font.poppins_semibold)),
                                        color = Color.Black
                                    )
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier
                        .height(0.5.dp)
                        .fillMaxWidth()
                        .background(color = Color.Gray)
                        .padding(start = 20.dp, end = 20.dp)
                        .constrainAs(spacerMobile) {
                            top.linkTo(lytMobile.bottom)
                            start.linkTo(parent.start)
                        })

                    // start Address
                    Column(modifier = Modifier
                        .padding(top = 10.dp, bottom = 10.dp)
                        .constrainAs(lytAddress) {
                            top.linkTo(spacerMobile.bottom)
                            start.linkTo(parent.start)
                        }) {
                        ConstraintLayout {
                            val (image, userAddress) = createRefs()
                            Image(
                                painter = painterResource(id = R.drawable.current_address),
                                contentDescription = "Address",
                                modifier = Modifier
                                    .padding(end = 20.dp)
                                    .size(30.dp)
                                    .constrainAs(image) {
                                        top.linkTo(parent.top)
                                        start.linkTo(parent.start)
                                        bottom.linkTo(parent.bottom)
                                    })
                            Column(
                                modifier = Modifier.constrainAs(userAddress) {
                                    start.linkTo(image.end, margin = 8.dp)
                                    top.linkTo(parent.top)
                                }
                            ) {
                                Text(
                                    text = "Address",
                                    fontSize = 13.sp,
                                    color = Color.Gray,
                                    modifier = Modifier.padding(bottom = 0.dp),
                                    fontFamily = FontFamily(Font(R.font.poppins_medium))
                                )
                                BasicTextField(
                                    value = address,
                                    onValueChange = { address = it },
                                    textStyle = TextStyle(
                                        fontSize = 17.sp,
                                        fontFamily = FontFamily(Font(R.font.poppins_semibold)),
                                        color = Color.Black
                                    )
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier
                        .height(0.5.dp)
                        .fillMaxWidth()
                        .background(color = Color.Gray)
                        .padding(start = 20.dp, end = 20.dp)
                        .constrainAs(spacerAddress) {
                            top.linkTo(lytAddress.bottom)
                            start.linkTo(parent.start)
                        })
                    
                    Column(modifier = Modifier
                        .padding(top = 10.dp, bottom = 10.dp)
                        .constrainAs(lytEducation) {
                            top.linkTo(spacerAddress.bottom)
                            start.linkTo(parent.start)
                        }) {
                        Text(text = "Education", color = Color.Gray, fontFamily = FontFamily(Font(R.font.poppins_medium)))
                    }

                    // start Graduation
                    Column(modifier = Modifier
                        .padding(top = 10.dp, bottom = 10.dp)
                        .constrainAs(lytGraduation) {
                            top.linkTo(lytEducation.bottom)
                            start.linkTo(parent.start)
                        }) {
                        ConstraintLayout {
                            val (image, graducation) = createRefs()
                            Image(
                                painter = painterResource(id = R.drawable.degree_icon),
                                contentDescription = "Graduation",
                                modifier = Modifier
                                    .padding(end = 20.dp)
                                    .size(30.dp)
                                    .constrainAs(image) {
                                        top.linkTo(parent.top)
                                        start.linkTo(parent.start)
                                        bottom.linkTo(parent.bottom)
                                    })
                            Column(
                                modifier = Modifier.constrainAs(graducation) {
                                    start.linkTo(image.end, margin = 8.dp)
                                    top.linkTo(parent.top)
                                }
                            ) {
                                Text(
                                    text = "Degree Education",
                                    fontSize = 13.sp,
                                    color = Color.Gray,
                                    modifier = Modifier.padding(bottom = 0.dp),
                                    fontFamily = FontFamily(Font(R.font.poppins_medium))
                                )
                                BasicTextField(
                                    value = edGraduation,
                                    onValueChange = { edGraduation = it },
                                    textStyle = TextStyle(
                                        fontSize = 17.sp,
                                        fontFamily = FontFamily(Font(R.font.poppins_semibold)),
                                        color = Color.Black
                                    )
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier
                        .height(0.5.dp)
                        .fillMaxWidth()
                        .background(color = Color.Gray)
                        .padding(start = 20.dp, end = 20.dp)
                        .constrainAs(spacerGraduation) {
                            top.linkTo(lytGraduation.bottom)
                            start.linkTo(parent.start)
                        })

                    // start Intermediate
                    Column(modifier = Modifier
                        .padding(top = 10.dp, bottom = 10.dp)
                        .constrainAs(lytInter) {
                            top.linkTo(spacerGraduation.bottom)
                            start.linkTo(parent.start)
                        }) {
                        ConstraintLayout {
                            val (image, intermidate) = createRefs()
                            Image(
                                painter = painterResource(id = R.drawable.intermediate_icon),
                                contentDescription = "Intermediate",
                                modifier = Modifier
                                    .padding(end = 20.dp)
                                    .size(30.dp)
                                    .constrainAs(image) {
                                        top.linkTo(parent.top)
                                        start.linkTo(parent.start)
                                        bottom.linkTo(parent.bottom)
                                    })
                            Column(
                                modifier = Modifier.constrainAs(intermidate) {
                                    start.linkTo(image.end, margin = 8.dp)
                                    top.linkTo(parent.top)
                                }
                            ) {
                                Text(
                                    text = "Intermediate",
                                    fontSize = 13.sp,
                                    color = Color.Gray,
                                    modifier = Modifier.padding(bottom = 0.dp),
                                    fontFamily = FontFamily(Font(R.font.poppins_medium))
                                )
                                BasicTextField(
                                    value = edIntermediate,
                                    onValueChange = { edIntermediate = it },
                                    textStyle = TextStyle(
                                        fontSize = 17.sp,
                                        fontFamily = FontFamily(Font(R.font.poppins_semibold)),
                                        color = Color.Black
                                    )
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier
                        .height(0.5.dp)
                        .fillMaxWidth()
                        .background(color = Color.Gray)
                        .padding(start = 20.dp, end = 20.dp)
                        .constrainAs(spacerInter) {
                            top.linkTo(lytInter.bottom)
                            start.linkTo(parent.start)
                        })

                    // start High Secondary
                    Column(modifier = Modifier
                        .padding(top = 10.dp, bottom = 10.dp)
                        .constrainAs(lytHigh) {
                            top.linkTo(spacerInter.bottom)
                            start.linkTo(parent.start)
                        }) {
                        ConstraintLayout {
                            val (image, high) = createRefs()
                            Image(
                                painter = painterResource(id = R.drawable.high_secondary_icon),
                                contentDescription = "High Secondary",
                                modifier = Modifier
                                    .padding(end = 20.dp)
                                    .size(30.dp)
                                    .constrainAs(image) {
                                        top.linkTo(parent.top)
                                        start.linkTo(parent.start)
                                        bottom.linkTo(parent.bottom)
                                    })
                            Column(
                                modifier = Modifier.constrainAs(high) {
                                    start.linkTo(image.end, margin = 8.dp)
                                    top.linkTo(parent.top)
                                }
                            ) {
                                Text(
                                    text = "High Secondary",
                                    fontSize = 13.sp,
                                    color = Color.Gray,
                                    modifier = Modifier.padding(bottom = 0.dp),
                                    fontFamily = FontFamily(Font(R.font.poppins_medium))
                                )
                                BasicTextField(
                                    value = edHigh,
                                    onValueChange = { edHigh = it },
                                    textStyle = TextStyle(
                                        fontSize = 17.sp,
                                        fontFamily = FontFamily(Font(R.font.poppins_semibold)),
                                        color = Color.Black
                                    )
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier
                        .height(0.5.dp)
                        .fillMaxWidth()
                        .background(color = Color.Gray)
                        .padding(start = 20.dp, end = 20.dp)
                        .constrainAs(spacerHigh) {
                            top.linkTo(lytInter.bottom)
                            start.linkTo(parent.start)
                        })

                }
            }

        }
    }
}

fun editProfile(viewModel: UserViewModel,firstName:String, lastName:String, dob:String, gender:String, maritalStatus:String, nationality:String, userName:String, email:String, countryCode:String, mobileNumber:String, currentAdd:String, degreeEducation:String, intermediate:String, highSchool:String){



}
