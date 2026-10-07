# CF example app: ping-pong matching server

This is an app to match ping-pong players with each other. It's currently an
API only, so you have to use `curl` to interact with it.

It has an [acceptance test suite][acceptance-test] you might like to look at.

It is a Spring Boot 4 application and requires JDK 17 or later.

## Running on Cloud Foundry

Log in and target your org and space.

```bash
cf login -a API-ENDPOINT
cf target -o myorg -s myspace
```

Build the app.

```bash
./mvnw package
```

Push the app.

```bash
cf push
```

Without a bound database service, the app uses an in-memory H2 database, so
data is lost when the app restarts.

To use a persistent database, create a MySQL or PostgreSQL service instance
from `cf marketplace`, bind it to the app, and restage:

```bash
cf create-service SERVICE PLAN pong-db
cf bind-service springpong pong-db
cf restage springpong
```

For Spring Boot 3 and 4 apps, the [Java buildpack][java-buildpack]
automatically adds the [java-cfenv][java-cfenv] library. java-cfenv sets the
`spring.datasource.*` properties from the bound database service and activates
the `cloud` Spring profile. The app does not need any Cloud Foundry specific
code or configuration for this.

Export the test host, using the route shown by `cf push`.

```bash
export HOST=https://springpong.example.com
```

Now follow the [interaction instructions][interaction].

## Running locally

Start the application with an in-memory H2 database:

```bash
./mvnw spring-boot:run
```

Export the test host

```bash
export HOST=http://localhost:8080
```

Now follow the [interaction instructions][interaction].

## Interaction instructions

Start by clearing the database from any previous tests.
You should get a 200.

```bash
curl -v -X DELETE $HOST/all
```

Then request a match, providing both a request ID and player ID. Again, you
should get a 200.

```bash
curl -v -H "Content-Type: application/json" -X PUT $HOST/match_requests/firstrequest -d '{"player": "andrew"}'
```

Now pretend to be someone else requesting a match:

```bash
curl -v -H "Content-Type: application/json" -X PUT $HOST/match_requests/secondrequest -d '{"player": "navratilova"}'
```

Let's check on the status of our first match request:

```bash
curl -v -X GET $HOST/match_requests/firstrequest
```

The bottom of the output should show you the match_id. You'll need this in the
next step.

Now pretend that you've got back to your desk and need to enter the result:

```bash
curl -v -H "Content-Type: application/json" -X POST $HOST/results -d '
{
    "match_id":"thematchidyoureceived",
    "winner":"andrew",
    "loser":"navratilova"
}'
```

You should get a 201 Created response.

Future requests with different player IDs should not cause a match with someone
who has already played. The program is not yet useful enough to
allow pairs who've already played to play again.

[acceptance-test]:https://github.com/cloudfoundry-samples/pong_matcher_acceptance
[interaction]:#interaction-instructions
[java-buildpack]:https://github.com/cloudfoundry/java-buildpack
[java-cfenv]:https://github.com/pivotal-cf/java-cfenv
