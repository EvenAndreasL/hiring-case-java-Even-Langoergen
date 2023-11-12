<script lang="ts">

declare global {
  var PATGENBACKEND_EXTERNAL_URL: string;
  var ZIPCODESERVICE_EXTERNAL_URL: string;
  var CODESPACES: string;
}

export default {
  name: "App",
  methods: {
    // Sjekker at backendtjenestene kjører og kan kobles til fra frontend
    async checkBackend(
      port: string,
      outputId: string,
      serviceName: string
    ): Promise<void> {
      const outputDiv = document.getElementById(outputId)!;
      outputDiv.textContent = `${serviceName}: checking...`;
      
      // Sett URL som brukes mot backend. Dersom vi kjører i Codespaces, bruker vi en ekstern URL.
      let url = "http://localhost:" + port;
      if (window.CODESPACES == "true") {
        if (typeof window.PATGENBACKEND_EXTERNAL_URL !== "undefined" && serviceName == "patgen-backend") {
          url = `${window.PATGENBACKEND_EXTERNAL_URL}`;
        }
        if (typeof window.ZIPCODESERVICE_EXTERNAL_URL !== "undefined" && serviceName == "zipcode-service") {
          url = `${window.ZIPCODESERVICE_EXTERNAL_URL}`;
        }
      }

      try {
        // Timeout etter 5 sekunder betyr gjerne at backend ikke kjører
        const timeoutPromise = new Promise<void>((_, reject) => {
          const id = setTimeout(() => {
            clearTimeout(id);
            reject(
              `${serviceName}: Timeout! Kjører du ${serviceName}? Prøv å last inn siden på nytt hvis du har nylig startet den.`
            );
          }, 5000);
        });

        const response = (await Promise.race([
          fetch(url + "/health-check"),
          timeoutPromise,
        ])) as Response;

        if (!response.ok) {
          throw new Error(
            `${serviceName}: HTTP feil! Status: ${response.status} - Kjører du ${serviceName}?`
          );
        }

        const jsonResponse: {
        service?: string;
        "health-patgen-backend"?: string;
        "health-zipcode-backend"?: string;
        } = await response.json();

        // Sjekker at backend svarer med riktig output
        if (jsonResponse.service === "up") {
          outputDiv.textContent = `${serviceName}: OK!`;
        } else {
          throw new Error(`${serviceName}: Feil output fra ${serviceName}`);
        }
      } catch (error) {
        // Håndterer og viser feilmelding
        const err = error as string;
        if (err.toString().startsWith("TypeError") && window.CODESPACES == "true") {
          outputDiv.textContent = `CORS feil! Sjekk 'Ports' fanen i Codespaces og sjekk at backendtjenestene er satt til public.`;
        } else {
          outputDiv.textContent = err;
        }
      }
    }
  }
};
</script>

<template>
  <main className="flex min-h-screen w-full flex-col items-center justify-center content-center p-8">
    <div className="max-w-5xl w-full flex flex-col items-center text-center bg-white bg-opacity-80 p-8 rounded-3xl">
      <h1 className="mb-8 text-4xl font-bold">
        Welcome to the frontend!
      </h1>
      <p className="mb-4">
        Her kan du løse frontend oppgavene. Bruk gjerne en frontend
        bibliotek du er kjent med. :)
      </p>
      <p className="mb-8 pb-8 border-b-slate-300 border-b">
        Trykk på begge knappene under for å sikre at frontend'en har
        tilgang til backend tjenestene.
      </p>
      <div className="flex flex-col gap-4 w-1/2 min-w-fit">
        <button className="btn-standard" @click="checkBackend('8081', 'patgen-results', 'patgen-backend')">
          Kjør patgen-backend test
        </button>
        <div id="patgen-results" className="">
          ...
        </div>

        <button className="btn-standard" @click="checkBackend('8082', 'zipcode-results', 'zipcode-service')">
          Kjør zipcode-service test
        </button>
        <div id="zipcode-results" className="">
          ...
        </div>
      </div>
    </div>
  </main>
</template>

<style scoped></style>
