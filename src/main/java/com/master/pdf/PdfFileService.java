package com.master.pdf;

import com.master.db.DbConnection;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileOwnerAttributeView;
import java.nio.file.attribute.FileTime;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class PdfFileService {

    private List<String> listPDF = new ArrayList<>();
    private List<PdfFile> pdfFiles = new ArrayList<>();


    public List<String> searchFolderWithPDF(String path, String date) {

        //проверяем существует ли данный путь и указывает ли он на каталог
        File file = new File(path);
        if (!file.exists()) {
            System.out.println("Такого пути нет");
        }
        if (!file.isDirectory()) {
            System.out.println("Это не каталог с папками");
        }
        else {

            //массив для хранения названий каталогов в данной папке
            String[] dirList = file.list();

            for (int i=0; i < dirList.length; i++) {
                File f1 = new File(path + File.separator + dirList[i]);
                if (f1.isFile()) {

                    if (f1.getName().endsWith("pdf") && getFileCreationTime(f1).equals(date)) {
                        String link = path + File.separator + dirList[i];
                        listPDF.add(link);
                    }

                } else {
                    searchFolderWithPDF(path + File.separator + dirList[i], date);
                }
            }
        }
        return listPDF;
    }



    public List<PdfFile> addPdfFilesToList(List<String> listPDF) {

        for (String path : listPDF) {
            PdfFile pdfFile = createFileWithInfo(path);
            pdfFiles.add(pdfFile);
        }

        try {
            createFileInfo(pdfFiles);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return pdfFiles;
    }



    public PdfFile createFileWithInfo(String path) {
        File file = new File(path);
        PdfFile pdfFile = new PdfFile();

        pdfFile.setName(getFileName(file));

        //экранирование
        String pathEscape = file.getPath().replace("\\", "\\\\");

        pdfFile.setPath(pathEscape);
        pdfFile.setAuthor(getFileAuthor(file));
        pdfFile.setPageCount(getFileCountPages(file));
        pdfFile.setTimeCreation(getFileCreationTime(file));

        return pdfFile;

    }



    public String getFileName(File file) {
        return file.getName();
    }



    public String getFileCreationTime(File file) {

        try {
            BasicFileAttributes attributes = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
            FileTime timeFile = attributes.creationTime();
//            SimpleDateFormat df = new SimpleDateFormat("MM.dd.yyyy HH:mm");
            SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
            String dateCreated = df.format(timeFile.toMillis());
            return dateCreated;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    public String getFileAuthor(File file) {

        try {
            FileOwnerAttributeView fileOwnerAttributeView = java.nio.file.Files.getFileAttributeView(file.toPath(), FileOwnerAttributeView.class);
            String authorAndDomen = fileOwnerAttributeView.getOwner().getName().toString();
            String author = authorAndDomen.substring(8);
            return author;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    public int getFileCountPages(File file) {

        try {
            PDDocument doc = PDFParser.load(file);
            int countPages = doc.getNumberOfPages();
            return countPages;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    public void createFileInfo(List<PdfFile> pdfFiles) throws SQLException {

        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder sqlStringBuilder = new StringBuilder();


        try {
            PrintWriter printWriter = new PrintWriter("F:\\pdf-statistics.txt");
            printWriter.write(stringBuilder.toString());
            printWriter.flush();
            printWriter.close();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    public String getAllFiles() {

        //INSERT INTO pdf_info(date, filename, path, pagesCount, username)
        StringBuilder sqlStringBuilder = new StringBuilder();

        for (PdfFile pdf : pdfFiles) {
            sqlStringBuilder.append("('" +
                    pdf.getTimeCreation() + "', '" +
                    pdf.getName() + "', '" +
                    pdf.getPath() + "', '" +
                    pdf.getPageCount() + "', '" +
                    pdf.getAuthor() + "'), ");
        }

        String sql = sqlStringBuilder.toString();
        int lastIndex = sql.length();
        String sql2 = sql.substring(0, lastIndex - 2);
        String sql3 = sql2 + ";";

        return sql3;
    }




}
