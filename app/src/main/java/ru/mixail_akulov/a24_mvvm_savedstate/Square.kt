package ru.mixail_akulov.a24_mvvm_savedstate

class Squares private constructor(
    val size: Int,
    val colors: List<Int>
) {

    constructor(
        size: Int,
        colorProducer: () -> Int
    ) : this(
        size = size,
        colors = (0 until size*size).map { colorProducer() } // для каждого элемента списка
                                                             // будет вызываться метод colorProducer()
    )
}