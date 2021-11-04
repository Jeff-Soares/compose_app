package dev.jx.composeweather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.jx.composeweather.ui.theme.ComposeWeatherTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeWeatherTheme {

            }
        }
    }
}

@Composable
fun WeatherCard() {
    Card(
        elevation = 8.dp,
        modifier = Modifier.width(IntrinsicSize.Min)
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Text(text = "Hong Kong", fontSize = 32.sp)
            Text(text = "Mon, 11:00 AM, Mostly Sunny")
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "23", fontSize = 80.sp)
                Text(text = "ºC ", fontSize = 48.sp)
                Image(
                    painter = painterResource(id = R.drawable.cloudy_day_3),
                    contentDescription = "Weather Condition",
                    modifier = Modifier.size(132.dp)
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row {
                    Image(
                        painter = painterResource(id = R.drawable.ic_precipitation),
                        contentDescription = "Precipitation icon",
                        modifier = Modifier.size(16.dp)
                    )
                    Text(text = "Precipitation", modifier = Modifier.padding(horizontal = 4.dp))
                }
                Row {
                    Image(
                        painter = painterResource(id = R.drawable.ic_wind),
                        contentDescription = "Winds icon",
                        modifier = Modifier.size(16.dp)
                    )
                    Text(text = "5 km/h Winds", modifier = Modifier.padding(horizontal = 4.dp))
                }
            }
        }
    }
}

@Preview
@Composable
fun WeatherCardPreview() {
    ComposeWeatherTheme {
        WeatherCard()
    }
}