package com.jmapps.dayclose.navigationgraph

import androidx.compose.animation.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
//google is moving away from accompanist navigation now we use androidx.navigation.compose
import androidx.navigation.NavHostController
import com.jmapps.dayclose.ui_screens.AppDetailScreen
import com.jmapps.dayclose.ui_screens.DailyExpenseScreen
import com.jmapps.dayclose.ui_screens.HistoryScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.jmapps.dayclose.ui_screens.AboutScreen
import com.jmapps.dayclose.ui_screens.MainAppScreen
import com.jmapps.dayclose.ui_screens.ManageBusinessScreen
import com.jmapps.dayclose.ui_screens.SettingScreen

/**
 * Sealed class to define all top-level destinations.
 * This gives you type safety instead of raw strings everywhere.
 */
sealed class Screen(val route: String) {
    object MainApp : Screen("home")
    object Settings : Screen("settings")

    object AppDetails: Screen("appDetails")

    object HistoryData: Screen("history")


    object DailyExpense: Screen("dailyExpense/{dateId}")


    object BusinessProfile: Screen("businessProfile")

    object AboutApp: Screen("aboutApp")

}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    /**
     * AnimatedNavHost is the root navigation container for the app.
     * Think of it as the "router" of your entire app.
     */
    AnimatedNavHost(
        navController = navController,
        startDestination = Screen.MainApp.route,
        modifier = modifier,

        // Animation when navigating forward
        enterTransition = {
            slideInHorizontally { 1000 } + fadeIn()
        },
        exitTransition = {
            slideOutHorizontally { -1000 } + fadeOut()
        },

        // Animation when navigating back
        popEnterTransition = {
            slideInHorizontally { -1000 } + fadeIn()
        },
        popExitTransition = {
            slideOutHorizontally { 1000 } + fadeOut()
        }
    ) {

        /**
         * MainApp is the container screen.
         * This is where your Scaffold and (later) bottom navigation will live.
         */
        composable(Screen.MainApp.route) {
            MainAppScreen(navController)
        }

        /**
         * Example future screen
         */
        composable(Screen.Settings.route) {
            SettingScreen(navController)
        }

        composable(Screen.AppDetails.route) {
            AppDetailScreen(navController)
        }


        composable(Screen.HistoryData.route) {
            HistoryScreen(navController)
        }

        composable(Screen.BusinessProfile.route){
            ManageBusinessScreen(navController)
        }


        composable(Screen.AboutApp.route){
            AboutScreen(navController)
        }



        composable(Screen.DailyExpense.route) { backStackEntry ->
            val dateId = backStackEntry.arguments?.getString("dateId") ?: "Unknown"
            DailyExpenseScreen(navController, dateId)
        }
    }
}
