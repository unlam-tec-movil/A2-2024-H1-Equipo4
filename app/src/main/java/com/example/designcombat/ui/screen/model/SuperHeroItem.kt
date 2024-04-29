package com.example.testcombat.data.model

data class SuperHeroItem(
    val id: String,
    val name: String,
    val powerStats: SuperHeroPowerStats,
    val biography : SuperHeroBiography,
    val appearance : SuperHeroAppearance,
    val image : SuperHeroImage
)
