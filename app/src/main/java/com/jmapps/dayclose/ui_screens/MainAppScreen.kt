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
import androidx.compose.material.icons.filled.CalendarToday
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
import com.jmapps.dayclose.ui_screens.components.GreetingHeader
import com.jmapps.dayclose.utils.getGreeting
import com.jmapps.dayclose.utils.standardDateFormat
import com.jmapps.dayclose.viewmodel.BusinessProfilePreferenceViewModel
import com.jmapps.dayclose.viewmodel.DayDataViewModel
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.ChartBar
import compose.icons.fontawesomeicons.solid.FileInvoiceDollar
import compose.icons.fontawesomeicons.solid.MoneyBillAlt
import compose.icons.fontawesomeicons.solid.MoneyBillWaveAlt
import org.koin.androidx.compose.koinViewModel


//@SuppressLint("SuspiciousIndentation")
//@Composable
//fun MainAppScreen(navController: NavController) {
//
//    val viewModel : BusinessProfilePreferenceViewModel = koinViewModel()
//
//
//    val businessName by viewModel.businessName.collectAsStateWithLifecycle(
//        initialValue = ""
//    )
//
//    val isProfileSet by viewModel.isProfileSet.collectAsStateWithLifecycle(
//        initialValue = false
//    )
//
//
//    var showOpeningDialog by remember { mutableStateOf(false) }
//    var showEditOpeningDialog by remember { mutableStateOf(false) }
//
//    var showClosingDialog by remember { mutableStateOf(false) }
//    var showEditClosingDialog by remember { mutableStateOf(false) }
//
//    var showAddExpenseDialog by remember { mutableStateOf(false) }
//
//
//
//    val dayDataViewModel: DayDataViewModel= koinViewModel()
//
//    val todayDate = standardDateFormat(System.currentTimeMillis())
//
//    val todaySummary by dayDataViewModel
//        .getDaySummaryByDate(todayDate)
//        .collectAsStateWithLifecycle(
//            initialValue = DaySummary(
//                id = 0,
//                dayId = "",
//                date = todayDate,
//                openingCash = 0f,
//                closingCash = 0f,
//                openedAt = 0L,
//                closedAt = null,
//                note = null,
//                timestamp = 0L,
//                totalExpense = 0f,
//                netProfit = 0f
//            )
//        )
//
//
//    val hasDay = todaySummary.dayId.isNotEmpty()
//
//    val isDayOpen = hasDay && todaySummary.closedAt == null
//
//    val isDayClosed = hasDay && todaySummary.closedAt != null
//
//
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//
//                // Scrollable content
//                .verticalScroll(rememberScrollState())
//
//
//                .background(colorResource(id = R.color.border_color))
//        ) {
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//
//
//            // Scrollable content
////            .verticalScroll(rememberScrollState())
//
//            // Your own spacing
//            .padding(horizontal = 12.dp, vertical = 16.dp)
//
//            .background(colorResource(id = R.color.border_color))
//
//            // 👇 THIS is the key line
//            // It pushes CONTENT below the status bar
//            .statusBarsPadding()
//    ) {
//
//        GreetingHeader(
//            businessName = businessName,
//            isProfileSet = isProfileSet,
//            onProfileClick = {
//                navController.navigate(Screen.BusinessProfile.route)
//            }
//        )
//        Spacer(modifier = Modifier.height(8.dp))
//
//        // 2. SUMMARY SECTION: Cash flow
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.spacedBy(12.dp)
//        ) {
//            SmallStatCard(
//                title = "Opening",
//                amount = todaySummary.openingCash.toString(),
//                icon = FontAwesomeIcons.Solid.MoneyBillAlt,
//                color = colorResource(id = R.color.seaweed),
//                modifier = Modifier.weight(1f)
//            )
//            SmallStatCard(
//                title = "Closing",
//                amount = todaySummary.closingCash.toString(),
//                icon = FontAwesomeIcons.Solid.MoneyBillWaveAlt,
//                color = colorResource(id = R.color.oxford_navy),
//                modifier = Modifier.weight(1f)
//            )
//        }
//        Spacer(modifier = Modifier.height(8.dp))
//
//        // 1. HERO SECTION: Net Profit with Gradient
//        ProfitHeroCard(amount = todaySummary.netProfit.toString())
//        Spacer(modifier = Modifier.height(8.dp))
//        // 3. EXPENSE HIGHLIGHT
//        ExpenseCard(amount = todaySummary.totalExpense.toString())
//        Spacer(modifier = Modifier.height(8.dp))
//        // 4. ACTION GRID
//        Text(
//            text = "Quick Actions",
//            fontWeight = FontWeight.Bold,
//            fontSize = 18.sp,
//            modifier = Modifier.padding(top = 8.dp)
//        )
//
//
//        ActionGrid(
//            openingLabel = if (hasDay) "Edit Opening" else "Opening",
//
//            closingLabel = when {
//                isDayClosed -> "Edit Closing"
//                isDayOpen -> "Close Day"
//                else -> "Close Day"
//            },
//
//            onAddOpenCash = {
//                if (hasDay) {
//                    showEditOpeningDialog = true
//                } else {
//                    showOpeningDialog = true
//                }
//            },
//
//            onAddExpense = {
//                if (isDayOpen) {
//                    showAddExpenseDialog = true
//                }
//            },
//
//            onCloseDay = {
//                when {
//                    isDayClosed -> showEditClosingDialog = true
//                    isDayOpen -> showClosingDialog = true
//                }
//            },
//
//            onViewAllExpense = {
//                navController.navigate("dailyExpense/$todayDate")
//            }
//        )
//
//
//    }
//}
//
//
//    if (showOpeningDialog) {
//        DayOpenPopup (onDismiss = { showOpeningDialog = false })
//    }
//
//
//    if (showClosingDialog) {
//        DayClosePopup (
//            onDismiss = { showClosingDialog = false },
//            dayId = todaySummary.dayId
//        )
//    }
//
//
//    if (showAddExpenseDialog) {
//        AddExpensePopup (
//            onDismiss = { showAddExpenseDialog = false },
//            dayId= todaySummary.dayId
//        )
//    }
//
//
//    if (showEditOpeningDialog) {
//
//        EditDayOpenPopup (
//            onDismiss = { showEditOpeningDialog = false },
//            openingCash = todaySummary.openingCash,
//            dayId= todaySummary.dayId
//        )
//    }
//
//    if (showEditClosingDialog) {
//        EditDayClosePopup(
//            onDismiss = { showEditClosingDialog = false },
//            closingCash = todaySummary.closingCash ?: 0f,
//            dayId = todaySummary.dayId
//        )
//    }
//
//
//
//}


