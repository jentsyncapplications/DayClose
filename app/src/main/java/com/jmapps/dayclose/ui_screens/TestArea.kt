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
//                title = { Text("Expense Details", color = Color.White) },
//                navigationIcon = {
//                    IconButton(onClick = { navController.popBackStack() }) {
//                        Icon(
//                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
//                            contentDescription = "Back",
//                            tint = Color.White
//                        )
//                    }
//                },
//                colors = TopAppBarDefaults.topAppBarColors(containerColor = backgroundColor)
//            )
//        }
//    ) { innerPadding ->
//
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(innerPadding)
//                .background(colorResource(id = R.color.gray))
//        ) {
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
//                                if (selected) colorResource(id = R.color.tabSelected)
//                                else colorResource(id = R.color.tabUnselected)
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
//            Spacer(modifier = Modifier.height(12.dp))
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
//                            .padding(horizontal = 16.dp, vertical = 8.dp)
//                    ) {
//                        expenses.forEach { expense ->
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
//                                        horizontalArrangement = Arrangement.SpaceBetween,
//                                        verticalAlignment = Alignment.CenterVertically
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
//        }
//    }
//}
//
//// --- Mock Expense Data Class ---
//data class Expense(
//    val name: String,
//    val amount: Int,
//    val description: String
//)
//
//@Preview(showBackground = true)
//@Composable
//fun DailyExpenseScreenPreview() {
//    DailyExpenseScreen(navController = rememberNavController(), dateId = "2026-01-29")
//}
