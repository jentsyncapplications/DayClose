package com.jmapps.dayclose.ui_screens

import android.graphics.Paint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.jmapps.dayclose.R
import com.jmapps.dayclose.model.DaySummary
import com.jmapps.dayclose.model.parseSummaryDate
import com.jmapps.dayclose.utils.getChartPeriod

import com.jmapps.dayclose.viewmodel.DayDataViewModel
import kotlinx.coroutines.launch
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.text.style.TextAlign
import com.jmapps.dayclose.model.ChartData
import com.jmapps.dayclose.utils.generateFakeDaySummaries
import com.jmapps.dayclose.utils.getChartData

import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.MoneyBillAlt
import compose.icons.fontawesomeicons.solid.MoneyBillWaveAlt
import compose.icons.fontawesomeicons.solid.Search
import org.koin.androidx.compose.koinViewModel

import java.time.LocalDate
import java.time.format.DateTimeFormatter


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(navController: NavController) {
    // 1. State for the Date and Range
    ///////////////


    val dayDataViewModel: DayDataViewModel = koinViewModel()

    // Observe all business-day summaries
    val summaries by dayDataViewModel
        .getAllDaySummaries()
        .collectAsState(initial = emptyList())

//    // Temporary fake data for chart testing
//    val summaries = remember {
//        generateFakeDaySummaries(
//            endDate = LocalDate.now(),
//            years = 4
//        )
//    }

    // Date and range states
    var selectedDate by remember {
        mutableStateOf(LocalDate.now())
    }

    val pagerState = rememberPagerState(
        pageCount = { TimeRange.entries.size }
    )

    val coroutineScope = rememberCoroutineScope()

    val activeRange = TimeRange.entries[pagerState.currentPage]

    // Earliest recorded business day acts as the weekly anchor
    val firstUseDate = remember(summaries) {
        summaries.mapNotNull {
            parseSummaryDate(it.date)
        }.minOrNull() ?: LocalDate.now()
    }

    // Calculate the period being viewed
    val period = remember(
        activeRange,
        selectedDate,
        firstUseDate
    ) {
        getChartPeriod(
            range = activeRange,
            selectedDate = selectedDate,
            firstUseDate = firstUseDate
        )
    }



    var searchQuery by remember { mutableStateOf("") }
    var currentPage by remember { mutableIntStateOf(0) }

    val itemsPerPage = 4

    // Search and sort records by newest first
    val filteredItems = remember(summaries, searchQuery) {
        summaries
            .sortedByDescending { it.timestamp }
            .filter {
                it.date.contains(searchQuery.trim(), ignoreCase = true)
            }
    }

    // Calculate pagination
    val totalPages = if (filteredItems.isEmpty()) {
        0
    } else {
        (filteredItems.size + itemsPerPage - 1) / itemsPerPage
    }

    // Reset page when the search query changes
    LaunchedEffect(searchQuery) {
        currentPage = 0
    }

    // Keep page within valid bounds after database updates
    LaunchedEffect(totalPages) {
        if (totalPages == 0) {
            currentPage = 0
        } else if (currentPage >= totalPages) {
            currentPage = totalPages - 1
        }
    }

    // Get records for the current page
    val pagedItems = remember(filteredItems, currentPage) {
        filteredItems.drop(currentPage * itemsPerPage)
            .take(itemsPerPage)
    }


    ///////




    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.border_color))

            .verticalScroll(rememberScrollState())

    ) {


        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(horizontal = 12.dp, vertical = 16.dp)
        ) {
        HistIntroHeader()

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {

                TimeRangeToggle(
                    selectedIndex = pagerState.currentPage,
                    onSelected = { index ->
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    }
                )

                DateNavigator(
                    currentDate = selectedDate,
                    range = activeRange,
                    firstUseDate = firstUseDate,
                    onDateChange = {
                        selectedDate = it
                    }
                )

                Spacer(modifier = Modifier.height(6.dp))

                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxWidth(),
                    userScrollEnabled = false
                ) { page ->

                    val range = TimeRange.entries[page]

                    // Calculate data specifically for this page.
                    val pageChartData = remember(
                        summaries,
                        range,
                        selectedDate,
                        firstUseDate
                    ) {
                        getChartData(
                            summaries = summaries,
                            range = range,
                            selectedDate = selectedDate,
                            firstUseDate = firstUseDate
                        )
                    }

                    NetProfitLineChart(
                        chartData = pageChartData,
                        range = range,
                        selectedDate = selectedDate
                    )
                }
            }


            Spacer(modifier = Modifier.height(8.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
            ) {

                // Search field
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = {
                        Text(
                            "Search by date... yyyy-mm-dd",
                            fontSize = 13.sp
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = FontAwesomeIcons.Solid.Search,
                            contentDescription = "Search",
                            tint = colorResource(R.color.oxford_navy),
                            modifier = Modifier.size(19.dp)
                        )
                    },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(
                                onClick = { searchQuery = "" }
                            ) {
                                Icon(
                                    Icons.Default.Close,
                                    contentDescription = "Clear search",
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 54.dp),
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedBorderColor = colorResource(R.color.seaweed),
                        unfocusedBorderColor = colorResource(R.color.border_color),
                        cursorColor = colorResource(R.color.seaweed)
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                // History cards
                if (pagedItems.isEmpty()) {

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 40.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                Icons.Default.Receipt,
                                contentDescription = null,
                                modifier = Modifier.size(36.dp),
                                tint = colorResource(R.color.gray)
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = if (searchQuery.isBlank()) {
                                    "No business records yet"
                                } else {
                                    "No matching records found"
                                },
                                color = colorResource(R.color.gray),
                                fontSize = 14.sp
                            )
                        }
                    }

                } else {

                    pagedItems.forEach { item ->

                        DayCloseCard(
                            item = item,
                            onViewExpenses = {
                                // Navigate using the unique business-day ID

                                navController.navigate("dailyExpense/${item.date}")
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Pagination controls
                if (totalPages > 1) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        OutlinedButton(
                            onClick = {
                                if (currentPage > 0) currentPage--
                            },
                            enabled = currentPage > 0,
                            shape = RoundedCornerShape(10.dp),
                            contentPadding = PaddingValues(
                                horizontal = 12.dp,
                                vertical = 6.dp
                            )
                        ) {
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Previous", fontSize = 12.sp)
                        }

                        Text(
                            text = "${currentPage + 1} / $totalPages",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = colorResource(R.color.oxford_navy)
                        )

                        OutlinedButton(
                            onClick = {
                                if (currentPage < totalPages - 1) currentPage++
                            },
                            enabled = currentPage < totalPages - 1,
                            shape = RoundedCornerShape(10.dp),
                            contentPadding = PaddingValues(
                                horizontal = 12.dp,
                                vertical = 6.dp
                            )
                        ) {
                            Text("Next", fontSize = 12.sp)
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }
            }


    }
    }
}






