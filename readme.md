# Zadanie Verestro

### Skrypty:
* [docker-compose for postgres](./docker/docker-compose.yml)
* [przykładowe requesty http](./requests/example-requests.http)

### Wymagania:

1.  Aplikacja powinna umożliwiać rejestrację użytkownika. Należy wystawić metodę http, która przyjmie dane:
    1.  username,
    2.  password,
    3.  phoneNumber,
    4.  email,
    5.  preferred_notification_channel (SMS/EMAIL)Dane powinny zostać zapisane w wybranej bazie danych z uwzględnieniem walidacji i mechanizmów odpowiedniego przechowywania wrażliwych danych, tj password
2.  W aplikacji uwierzytelnienie powinno być zapewnione poprzez Basic Auth, gdzie username i password to dane użytkownika
3.  Aplikacja powinna umożliwiać utworzenie salda dla użytkownika poprzez wystawienie metody http
    1.  Saldo powinno mieć unikalny identyfikator w postaci 20 cyfr oraz kwota jaka znajduje się na tym saldzie
    2.  Użytkownik może posiadać tylko 1 saldo w systemie
    3.  Kwota zależy od podanego w żądaniu tworzenia salda, kodu promocyjnego:
        1.  KOD_1 - 100 zł

        2.  KOD_2 - 200 zł

        3.  KOD_3 - 300 zł

        4.  KOD_4 - 400 zł

        5.  KOD_5 - 500 zł

4.  Aplikacja powinna umożliwiać wykonanie przelewu z salda danego użytkownika(pobranego przez basic auth) poprzez wystawienie metody http

    1.  Na wejściu metody powinny być następujące dane:
        1.  nr rachunku użytkownika z którego ma być zlecony przelew
        2.  docelowy nr rachunku
        3.  kwota
    2.  Logika przelewu:
        1.  Docelowy rachunek musi istnieć w systemie
        2.  Każde saldo posiada limit dzienny przelewów. Nie można zrealizować więcej niż 3 przelewy dziennie. Jeżeli limit został przekroczony, należy zwrócić błąd
        3.  Saldo po przelewie nie może być mniejsze niż 0
    3.  Po udanym przelewie powinna zostać wysłana asynchronicznie(w innym wątku) wiadomość z użyciem preferowanego kanału zapisanego przy użytkownik
        1.  Wysyłka SMS oraz email powinna być zaślepiona i w przypadku wysyłki jednym z tych dwóch kanałów powinien pojawić się log:
            1.  SMS: "sending sms to phone number: {phoneNumber}, {content}" 
            2.  E-mail: "sending email to email addres: {email}, content: {content} "

### Wskazówki:

Nie musisz skupiać się na znajdowaniu brzegowych przypadków, wystarczy, że zaimplementujesz podstawową logikę opisaną w zadaniu. Jeżeli coś chcesz uwzględnić i brakuje Ci odpowiedzi na pytanie jak powinna aplikacja zachować się w konkretnym przypadku, zapisz to w komentarzu.