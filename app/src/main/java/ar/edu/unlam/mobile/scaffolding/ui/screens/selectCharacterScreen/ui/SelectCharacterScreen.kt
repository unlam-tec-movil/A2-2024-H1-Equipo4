package ar.edu.unlam.mobile.scaffolding.ui.screens.selectCharacterScreen.ui

import android.annotation.SuppressLint
import android.content.Context
import android.media.MediaPlayer
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import ar.edu.unlam.mobile.scaffolding.R
import ar.edu.unlam.mobile.scaffolding.data.local.Background
import ar.edu.unlam.mobile.scaffolding.data.local.OrientationScreen.PORTRAIT
import ar.edu.unlam.mobile.scaffolding.data.local.model.SuperHeroItem
import ar.edu.unlam.mobile.scaffolding.ui.components.ButtonWithBackgroundImage
import ar.edu.unlam.mobile.scaffolding.ui.components.ExitConfirmation
import ar.edu.unlam.mobile.scaffolding.ui.components.IconPowerDetail
import ar.edu.unlam.mobile.scaffolding.ui.components.SearchHero
import ar.edu.unlam.mobile.scaffolding.ui.components.SetOrientationScreen
import ar.edu.unlam.mobile.scaffolding.ui.components.mediaPlayer
import ar.edu.unlam.mobile.scaffolding.ui.navigation.Routes
import ar.edu.unlam.mobile.scaffolding.ui.screens.selectCharacterScreen.ui.viewModel.SelectCharacterViewModel
import coil.compose.rememberAsyncImagePainter
import java.io.File

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SelectCharacterScreen(
    navController: NavHostController,
    selectCharacterViewModel: SelectCharacterViewModel
) {
    val context = LocalContext.current
    val isLoading = selectCharacterViewModel.isLoading.collectAsState()
    var showExitConfirmation by rememberSaveable {
        mutableStateOf(false)
    }

    SetOrientationScreen(
        context = context,
        orientation = PORTRAIT.orientation
    )

    if (isLoading.value) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        Scaffold(
            topBar = { TopBar(navController, selectCharacterViewModel, context) },
            content = { ContentView(navController, selectCharacterViewModel, context) }
        )
    }

    ExitConfirmation(
        show = showExitConfirmation,
        onDismiss = { showExitConfirmation = false },
        onConfirm = {
            navController.navigate(Routes.PresentationScreen.route) {
                popUpTo(Routes.PresentationScreen.route) {
                    inclusive = true
                }
            }
        },
        title = stringResource(id = R.string.ExitConfirmation),
        message = stringResource(id = R.string.ExitSelectCharacter)
    )

    BackHandler {
        showExitConfirmation = true
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    navController: NavHostController,
    selectCharacterViewModel: SelectCharacterViewModel,
    context: Context
) {
    val (expanded, setExpanded) = remember { mutableStateOf(false) }

    TopAppBar(
        modifier = Modifier.height(48.dp),
        title = {
            Text(
                text = "Character Selection",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                textAlign = TextAlign.Start,
                color = Color.White
            )
        },
        navigationIcon = {
            IconButton(onClick = {
                navController.navigate(Routes.PresentationScreen.route) {
                    popUpTo(Routes.PresentationScreen.route) {
                        inclusive = true
                    }
                }
            }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(Color.Black),
        actions = {
            IconButton(onClick = {
                selectCharacterViewModel.initListHero()
                Toast.makeText(context, "Update characters", Toast.LENGTH_SHORT).show()
            }) {
                Icon(
                    imageVector = Icons.Filled.Refresh,
                    contentDescription = null,
                    tint = Color.White
                )
            }
            IconButton(onClick = { setExpanded(true) }) {
                Icon(
                    imageVector = Icons.Filled.MoreVert,
                    contentDescription = null,
                    tint = Color.White
                )
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { setExpanded(false) }
            ) {
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clickable {
                            navController.navigate(Routes.SuperHeroRankedScreen.route)
                        }
                        .fillMaxWidth()
                ) {

                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.List,
                        contentDescription = null,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )

                    Text(
                        text = "View Ranked",
                        fontSize = 19.sp,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.padding(end = 16.dp)
                    )
                }
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                )
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clickable {
                            navController.navigate(Routes.PresentationScreen.route) {
                                popUpTo(Routes.PresentationScreen.route) {
                                    inclusive = true
                                }
                            }
                        }
                        .fillMaxWidth()
                ) {

                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                        contentDescription = null,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )

                    Text(
                        text = "Exit Game",
                        fontSize = 19.sp,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.padding(end = 16.dp)
                    )
                }

            }
        }
    )
}

