# Generated by Django 5.1.2 on 2024-11-20 20:35

import django.utils.timezone
from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('app', '0006_alter_osoba_options_alter_osoba_plec'),
    ]

    operations = [
        migrations.AlterField(
            model_name='osoba',
            name='data_dodania',
            field=models.DateField(default=django.utils.timezone.now),
        ),
    ]