package com.master.display.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 */

public class DateFieldService {

    private String regexDateOneDay = "[0-9]{2}.[0-9]{2}.[0-9]{4}";    //regex соответствует формату дата 01.02.2024
    private String regexDateFewDays = "[0-9]{2}.[0-9]{2}.[0-9]{4}-[0-9]{2}.[0-9]{2}.[0-9]{4}";    //regex соответствует формату дата 01.02.2024-02.01.2024


    //проверка последовательности дат
    public boolean isCorrectFormat() {
        return false;
    }


    //один день или нет
    public boolean isOneDay(String date) {
        if (date.matches(regexDateOneDay)) {
            return true;
        }
        else return false;
    }


    //отделить даты
    public String[] splitDate(String dates) {



        return null;
    }


    //возвращает строк в виде LocalDate
    public LocalDate getLocalDateFromString(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return LocalDate.parse(date, formatter);
    }
}
