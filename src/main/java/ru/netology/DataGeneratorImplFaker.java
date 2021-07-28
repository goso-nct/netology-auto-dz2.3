package ru.netology;

import com.github.javafaker.Faker;

import java.util.Locale;

public class DataGeneratorImplFaker implements DataGenerator {

    Faker ruFaker = new Faker(new Locale("ru-RU"));

    @Override
    public String generateCity() {
        return ruFaker.address().cityName();
    }

    @Override
    public String generateName() {
        return ruFaker.name().fullName();
    }

    @Override
    public String generatePhone() {
        return ruFaker.phoneNumber().phoneNumber().replaceAll("[^0-9+]", "");
    }
}