@Composable
fun ContentView(
    navController: NavHostController,
    selectCharacterViewModel: SelectCharacterViewModel,
    context: Context
) {
    val playerList by selectCharacterViewModel.superHeroListPlayer.collectAsState()
    var searchHeroPlayer by remember { mutableStateOf("") }
    val backgroundData by selectCharacterViewModel.backgroundData.collectAsState()
    val comList by selectCharacterViewModel.superHeroListCom.collectAsState()
    var searchHeroCom by remember { mutableStateOf("") }
    val player by selectCharacterViewModel.playerSelected.collectAsState()
    val comPlayer by selectCharacterViewModel.comSelected.collectAsState()
    val background by selectCharacterViewModel.background.collectAsState()
    val audioPosition = selectCharacterViewModel.audioPosition.collectAsState()
    val isInternetAvailable by selectCharacterViewModel.isInternetAvailable.collectAsState()
    val audio = mediaPlayer(context, audioPosition)

    if (playerList.isNotEmpty() && comList.isNotEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 48.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {

                Text(text = "Select your player or search for your favorite...")

                SearchHero(
                    query = searchHeroPlayer,
                    onQueryChange = { searchHeroPlayer = it },
                    onSearch = { selectCharacterViewModel.searchHeroByNameToPlayer(searchHeroPlayer) },
                    searchEnabled = isInternetAvailable
                )

                Box(modifier = Modifier.weight(2f)) {
                    LazyRowWithImagesHeroPlayer(
                        heroList = playerList,
                        selectCharacterViewModel,
                        player,
                        navController,
                        audio,
                        isInternetAvailable
                    )
                }

                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(4.dp)
                        .padding(top = 4.dp),
                    color = Color.White
                )

                Text(
                    text = "Select player com or search your favorite...",
                    modifier = Modifier.padding(top = 8.dp)
                )

                SearchHero(
                    query = searchHeroCom,
                    onQueryChange = { searchHeroCom = it },
                    onSearch = { selectCharacterViewModel.searchHeroByNameToCom(searchHeroCom) },
                    searchEnabled = isInternetAvailable
                )

                Box(modifier = Modifier.weight(2f)) {
                    LazyRowWithImagesHeroCom(
                        heroList = comList,
                        selectCharacterViewModel,
                        comPlayer,
                        navController,
                        audio,
                        isInternetAvailable
                    )
                }

                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(1.dp)
                        .padding(top = 2.dp),
                    color = Color.White
                )

                Text(text = "Select the combat map ...", modifier = Modifier.padding(top = 8.dp))

                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(1.dp)
                        .padding(top = 2.dp),
                    color = Color.White
                )
                Box(modifier = Modifier.weight(2f)) {
                    LazyRowBackgroundData(
                        backgroundsList = backgroundData,
                        selectCharacterViewModel,
                        background
                    )
                }

                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(1.dp)
                        .padding(top = 2.dp),
                    color = Color.White
                )
                Box(modifier = Modifier.weight(1f)) {
                    ButtonWithBackgroundImage(
                        imageResId = R.drawable.iv_attack,
                        onClick = {
                            if (player != null && comPlayer != null && background != null) {
                                selectCharacterViewModel.setCombatData(
                                    player!!,
                                    comPlayer!!,
                                    background!!
                                )
                                navController.navigate(Routes.SuperHeroCombatScreen.route)
                            } else {
                                Toast.makeText(
                                    context,
                                    "Heroes or map not selected ",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }
                        },
                        modifier = Modifier
                            .align(alignment = Alignment.Center)
                            .fillMaxSize()
                    ) {
                        Text(text = "Start Combat", fontSize = 26.sp)
                    }

                }
            }
        }
    } else {
        Box(Modifier.fillMaxSize()) {
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        }
    }


}

