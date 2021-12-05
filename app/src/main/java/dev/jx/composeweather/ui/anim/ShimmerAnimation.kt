package dev.jx.composeweather.ui.anim

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.jx.composeweather.R
import dev.jx.composeweather.ui.theme.BlueLight
import dev.jx.composeweather.ui.theme.ShimmerColorShades

@Composable
fun ShimmerAnimation(content: @Composable (Brush) -> Unit) {
    val transition = rememberInfiniteTransition()
    val translateAnim by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            tween(durationMillis = 1200, easing = FastOutSlowInEasing),
            RepeatMode.Reverse
        )
    )
    val brush = Brush.linearGradient(
        colors = ShimmerColorShades,
        start = Offset(10f, 10f),
        end = Offset(translateAnim, translateAnim)
    )
    content(brush)
}

@Composable
fun ShimmerWeatherCard(brush: Brush) {
    Card(
        elevation = 8.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            Spacer(
                modifier = Modifier
                    .size(250.dp, 50.dp)
                    .padding(bottom = 8.dp)
                    .background(brush)
            )
            Spacer(
                modifier = Modifier
                    .size(150.dp, 24.dp)
                    .background(brush)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(120.dp)
                        .padding(vertical = 8.dp)
                        .background(brush)
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Spacer(
                    modifier = Modifier
                        .size(120.dp, 24.dp)
                        .background(brush)
                )

                Spacer(modifier = Modifier.weight(1f))

                Spacer(
                    modifier = Modifier
                        .size(120.dp, 24.dp)
                        .background(brush)
                )
            }
        }
    }
}

@Composable
fun ShimmerWeatherHourlyCard(brush: Brush) {
    Card(
        elevation = 8.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            LazyRow {
                repeat(6) {
                    item {
                        ShimmerWeatherHourlyItem(brush)
                    }
                }
            }
        }
    }
}

@Composable
fun ShimmerWeatherHourlyItem(brush: Brush) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(horizontal = 8.dp)
    ) {
        Spacer(
            modifier = Modifier
                .padding(horizontal = 4.dp)
                .size(56.dp)
                .background(brush)
        )
        Spacer(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .size(50.dp, 16.dp)
                .background(brush)
        )
        Spacer(
            modifier = Modifier
                .size(64.dp, 16.dp)
                .background(brush)
        )
    }
}

@Composable
fun ShimmerWeatherDailyCard(brush: Brush) {
    Card(
        elevation = 8.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(bottom = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            LazyColumn(modifier = Modifier.heightIn(max = 250.dp)) {
                repeat(7) {
                    item {
                        ShimmerWeatherDailyItem(brush)
                    }
                }
            }
        }
    }
}

@Composable
fun ShimmerWeatherDailyItem(brush: Brush) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Spacer(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .size(100.dp, 20.dp)
                .background(brush)
        )
        Spacer(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .size(45.dp, 20.dp)
                .background(brush)
        )
        Spacer(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .size(100.dp, 20.dp)
                .background(brush)
        )
    }
}

@Composable
@Preview
fun Preview() {
    ShimmerAnimation {
//        ShimmerWeatherCard(it)
//        ShimmerWeatherHourlyCard(it)
        ShimmerWeatherDailyCard(it)
    }
}