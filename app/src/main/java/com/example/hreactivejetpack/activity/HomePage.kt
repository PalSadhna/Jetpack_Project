package com.example.hreactivejetpack.activity

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.media.Image
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ImageView.ScaleType
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.DrawerValue
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ModalDrawer
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.rememberDrawerState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.example.hreactivejetpack.NavigationItem
import com.example.hreactivejetpack.R
import com.example.hreactivejetpack.files.ActivityScreen
import com.example.hreactivejetpack.files.HomeScreen
import com.example.hreactivejetpack.files.MailScreen
import com.example.hreactivejetpack.files.PersonalLayout
import com.example.hreactivejetpack.files.ProfileScreen
import com.example.hreactivejetpack.model.EmployeeDetailResposne
import com.example.hreactivejetpack.model.HomeModel
import com.example.hreactivejetpack.ui.theme.HreactiveJetpackTheme
import com.example.hreactivejetpack.utils.ApiState
import com.example.hreactivejetpack.utils.FileUriUtils
import com.example.hreactivejetpack.utils.GlobalVaribles
import com.example.hreactivejetpack.utils.UserDataPref
import com.example.hreactivejetpack.utils.Utils
import com.example.hreactivejetpack.viewmodel.UserViewModel
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class HomePage : ComponentActivity() {
    lateinit var navController: NavHostController
    val screens = listOf("Home", "AddEmployee", "Mail")
    var homeSpacer by mutableStateOf(true)
    var addEmployeeSpacer by mutableStateOf(false)
    var mailSpacer by mutableStateOf(false)
    var activitySpacer by mutableStateOf(false)
    private val viewModel: UserViewModel by viewModels()
    var imageUri by mutableStateOf<Uri?>(null)
    @Inject
    lateinit var userDataPref: UserDataPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_home_page)
        setContent {
            HreactiveJetpackTheme {
                SetLayout()
            }
        }
    }

    private var permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){ map ->
        var isGranted = true
        for (items in map){
            if (!items.value){
                isGranted = false
            }
        }
        if (!isGranted){
            Toast.makeText(this,"Permission Not Granted", Toast.LENGTH_SHORT).show()
        } else{
            selectDocument()
        }
    }

    private val pickPdfLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
                result.data?.data?.let {
                    val path = FileUriUtils.getRealPath(this, it).toString()
                    GlobalVaribles.globalProfilePicture = path
                    imageUri = Uri.parse(path)
                }
            }
    }

    private fun selectDocument() {
        val documentIntent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            type = "image/*"
            addCategory(Intent.CATEGORY_OPENABLE)
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        pickPdfLauncher.launch(documentIntent)
    }
    @Composable
    fun SetLayout() {
         navController = rememberNavController()
        val scaffoldState = rememberScaffoldState()
        val gradientBrush = Brush.horizontalGradient(colors = listOf( colorResource(id = R.color.start_color), // Start color
            colorResource(id = R.color.end_color)  // End color
        ))
        Scaffold(
            scaffoldState = scaffoldState,
            topBar = {
                TopBar(scaffoldState = scaffoldState)
            },
            bottomBar = {
                BottomView(navController)
            },
            content = {
                Body()
            },
            drawerContent = {
                CustomNavigationDrawer(navController)
            },
        )
    }

    @Composable
    fun Body() {
        val drawerState = rememberDrawerState(DrawerValue.Closed)
        NavHost(navController = navController, startDestination = "Home"){
            composable("Home"){
                homeSpacer = true
                addEmployeeSpacer = false
                mailSpacer = false
                activitySpacer = false
                HomeScreen(navHostController = navController,userDataPref)
            }
            composable("Activity"){
                homeSpacer = false
                addEmployeeSpacer = false
                mailSpacer = false
                activitySpacer = true
                ActivityScreen(navController,userDataPref)
            }
            composable("Mail"){
                homeSpacer = false
                addEmployeeSpacer = false
                mailSpacer = true
                activitySpacer = false
                MailScreen(navController)
            }
            composable("Profile"){
                homeSpacer = false
                addEmployeeSpacer = true
                mailSpacer = false
                activitySpacer = false
                ProfileScreen(viewModel = viewModel, navController,userDataPref)
            }
        }
    }

    @Composable
    fun BottomView(navController: NavHostController){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(85.dp)
        ) {
            Card(
                shape = RoundedCornerShape(
                    topStart = 20.dp,
                    topEnd = 20.dp
                ),
                modifier = Modifier.fillMaxHeight(),
                colors = CardDefaults.cardColors(Color.White),
                elevation = CardDefaults.cardElevation(15.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp, top = 5.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Column(
                        modifier = Modifier.clickable { navController.navigate("Home"){
                            popUpTo(navController.graph.id){
                                inclusive = true
                            }
                        }
                            homeSpacer = true
                            addEmployeeSpacer = false
                            mailSpacer = false
                            activitySpacer = false },
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.home_icon),
                            contentDescription = "Home",
                            modifier = Modifier
                                .height(45.dp)
                                .width(45.dp)
                        )
                        Text(
                            text = "Home",
                            fontFamily = FontFamily(Font(R.font.poppins_medium)),
                            color = colorResource(id = R.color.black),
                            fontSize = 10.sp,
                            textAlign = TextAlign.Center
                        )
                        AnimatedVisibility(visible = homeSpacer) {
                            Spacer(
                                modifier = Modifier
                                    .layoutId("HomeSpacerId")
                                    .height(2.dp)
                                    .width(25.dp)
                                    .padding(top = 1.dp)
                                    .background(Color.Black)
                            )
                        }
                    /*    if (homeSpacer) {
                            Spacer(
                                modifier = Modifier
                                    .layoutId("HomeSpacerId")
                                    .height(2.dp)
                                    .width(25.dp)
                                    .padding(top = 1.dp)
                                    .background(Color.Black)
                            )
                        }*/

                    }
                    Column(
                        modifier = Modifier.clickable { navController.navigate("Activity")
                            homeSpacer = false
                            activitySpacer = true
                            addEmployeeSpacer = false
                            mailSpacer = false },
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.activity),
                            contentDescription = "Add",
                            modifier = Modifier
                                .height(45.dp)
                                .width(45.dp)
                        )
                        Text(
                            text = "Activity",
                            fontFamily = FontFamily(Font(R.font.poppins_medium)),
                            color = colorResource(id = R.color.black),
                            fontSize = 10.sp,
                            textAlign = TextAlign.Center
                        )
                        if (activitySpacer) {
                            Spacer(
                                modifier = Modifier
                                    .height(2.dp)
                                    .width(25.dp)
                                    .padding(top = 1.dp)
                                    .background(Color.Black)
                            )
                        }
                    }
                    Column(
                        modifier = Modifier
                            .clickable {
                                navController.navigate("Mail")
                                homeSpacer = false
                                addEmployeeSpacer = false
                                activitySpacer = false
                                mailSpacer = true
                            }
                            .testTag("MailTestTag"),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.mail_icon),
                            contentDescription = "Mail",
                            modifier = Modifier
                                .height(45.dp)
                                .width(45.dp)
                        )
                        Text(
                            text = "Mail",
                            fontFamily = FontFamily(Font(R.font.poppins_medium)),
                            color = colorResource(id = R.color.black),
                            fontSize = 10.sp,
                            textAlign = TextAlign.Center
                        )
                        if (mailSpacer) {
                            Spacer(
                                modifier = Modifier
                                    .height(2.dp)
                                    .width(25.dp)
                                    .layoutId("MailLine")
                                    .padding(top = 1.dp)
                                    .background(Color.Black)
                            )
                        }
                    }
                    Column(
                        modifier = Modifier.clickable { navController.navigate("Profile")
                            homeSpacer = false
                            addEmployeeSpacer = true
                            activitySpacer = false
                            mailSpacer = false },
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.profile),
                            contentDescription = "Add",
                            modifier = Modifier
                                .height(45.dp)
                                .width(45.dp)
                        )
                        Text(
                            text = "Profile",
                            fontFamily = FontFamily(Font(R.font.poppins_medium)),
                            color = colorResource(id = R.color.black),
                            fontSize = 10.sp,
                            textAlign = TextAlign.Center
                        )
                        if (addEmployeeSpacer) {
                            Spacer(
                                modifier = Modifier
                                    .height(2.dp)
                                    .width(25.dp)
                                    .padding(top = 1.dp)
                                    .background(Color.Black)
                            )
                        }
                    }
                }
            }

        }
    }

    @Composable
    fun TopBar(scaffoldState: ScaffoldState) {
        val usersState by viewModel.users
        val context = LocalContext.current
        var userName = "User Name"
        var designation = "Designation"
        runBlocking {
            launch {
                imageUri = Uri.parse(userDataPref.getString(UserDataPref.PROFILE_PICTURE))
                userName = userDataPref.getString(UserDataPref.FirstNAME)
                designation = userDataPref.getString(UserDataPref.DESIGNATION)
            }
        }

       /* LaunchedEffect(key1 = Unit) {
            viewModel.fetchUsers()
        }
        when(usersState) {
            is ApiState.Loading -> {
                CircularProgressIndicator()
            }

            is ApiState.Success -> {
                val response = (usersState as ApiState.Success<EmployeeDetailResposne>)
             //   userName = (response.data?.result?.firstName ?: "") +  " " + (response.data?.result?.lastName
               //     ?: "")
                designation = response.data?.result?.designation ?: ""
              //  imageUri = Uri.parse(response.data?.result?.profilePicturePath ?: "")
            }

            is ApiState.Error -> {
                val error = (usersState as ApiState.Error).message
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
        val coroutineScope = rememberCoroutineScope()
        val gradientBrush = Brush.horizontalGradient(
            colors = listOf(
                colorResource(id = R.color.start_color), // Start color
                colorResource(id = R.color.end_color)    // End color
            )
        )
        ConstraintLayout(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
        ) {
            val (profileIcon, topHeader, txtName,txtDesig) = createRefs()
            Row(modifier = Modifier
                .constrainAs(topHeader) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .height(120.dp)) {
               Card(
                    shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .background(gradientBrush)
                            .fillMaxSize()
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(top = 20.dp, start = 10.dp, end = 10.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.manubar_icon),
                                contentDescription = "Side Bar",
                                modifier = Modifier
                                    .height(30.dp)
                                    .width(30.dp)
                                    .clickable {
                                        coroutineScope.launch {
                                            scaffoldState.drawerState.open()
                                        }
                                    }
                            )
                            Text(
                                text = "Dashboard",
                                color = Color.White,
                                fontSize = 20.sp,
                                fontFamily = FontFamily(Font(R.font.poppins_semibold))
                            )
                            Image(
                                painter = painterResource(id = R.drawable.notification_icon),
                                contentDescription = "Side Bar",
                                modifier = Modifier
                                    .height(30.dp)
                                    .width(30.dp)
                            )
                        }
                    }
                }
            }
            Row(modifier = Modifier
                .padding(top = 65.dp)
                .constrainAs(profileIcon) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(imageUri)
                        .build(),
                    placeholder = painterResource(id = R.drawable.dummy_profile2),
                    error = painterResource(id = R.drawable.dummy_profile2),
                    contentDescription = "Side Bar",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(90.dp)
                        .clip(CircleShape)
                        .clickable {
                            val readImagePermission =
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                                    Manifest.permission.READ_MEDIA_IMAGES
                                else
                                    Manifest.permission.READ_EXTERNAL_STORAGE

                            if (ContextCompat.checkSelfPermission(
                                    context,
                                    readImagePermission
                                ) == PackageManager.PERMISSION_GRANTED
                            ) {
                                selectDocument()
                            } else {
                                requestPermission()
                            }
                        }
                )
            }
                Text(text = userName,  color = Color.Black, fontFamily = FontFamily(Font(R.font.poppins_semibold)), fontSize = 20.sp,
                modifier = Modifier.constrainAs(txtName) {
                    top.linkTo(profileIcon.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                })
                Text(text = designation, color = Color.Gray, fontFamily = FontFamily(Font(R.font.poppins_medium)), fontSize = 18.sp,
                    modifier = Modifier.constrainAs(txtDesig) {
                        top.linkTo(txtName.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    })
        }
    }

    @Composable
    fun CustomNavigationDrawer(
        navController: NavHostController,
        modifier: Modifier = Modifier
    ) {
        val gradientBrush = Brush.horizontalGradient(
            colors = listOf(
                colorResource(id = R.color.start_color), // Start color
                colorResource(id = R.color.end_color)    // End color
            )
        )
        Column(modifier = Modifier
            .fillMaxSize()
            .background(gradientBrush)){
            // Header
           ConstraintLayout(modifier.fillMaxSize()){
               // Creating references
               val (header, topBar, bottomBar,menuDivider,menuColum,logoutIcon,logoutText,version) = createRefs()
               Box(modifier = Modifier
                   .fillMaxWidth()
                   .constrainAs(topBar) {
                       top.linkTo(parent.top)
                       start.linkTo(parent.start)
                       end.linkTo(parent.end)
                       bottom.linkTo(bottomBar.top)
                   }){
                   ConstraintLayout(modifier = modifier.fillMaxSize()){
                       Box(
                           modifier = Modifier
                               .height(100.dp)
                               .fillMaxWidth()
                               .padding(top = 20.dp)
                               .constrainAs(header) {
                                   top.linkTo(parent.top)
                                   start.linkTo(parent.start)
                                   end.linkTo(parent.end)
                                   bottom.linkTo(menuDivider.top)
                               }

                       ) {
                           Row(modifier = modifier.fillMaxSize(),
                               horizontalArrangement = Arrangement.SpaceBetween) {
                               Image(painter = painterResource(id = R.drawable.app_banner), contentDescription = "Banner Image", modifier = Modifier
                                   .height(70.dp)
                                   .padding(start = 15.dp)
                                   .align(Alignment.CenterVertically))
                               Image(painter = painterResource(id = R.drawable.cross), contentDescription = "Close", modifier= modifier
                                   .padding(end = 15.dp)
                                   .align(Alignment.CenterVertically))
                           }
                       }

                       // Divider
                       Divider(color = Color.LightGray, thickness = 0.3.dp, modifier = modifier
                           .padding(start = 30.dp, end = 30.dp)
                           .constrainAs(menuDivider) {
                               top.linkTo(header.bottom)
                               start.linkTo(parent.start)
                               end.linkTo(parent.end)
                           })
                       // Navigation items
                       Column(
                           modifier
                               .padding(start = 16.dp, top = 16.dp)
                               .verticalScroll(
                                   rememberScrollState()
                               )
                               .constrainAs(menuColum) {
                                   top.linkTo(menuDivider.bottom)
                                   start.linkTo(parent.start)
                                   end.linkTo(parent.end)
                               }) {
                           MenuItem(text = "Activity", iconResId = R.drawable.home_icon, onClick = { startActivity(Intent(this@HomePage,TestActivity::class.java)) })
                           MenuItem(text = "Performance Ratings", iconResId = R.drawable.home_icon, onClick = { navController.navigate("Home") })
                           MenuItem(text = "Company Policy", iconResId = R.drawable.home_icon, onClick = { navController.navigate("Profile") })

                       }
                   }

                }

                // Footer
               Box(
                   modifier = Modifier
                       .wrapContentWidth()
                       .wrapContentHeight()
                       .constrainAs(bottomBar) {
                           bottom.linkTo(parent.bottom)
                           start.linkTo(parent.start)
                           end.linkTo(parent.end)
                       }
               ) {
                   ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
                       val (logoutIcon, logoutText, version) = createRefs()
                       Image(
                           painter = painterResource(id = R.drawable.sidebar_logout),
                           contentDescription = "Logout",
                           modifier = Modifier
                               .padding(start = 10.dp)
                               .constrainAs(logoutIcon) {
                                   top.linkTo(parent.top)
                                   start.linkTo(parent.start)
                                   bottom.linkTo(parent.bottom)
                               }
                       )

                       Text(
                           text = "Logout ",
                           color = Color.White,
                           fontSize = 18.sp,
                           fontFamily = FontFamily(Font(R.font.poppins_semibold)),
                           modifier = Modifier
                               .constrainAs(logoutText) {
                                   top.linkTo(parent.top)
                                   start.linkTo(logoutIcon.end)
                                   bottom.linkTo(parent.bottom)

                               }
                               .width(IntrinsicSize.Min)
                       )

                       Text(
                           text = "Version",
                           color = Color.White,
                           fontSize = 18.sp,
                           fontFamily = FontFamily(Font(R.font.poppins_semibold)),
                           modifier = Modifier
                               .padding(end = 10.dp)
                               .constrainAs(version) {
                                   top.linkTo(parent.top)
                                   end.linkTo(parent.end)
                                   bottom.linkTo(parent.bottom)
                               }
                       )
                   }
               }

           }
        }
    }
    @Composable
    fun MenuItem(
        text: String,
        iconResId: Int,
        onClick: () -> Unit
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 16.dp)
                .clickable(onClick = onClick)
        ) {
            Image(
                painter = painterResource(id = iconResId),
                contentDescription = "Icon",
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp)) // Add spacing between icon and text
            Text(text = text, color = Color.White)
        }
    }

    private fun requestPermission() {
        val permissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            arrayOf(Manifest.permission.READ_MEDIA_IMAGES)
        }else{
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        permissionLauncher.launch(permissions)
    }

}