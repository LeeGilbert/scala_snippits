package x.util
import java.awt.Color._
import java.awt.Rectangle
import java.awt.geom.Rectangle2D
import java.io._


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
}
