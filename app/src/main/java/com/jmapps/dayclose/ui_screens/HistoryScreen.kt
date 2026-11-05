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
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.jmapps.dayclose.R
import kotlinx.coroutines.launch

// Vico Imports
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.compose.component.lineComponent
import com.patrykandpatrick.vico.compose.component.shape.dashedShape
import com.patrykandpatrick.vico.compose.component.shapeComponent
import com.patrykandpatrick.vico.compose.component.textComponent
import com.patrykandpatrick.vico.core.chart.DefaultPointConnector
import com.patrykandpatrick.vico.core.chart.line.LineChart
import com.patrykandpatrick.vico.core.component.marker.MarkerComponent
import com.patrykandpatrick.vico.core.component.shape.Shapes
import com.patrykandpatrick.vico.core.component.text.VerticalPosition
import com.patrykandpatrick.vico.core.component.text.textComponent
import com.patrykandpatrick.vico.core.dimensions.MutableDimensions
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.patrykandpatrick.vico.core.entry.entryOf
import com.patrykandpatrick.vico.core.formatter.DecimalFormatValueFormatter
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.MoneyBillAlt
import compose.icons.fontawesomeicons.solid.MoneyBillWaveAlt
import compose.icons.fontawesomeicons.solid.Search

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

@OptIn(ExperimentalMaterial3Api::class)




//@Composable
//fun HistoryScreen(navController: NavController) {
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(colorResource(id = R.color.gray))
//            .statusBarsPadding()
//            .verticalScroll(rememberScrollState())
//            .padding(horizontal = 12.dp, vertical = 16.dp)
//    ) {
//        HistIntroHeader()
//
//        val pagerState = rememberPagerState(
//            initialPage = 0,
//            pageCount = { TimeRange.entries.size }
//        )
//        val coroutineScope = rememberCoroutineScope()
//
//        Column {
//            // Toggle Buttons
//            TimeRangeToggle(
//                selectedIndex = pagerState.currentPage,
//                onSelected = { index ->
//                    coroutineScope.launch {
//                        pagerState.animateScrollToPage(index)
//                    }
//                }
//            )
//
//            // Pager
//            HorizontalPager(
//                state = pagerState,
//                modifier = Modifier.fillMaxWidth()
//            ) { page ->
//                when (TimeRange.entries[page]) {
//                    TimeRange.WEEKLY -> {
//                        NetProfitLineChart(
//                            data = listOf(18000f, 12000f, -3000f, 4000f, 13000f, 8000f, 11000f),
//                            labels = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
//                        )
//                    }
//                    TimeRange.MONTHLY -> {
//                        NetProfitLineChart(
//                            data = listOf(120000f, 98000f, 140000f, -20000f),
//                            labels = listOf("Week 1", "Week 2", "Week 3", "Week 4")
//                        )
//                    }
//                    TimeRange.YEARLY -> {
//                        NetProfitLineChart(
//                            data = listOf(900000f, 1100000f, 850000f, 1300000f),
//                            labels = listOf("Q1", "Q2", "Q3", "Q4")
//                        )
//                    }
//                }
//            }
//        }
//
//    }
//}

@Composable
fun HistoryScreen(navController: NavController) {
    // 1. State for the Date and Range
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    val pagerState = rememberPagerState(pageCount = { TimeRange.entries.size })
    val coroutineScope = rememberCoroutineScope()
    val activeRange = TimeRange.entries[pagerState.currentPage]


    val mockDayCloses = remember {
        generateMockDayCloses()
    }


//    val mockDayCloses = List(15) { index ->
//        DayCloseItem(
//            date = "2026-01-${(index + 1).toString().padStart(2, '0')}",
//            openingCash = "20,000",
//            closingCash = "${20_000 + index * 1_500}",
//            netProfit = "${5_000 - index * 300}",
//            totalExpense = "${3_000 + index * 200}",
//            status = if (index % 2 == 0) "Above average" else "Below average"
//        )
//    }



//Pagination logic (IMPORTANT PART)
    val itemsPerPage = 4
    var currentPage by remember { mutableStateOf(0) }

    val totalPages = (mockDayCloses.size + itemsPerPage - 1) / itemsPerPage

    val pagedItems = mockDayCloses.drop(currentPage * itemsPerPage)
        .take(itemsPerPage)

    var searchQuery by remember { mutableStateOf("") }
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

        // 2. Range Selector (Weekly, Monthly, Yearly)
        TimeRangeToggle(
            selectedIndex = pagerState.currentPage,
            onSelected = { index ->
                coroutineScope.launch { pagerState.animateScrollToPage(index) }
            }
        )

        // 3. NEW: Date Navigator (Previous/Next)
        DateNavigator(
            currentDate = selectedDate,
            range = activeRange,
            onDateChange = { newDate -> selectedDate = newDate }
        )

        Spacer(modifier = Modifier.height(6.dp))

        // 4. Pager (Displays the chart for the selected period)
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth(),
            userScrollEnabled = false // Disabled to prevent confusion with button nav
        ) { page ->
            // In a real app, you'd fetch data here: getProfitData(activeRange, selectedDate)
            val mockData = remember(selectedDate, activeRange) {
                List(if (activeRange == TimeRange.WEEKLY) 7 else 4) {
                    (-5000..20000).random().toFloat()
                }
            }

            val mockLabels = when (activeRange) {
                TimeRange.WEEKLY -> listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
                TimeRange.MONTHLY -> listOf("Wk 1", "Wk 2", "Wk 3", "Wk 4")
                TimeRange.YEARLY -> listOf("Q1", "Q2", "Q3", "Q4")
            }

            NetProfitLineChart(data = mockData, labels = mockLabels)
        }

