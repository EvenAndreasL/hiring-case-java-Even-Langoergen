# Frontend

Team for utvikling av medisinske kvalitetsregistre ønsker også en nettløsning for å kjapt hente ut testpasienter i CSV-fil for gjenbruk i database og regneark. Denne oppgaven kan løses uavhengig av de andre oppgavene i [backend](backend.md) dokumentet.

Du må velge å enten videreutvikle `patgen-app/patgen-app-vue` eller `patgen-app/patgen-app-vue`, samt gjenbruke *backend-tjenestene* `patgen-backend` og `zipcode-service`.

## Komme i gang med patgen-app

Velg en av Vue.js eller React.

### Vue.js

```sh
cd patgen-app/patgen-app-vue
npm install
npm run dev
```

### React

```sh
cd patgen-app/patgen-app-react
npm install
npm run dev
```

Om du trenger eksterne pakker, kan du installere disse med `npm`. Begge prosjektene inneholder et standard layout som bruker [Tailwind CSS](https://tailwindcss.com/) for styling. Det er veldig lov å endre på utseende på sidene, eller bruke en ekstern UI framework dersom du ser det passer.

Landingsidene i React og Vue.js templatene har test-knapper som sjekker om *backend-tjenestene* er tilgjengelige. Du kan fjerne disse knappene når du er klar til å sette i gang med oppgavene.

Teamet vil **sette stor pris på** om du kommer med forslag til endringer i *backend-koden* slik at det blir lettere å utvikle *frontend* og at arkitekturen blir forbedret.

### Tester
For å kjøre tester: 
```sh
npm run test
```
Både React og Vue frontenden har tester som kan kjøres med `npm run test`.
Det er lagt inn en initiell test bare for å se at konfigurasjonen fungerer. Den sjekker hva som står i overskriften på hovedsiden, så hvis det endres må også den testen endres.
Der man ser det hensiktsmessig kan man skrive tester.

Det er lagt opp for å lage tester i React med Jest og i Vue med Vitest, men hvis man har lyst å bruke noe annet er det bare å bruke det.

For hver oppgave og/eller commit er det ønskelig at kommandoen `npm run test && npm run build` skal kunne kjøres.


### Codespaces

Programmet vil kjøre på port `8080`, og kan sees i nettleseren ved å følge lenke i miljøvariabelen `$PATGENFRONTEND_EXTERNAL_URL` eller å åpne `patgen-app` fra *PORTS* fanen.

Backendtjenestene startes ved å kjøre `cli-docker-compose-app-refresh` i terminalen.

Bruk følgende kommando for å finne endepunkter du kan prøve ut i nettleseren.

```sh
echo $PATGENBACKEND_EXTERNAL_URL/generate-patient # Returnerer en pasient.
echo $ZIPCODESERVICE_EXTERNAL_URL/zipcode-details?zipCode=1234 # Merk at dette endepunktet feiler ofte.
```

I både React/Vue, kan du bruke `window.PATGENBACKEND_EXTERNAL_URL` og `window.ZIPCODESERVICE_EXTERNAL_URL` for å hente ut ekstern URL til backend tjenestene i Codespaces. Pass på at de eksterne adressene er endret til *public* i *PORTS* fanen slik dette er beskrevet i [README.md](../README.md).

(Kjører du derimot alt lokalt, er det `http://localhost:8080`, `http://localhost:8081` og `http://localhost:8082` du vil forholde deg til.)

## Oppgave 1 - Generere testpasienter og vise liste

Lag en side hvor du kan angi hvor mange testpasienter du ønsker, og returnere liste over pasienter med tilleggsinformasjon som fornavn, etternavn og alder.

## Oppgave 2 - Eksport

Legg til eksportknapp for den genererte listen av testpasienter slik at du kan laste ned en CSV fil med header.

## Oppgave 3 - Legge til stedsnavn

Legg til knapp for å tilføye og generere stedsnavn for hver testpasient. For denne oppgaven skal du bruke `zipcode-service` som generer tilfeldige stedsnavn fra postnummer. Zipcode-tjenesten simulerer feil og ukjente postnummer relativt ofte. Får du ikke noe stedsnavn fra Zipcode-tjenesten viser du bare stedsnavn som "ukjent".

## Oppgave 4 - Eksport med stedsnavn

Oppdater eksport slik at stedsnavn tas med i CSV fil dersom dette er valgt.

## Oppgave 5 - Brukervennlighet for andre utviklere

Det er ønskelig at *testpasientgeneratoren* og tilhørende avhengigheter skal være enkelt å starte opp for andre utviklere, selv om de ikke er så kjent med Vue.js og React. I denne oppgaven skal du gjøre nødvendige tilpasninger i `docker-compose.yml` og eventuelt `.devcontainer/cli-shortcuts.sh` slik at både frontend og backend-tjenestene kan startes fra en kommando.

---

Når du er ferdig med oppgavene over, se instruksene i [README](../README.md) om leveringsmåte. Har du valgt å jobbe med *frontend* oppgavene er det ikke nødvendig å løse *[backend oppgavene](backend.md)*. Poenget er å gjøre en av delene. :)