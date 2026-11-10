//
//@Composable
//fun HistoryScreen(
//    navController: NavController
//) {
//    val viewModel: DayCloseViewModel = koinViewModel()
//
//    // Observe all business-day summaries
//    val summaries by viewModel
//        .getAllDaySummaries()
//        .collectAsState(initial = emptyList())
//
//    var searchQuery by remember { mutableStateOf("") }
//    var currentPage by remember { mutableIntStateOf(0) }
//
//    val itemsPerPage = 4
//
//    // Search and sort records by newest first
//    val filteredItems = remember(summaries, searchQuery) {
//        summaries
//            .sortedByDescending { it.timestamp }
//            .filter {
//                it.date.contains(searchQuery.trim(), ignoreCase = true)
//            }
//    }
//
//    // Calculate pagination
//    val totalPages = if (filteredItems.isEmpty()) {
//        0
//    } else {
//        (filteredItems.size + itemsPerPage - 1) / itemsPerPage
//    }
//
//    // Reset page when the search query changes
//    LaunchedEffect(searchQuery) {
//        currentPage = 0
//    }
//
//    // Keep page within valid bounds after database updates
//    LaunchedEffect(totalPages) {
//        if (totalPages == 0) {
//            currentPage = 0
//        } else if (currentPage >= totalPages) {
//            currentPage = totalPages - 1
//        }
//    }
//
//    // Get records for the current page
//    val pagedItems = remember(filteredItems, currentPage) {
//        filteredItems.drop(currentPage * itemsPerPage)
//            .take(itemsPerPage)
//    }
//
//    Column(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(horizontal = 12.dp)
//    ) {
//
//        // Search field
//        OutlinedTextField(
//            value = searchQuery,
//            onValueChange = { searchQuery = it },
//            placeholder = {
//                Text(
//                    "Search by date... yyyy-mm-dd",
//                    fontSize = 13.sp
//                )
//            },
//            leadingIcon = {
//                Icon(
//                    imageVector = FontAwesomeIcons.Solid.Search,
//                    contentDescription = "Search",
//                    tint = colorResource(R.color.oxford_navy),
//                    modifier = Modifier.size(19.dp)
//                )
//            },
//            trailingIcon = {
//                if (searchQuery.isNotEmpty()) {
//                    IconButton(
//                        onClick = { searchQuery = "" }
//                    ) {
//                        Icon(
//                            Icons.Default.Close,
//                            contentDescription = "Clear search",
//                            modifier = Modifier.size(18.dp)
//                        )
//                    }
//                }
//            },
//            modifier = Modifier
//                .fillMaxWidth()
//                .heightIn(min = 54.dp),
//            singleLine = true,
//            shape = RoundedCornerShape(12.dp),
//            colors = OutlinedTextFieldDefaults.colors(
//                focusedContainerColor = Color.White,
//                unfocusedContainerColor = Color.White,
//                focusedBorderColor = colorResource(R.color.seaweed),
//                unfocusedBorderColor = colorResource(R.color.border_color),
//                cursorColor = colorResource(R.color.seaweed)
//            )
//        )
//
//        Spacer(modifier = Modifier.height(8.dp))
//
//        // History cards
//        if (pagedItems.isEmpty()) {
//
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(vertical = 40.dp),
//                contentAlignment = Alignment.Center
//            ) {
//                Column(
//                    horizontalAlignment = Alignment.CenterHorizontally
//                ) {
//                    Icon(
//                        Icons.Default.Receipt,
//                        contentDescription = null,
//                        modifier = Modifier.size(36.dp),
//                        tint = colorResource(R.color.gray)
//                    )
//
//                    Spacer(modifier = Modifier.height(8.dp))
//
//                    Text(
//                        text = if (searchQuery.isBlank()) {
//                            "No business records yet"
//                        } else {
//                            "No matching records found"
//                        },
//                        color = colorResource(R.color.gray),
//                        fontSize = 14.sp
//                    )
//                }
//            }
//
//        } else {
//
//            pagedItems.forEach { item ->
//
//                DayCloseCard(
//                    item = item,
//                    onViewExpenses = {
//                        // Navigate using the unique business-day ID
//                        navController.navigate(
//                            "daily_expense/${item.dayId}"
//                        )
//                    }
//                )
//            }
//        }
//
//        Spacer(modifier = Modifier.height(8.dp))
//
//        // Pagination controls
//        if (totalPages > 1) {
//
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//
//                OutlinedButton(
//                    onClick = {
//                        if (currentPage > 0) currentPage--
//                    },
//                    enabled = currentPage > 0,
//                    shape = RoundedCornerShape(10.dp),
//                    contentPadding = PaddingValues(
//                        horizontal = 12.dp,
//                        vertical = 6.dp
//                    )
//                ) {
//                    Icon(
//                        Icons.AutoMirrored.Filled.ArrowBack,
//                        contentDescription = null,
//                        modifier = Modifier.size(16.dp)
//                    )
//                    Spacer(modifier = Modifier.width(4.dp))
//                    Text("Previous", fontSize = 12.sp)
//                }
//
//                Text(
//                    text = "${currentPage + 1} / $totalPages",
//                    fontSize = 13.sp,
//                    fontWeight = FontWeight.SemiBold,
//                    color = colorResource(R.color.oxford_navy)
//                )
//
//                OutlinedButton(
//                    onClick = {
//                        if (currentPage < totalPages - 1) currentPage++
//                    },
//                    enabled = currentPage < totalPages - 1,
//                    shape = RoundedCornerShape(10.dp),
//                    contentPadding = PaddingValues(
//                        horizontal = 12.dp,
//                        vertical = 6.dp
//                    )
//                ) {
//                    Text("Next", fontSize = 12.sp)
//                    Spacer(modifier = Modifier.width(4.dp))
//                    Icon(
//                        Icons.AutoMirrored.Filled.ArrowForward,
//                        contentDescription = null,
//                        modifier = Modifier.size(16.dp)
//                    )
//                }
//            }
//        }
//    }
//}