@Composable
fun LazyRowWithImagesHeroPlayer(
    heroList: List<SuperHeroItem>,
    selectCharacterViewModel: SelectCharacterViewModel,
    player: SuperHeroItem?,
    navController: NavHostController,
    audio: MediaPlayer,
    isInternetAvailable: Boolean
) {
    val selectAudio = MediaPlayer.create(LocalContext.current, R.raw.raw_select)
    val cancelSelect = MediaPlayer.create(LocalContext.current, R.raw.raw_cancelselect)
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        items(heroList) { hero ->
            Card(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .size(150.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .clickable {
                        selectCharacterViewModel.setPlayer(hero)
                        if (player == hero) cancelSelect.start() else selectAudio.start()
                    }
                    .border(
                        width = 2.dp,
                        color = if (player != null && player == hero) Color.Green else Color.Transparent,
                        shape = RoundedCornerShape(8.dp)
                    ),

                elevation = CardDefaults.cardElevation(defaultElevation = 16.dp)
            ) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    if (isInternetAvailable){
                        Image(
                            painter = rememberAsyncImagePainter(hero.image.url),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }else{
                        Image(
                            painter = rememberAsyncImagePainter(File(hero.imagePath!!)),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }


                    IconButton(
                        onClick = {
                            selectCharacterViewModel.setSuperHeroDetail(hero)
                            navController.navigate(Routes.SuperHeroDetailScreen.route)
                            selectCharacterViewModel.setAudioPosition(audio.currentPosition)
                        }, modifier = Modifier.align(
                            Alignment.TopStart
                        )
                    ) {
                        IconPowerDetail()
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(30.dp)
                            .align(Alignment.BottomCenter)
                            .background(
                                colorResource(id = R.color.superhero_item_name)
                            )
                    ) {
                        Text(
                            text = hero.name,
                            modifier = Modifier.align(Alignment.BottomCenter),
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )

                    }
                }

            }
        }
    }
}

@Composable
fun LazyRowWithImagesHeroCom(
    heroList: List<SuperHeroItem>,
    selectCharacterViewModel: SelectCharacterViewModel,
    comPlayer: SuperHeroItem?,
    navController: NavHostController,
    audio: MediaPlayer,
    isInternetAvailable: Boolean
) {

    val selectAudio = MediaPlayer.create(LocalContext.current, R.raw.raw_select)
    val cancelSelect = MediaPlayer.create(LocalContext.current, R.raw.raw_cancelselect)
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        items(heroList) { hero ->
            Card(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .size(150.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .clickable {
                        selectCharacterViewModel.setCom(hero)
                        if (comPlayer == hero) cancelSelect.start() else selectAudio.start()
                    }
                    .border(
                        width = 2.dp,
                        color = if (comPlayer != null && comPlayer == hero) Color.Green else Color.Transparent,
                        shape = RoundedCornerShape(8.dp)
                    ),
                elevation = CardDefaults.cardElevation(defaultElevation = 16.dp)
            ) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    if (isInternetAvailable){
                        Image(
                            painter = rememberAsyncImagePainter(hero.image.url),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }else{
                        Image(
                            painter = rememberAsyncImagePainter(File(hero.imagePath!!)),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    IconButton(
                        onClick = {
                            selectCharacterViewModel.setSuperHeroDetail(hero)
                            navController.navigate(Routes.SuperHeroDetailScreen.route)
                            selectCharacterViewModel.setAudioPosition(audio.currentPosition)
                        }, modifier = Modifier.align(
                            Alignment.TopStart
                        )
                    ) {
                        IconPowerDetail()
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(30.dp)
                            .align(Alignment.BottomCenter)
                            .background(
                                colorResource(id = R.color.superhero_item_name)
                            )
                    ) {
                        Text(
                            text = hero.name,
                            modifier = Modifier.align(Alignment.BottomCenter),
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )

                    }
                }
            }
        }
    }
}

@Composable
fun LazyRowBackgroundData(
    backgroundsList: List<Background>,
    selectCharacterViewModel: SelectCharacterViewModel,
    background: Background?,
) {

    val selectAudio = MediaPlayer.create(LocalContext.current, R.raw.raw_select)
    val cancelSelect = MediaPlayer.create(LocalContext.current, R.raw.raw_cancelselect)
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        items(backgroundsList) { back ->
            Card(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .size(150.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .clickable {
                        selectCharacterViewModel.setBackground(back)
                        if (background == back) cancelSelect.start() else selectAudio.start()
                    }
                    .border(
                        width = 2.dp,
                        color = if (background != null && background == back) Color.Green else Color.Transparent,
                        shape = RoundedCornerShape(8.dp)
                    ),
                elevation = CardDefaults.cardElevation(defaultElevation = 16.dp)
            ) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Image(
                        painter = painterResource(id = back.background),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(30.dp)
                            .align(Alignment.BottomCenter)
                            .background(
                                colorResource(id = R.color.superhero_item_name)
                            )
                    ) {
                        Text(
                            text = back.name,
                            modifier = Modifier.align(Alignment.BottomCenter),
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )

                    }
                }
            }
        }
    }
}





