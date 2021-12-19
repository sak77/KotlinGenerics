package com.saket.kotlingenerics

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

/**
 * The purpose of this project is to explore generics in
 * Kotlin. Find out use cases where generics are a good option.
 * Generic classes and functions.
 * Inheritance with generics: covariance, contravariant, invariance etc.
 * Type projection and star projection.
 *
 * Here i create my own collection class that holds a list of objects.
 * One can perform some actions with this class.
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Create a collection of cars and engines
        val engines = MyCollection<Engine>()
        engines.addItem(Engine("Honda", 100))
        engines.addItem(Engine("Mercedes", 125))
        engines.addItem(Engine("Renault", 90))

        engines.getList().forEach {
            println("Engine brand: ${it.brand}, Engine power: ${it.power}")
        }

        //Create collection of cars...
        val cars = MyCollection<Car>()
        val mercedesGray = Car("Mercedes", "Grey")
        mercedesGray.setEngine(engines.getItem(1))
        val mercedesRed = Car("Mercedes", "Red")
        mercedesRed.setEngine(engines.getItem(0))
        val hondaBlue = Car("Honda", "Blue")
        hondaBlue.setEngine(engines.getItem(2))

        cars.addItem(mercedesGray)
        cars.addItem(mercedesRed)
        cars.addItem(hondaBlue)

        //Looking at type projections.
        boxCar(Box(mercedesGray))
        examine(Box(mercedesGray))
        examine(Box(FerrariCar()))
        examine(Box(mercedesRed))
    }

    /*
    out is a variance annotation. It is a promise that the function will only read
    the parameter value and not set/update its value. Or it gives an error.

    This can be considered as a drawback of type projections. But the benefit here is
    that we can use sub-typing. So examine can accept any object which derives from
    Box class, eg: FerrariCar().
     */
    private fun examine(boxedCar: Box<out Car>) {
        println("${boxedCar.item} has boxed")
        //boxedCar.item = Car() error: Setter for item is removed by type projection.
    }

    /*
    in is a variance annotation. The value of input type can be set. Irrespective of the input type,
    when reading the value, it is of type Any?. So i cast it here to Car before returning its value.

    This can be considered as a drawback of type projections. But the benefit here is
    that we can use sub-typing.
     */
    private fun boxCar(boxedCar: Box<in Car>): Car {
        return boxedCar.item as Car
    }

    /*
    Star projections combine restrictions of in and out projections:
    Item value cannot be set. Item value returned is of type Any?
     */
    private fun starCar(car: Box<*>) : Car {
        //car.item = Car() error
        return car.item as Car
    }
}

open class Car(val brand: String, val color: String) {
    lateinit var carEngine: Engine

    //functions can also have generic parameters.
    //This is not such a good example, but it is just to demonstrate
    fun <E> setEngine(engine: E) {
        carEngine = engine as Engine
    }

    fun getEngine() = carEngine
}

data class FerrariCar(val ferrariBrand: String = "Ferrari", val ferrariColor: String = "Red") : Car(ferrariBrand, ferrariColor) {

}

data class Engine(val brand: String, val power: Int)

class Box<T>(var item: T) {}
