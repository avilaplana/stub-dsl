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

package unit.stub.dsl

import org.scalatest.{Matchers, WordSpec}
import play.api.libs.json._
import stub.dsl.Resource

class ResourceMarshallSpec extends WordSpec with Matchers {

  val resouce = Resource().
    get.
    url("/some/thing").
    headerEqualTo("Authorization", "Basic 01234567890").
    headerContains("Request-Id", "09876").
    bodyPatternsEqualToXml("<resource>aa</resource>").
    bodyPatternsMatchesXPath("<resource>").
    cookiesContains("Session-Id", "1234567890").
    queryParametersContains("page", "1").
    responseHeader("Request-Id", "09876").
    status(200).
    responseBody("this is an example")


  val jsonToCompared =
    """{
      |  "request": {
      |    "method": "GET",
      |    "url": "/some/thing",
      |    "cookies": {
      |      "Session-Id": {
      |        "contains": "1234567890"
      |      }
      |    },
      |    "bodyPatterns": [
      |      {
      |        "equalToXml": "<resource>aa</resource>"
      |      },
      |      {
      |        "matchesXPath": "<resource>"
      |      }
      |    ],
      |    "queryParameters": {
      |      "page": {
      |        "contains": "1"
      |      }
      |    },
      |    "headers": {
      |      "Authorization": {
      |        "equalTo": "Basic 01234567890"
      |      },
      |      "Request-Id": {
      |        "contains": "09876"
      |      }
      |    }
      |  },
      |  "response": {
      |    "headers": {
      |      "Request-Id": "09876"
      |    },
      |    "status": 200,
      |    "body": "this is an example"
      |  }
      |}""".stripMargin


  "a Resource" should {
    "be marshalled to json" in {
      resouce.json shouldBe Json.parse(jsonToCompared)
    }
  }

  "a json" should {
    "be unmarshalled to Resource" in {
      Json.parse(jsonToCompared).as[Resource] shouldBe resouce
    }
  }
}
