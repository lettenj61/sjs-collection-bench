package bench.sjs

import scala.collection._
import immutable.{ Queue, Stack }

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
      "sets" -> sets,
      "maps" -> maps,
      "javaArrays" -> javaArrays,
      "jsArrays" -> jsArrays
    )
  }

  val Sizes = Seq(0, 1, 2, 4, 16, 64, 256, 1024, 4096, 16192)
  def elem() = js.Dynamic.literal().asInstanceOf[js.Object]

  def samples[T](f: Int => T): js.Dictionary[Any] = {
    val d = js.Dictionary.empty[Any]
    for (i <- Sizes) d.update(i.toString, f(i))
    d
  }
  def iter(size: Int) = Iterator.fill[js.Object](size)(elem())

  def lists = samples(i => iter(i).toList)
  def vectors = samples(i => iter(i).toVector)
  def unforcedStreams = samples(i => iter(i).toStream)
  def forcedStreams = samples({ i =>
    val s = iter(i).toStream
    s foreach (_ => ())
    s
  })
  def sets = samples(i => iter(i).toSet)
  def maps = samples({ i =>
    Iterator.fill(i)(elem(), elem()).toMap
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
