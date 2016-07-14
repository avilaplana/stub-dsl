package acceptance

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.core.WireMockConfiguration._
import cucumber.api.scala.{EN, ScalaDsl}
import org.scalatest.Matchers

trait Environment extends ScalaDsl with EN with Matchers{

  val port = 8080
  val url = s"http://localhost:$port"
  val wm: WireMockServer = new WireMockServer(options().port(port))


  Before { s =>
    wm.start()
  }

  After { s =>
    wm.stop()
  }

}