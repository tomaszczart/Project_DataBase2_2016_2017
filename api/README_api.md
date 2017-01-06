
# Rejestracja.

Rejestracja odbywa się po wysłaniu zapytania zapytania HTTP typu POST do API pod adres "127.0.0.1:3000/post/register".
Do rejestracji wymagane są następujące elementy w sekcji body zapytania HTTP:
* <b>employee</b> *true (employee) albo false (customer)* -> <b>Pozosotałe elementy nagłówka zależą od tego pola</b>

<b>Jeżeli *true* to rejestrujemy Employee:</b>
* username
* name
* address
* password 

<b>Jeżeli *false* to rejestrujemy Customera:</b>
* username
* name
* address
* email
* phone
* password 

<l>Na razie hasło jest wysyłane w postaci czystego tekstu który jest haszownay przez api, później bd trzeba to przerobić na haszowanie po stronie aplikacji androidowej [BCrypt].</l>

# Logowanie.

Logowanie odbywa się po wysłaniu zapytania HTTP typu POST do API pod adres "127.0.0.1:3000/post/login".
Do logowania wymagane są następujące elementy w sekcji body zapytania HTTP:
* <b>employee</b>  <l>true (employee) albo false (customer)</l>
* username
* password //wysyłane jako czysty tekst, porównanie jest robione przez api nie wiem na ile jest to bezpieczne rozwiązanie bo nie wygląda to zbyt ciekawie jak wysyłamy jawnie hasło po http ;/ -- trzeba bd to zmienić

# Autoryzacja.

Autoryzacje odbywa się za pomocą JSON Web Token. Po zalogowaniu aplikacja androidowa otrzymuje odpowiedź od API z podpisanym loginem na pomocą tokenu.
Token ten musi być wysyłany z kazdym zapytaniem w celu weryfikacji uprawnień użytkownika. Token znajduje się w customowym nagłówku zapytania HTTP, "x-auth".

# Pobieranie danych z API.

* '/get/employees' - lista pracowników
* '/get/items' = lista itemów
* '/get/models' - lista mdoeli
* '/get/transactions' - lista transakcji (autoryzacja tylko dla usera z uprawieniami pracownika)
* '/post/register'
* '/post/login'

# Dodawanie rezerwacji.

Data musi być w formacie 'YYYY-mm-dd HH:ii:ss';

