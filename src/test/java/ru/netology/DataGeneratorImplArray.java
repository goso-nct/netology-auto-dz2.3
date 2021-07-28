package ru.netology;

import java.util.Random;

public class DataGeneratorImplArray implements IDataGenerator {

    // only 10 elements or change getRandom() !!!

    String cities[] = new String[] {
            "Абакан", "Волгоград", "Вологда", "Кемерово", "Майкоп",
            "Москва", "Санкт-Петербург", "Самара", "Смоленск", "Тамбов"
    };

    String[] names = new String[] {
            "Александр", "Андрей", "Борис", "Владимир", "Иван",
            "Михаил", "Николай", "Олег", "Сергей", "Тимофей"
    };

    String phones[] = new String[] {
            "+79850000001", "+79850000002", "+79850000003", "+79850000004", "+79850000005",
            "+79850000006", "+79850000007", "+79850000008", "+79850000009", "+79850000010"
    };

    @Override
    public String generateCity() {
        return cities[getRandom()];
    }

    @Override
    public String generateName() {
        return names[getRandom()];
    }

    @Override
    public String generatePhone() {
        return phones[getRandom()];
    }

    private int getRandom() {
        Random generator = new Random();
        int randomIndex = generator.nextInt(10);
        return randomIndex;
    }
}
