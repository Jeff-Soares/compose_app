package dev.jx.composeweather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import dev.jx.composeweather.data.remote.model.googleplaces.PlaceAutocompletePrediction
import dev.jx.composeweather.data.remote.model.openweather.WeatherDaily
import dev.jx.composeweather.data.remote.model.openweather.WeatherHourly
import dev.jx.composeweather.ui.theme.BlueLight
import dev.jx.composeweather.ui.theme.ComposeWeatherTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeWeatherTheme {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize()
                ) {
                    SearchBar()
                    WeatherCard()
                    WeatherHourlyCard((1..24).map { WeatherHourly() })
                    WeatherDailyCard((1..7).map { WeatherDaily() })
                }
            }
        }
    }
}

@Composable
fun SearchBar() {
    var query by remember { mutableStateOf("") }
    var predictions by remember { mutableStateOf(listOf<PlaceAutocompletePrediction>()) }

    var showMenu by remember { mutableStateOf(false) }
    var textFieldSize by remember { mutableStateOf(Size.Zero) }

    Surface(
        elevation = 8.dp,
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .onGloballyPositioned { coordinates ->
                // This value is used to assign to the DropDown the same width
                textFieldSize = coordinates.size.toSize()
            }
    ) {
        TextField(
            value = query,
            label = { Text(text = "City") },
            maxLines = 1,
            onValueChange = {
                query = it
                // Implement google places auto complete service
            },
            leadingIcon = { Icon(imageVector = Icons.Filled.Search, contentDescription = null) },
            colors = TextFieldDefaults
                .textFieldColors(
                    textColor = MaterialTheme.colors.secondary,
                    backgroundColor = MaterialTheme.colors.surface
                )
        )
        DropdownMenu(
            expanded = showMenu,
            onDismissRequest = { showMenu = false },
            modifier = Modifier
                .width(with(LocalDensity.current) { textFieldSize.width.toDp() })
        ) {
            QueryAutoComplete(predictions = predictions)
        }
    }
}

@Composable
fun QueryAutoComplete(predictions: List<PlaceAutocompletePrediction>) {
    predictions.forEach { prediction ->
        DropdownMenuItem(onClick = { /*TODO*/ }) {
            Icon(imageVector = Icons.Filled.LocationOn, contentDescription = null)
            Text(text = prediction.description)
        }
    }
}

@Composable
fun WeatherCard() {
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
            Text(text = "Hong Kong", fontSize = 32.sp)
            Text(text = "Mon, 11:00 AM, Mostly Sunny")
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "23", fontSize = 80.sp)
                Text(text = "ºC", fontSize = 48.sp)
                Spacer(modifier = Modifier.weight(1f))
                Image(
                    painter = painterResource(id = R.drawable.cloudy_day_3),
                    contentDescription = "Weather Condition",
                    modifier = Modifier.size(132.dp)
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_precipitation),
                    contentDescription = "Precipitation icon",
                    modifier = Modifier.size(16.dp),
                    colorFilter = ColorFilter.tint(MaterialTheme.colors.secondary)
                )
                Text(text = "Precipitation", modifier = Modifier.padding(horizontal = 4.dp))
                Spacer(modifier = Modifier.weight(1f))
                Image(
                    painter = painterResource(id = R.drawable.ic_wind),
                    contentDescription = "Winds icon",
                    modifier = Modifier.size(16.dp),
                    colorFilter = ColorFilter.tint(MaterialTheme.colors.secondary)
                )
                Text(text = "5 km/h Winds", modifier = Modifier.padding(horizontal = 4.dp))
            }
        }
    }
}

@Composable
fun WeatherHourlyCard(weathers: List<WeatherHourly>) {
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
                items(items = weathers) { weather ->
                    WeatherHourlyItem(weather)
                }
            }
        }
    }
}

@Composable
fun WeatherHourlyItem(weather: WeatherHourly) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(horizontal = 8.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_condition),
            contentDescription = null,
            modifier = Modifier.size(56.dp)
        )
        Text(text = "20ºC", modifier = Modifier.padding(vertical = 8.dp))
        Text(text = "9 AM")
    }
}

@Composable
fun WeatherDailyCard(weathers: List<WeatherDaily>) {
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
            LazyColumn {
                items(items = weathers) { weather ->
                    WeatherDailyItem(weather)
                }
            }
        }
    }
}

@Composable
fun WeatherDailyItem(weather: WeatherDaily) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(text = "Today", modifier = Modifier.padding(vertical = 8.dp))
        Row {
            Image(
                painter = painterResource(id = R.drawable.ic_condition),
                contentDescription = null
            )
            Text(text = "40%", modifier = Modifier.padding(vertical = 8.dp), color = BlueLight)
        }
        Text(text = "19ºC", modifier = Modifier.padding(vertical = 8.dp))
        Text(text = "29ºC")
    }
}

@Preview
@Composable
fun WeatherCardPreview() {
    ComposeWeatherTheme {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            SearchBar()
            WeatherCard()
            WeatherHourlyCard((1..24).map { WeatherHourly() })
            WeatherDailyCard((1..7).map { WeatherDaily() })
        }
    }
}