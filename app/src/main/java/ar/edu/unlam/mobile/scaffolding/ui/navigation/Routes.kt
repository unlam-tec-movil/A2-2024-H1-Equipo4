package ar.edu.unlam.mobile.scaffolding.ui.navigation

sealed class Routes(val route : String){
    data object PresentationScreen : Routes("PresentationScreen")
    data object SelectCharacterScreen : Routes("SelectCharacterScreen")
    data object SuperHeroCombatScreen : Routes("SuperHeroCombatScreen")
    data object SuperHeroCombatResultScreen : Routes("SuperHeroCombatResultScreen")
    data object SuperHeroDetailScreen : Routes("SuperHeroDetailScreen")
    data object SuperHeroRankedScreen: Routes ("SuperHeroRankedScreen")
}
