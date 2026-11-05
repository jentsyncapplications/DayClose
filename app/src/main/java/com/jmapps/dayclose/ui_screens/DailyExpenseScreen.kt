package com.jmapps.dayclose.ui_screens




import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.jmapps.dayclose.R
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.jmapps.dayclose.utils.getGreeting
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.ArrowLeft
import compose.icons.fontawesomeicons.solid.DollarSign
import compose.icons.fontawesomeicons.solid.FileInvoiceDollar
import kotlinx.coroutines.launch
import kotlin.text.get


//@Composable
//fun DailyExpenseScreen(navController: NavController) {
//
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            // 👇 This background will go BEHIND the status bar
//            .background(colorResource(id = R.color.gray))
//    ) {
//
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//
//                // 👇 THIS is the key line
//                // It pushes CONTENT below the status bar
//                .statusBarsPadding()
//
//                // Scrollable content
//                .verticalScroll(rememberScrollState())
//
//                // Your own spacing
//                .padding(horizontal = 12.dp, vertical = 16.dp)
//        ) {
//
//            ExpenseHeader(
//                date="29/01/2026",
//            )
//
//
//
//
//
//
//
//        }
//    }
//}





@OptIn(ExperimentalMaterial3Api::class, ExperimentalPagerApi::class)
@Composable
fun DailyExpenseScreen(navController: NavController, dateId: String) {

    val backgroundColor = colorResource(id = R.color.seaweed)

    // --- Mock categories & expenses ---
    val categories = listOf("Bills", "Food", "Transport", "Shopping", "Others")
    val expensesData = mapOf(
        "Bills" to listOf(
            Expense("Electricity", 2500, "Monthly electricity bill"),
            Expense("Water", 1200, "Monthly water bill"),
            Expense("Electricity", 2500, "Monthly electricity bill"),
            Expense("Water", 1200, "Monthly water bill"),
            Expense("Electricity", 2500, "Monthly electricity bill"),
            Expense("Water", 1200, "Monthly water bill"),
            Expense("Electricity", 2500, "Monthly electricity bill"),
            Expense("Water", 1200, "Monthly water bill")
        ),
        "Food" to listOf(
            Expense("Lunch", 500, "Lunch at restaurant"),
            Expense("Groceries", 2000, "Weekly groceries")
        ),
        "Transport" to listOf(
            Expense("Taxi", 800, "Airport ride"),
            Expense("Fuel", 1500, "Fuel for the car")
        ),
        "Shopping" to listOf(
            Expense("Clothes", 3500, "Bought a jacket"),
            Expense("Shoes", 2000, "Sneakers")
        ),
        "Others" to listOf(
            Expense("Gift", 1000, "Birthday gift"),
            Expense("Donation", 500, "Charity donation")
        )
    )

    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Expense Details", color = Color.White)
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = backgroundColor
                )
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                        start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
                    top = innerPadding.calculateTopPadding(),
                    end = innerPadding.calculateEndPadding(LocalLayoutDirection.current),
                    bottom =innerPadding.calculateEndPadding(LocalLayoutDirection.current)
                        //innerPadding.calculateBottomPadding()
                )
                .background(colorResource(id = R.color.gray))
