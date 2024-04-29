package com.example.designcombat.ui.screen

import androidx.lifecycle.ViewModel
import com.example.testcombat.data.model.SuperHeroAppearance
import com.example.testcombat.data.model.SuperHeroBiography
import com.example.testcombat.data.model.SuperHeroImage
import com.example.testcombat.data.model.SuperHeroItem
import com.example.testcombat.data.model.SuperHeroPowerStats
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject



@HiltViewModel
class CombatViewModel @Inject constructor() : ViewModel() {

    private var _superHeroPlayer = MutableStateFlow<SuperHeroItem?>(null)
    val superHeroPlayer = _superHeroPlayer.asStateFlow()
    private var _superHeroCom = MutableStateFlow<SuperHeroItem?>(null)
    val superHeroCom = _superHeroCom.asStateFlow()
    private var _buttonEnable = MutableStateFlow(true)
    val buttonEnable = _buttonEnable.asStateFlow()


    init {
        val superHero1 = SuperHeroItem(
            id = "70", name = "Batman",
            powerStats = SuperHeroPowerStats(
                intelligence = "100",
                strength = "26",
                speed = "27",
                durability = "100",
                power = "47",
                combat = "100"
            ),
            biography = SuperHeroBiography(
                fullName = "Bruce Wayne",
                firstAppearance = "Detective Comics #27",
                publisher = "DC Comics",
                alignment = "good"
            ),
            appearance = SuperHeroAppearance(gender = "Male", race = "Human"),
            image = SuperHeroImage(url = "https://www.superherodb.com/pictures2/portraits/10/100/639.jpg")
        )

        val superHero2 = SuperHeroItem(
            id = "644",
            name = "Superman",
            powerStats = SuperHeroPowerStats(
                intelligence = "94",
                strength = "100",
                speed = "100",
                durability = "100",
                power = "100",
                combat = "85"
            ),
            biography = SuperHeroBiography(
                fullName = "Clark Kent",
                firstAppearance = "ACTION COMICS #1",
                publisher = "Superman Prime One-Million",
                alignment = "good"
            ),
            appearance = SuperHeroAppearance(gender = "Male", race = "Kryptonian"),
            image = SuperHeroImage(url = "https://www.superherodb.com/pictures2/portraits/10/100/791.jpg")
        )
        _superHeroPlayer.value = superHero1
        _superHeroCom.value = superHero2
    }
}