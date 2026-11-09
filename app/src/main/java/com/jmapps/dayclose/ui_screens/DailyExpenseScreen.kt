package com.jmapps.dayclose.ui_screens


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.jmapps.dayclose.R
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.text.style.TextAlign
import com.jmapps.dayclose.model.DayExpenseEntity
import com.jmapps.dayclose.viewmodel.DayExpenseViewModel
import org.koin.androidx.compose.koinViewModel




//@OptIn(ExperimentalMaterial3Api::class, ExperimentalPagerApi::class)
//@Composable
//fun DailyExpenseScreen(navController: NavController, dateId: String) {
//
//    val backgroundColor = colorResource(id = R.color.seaweed)
//
//    // --- Mock categories & expenses ---
//    val categories = listOf("Bills", "Food", "Transport", "Shopping", "Others")
//    val expensesData = mapOf(
//        "Bills" to listOf(
//            Expense("Electricity", 2500, "Monthly electricity bill"),
//            Expense("Water", 1200, "Monthly water bill"),
//            Expense("Electricity", 2500, "Monthly electricity bill"),
//            Expense("Water", 1200, "Monthly water bill"),
//            Expense("Electricity", 2500, "Monthly electricity bill"),
//            Expense("Water", 1200, "Monthly water bill"),
//            Expense("Electricity", 2500, "Monthly electricity bill"),
//            Expense("Water", 1200, "Monthly water bill")
//        ),
//        "Food" to listOf(
//            Expense("Lunch", 500, "Lunch at restaurant"),
//            Expense("Groceries", 2000, "Weekly groceries")
//        ),
//        "Transport" to listOf(
//            Expense("Taxi", 800, "Airport ride"),
//            Expense("Fuel", 1500, "Fuel for the car")
//        ),
//        "Shopping" to listOf(
//            Expense("Clothes", 3500, "Bought a jacket"),
//            Expense("Shoes", 2000, "Sneakers")
//        ),
//        "Others" to listOf(
//            Expense("Gift", 1000, "Birthday gift"),
//            Expense("Donation", 500, "Charity donation")
//        )
//    )
//
//    val pagerState = rememberPagerState()
//    val coroutineScope = rememberCoroutineScope()
//
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = {
//                    Text("Expense Details", color = Color.White)
//                },
//                navigationIcon = {
//                    IconButton(onClick = { navController.popBackStack() }) {
//                        Icon(
//                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
//                            contentDescription = "Back",
//                            tint = Color.White
//                        )
//                    }
//                },
//                colors = TopAppBarDefaults.topAppBarColors(
//                    containerColor = backgroundColor
//                )
//            )
//        }
//    ) { innerPadding ->
//
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(
//                        start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
//                    top = innerPadding.calculateTopPadding(),
//                    end = innerPadding.calculateEndPadding(LocalLayoutDirection.current),
//                    bottom =innerPadding.calculateEndPadding(LocalLayoutDirection.current)
//                        //innerPadding.calculateBottomPadding()
//                )
//                .background(colorResource(id = R.color.gray))
////                .verticalScroll(rememberScrollState())
//                .padding(16.dp)
//        ) {
//            ExpenseHeader(
//                date=dateId,
//            )
//
//            // 🔽 small space BELOW the tabs
//            Spacer(modifier = Modifier.height(12.dp))
//
//            // --- Scrollable Pill Tabs ---
//            ScrollableTabRow(
//                selectedTabIndex = pagerState.currentPage,
//                edgePadding = 12.dp,
//                containerColor = Color.Transparent,
//                divider = {}, // No divider
//                indicator = {} // No default indicator
//            ) {
//                categories.forEachIndexed { index, title ->
//                    val selected = pagerState.currentPage == index
//                    Tab(
//                        selected = selected,
//                        onClick = { coroutineScope.launch { pagerState.animateScrollToPage(index) } },
//                        modifier = Modifier
//                            .padding(horizontal = 4.dp)
//                            .clip(RoundedCornerShape(12.dp))
//                            .background(
//                                if (selected) colorResource(id = R.color.yale_blue)
//                                else colorResource(id = R.color.gray)
//                            )
//                    ) {
//                        Text(
//                            text = title,
//                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
//                            color = if (selected) Color.White else Color.Gray,
//                            fontWeight = FontWeight.Medium
//                        )
//                    }
//                }
//            }
//
////            Spacer(modifier = Modifier.height(12.dp))
//
//            // --- Horizontal Pager for Tab Content ---
//            HorizontalPager(
//                count = categories.size,
//                state = pagerState,
//                modifier = Modifier.fillMaxSize()
//            ) { page ->
//
//                val selectedCategory = categories[page]
//                val expenses = expensesData[selectedCategory] ?: emptyList()
//
//                if (expenses.isEmpty()) {
//                    Box(
//                        modifier = Modifier.fillMaxSize(),
//                        contentAlignment = Alignment.Center
//                    ) {
//                        Text(
//                            text = "No expenses in $selectedCategory",
//                            color = colorResource(id = R.color.gray),
//                            fontSize = 16.sp
//                        )
//                    }
//                } else {
//                    Column(
//                        modifier = Modifier
//                            .verticalScroll(rememberScrollState())
//                            .padding(horizontal = 6.dp, vertical = 8.dp)
//                    ) {
//                        expenses.forEach { expense ->
//
//                            // 🔽 small space BELOW the tabs
////                            Spacer(modifier = Modifier.height(12.dp))
//                            Card(
//                                modifier = Modifier
//                                    .fillMaxWidth()
//                                    .padding(vertical = 8.dp)
//                                    .clickable { /* Navigate to details */ },
//                                shape = RoundedCornerShape(8.dp),
//                                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
//                                colors = CardDefaults.cardColors(containerColor = Color.White)
//                            ) {
//                                Column(modifier = Modifier.padding(16.dp)) {
//                                    Row(
//                                        modifier = Modifier.fillMaxWidth(),
//                                        horizontalArrangement = Arrangement.SpaceBetween
////                                        verticalAlignment = Alignment.CenterStart as Alignment.Vertical
//                                    ) {
//                                        Column {
//                                            Text(
//                                                text = "Category: $selectedCategory",
//                                                fontSize = 14.sp,
//                                                fontWeight = FontWeight.SemiBold,
//                                                color = colorResource(id = R.color.gray)
//                                            )
//                                            Spacer(modifier = Modifier.height(4.dp))
//                                            Text(
//                                                text = "KES ${expense.amount}",
//                                                fontSize = 22.sp,
//                                                fontWeight = FontWeight.Bold,
//                                                color = colorResource(id = R.color.yale_blue)
//                                            )
//                                        }
//                                    }
//
//                                    Spacer(modifier = Modifier.height(12.dp))
//
//                                    Text(
//                                        text = expense.description,
//                                        fontSize = 16.sp,
//                                        color = colorResource(id = R.color.gray),
//                                        maxLines = 2,
//                                        overflow = TextOverflow.Ellipsis
//                                    )
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//
//        }
//    }
//}


@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalPagerApi::class,
    ExperimentalFoundationApi::class
)
@Composable
fun DailyExpenseScreen(
    navController: NavController,
    dateId: String
) {
    val dayExpenseViewModel: DayExpenseViewModel = koinViewModel()

    // App colors
    val backgroundColor = colorResource(R.color.seaweed)
    val screenColor = colorResource(R.color.gray)
    val primaryColor = colorResource(R.color.yale_blue)

    // Observe expenses for the selected date
    val expenses by dayExpenseViewModel
        .getExpensesByDate(dateId)
        .collectAsState(initial = emptyList())

    // Create category tabs from actual expense records
    val categories = remember(expenses) {
        expenses.map { it.expenseCategory }
            .distinct()
            .ifEmpty {
                listOf("Bills", "Food", "Transport", "Shopping", "Others")
            }
    }

    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    // Store the selected expense using its unique expenseId
    var selectedExpenseId by remember {
        mutableStateOf<String?>(null)
    }

    // Get the selected expense from the observed list
    val selectedExpense = expenses.find {
        it.expenseId == selectedExpenseId
    }

    // Clear selection when the selected category changes
    LaunchedEffect(pagerState.currentPage) {
        selectedExpenseId = null
    }

    Scaffold(
        containerColor = screenColor,

        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Expense Details",
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() }
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = backgroundColor
                )
            )
        },

        // Bottom management bar appears only when an expense is selected
        bottomBar = {
            if (selectedExpense != null) {
                ExpenseManagementBar(
                    onEdit = {
                        val expenseId = selectedExpense.expenseId

                        // TODO: Open your edit expense screen/dialog
                        // Pass expenseId to retrieve the expense.
                    },
                    onDelete = {
                        val expenseId = selectedExpense.expenseId

                        // TODO: Call your ViewModel delete function
                        // Example: dayExpenseViewModel.deleteExpense(expenseId)

                        selectedExpenseId = null
                    },
                    onDismiss = {
                        selectedExpenseId = null
                    }
                )
            }
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(screenColor)
                .padding(
                    start = innerPadding.calculateStartPadding(
                        LocalLayoutDirection.current
                    ),
                    end = innerPadding.calculateEndPadding(
                        LocalLayoutDirection.current
                    ),
                    top = innerPadding.calculateTopPadding(),
                    bottom = innerPadding.calculateBottomPadding()
                )
                .padding(horizontal = 16.dp),

            // Always start content from the top
            verticalArrangement = Arrangement.Top
        ) {

            // Daily expense date header
            ExpenseHeader(date = dateId)

            Spacer(modifier = Modifier.height(14.dp))

            // Category tabs
            ScrollableTabRow(
                selectedTabIndex = pagerState.currentPage,
                edgePadding = 0.dp,
                containerColor = Color.Transparent,
                divider = {},
                indicator = {}
            ) {
                categories.forEachIndexed { index, category ->

                    val selected = pagerState.currentPage == index

                    Tab(
                        selected = selected,
                        onClick = {
                            selectedExpenseId = null

                            coroutineScope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        },
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(
                                if (selected) primaryColor
                                else Color.White
                            )
                    ) {
                        Text(
                            text = category,
                            modifier = Modifier.padding(
                                horizontal = 16.dp,
                                vertical = 11.dp
                            ),
                            color = if (selected) {
                                Color.White
                            } else {
                                colorResource(R.color.oxford_navy)
                            },
                            fontWeight = if (selected) {
                                FontWeight.SemiBold
                            } else {
                                FontWeight.Medium
                            },
                            fontSize = 13.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Expense cards for each category
            HorizontalPager(
                count = categories.size,
                state = pagerState,
                modifier = Modifier.weight(1f)
            ) { page ->

                val selectedCategory = categories[page]

                val categoryExpenses = expenses.filter {
                    it.expenseCategory == selectedCategory
                }

                if (categoryExpenses.isEmpty()) {

                    // Empty category state
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                Icons.Default.Receipt,
                                contentDescription = null,
                                tint = Color.Gray,
                                modifier = Modifier.size(36.dp)
                            )

                            Text(
                                text = "No expenses in $selectedCategory",
                                color = Color.Gray,
                                fontSize = 14.sp
                            )
                        }
                    }

                } else {

                    // Cards are arranged from the top, not the center
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.Top
                    ) {

                        categoryExpenses.forEach { expense ->

                            val isSelected =
                                selectedExpenseId == expense.expenseId

                            ExpenseItemCard(
                                expense = expense,
                                isSelected = isSelected,

                                onLongPress = {
                                    selectedExpenseId =
                                        if (isSelected) null
                                        else expense.expenseId
                                },

                                onClick = {
                                    // Normal click can be used for details.
                                    if (selectedExpenseId != null) {
                                        selectedExpenseId =
                                            if (isSelected) null
                                            else expense.expenseId
                                    }
                                }
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }
            }
        }
    }
}






@Preview(showBackground = true)
@Composable
fun DailyExpenseScreenPreview() {
    DailyExpenseScreen(navController = rememberNavController(),"2026/01/03")
}












@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ExpenseItemCard(
    expense: DayExpenseEntity,
    isSelected: Boolean,
    onLongPress: () -> Unit,
    onClick: () -> Unit
) {
    val primaryColor = colorResource(R.color.yale_blue)
    val borderColor = colorResource(R.color.seaweed)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongPress
            ),
        shape = RoundedCornerShape(14.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isSelected) 1.dp else 2.dp
        ),
        border = if (isSelected) {
            BorderStroke(
                width = 1.5.dp,
                color = borderColor
            )
        } else null,
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) {
                borderColor.copy(alpha = 0.08f)
            } else {
                Color.White
            }
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            // Category and amount
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = expense.expenseCategory,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(5.dp))

                    Text(
                        text = "KES ${"%,.2f".format(expense.totalExpense)}",
                        fontSize = 21.sp,
                        fontWeight = FontWeight.Bold,
                        color = primaryColor
                    )
                }

                // Selection indicator
                if (isSelected) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Selected",
                        tint = borderColor,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            // Expense description
            if (!expense.expenseDescription.isNullOrBlank()) {
                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = expense.expenseDescription,
                    fontSize = 14.sp,
                    color = colorResource(R.color.oxford_navy),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}







@Composable
fun ExpenseManagementBar(
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    onDismiss: () -> Unit
) {
    Surface(
        color = Color.White,
        shadowElevation = 12.dp,
        tonalElevation = 3.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(
                    horizontal = 20.dp,
                    vertical = 12.dp
                ),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // Close selection
            IconButton(
                onClick = onDismiss,
                modifier = Modifier.size(44.dp)
            ) {
                Icon(
                    Icons.Default.Close,
                    contentDescription = "Cancel selection",
                    tint = Color.Gray,
                    modifier = Modifier.size(22.dp)
                )
            }

            // Edit action
            OutlinedButton(
                onClick = onEdit,
                modifier = Modifier
                    .weight(1f)
                    .height(46.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = colorResource(R.color.yale_blue)
                )
            ) {
                Icon(
                    Icons.Default.Edit,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    "Edit",
                    fontWeight = FontWeight.SemiBold
                )
            }

            // Delete action
            Button(
                onClick = onDelete,
                modifier = Modifier
                    .weight(1f)
                    .height(46.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFD32F2F),
                    contentColor = Color.White
                )
            ) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    "Delete",
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
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