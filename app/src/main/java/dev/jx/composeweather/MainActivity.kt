package dev.jx.composeweather

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.window.PopupProperties
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.LocationServices
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.skydoves.landscapist.glide.GlideImage
import dagger.hilt.android.AndroidEntryPoint
import dev.jx.composeweather.data.remote.model.openweather.DailyWeather
import dev.jx.composeweather.data.remote.model.openweather.HourlyWeather
import dev.jx.composeweather.ui.theme.BlueLight
import dev.jx.composeweather.ui.theme.ComposeWeatherTheme
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

private lateinit var viewModel: MainActivityViewModel
private lateinit var permissionsLauncher: ActivityResultLauncher<String>
private lateinit var geoCoder: Geocoder

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        geoCoder = Geocoder(this, Locale.getDefault())

        permissionsLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) {}

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
            == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                if (Geocoder.isPresent()) {
                    val city = geoCoder.getFromLocation(location.latitude, location.longitude, 1)
                        .firstOrNull()?.locality
                    if (city != null) viewModel.getWeatherInfo(city)
                }
            }
        } else {
            permissionsLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
        }

        setContent {
            ComposeWeatherTheme {

                val localFocusManager = LocalFocusManager.current

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .pointerInput(Unit) {
                            detectTapGestures(onTap = {
                                localFocusManager.clearFocus()
                            })
                        }
                        .verticalScroll(rememberScrollState())
                ) {
                    SearchBar()
                    WeatherCard()
//                    WeatherHourlyCard((1..24).map { HourlyWeather() })
//                    WeatherDailyCard((1..7).map { DailyWeather() })
                    WeatherHourlyCard()
                    WeatherDailyCard()
                }
            }
        }
    }
}

@Composable
fun SearchBar() {
    val query = viewModel.query
    val predictions = viewModel.predictions.value
    var showMenu = predictions.isNotEmpty() && query.value.isNotEmpty()

    val keyboardControl = LocalFocusManager.current
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
            value = query.value,
            label = { Text(text = "City") },
            singleLine = true,
            onValueChange = {
                query.value = it
                viewModel.getPlacesPrediction(query.value)
            },
            leadingIcon = { Icon(imageVector = Icons.Filled.Search, contentDescription = null) },
            trailingIcon = {
                if (query.value.isNotEmpty()) {
                    Icon(Icons.Filled.Clear,
                        contentDescription = "Clear text",
                        modifier = Modifier.clickable {
                            query.value = ""
                        }
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    viewModel.getPlacesPrediction(query.value)
                    query.value = ""
                    keyboardControl.clearFocus()
                }
            ),
            colors = TextFieldDefaults
                .textFieldColors(
                    textColor = MaterialTheme.colors.secondary,
                    backgroundColor = MaterialTheme.colors.surface
                ),
            modifier = Modifier.onFocusChanged { query.value = "" }
        )
        DropdownMenu(
            expanded = showMenu,
            onDismissRequest = { showMenu = false },
            modifier = Modifier
                .width(with(LocalDensity.current) { textFieldSize.width.toDp() }),
            properties = PopupProperties(focusable = false)
        ) {
            QueryAutoComplete(predictions = predictions) { prediction ->
                viewModel.getWeatherInfo(prediction.getPrimaryText(null).toString())
                query.value = ""
                keyboardControl.clearFocus()
            }
        }
    }
}

