Feature: Create stub

  Background:
    Given The wiremock server is reset

  Scenario: Basic stubbing
    When I stub a endpoint with the following properties:
      | method | url         | status | response body | response header         |
      | GET    | /some/thing | 200    | Hello world!  | Content-Type:text/plain |
    Then The stub in the wiremock server is:
    """
     {
      "request": {
              "method": "GET",
              "url": "/some/thing"
      },
      "response": {
            "status": 200,
            "body": "Hello world!",
            "headers": {
                    "Content-Type": "text/plain"
            }
       }
     }"""

  Scenario: URL matching with urlPattern
    When I stub a endpoint with the following properties:
      | method | urlPattern             | status |
      | PUT    | /thing/matching/[0-9]+ | 200    |
    Then The stub in the wiremock server is:
    """
    {
    "request": {
        "method": "PUT",
        "urlPattern": "/thing/matching/[0-9]+"
      },
    "response": {
        "status": 200
      }
    }"""

  Scenario: URL matching with urlPath
    When I stub a endpoint with the following properties:
      | method | urlPath | status |
      | PUT    | /query  | 200    |
    Then The stub in the wiremock server is:
    """
    {
    "request": {
        "method": "PUT",
        "urlPath": "/query"
        },
    "response": {
        "status": 200
      }
    }"""

  Scenario: Request header matchers
    When I stub a endpoint with the following properties:
      | method | url           | request header 1             | request header 2         | request header 3        | request header 4        | status |
      | POST   | /with/headers | X-Custom1 doesNotMatch text1 | X-Custom2 contains text2 | X-Custom3 equalTo text3 | X-Custom4 matches text4 | 200    |
    Then The stub in the wiremock server is:
    """
    {
    "request": {
        "method": "POST",
        "url": "/with/headers",
        "headers": {
            "X-Custom1": {
                "doesNotMatch": "text1"
            },
            "X-Custom2": {
                "contains": "text2"
            },
            "X-Custom3": {
                "equalTo": "text3"
            },
            "X-Custom4": {
                "matches": "text4"
            }
        }
    },
    "response": {
            "status": 200
    }
  }
    """


  Scenario: Query parameter matchers
    When I stub a endpoint with the following properties:
      | method | urlPath     | queryParameters 1          | queryParameters 2      | queryParameters 3     | queryParameters 4     | status |
      | GET    | /with/query | search1 doesNotMatch text1 | search2 contains text2 | search3 equalTo text3 | search4 matches text4 | 200    |
    Then The stub in the wiremock server is:
    """
    {
    "request": {
        "method": "GET",
        "urlPath": "/with/query",
        "queryParameters": {
            "search1": {
                "doesNotMatch": "text1"
            },
            "search2": {
                "contains": "text2"
            },
            "search3": {
                "equalTo": "text3"
            },
            "search4": {
                "matches": "text4"
            }
        }
    },
    "response": {
            "status": 200
    }
  }
    """


  Scenario: Cookie Matchers
    When I stub a endpoint with the following properties:
      | method | urlPath     | cookies 1                  | cookies 2              | cookies 3             | cookies 4             | status |
      | GET    | /with/query | search1 doesNotMatch text1 | search2 contains text2 | search3 equalTo text3 | search4 matches text4 | 200    |
    Then The stub in the wiremock server is:
    """
    {
    "request": {
        "method": "GET",
        "urlPath": "/with/query",
        "cookies": {
            "search1": {
                "doesNotMatch": "text1"
            },
            "search2": {
                "contains": "text2"
            },
            "search3": {
                "equalTo": "text3"
            },
            "search4": {
                "matches": "text4"
            }
        }
    },
    "response": {
            "status": 200
    }
  }
    """

  Scenario: BodyPatterns Matchers
    When I stub a endpoint with the following properties:
      | method | urlPath     | bodyPatterns 1                | bodyPatterns 2                | status |
      | GET    | /with/query | equalToXml <search-results/> | matchesXPath //search-results | 200    |
    Then The stub in the wiremock server is:
    """
    {
    "request": {
        "method": "GET",
        "urlPath": "/with/query",
        "bodyPatterns": [
            {"equalToXml" : "<search-results/>"},
            {"matchesXPath" : "//search-results"}
          ]
    },
    "response": {
            "status": 200
    }
  }
    """