//                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            ExpenseHeader(
                date=dateId,
            )

            // 🔽 small space BELOW the tabs
            Spacer(modifier = Modifier.height(12.dp))

            // --- Scrollable Pill Tabs ---
            ScrollableTabRow(
                selectedTabIndex = pagerState.currentPage,
                edgePadding = 12.dp,
                containerColor = Color.Transparent,
                divider = {}, // No divider
                indicator = {} // No default indicator
            ) {
                categories.forEachIndexed { index, title ->
                    val selected = pagerState.currentPage == index
                    Tab(
                        selected = selected,
                        onClick = { coroutineScope.launch { pagerState.animateScrollToPage(index) } },
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(
                                if (selected) colorResource(id = R.color.yale_blue)
                                else colorResource(id = R.color.gray)
                            )
                    ) {
                        Text(
                            text = title,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
                            color = if (selected) Color.White else Color.Gray,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

//            Spacer(modifier = Modifier.height(12.dp))

            // --- Horizontal Pager for Tab Content ---
            HorizontalPager(
                count = categories.size,
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->

                val selectedCategory = categories[page]
                val expenses = expensesData[selectedCategory] ?: emptyList()

                if (expenses.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No expenses in $selectedCategory",
                            color = colorResource(id = R.color.gray),
                            fontSize = 16.sp
                        )
                    }
                } else {
                    Column(
                        modifier = Modifier
                            .verticalScroll(rememberScrollState())
                            .padding(horizontal = 6.dp, vertical = 8.dp)
                    ) {
                        expenses.forEach { expense ->

                            // 🔽 small space BELOW the tabs
//                            Spacer(modifier = Modifier.height(12.dp))
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp)
                                    .clickable { /* Navigate to details */ },
                                shape = RoundedCornerShape(8.dp),
                                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.White)
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
//                                        verticalAlignment = Alignment.CenterStart as Alignment.Vertical
                                    ) {
                                        Column {
                                            Text(
                                                text = "Category: $selectedCategory",
                                                fontSize = 14.sp,
                                                fontWeight = FontWeight.SemiBold,
                                                color = colorResource(id = R.color.gray)
                                            )
                                            Spacer(modifier = Modifier.height(4.dp))
                                            Text(
                                                text = "KES ${expense.amount}",
                                                fontSize = 22.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = colorResource(id = R.color.yale_blue)
                                            )
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(12.dp))

                                    Text(
                                        text = expense.description,
                                        fontSize = 16.sp,
                                        color = colorResource(id = R.color.gray),
                                        maxLines = 2,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                            }
                        }
                    }
                }
            }


//            repeat(5){
//                Card(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(horizontal = 0.dp, vertical = 8.dp)
//                        .clickable { /* Handle click */ },
//                    shape = RoundedCornerShape(8.dp),
//                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
//                    colors = CardDefaults.cardColors(containerColor = Color.White)
//                ) {
//                    Column(modifier = Modifier.padding(16.dp)) {
//
//                        // Top Row: Amount & Category
//                        Row(
//                            modifier = Modifier.fillMaxWidth(),
//                            horizontalArrangement = Arrangement.SpaceBetween,
//                            verticalAlignment = Alignment.CenterVertically
//                        ) {
//                            Column {
//                                Text(
//                                    text = "category: bills",
//                                    fontSize = 14.sp,
//                                    fontWeight = FontWeight.SemiBold,
//                                    color = colorResource(id = R.color.gray)
//                                )
//                                Spacer(modifier = Modifier.height(4.dp))
//                                Text(
//                                    text = "KES 5000",
//                                    fontSize = 22.sp,
//                                    fontWeight = FontWeight.Bold,
//                                    color = colorResource(id = R.color.yale_blue)
//                                )
//                            }
//
//
//                        }
//
//                        Spacer(modifier = Modifier.height(12.dp))
//
//                        // Description
//                        Text(
//                            text = "expenses Description",
//                            fontSize = 16.sp,
//                            color = colorResource(id = R.color.gray),
//                            maxLines = 2,
//                            overflow = TextOverflow.Ellipsis
//                        )
//
//                        Spacer(modifier = Modifier.height(8.dp))
//
//
//                    }
//                }
//            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DailyExpenseScreenPreview() {
    DailyExpenseScreen(navController = rememberNavController(),"2026/01/03")
}


@Composable
fun ExpenseHeader(
    date: String,
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
                text = "Daily Expenses for:",
                fontSize = 14.sp,
                color = colorResource(id = R.color.oxford_navy)
            )

            Text(
                text = date,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.seaweed)
            )
        }

    }
}


// --- Mock data class ---
data class Expense(
    val name: String,
    val amount: Int,
    val description: String
)