@Composable
fun NetProfitLineChart(
    chartData: ChartData,
    range: TimeRange,
    selectedDate: LocalDate
) {
    val seaweed = colorResource(R.color.seaweed)
    val navy = colorResource(R.color.oxford_navy)
    val gridColor = colorResource(R.color.border_color)

    val formatter = remember {
        java.text.DecimalFormat("#,##0")
    }

    val title = when (range) {
        TimeRange.WEEKLY -> "Weekly Net Profit"
        TimeRange.MONTHLY -> "Monthly Net Profit"
        TimeRange.YEARLY -> "Yearly Net Profit"
    }

    val subtitle = when (range) {
        TimeRange.WEEKLY ->
            selectedDate.format(
                DateTimeFormatter.ofPattern("MMMM yyyy")
            )

        TimeRange.MONTHLY ->
            selectedDate.format(
                DateTimeFormatter.ofPattern("MMMM yyyy")
            )

        TimeRange.YEARLY ->
            selectedDate.year.toString()
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Color.White,
                RoundedCornerShape(14.dp)
            )
            .padding(12.dp)
    ) {

        Text(
            text = title,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = navy
        )

        Spacer(Modifier.height(4.dp))

        Text(
            text = subtitle,
            fontSize = 11.sp,
            color = Color.Gray
        )

        Spacer(Modifier.height(12.dp))

        if (chartData.points.isEmpty()) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No closed-day profits for this period",
                    color = Color.Gray,
                    fontSize = 13.sp
                )
            }

        } else {

            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
            ) {

                val leftPadding = 58.dp.toPx()
                val rightPadding = 12.dp.toPx()
                val topPadding = 16.dp.toPx()
                val bottomPadding = 35.dp.toPx()

                val chartWidth = size.width -
                        leftPadding - rightPadding

                val chartHeight = size.height -
                        topPadding - bottomPadding

                val slotCount = chartData.slotCount

                // Calculate X using fixed chart positions.
                fun getX(slot: Int): Float {

                    val position = if (slotCount > 1) {
                        slot.toFloat() / (slotCount - 1)
                    } else {
                        0.5f
                    }

                    return leftPadding +
                            position * chartWidth
                }

                // Include zero in the Y-axis.
                val minimum = minOf(
                    chartData.points.minOf { it.value },
                    0f
                )

                val maximum = maxOf(
                    chartData.points.maxOf { it.value },
                    0f
                )

                val difference = (maximum - minimum)
                    .takeIf { it > 0f } ?: 1f

                val padding = difference * 0.15f

                val minY = minimum - padding
                val maxY = maximum + padding

                fun getY(value: Float): Float {

                    return topPadding +
                            ((maxY - value) / (maxY - minY)) *
                            chartHeight
                }

                val textPaint = android.graphics.Paint(
                    android.graphics.Paint.ANTI_ALIAS_FLAG
                ).apply {
                    color = android.graphics.Color.rgb(
                        5, 47, 95
                    )
                    textSize = 10.sp.toPx()
                    textAlign = android.graphics.Paint.Align.RIGHT
                }

                val datePaint = android.graphics.Paint(
                    android.graphics.Paint.ANTI_ALIAS_FLAG
                ).apply {
                    color = android.graphics.Color.GRAY
                    textSize = 9.sp.toPx()
                    textAlign = android.graphics.Paint.Align.CENTER
                }

                // Horizontal grid lines.
                for (i in 0..4) {

                    val value = maxY -
                            ((maxY - minY) * i / 4f)

                    val y = getY(value)

                    drawLine(
                        color = gridColor,
                        start = Offset(leftPadding, y),
                        end = Offset(
                            leftPadding + chartWidth,
                            y
                        ),
                        strokeWidth = 0.8.dp.toPx()
                    )

                    drawContext.canvas.nativeCanvas.drawText(
                        formatter.format(value),
                        leftPadding - 8.dp.toPx(),
                        y + 4.dp.toPx(),
                        textPaint
                    )
                }

                // Zero-profit reference line.
                if (minY < 0f && maxY > 0f) {

                    val zeroY = getY(0f)

                    drawLine(
                        color = navy.copy(alpha = 0.4f),
                        start = Offset(leftPadding, zeroY),
                        end = Offset(
                            leftPadding + chartWidth,
                            zeroY
                        ),
                        strokeWidth = 1.2.dp.toPx()
                    )
                }

                // X-axis labels.
                // Avoid overcrowding in monthly charts.
                val labelSlots = when (range) {

                    TimeRange.WEEKLY ->
                        chartData.axisLabels.indices.toList()

                    TimeRange.MONTHLY ->
                        chartData.axisLabels.indices.filter {
                            it % 5 == 0 ||
                                    it == slotCount - 1
                        }

                    TimeRange.YEARLY ->
                        chartData.axisLabels.indices.toList()
                }

                labelSlots.forEach { slot ->

                    val x = getX(slot)

                    drawContext.canvas.nativeCanvas.drawText(
                        chartData.axisLabels[slot],
                        x,
                        size.height - 5.dp.toPx(),
                        datePaint
                    )
                }

                // Sort points by their fixed positions.
                val sortedPoints = chartData.points
                    .sortedBy { it.slot }

                // Draw connecting line.
                val path = Path()

                sortedPoints.forEachIndexed { index, point ->

                    val position = Offset(
                        getX(point.slot),
                        getY(point.value)
                    )

                    if (index == 0) {
                        path.moveTo(position.x, position.y)
                    } else {
                        path.lineTo(position.x, position.y)
                    }
                }

                drawPath(
                    path = path,
                    color = seaweed,
                    style = Stroke(
                        width = 1.8.dp.toPx(),
                        cap = StrokeCap.Round
                    )
                )

                // Draw a circle at every available data point.
                sortedPoints.forEach { point ->

                    val center = Offset(
                        getX(point.slot),
                        getY(point.value)
                    )

                    drawCircle(
                        color = Color.White,
                        radius = 5.dp.toPx(),
                        center = center
                    )

                    drawCircle(
                        color = if (point.value < 0f) {
                            Color(0xFFD32F2F)
                        } else {
                            seaweed
                        },
                        radius = 5.dp.toPx(),
                        center = center,
                        style = Stroke(
                            width = 2.dp.toPx()
                        )
                    )
                }
            }
        }
    }
}





