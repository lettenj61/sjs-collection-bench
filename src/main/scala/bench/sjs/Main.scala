package bench.sjs

import scala.scalajs.js
import org.scalajs.dom.document
import japgolly.scalajs.benchmark.gui.BenchmarkGUI

object Main extends js.JSApp {
  def main() = {
    val body = document.getElementById("body")
    BenchmarkGUI.renderSuite(body)(Comparisons.suite)
  }
}
