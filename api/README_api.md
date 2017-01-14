
# Token

Token po rozkodowaniu ma taką strukturę: 
*{
  "username": "Tofik123",
  "employee": false
}*

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

Po poprawnym zalogowaniu serwer wysyła ospowiedź HTTP o kodzie __200__ która w sekcji __body__ zawiera wygenerowany __token__, który będzie musiał być wysyłany przy każdym kolejnym zapytaniu w celu autoryzacji użytkownika.

# Autoryzacja.

Autoryzacje odbywa się za pomocą JSON Web Token. Po zalogowaniu aplikacja androidowa otrzymuje odpowiedź od API z podpisanym loginem na pomocą tokenu.
Token ten musi być wysyłany z kazdym zapytaniem w celu weryfikacji uprawnień użytkownika. Token znajduje się w customowym nagłówku zapytania HTTP, "x-auth".

# Dodawanie rezerwacji.

Data musi być w formacie 'YYYY-mm-dd HH:ii:ss';

# Pobranie listy modeli oraz ich obrazków

* '/get/models' - lista modeli bez obrazków. Pobierane są następujaće pola: model_id,name,description,price_per_hour,picture

* '/get/imgOfModel/model_id' - pobieranie obrazka o wskazanym __model_id__.

Aby pobrać obrazek dla wybranego modelu należy w adresie URL podać jego __model_id__.
*Przykładowo, pobranie obrazka dla modelu o __model_id__=__6__ adres URL będzie nastepujący: "__/get/imgOfModel/6__".*

# Pobieranie listy modeli z wybranej kategorii

* '/get/modelsByCategory/category_id' - pobieranie wszystkich modeli o wskazanym __category_id__.
Zapytanie zwraca nastepujace pola tablicy Models: *model_id, name, description, price_per_hour*.

*Przykład, dla __category_id__=__5__ adres URL będzie nastepujący: "__/get/modelsByCategory/5__".*

# Pobieranie listy itemów danego modelu

* '/get/itemsByModel/model_id' - pobieranie wszystkich itemów o wskazanym __model_id__.
Zapytanie zwraca nastepujace pola tablicy Items: *item_id, itemstatus, condition*.

*Przykład, dla __model_id__=__12__ adres URL będzie nastepujący: "__/get/itemsByModel/12__".*

# Pobieranie danych z API.

* '/get/employees' - lista pracowników
* '/get/items' = lista itemów
* '/get/transactions' - lista transakcji (autoryzacja tylko dla usera z uprawieniami pracownika)
