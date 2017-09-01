# catalog

## Building for production:

    ./gradlew -Pprod clean bootRepackage

## Run:
	(JRE 1.8)
    java -jar build/libs/<appName>.war --redis.host-name=<path> --redis.port=<port>

	
Then navigate to [http://localhost:8080] in your browser.

## Comments:

	Изначально хотел применить Redis как L2 cache для hibernate. Но нормально так и не заработало. 
	Постоянно возникали исключения из-за несовместимости версий и не хватило времени все отладить.
	Поэтому имплементировал кеширование с Redis на уровне сервисов.

