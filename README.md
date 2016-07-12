# Exercise - Excution System

Technologies:
```
1. JDK 1.8
2. Scala 2.10.2
3. Sbt 0.13.8
4. Scalatest
5. Cucumber
```

To solve the problem I have used BDD/TDD. I consider that it is very important for a developer to write acceptance
tests firsts because make the developer understand the business value.

I like the idea of writing unit tests for small pieces of functionality that cannot be tested from a feature
test.

I like the balance between acceptance tests and unit tests but it is very important avoid the duplicity.

The class **ExchangeSystem** is responsible for:
```
1. Provide open orders
2. Provide executed orders
3. Provide open interest for a given RIC and direction
4. Provide the average execution price for a given RIC
5. Provide executed quantity for a given RIC and user
```

Because this is an exercise, I have used a stateful repository **OrderRepository** that replace the idea
of sql/nosql repository or a gateway in the case of microservice architecture.

As data structure i have used **Buffer[Order]** to append/remove orders.

I have followed the principles:
```
1. Interface oriented programming
2. Immutable principle (except the repository)
```

The case class that represents the orders is
```
case class Order(direction: Direction,
                 ric: Ric,
                 quantity: Long,
                 price: BigDecimal,
                 user: String,
                 id: String = UUID.randomUUID().toString,
                 status: Option[Status] = None
                )
**Feature tests** are in:
 ```
 test/resources/features
 ```

There are two feature files:

1. matching.feature: To satisfy the matching rules

2. orders.feature: To satisfy the example provided

To run the test:
```sbt cucumber```


# stub-dsl
