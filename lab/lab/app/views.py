from django.shortcuts import get_object_or_404
from rest_framework import status
from rest_framework.decorators import api_view, authentication_classes, permission_classes
from rest_framework.authentication import TokenAuthentication, SessionAuthentication, BasicAuthentication
from rest_framework.permissions import IsAuthenticated
from rest_framework.response import Response
from django.contrib.auth.models import User
from rest_framework.authtoken.models import Token
from .models import Osoba, Stanowisko
from .serializers import OsobaSerializer, StanowiskoSerializer

# Lista wszystkich obiektów typu Osoba (tylko dla właściciela)
@api_view(['GET', 'POST'])
@authentication_classes([TokenAuthentication, SessionAuthentication, BasicAuthentication])
@permission_classes([IsAuthenticated])
def osoba_list(request):
    """
    Lista wszystkich obiektów typu Osoba lub dodanie nowego obiektu (tylko dla zalogowanego użytkownika).
    """
    if request.method == 'GET':
        osoby = Osoba.objects.filter(wlasciciel=request.user)
        serializer = OsobaSerializer(osoby, many=True)
        return Response(serializer.data)

    elif request.method == 'POST':
        serializer = OsobaSerializer(data=request.data)
        if serializer.is_valid():
            serializer.save(wlasciciel=request.user)
            return Response(serializer.data, status=status.HTTP_201_CREATED)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

# Pobranie szczegółów obiektu typu Osoba
@api_view(['GET'])
def osoba_detail(request, pk):
    """
    Pobranie pojedynczego obiektu typu Osoba (dostęp dla każdego).
    """
    osoba = get_object_or_404(Osoba, pk=pk)
    serializer = OsobaSerializer(osoba)
    return Response(serializer.data)

# Aktualizacja obiektu typu Osoba (tylko dla właściciela)
@api_view(['PUT'])
@authentication_classes([TokenAuthentication, SessionAuthentication, BasicAuthentication])
@permission_classes([IsAuthenticated])
def osoba_update(request, pk):
    """
    Aktualizacja obiektu typu Osoba (tylko dla zalogowanego użytkownika).
    """
    osoba = get_object_or_404(Osoba, pk=pk)
    if osoba.wlasciciel != request.user:
        return Response(status=status.HTTP_403_FORBIDDEN)

    serializer = OsobaSerializer(osoba, data=request.data)
    if serializer.is_valid():
        serializer.save()
        return Response(serializer.data)
    return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

# Usunięcie obiektu typu Osoba (tylko dla właściciela, wymaga uwierzytelnienia tokenem)
@api_view(['DELETE'])
@authentication_classes([TokenAuthentication])
@permission_classes([IsAuthenticated])
def osoba_delete(request, pk):
    """
    Usunięcie obiektu typu Osoba (tylko dla zalogowanego użytkownika, wymaga uwierzytelnienia tokenem).
    """
    osoba = get_object_or_404(Osoba, pk=pk)
    if osoba.wlasciciel != request.user:
        return Response(status=status.HTTP_403_FORBIDDEN)

    osoba.delete()
    return Response(status=status.HTTP_204_NO_CONTENT)

# Widoki dla modelu Stanowisko
@api_view(['GET', 'POST'])
def stanowisko_list(request):
    """
    Lista wszystkich obiektów typu Stanowisko lub dodanie nowego obiektu.
    """
    if request.method == 'GET':
        stanowiska = Stanowisko.objects.all()
        serializer = StanowiskoSerializer(stanowiska, many=True)
        return Response(serializer.data)

    elif request.method == 'POST':
        serializer = StanowiskoSerializer(data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data, status=status.HTTP_201_CREATED)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

@api_view(['GET', 'PUT', 'DELETE'])
def stanowisko_detail(request, pk):
    """
    Pobranie, aktualizacja lub usunięcie pojedynczego obiektu typu Stanowisko.
    """
    stanowisko = get_object_or_404(Stanowisko, pk=pk)

    if request.method == 'GET':
        serializer = StanowiskoSerializer(stanowisko)
        return Response(serializer.data)

    elif request.method == 'PUT':
        serializer = StanowiskoSerializer(stanowisko, data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

    elif request.method == 'DELETE':
        stanowisko.delete()
        return Response(status=status.HTTP_204_NO_CONTENT)

# Generowanie tokenów dla istniejących użytkowników
@api_view(['GET'])
@authentication_classes([SessionAuthentication, BasicAuthentication])
@permission_classes([IsAuthenticated])
def generate_tokens_for_users(request):
    """
    Generowanie tokenów dla wszystkich istniejących użytkowników, którzy nie mają jeszcze tokena.
    """
    users = User.objects.all()
    for user in users:
        Token.objects.get_or_create(user=user)
    return Response({'status': 'Tokens generated for all users'}, status=status.HTTP_200_OK)

# Lista wszystkich obiektów typu Osoba przypisanych do danego Stanowiska
@api_view(['GET'])
@authentication_classes([TokenAuthentication])
@permission_classes([IsAuthenticated])
def stanowisko_members(request, pk):
    """
    Lista wszystkich obiektów typu Osoba przypisanych do danego Stanowiska (tylko do odczytu, wymaga uwierzytelnienia tokenem).
    """
    stanowisko = get_object_or_404(Stanowisko, pk=pk)
    osoby = Osoba.objects.filter(stanowisko=stanowisko)
    serializer = OsobaSerializer(osoby, many=True)
    return Response(serializer.data)
