
package com.jmapps.dayclose

import compose.icons.fontawesomeicons.solid.ChartBar
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import com.jmapps.dayclose.R
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.jmapps.dayclose.navigationgraph.AppNavHost
import com.jmapps.dayclose.navigationgraph.Screen
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.ChartLine
import compose.icons.fontawesomeicons.solid.ClipboardList
import compose.icons.fontawesomeicons.solid.Cog
import compose.icons.fontawesomeicons.solid.FileInvoiceDollar
import compose.icons.fontawesomeicons.solid.Home
import compose.icons.fontawesomeicons.solid.ListAlt
import compose.icons.fontawesomeicons.solid.StoreAlt
import compose.icons.fontawesomeicons.solid.UserCog
import compose.icons.fontawesomeicons.solid.Warehouse
import java.util.Locale

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.S)
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Ensure full-screen layout
        WindowCompat.setDecorFitsSystemWindows(window, false)


        // ✅ Enable edge-to-edge layout with transparent system bars
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto(
                lightScrim = Color.TRANSPARENT,
                darkScrim = Color.TRANSPARENT
            ),
            navigationBarStyle = SystemBarStyle.auto(
                lightScrim = Color.TRANSPARENT,
                darkScrim = Color.TRANSPARENT
            )
        )

        setContent {
            val navController = rememberAnimatedNavController()

            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            // Define screens where the bottom bar should be hidden
            val hideBottomBarScreens = listOf(Screen.AppDetails.route,Screen.DailyExpense.route, Screen.BusinessProfile.route,Screen.AboutApp.route)

            Scaffold(
                bottomBar = {
//                    BottomNavigationBar(navController)
                    if (currentRoute !in hideBottomBarScreens) {
//                        BottomNavigationBar(navController)
                        BottomNavigationBarModern(navController)
                    }
                },
                floatingActionButton = {

                }

            ) { paddingValues ->
                // 🔑 This padding now represents:
                // - status bar
                // - bottom navigation bar
                AppNavHost(
                    navController = navController,
                    modifier = Modifier.padding(
                        bottom = paddingValues.calculateBottomPadding()
                    )
//                    modifier = Modifier.padding(paddingValues)
                )
//                AppNavHost(navController,
//                    Modifier
//                        .padding(paddingValues)
//                        .windowInsetsPadding(WindowInsets.systemBars))
            }
        }
    }





}

@Composable
fun BottomNavigationBarModern(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination?.route

    val screens = listOf(Screen.MainApp, Screen.HistoryData, Screen.Settings)

    val backgroundColor = colorResource(id = R.color.bottom_bar_background)
    val selectedColor = colorResource(id = R.color.tab_selected)
    val unselectedColor = colorResource(id = R.color.tab_unselected)
    val indicatorColor = colorResource(id = R.color.tab_indicator)

    Surface(
        color = backgroundColor,
        shadowElevation = 0.dp,
        modifier = Modifier.fillMaxWidth()
            .navigationBarsPadding() // <-- adds safe bottom padding
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            screens.forEach { screen ->
                val isSelected = currentDestination == screen.route

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .clickable {
                            if (currentDestination != screen.route) {
                                navController.navigate(screen.route) {
                                    popUpTo(Screen.MainApp.route) { inclusive = false }
                                    launchSingleTop = true
                                }
                            }
                        }
                        .padding(horizontal = 8.dp)
                ) {
                    // Icon
                    Icon(
                        imageVector = when (screen) {
                            is Screen.MainApp -> FontAwesomeIcons.Solid.FileInvoiceDollar
                            is Screen.HistoryData -> FontAwesomeIcons.Solid.ListAlt
                            is Screen.Settings -> FontAwesomeIcons.Solid.UserCog
                            else -> FontAwesomeIcons.Solid.Home
                        },
                        contentDescription = screen.route,
                        tint = if (isSelected) selectedColor else unselectedColor,
                        modifier = Modifier.size(22.dp)
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    // Label
                    Text(
                        text = screen.route.replaceFirstChar { it.titlecase(Locale.ROOT) },
                        color = if (isSelected) selectedColor else unselectedColor,
                        fontSize = 11.sp
                    )

                    // Small underline indicator for selected tab
                    if (isSelected) {
                        Spacer(
                            modifier = Modifier
                                .height(2.dp)
                                .width(24.dp)
                                .background(indicatorColor, shape = RoundedCornerShape(1.dp))
                                .padding(top = 2.dp)
                        )
                    } else {
                        Spacer(modifier = Modifier.height(4.dp)) // placeholder
                    }
                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun BottomNavigationBarModernPreview() {
    BottomNavigationBarModern(navController = rememberNavController())
}