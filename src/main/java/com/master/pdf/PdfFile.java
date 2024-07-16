package com.master.pdf;

public class PdfFile {

    private String name;
    private String path;
    private String author;
    private String timeCreation;
    private long pageCount;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTimeCreation() {
        return timeCreation;
    }

    public void setTimeCreation(String timeCreation) {
        this.timeCreation = timeCreation;
    }

    public long getPageCount() {
        return pageCount;
    }

    public void setPageCount(long pageCount) {
        this.pageCount = pageCount;
    }

    @Override
    public String toString() {
        return "com.master.pdf.PdfFile{" +
                "name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", author='" + author + '\'' +
                ", timeCreation='" + timeCreation + '\'' +
                ", pageCount=" + pageCount +
                '}';
    }
}
