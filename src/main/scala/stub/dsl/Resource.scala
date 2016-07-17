/*
 * Copyright 2015 Alvaro Vilaplana Garcia
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package stub.dsl

import play.api.libs.json.{JsNumber, JsObject, JsSuccess, _}


case class Resource(private val request: Map[String, Any] = Map.empty[String, Any],
                    private val response: Map[String, Any] = Map.empty[String, Any]) {

}

object Resource {

  implicit val fmt = new Format[Map[String, Any]] {
    override def reads(json: JsValue): JsResult[Map[String, Any]] = {
      def a(j: JsValue): Map[String, Any] = j.as[Map[String, JsValue]].map { case (k, v) =>
        k -> (v match {
          case s: JsString => s.as[String]
          case i: JsNumber => i.as[Long]
          case l: JsArray => l.value.collect {
            case jso: JsObject => (jso.value.head._1, jso.value.head._2.as[String])
          }
          case other: JsValue => a(other)
        })
      }
      JsSuccess(a(json))
    }

    override def writes(o: Map[String, Any]): JsValue =
      JsObject(
        o.mapValues {
          case v: String => JsString(v)
          case v: Int => JsNumber(v)
          case v: Seq[(String, String)] =>
            val b = v.map(e => JsObject(Seq(e._1 -> JsString(e._2))))
            val c = JsArray(b)
            c

          case v: Map[String, String] => writes(v)
        }
      )
  }

  implicit val resourceFmt = Json.format[Resource]

  implicit class DSL(stub: Resource) {

    private def method(m: String) = stub.copy(request = stub.request + ("method" -> m))

    def get = method("GET")

    def post = method("POST")

    def put = method("PUT")

    def url(u: String) = stub.copy(request = stub.request + ("url" -> u))

    def urlPattern(pattern: String) = stub.copy(request = stub.request + ("urlPattern" -> pattern))

    def urlPath(path: String) = stub.copy(request = stub.request + ("urlPath" -> path))

    def status(st: Int) = stub.copy(response = stub.response + ("status" -> st))

    def responseBody(respBody: String) = stub.copy(response = stub.response + ("body" -> respBody))

    def responseHeader(header: String, value: String) = {
      val headers: Map[String, Any] = stub.response.get("headers") match {
        case Some(h: Map[String, Any]) => h + (header -> value)
        case None => Map(header -> value)
      }

      stub.copy(response = stub.response + ("headers" -> headers))
    }

    def queryParametersEqualsTo(header: String, value: String) = requestMatcher(header, value, "equalTo", "queryParameters")

    def queryParametersMatching(header: String, value: String) = requestMatcher(header, value, "matches", "queryParameters")

    def queryParametersNotMatching(header: String, value: String) = requestMatcher(header, value, "doesNotMatch", "queryParameters")

    def queryParametersContains(header: String, value: String) = requestMatcher(header, value, "contains", "queryParameters")

    def cookiesEqualsTo(header: String, value: String) = requestMatcher(header, value, "equalTo", "cookies")

    def cookiesMatching(header: String, value: String) = requestMatcher(header, value, "matches", "cookies")

    def cookiesNotMatching(header: String, value: String) = requestMatcher(header, value, "doesNotMatch", "cookies")

    def cookiesContains(header: String, value: String) = requestMatcher(header, value, "contains", "cookies")

    def bodyPatternsEqualToXml(value: String) = requestMatcher(value, "equalToXml", "bodyPatterns")

    def bodyPatternsMatchesXPath(value: String) = requestMatcher(value, "matchesXPath", "bodyPatterns")

    def headerEqualTo(header: String, value: String) = requestMatcher(header, value, "equalTo", "headers")

    def headerMatching(header: String, value: String) = requestMatcher(header, value, "matches", "headers")

    def headerNotMatching(header: String, value: String) = requestMatcher(header, value, "doesNotMatch", "headers")

    def headerContains(header: String, value: String) = requestMatcher(header, value, "contains", "headers")

    def json = Json.toJson(stub)

    def jsonAsString = json.toString()


    private def requestMatcher(header: String, value: String, condition: String, parent: String) = {
      val headers: Map[String, Any] = stub.request.get(parent) match {
        case Some(h: Map[String, Any]) => h + (header -> Map(condition -> value))
        case None => Map(header -> Map(condition -> value))
      }

      stub.copy(request = stub.request + (parent -> headers))

    }

    private def requestMatcher(value: String, condition: String, parent: String) = {
      val headers: Seq[(String, String)] = stub.request.get(parent) match {
        case Some(h: Seq[(String, String)]) => h :+ (condition -> value)
        case None => Seq(condition -> value)
      }

      stub.copy(request = stub.request + (parent -> headers))

    }
  }

}



