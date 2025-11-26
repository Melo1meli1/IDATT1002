package no.idatt1002.controller.Budget;

import no.idatt1002.Model.Budget;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

/**
 * Controller class for BudgetElement.fxml
 * Used to display a single budget element in the budget view
 */
public class BudgetElementController {
    @FXML
    private Label category;
    @FXML
    private Label sum;
    @FXML
    private Label color;
    @FXML
    private Label editIcon;

    public Budget budget;
    private String categoryColor = "#aaaaaa";

    public void setColor(String color) {
        categoryColor = color;
    }

    /**
     * This function sets the data for a Budget object, updates the text, and styles
     * the elements with a
     * given color.
     * 
     * @param budget The Budget Object
     */

    public void setData(Budget budget) throws FileNotFoundException {
        this.budget = budget;
        setText();
        styleElements(categoryColor);
    }

    /**
     * This function loads a new FXML file for editing a budget element and sets the
     * data for the
     * controller.
     * 
     * @param event An ActionEvent object that represents the user's action that
     *              triggered the method
     *              call. It could be a button click, menu item selection, or any
     *              other user interaction that is
     *              associated with an action.
     */
    public void editBudgetElement(ActionEvent event) {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/BudgetEdit"
                    + ".fxml"));
            Stage stage = new Stage();
            Parent root = fxmlLoader.load(); // Problemet er her
            stage.setScene(new Scene(root));
            BudgetEditController controller = fxmlLoader.getController();
            controller.setData(budget);
            stage.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setText() {
        category.setText(budget.getCategory());
        sum.setText(Double.toString(budget.getSum()));
    }

    private void styleElements(String categoryColor) throws FileNotFoundException {
        category.setStyle(
                "-fx-font-family: Arial; -fx-font-size: 1.25em; -fx-font-weight: bold; -fx-text-fill: #ffffff; -fx-padding: 4 8 4 40");

        category.setTranslateX(-20);

        sum.setStyle(
                "-fx-font-family: Arial; -fx-font-size: 1.4em; -fx-font-weight: bold; -fx-text-fill: #212529; -fx-padding: 4 8 4 4");

        sum.setBorder(new Border(new BorderStroke(Color.web("#212529"), BorderStrokeStyle.SOLID, new CornerRadii(16),
                new BorderWidths(1))));
        sum.setBackground(
                new Background(new BackgroundFill(Color.web("#ffffff"), new CornerRadii(16), Insets.EMPTY)));
        sum.setAlignment(Pos.BASELINE_RIGHT);

        color.setBackground(
                new Background(new BackgroundFill(Color.web(categoryColor), new CornerRadii(16), Insets.EMPTY)));

    }

}
