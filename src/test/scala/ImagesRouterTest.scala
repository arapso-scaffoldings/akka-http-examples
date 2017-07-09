import akka.event.{LoggingAdapter, NoLogging}
import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalatest.{FlatSpec, Matchers}
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.model.ContentTypes._

class ImagesRouterTest extends FlatSpec with Matchers with ScalatestRouteTest with ImagesRouter {
  override def logger: LoggingAdapter = NoLogging

  "Image Service" should "respond with image" in {
    Get(s"/images/myimage") ~> routes ~> check {
      status shouldBe OK
      contentType shouldBe `text/plain(UTF-8)`
      responseAs[String] shouldBe "Original image view myimage false"
    }
  }

  it should "response with string when preview param is present and false" in {
    Get(s"/images/myimage?preview=false") ~> routes ~> check {
      status shouldBe OK
      contentType shouldBe `text/plain(UTF-8)`
      responseAs[String] shouldBe "Original image view myimage false"
    }
  }

  it should "response with string when preview param is present and true" in {
    Get(s"/images/myimage?preview=true") ~> routes ~> check {
      status shouldBe OK
      contentType shouldBe `text/plain(UTF-8)`
      responseAs[String] shouldBe "Original image view myimage true"
    }
  }

}

