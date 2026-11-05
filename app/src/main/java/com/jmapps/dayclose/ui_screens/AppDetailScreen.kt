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
//fun AppDetailScreen(navController: NavController) {
//    val backgroundColor = colorResource(id = R.color.seaweed)
////    StatusBarColor(backgroundColor)
//
//    Scaffold(
//        topBar = {
//            CenterAlignedTopAppBar(
//                title = { Text("App Details", color = Color.White) },
//                navigationIcon = {
//                    IconButton(onClick = { navController.popBackStack() }) {
//                        Icon(
//                            imageVector = FontAwesomeIcons.Solid.ArrowLeft,
//                            contentDescription = "Back",
//                            tint = Color.White,
//                            modifier = Modifier.size(24.dp)
//                        )
//                    }
//                },
//                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
//                    containerColor = backgroundColor
//                )
//            )
//        },
//    ) { paddingValues ->
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(paddingValues)          // ✅ handles system insets
//                .background(colorResource(id = R.color.gray))
//                .verticalScroll(rememberScrollState())
//                .padding(horizontal = 12.dp)    // ✅ your own content padding
//        ) {
//            // Your screen content here
//
//
//            Text("hello this is my details screen")
//        }
//    }
//}
//
//
//
//
//
//
//
//@Preview(showBackground = true)
//@Composable
//fun AppDetailScreenPreview() {
//    AppDetailScreen(navController = rememberNavController())
//}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDetailScreen(navController: NavController) {

    val topBarColor = colorResource(id = R.color.seaweed)

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "App Details",
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = FontAwesomeIcons.Solid.ArrowLeft,
                            contentDescription = "Back",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = topBarColor
                )
            )
        }
    ) { innerPadding ->

        /**
         * innerPadding already includes:
         * - Status bar height
         * - TopAppBar height
         *
         * Bottom bar padding is NOT included
         * (because MainActivity hides it for this screen)
         */
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(colorResource(id = R.color.gray))
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 12.dp, vertical = 16.dp)
        ) {

            Text(
                text = "Hello, this is my details screen",
                fontSize = 18.sp
            )
        }
    }
}