@SuppressLint("SuspiciousIndentation")
@Composable
fun MainAppScreen(navController: NavController) {

    val viewModel: BusinessProfilePreferenceViewModel = koinViewModel()

    val businessName by viewModel.businessName.collectAsStateWithLifecycle(
        initialValue = ""
    )

    val isProfileSet by viewModel.isProfileSet.collectAsStateWithLifecycle(
        initialValue = false
    )

    val dayDataViewModel: DayDataViewModel = koinViewModel()

    val todayDate = standardDateFormat(System.currentTimeMillis())

    val todaySummary by dayDataViewModel
        .getDaySummaryByDate(todayDate)
        .collectAsStateWithLifecycle(
            initialValue = DaySummary(
                id = 0,
                dayId = "",
                date = todayDate,
                openingCash = 0f,
                closingCash = null,
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

    // Calculate cash inflow only when the day is closed.
    val estimatedCashInflow = if (isDayClosed) {
        todaySummary.closingCash?.let { closing ->
            closing - todaySummary.openingCash + todaySummary.totalExpense
        }
    } else {
        null
    }

    var showOpeningDialog by remember { mutableStateOf(false) }
    var showEditOpeningDialog by remember { mutableStateOf(false) }
    var showClosingDialog by remember { mutableStateOf(false) }
    var showEditClosingDialog by remember { mutableStateOf(false) }
    var showAddExpenseDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.border_color))
            .verticalScroll(rememberScrollState())
            .statusBarsPadding()
            .padding(horizontal = 14.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {

        // Keep your existing greeting card exactly as it is.
        GreetingHeader(
            businessName = businessName,
            isProfileSet = isProfileSet,
            onProfileClick = {
                navController.navigate(Screen.BusinessProfile.route)
            }
        )

        // Today's date
        TodayDateCard()

        // Opening and closing cash
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SmallStatCard(
                title = "Opening Cash",
                amount = if (hasDay) {
                    formatAmount(todaySummary.openingCash)
                } else {
                    "Not Started"
                },
                icon = FontAwesomeIcons.Solid.MoneyBillAlt,
                color = colorResource(R.color.seaweed),
                modifier = Modifier.weight(1f)
            )

            SmallStatCard(
                title = "Closing Cash",
                amount = if (isDayClosed && todaySummary.closingCash != null) {
                    formatAmount(todaySummary.closingCash!!)
                } else {
                    "Day Not Closed"
                },
                icon = FontAwesomeIcons.Solid.MoneyBillWaveAlt,
                color = colorResource(R.color.yale_blue),
                modifier = Modifier.weight(1f)
            )
        }

        // Estimated Cash Inflow
        CashInflowHeroCard(
            amount = estimatedCashInflow
        )

        // Total expenses
        ExpenseCard(
            amount = todaySummary.totalExpense,
            hasDay = hasDay
        )

        // Quick actions
        Text(
            text = "Quick Actions",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(R.color.oxford_navy),
            modifier = Modifier.padding(top = 4.dp)
        )

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

    // Dialogs
    if (showOpeningDialog) {
        DayOpenPopup(
            onDismiss = { showOpeningDialog = false }
        )
    }

    if (showClosingDialog) {
        DayClosePopup(
            onDismiss = { showClosingDialog = false },
            dayId = todaySummary.dayId
        )
    }

    if (showAddExpenseDialog) {
        AddExpensePopup(
            onDismiss = { showAddExpenseDialog = false },
            dayId = todaySummary.dayId
        )
    }

    if (showEditOpeningDialog) {
        EditDayOpenPopup(
            onDismiss = { showEditOpeningDialog = false },
            openingCash = todaySummary.openingCash,
            dayId = todaySummary.dayId
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




//
@Composable
fun TodayDateCard() {

    val today = java.time.LocalDate.now()
    val dateText = today.format(
        java.time.format.DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy")
    )

    val navy = colorResource(R.color.oxford_navy)

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .background(
                        colorResource(R.color.yale_blue).copy(alpha = 0.10f),
                        RoundedCornerShape(14.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.CalendarToday,
                    contentDescription = null,
                    tint = colorResource(R.color.yale_blue),
                    modifier = Modifier.size(22.dp)
                )
            }

            Spacer(Modifier.width(14.dp))

            Column {
                Text(
                    text = "TODAY'S RECORD",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp,
                    color = Color.Gray
                )

                Spacer(Modifier.height(4.dp))

                Text(
                    text = dateText,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = navy
                )
            }
        }
    }
}






@Composable
fun CashInflowHeroCard(amount: Float?) {

    val gradient = Brush.linearGradient(
        colors = listOf(
            colorResource(R.color.oxford_navy),
            colorResource(R.color.yale_blue)
        )
    )

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(gradient)
                .padding(22.dp)
        ) {
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "ESTIMATED CASH INFLOW",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp,
                        color = Color.White.copy(alpha = 0.8f)
                    )

                    Spacer(Modifier.width(8.dp))

                    Icon(
                        imageVector = FontAwesomeIcons.Solid.ChartBar,
                        contentDescription = null,
                        tint = Color.White.copy(alpha = 0.8f),
                        modifier = Modifier.size(17.dp)
                    )
                }

                Spacer(Modifier.height(13.dp))

                if (amount == null) {
                    Text(
                        text = "Day Not Closed",
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    Spacer(Modifier.height(5.dp))

                    Text(
                        text = "Close today's records to calculate cash inflow.",
                        fontSize = 12.sp,
                        color = Color.White.copy(alpha = 0.75f)
                    )
                } else {
                    Text(
                        text = formatAmount(amount),
                        fontSize = 32.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White
                    )

                    Spacer(Modifier.height(5.dp))

                    Text(
                        text = "Estimated cash received today",
                        fontSize = 12.sp,
                        color = Color.White.copy(alpha = 0.75f)
                    )
                }
            }

            Icon(
                imageVector = FontAwesomeIcons.Solid.ChartBar,
                contentDescription = null,
                tint = Color.White.copy(alpha = 0.08f),
                modifier = Modifier
                    .size(95.dp)
                    .align(Alignment.BottomEnd)
            )
        }
    }
}


