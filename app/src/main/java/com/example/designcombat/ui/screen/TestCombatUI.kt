package com.example.designcombat.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.designcombat.ui.screen.component.SetOrientationScreen

@Composable
fun TestCombatUI(viewModel: CombatViewModel = hiltViewModel()) {
    val superHeroPlayer by viewModel.superHeroPlayer.collectAsState()
    val superHeroCom by viewModel.superHeroCom.collectAsState()
    val enableButton by viewModel.buttonEnable.collectAsState()

    if (superHeroPlayer == null && superHeroCom == null) {
        Box(Modifier.fillMaxSize()) {
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        }
    } else {

        SetOrientationScreen(
            context = LocalContext.current,
            orientation = OrientationScreen.PORTRAIT.orientation
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Cyan),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Holita", fontSize = 54.sp)
        }
    }
}





