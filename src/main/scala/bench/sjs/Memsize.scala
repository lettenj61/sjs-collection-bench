package bench.sjs

import scala.collection._
import scala.scalajs.js
import js.annotation._

@JSExport
object Memsize {

  @JSExport
  def main(): Unit = {
    js.Dynamic.global.memsizeSnapshot = js.Dictionary(
      "lists" -> lists,
      "vectors" -> vectors,
      "unforcedStreams" -> unforcedStreams,
      "forcedStreams" -> forcedStreams,
      "javaArrays" -> javaArrays,
      "jsArrays" -> jsArrays
    )
  }

  def elem() = js.Dynamic.literal().asInstanceOf[js.Object]

  def samples[T](f: Int => T): js.Dictionary[Any] = {
    val d = js.Dictionary.empty[Any]
    for (i <- Sizes) d.update(i.toString, f(i))
    d
  }

  def Sizes = Seq(0, 1, 2, 4, 16, 64, 256, 1024, 4096, 16192)

  def lists = samples(i => List.fill(i)(elem()))
  def vectors = samples(i => Vector.fill(i)(elem()))
  def unforcedStreams = samples(i => Stream.fill(i)(elem()))
  def forcedStreams = samples({ i =>
    val s = Iterator.fill(i)(elem()).toStream
    s foreach (_ => ())
    s
  })

  def javaArrays = samples({ i =>
    val b = Array.newBuilder[js.Object]
    for (_ <- 0 until i) b += elem()
    b.result()
  })

  def jsArrays = samples({ i =>
    val xs = new js.Array[js.Object]()
    for (_ <- 0 until i) xs.push(elem())
    xs
  })
}
