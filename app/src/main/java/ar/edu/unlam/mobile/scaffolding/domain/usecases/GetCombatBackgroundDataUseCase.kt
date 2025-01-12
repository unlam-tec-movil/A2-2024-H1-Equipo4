package ar.edu.unlam.mobile.scaffolding.domain.usecases

import ar.edu.unlam.mobile.scaffolding.data.local.Background
import ar.edu.unlam.mobile.scaffolding.data.repository.SuperHeroRepository
import javax.inject.Inject

class GetCombatBackgroundDataUseCase
    @Inject
    constructor(
        private val superHeroRepository: SuperHeroRepository,
    ) {
        operator fun invoke(): List<Background> = superHeroRepository.getCombatBackgroundData()
    }
