
package com.jmapps.dayclose.ui_screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.jmapps.dayclose.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(
    navController: NavController,
//    onPrivacyPolicy: () -> Unit = {},
//    onTermsOfService: () -> Unit = {},
//    onFeedback: () -> Unit = {},
//    onHelp: () -> Unit = {},
//    onAbout: () -> Unit = {},
//    onOnboarding: () -> Unit = {},
//    onNotificationChanged: (Boolean) -> Unit = {}
) {

    val navy = colorResource(R.color.oxford_navy)
    val seaweed = colorResource(R.color.seaweed)
    val yaleBlue = colorResource(R.color.yale_blue)
    val goldenOrange = colorResource(R.color.golden_orange)
    val background = colorResource(R.color.light_background)
    val border = colorResource(R.color.border_color)

    var notificationsEnabled by rememberSaveable {
        mutableStateOf(true)
    }



    Scaffold(
        containerColor = background,
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Settings",
                            fontSize = 21.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "Personalize your DayClose experience",
                            fontSize = 11.sp,
                            color = Color.White.copy(alpha = 0.8f)
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
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(22.dp)
        ) {

            // PREFERENCES
            SettingsSection(title = "PREFERENCES", titleColor = navy) {

                SettingsToggleItem(
                    icon = Icons.Outlined.Notifications,
                    title = "Notifications",
                    subtitle = "Receive daily reminders and updates",
                    iconColor = seaweed,
                    checked = notificationsEnabled,
                    onCheckedChange = { enabled ->
                        notificationsEnabled = enabled
                    }
                )
            }

            // SUPPORT & LEGAL
            SettingsSection(title = "SUPPORT & LEGAL", titleColor = navy) {

                SettingsItem(
                    icon = Icons.Outlined.PrivacyTip,
                    title = "Privacy Policy",
                    subtitle = "Learn how your data is handled",
                    iconColor = yaleBlue,
                    onClick = {

                    }
                )

                SettingsDivider(color = border)

                SettingsItem(
                    icon = Icons.Outlined.Gavel,
                    title = "Terms of Service",
                    subtitle = "Rules and conditions of using DayClose",
                    iconColor = goldenOrange,
                    onClick = {

                    }
                )

                SettingsDivider(color = border)

                SettingsItem(
                    icon = Icons.Outlined.Feedback,
                    title = "Send Feedback",
                    subtitle = "Share suggestions or report a problem",
                    iconColor = seaweed,
                    onClick = {

                    }
                )

                SettingsDivider(color = border)

                SettingsItem(
                    icon = Icons.Outlined.HelpOutline,
                    title = "Help & Support",
                    subtitle = "Get assistance using DayClose",
                    iconColor = yaleBlue,
                    onClick = {

                    }
                )
            }

            // ABOUT
            SettingsSection(title = "ABOUT", titleColor = navy) {

                SettingsItem(
                    icon = Icons.Outlined.AutoAwesome,
                    title = "App Onboarding",
                    subtitle = "Explore features and learn how to get started",
                    iconColor = goldenOrange,
                    onClick = {

                    }
                )

                SettingsDivider(color = border)

                SettingsItem(
                    icon = Icons.Outlined.Info,
                    title = "About DayClose",
                    subtitle = "App version and application information",
                    iconColor = navy,
                    onClick = {

                    }
                )
            }

            // FOOTER
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 2.dp, bottom = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "DayClose",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = navy
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Close your day. Know your numbers.",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }
    }
}


@Composable
private fun SettingsSection(
    title: String,
    titleColor: Color,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(9.dp)
    ) {

        Text(
            text = title,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.2.sp,
            color = titleColor.copy(alpha = 0.8f),
            modifier = Modifier.padding(start = 5.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(20.dp)
                ),
            content = content
        )
    }
}


@Composable
private fun SettingsItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    iconColor: Color,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 15.dp, vertical = 15.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .size(43.dp)
                .background(
                    color = iconColor.copy(alpha = 0.10f),
                    shape = RoundedCornerShape(13.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconColor,
                modifier = Modifier.size(22.dp)
            )
        }

        Spacer(modifier = Modifier.width(13.dp))

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF202938)
            )

            Text(
                text = subtitle,
                fontSize = 11.sp,
                color = Color.Gray,
                lineHeight = 16.sp
            )
        }

        Icon(
            imageVector = Icons.Outlined.ChevronRight,
            contentDescription = "Open $title",
            tint = Color.LightGray,
            modifier = Modifier.size(22.dp)
        )
    }
}


@Composable
private fun SettingsToggleItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    iconColor: Color,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onCheckedChange(!checked)
            }
            .padding(horizontal = 15.dp, vertical = 15.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .size(43.dp)
                .background(
                    color = iconColor.copy(alpha = 0.10f),
                    shape = RoundedCornerShape(13.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconColor,
                modifier = Modifier.size(22.dp)
            )
        }

        Spacer(modifier = Modifier.width(13.dp))

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF202938)
            )

            Text(
                text = subtitle,
                fontSize = 11.sp,
                color = Color.Gray,
                lineHeight = 16.sp
            )
        }

        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = iconColor,
                uncheckedThumbColor = Color.White,
                uncheckedTrackColor = Color.LightGray,
                uncheckedBorderColor = Color.LightGray
            )
        )
    }
}


@Composable
private fun SettingsDivider(color: Color) {
    HorizontalDivider(
        modifier = Modifier.padding(start = 70.dp, end = 15.dp),
        color = color,
        thickness = 0.7.dp
    )
}


@Preview(showBackground = true)
@Composable
fun SettingScreenPreview() {
    SettingScreen(
        navController = rememberNavController()
    )
}
