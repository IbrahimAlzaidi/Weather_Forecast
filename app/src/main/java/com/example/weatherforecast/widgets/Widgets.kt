package com.example.weatherforecast.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.weatherforecast.R
import com.example.weatherforecast.model.WeatherItem
import com.example.weatherforecast.util.formatDate
import com.example.weatherforecast.util.formatDateTime
import com.example.weatherforecast.util.formatDecimals


@Composable
fun WeatherDetailRow(weather: WeatherItem, imageUrl: String) {

    Surface(modifier = Modifier
        .padding(3.dp)
        .fillMaxWidth(),
        shape = CircleShape.copy(topEnd = CornerSize(6.dp), bottomStart = CornerSize(6.dp)),
        color = Color.White) {
        Row(modifier = Modifier
            .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween) {
            Text(formatDate(weather.dt).split(",")[0],
                modifier = Modifier
                    .padding(start = 5.dp)
                    .weight(0.15f))
            WeatherStateImage(imageUrl = imageUrl)
            Surface(shape = CircleShape,
                color = Color(0xFFFFC400),
                modifier = Modifier.weight(0.3f)) {
                Text(text = weather.weather[0].description,
                    modifier = Modifier.padding(4.dp),
                    style = MaterialTheme.typography.caption,
                    textAlign = TextAlign.Center,
                    color = Color(0xFF2F0909))
            }
            Text(text = buildAnnotatedString {
                withStyle(style = SpanStyle(
                    color = Color.Blue.copy(alpha = 0.7f),
                    fontWeight = FontWeight.SemiBold)) { append(formatDecimals(weather.temp.max) + "°") }
                withStyle(style = SpanStyle(
                    color = Color.LightGray,
                    fontWeight = FontWeight.SemiBold)) { append(formatDecimals(weather.temp.min) + "°") }
            }, modifier = Modifier
                .weight(0.15f)
                .padding(end = 4.dp))
        }
    }
}


@Composable
fun SunSetSunRiseRow(weather: WeatherItem) {
    Row(modifier = Modifier
        .padding(top = 15.dp, bottom = 6.dp, start = 12.dp, end = 12.dp)
        .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween) {
        Row(modifier = Modifier.padding(4.dp)) {
            Icon(painter = painterResource(id = R.drawable.sunrise),
                contentDescription = "Sunrise icon", modifier = Modifier.size(25.dp))
            Text(text = formatDateTime(weather.sunrise), style = MaterialTheme.typography.caption)
        }
        Row(modifier = Modifier.padding(4.dp)) {
            Icon(painter = painterResource(id = R.drawable.sunset),
                contentDescription = "Sunset icon", modifier = Modifier.size(20.dp))
            Text(text = formatDateTime(weather.sunset), style = MaterialTheme.typography.caption)
        }


    }
}

@Composable
fun HumidityWindPressureRow(weather: WeatherItem, isImperial: Boolean) {

    Row(modifier = Modifier
        .padding(12.dp)
        .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween) {
        Row(modifier = Modifier.padding(4.dp)) {
            Icon(painter = painterResource(id = R.drawable.humidity),
                contentDescription = "Humidity icon", modifier = Modifier.size(20.dp))
            Text(text = "${weather.humidity}%", style = MaterialTheme.typography.caption)
        }
        Row(modifier = Modifier.padding(4.dp)) {
            Icon(painter = painterResource(id = R.drawable.pressure),
                contentDescription = "pressure icon", modifier = Modifier.size(20.dp))
            Text(text = "${weather.pressure} psi", style = MaterialTheme.typography.caption)
        }
        Row(modifier = Modifier.padding(4.dp)) {
            Icon(painter = painterResource(id = R.drawable.wind),
                contentDescription = "wind icon", modifier = Modifier.size(20.dp))
            Text(text = "${weather.speed}" + if (isImperial) "mph" else "m/s",
                style = MaterialTheme.typography.caption)
        }


    }

}


@Composable
fun WeatherStateImage(imageUrl: String) {

    Image(painter = rememberAsyncImagePainter(imageUrl),
        contentDescription = "Icon image",
        modifier = Modifier.size(80.dp))

}