@Composable
fun QueryAutoComplete(
    predictions: List<AutocompletePrediction>,
    itemCallback: (AutocompletePrediction) -> Unit
) {
    predictions.take(3).forEach { prediction ->
        DropdownMenuItem(onClick = { itemCallback(prediction) }) {
            Icon(
                imageVector = Icons.Filled.LocationOn,
                contentDescription = null,
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(text = prediction.getFullText(null).toString())
        }
    }
}

@Composable
fun WeatherCard() {
    val currentlyWeather = viewModel.currentlyWeather.value
    val weatherDescription = currentlyWeather.weather.firstOrNull()?.description?.replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
    } ?: ""
    val calendar = Calendar.getInstance()
    val date = calendar.time
    val formattedDate = SimpleDateFormat("EE, hh:mm a", Locale.getDefault()).format(date)

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
            Text(text = currentlyWeather.name, fontSize = 32.sp)
            Text(text = "$formattedDate, $weatherDescription")
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = currentlyWeather.main.temp.roundToInt().toString(), fontSize = 80.sp)
                Text(text = "ºC", fontSize = 48.sp)

                Spacer(modifier = Modifier.weight(1f))

                GlideImage(
                    imageModel = currentlyWeather.weather.firstOrNull()?.let {
                        stringResource(R.string.icon_url_2x, it.icon)
                    },
                    failure = {
                        Image(
                            painter = painterResource(id = R.drawable.cloudy_day_3),
                            contentDescription = "Weather Condition",
                            modifier = Modifier.size(132.dp)
                        )
                    },
                    contentDescription = "Weather Condition",
                    modifier = Modifier.size(132.dp)
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_humidity),
                    contentDescription = "Humidity icon",
                    modifier = Modifier.size(16.dp),
                    colorFilter = ColorFilter.tint(MaterialTheme.colors.secondary)
                )
                Text(
                    text = "${currentlyWeather.main.humidity}% Humidity",
                    modifier = Modifier.padding(horizontal = 4.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
                Image(
                    painter = painterResource(id = R.drawable.ic_wind),
                    contentDescription = "Winds icon",
                    modifier = Modifier.size(16.dp),
                    colorFilter = ColorFilter.tint(MaterialTheme.colors.secondary)
                )
                Text(
                    text = "${(currentlyWeather.wind.speed * 3.6).roundToInt()} km/h Winds",
                    modifier = Modifier.padding(horizontal = 4.dp)
                )
            }
        }
    }
}

@Composable
fun WeatherHourlyCard() {
    val hourlyWeather = viewModel.hourlyWeather.value

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
            LazyRow() {
                itemsIndexed(items = hourlyWeather.take(24)) { index, weather ->
                    WeatherHourlyItem(weather, index)
                }
            }
        }
    }
}

@Composable
fun WeatherHourlyItem(weather: HourlyWeather, pos: Int) {
    val hour = Calendar.getInstance()
    hour.set(Calendar.HOUR_OF_DAY, pos)
    val hourFormatted = SimpleDateFormat("h a", Locale.getDefault()).format(hour.time)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(horizontal = 8.dp)
    ) {
        GlideImage(
            imageModel = stringResource(R.string.icon_url_2x, weather.weather.first().icon),
            contentDescription = "Weather Condition",
            modifier = Modifier.size(56.dp)
        )
        Text(text = "${weather.temp.roundToInt()}ºC", modifier = Modifier.padding(vertical = 8.dp))
        Text(text = hourFormatted)
    }
}

@Composable
fun WeatherDailyCard() {
    val dailyWeather = viewModel.dailyWeather.value

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
                itemsIndexed(items = dailyWeather) { index, weather ->
                    WeatherDailyItem(weather, index)
                }
            }
        }
    }
}

@Composable
fun WeatherDailyItem(weather: DailyWeather, pos: Int) {
    val calendar = Calendar.getInstance(Locale.getDefault())
    calendar.timeInMillis = weather.dt * 1000

    val formattedDate = SimpleDateFormat("EEEE", Locale.getDefault()).format(calendar.time)

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = formattedDate,
            modifier = Modifier
                .padding(vertical = 8.dp)
                .widthIn(min = 100.dp, max = 100.dp)
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.ic_condition),
                contentDescription = null
            )
            Text(text = "${weather.humidity}%", color = BlueLight)
        }
        Text(text = "${weather.temp.min.roundToInt()}ºC")
        Text(text = "${weather.temp.max.roundToInt()}ºC")
    }
}

@Preview
@Composable
fun WeatherCardPreview() {
    ComposeWeatherTheme {
        val localFocusManager = LocalFocusManager.current
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        localFocusManager.clearFocus()
                    })
                }
        ) {
            SearchBar()
            WeatherCard()
//            WeatherHourlyCard((1..24).map { HourlyWeather() })
//            WeatherDailyCard((1..7).map { DailyWeather() })
        }
    }
}