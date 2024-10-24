```python
from app.models import Osoba, Stanowisko
from app.serializers import OsobaSerializer, StanowiskoSerializer
from rest_framework.renderers import JSONRenderer
from rest_framework.parsers import JSONParser
import io

# 1. Stworzenie nowej instancji klasy Osoba
stanowisko = Stanowisko.objects.create(nazwa='Programista', opis='Odpowiedzialny za kodowanie aplikacji')
osoba = Osoba(imie='Jan', nazwisko='Kowalski', plec=Osoba.Plec.MEZCZYZNA, stanowisko=stanowisko)
osoba.save()

# 2. Inicjalizacja serializera dla Osoba
osoba_serializer = OsobaSerializer(osoba)
print(osoba_serializer.data)

# 3. Serializacja danych do formatu JSON
osoba_content = JSONRenderer().render(osoba_serializer.data)
print(osoba_content)

# 4. Parsowanie danych JSON do obiektu Python
osoba_stream = io.BytesIO(osoba_content)
osoba_data = JSONParser().parse(osoba_stream)

# 5. Deserializacja danych
osoba_deserializer = OsobaSerializer(data=osoba_data)
osoba_deserializer.is_valid()
print(osoba_deserializer.validated_data)

# 6. Utrwalenie danych po deserializacji
osoba_deserializer.save()

# 7. Inicjalizacja serializera dla Stanowisko
stanowisko_serializer = StanowiskoSerializer(stanowisko)
print(stanowisko_serializer.data)

# 8. Serializacja danych Stanowisko do formatu JSON
stanowisko_content = JSONRenderer().render(stanowisko_serializer.data)
print(stanowisko_content)
```
