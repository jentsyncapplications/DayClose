package com.jmapps.dayclose.ui_screens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.jmapps.dayclose.R
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.ArrowLeft


//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun SettingScreen(navController: NavController) {
//    val backgroundColor = colorResource(id = R.color.seaweed)
//
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = {
//                    Text(
//                        text = "App Settings",
//                        color = Color.White
//                    )
//                },
//                colors = TopAppBarDefaults.topAppBarColors(
//                    containerColor = backgroundColor,
//                    titleContentColor = Color.White,
//                    navigationIconContentColor = Color.White
//                )
//            )
//        }
//    ) { innerPadding ->
//
//        /**
//         * innerPadding already includes:
//         * - Status bar height
//         * - TopAppBar height
//         *
//         * Bottom bar padding is NOT included
//         * (because MainActivity hides it for this screen)
//         */
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(innerPadding)
//                .background(colorResource(id = R.color.gray))
//                .verticalScroll(rememberScrollState())
//                .padding(horizontal = 12.dp, vertical = 16.dp)
//        ) {
//
//            Text(
//                text = "Hello, this is my setting screen",
//                fontSize = 18.sp
//            )
//        }
//    }
//}
//
//
//

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(navController: NavController) {

    val backgroundColor = colorResource(id = R.color.seaweed)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("App Settings",color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = backgroundColor,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(//innerPadding
                    start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
                    top = innerPadding.calculateTopPadding(),
                    end = innerPadding.calculateEndPadding(LocalLayoutDirection.current),
                    bottom = innerPadding.calculateEndPadding(LocalLayoutDirection.current)
                        //innerPadding.calculateBottomPadding()

                )
                .background(colorResource(id = R.color.gray))
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Text("Hello, this is my setting screen", fontSize = 18.sp)
        }
    }
}




@Preview(showBackground = true)
@Composable
fun SettingScreenPreview() {
    SettingScreen(navController = rememberNavController())
}