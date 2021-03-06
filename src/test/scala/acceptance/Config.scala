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