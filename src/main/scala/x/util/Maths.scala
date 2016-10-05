package x.util

import java.util

/**
  *
  */
object Maths {

  /**
    * Estimates the <code>p</code>th percentile of the values in the <code>values</code> array, starting with the
    * element in (0-based) position <code>begin</code> in the array and including <code>length</code> values.
    * <p>
    * <ul>
    * <li>Returns <code>Double.NaN</code> if <code>length = 0</code></li>
    * <li>Returns (for any value of <code>p</code>) <code>values[begin]</code>
    *  if <code>length = 1 </code></li>
    * <li>Throws <code>IllegalArgumentException</code> if <code>values</code>
    *  is null , <code>begin</code> or <code>length</code> is invalid, or
    * <code>p</code> is not a valid quantile value (p must be greater than 0
    * and less than or equal to 100)</li>
    * </ul></p>
    * <p>
    *
    * @param values array of input values
    * @param p  the percentile to compute
    * @param begin  the first (0-based) element to include in the computation
    * @param length  the number of array elements to include
    * @return  the percentile value
    * @throws IllegalArgumentException if the parameters are not valid or the
    * input array is null
    */
  def  eval(values : Array[Double], begin : Int, length : Int,  p : Double) : Double = {
    if ((p > 100) || (p <= 0)) {
      throw new RuntimeException("out of bounds quantile value: {0}, must be in (0, 100], was: " + p)
    }
    if (length == 0) {
      return Double.NaN
    }
    if (length == 1) {
      return values(begin) // always return single value for n = 1
    }
    val n = length
    val pos = p * (n + 1) / 100 // non-excel way
    //val pos=(1+p*(n-1))/100 // excel way
    val  fpos = Math.floor(pos)
    val  intPos : Int = fpos.toInt
    val dif = pos - fpos
    val sorted = Array.ofDim[Double](length)
    System.arraycopy(values, begin, sorted, 0, length)
    util.Arrays.sort(sorted)

    if (pos < 1) {
      return sorted(0)
    }
    if (pos >= n) {
      return sorted(length - 1)
    }
    val lower = sorted(intPos - 1)
    val upper = sorted(intPos)
    return lower + dif * (upper - lower)
  }

  /**
    * Estimates the <code>p</code>th percentile of the values in the <code>values</code> array, starting with the
    * element in (0-based) position <code>begin</code> in the array and including <code>length</code> values.
    * <p>
    * <ul>
    * <li>Returns <code>Double.NaN</code> if <code>length = 0</code></li>
    * <li>Returns (for any value of <code>p</code>) <code>values[begin]</code>
    *  if <code>length = 1 </code></li>
    * <li>Throws <code>IllegalArgumentException</code> if <code>values</code>
    *  is null , <code>begin</code> or <code>length</code> is invalid, or
    * <code>p</code> is not a valid quantile value (p must be greater than 0
    * and less than or equal to 100)</li>
    * </ul></p>
    *
    * @param values array of input values
    * @param p  the percentile to compute
    * @param begin  the first (0-based) element to include in the computation
    * @param length  the number of array elements to include
    * @return  the percentile value
    * @throws IllegalArgumentException if the parameters are not valid or the
    * input array is null
    */
  def  eval(values : Array[Int], begin : Int, length : Int,  p : Double) : Double = {
    if ((p > 100) || (p <= 0)) {
      throw new RuntimeException("out of bounds quantile value: {0}, must be in (0, 100], was: " + p)
    }
    if (length == 0) {
      return Double.NaN
    }
    if (length == 1) {
      return values(begin) // always return single value for n = 1
    }
    val n = length
    val pos = p * (n + 1) / 100 // non-excel way
    //val pos=(1+p*(n-1))/100 // excel way
    val  fpos = Math.floor(pos)
    val  intPos : Int = fpos.toInt
    val dif = pos - fpos
    val sorted = Array.ofDim[Int](length)
    System.arraycopy(values, begin, sorted, 0, length)
    util.Arrays.sort(sorted)

    if (pos < 1) {
      return sorted(0)
    }
    if (pos >= n) {
      return sorted(length - 1)
    }
    val lower = sorted(intPos - 1)
    val upper = sorted(intPos)
    return lower + dif * (upper - lower)
  }
}
