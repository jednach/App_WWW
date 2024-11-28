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
from rest_framework.authtoken import views as drf_views
from app.views import (
    osoba_list, osoba_detail, osoba_detail_view, osoba_update, osoba_delete,
    stanowisko_list, stanowisko_detail, stanowisko_members,
    test_osoba_view
)

urlpatterns = [
    path('admin/', admin.site.urls),
    path('osoby/', osoba_list, name='osoba-list'),
    path('osoby/<int:pk>/', osoba_detail, name='osoba-detail'),
    path('osoba/<int:pk>/', osoba_detail_view, name='osoba-detail'),
    path('osoby/update/<int:pk>/', osoba_update, name='osoba-update'),
    path('osoby/delete/<int:pk>/', osoba_delete, name='osoba-delete'),
    path('stanowiska/', stanowisko_list, name='stanowisko-list'),
    path('stanowiska/<int:pk>/', stanowisko_detail, name='stanowisko-detail'),
    path('stanowiska/members/<int:pk>/', stanowisko_members, name='stanowisko-members'),
    path('api-auth/', include('rest_framework.urls')),
    path('api-token-auth/', drf_views.obtain_auth_token, name='api_token_auth'),
    path('test-osoba/', test_osoba_view, name='test-osoba'),
]

if settings.DEBUG:
    import debug_toolbar
    urlpatterns += [path('__debug__/', include(debug_toolbar.urls))]