package ru.netology;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

public class DataGenerator {

    static Faker faker = new Faker(new Locale("ru-RU"));;

    private DataGenerator() {
    }

    public static String generateDate(int minShift, int maxShift) {
        Random random = new Random();
        int addDays = random.ints(minShift, maxShift).findFirst().getAsInt();
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public static String generateCity() {
        return faker.address().cityName();
    }

    public static String generateName() {
        return faker.name().fullName().replaceAll("ё","е");
    }

    public static String generatePhone() {
        return faker.phoneNumber().phoneNumber().replaceAll("[^0-9+]", "");
    }

    public static class Registration
    {
        private Registration() {
        }

        public static UserInfo generateUser(String locale) {
            UserInfo user = new UserInfo(
                    generateCity(),
                    generateName(),
                    generatePhone()
            );
            return user;
        }
    }

    @Value
    public static class UserInfo {
        String city;
        String name;
        String phone;
    }

}
