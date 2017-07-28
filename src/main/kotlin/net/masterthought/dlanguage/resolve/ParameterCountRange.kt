package net.masterthought.dlanguage.resolve

/**
 * Created by francis on 7/22/2017.
 */
class ParameterCountRange(val min: Int, val max: Int) {
    fun matches(num: Int): Boolean {
        return num in min..max
    }
}
