package com.example.bookreadingapp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.unit.sp
import com.example.bookreadingapp.R

val BonaNovaSc = FontFamily(
    Font(R.font.bonanovasc_regular),
    Font(R.font.bonanovasc_bold),
    Font(R.font.bonanovasc_italic)
)

val EBGaramond = FontFamily(
    Font(R.font.ebgaramond_regular),
    Font(R.font.ebgaramond_bold),
    Font(R.font.ebgaramond_italic)
)

val NotoSerif = FontFamily(
    Font(R.font.notoserif_regular),
    Font(R.font.notoserif_bold),
    Font(R.font.notoserif_italic)
)

val Prompt = FontFamily(
    Font(R.font.prompt_regular),
    Font(R.font.prompt_bold),
    Font(R.font.prompt_italic)
)

// Set of Material typography styles to start with
val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = BonaNovaSc,
        fontWeight = FontWeight.Normal,
        fontSize = 36.sp
    ),
    displayMedium = TextStyle(
        fontFamily = EBGaramond,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    ),
    labelSmall = TextStyle(
        fontFamily = NotoSerif,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = Prompt,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    )
)