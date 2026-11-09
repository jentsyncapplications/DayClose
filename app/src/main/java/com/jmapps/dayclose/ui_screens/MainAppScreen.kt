package com.jmapps.dayclose.ui_screens



import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import com.jmapps.dayclose.R
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.jmapps.dayclose.model.DaySummary
import com.jmapps.dayclose.navigationgraph.Screen
import com.jmapps.dayclose.ui_screens.components.AddExpensePopup
import com.jmapps.dayclose.ui_screens.components.DayClosePopup
import com.jmapps.dayclose.ui_screens.components.DayOpenPopup
import com.jmapps.dayclose.ui_screens.components.EditDayClosePopup
import com.jmapps.dayclose.ui_screens.components.EditDayOpenPopup
import com.jmapps.dayclose.utils.getGreeting
import com.jmapps.dayclose.utils.standardDateFormat
import com.jmapps.dayclose.viewmodel.DayDataViewModel
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.ChartBar
import compose.icons.fontawesomeicons.solid.FileInvoiceDollar
import compose.icons.fontawesomeicons.solid.MoneyBillAlt
import compose.icons.fontawesomeicons.solid.MoneyBillWaveAlt
import org.koin.androidx.compose.koinViewModel


@SuppressLint("SuspiciousIndentation")
@Composable
fun MainAppScreen(navController: NavController) {

//    var showOpeningDialog by remember { mutableStateOf(false) }
//    var showClosingDialog by remember { mutableStateOf(false) }
//    var showAddExpenseDialog by remember { mutableStateOf(false) }
    var showOpeningDialog by remember { mutableStateOf(false) }
    var showEditOpeningDialog by remember { mutableStateOf(false) }

    var showClosingDialog by remember { mutableStateOf(false) }
    var showEditClosingDialog by remember { mutableStateOf(false) }

    var showAddExpenseDialog by remember { mutableStateOf(false) }



    val dayDataViewModel: DayDataViewModel= koinViewModel()

    val todayDate = standardDateFormat(System.currentTimeMillis())

    val todaySummary by dayDataViewModel
        .getDaySummaryByDate(todayDate)
        .collectAsStateWithLifecycle(
            initialValue = DaySummary(
                id = 0,
                dayId = "",
                date = todayDate,
                openingCash = 0f,
                closingCash = 0f,
                openedAt = 0L,
                closedAt = null,
                note = null,
                timestamp = 0L,
                totalExpense = 0f,
                netProfit = 0f
            )
        )



    val hasDay = todaySummary.dayId.isNotEmpty()

    val isDayOpen = hasDay && todaySummary.closedAt == null

    val isDayClosed = hasDay && todaySummary.closedAt != null


        Column(
            modifier = Modifier
                .fillMaxSize()

                // Scrollable content
                .verticalScroll(rememberScrollState())


                .background(colorResource(id = R.color.border_color))
        ) {
    Column(
        modifier = Modifier
            .fillMaxSize()


            // Scrollable content
//            .verticalScroll(rememberScrollState())

            // Your own spacing
            .padding(horizontal = 12.dp, vertical = 16.dp)

            .background(colorResource(id = R.color.border_color))

            // 👇 THIS is the key line
            // It pushes CONTENT below the status bar
            .statusBarsPadding()
    ) {
        // Using a specialized header instead of putting it in the column
        GreetingHeader(
            userName = "jmapps Solutions",
            onProfileClick = { navController.navigate("settings") }

        )
        Spacer(modifier = Modifier.height(8.dp))

        // 2. SUMMARY SECTION: Cash flow
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SmallStatCard(
                title = "Opening",
                amount = todaySummary.openingCash.toString(),
                icon = FontAwesomeIcons.Solid.MoneyBillAlt,
                color = colorResource(id = R.color.seaweed),
                modifier = Modifier.weight(1f)
            )
            SmallStatCard(
                title = "Closing",
                amount = todaySummary.closingCash.toString(),
                icon = FontAwesomeIcons.Solid.MoneyBillWaveAlt,
                color = colorResource(id = R.color.oxford_navy),
                modifier = Modifier.weight(1f)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))

        // 1. HERO SECTION: Net Profit with Gradient
        ProfitHeroCard(amount = todaySummary.netProfit.toString())
        Spacer(modifier = Modifier.height(8.dp))
        // 3. EXPENSE HIGHLIGHT
        ExpenseCard(amount = todaySummary.totalExpense.toString())
        Spacer(modifier = Modifier.height(8.dp))
        // 4. ACTION GRID
        Text(
            text = "Quick Actions",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier.padding(top = 8.dp)
        )

//        ActionGrid(
//            onAddOpenCash = { showOpeningDialog = true },
//            onAddExpense = { showAddExpenseDialog = true },
//            onCloseDay = { showClosingDialog= true },
//            onViewAllExpense = { navController.navigate("dailyExpense/29-01-2026") }
//        )


        ActionGrid(
            openingLabel = if (hasDay) "Edit Opening" else "Opening",

            closingLabel = when {
                isDayClosed -> "Edit Closing"
                isDayOpen -> "Close Day"
                else -> "Close Day"
            },

            onAddOpenCash = {
                if (hasDay) {
                    showEditOpeningDialog = true
                } else {
                    showOpeningDialog = true
                }
            },

            onAddExpense = {
                if (isDayOpen) {
                    showAddExpenseDialog = true
                }
            },

            onCloseDay = {
                when {
                    isDayClosed -> showEditClosingDialog = true
                    isDayOpen -> showClosingDialog = true
                }
            },

            onViewAllExpense = {
                navController.navigate("dailyExpense/$todayDate")
            }
        )



    }
}


    if (showOpeningDialog) {
        DayOpenPopup (onDismiss = { showOpeningDialog = false })
    }


    if (showClosingDialog) {
        DayClosePopup (
            onDismiss = { showClosingDialog = false },
            dayId = todaySummary.dayId
        )
    }


    if (showAddExpenseDialog) {
        AddExpensePopup (
            onDismiss = { showAddExpenseDialog = false },
            dayId= todaySummary.dayId
        )
    }


    if (showEditOpeningDialog) {

        EditDayOpenPopup (
            onDismiss = { showEditOpeningDialog = false },
            openingCash = todaySummary.openingCash,
            dayId= todaySummary.dayId
        )
    }

    if (showEditClosingDialog) {
        EditDayClosePopup(
            onDismiss = { showEditClosingDialog = false },
            closingCash = todaySummary.closingCash ?: 0f,
            dayId = todaySummary.dayId
        )
    }



}