@Composable
fun DateNavigator(
    currentDate: LocalDate,
    range: TimeRange,
    firstUseDate: LocalDate,
    onDateChange: (LocalDate) -> Unit
) {
    val period = getChartPeriod(
        range = range,
        selectedDate = currentDate,
        firstUseDate = firstUseDate
    )

    val today = LocalDate.now()

    // Do not navigate before the user's first recorded day
    val canGoPrevious = period.startDate > firstUseDate

    // Do not navigate into future periods
    val canGoNext = period.endDate < today

    val formatter = when (range) {
        TimeRange.WEEKLY ->
            DateTimeFormatter.ofPattern("dd MMM")

        TimeRange.MONTHLY ->
            DateTimeFormatter.ofPattern("MMMM yyyy")

        TimeRange.YEARLY ->
            DateTimeFormatter.ofPattern("yyyy")
    }

    val displayDate = when (range) {
        TimeRange.WEEKLY ->
            "${period.startDate.format(formatter)} - " +
                    period.endDate.format(
                        DateTimeFormatter.ofPattern("dd MMM yyyy")
                    )

        TimeRange.MONTHLY ->
            period.startDate.format(formatter)

        TimeRange.YEARLY ->
            period.startDate.format(formatter)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        IconButton(
            enabled = canGoPrevious,
            onClick = {
                onDateChange(
                    when (range) {
                        TimeRange.WEEKLY -> currentDate.minusWeeks(1)
                        TimeRange.MONTHLY -> currentDate.minusMonths(1)
                        TimeRange.YEARLY -> currentDate.minusYears(1)
                    }
                )
            }
        ) {
            Icon(
                Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Previous period",
                tint = if (canGoPrevious) {
                    colorResource(R.color.oxford_navy)
                } else {
                    Color.LightGray
                }
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = displayDate,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(R.color.oxford_navy)
            )

            Text(
                text = when (range) {
                    TimeRange.WEEKLY -> "7-day business cycle"
                    TimeRange.MONTHLY -> "Monthly performance"
                    TimeRange.YEARLY -> "Yearly performance"
                },
                fontSize = 11.sp,
                color = Color.Gray
            )
        }

        IconButton(
            enabled = canGoNext,
            onClick = {
                onDateChange(
                    when (range) {
                        TimeRange.WEEKLY -> currentDate.plusWeeks(1)
                        TimeRange.MONTHLY -> currentDate.plusMonths(1)
                        TimeRange.YEARLY -> currentDate.plusYears(1)
                    }
                )
            }
        ) {
            Icon(
                Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = "Next period",
                tint = if (canGoNext) {
                    colorResource(R.color.oxford_navy)
                } else {
                    Color.LightGray
                }
            )
        }
    }
}




