package bench.sjs

import scala.scalajs.js
import japgolly.scalajs.benchmark._, gui._

object Comparisons {

  type T = AnyRef
  def newly(): T = new AnyRef {}
  //private val Sizes = Seq(1, 4, 16, 64, 256, 1024, 4096, 16192, 65536)

  val suite = GuiSuite(
    Suite("Scala and JS collection comparisons")(
      Benchmark("scala.Array(with Builder)"){
        val b = Array.newBuilder[T]
        for (_ <- 0 until 4096) b += newly()
        b.result()
      },

      Benchmark("js.Array pre-allocated"){
        val ary = new js.Array[T](4096)
        for (_ <- 0 until 4096) ary.push(newly())
        ary
      }
    )
  )
}
