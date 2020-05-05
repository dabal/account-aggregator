# Simple OpenApi Connector to AliorBank
Jest to aplikacja demonsturjąca w jaki sposób z wykorzystaniem standardu PolishApi połączyć się z bankiem. Do demonstracji wybrany został Alior Bank S.A. ponieważ udostępnia on mozliwość połączenia do testowego środowiska bez certyfikatu wystawione przez zaufane centrum autoryzacji. Aplikacja udostępnia wyłacznie api.

## Stos technologiczny 
Aplikacja zbudowana jest z wykorzystaniem
* Spring Boot
* Hibernate
* Spring Data
* Spring Security
* Spring Fox(swagger)
* RestTemplate
* MySql

## Funkcjonalnosci

Aplikacja umożliwia:
1. rejestracje nowego uzytkownika
2. logowanie
3. dodanie rachunków z Alior Banku / Kantoru Walutowego Alior Banku / T-Mobile Usługi Bankowe

Wszystkie endpointy poza /public/** wymagają autoryzacji tokenem. Token zwracany jest w odpowiedzi na logowanie.

Szczegóły api dostępne są w swaggerze.

## Dodawanie rachunków
Dodawanie rachunków odbywa się za pomocą standardu Polish Api. Standard ten implementuje protokół OAuth. Dodawanie rachunków odbywa się poprzez przesłonięcie wywołań PolishApi Api aplikacyjnym. W związku z tym dodanie rachunków (wyrażenie zgody) odbywa się w dwóch krokach:
1. wywyołaniu metody `add` - w jej odpowiedzi zwrócony zostanie link pod, który należy przekierować użytkownika w aplikacji konsumującej api w celu uwierzytelnienia się użytkownika w banku.
2. po pomyślnym uwierzytelnieniu bank przekierowuje użytkownika na adres aplikacji - przekierowanie to jest równoznaczne z dodaniem do bazy informacji o tokenie dostępowym, o raz numerach rachunków.

## Dostęp do api Alior Bank
W celu uzyskania dostępu do środowiska testowego Alior Banku nalezy postępować zgodnie z instrukcją - https://developer.aliorbank.pl/openapipl/sb/how-to

## Uruchomienie aplikacji 
W celu uruchomienia aplikacji należy stworzyć plik `/src/resources/application-dev.properties` i uzupełnić odpowiednimi wpisami (w szczególności danymi dostępowymi do Api). Przykładowy plik dostępny jest w `src/resources/sample-application-dev.properties`

## Swagger
Aktualna wersja swaggera dostępna jest po uruchomieniu aplikacji pod adresem http://localhost:8080/swagger-ui.html dla uproszczenia aktualna wersja znajduje się również w `/src/resources/swagger/swagger.json`.

## Uwagi
Aplikacja na chwilę obecną pobiera informacje tylko o rachunkach, nie pobiera informacji o historii operacji.