//package com.jmapps.dayclose.ui_screens.components
//
//
//
//import android.widget.Toast
//import androidx.compose.animation.core.animateFloatAsState
//import androidx.compose.foundation.interaction.MutableInteractionSource
//import androidx.compose.foundation.interaction.collectIsPressedAsState
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.heightIn
//import androidx.compose.foundation.layout.imePadding
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material3.Button
//import androidx.compose.material3.ButtonDefaults
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.OutlinedTextField
//import androidx.compose.material3.OutlinedTextFieldDefaults
//import androidx.compose.material3.Surface
//import androidx.compose.material3.Text
//import androidx.compose.material3.TextButton
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.graphicsLayer
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.res.colorResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.input.KeyboardType
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.compose.ui.window.Dialog
//import androidx.lifecycle.compose.collectAsStateWithLifecycle
//import com.jmapps.dayclose.R
//import com.jmapps.dayclose.utils.standardDateFormat
//import com.jmapps.dayclose.viewmodel.DayDataViewModel
//import org.koin.androidx.compose.koinViewModel
//
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun DayOpenPopup(
//    onDismiss: () -> Unit
//) {
////    val earnedCashViewModel: EarnedCashViewModel = koinViewModel()
//     val dayDataViewModel: DayDataViewModel = koinViewModel()
//
//
//    val today = standardDateFormat(System.currentTimeMillis())
//
//
//    val backgroundColor = colorResource(id = R.color.seaweed)
//
//
//    var openingCash by remember { mutableStateOf("") }
//
//
//    val context = LocalContext.current
//
//
//    val interactionSource = remember { MutableInteractionSource() }
//    val pressed by interactionSource.collectIsPressedAsState()
//
//    val scale by animateFloatAsState(
//        targetValue = if (pressed) 0.96f else 1f,
//        label = ""
//    )
//
//    // Error states
//    var openingCashError by remember { mutableStateOf(false) }
//
//
//    Dialog(onDismissRequest = { onDismiss() }) {
//        Surface(
//            shape = RoundedCornerShape(16.dp),
//            color = Color.White,
//            tonalElevation = 8.dp,
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp)
//        ) {
//            Column(
//                Modifier
//                    .padding(16.dp)
//                    .imePadding()
//                    .verticalScroll(rememberScrollState()),
//                verticalArrangement = Arrangement.spacedBy(12.dp)
//            ) {
//
//                Text(
//                    text = "Add Opening Cash",
//                    fontWeight = FontWeight.Bold,
//                    fontSize = 18.sp
//                )
//
//
//
//                // AMOUNT EARNED
//                OutlinedTextField(
//                    value = openingCash,
//                    onValueChange = {
//                        openingCash = it
//                        openingCashError = false
//                    },
//                    label = { Text("Amount Earned *") },
//                    modifier = Modifier.fillMaxWidth(),
//                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
//                    isError = openingCashError,
//                    singleLine = true,
//                    colors = OutlinedTextFieldDefaults.colors(
//                        focusedBorderColor = backgroundColor,
//                        focusedLabelColor = backgroundColor,
//                        cursorColor = backgroundColor
//                    )
//                )
//
//                if (openingCashError) {
//                    Text("Enter valid amount", color = Color.Red, fontSize = 12.sp)
//                }
//
//
//
//                // BUTTONS
//                Row(
//                    modifier = Modifier.fillMaxWidth(),
//                    horizontalArrangement = Arrangement.End
//                ) {
//
//                    TextButton(onClick = onDismiss) {
//                        Text("Cancel", color = backgroundColor)
//                    }
//
//                    Spacer(modifier = Modifier.width(8.dp))
//
//
//
//
//                    Button(
////                        onClick = { /* your logic */ },
//                        interactionSource = interactionSource,
//                        modifier = Modifier
//                            .height(56.dp)
//                            .fillMaxWidth()
//                            .graphicsLayer {
//                                scaleX = scale
//                                scaleY = scale
//                            },
//                        shape = RoundedCornerShape(14.dp),
//                        colors = ButtonDefaults.buttonColors(
//                            containerColor = backgroundColor
//                        )
//
//                        ,
//                        onClick = {
//
//                            val opening = openingCash.toDoubleOrNull()
//
//
//                            var valid = true
//
//                            if (opening == null || opening <= 0) {
//                               openingCashError = true
//                                valid = false
//                            }
//
//
//                            if (valid) {
//
//
//                                Toast.makeText(
//                                    context,
//                                    "Day Opened successfully",
//                                    Toast.LENGTH_SHORT
//                                ).show()
//
//                                onDismiss()
//                            }
//                        }
//                    ) {
//                        Text(
//                            text = "Open Day",
//                            color = Color.White,
//                            fontWeight = FontWeight.Bold
//                        )
//                    }
//                }
//            }
//        }
//    }
//}
//
//
//fun generateTimestampBased10DigitNumberItemNo(): Long {
//    val timestampPart = (System.currentTimeMillis() / 1000) % 100000 // Last 5 digits of timestamp (seconds)
//    val randomPart = (10000..99999).random() // 5 random digits
//    return "$timestampPart$randomPart".toLong()
//}
//