//        Column(modifier =Modifier.fillMaxWidth()){
//            // Search Field
//            TextField(
//                value = searchQuery,
//                onValueChange = { searchQuery = it },
//                placeholder = { Text(text = "Search by date... yyyy-mm-dd") },
//                leadingIcon = {
//
//                    Icon(
//                        imageVector = FontAwesomeIcons.Solid.Search,
//                        contentDescription = "Search Icon",
//                        tint = colorResource(id=R.color.oxford_navy),
//                        modifier = Modifier.size(24.dp)
//                    )
//                },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(4.dp)
//                    .clip(RoundedCornerShape(12.dp))
//                    .background(Color.White),
//                colors = TextFieldDefaults.colors(
//                    focusedContainerColor = Color.Transparent,
//                    unfocusedContainerColor = Color.Transparent,
//                    disabledContainerColor = Color.Transparent,
//                    cursorColor = Color.Black,
//                    focusedIndicatorColor = Color.Transparent,
//                    unfocusedIndicatorColor = Color.Transparent
//                ),
//                singleLine = true
//            )
//
//            Spacer(modifier = Modifier.height(8.dp))
//
//          repeat(5){
//
//              Box(
//                  modifier = Modifier
//                      .fillMaxWidth()
//                      .padding(vertical = 6.dp)
//                      .border(
//                          width = 1.dp,
//                          color = Color(0xFF2E7D32), // outline color
//                          shape = RoundedCornerShape(16.dp)
//                      )
//                      .background(
//                          color = Color(0xFFE8F5E9), // background color
//                          shape = RoundedCornerShape(16.dp)
//                      )
//                      .padding(16.dp) // inner padding
//              ) {
//                 Column{
//                     // content here
//                     Text(
//                         "31/01/2026",
//                         fontWeight = FontWeight.Bold,
//                         color = colorResource(id = R.color.oxford_navy)
//                     )
//                     Row(
//                         modifier = Modifier
//                             .fillMaxWidth()
//                             .padding(6.dp),
//                         horizontalArrangement = Arrangement.spacedBy(4.dp)
//                     ) {
//
//                         CashCard(
//                             title = "Opening Cash",
//                             amount = "20,000",
//                             backgroundColor = R.color.light_background,
//                             modifier = Modifier.weight(1f)
//                         )
//
//                         CashCard(
//                             title = "Closing Cash",
//                             amount = "30,000",
//                             backgroundColor = R.color.light_background,
//                             modifier = Modifier.weight(1f)
//                         )
//                     }
//                     Spacer(modifier = Modifier.height(6.dp))
//
//                     Box(
//                         modifier = Modifier
//                             .fillMaxWidth()                    // ✅ takes full screen width
//                             .padding(horizontal = 16.dp)       // ✅ outer spacing from screen edges
//                             .background(
//                                 color = colorResource(id = R.color.light_background),
//                                 shape = RoundedCornerShape(8.dp)
//                             )
//                             .padding(6.dp)                    // ✅ big inner padding
//                     ) {
//                         Column {
//                             Text(
//                                 text = "Net Profit",
//                                 color = colorResource(id = R.color.seaweed),
//                                 fontSize = 16.sp                // slightly bigger label
//                             )
//
//                             Spacer(modifier = Modifier.height(6.dp))
//
//                             Text(
//                                 text = "10,000",
//                                 color = colorResource(id = R.color.yale_blue),
//                                 fontSize = 28.sp,               // big emphasis
//                                 fontWeight = FontWeight.SemiBold
//                             )
//                         }
//                     }
//                     Spacer(modifier = Modifier.height(6.dp))
//                     Box(
//                         modifier = Modifier
//                             .fillMaxWidth()                    // ✅ takes full screen width
//                             .padding(horizontal = 16.dp)       // ✅ outer spacing from screen edges
//                             .background(
//                                 color = colorResource(id = R.color.light_background),
//                                 shape = RoundedCornerShape(8.dp)
//                             )
//                             .padding(6.dp)                    // ✅ big inner padding
//                     ) {
//                         Column {
//                             Text(
//                                 text = "Total expense",
//                                 color = colorResource(id = R.color.seaweed),
//                                 fontSize = 16.sp                // slightly bigger label
//                             )
//
//                             Spacer(modifier = Modifier.height(6.dp))
//
//                             Text(
//                                 text = "5,000",
//                                 color = colorResource(id = R.color.yale_blue),
//                                 fontSize = 28.sp,               // big emphasis
//                                 fontWeight = FontWeight.SemiBold
//                             )
//                         }
//                     }
//                     Box(
//                         modifier = Modifier
//                             .background(
//                                 colorResource(id = R.color.seaweed),
//                                 RoundedCornerShape(20.dp)
//                             )
//                             .padding(horizontal = 12.dp, vertical = 8.dp)
//                     ) {
//                         Text(
//                             "Status: Above average ",
//                             color = Color.White,
//                             fontSize = 12.sp
//                         )
//                     }
//
//
//                 }
//
//              }
//
//
//          }
//
//
//
//        }


        Column(modifier = Modifier.fillMaxWidth()) {


            // Search Field by date
            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text(text = "Search by date... yyyy-mm-dd") },
                leadingIcon = {

                    Icon(
                        imageVector = FontAwesomeIcons.Solid.Search,
                        contentDescription = "Search Icon",
                        tint = colorResource(id = R.color.oxford_navy),
                        modifier = Modifier.size(24.dp)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.White),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    cursorColor = Color.Black,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            // 📄 PAGED ITEMS (ONLY 4)
            pagedItems.forEach { item ->
                DayCloseCard(item)
            }

            Spacer(modifier = Modifier.height(12.dp))

            // ⏮️ ⏭️ Pagination buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = { if (currentPage > 0) currentPage-- },
                    enabled = currentPage > 0
                ) {
                    Text("Previous")
                }

                Text(
                    text = "Page ${currentPage + 1} of $totalPages",
                    modifier = Modifier.align(Alignment.CenterVertically)
                )

                Button(
                    onClick = { if (currentPage < totalPages - 1) currentPage++ },
                    enabled = currentPage < totalPages - 1
                ) {
                    Text("Next")
                }
            }
        }


    }
    }
}





