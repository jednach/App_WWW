"""
URL configuration for lab project.

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/4.2/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  path('', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  path('', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.urls import include, path
    2. Add a URL to urlpatterns:  path('blog/', include('blog.urls'))
"""
from django.contrib import admin
from django.urls import path, include
from django.conf import settings
from app.views import (
    OsobaDetailAPIView, OsobaListAPIView, OsobaSearchAPIView,
    stanowisko_list, stanowisko_detail
)

urlpatterns = [
    path('osoby/<int:pk>/', OsobaDetailAPIView.as_view(), name='osoba-detail'),
    path('osoby/', OsobaListAPIView.as_view(), name='osoba-list'),
    path('osoby/search/<str:nazwa_fragment>/', OsobaSearchAPIView.as_view(), name='osoba-search'),
    path('stanowiska/', stanowisko_list, name='stanowisko-list'),
    path('stanowiska/<int:pk>/', stanowisko_detail, name='stanowisko-detail'),
]

if settings.DEBUG:
    import debug_toolbar
    urlpatterns += [path('__debug__/', include(debug_toolbar.urls))]