package com.jmapps.dayclose.ui_screens.components

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.jmapps.dayclose.R
import com.jmapps.dayclose.model.DayDataEntity
import com.jmapps.dayclose.utils.standardDateFormat
import com.jmapps.dayclose.viewmodel.DayDataViewModel
import org.koin.androidx.compose.koinViewModel
import kotlin.Float
import kotlin.String

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DayOpenPopup(
    onDismiss: () -> Unit
) {
    val today = standardDateFormat(System.currentTimeMillis())
         val dayDataViewModel: DayDataViewModel = koinViewModel()


    val seaweed = colorResource(id = R.color.seaweed)
    val yaleBlue = colorResource(id = R.color.yale_blue)

    val context = LocalContext.current

    var openingCash by remember { mutableStateOf("") }
    var openingCashError by remember { mutableStateOf(false) }

    val interactionSource = remember { MutableInteractionSource() }

    // Two-color gradient
    val gradientBrush = Brush.linearGradient(
        colors = listOf(
            seaweed,
            yaleBlue
        )
    )

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            shape = RoundedCornerShape(24.dp),
            color = Color.Transparent,
            tonalElevation = 8.dp
        ) {
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(24.dp))
                    .background(gradientBrush)
                    .padding(22.dp)
                    .imePadding()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                // HEADER
                Column(
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Text(
                        text = "Start Your Day",
                        fontSize = 23.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    Text(
                        text = "Set your opening cash to get started.",
                        fontSize = 13.sp,
                        color = Color.White.copy(alpha = 0.85f)
                    )

                    Text(
                        text = today,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White.copy(alpha = 0.75f)
                    )
                }

                // INPUT SECTION
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Opening Cash",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )

                    OutlinedTextField(
                        value = openingCash,
                        onValueChange = {
                            openingCash = it
                            openingCashError = false
                        },
                        placeholder = {
                            Text("Enter amount")
                        },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Decimal
                        ),
                        isError = openingCashError,
                        singleLine = true,
                        shape = RoundedCornerShape(14.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color.White,
                            unfocusedBorderColor = Color.White.copy(alpha = 0.6f),
                            focusedLabelColor = Color.White,
                            cursorColor = seaweed,
                            focusedTextColor = Color(0xFF17313D),
                            unfocusedTextColor = Color(0xFF17313D),
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            unfocusedPlaceholderColor = Color.Gray,
                            focusedPlaceholderColor = Color.Gray,
                            errorBorderColor = Color.Red
                        )
                    )

                    if (openingCashError) {
                        Text(
                            text = "Please enter a valid amount greater than zero.",
                            color = Color.White,
                            fontSize = 12.sp
                        )
                    }
                }

                // BALANCED BUTTONS
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    // CANCEL BUTTON
                    OutlinedButton(
                        onClick = onDismiss,
                        modifier = Modifier
                            .weight(1f)
                            .height(52.dp),
                        shape = RoundedCornerShape(14.dp),
                        border = ButtonDefaults.outlinedButtonBorder(
                            enabled = true
                        ).copy(
                            brush = Brush.linearGradient(
                                listOf(Color.White, Color.White)
                            )
                        ),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Color.White
                        )
                    ) {
                        Text(
                            text = "Cancel",
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    // OPEN DAY BUTTON
                    Button(
                        onClick = {
                            val opening = openingCash.toDoubleOrNull()

                            if (opening == null || !opening.isFinite() || opening <= 0) {
                                openingCashError = true
                            } else {
                                dayDataViewModel.openDay(
                                    DayDataEntity(
                                   openingCash = openingCash.toFloat(),
                                     dayId= generateTimestampBasedItemNo()
                                    )
                                )

                                Toast.makeText(
                                    context,
                                    "Day Opened successfully",
                                    Toast.LENGTH_SHORT
                                ).show()

                                onDismiss()
                            }
                        },
                        interactionSource = interactionSource,
                        modifier = Modifier
                            .weight(1f)
                            .height(52.dp),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = yaleBlue
                        )
                    ) {
                        Text(
                            text = "Open Day",
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}


fun generateTimestampBasedItemNo(): String {
    val characters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"

    val timestampPart = (System.currentTimeMillis() / 1000)
        .toString()
        .takeLast(2)

    val randomPart = (1..6)
        .map { characters.random() }
        .joinToString("")

    return timestampPart + randomPart
}
