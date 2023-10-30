# ShamanWebApp

# Polish

## Diagram ERD bazy danych

![ERD](./shaman-erd.drawio.svg)

## Diagram Przypadków użycia

![Przypadki użycia](./przypadkow_uzycia.png)

## Architektura oprogramowania

### Aplikacja webowa

Jest to standartowa architektura dla angulara. W folderze [app](./src/app) znajdują się kolejno foldery:

1) [models](./src/app/models) zawierający modele obiektów odbieranych z API,
2) [services](./src/app/services) zawieracjący serwisy odpowiedzialne za rzeczy takie jak np. serializacja danych odbieranych z API,
3) [components](./src/app/components) zawierający komponenty, lub strony angulara.

## API

API znajduje się [tutaj](https://github.com/broniuu/ToikProject2022).

### Uruchamianie API

Aby uruchomić api użyj polecenia:

```
./mvnw spring-boot:run
```

## Aplikacja webowa

Wpisz `ng serve` aby uruchomić serwer. Przejdź do `http://localhost:4200/`. Aplikacja będzie się odświeżać przy każdej zmianie plików źródłowych.

# English

## API

API znajduje się [tutaj](https://github.com/broniuu/ToikProject2022).


### Run API

To run API you need to execute following command

```
./mvnw spring-boot:run
```

## Web App

Run `ng serve` for a dev server. Navigate to `http://localhost:4200/`. The application will automatically reload if you change any of the source files.
