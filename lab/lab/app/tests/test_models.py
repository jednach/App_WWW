from django.test import TestCase
from ..models import Osoba, Stanowisko
from django.contrib.auth.models import User
from rest_framework.test import APIClient
from rest_framework import status


class OsobaModelTest(TestCase):
    @classmethod
    def setUpTestData(cls):
        # Przygotowanie danych testowych
        cls.user = User.objects.create_user(username='testuser', password='testpassword')
        cls.stanowisko = Stanowisko.objects.create(nazwa='Manager', opis='Zarządza zespołem')
        cls.osoba = Osoba.objects.create(
            imie='Jan', nazwisko='Kowalski', plec=1, wlasciciel=cls.user, stanowisko=cls.stanowisko
        )

    def test_str_method(self):
        # Test metody __str__
        osoba = Osoba.objects.get(id=self.osoba.id)
        self.assertEqual(str(osoba), 'Jan Kowalski')

    def test_verbose_name(self):
        # Test verbose_name dla pola imie
        osoba = Osoba.objects.get(id=self.osoba.id)
        field_label = osoba._meta.get_field('imie').verbose_name
        self.assertEqual(field_label, 'imie')

    def test_plec_choices(self):
        # Test wartości z choices dla pola plec
        osoba = Osoba.objects.get(id=self.osoba.id)
        self.assertEqual(osoba.get_plec_display(), 'Kobieta')

from django.test import TestCase
from ..models import Person, Team


class PersonModelTest(TestCase):
    @classmethod
    def setUpTestData(cls):
        # Tworzymy dane testowe dla modelu Person
        cls.person1 = Person.objects.create(name='Jan', shirt_size='M')
        cls.person2 = Person.objects.create(name='Anna', shirt_size='L')

    def test_ids_are_unique(self):
        # Sprawdzanie unikalności identyfikatorów
        self.assertNotEqual(self.person1.id, self.person2.id)

    def test_str_method(self):
        # Test metody __str__
        self.assertEqual(str(self.person1), 'Jan')

    def test_shirt_size_label(self):
        # Test etykiety pola shirt_size
        field_label = self.person1._meta.get_field('shirt_size').verbose_name
        self.assertEqual(field_label, 'shirt size')


class TeamModelTest(TestCase):
    @classmethod
    def setUpTestData(cls):
        # Tworzymy dane testowe dla modelu Team
        cls.team1 = Team.objects.create(name='Team Alpha', country='PL')
        cls.team2 = Team.objects.create(name='Team Beta', country='US')

    def test_ids_are_unique(self):
        # Sprawdzanie unikalności identyfikatorów
        self.assertNotEqual(self.team1.id, self.team2.id)

    def test_str_method(self):
        # Test metody __str__
        self.assertEqual(str(self.team1), 'Team Alpha')

    def test_country_field(self):
        # Sprawdzanie poprawności pola country
        self.assertEqual(self.team1.country, 'PL')
        self.assertEqual(self.team2.country, 'US')


class OsobaListViewTest(TestCase):
    def setUp(self):
        # Przygotowanie danych testowych
        self.client = APIClient()
        self.user = User.objects.create_user(username='testuser', password='testpassword')
        self.stanowisko = Stanowisko.objects.create(nazwa='Developer', opis='Tworzy aplikacje')
        Osoba.objects.create(imie='Anna', nazwisko='Nowak', plec=2, wlasciciel=self.user, stanowisko=self.stanowisko)

        # Autoryzacja użytkownika
        self.client.force_authenticate(user=self.user)

    def test_osoba_list_status_code(self):
        # Wysłanie żądania GET do widoku osoba_list
        response = self.client.get('/osoby/')

        # Sprawdzamy, czy status odpowiedzi to 200 OK
        self.assertEqual(response.status_code, status.HTTP_200_OK)