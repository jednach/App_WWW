```python
# Import modelu Osoba i Stanowisko
from app.models import Osoba, Stanowisko

# 1. Wyświetl wszystkie obiekty modelu Osoba
osoby = Osoba.objects.all()
print(osoby)

# 2. Wyświetl obiekt modelu Osoba z id = 3
osoba_id_3 = Osoba.objects.get(id=3)
print(osoba_id_3)

# 3. Wyświetl obiekty modelu Osoba, których imie rozpoczyna się na literę 'i'
osoby_i = Osoba.objects.filter(imie__startswith='i')
print(osoby_i)

# 4. Wyświetl unikalną listę stanowisk przypisanych dla modeli Osoba
stanowiska_unikalne = Stanowisko.objects.filter(osoba__isnull=False).distinct()
print(stanowiska_unikalne)

# 5. Wyświetl nazwy stanowisk posortowane alfabetycznie malejąco
stanowiska_posortowane = Stanowisko.objects.order_by('-nazwa')
print(stanowiska_posortowane)

# 6. Dodaj nową instancję obiektu klasy Osoba i zapisz w bazie
pierwsze_stanowisko = Stanowisko.objects.first()
nowa_osoba = Osoba.objects.create(
    imie='Anna',
    nazwisko='Nowak',
    plec=Osoba.Plec.KOBIETA,
    stanowisko=pierwsze_stanowisko
)
print(nowa_osoba)
```
