package ar.edu.unlam.mobile.scaffolding.domain.usecases

import ar.edu.unlam.mobile.scaffolding.data.local.SuperHeroCombat
import ar.edu.unlam.mobile.scaffolding.data.repository.SuperHeroRepository
import javax.inject.Inject

class SetResultDataScreen @Inject constructor(private val repository: SuperHeroRepository) {

    operator fun invoke(
        superHeroPlayer: SuperHeroCombat,
        superHeroCom: SuperHeroCombat,
        lifePlayer: Int,
        lifeCom: Int
    ){
        repository.setResultDataScreen(superHeroPlayer,superHeroCom,lifePlayer,lifeCom)
    }
}