//@Composable
//fun ProfitHeroCard(amount: String) {
//    val gradient = Brush.verticalGradient(
//        colors = listOf(colorResource(id = R.color.seaweed), colorResource(id = R.color.yale_blue))
//    )
//
//    ElevatedCard(
//        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 8.dp),
//        shape = RoundedCornerShape(24.dp),
//        modifier = Modifier.fillMaxWidth()
//    ) {
//        Box(
//            modifier = Modifier
//                .background(gradient)
//                .padding(24.dp)
//        ) {
//            Column {
//                Text("Expected Net Profit", color = Color.White.copy(alpha = 0.8f), fontSize = 14.sp)
//                Spacer(modifier = Modifier.height(8.dp))
//                Text(
//                    text = amount,
//                    color = Color.White,
//                    fontSize = 32.sp,
//                    fontWeight = FontWeight.ExtraBold
//                )
//            }
//            // Background Decorative Circle
//            Icon(
//                imageVector = FontAwesomeIcons.Solid.ChartBar,
//                contentDescription = null,
//                modifier = Modifier
//                    .size(80.dp)
//                    .align(Alignment.BottomEnd)
//                    .graphicsLayer(alpha = 0.2f),
//                tint = Color.White
//            )
//        }
//    }
//}

@Composable
fun ProfitHeroCard(amount: String) {
    val gradient = Brush.verticalGradient(
        colors = listOf(
            colorResource(id = R.color.seaweed),
            colorResource(id = R.color.yale_blue)
        )
    )

    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.elevatedCardElevation(8.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = Color.Transparent
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(gradient)
                .padding(24.dp)
        ) {
            Column {
                Text(
                    "Expected Net Profit",
                    color = Color.White.copy(alpha = 0.8f),
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = amount,
                    color = Color.White,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }

            Icon(
                imageVector = FontAwesomeIcons.Solid.ChartBar,
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .align(Alignment.BottomEnd)
                    .graphicsLayer(alpha = 0.2f),
                tint = Color.White
            )
        }
    }
}


@Composable
fun SmallStatCard(title: String, amount: String, icon: ImageVector, color: Color, modifier: Modifier) {
    ElevatedCard(
        colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
        shape = RoundedCornerShape(20.dp),
        modifier = modifier
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Icon(imageVector = icon, contentDescription = null, tint = color, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.height(12.dp))
            Text(title, fontSize = 12.sp, color = Color.Gray)
            Text(amount, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = color)
        }
    }
}

//@Composable
//fun ActionGrid(
//    onAddOpenCash: () -> Unit,
//    onAddExpense: () -> Unit,
//    onCloseDay: () -> Unit,
//    onViewAllExpense: () -> Unit
//) {
//    // A clean 2x2 grid using standard modifiers
//    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
//        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
//            ModernActionButton("Opening", Icons.Default.Add, colorResource(id = R.color.seaweed), onAddOpenCash, Modifier.weight(1f))
//            ModernActionButton("Expense", Icons.Default.Create, colorResource(id = R.color.yale_blue), onAddExpense, Modifier.weight(1f))
//        }
//        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
//            ModernActionButton("Close Day", Icons.Default.Lock, colorResource(id = R.color.oxford_navy), onCloseDay, Modifier.weight(1f))
//            ModernActionButton("History", FontAwesomeIcons.Solid.FileInvoiceDollar, colorResource(id = R.color.oxford_navy), onViewAllExpense, Modifier.weight(1f))
//        }
//    }
//}

