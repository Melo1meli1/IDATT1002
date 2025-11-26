package no.idatt1002.controller.Overview;

import javafx.scene.text.Text;

/**
 * Class that creates a table
 */
public class Table {
    private String Kategori;
    private double Forbruk;
    private double Budsjett;
    private Text Margin;

    public Table(String kategori, Double forbruk, Double budsjett, Text margin) {
        Kategori = kategori;
        Forbruk = forbruk;
        Budsjett = budsjett;
        Margin = margin;
    }

    /**
     * Gets the category
     * @return Kategori
     */
    public String getKategori() {
        return Kategori;
    }

    /**
     * get expence
     * @return Forbruk
     */
    public double getForbruk() {
        return Forbruk;
    }

    /**
     * Gets budget
     * @return Budsjett
     */
    public double getBudsjett() {
        return Budsjett;
    }

    /** gets margin (which is the difference between expence and budget)
     * @return Margin
     */
    public Text getMargin() {
        return Margin;
    }
}
