# Generated by Django 5.1.2 on 2024-10-23 20:09

import django.db.models.deletion
from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('app', '0002_coach'),
    ]

    operations = [
        migrations.CreateModel(
            name='Stanowisko',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('nazwa', models.CharField(max_length=100)),
                ('opis', models.TextField(blank=True, null=True)),
            ],
        ),
        migrations.CreateModel(
            name='Osoba',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('imie', models.CharField(max_length=50)),
                ('nazwisko', models.CharField(max_length=50)),
                ('plec', models.CharField(choices=[('K', 'Kobieta'), ('M', 'Mężczyzna'), ('I', 'Inne')], max_length=1)),
                ('stanowisko', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='app.stanowisko')),
            ],
        ),
    ]
