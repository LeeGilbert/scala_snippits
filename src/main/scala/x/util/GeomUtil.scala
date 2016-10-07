package x.util


object GeomUtil {

  def distance2D (p:(Double,Double), p2: (Double, Double)) = {
    val dx = p._1 - p2._1
    val dy = p._2 - p2._2
    math.sqrt(dx*dx + dy*dy)  // replace with lookup: if (xd*xd + yd*yd) < (Diameter*Diameter)
  }

  def distance3D (p:(Double,Double, Double), p2: (Double, Double, Double)) = {
    val dx = p._1 - p2._1
    val dy = p._2 - p2._2
    val dz = p._3 - p2._3
    math.sqrt(dx*dx + dy*dy + dz*dz)
  }

 // interpolating spline (a curve that goes through its control points) defined by four control points {\displaystyle \mathbf {P} _{0},\mathbf {P} _{1},\mathbf {P} _{2},\mathbf {P} _{3}} {\mathbf  {P}}_{0},{\mathbf  {P}}_{1},{\mathbf  {P}}_{2},{\mathbf  {P}}_{3},
 // with the curve drawn only from {\displaystyle \mathbf {P} _{1}} {\mathbf  {P}}_{1} to {\displaystyle \mathbf {P} _{2}} {\mathbf  {P}}_{2}.
 // Catmull-Rom spline interpolation function
 def doubleCR( t : Double, p0 : Double, p1: Double, p2: Double, p3: Double) = {
   0.5 * ((2 * p1) + (-p0 + p2) * t
     + (2 * p0 - 5 * p1 + 4 * p2 - p3) * (t * t) + (-p0 + 3 * p1 - 3
     * p2 + p3)
     * (t * t * t))
 }

}
