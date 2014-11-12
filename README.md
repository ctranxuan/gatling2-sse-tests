## Introduction 

This project is a sample project to test the SSE (Server Sent Event) feature of the [open-source stress tool Gatling2](http://gatling.io/).
This project depends on the implementation of the SSE feature ([https://github.com/gatling/gatling/issues/1734]()) which is not
yet integrated into the master branch of Gatling2 (as of 2014/11/12) but can be found on [https://github.com/ctranxuan/gatling.git]()

## Steps

### Clone the repository [https://github.com/ctranxuan/gatling.git]()

```
git clone https://github.com/ctranxuan/gatling.git
cd gatling
sbt aether-install
```

### Clone this repository [https://github.com/ctranxuan/gatling2-sse-tests.git]()

``` 
git clone https://github.com/ctranxuan/gatling2-sse-tests.git
cd gatling2-sse-tests
mvn clean install
cd ./src/test/javascript
node sse_server.js
```

Run one of the simulations under `src/test/scala/org.tests.gatling.sse`