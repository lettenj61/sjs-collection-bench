import scala.collection._
import scala.scalajs.js
import js.annotation._

@JSExport
object Memsize {
  def elem() = js.Dynamic.literal().asInstanceOf[js.Object]

  def samples[T](f: Int => T): js.Dictionary[Any] = {
    val d = js.Dictionary.empty[Any]
    for (i <- Sizes) d.update(i.toString, f(i))
    d
  }

  val Sizes = Seq(0, 1, 2, 4, 16, 64, 256, 1024, 4096, 16192)

  @JSExport
  val lists = samples(i => List.fill(i)(elem()))
  @JSExport
  val vectors = samples(i => Vector.fill(i)(elem()))

  @JSExport
  val javaArrays = samples({ i =>
    val b = Array.newBuilder[js.Object]
    for (_ <- 0 until i) b += elem()
    b.result()
  })

  @JSExport
  val jsArrays = samples({ i =>
    val xs = new js.Array[js.Object](i)
    for (_ <- 0 until i) xs.push(elem())
    xs
  })
}
