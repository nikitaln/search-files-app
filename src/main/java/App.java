
import com.master.display.MainForm;

import javax.swing.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class App {

    public static void main(String[] args) {

        String date1 = "01.01.2024";
        String date2 = "01.01.2024-01.02.2024";

        String regexDate1 = "[0-9]{2}.[0-9]{2}.[0-9]{4}";    //regex соответствует формату дата 01.02.2024
        String regexDate2 = "[0-9]{2}.[0-9]{2}.[0-9]{4}-[0-9]{2}.[0-9]{2}.[0-9]{4}";    //regex соответствует формату дата 01.02.2024-02.01.2024

        /**
         * Проверка формата ввода даты
         * Проверка правильности последовательности от меньшей даты к большей
         */


        if (date2.matches(regexDate2)) {    //проверка правильной последовательности дат
            System.out.println(true);
            String[] dates = date2.split("-");
            String firstDate = dates[0];
            String secondDate = dates[1];

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            LocalDate localDate1 = LocalDate.parse(firstDate, formatter);
            DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            LocalDate localDate2 = LocalDate.parse(secondDate, formatter);

            if (localDate2.isAfter(localDate1)) {
                System.out.println("верная последовательность");
            } else {
                System.out.println("не верная последовательность");
            }

        } else {
            System.out.println(false);
        }


//        JFrame frame = new JFrame();
//        frame.setSize(500, 400);
//        frame.setTitle("PDF-analyzer");
//
//        MainForm mf = new MainForm();
//        frame.add(mf.getMainPanel());
//
//        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//        frame.setLocationRelativeTo(null);
//        frame.setVisible(true);

    }
}
