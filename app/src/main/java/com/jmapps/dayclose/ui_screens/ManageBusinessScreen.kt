package com.jmapps.dayclose.ui_screens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Business
import androidx.compose.material.icons.outlined.Store
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.jmapps.dayclose.R
import com.jmapps.dayclose.viewmodel.BusinessProfilePreferenceViewModel
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageBusinessScreen(
    navController: NavController
) {

    val viewModel : BusinessProfilePreferenceViewModel = koinViewModel()

    val navy = colorResource(R.color.oxford_navy)
    val yaleBlue = colorResource(R.color.yale_blue)
    val seaweed = colorResource(R.color.seaweed)
    val background = colorResource(R.color.light_background)
    val border = colorResource(R.color.border_color)

    val businessName by viewModel.businessName.collectAsStateWithLifecycle(
        initialValue = ""
    )

    val isProfileSet by viewModel.isProfileSet.collectAsStateWithLifecycle(
        initialValue = false
    )

    var enteredName by rememberSaveable {
        mutableStateOf("")
    }

    // Track whether the user has started editing.
    var hasEdited by rememberSaveable {
        mutableStateOf(false)
    }

    // Prefill the field when the saved business name is loaded.
    LaunchedEffect(businessName) {
        if (!hasEdited) {
            enteredName = businessName
        }
    }

    val isNameValid = enteredName.trim().isNotEmpty()
    val hasChanges = enteredName.trim() != businessName.trim()

    Scaffold(
        containerColor = background,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Manage Business",
                        fontSize = 19.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Go back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = navy
                )
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(
                    rememberScrollState()
                )
                .padding(horizontal = 20.dp, vertical = 25.dp),
            verticalArrangement = Arrangement.spacedBy(22.dp)
        ) {

            // BUSINESS PROFILE HEADER
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Box(
                    modifier = Modifier
                        .size(85.dp)
                        .background(
                            color = yaleBlue.copy(alpha = 0.10f),
                            shape = RoundedCornerShape(25.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Store,
                        contentDescription = null,
                        tint = yaleBlue,
                        modifier = Modifier.size(43.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = if (isProfileSet)
                        "Your Business Profile"
                    else
                        "Set Up Your Business",
                    fontSize = 21.sp,
                    fontWeight = FontWeight.Bold,
                    color = navy
                )

                Spacer(modifier = Modifier.height(7.dp))

                Text(
                    text = if (isProfileSet)
                        "Keep your business information up to date."
                    else
                        "Give your business a name to personalize your DayClose experience.",
                    fontSize = 13.sp,
                    color = Color.Gray,
                    lineHeight = 20.sp,
                    modifier = Modifier.padding(horizontal = 12.dp)
                )
            }

            // BUSINESS NAME FORM
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(20.dp)
                    )
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                Text(
                    text = "BUSINESS INFORMATION",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp,
                    color = navy
                )

                OutlinedTextField(
                    value = enteredName,
                    onValueChange = {
                        enteredName = it
                        hasEdited = true
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        Text("Business name")
                    },
                    placeholder = {
                        Text("e.g. Jelly's Fashion")
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.Business,
                            contentDescription = null,
                            tint = yaleBlue
                        )
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(14.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = yaleBlue,
                        unfocusedBorderColor = border,
                        focusedLabelColor = yaleBlue,
                        cursorColor = yaleBlue
                    )
                )

                Text(
                    text = "This name will help identify your business within DayClose.",
                    fontSize = 11.sp,
                    color = Color.Gray,
                    lineHeight = 16.sp
                )

                Spacer(modifier = Modifier.height(5.dp))

                Button(
                    onClick = {
                        val name = enteredName.trim()

                        if (isProfileSet) {
                            viewModel.updateBusinessName(name)
                        } else {
                            viewModel.saveBusinessName(name)
                        }

                        navController.popBackStack()
                    },
                    enabled = isNameValid &&
                            (!isProfileSet || hasChanges),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isProfileSet) seaweed else yaleBlue,
                        disabledContainerColor = Color.LightGray
                    )
                ) {
                    Text(
                        text = if (isProfileSet)
                            "Update Business"
                        else
                            "Save Business",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // PROFILE STATUS
            if (isProfileSet) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = seaweed.copy(alpha = 0.08f),
                            shape = RoundedCornerShape(14.dp)
                        )
                        .padding(15.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Business,
                        contentDescription = null,
                        tint = seaweed,
                        modifier = Modifier.size(22.dp)
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Text(
                            text = "Business profile active",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = seaweed
                        )

                        Text(
                            text = "You can change your business name anytime.",
                            fontSize = 11.sp,
                            color = Color.Gray
                        )
                    }
                }
            }
        }
    }
}
