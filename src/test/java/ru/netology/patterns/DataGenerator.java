package ru.netology.patterns;


import com.github.javafaker.Address;
import com.github.javafaker.Faker;
import lombok.Data;
import lombok.Value;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;


public class DataGenerator {
    private DataGenerator() {
    }

    public static String generateDate(int shift) {



        return LocalDate.now().plusDays(shift).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }


    public static String generateCity() {


        var cities = new String[]{"Москва", "Астрахань", "Краснодар", "Ростов-на-Дону", "Киров", "Санкт-Петербург", "Екатеринбург",
                "Тверь", "Омск", "Рязань"};

        return cities[new Random().nextInt(cities.length)];
    }

    public static String generateName(String locale) {



        var faker = new Faker(new Locale(locale));

        return faker.name().lastName() + " " + faker.name().firstName();
    }

    public static String generatePhone(String locale) {


        var faker = new Faker(new Locale(locale));

        return faker.phoneNumber().phoneNumber();
    }


    public static class Registration {
        private Registration() {
        }

        public static UserInfo generateUser(String locale) {



            return new UserInfo(generateCity(), generateName(locale), generatePhone(locale));
        }
    }

    @Data
    public static class UserInfo {
        private final String city;
        private final String name;
        private final String phone;

    }
}
