[ ![Codeship Status for avilaplana/stub-dsl](https://codeship.com/projects/83cbc8a0-c6b8-0132-d589-5a19aff8c0c6/status?branch=master)](https://codeship.com/projects/74770)

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
