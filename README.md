# person-manager

Ez a projekt egy személynyilvántartó rendszer, amely személyek, címeik és elérhetőségeik karbantartására szolgál. A rendszer Spring Boot alapon készült, Java 17 és MS SQL Server 2019 használatával.

## Feladat leírás

A rendszer 3 entitást kezel:

- `Person` (személyek)
- `Address` (címek)
- `Contact` (elérhetőségek)

Egy személynek:

- Legfeljebb 2 címe lehet: egy `PERMANENT` és egy `TEMPORARY`
- Egy címhez több elérhetőség tartozhat (pl. email, telefon)

## Fő funkcionalitások

- Adatok lekérdezése
  - `GET /api/persons`
  - `GET /api/persons/{id}`
- Adatok rögzítése
  - `POST /api/persons`
- Adatok módosítása
  - `PUT /api/persons/{id}`
- Adatok törlése
  - `DELETE /api/persons/{id}`
- Címek hozzáadása és törlése
  - `POST /api/persons/{id}/addresses`
  - `DELETE /api/persons/{personId}/addresses/{addressId}`

## Technológiák

- Java 17
- Spring Boot
- Spring Data JPA
- Hibernate
- Lombok
- Swagger / OpenAPI
- MS SQL Server 2019

## Adatbázis inicializálás

A projekt tartalmazza a következő SQL fájlokat:

- `schema.sql`: táblák és kapcsolatok definiálása
- `data.sql`: minta adatok betöltése

Ezek automatikusan lefutnak induláskor, ha a `resources/` könyvtárban vannak.

## Konfiguráció

Az alkalmazás az `application.properties` fájl alapján csatlakozik az adatbázishoz.

## Swagger UI

Az alkalmazás elindítása után a Swagger UI elérhető a következő címen:

```
http://localhost:8080/swagger-ui/index.html
```

## Tesztelés

A `PersonService` osztályhoz JUnit tesztek készültek, amelyek lefedik:

- Új személy sikeres létrehozását
- Hiba dobása duplikált címtípus esetén

## Hibakezelés

A projekt tartalmaz globális hibakezelőt (`GlobalExceptionHandler`), amely kezeli a 400-as, 404-es és 500-as hibákat egységes API formátumban.

## Fejlesztői információk

A projekt bővíthető új entitásokkal, szolgáltatásokkal és REST endpointokkal.

---

A projekt megfelel a kiírt feladat minden funkcionalitásának, és tartalmazza a dokumentációt, teszteket és SQL inicializáló fájlokat is.

