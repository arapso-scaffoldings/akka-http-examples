import akka.actor.ActorSystem
import akka.event.{Logging, LoggingAdapter}
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import com.typesafe.config.ConfigFactory

trait ImagesRouter {

  protected final val ImageBase = "image"

  def logger: LoggingAdapter

  def imagesPath: String

  final def routes = {
    (get & path(ImageBase / Segment ~ Slash.?)
      & parameter('preview.as[Boolean].?(false))) {
      (imageId: String, preview: Boolean) => {
        getFromFile(s"$imagesPath/$imageId")
      }
    }
  }
}

object ImagesApp extends App with ImagesRouter {
  implicit val system = ActorSystem()
  implicit val executor = system.dispatcher
  implicit val materializer = ActorMaterializer()

  val config = ConfigFactory.load()
  val logger: LoggingAdapter = Logging(system, getClass)

  val iface = config.getString("http.interface")
  val port = config.getInt("http.port")

  val imagesPath = config.getString("app.images.path")

  Http().bindAndHandle(routes, iface, port)

}