@Composable
fun ActionGrid(
    openingLabel: String,
    closingLabel: String,
    onAddOpenCash: () -> Unit,
    onAddExpense: () -> Unit,
    onCloseDay: () -> Unit,
    onViewAllExpense: () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            ModernActionButton(
                openingLabel,
                Icons.Default.Add,
                colorResource(id = R.color.seaweed),
                onAddOpenCash,
                Modifier.weight(1f)
            )

            ModernActionButton(
                "Expense",
                Icons.Default.Create,
                colorResource(id = R.color.yale_blue),
                onAddExpense,
                Modifier.weight(1f)
            )
        }

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            ModernActionButton(
                closingLabel,
                Icons.Default.Lock,
                colorResource(id = R.color.oxford_navy),
                onCloseDay,
                Modifier.weight(1f)
            )

            ModernActionButton(
                "View Expenses",
                FontAwesomeIcons.Solid.FileInvoiceDollar,
                colorResource(id = R.color.oxford_navy),
                onViewAllExpense,
                Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun ModernActionButton(text: String, icon: ImageVector, color: Color, onClick: () -> Unit, modifier: Modifier) {
    Button(
        onClick = onClick,
        modifier = modifier.height(64.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(imageVector = icon, contentDescription = null, tint = color, modifier = Modifier.size(24.dp))
            Text(text, color = color, fontSize = 10.sp, fontWeight = FontWeight.Bold)
        }
    }
}


//@Composable
//fun ExpenseCard(
//    amount: String,
//    modifier: Modifier = Modifier
//) {
//    Box(
//        modifier = modifier
//            .fillMaxWidth()                    // ✅ takes full screen width
//            .padding(horizontal = 16.dp)       // ✅ outer spacing from screen edges
//            .background(
//                color = colorResource(id = R.color.light_background),
//                shape = RoundedCornerShape(20.dp)
//            )
//            .padding(24.dp)                    // ✅ big inner padding
//    ) {
//        Column {
//            Text(
//                text = "Today's Expenses",
//                color = colorResource(id=R.color.golden_sand),
//                fontSize = 16.sp                // slightly bigger label
//            )
//
//            Spacer(modifier = Modifier.height(12.dp))
//
//            Text(
//                text = amount,
//                color = colorResource(id = R.color.golden_orange),
//                fontSize = 28.sp,               // big emphasis
//                fontWeight = FontWeight.Bold
//            )
//        }
//    }
//}



@Composable
fun ExpenseCard(amount: String) {
    val gradient = Brush.verticalGradient(
        colors = listOf(
            colorResource(id = R.color.golden_sand),
            colorResource(id = R.color.golden_orange)
        )
    )

    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.elevatedCardElevation(8.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = Color.Transparent
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(gradient)
                .padding(24.dp)
        ) {
            Column {
                Text(
                    "Total Expenses",
                    color = Color.White.copy(alpha = 0.8f),
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = amount,
                    color = Color.White,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }

            Icon(
                imageVector = FontAwesomeIcons.Solid.FileInvoiceDollar,
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .align(Alignment.BottomEnd)
                    .graphicsLayer(alpha = 0.2f),
                tint = Color.White
            )
        }
    }
}


@Composable
fun GreetingHeader(
    userName: String,
    onProfileClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.light_background),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(
                top = 16.dp,
                start = 16.dp,
                end = 16.dp,
                bottom = 12.dp
            )


    ) {

        // LEFT SIDE TEXT
        Column(
            modifier = Modifier.align(Alignment.CenterStart)
        ) {
            Text(
                text = rememberGreeting(),
                fontSize = 14.sp,
                color = colorResource(id = R.color.oxford_navy)
            )

            Text(
                text = userName,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.seaweed)
            )
        }

        // RIGHT SIDE USER ICON
        IconButton(
            onClick = onProfileClick,
            modifier = Modifier.align(Alignment.TopEnd)
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "User profile",
                tint = colorResource(id = R.color.seaweed),
                modifier = Modifier.size(28.dp)
            )
        }
    }
}




@Composable
fun rememberGreeting(): String {
    var greeting by remember { mutableStateOf(getGreeting()) }

    LaunchedEffect(Unit) {
        while (true) {
            greeting = getGreeting()
            kotlinx.coroutines.delay(60_000) // update every minute
        }
    }

    return greeting
}