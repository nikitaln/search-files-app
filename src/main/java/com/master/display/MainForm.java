package com.master.display;

import com.master.db.DbConnection;
import com.master.display.service.DateFieldService;
import com.master.pdf.PdfFile;
import com.master.pdf.PdfFileService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class MainForm {

    private JPanel mainPanel;
    private JPanel managePanel;
    private JLabel titleLabel;
    private JTextField mainPathToDirectoryField;
    private JLabel mainPathToDirectoryLabel;
    private JButton searchButton;
    private JTextField filesCountField;
    private JTextField pagesCountField;
    private JTextField dateField;
    private JLabel filesCountLabel;
    private JLabel pagesCountLabel;
    private JLabel dateLabel;
    private JLabel statisticsLabel;
    private JProgressBar progressBar;
    private JButton saveDbButton;
    private JLabel resultSaveDBLabel;

    private PdfFileService searchPdfFile;
    private DbConnection dbConnection = new DbConnection();


    public MainForm() {

        //кнопка поиска файлов
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                    @Override
                    protected Void doInBackground() {

                        progressBar.setIndeterminate(true);

                        scanningPdf();

                        return null;
                    }
                    @Override
                    public void done() {
                        progressBar.setIndeterminate(false);
                    }
                };
                worker.execute();
            }
        });


        //кнопка сохранения в бд
        saveDbButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /**
                 * TODO: insert to db
                 */

                System.out.println("connect to DB");
                String sql = searchPdfFile.getAllFiles();

                System.out.println(sql);
                dbConnection.getConnection();

                try {
                    dbConnection.executeMultiInsert(sql);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                resultSaveDBLabel.setVisible(true);
            }
        });
    }



    public void scanningPdf() {

        String path = mainPathToDirectoryField.getText();
        String date = dateField.getText();

        DateFieldService dateFieldService = new DateFieldService();

        if (dateFieldService.isCorrectDateFormat(date)) {
            System.out.println("даты введены верно");
            if (dateFieldService.isOneDay(date)) {
                System.out.println("один день");
                //
                LocalDate localDateOneDay = dateFieldService.getLocalDateFromString(date);
                List<LocalDate> dates = new ArrayList<>();
                dates.add(localDateOneDay);
                searchPdf(path, dates);

            } else {
                System.out.println("несколько дней");
                //
                String[] days = dateFieldService.splitDate(date);
                String dayStart = days[0];
                String dayEnd = days[1];

                LocalDate localDateStart = dateFieldService.getLocalDateFromString(dayStart);
                LocalDate localDateEnd = dateFieldService.getLocalDateFromString(dayEnd);

                if (dateFieldService.isCorrectPeriodOfDate(localDateStart, localDateEnd)) {
                    System.out.println("корректная последовательность дат");
                    List<LocalDate> dates = dateFieldService.getAllDaysBetweenTwoDates(localDateStart, localDateEnd);

                    for (LocalDate date1 : dates) {
                        System.out.println(date1.toString());
                    }
                    searchPdf(path, dates);
                }
            }
        } else {
            System.out.println("неверный формат даты");
        }
    }



    public void searchPdf(String path, List<LocalDate> dates) {

        searchPdfFile = new PdfFileService();
        List<String> paths = searchPdfFile.searchFolderWithPDF(path, dates);
        List<PdfFile> PDFs = searchPdfFile.addPdfFilesToList(paths);

        int count = 0;
        long pagesCount = 0;

        System.out.println("Перечисление всех файлов");

        for (PdfFile file : PDFs) {
            if (dates.contains(file.getTimeCreation())) {
                System.out.println(file.toString());
                count = count + 1;
                pagesCount = pagesCount + file.getPageCount();
            }
        }

        filesCountField.setText(String.valueOf(count));
        pagesCountField.setText(String.valueOf(pagesCount));

    }



    public JPanel getMainPanel() {
        return mainPanel;
    }
}
