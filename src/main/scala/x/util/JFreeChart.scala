package x.util
import java.awt.Color._
import java.awt.Rectangle
import java.awt.geom.Rectangle2D
import java.io._

import scala.collection.mutable
import scala.collection.mutable.{ArrayBuffer, ListBuffer}
import scala.math._

import org.jfree.chart.block.BlockBorder
import org.jfree.chart.{ChartUtilities, JFreeChart}
import org.jfree.graphics2d.svg.SVGGraphics2D

/**
  * Utilities.
  */
object JFreeChartX {

  /**
    * Saves JFreeChart as SVG 1.1 to output stream.
    * Requires:   "org.jfree" % "jfreechart" % "1.0.19", "org.jfree" % "jfreesvg" % "3.1"
    * @param os OutputStream
    * @param chart JFreeChart
    * @param width
    * @param height
    *
    */
  def saveChartAsSVG(os: OutputStream, chart: JFreeChart, width: Int, height: Int ): Unit = {
    val g2 = new SVGGraphics2D(width, height)
    g2.setRenderingHint(JFreeChart.KEY_SUPPRESS_SHADOW_GENERATION, true)
    val r = new Rectangle(0, 0, width, height)
    g2.setColor(WHITE)
    chart.draw(g2, r)
    chart.setTextAntiAlias(true)
    chart.setAntiAlias(true)
    if (chart.getLegend != null) {
      chart.getLegend.setFrame(BlockBorder.NONE)
    }
    chart.draw(g2, new Rectangle2D.Float(0, 0, width, height))
    val osw: OutputStreamWriter = new OutputStreamWriter(os, "UTF-8")
    var writer = new BufferedWriter(osw)
    writer.write("<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\" \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">\n")
    writer.write(g2.getSVGElement() + "\n")
    writer.flush
  }


  case class CandleDatum(val dt: Long, p98: Double, p75: Double, p50: Double, p25: Double, p2: Double, triMean: Double) {
    override def toString: String = s"p98=$p98 p75=$p75 p50=$p50 p25=$p25 p2=$p2 triMean=$triMean [2*p50+p75+p25)/4]"
  }
  type CandlePointList = List[CandleDatum]

  /**
    *
    * @param input Map[Long, List[Int]
    * @return CandlePointList
    */
  def calcCandleStats(input: Map[Long, List[Int]]) : CandlePointList = {
    val rtnData = ListBuffer.empty[CandleDatum]
    val sumStats = mutable.LinkedHashMap.empty[Long, ArrayBuffer[Int]]
    input.foreach(in => {
      val (dayDT, dv) = in
      val buf: ArrayBuffer[Int] = sumStats.getOrElse(dayDT, ArrayBuffer.empty[Int])
      sumStats += ( dayDT -> buf )
      buf ++= dv
    })
    sumStats.foreach(ss => {
      val dt = ss._1
      val dv = ss._2.toArray
      val vLen = dv.size
      val p98 = Maths.eval(dv, 0, vLen, 98)
      val p75 = Maths.eval(dv, 0, vLen, 75)
      val p50 = Maths.eval(dv, 0, vLen, 50)
      val p25 = Maths.eval(dv, 0, vLen, 25)
      val p2 =  Maths.eval(dv, 0, vLen, 2)
      rtnData += (CandleDatum(dt, p98=p98, p75=p75, p50=p50, p25=p25, p2=p2, triMean = (p50 * 2 + p75 + p25) / 4))
    })
    rtnData.toList
  }

}
