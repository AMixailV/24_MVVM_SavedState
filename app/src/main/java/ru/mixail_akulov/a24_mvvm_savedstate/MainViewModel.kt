package ru.mixail_akulov.a24_mvvm_savedstate

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import kotlin.random.Random

class MainViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    // Здесь пример немного отличается от примера в видеоуроке.

    // Обычно сложные объекты лучше не сохранять в Bundle (и в SavedStateHandle тоже).
    // Например, вы можете сохранить простое длинное значение с именем SEED; а затем использовать его для случайной
    // инициализации генератора, который всегда генерирует одни и те же квадраты для одного и того же начального числа.

    // LiveData для квадратов
    private val _seed = savedStateHandle.getLiveData<Long>(KEY_SEED)
    val squares: LiveData<Squares> = Transformations.map(_seed) { createSquares(it) }

    init {
        if (!savedStateHandle.contains(KEY_SEED)) { // если сохранитель состояния не имеет состояния по ключу
            savedStateHandle[KEY_SEED] = Random.nextLong() // то создаем новое
        }
    }

    fun generateSquares() {
        _seed.value = Random.nextLong()
    }

    private fun createSquares(seed: Long): Squares {
        // создать случайный объект с указанным начальным числом
        // Генераторы случайных чисел, инициализированные одним и тем же начальным числом,
        // всегда будут выдавать одну и ту же последовательность чисел.
        val random = Random(seed)
        return Squares(
            size = random.nextInt(5, 110),
            colorProducer = { -random.nextInt(0xFFFFFF) }
        )
    }

    companion object {
        const val KEY_SEED = "seed"
    }
}