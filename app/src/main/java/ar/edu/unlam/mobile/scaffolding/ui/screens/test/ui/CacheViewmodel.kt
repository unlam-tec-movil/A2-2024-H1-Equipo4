package ar.edu.unlam.mobile.scaffolding.ui.screens.test.ui

import androidx.lifecycle.ViewModel
import ar.edu.unlam.mobile.scaffolding.data.di.NetworkUtils
import ar.edu.unlam.mobile.scaffolding.ui.screens.test.data.Repository
import ar.edu.unlam.mobile.scaffolding.ui.screens.test.data.SuperHero
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class CacheViewmodel @Inject constructor(
    private val repository: Repository,
    private val networkUtils: NetworkUtils
) : ViewModel() {

    private val _superHero = MutableStateFlow(null)
    val superHero = _superHero.asStateFlow()
    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()
    private val _isInternet = MutableStateFlow(networkUtils.isInternetAvailable())
    val isInternet = _isInternet.asStateFlow()

    init {
        repository.superHero = SuperHero(
            "Batman",
            imageURL = "https://www.mundodeportivo.com/alfabeta/hero/2023/11/batman-es-un-heroe-iconico-de-dc-1.png?width=768&aspect_ratio=16:9&format=nowebp"
        )
        _isLoading.value = false
    }
}