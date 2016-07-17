[ ![Codeship Status for avilaplana/stub-dsl](https://codeship.com/projects/83cbc8a0-c6b8-0132-d589-5a19aff8c0c6/status?branch=master)](https://codeship.com/projects/74770)

# stub-dsl

The way of defining stubs on demand in a wiremock server deployed in a external environment is using a [wiremock Rest API](http://wiremock.org/docs/stubbing/) where the body of the request is the definition of the stub. 
The purpose of this library is to provide a DSL in scala to define the body.


## How to configure your project


Add the following to your sbt `project/plugins.sbt` file:

```scala
addSbtPlugin("me.lessis" % "bintray-sbt" % "0.3.0")
```

_NOTE_ this plugin targets sbt 0.13.8+

You will need to add the following to your `project/build.properties` file if you have multiple versions of sbt installed

    sbt.version=0.13.8

Add the library dependency

```scala
libraryDependencies += "alvarovg" %% "stub-dsl_2.11" % "0.1.0"
```

_NOTE_ this libraries is tested against wiremock 2.1.7

## Usage


Here there are some examples that explains how easy is to use the DSL
 
Example 1
---------

```
Resource()
        .get
        .url("/some/thing")
        .status(200)
        .responseBody("Hello world!")
        .responseHeader("Content-Type","text/plain")
        .jsonAsString
```

```
{
   "request":{
      "method":"GET",
      "url":"/some/thing"
   },
   "response":{
      "status":200,
      "body":"Hello world!",
      "headers":{
         "Content-Type":"text/plain"
      }
   }
}
```

Example 2
---------

```
Resource()
        .put
        .urlPattern("/thing/matching/[0-9]+")
        .status(200)
        .jsonAsString
```
```
{
   "request":{
      "method":"PUT",
      "urlPattern":"/thing/matching/[0-9]+"
   },
   "response":{
      "status":200
   }
}
```

Example 3
---------

```
Resource()
        .put
        .urlPath("/query")
        .status(200)
```

```
{
   "request":{
      "method":"PUT",
      "urlPath":"/query"
   },
   "response":{
      "status":200
   }
}
```

Example 4
---------
```
Resource()
        .post
        .url("/with/headers")
        .headerNotMatching("X-Custom1", "text1")
        .headerContains("X-Custom2", "text2")
        .headerEqualTo("X-Custom3", "text3")
        .headerMatching("X-Custom4", "text4")
        .status(200)
        .jsonAsString

```
```
{
   "request":{
      "method":"POST",
      "url":"/with/headers",
      "headers":{
         "X-Custom1":{
            "doesNotMatch":"text1"
         },
         "X-Custom2":{
            "contains":"text2"
         },
         "X-Custom3":{
            "equalTo":"text3"
         },
         "X-Custom4":{
            "matches":"text4"
         }
      }
   },
   "response":{
      "status":200
   }
}
```

Example 5
---------

```
Resource()
        .get
        .urlPath("/with/query")
        .queryParametersNotMatching("search1", "text1")
        .queryParametersContains("search2", "text2")
        .queryParametersEqualsTo("search3", "text3")
        .queryParametersMatching("search4", "text4")
        .status(200)
        .jsonAsString
```

```
{
   "request":{
      "method":"GET",
      "urlPath":"/with/query",
      "queryParameters":{
         "search1":{
            "doesNotMatch":"text1"
         },
         "search2":{
            "contains":"text2"
         },
         "search3":{
            "equalTo":"text3"
         },
         "search4":{
            "matches":"text4"
         }
      }
   },
   "response":{
      "status":200
   }
}
```

Example 6
---------

```
Resource()
        .get
        .urlPath("/with/query")
        .cookiesNotMatching("search1", "text1")
        .cookiesContains("search2", "text2")
        .cookiesEqualsTo("search3", "text3")
        .cookiesMatching("search4", "text4")
        .status(200)
        .jsonAsString

```


```
{
   "request":{
      "method":"GET",
      "urlPath":"/with/query",
      "cookies":{
         "search1":{
            "doesNotMatch":"text1"
         },
         "search2":{
            "contains":"text2"
         },
         "search3":{
            "equalTo":"text3"
         },
         "search4":{
            "matches":"text4"
         }
      }
   },
   "response":{
      "status":200
   }
}
```



Example 7
---------

```
Resource()
        .get
        .urlPath("/with/query")
        .bodyPatternsEqualToXml("<search-results/>")
        .bodyPatternsMatchesXPath("//search-results")
        .status(200)
        .jsonAsString

```
```
{
   "request":{
      "method":"GET",
      "urlPath":"/with/query",
      "bodyPatterns":[
         {
            "equalToXml":"<search-results/>"
         },
         {
            "matchesXPath":"//search-results"
         }
      ]
   },
   "response":{
      "status":200
   }
}
````

Example 8
---------
```
Resource().
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
    .jsonAsString

```
```
{
   "request":{
      "method":"GET",
      "url":"/some/thing",
      "cookies":{
         "Session-Id":{
            "contains":"1234567890"
         }
      },
      "bodyPatterns":[
         {
            "equalToXml":"<resource>aa</resource>"
         },
         {
            "matchesXPath":"<resource>"
         }
      ],
      "queryParameters":{
         "page":{
            "contains":"1"
         }
      },
      "headers":{
         "Authorization":{
            "equalTo":"Basic 01234567890"
         },
         "Request-Id":{
            "contains":"09876"
         }
      }
   },
   "response":{
      "headers":{
         "Request-Id":"09876"
      },
      "status":200,
      "body":"this is an example"
   }
}
```
