# catalog

## Building for production:

    ./gradlew -Pprod clean bootRepackage

## Run:
	(JRE 1.8)
    java -jar build/libs/<appName>.war --redis.host-name=<path> --redis.port=<port>

	
Then navigate to [http://localhost:8080] in your browser.

