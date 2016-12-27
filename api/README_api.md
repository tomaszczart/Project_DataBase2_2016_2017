
Rejestracja.

Rejestracja odbywa się po wysłaniu zapytania zapytania HTTP typu POST do API pod adres "127.0.0.1:3000/post/register".
Do rejestracji wymagane są następujące elementy w sekcji body zapytania HTTP:
* username //jeszcze nie zrobione
* name
* address
* email
* phone
* password //na razie hasło jest wysyłane w postaci czystego tekstu który jest haszownay przez api, później bd trzeba to przerobić na haszowanie po stronie aplikacji androidowej [BCrypt].

Username (Jeszcze nie zrobione), email i phone są polami które muszą być unikalne.

Logowanie.

Logowanie odbywa się po wysłaniu zapytania HTTP typu POST do API pod adres "127.0.0.1:3000/post/login".
Do logowania wymagane są następujące elementy w sekcji body zapytania HTTP:
* username //jeszcze nie zrobione
* email // teraz logowanie się o niego opiera, będzie zastąpiony poprzez username!!!
* password //wysyłane jako czysty tekst, porównanie jest robione przez api nie wiem na ile jest to bezpieczne rozwiązanie bo nie wygląda to zbyt ciekawie jak wysyłamy jawnie hasło po http ;/ -- trzeba bd to zmienić

Autoryzacja.

Autoryzacje odbywa się za pomocą JSON Web Token. Po zalogowaniu aplikacja androidowa otrzymuje odpowiedź od API z podpisanym loginem na pomocą tokenu.
Token ten musi być wysyłany z kazdym zapytaniem w celu weryfikacji uprawnień użytkownika. Token znajduje się w customowym nagłówku zapytania HTTP, "x-auth".

Pobieranie danych z API.

'/get/employees' - lista pracowników
'/get/items' = lista itemów
'/get/models' - lista mdoeli
'/get/transactions' - lista transakcji (autoryzacja tylko dla usera z uprawieniami pracownika)
'/post/register'
'/post/login'

Dodawanie rezerwacji.

Data musi być w formacie 'YYYY-mm-dd HH:ii:ss';

//****************** WARNING ************************************//
Aby uruchomić API trzeba zaktualizować dane w bazie danych. Wszystko co trzeba zmienić podałem na trello...
