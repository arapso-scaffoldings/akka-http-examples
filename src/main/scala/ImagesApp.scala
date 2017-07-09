import akka.actor.ActorSystem
import akka.event.{Logging, LoggingAdapter}
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import com.typesafe.config.ConfigFactory

trait ImagesRouter {
  def logger: LoggingAdapter

  final def routes = {
    (get & path("images" / Segment ~ Slash.?)
      & parameter('preview.as[Boolean].?(false))) {
      (imageId: String, preview: Boolean) => {
        complete(s"Original image view $imageId $preview")
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

  Http().bindAndHandle(routes, iface, port)

}