@Composable
fun TimeRangeToggle(
    selectedIndex: Int,
    onSelected: (Int) -> Unit
) {
    val options = TimeRange.entries

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp),
        color = colorResource(R.color.border_color),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            options.forEachIndexed { index, option ->

                val selected = index == selectedIndex

                Surface(
                    modifier = Modifier
                        .weight(1f)
                        .clickable {
                            onSelected(index)
                        },
                    color = if (selected) {
                        colorResource(R.color.oxford_navy)
                    } else {
                        Color.Transparent
                    },
                    shape = RoundedCornerShape(9.dp)
                ) {
                    Text(
                        text = option.title,
                        modifier = Modifier.padding(
                            horizontal = 6.dp,
                            vertical = 10.dp
                        ),
                        textAlign = TextAlign.Center,
                        fontSize = 13.sp,
                        fontWeight = if (selected) {
                            FontWeight.Bold
                        } else {
                            FontWeight.Medium
                        },
                        color = if (selected) {
                            Color.White
                        } else {
                            colorResource(R.color.oxford_navy)
                        }
                    )
                }
            }
        }
    }
}



@Composable
fun HistIntroHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                colorResource(id = R.color.light_background),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(16.dp)
    ) {
        Column(modifier = Modifier.align(Alignment.CenterStart)) {
            Text(
                text = "Summary Data",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.seaweed)
            )
            Text(
                text = "View summary and history data.",
                fontSize = 14.sp,
                color = colorResource(id = R.color.oxford_navy)
            )
        }
    }
}

enum class TimeRange(val title: String) {
    WEEKLY("Weekly"),
    MONTHLY("Monthly"),
    YEARLY("Yearly")
}
@Preview(showBackground = true)
@Composable
fun HistoryScreenPreview() {
    HistoryScreen(navController = rememberNavController())
}





