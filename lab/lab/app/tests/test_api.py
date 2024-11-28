from rest_framework.test import APITestCase
from rest_framework import status
from rest_framework.authtoken.models import Token
from django.contrib.auth.models import User
from ..models import Osoba, Stanowisko


class OsobaAPITests(APITestCase):
    def setUp(self):
        # Tworzenie użytkownika dla obu testów
        self.user = User.objects.create_user(username='testuser', password='testpassword')
        self.stanowisko = Stanowisko.objects.create(nazwa='Developer', opis='Tworzy aplikacje')

        # Generowanie tokenu dla użytkownika
        self.token = Token.objects.create(user=self.user)
        self.client.force_authenticate(user=self.user)  # Domyślna autoryzacja

    def test_create_osoba_with_token_authentication(self):
        """
        Test tworzenia obiektu Osoba z uwierzytelnianiem tokenowym.
        """
        # Konfiguracja tokenu w nagłówku
        self.client.credentials(HTTP_AUTHORIZATION=f'Token {self.token.key}')

        # Dane do stworzenia obiektu
        payload = {
            "imie": "Anna",
            "nazwisko": "Kowalska",
            "plec": 2,
            "stanowisko": self.stanowisko.id
        }

        # Wysłanie żądania POST
        response = self.client.post('/osoby/', payload, format='json')

        # Sprawdzanie odpowiedzi
        self.assertEqual(response.status_code, status.HTTP_201_CREATED)
        self.assertEqual(response.data['imie'], 'Anna')
        self.assertEqual(response.data['nazwisko'], 'Kowalska')

    def test_create_osoba_with_session_authentication(self):
        """
        Test tworzenia obiektu Osoba z uwierzytelnianiem sesyjnym.
        """
        # Logowanie użytkownika do sesji
        self.client.login(username='testuser', password='testpassword')

        # Dane do stworzenia obiektu
        payload = {
            "imie": "Piotr",
            "nazwisko": "Nowak",
            "plec": 1,
            "stanowisko": self.stanowisko.id
        }

        # Wysłanie żądania POST
        response = self.client.post('/osoby/', payload, format='json')

        # Sprawdzanie odpowiedzi
        self.assertEqual(response.status_code, status.HTTP_201_CREATED)
        self.assertEqual(response.data['imie'], 'Piotr')
        self.assertEqual(response.data['nazwisko'], 'Nowak')
