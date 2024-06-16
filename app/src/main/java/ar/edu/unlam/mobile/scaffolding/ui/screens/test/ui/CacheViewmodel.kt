package ar.edu.unlam.mobile.scaffolding.ui.screens.test.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.data.di.ImageCacheUtil
import ar.edu.unlam.mobile.scaffolding.data.di.NetworkUtils
import ar.edu.unlam.mobile.scaffolding.ui.screens.test.data.Repository
import ar.edu.unlam.mobile.scaffolding.ui.screens.test.data.SuperHero
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CacheViewmodel @Inject constructor(
    private val repository: Repository,
    private val networkUtils: NetworkUtils
) : ViewModel() {



    private var _superHero =  MutableStateFlow<SuperHero?>(null)
    val superHero = _superHero.asStateFlow()
    private var _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()
    private var _isInternet = MutableStateFlow(networkUtils.isInternetAvailable())
    val isInternet = _isInternet.asStateFlow()

    init {
        repository.superHero = SuperHero(
            "Batman",
            imageURL = "https://www.mundodeportivo.com/alfabeta/hero/2023/11/batman-es-un-heroe-iconico-de-dc-1.png?width=768&aspect_ratio=16:9&format=nowebp"
        )
        _superHero.value = repository.superHero
        _isLoading.value = false
    }

    fun updateInternet() {
        _isInternet.value = networkUtils.isInternetAvailable()
    }

    fun savePath(context: Context, imageURL: String) {
        viewModelScope.launch{
            val path = ImageCacheUtil.cacheImage(context,imageURL)
            repository.superHero!!.imagePath = path
        }

    }
}