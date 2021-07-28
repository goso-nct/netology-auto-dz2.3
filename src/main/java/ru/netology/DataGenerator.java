package ru.netology;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public interface DataGenerator {
    default String generateDate(int shift) {
        return LocalDate.now().plusDays(shift).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }
    String generateCity();
    String generateName();
    String generatePhone();
}