@Composable
fun DateNavigator(
    currentDate: LocalDate,
    range: TimeRange,
    onDateChange: (LocalDate) -> Unit
) {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM")

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = {
            onDateChange(when(range) {
                TimeRange.WEEKLY -> currentDate.minusWeeks(1)
                TimeRange.MONTHLY -> currentDate.minusMonths(1)
                TimeRange.YEARLY -> currentDate.minusYears(1)
            })
        }) {
            Text("<", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        }

        // Current Date Display
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = currentDate.format(formatter),
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = colorResource(id = R.color.oxford_navy)
            )
            Text(
                text = if (range == TimeRange.WEEKLY) "Week View" else if (range == TimeRange.MONTHLY) "Month View" else "Year View",
                fontSize = 12.sp,
                color = Color.Gray
            )
        }

        IconButton(onClick = {
            onDateChange(when(range) {
                TimeRange.WEEKLY -> currentDate.plusWeeks(1)
                TimeRange.MONTHLY -> currentDate.plusMonths(1)
                TimeRange.YEARLY -> currentDate.plusYears(1)
            })
        }) {
            Text(">", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        }
    }
}



@Composable
fun NetProfitLineChart(
    data: List<Float>,
    labels: List<String>
) {
    // entryOf creates a proper FloatEntry that won't crash on touch/animation
    val modelProducer = remember(data) {
        ChartEntryModelProducer(
            data.mapIndexed { index, value -> entryOf(index.toFloat(), value) }
        )
    }

    Chart(
        chart = lineChart(
            lines = listOf(
                LineChart.LineSpec(
                    lineColor = Color(0xFF2E7D32).toArgb(),
                    lineThicknessDp = 3f,
                    lineCap = Paint.Cap.ROUND,
                    pointSizeDp = 8f,
                    dataLabelVerticalPosition = VerticalPosition.Top,
                    dataLabelValueFormatter = DecimalFormatValueFormatter(),
                    pointConnector = DefaultPointConnector(cubicStrength = 0.2f),
                )
            )
        ),
        chartModelProducer = modelProducer,
        startAxis = rememberStartAxis(),
        bottomAxis = rememberBottomAxis(
            valueFormatter = { value, _ ->
                labels.getOrNull(value.toInt()) ?: ""
            }
        ),
        marker = rememberMarker(),
        modifier = Modifier
            .fillMaxWidth()
            .height(260.dp)
            .padding(16.dp)
    )
}


