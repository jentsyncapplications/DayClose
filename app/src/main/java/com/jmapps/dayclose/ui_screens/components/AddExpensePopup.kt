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
import androidx.room.PrimaryKey
import com.jmapps.dayclose.R
import com.jmapps.dayclose.model.DayExpenseEntity
import com.jmapps.dayclose.utils.standardDateFormat
import com.jmapps.dayclose.viewmodel.DayExpenseViewModel
import org.koin.androidx.compose.koinViewModel
import kotlin.String

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpensePopup(
    onDismiss: () -> Unit,
    dayId: String
) {
    val today = standardDateFormat(System.currentTimeMillis())


    val dayExpenseViewModel: DayExpenseViewModel = koinViewModel()

    val seaweed = colorResource(id = R.color.seaweed)
    val yaleBlue = colorResource(id = R.color.golden_orange)

    val context = LocalContext.current

    val expenseCategories = listOf(
        "Bills",
        "Food",
        "Transport",
        "Rent",
        "Utilities",
        "Supplies",
        "Maintenance",
        "Salaries",
        "Marketing",
        "Communication",
        "Other"
    )

    var categoryExpanded by remember { mutableStateOf(false) }

    var totalExpense by remember { mutableStateOf("") }
    var expenseCategory by remember { mutableStateOf("") }
    var expenseDescription by remember { mutableStateOf("") }


    var totalExpenseError by remember { mutableStateOf(false) }
    var expenseCategoryError by remember { mutableStateOf(false) }


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
                        text = "Add Expense",
                        fontSize = 23.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    Text(
                        text = "Add Business Expense for the day.",
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
                        text = "Expense Cost",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )

                    OutlinedTextField(
                        value = totalExpense,
                        onValueChange = {
                            totalExpense = it
                            totalExpenseError = false
                        },
                        placeholder = {
                            Text("Enter amount")
                        },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Decimal
                        ),
                        isError = totalExpenseError,
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

                    if (totalExpenseError) {
                        Text(
                            text = "Please enter a valid amount greater than zero.",
                            color = Color.White,
                            fontSize = 12.sp
                        )
                    }





                    Text(
                        text = "Expense Category",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )

                    ExposedDropdownMenuBox(
                        expanded = categoryExpanded,
                        onExpandedChange = {
                            categoryExpanded = !categoryExpanded
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        OutlinedTextField(
                            value = expenseCategory,
                            onValueChange = {},
                            readOnly = true,
                            placeholder = {
                                Text("Select expense category")
                            },
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(
                                    expanded = categoryExpanded
                                )
                            },
                            modifier = Modifier
                                .menuAnchor(
                                    type = ExposedDropdownMenuAnchorType.PrimaryNotEditable,
                                    enabled = true
                                )
                                .fillMaxWidth(),
                            isError = expenseCategoryError,
                            singleLine = true,
                            shape = RoundedCornerShape(14.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color.White,
                                unfocusedBorderColor = Color.White.copy(alpha = 0.6f),
                                focusedTextColor = Color(0xFF17313D),
                                unfocusedTextColor = Color(0xFF17313D),
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White,
                                focusedPlaceholderColor = Color.Gray,
                                unfocusedPlaceholderColor = Color.Gray,
                                errorBorderColor = Color.Red
                            )
                        )

                        ExposedDropdownMenu(
                            expanded = categoryExpanded,
                            onDismissRequest = {
                                categoryExpanded = false
                            },
                            modifier = Modifier.background(Color.White)
                        ) {
                            expenseCategories.forEach { category ->

                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            text = category,
                                            color = Color(0xFF17313D),
                                            fontSize = 14.sp
                                        )
                                    },
                                    onClick = {
                                        expenseCategory = category
                                        categoryExpanded = false
                                        expenseCategoryError = false
                                    }
                                )
                            }
                        }
                    }

                    if (expenseCategoryError) {
                        Text(
                            text = "Please select an expense category",
                            color = Color.White,
                            fontSize = 12.sp
                        )
                    }





                    Text(
                        text = "Note(Optional)",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )

                    OutlinedTextField(
                        value = expenseDescription,
                        onValueChange = {
                            expenseDescription = it
                        },
                        placeholder = {
                            Text("Add a note")
                        },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text
                        ),
//                        singleLine = true,
                        maxLines = 4,
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
                            val expenseAmt = totalExpense.toDoubleOrNull()
                            var valid = true
//                            val opening = openingCash.toDoubleOrNull()

                            if (expenseAmt == null || !expenseAmt.isFinite() || expenseAmt <= 0) {
                                totalExpenseError = true
                                valid = false
                            }

                            if(expenseCategory.isBlank()){
                                expenseCategoryError = true
                                    valid = false

                            }

                            if (valid) {

                                dayExpenseViewModel.addExpense(
                                    DayExpenseEntity(
                                    dayId = dayId,
                                  totalExpense = totalExpense.toFloat(),
                                   expenseCategory =expenseCategory,
                                  expenseDescription = expenseDescription,
                                     expenseId = generateTimestampBasedExpenseNo()
                                    )
                                )


                                Toast.makeText(
                                    context,
                                    "Expense Added successfully",
                                    Toast.LENGTH_SHORT
                                ).show()

                                onDismiss()

                            }



//                            else {

//                            }
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
                            text = "Save Expense",
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }



        }
    }
}



fun generateTimestampBasedExpenseNo(): String {
    val characters = "0123456789abcdefghijklmnopqrstuvwxyz"

    val timestampPart = (System.currentTimeMillis() / 1000)
        .toString()
        .takeLast(3)

    val randomPart = (1..5)
        .map { characters.random() }
        .joinToString("")

    return "exp-$timestampPart$randomPart"
}