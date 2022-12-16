package com.iago.reminder.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.iago.reminder.R

val fonts = FontFamily(
    Font(R.font.nunito_black, weight = FontWeight.Black, style = FontStyle.Normal),
    Font(R.font.nunito_blackitalic, weight = FontWeight.Black, style = FontStyle.Italic),
    Font(R.font.nunito_bold, weight = FontWeight.Bold, style = FontStyle.Normal),
    Font(R.font.nunito_bolditalic, weight = FontWeight.Bold, style = FontStyle.Italic),
    Font(R.font.nunito_extrabold, weight = FontWeight.ExtraBold, style = FontStyle.Normal),
    Font(R.font.nunito_extrabolditalic, weight = FontWeight.ExtraBold, style = FontStyle.Italic),
    Font(R.font.nunito_extralight, weight = FontWeight.ExtraLight, style = FontStyle.Normal),
    Font(R.font.nunito_extralightitalic, weight = FontWeight.ExtraLight, style = FontStyle.Italic),
    Font(R.font.nunito_italic, weight = FontWeight.Normal, style = FontStyle.Italic),
    Font(R.font.nunito_light, weight = FontWeight.Light, style = FontStyle.Normal),
    Font(R.font.nunito_lightitalic, weight = FontWeight.Light, style = FontStyle.Italic),
    Font(R.font.nunito_medium, weight = FontWeight.Medium, style = FontStyle.Normal),
    Font(R.font.nunito_mediumitalic, weight = FontWeight.Medium, style = FontStyle.Italic),
    Font(R.font.nunito_regular, weight = FontWeight.Normal, style = FontStyle.Normal),
    Font(R.font.nunito_semibold, weight = FontWeight.SemiBold, style = FontStyle.Normal),
    Font(R.font.nunito_semibolditalic, weight = FontWeight.SemiBold, style = FontStyle.Italic),
)

val Typography = Typography(
    body1 = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    body2 = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp
    ),
    button = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp
    ),
    h1 = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp
    ),
    h2 = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Normal,
        fontSize = 26.sp
    ),
    h3 = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp
    ),
)