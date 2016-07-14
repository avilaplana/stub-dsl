package acceptance

import cucumber.api.DataTable
import cucumber.api.scala.{EN, ScalaDsl}
import org.scalatest.Matchers
import play.api.libs.json.Json
import stub.dsl.Resource
import stub.util.Http

import scala.collection.JavaConverters._;


case class StubMappings(mappings: Seq[Resource])

object StubMappings {
  implicit val stubFmt = Json.format[StubMappings]
}

class Steps extends Environment {

  Given("""^The wiremock server is reset$""") { () =>
    Http.POST(s"$url/__admin/mappings/reset")
  }

  When("""^I stub a endpoint with the following properties:$""") { (stubProperties: DataTable) =>

    val prop = stubProperties.asMaps(classOf[String], classOf[String]).asScala.head.asScala
    val resource = prop.foldLeft(Resource()) {
      case (r, (k, v)) => build(k, v)(r)
    }

    val (status, body) = Http.POST(s"$url/__admin/mappings/new", resource)(Resource.resourceFmt)

    withClue(s"The resource: $resource has not been stub. Status: $status") {
      status shouldBe "201"
    }
  }

  Then("""^The stub in the wiremock server is:$""") { (resourceExpected: String) =>
    val expectation = Json.parse(resourceExpected).as[Resource]
    val (status, body) = Http.GET(s"$url/__admin/")
    withClue(s"The wiremock server is failing in the response getting all the mappings. Status: $status") {
      status shouldBe "200"
    }

    val mappingsStub = Json.parse(body).as[StubMappings]
    mappingsStub.mappings.head shouldBe expectation
  }


  private def build(key: String, value: String): Resource => Resource =
    res => key match {
      case "url" => res.url(value)
      case "urlPath" => res.urlPath(value)
      case "urlPattern" => res.urlPattern(value)
      case "method" => method(value)(res)
      case "status" => res.status(value.toInt)
      case "response body" => res.responseBody(value)
      case "response header" =>
        val h = value.split(":")
        res.responseHeader(h.head, h.last)
      case s if s.contains("request header") =>
        val h = value.split("\\s")
        val header = h(0)
        val condition = h(1)
        val v = h(2)
        reqHeader(header, v, condition)(res)
      case s if s.contains("queryParameters") =>
        val h = value.split("\\s")
        val header = h(0)
        val condition = h(1)
        val v = h(2)
        queryParameter(header, v, condition)(res)
      case s if s.contains("cookies") =>
        val h = value.split("\\s")
        val header = h(0)
        val condition = h(1)
        val v = h(2)
        cookies(header, v, condition)(res)
      case s if s.contains("bodyPatterns") =>
        val h = value.split("\\s")
        val condition = h(0)
        val v = h(1)
        bodyPatterns(v, condition)(res)
    }


  private def queryParameter(header: String, value: String, condition: String): Resource => Resource =
    r =>
      condition match {
        case "equalTo" => r.queryParametersEqualsTo(header, value)
        case "matches" => r.queryParametersMatching(header, value)
        case "doesNotMatch" => r.queryParametersNotMatching(header, value)
        case "contains" => r.queryParametersContains(header, value)
      }

  private def cookies(header: String, value: String, condition: String): Resource => Resource =
    r =>
      condition match {
        case "equalTo" => r.cookiesEqualsTo(header, value)
        case "matches" => r.cookiesMatching(header, value)
        case "doesNotMatch" => r.cookiesNotMatching(header, value)
        case "contains" => r.cookiesContains(header, value)
      }

  private def bodyPatterns(value: String, condition: String): Resource => Resource =
    r =>
      condition match {
        case "equalToXml" => r.bodyPatternsEqualToXml(value)
        case "matchesXPath" => r.bodyPatternsMatchesXPath(value)
      }


  private def reqHeader(header: String, value: String, condition: String): Resource => Resource =
    r => condition match {
      case "equalTo" => r.headerEqualTo(header, value)
      case "matches" => r.headerMatching(header, value)
      case "doesNotMatch" => r.headerNotMatching(header, value)
      case "contains" => r.headerContains(header, value)
    }

  private def method(value: String): Resource => Resource = {
    r => value match {
      case "GET" => r.get
      case "POST" => r.post
      case "PUT" => r.put
    }
  }

}