@Composable
fun DayCloseCard(
    item: DaySummary,
    onViewExpenses: () -> Unit
) {
    val navy = colorResource(R.color.oxford_navy)
    val yaleBlue = colorResource(R.color.yale_blue)
    val seaweed = colorResource(R.color.seaweed)
    val lightBackground = colorResource(R.color.light_background)

    // Calculate daily business performance
    val performance = calculateProfitPerformance(
        netProfit = item.netProfit,
        totalExpense = item.totalExpense
    )

    val currencyFormat = remember {
        java.text.DecimalFormat("#,##0.00")
    }

    fun formatCash(amount: Float): String {
        return "${currencyFormat.format(amount)}"
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {

            // DATE AND PERFORMANCE BADGE
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(7.dp)
                ) {
                    Icon(
                        Icons.Default.CalendarToday,
                        contentDescription = null,
                        tint = seaweed,
                        modifier = Modifier.size(17.dp)
                    )

                    Text(
                        text = item.date,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = navy
                    )
                }

                // Performance badge
                Surface(
                    color = performance.color.copy(alpha = 0.12f),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text(
                        text = performance.label,
                        modifier = Modifier.padding(
                            horizontal = 10.dp,
                            vertical = 5.dp
                        ),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = performance.color
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // OPENING AND CLOSING CASH
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                HistoryStat(
                    title = "Opening Cash",
                    amount = formatCash(item.openingCash),
                    modifier = Modifier.weight(1f),
                    accentColor = seaweed
                )

                HistoryStat(
                    title = "Closing Cash",
                    amount = item.closingCash?.let {
                        formatCash(it)
                    } ?: "Not closed",
                    modifier = Modifier.weight(1f),
                    accentColor = yaleBlue
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // NET PROFIT
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = lightBackground,
                shape = RoundedCornerShape(10.dp)
            ) {
                Row(
                    modifier = Modifier.padding(
                        horizontal = 12.dp,
                        vertical = 10.dp
                    ),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            "Net Profit",
                            fontSize = 12.sp,
                            color = navy
                        )

                        Text(
                            text = item.netProfit?.let {
                                formatCash(it)
                            } ?: "Pending",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = if ((item.netProfit ?: 0f) >= 0f) {
                                seaweed
                            } else {
                                Color(0xFFD32F2F)
                            }
                        )
                    }

                    // Profit percentage
                    Column(
                        horizontalAlignment = Alignment.End
                    ) {
                        Text(
                            "Profit margin",
                            fontSize = 11.sp,
                            color = Color.Gray
                        )

                        Text(
                            "${"%.1f".format(performance.percentage)}%",
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            color = performance.color
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // TOTAL EXPENSE AND VIEW EXPENSE BUTTON
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Column {
                    Text(
                        "Total Expense",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )

                    Text(
                        formatCash(item.totalExpense),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = navy
                    )
                }

                TextButton(
                    onClick = onViewExpenses,
                    contentPadding = PaddingValues(
                        horizontal = 8.dp,
                        vertical = 4.dp
                    )
                ) {
                    Text(
                        "View Expenses",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = yaleBlue
                    )

                    Spacer(modifier = Modifier.width(3.dp))

                    Icon(
                        Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "View expenses",
                        modifier = Modifier.size(15.dp),
                        tint = yaleBlue
                    )
                }
            }
        }
    }
}








@Composable
fun HistoryStat(
    title: String,
    amount: String,
    modifier: Modifier = Modifier,
    accentColor: Color
) {
    Surface(
        modifier = modifier,
        color = colorResource(R.color.light_background),
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(
            modifier = Modifier.padding(
                horizontal = 10.dp,
                vertical = 9.dp
            )
        ) {
            Text(
                text = title,
                fontSize = 11.sp,
                color = Color.Gray,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = amount,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = accentColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}








///profit

data class ProfitPerformance(
    val percentage: Float,
    val label: String,
    val color: Color
)

@Composable
fun calculateProfitPerformance(
    netProfit: Float?,
    totalExpense: Float
): ProfitPerformance {

    val profit = netProfit ?: 0f
    val revenue = profit + totalExpense

    val percentage = if (revenue > 0f) {
        (profit / revenue) * 100f
    } else {
        0f
    }

    val seaweed = colorResource(R.color.seaweed)
    val yaleBlue = colorResource(R.color.yale_blue)
    val goldenOrange = colorResource(R.color.golden_orange)

    return when {
        profit < 0f -> ProfitPerformance(
            percentage = percentage,
            label = "Loss Day",
            color = Color(0xFFD32F2F)
        )

        percentage >= 70f -> ProfitPerformance(
            percentage = percentage,
            label = "Excellent Day",
            color = seaweed
        )

        percentage >= 60f -> ProfitPerformance(
            percentage = percentage,
            label = "Better Day",
            color = seaweed
        )

        percentage >= 50f -> ProfitPerformance(
            percentage = percentage,
            label = "Average Day",
            color = goldenOrange
        )

        else -> ProfitPerformance(
            percentage = percentage,
            label = "Low Profit",
            color = yaleBlue
        )
    }
}



