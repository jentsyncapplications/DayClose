package com.jmapps.dayclose.ui_screens.components

import androidx.compose.animation.animateColorAsState

//GreetingHeaderComponent


import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.NightsStay
import androidx.compose.material.icons.outlined.WbSunny
import androidx.compose.material.icons.outlined.WbTwilight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.colorResource
import com.jmapps.dayclose.R
import java.time.LocalTime

private enum class GreetingPeriod {
    MORNING,
    AFTERNOON,
    EVENING,
    NIGHT
}

private fun getGreetingPeriod(): GreetingPeriod {
    return when (LocalTime.now().hour) {
        in 5..11 -> GreetingPeriod.MORNING
        in 12..16 -> GreetingPeriod.AFTERNOON
        in 17..19 -> GreetingPeriod.EVENING
        else -> GreetingPeriod.NIGHT
    }
}

@Composable
fun GreetingHeader(
    businessName: String,
    isProfileSet: Boolean,
    onProfileClick: () -> Unit
) {

    val navy = colorResource(R.color.oxford_navy)
    val seaweed = colorResource(R.color.seaweed)

    val period = remember { getGreetingPeriod() }

    val greeting = when (period) {
        GreetingPeriod.MORNING -> "Good morning,"
        GreetingPeriod.AFTERNOON -> "Good afternoon,"
        GreetingPeriod.EVENING -> "Good evening,"
        GreetingPeriod.NIGHT -> "Good night,"
    }

    val displayName = if (isProfileSet && businessName.isNotBlank()) {
        businessName
    } else {
        "Business Owner"
    }

    val subtitle = if (isProfileSet && businessName.isNotBlank()) {
        "Here's your business at a glance."
    } else {
        "Let's make today count!"
    }

    // Time-based gradient colors
    val gradientStart = when (period) {
        GreetingPeriod.MORNING -> Color(0xFF365F87)
        GreetingPeriod.AFTERNOON -> Color(0xFF176B72)
        GreetingPeriod.EVENING -> Color(0xFF754D71)
        GreetingPeriod.NIGHT -> Color(0xFF172B50)
    }

    val gradientEnd = when (period) {
        GreetingPeriod.MORNING -> Color(0xFF78A9B8)
        GreetingPeriod.AFTERNOON -> Color(0xFF3C9B86)
        GreetingPeriod.EVENING -> Color(0xFFCF815F)
        GreetingPeriod.NIGHT -> Color(0xFF39466F)
    }

    // Smooth gradient transition
    val animatedStart by animateColorAsState(
        targetValue = gradientStart,
        animationSpec = tween(1200),
        label = "gradientStart"
    )

    val animatedEnd by animateColorAsState(
        targetValue = gradientEnd,
        animationSpec = tween(1200),
        label = "gradientEnd"
    )

    // Continuous floating animation
    val infiniteTransition = rememberInfiniteTransition(
        label = "headerAnimation"
    )

    val floatOffset by infiniteTransition.animateFloat(
        initialValue = -3f,
        targetValue = 3f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 2200,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "floating"
    )

    val iconRotation by infiniteTransition.animateFloat(
        initialValue = -8f,
        targetValue = 8f,
        animationSpec = infiniteRepeatable(
            animation = tween(3500),
            repeatMode = RepeatMode.Reverse
        ),
        label = "iconRotation"
    )

    val iconScale by infiniteTransition.animateFloat(
        initialValue = 0.95f,
        targetValue = 1.08f,
        animationSpec = infiniteRepeatable(
            animation = tween(1800),
            repeatMode = RepeatMode.Reverse
        ),
        label = "iconScale"
    )

    // Select time-based icon
    val weatherIcon = when (period) {
        GreetingPeriod.MORNING,
        GreetingPeriod.AFTERNOON -> Icons.Outlined.WbSunny

        GreetingPeriod.EVENING -> Icons.Outlined.WbTwilight

        GreetingPeriod.NIGHT -> Icons.Outlined.NightsStay
    }

    val atmosphereColor = when (period) {
        GreetingPeriod.MORNING -> Color(0xFFFFD88A)
        GreetingPeriod.AFTERNOON -> Color(0xFFFFE0A0)
        GreetingPeriod.EVENING -> Color(0xFFFFC09B)
        GreetingPeriod.NIGHT -> Color(0xFFD6E2FF)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        animatedStart,
                        animatedEnd
                    )
                ),
                shape = RoundedCornerShape(20.dp)
            )
            .padding(
                horizontal = 18.dp,
                vertical = 18.dp
            )
    ) {

        // Decorative background circles
        Box(
            modifier = Modifier
                .size(125.dp)
                .align(Alignment.CenterEnd)
                .offset(x = 35.dp, y = 35.dp)
                .background(
                    Color.White.copy(alpha = 0.06f),
                    CircleShape
                )
        )

        Box(
            modifier = Modifier
                .size(80.dp)
                .align(Alignment.TopEnd)
                .offset(x = 15.dp, y = (-35).dp)
                .background(
                    Color.White.copy(alpha = 0.05f),
                    CircleShape
                )
        )

        // Main content
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // Greeting and business name
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {

                Text(
                    text = greeting,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White.copy(alpha = 0.85f)
                )

                Text(
                    text = displayName,
                    fontSize = 21.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    maxLines = 1
                )

                Text(
                    text = subtitle,
                    fontSize = 11.sp,
                    color = Color.White.copy(alpha = 0.78f)
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Animated sun, sunset or moon
            Box(
                modifier = Modifier
                    .size(65.dp),
                contentAlignment = Alignment.Center
            ) {

                // Soft glowing background
                Box(
                    modifier = Modifier
                        .size(55.dp)
                        .scale(iconScale)
                        .background(
                            atmosphereColor.copy(alpha = 0.15f),
                            CircleShape
                        )
                )

                Icon(
                    imageVector = weatherIcon,
                    contentDescription = greeting,
                    tint = atmosphereColor,
                    modifier = Modifier
                        .size(39.dp)
                        .offset(y = floatOffset.dp)
                        .rotate(
                            if (period == GreetingPeriod.MORNING ||
                                period == GreetingPeriod.AFTERNOON
                            ) iconRotation else 0f
                        )
                )

                // Night-time decorative stars
                if (period == GreetingPeriod.NIGHT) {

                    Text(
                        text = "✦",
                        color = Color.White.copy(alpha = 0.85f),
                        fontSize = 13.sp,
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .alpha(iconScale - 0.1f)
                    )

                    Text(
                        text = "·",
                        color = Color.White,
                        fontSize = 20.sp,
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                    )
                }
            }

            // Profile button
            IconButton(
                onClick = onProfileClick,
                modifier = Modifier
                    .size(39.dp)
                    .background(
                        Color.White.copy(alpha = 0.16f),
                        CircleShape
                    )
            ) {
                Icon(
                    imageVector = Icons.Outlined.AccountCircle,
                    contentDescription = "Manage profile",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}
