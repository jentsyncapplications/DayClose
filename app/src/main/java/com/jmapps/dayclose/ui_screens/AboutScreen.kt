
package com.jmapps.dayclose.ui_screens

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.jmapps.dayclose.R
import java.time.Year
import androidx.core.net.toUri

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(
    navController: NavController
) {

    val context = LocalContext.current

    val navy = colorResource(R.color.oxford_navy)
    val yaleBlue = colorResource(R.color.yale_blue)
    val seaweed = colorResource(R.color.seaweed)
    val goldenOrange = colorResource(R.color.golden_orange)
    val background = colorResource(R.color.light_background)
    val border = colorResource(R.color.border_color)

    // Dynamic app version
//    val appVersion = BuildConfig.VERSION_NAME


    val appVersion = try{
        val packageInfo =context.packageManager.getPackageInfo(context.packageName, 0)
        packageInfo.versionName
    } catch (e: PackageManager.NameNotFoundException){
        "1.0.0"
    }

    // Replace these with your actual URLs and email.
    val privacyPolicyUrl = "https://yourdomain.com/privacy-policy"
    val termsUrl = "https://yourdomain.com/terms"
    val developerEmail = "support@yourdomain.com"

    fun openUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, url.toUri())
        context.startActivity(intent)
    }

    fun contactDeveloper() {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = "mailto:$developerEmail".toUri()
            putExtra(Intent.EXTRA_SUBJECT, "DayClose Support")
        }
        context.startActivity(intent)
    }

    fun shareApp() {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(
                Intent.EXTRA_TEXT,
                "Check out Day Close – Close your day. Know your numbers."
            )
        }

        context.startActivity(
            Intent.createChooser(intent, "Share DayClose")
        )
    }

    Scaffold(
        containerColor = background,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "About Day Close",
                        fontSize = 20.sp,
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
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {

            // APP IDENTITY
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Box(
                    modifier = Modifier
                        .size(85.dp)
                        .background(
                            color = yaleBlue.copy(alpha = 0.12f),
                            shape = RoundedCornerShape(24.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.AccountBalanceWallet,
                        contentDescription = "DayClose logo",
                        tint = yaleBlue,
                        modifier = Modifier.size(45.dp)
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = "Day Close",
                    fontSize = 27.sp,
                    fontWeight = FontWeight.Bold,
                    color = navy
                )

                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = "Close your day. Know your numbers.",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = seaweed
                )

                Spacer(modifier = Modifier.height(12.dp))

                Surface(
                    shape = RoundedCornerShape(50.dp),
                    color = seaweed.copy(alpha = 0.10f)
                ) {
                    Text(
                        text = "Version $appVersion",
                        modifier = Modifier.padding(
                            horizontal = 14.dp,
                            vertical = 6.dp
                        ),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = seaweed
                    )
                }
            }

            // ABOUT THE APP
            AboutInfoCard(
                title = "About the app",
                border = border
            ) {
                Text(
                    text = "DayClose helps you manage daily business cash flow, " +
                            "track expenses, monitor opening and closing balances, " +
                            "and understand your daily performance.",
                    fontSize = 13.sp,
                    color = Color.DarkGray,
                    lineHeight = 21.sp
                )
            }

            // APP INFORMATION
            AboutInfoCard(
                title = "App information",
                border = border
            ) {
                AboutDetailRow(
                    title = "App name",
                    value = "Day Close"
                )

                AboutDivider(border)

                AboutDetailRow(
                    title = "Version",
                    value = appVersion.toString()
                )

                AboutDivider(border)

                AboutDetailRow(
                    title = "Developer",
                    value = "Cassie J Digital"
                )
            }

            // MORE INFORMATION
            AboutInfoCard(
                title = "More information",
                border = border
            ) {

                AboutActionItem(
                    icon = Icons.Outlined.PrivacyTip,
                    title = "Privacy Policy",
                    subtitle = "Learn how your data is handled",
                    iconColor = yaleBlue,
                    onClick = {
                        openUrl(privacyPolicyUrl)
                    }
                )

                AboutDivider(border)

                AboutActionItem(
                    icon = Icons.Outlined.Description,
                    title = "Terms of Service",
                    subtitle = "Rules and conditions of using DayClose",
                    iconColor = goldenOrange,
                    onClick = {
                        openUrl(termsUrl)
                    }
                )

                AboutDivider(border)

                AboutActionItem(
                    icon = Icons.Outlined.Email,
                    title = "Contact Developer",
                    subtitle = "Get help or share your feedback",
                    iconColor = seaweed,
                    onClick = {
                        contactDeveloper()
                    }
                )

                AboutDivider(border)

                AboutActionItem(
                    icon = Icons.Outlined.Share,
                    title = "Share DayClose",
                    subtitle = "Recommend DayClose to others",
                    iconColor = yaleBlue,
                    onClick = {
                        shareApp()
                    }
                )
            }

            // FOOTER
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp, bottom = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "Made for better business management.",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = navy
                )

                Spacer(modifier = Modifier.height(6.dp))

                val currentYear = Year.now().value

                Text(
                    text = if (currentYear > 2026) {
                        "© 2026–$currentYear JM Apps. All rights reserved."
                    } else {
                        "© 2026 Cassie J Digital. All rights reserved."
                    },
                    fontSize = 11.sp,
                    color = Color.Gray
                )
            }
        }
    }
}


// REUSABLE INFORMATION CARD
@Composable
private fun AboutInfoCard(
    title: String,
    border: Color,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color.White,
                shape = RoundedCornerShape(19.dp)
            )
            .padding(17.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        content = content
    )
}


// APP INFORMATION ROW
@Composable
private fun AboutDetailRow(
    title: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            fontSize = 13.sp,
            color = Color.Gray
        )

        Text(
            text = value,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF263238)
        )
    }
}


// INFORMATION ACTION ITEM
@Composable
private fun AboutActionItem(
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
            .padding(vertical = 9.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .size(42.dp)
                .background(
                    color = iconColor.copy(alpha = 0.10f),
                    shape = RoundedCornerShape(12.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconColor,
                modifier = Modifier.size(21.dp)
            )
        }

        Spacer(modifier = Modifier.width(13.dp))

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            Text(
                text = title,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF202938)
            )

            Text(
                text = subtitle,
                fontSize = 11.sp,
                color = Color.Gray
            )
        }

        Icon(
            imageVector = Icons.Outlined.ChevronRight,
            contentDescription = "Open $title",
            tint = Color.LightGray,
            modifier = Modifier.size(21.dp)
        )
    }
}


@Composable
private fun AboutDivider(color: Color) {
    HorizontalDivider(
        color = color,
        thickness = 0.7.dp
    )
}
