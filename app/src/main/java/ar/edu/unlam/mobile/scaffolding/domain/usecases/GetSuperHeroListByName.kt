package ar.edu.unlam.mobile.scaffolding.domain.usecases

import ar.edu.unlam.mobile.scaffolding.core.repairImage
import ar.edu.unlam.mobile.scaffolding.data.database.entities.toEntity
import ar.edu.unlam.mobile.scaffolding.data.di.NetworkUtils
import ar.edu.unlam.mobile.scaffolding.data.local.model.SuperHeroItem
import ar.edu.unlam.mobile.scaffolding.data.local.model.toSuperHeroItem
import ar.edu.unlam.mobile.scaffolding.data.repository.SuperHeroRepository
import javax.inject.Inject

class GetSuperHeroListByName @Inject constructor(
    private val superHeroRepository: SuperHeroRepository,
    private val networkUtils: NetworkUtils
) {

    suspend operator fun invoke(query: String): List<SuperHeroItem> {
        return if (networkUtils.isInternetAvailable()) {
            val heroListFromApi = superHeroRepository.getSuperHeroListByName(query)
            superHeroRepository.deleteSuperHeroOffline()
            superHeroRepository.insertSuperHeroOffline(heroListFromApi.map { it.toEntity() })
            checkHeroListNulls(heroListFromApi)
        } else {
            superHeroRepository.getAllSuperHeroesFromDataBase().map { it.toSuperHeroItem() }
        }
    }
}

private fun checkHeroListNulls(heroList: List<SuperHeroItem>): List<SuperHeroItem> {
    val badIDs = listOf("7", "16", "22", "51", "59", "84", "113", "147")

    for (hero in heroList) {
        if (hero.id in badIDs) {
            hero.repairImage()
        }

        if (hero.powerstats.intelligence == "null") {
            hero.powerstats.intelligence = "50"
        }
        if (hero.powerstats.strength == "null") {
            hero.powerstats.strength = "70"
        }
        if (hero.powerstats.speed == "null") {
            hero.powerstats.speed = "70"
        }
        if (hero.powerstats.durability == "null") {
            hero.powerstats.durability = "100"
        }
        if (hero.powerstats.power == "null") {
            hero.powerstats.power = "70"
        }
        if (hero.powerstats.combat == "null") {
            hero.powerstats.combat = "70"
        }
    }
    return heroList
}

