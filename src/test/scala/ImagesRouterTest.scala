import java.net.URL

import akka.event.{LoggingAdapter, NoLogging}
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.model.{ContentType, MediaTypes}
import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalatest.{FlatSpec, Matchers}

import scala.io.Source

class ImagesRouterTest extends FlatSpec with Matchers with ScalatestRouteTest with ImagesRouter {
  override def logger: LoggingAdapter = NoLogging

  val `image/gif` = ContentType(MediaTypes.`image/gif`)

  "Image Service" should "respond with image" in {
    val image1Name = "black_pixel.gif"
    Get(s"/$ImageBase/$image1Name") ~> routes ~> check {
      status shouldBe OK
      contentType shouldBe `image/gif`
      responseAs[Array[Byte]] shouldBe getImageAsByteArray(image1Name)
    }

    val image2Name = "white_pixel.gif"
    Get(s"/$ImageBase/$image2Name") ~> routes ~> check {
      status shouldBe OK
      contentType shouldBe `image/gif`
      responseAs[Array[Byte]] shouldBe getImageAsByteArray(image2Name)
    }
  }

  it should "response with string when preview param is present and false" in {
    val imageName = "white_pixel.gif"
    Get(s"/$ImageBase/$imageName?preview=false") ~> routes ~> check {
      status shouldBe OK
      contentType shouldBe `image/gif`
      responseAs[Array[Byte]] shouldBe getImageAsByteArray(imageName)
    }
  }

  it should "response with string when preview param is present and true" in {
    val imageName = "white_pixel.gif"
    Get(s"/$ImageBase/$imageName?preview=true") ~> routes ~> check {
      status shouldBe OK
      contentType shouldBe `image/gif`
      responseAs[Array[Byte]] shouldBe getImageAsByteArray(imageName)
    }
  }

  val imagesPath = getClass.getResource("/images").getPath

  private def getImageAsByteArray(imageFileName: String) = {
    val resource: URL = getClass.getResource(s"/images/$imageFileName")
    Source.fromURL(resource, "ISO-8859-1").map(_.toByte).toArray
  }

}

