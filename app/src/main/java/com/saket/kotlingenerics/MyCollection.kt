package com.saket.kotlingenerics

/**
 * Custom collection class that holds list of objects.
 */
class MyCollection <T> {

    private val _list = mutableListOf<T>()

    fun addItem(item : T) {
        _list.add(item)
    }

    fun setList(items: List<T>) {
        _list.addAll(items)
    }

    fun getItem(index: Int) : T? {
        return if ((_list.size > 0) and (_list.size >= index)) {
            _list[index]
        } else {
            null
        }
    }

    fun getList(): List<T> = _list
}