//@Composable
//fun SmallStatCard(title: String, amount: String, icon: ImageVector, color: Color, modifier: Modifier) {
//    ElevatedCard(
//        colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
//        shape = RoundedCornerShape(20.dp),
//        modifier = modifier
//    ) {
//        Column(modifier = Modifier.padding(16.dp)) {
//            Icon(imageVector = icon, contentDescription = null, tint = color, modifier = Modifier.size(20.dp))
//            Spacer(modifier = Modifier.height(12.dp))
//            Text(title, fontSize = 12.sp, color = Color.Gray)
//            Text(amount, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = color)
//        }
//    }
//}


@Composable
fun SmallStatCard(
    title: String,
    amount: String,
    icon: ImageVector,
    color: Color,
    modifier: Modifier
) {
    ElevatedCard(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        color.copy(alpha = 0.11f),
                        RoundedCornerShape(13.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = color,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(Modifier.height(13.dp))

            Text(
                text = title,
                fontSize = 12.sp,
                color = Color.Gray,
                fontWeight = FontWeight.Medium
            )

            Spacer(Modifier.height(5.dp))

            Text(
                text = amount,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = color,
                maxLines = 2
            )
        }
    }
}


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

//@Composable
//fun ModernActionButton(text: String, icon: ImageVector, color: Color, onClick: () -> Unit, modifier: Modifier) {
//    Button(
//        onClick = onClick,
//        modifier = modifier.height(64.dp),
//        shape = RoundedCornerShape(16.dp),
//        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
//        elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)
//    ) {
//        Column(horizontalAlignment = Alignment.CenterHorizontally) {
//            Icon(imageVector = icon, contentDescription = null, tint = color, modifier = Modifier.size(24.dp))
//            Text(text, color = color, fontSize = 10.sp, fontWeight = FontWeight.Bold)
//        }
//    }
//}


