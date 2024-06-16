package ar.edu.unlam.mobile.scaffolding.ui.screens.test.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import java.io.File

@Composable
fun CacheTest(viewModel: CacheViewmodel = hiltViewModel()) {

    val superHero by viewModel.superHero.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val isInternet by viewModel.isInternet.collectAsState()
    val context = LocalContext.current

    if (isLoading){
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
    Column {

        Box(modifier = Modifier.fillMaxSize()){
            Text(text = superHero!!.name, modifier = Modifier
                .align(Alignment.Center)
                .padding(vertical = 30.dp), fontSize = 34.sp)

            Box(modifier = Modifier
                .fillMaxWidth()
                .height(250.dp), contentAlignment = Alignment.Center){

                if (isInternet){
                    Image(
                        painter = rememberAsyncImagePainter(superHero!!.imageURL),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }else{
                    Image(
                        painter = rememberAsyncImagePainter(File(superHero!!.imagePath!!)),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }


            }

            Button(onClick = { viewModel.updateInternet()}, modifier = Modifier.align(Alignment.BottomCenter)) {
                Text(text = "UPDATE")
            }

            Button(onClick = { viewModel.savePath(context,superHero!!.imageURL)}, modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 100.dp)) {
                Text(text = "SAVE")
            }

        }
    }

}