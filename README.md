# Milestone 4

**Il progetto prevede la realizzazione di una backoffice per la piattaforma di gestione delle richieste di supporto al team di assistenza tecnica di un prodotto.**

- Sviluppiamo un’applicazione Spring che permetta all’utente admin di gestire e ricercare i ticket di supporto assegnandoli agli operatori. Sui ticket sono possibili le seguenti operazioni:
  - creazione, visualizzazione, modifica, eliminazione
  - aggiornamento dello stato (da fare, in corso, completato)
  - aggiunta di una nota

- Un ticket deve essere obbligatoriamente assegnato ad un operatore disponibile in fase di creazione. Un operatore è disponibile quando non ha il flag di stato personale “non disponibile” attivo.

- Le entità categoria e operatore sono già caricate a DB (non è necessario sviluppare CRUD dedicate a queste risorse).

## Requisiti

Tipologie di utenti:

- Admin:
  - è già presente a database con sua email e password può creare, visualizzare e modificare ticket

- Operatore:
  - sono utenti già presenti in database, ognuno con propria mail e psw
  - può visualizzare la lista dei ticket a lui assegnati
  - può visualizzare il dettaglio di un ticket a lui assegnato
  - può aggiornare lo stato di un ticket a lui assegnato
  - puoi aggiungere una nota a un ticket a lui assegnato
  - può modificare i propri dati dalla sua pagina tra cui lo stato personale in “non attivo” solo se non ha nemmeno un ticket in stato “da fare” o “in corso”

- Dashboard admin:
  - visualizzazione tickets in formato tabellare
  - ricerca tickets per stringa di testo sul titolo
  - visualizzazione dettaglio ticket
  - aggiunta di note a un ticket

- Pagina Ticket:
  - mostra i dettagli del ticket
  - mostra lo stato
  - mostra l’operatore a cui è assegnato

- Pagina Operatore:
  - mostra dettagli operatore
  - mostra la lista di ticket assegnati

- Note ai ticket:
  - Nella pagina di dettaglio di un ticket c’è una sezione per poter visualizzare l’elenco note e lasciare una nuova nota. Per ogni nota visualizzare:
    - autore
    - data creazione
    - campo di testo

- Servizi API:
  - Esponi le API per la visualizzazione della risorsa Ticket:
    - visualizzare l’elenco dei ticket
    - filtrare l’elenco dei ticket per categoria
    - filtrare l’elenco dei ticket per stato

- Note Aggiuntive:
  - Validazione:
    - Ricorda di utilizzare la validazione (soprattutto lato server) dei campi per garantire l’inserimento corretto dei dati, in particolare i dati obbligatori.

- Libreria UI:
  - Puoi utilizzare Bootstrap o altra libreria UI per realizzare l’interfaccia del back-office, come ad esempio Tailwind.

## Bonus (opzionale)

- Altre CRUD di entità:
  - L’utente admin può anche creare nuovi operatori e gestire le categorie dei ticket.
  - Durante la creazione di un operatore, tra i campi è prevista la password per la login.

- Eccezioni:
  - Gestire il comportamento della nostra applicazione gestendo anche le eccezioni lanciate!

- Pagine di errore/not found:
  - Gestire il comportamento delle pagine di errore di base per gli errori emessi dalla nostra applicazione in pagina.