@Composable
fun ModernActionButton(
    text: String,
    icon: ImageVector,
    color: Color,
    onClick: () -> Unit,
    modifier: Modifier
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier.height(68.dp),
        shape = RoundedCornerShape(17.dp),
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            color.copy(alpha = 0.18f)
        ),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = Color.White,
            contentColor = color
        ),
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(21.dp)
            )

            Text(
                text = text,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1
            )
        }
    }
}




@Composable
fun ExpenseCard(
    amount: Float,
    hasDay: Boolean
) {
    val expenseColor = colorResource(R.color.golden_orange)
    val navy = colorResource(R.color.oxford_navy)

    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = colorResource(R.color.golden_sand)
        ),
        elevation = CardDefaults.elevatedCardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(46.dp)
                    .background(
                        Color.White.copy(alpha = 0.65f),
                        RoundedCornerShape(14.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = FontAwesomeIcons.Solid.FileInvoiceDollar,
                    contentDescription = null,
                    tint = expenseColor,
                    modifier = Modifier.size(22.dp)
                )
            }

            Spacer(Modifier.width(14.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Total Expenses",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = navy.copy(alpha = 0.7f)
                )

                Spacer(Modifier.height(4.dp))

                Text(
                    text = formatAmount(if (hasDay) amount else 0f),
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = navy
                )
            }
        }
    }
}




private fun formatAmount(amount: Float): String {
    return java.text.NumberFormat
        .getNumberInstance(java.util.Locale.getDefault())
        .apply {
            minimumFractionDigits = 2
            maximumFractionDigits = 2
        }
        .format(amount)
}