package com.example.projetopokedex.domain

private val KANTO_RANGE: IntRange = 1..151 // ids da Pokédex Kanto [web:86]

fun randomKantoId(): Int = KANTO_RANGE.random()