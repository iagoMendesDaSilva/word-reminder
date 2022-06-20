/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.iago.reminder.theme

import androidx.compose.ui.graphics.Color
import androidx.wear.compose.material.Colors


val Primary = Color(0xFF8587FE)
val Black = Color(0xFF000000)
val Error =Color(0xFFF4376D)
val Success =Color(0xFF9BE15D)
val BlackOpacity = Color(0x80000000)
val BlueInfo = Color(0xFF03A9F4)
val White = Color(0xFFFFFFFF)
val WhiteOpacity = Color(0x80FFFFFF)
val WhiteOpacityPlus = Color(0x40FFFFFF)
val Gray = Color(0xFFC0BEBE)
val BackGround = Color(0xFF4E40D9)
val BackGroundDark = Color(0xFF4336C0)

internal val wearColorPalette: Colors = Colors(
    primary = Primary,
    background = BackGround,
    onBackground = BackGroundDark,
)
