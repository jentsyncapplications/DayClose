//
//@Composable
//fun HistoryProfitSection() {
//
//    val viewModel: DayDataViewModel = koinViewModel()
//
//    // Real data from Room through ViewModel
//    val summaries by viewModel
//        .getAllDaySummaries()
//        .collectAsState(initial = emptyList())
//
//    // Date and range states
//    var selectedDate by remember {
//        mutableStateOf(LocalDate.now())
//    }
//
//    val pagerState = rememberPagerState(
//        pageCount = { TimeRange.entries.size }
//    )
//
//    val coroutineScope = rememberCoroutineScope()
//
//    val activeRange = TimeRange.entries[pagerState.currentPage]
//
//    // Earliest recorded business day acts as the weekly anchor
//    val firstUseDate = remember(summaries) {
//        summaries.mapNotNull {
//            parseSummaryDate(it.date)
//        }.minOrNull() ?: LocalDate.now()
//    }
//
//    // Calculate the period being viewed
//    val period = remember(
//        activeRange,
//        selectedDate,
//        firstUseDate
//    ) {
//        getChartPeriod(
//            range = activeRange,
//            selectedDate = selectedDate,
//            firstUseDate = firstUseDate
//        )
//    }
//
//    // Extract actual closed-day profits
//    val chartPoints = remember(summaries, period) {
//        getProfitPoints(
//            summaries = summaries,
//            period = period
//        )
//    }
//
//    Column(
//        modifier = Modifier.fillMaxWidth()
//    ) {
//
//        // Weekly, Monthly and Yearly selector
//        TimeRangeToggle(
//            selectedIndex = pagerState.currentPage,
//            onSelected = { index ->
//                coroutineScope.launch {
//                    pagerState.animateScrollToPage(index)
//                }
//            }
//        )
//
//        // Previous and Next period navigation
//        DateNavigator(
//            currentDate = selectedDate,
//            range = activeRange,
//            firstUseDate = firstUseDate,
//            onDateChange = { selectedDate = it }
//        )
//
//        Spacer(modifier = Modifier.height(6.dp))
//
//        // Chart
//        HorizontalPager(
//            state = pagerState,
//            modifier = Modifier.fillMaxWidth(),
//            userScrollEnabled = false
//        ) { page ->
//
//            val range = TimeRange.entries[page]
//
//            // Use the period belonging to the displayed page
//            val pagePeriod = getChartPeriod(
//                range = range,
//                selectedDate = selectedDate,
//                firstUseDate = firstUseDate
//            )
//
//            val pagePoints = remember(
//                summaries,
//                pagePeriod
//            ) {
//                getProfitPoints(summaries, pagePeriod)
//            }
//
//            NetProfitLineChart(
//                points = pagePoints,
//                period = pagePeriod,
//                range = range
//            )
//        }
//    }
//}
