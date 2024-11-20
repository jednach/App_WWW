from django.shortcuts import get_object_or_404
from rest_framework import status
from rest_framework.decorators import api_view
from rest_framework.response import Response
from rest_framework.views import APIView
from .models import Osoba, Stanowisko
from .serializers import OsobaSerializer, StanowiskoSerializer

# Endpointy dla modelu Osoba

# Widok do wyświetlania, dodawania i usuwania pojedynczego obiektu Osoba
class OsobaDetailAPIView(APIView):
    def get(self, request, pk):
        osoba = get_object_or_404(Osoba, pk=pk)
        serializer = OsobaSerializer(osoba)
        return Response(serializer.data)

    def delete(self, request, pk):
        osoba = get_object_or_404(Osoba, pk=pk)
        osoba.delete()
        return Response(status=status.HTTP_204_NO_CONTENT)

    def post(self, request):
        serializer = OsobaSerializer(data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data, status=status.HTTP_201_CREATED)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

# Widok do wyświetlania listy obiektów typu Osoba
class OsobaListAPIView(APIView):
    def get(self, request):
        osoby = Osoba.objects.all()
        serializer = OsobaSerializer(osoby, many=True)
        return Response(serializer.data)

# Widok do wyświetlania listy obiektów Osoba zawierających w nazwie zadany łańcuch znaków
class OsobaSearchAPIView(APIView):
    def get(self, request, nazwa_fragment):
        osoby = Osoba.objects.filter(nazwisko__icontains=nazwa_fragment)
        serializer = OsobaSerializer(osoby, many=True)
        return Response(serializer.data)

# Endpointy dla modelu Stanowisko

@api_view(['GET', 'POST'])
def stanowisko_list(request):
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
