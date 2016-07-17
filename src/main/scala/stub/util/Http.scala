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

package stub.util


import play.api.libs.json.{Json, Writes}

import scala.util.{Failure, Success, Try}
import scalaj.http.{Http => HttpClient}

trait Http {

  val EmptyBody = ""
  val statusCode = Map(
    "OK" -> "200",
    "NOT FOUND" -> "404",
    "BAD REQUEST" -> "400",
    "UNAUTHORIZED" -> "401",
    "CREATED" -> "201",
    "INTERNAL SERVER ERROR" -> "500"
  )

  def GET(url: String, header: (String, String)*): (String, String) = {
    Try(HttpClient(url).headers(header).asString) match {
      case Success(resp) => println("response >>>> " + resp);(resp.code.toString, resp.body)
      case Failure(ex) => println(s"Problem getting URL $url: ${ex.getMessage}"); throw ex
    }
  }

  def POST[T](url: String, bodyJson: T)(implicit writes: Writes[T]): (String, String) = {
    println(Json.toJson(bodyJson).toString())
    Try(HttpClient(url).postData(Json.toJson(bodyJson).toString()).header("content-type", "application/json").asString) match {
      case Success(resp) => (resp.code.toString, resp.body)
      case Failure(ex) => println(s"Problem post URL $url: ${ex.getMessage}"); throw ex
    }
  }

  def POST(url: String, bodyJson: String = EmptyBody): (String, String) = {
    Try(HttpClient(url).postData(bodyJson).header("content-type", "application/json").asString) match {
      case Success(resp) => (resp.code.toString, resp.body)
      case Failure(ex) => println(s"Problem post URL $url: ${ex.getMessage}"); throw ex
    }
  }
}

object Http extends Http