@Composable
fun rememberMarker(): MarkerComponent {
    val label = textComponent {
        color = Color.White.toArgb()
        background = shapeComponent(shape = Shapes.pillShape, color = Color.Black)
        padding = MutableDimensions(4f, 8f, 4f, 8f) // Core uses Float for dimensions
    }

    val indicator = shapeComponent(Shapes.pillShape, Color(0xFF2E7D32))

    val guideline = lineComponent(
        color = Color.LightGray,
        thickness = 2.dp, // Fixed name and type
        shape = Shapes.dashedShape(
            shape = Shapes.rectShape,
            dashLength = 8.dp,
            gapLength = 4.dp
        )
    )

    return remember(label, indicator, guideline) {
        MarkerComponent(
            label = label,
            indicator = indicator,
            guideline = guideline
        )
    }
}

@Composable
fun TimeRangeToggle(
    selectedIndex: Int,
    onSelected: (Int) -> Unit
) {
    val options = TimeRange.entries

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        options.forEachIndexed { index, option ->
            Button(
                onClick = { onSelected(index) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (index == selectedIndex)
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.surface
                ),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                shape = RoundedCornerShape(20.dp)
            ) {
                Text(
                    text = option.title,
                    color = if (index == selectedIndex)
                        MaterialTheme.colorScheme.onPrimary
                    else
                        MaterialTheme.colorScheme.primary
                )
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



// reusable card (your UI, cleaned)
@Composable
fun DayCloseCard(item: DayCloseItem) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .border(1.dp, Color(0xFF2E7D32), RoundedCornerShape(16.dp))
            .background(Color(0xFFE8F5E9), RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Column {
            Text(
                item.date,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.oxford_navy)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {


//                CashCard(
//                    title = "Opening Cash",
//                    amount = item.openingCash,
//                    backgroundColor = R.color.light_background,
//                    modifier = Modifier.weight(1f)
//                )
//                CashCard(
//                    title = "Closing Cash",
//                    amount = item.closingCash,
//                    backgroundColor = R.color.light_background,
//                    modifier = Modifier.weight(1f)
//                )

                SmallStatCard(
                    title = "Opening",
                    amount = "12,500",
                    icon = FontAwesomeIcons.Solid.MoneyBillAlt,
                    color = colorResource(id = R.color.seaweed),
                    modifier = Modifier.weight(1f)
                )
                SmallStatCard(
                    title = "Closing",
                    amount = "18,300",
                    icon = FontAwesomeIcons.Solid.MoneyBillWaveAlt,
                    color = colorResource(id = R.color.oxford_navy),
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            SummaryBox("Net Profit", item.netProfit)
            Spacer(modifier = Modifier.height(6.dp))
            SummaryBox("Total Expense", item.totalExpense)

            Spacer(modifier = Modifier.height(6.dp))

            Box(
                modifier = Modifier
                    .background(
                        colorResource(id = R.color.seaweed),
                        RoundedCornerShape(20.dp)
                    )
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            ) {
                Text(item.status, color = Color.White, fontSize = 12.sp)
            }
        }
    }
}



//reusable UI:
@Composable
fun SummaryBox(title: String, amount: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                colorResource(id = R.color.light_background),
                RoundedCornerShape(20.dp)
            )
            .padding(12.dp)
    ) {
        Column {
            Text(title, color = colorResource(id = R.color.seaweed))
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                amount,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.yale_blue)
            )
        }
    }
}





data class DayCloseItem(
    val date: String,
    val openingCash: String,
    val closingCash: String,
    val netProfit: String,
    val totalExpense: String,
    val status: String
)



//Helper: date range generator

fun generateMockDayCloses(): List<DayCloseItem> {
    val startDate = LocalDate.of(2025, 1, 1)
    val endDate = LocalDate.of(2026, 2, 1)

    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    val list = mutableListOf<DayCloseItem>()
    var date = startDate
    var dayIndex = 0

    while (!date.isAfter(endDate)) {
        val openingCash = 20_000
        val dailySales = (5_000..15_000).random()
        val expenses = (2_000..8_000).random()
        val closingCash = openingCash + dailySales - expenses
        val netProfit = dailySales - expenses

        list.add(
            DayCloseItem(
                date = date.format(formatter),
                openingCash = openingCash.formatMoney(),
                closingCash = closingCash.formatMoney(),
                netProfit = netProfit.formatMoney(),
                totalExpense = expenses.formatMoney(),
                status = when {
                    netProfit > 5_000 -> "Excellent"
                    netProfit > 0 -> "Above average"
                    else -> "Loss"
                }
            )
        )

        date = date.plusDays(1)
        dayIndex++
    }

    return list
}


//Money formatter (nice UI)
fun Int.formatMoney(): String {
    return "%,d".format(this)
}


