package no.idatt1002;

import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.TextAlignment;

/**
 * Costumized piechart class, that resembles a doughnutchart
 */
public class DoughnutChart extends PieChart {
  private Circle innerCircle;
  private final String[] colors = { "#9bc53d", "#ffc600", "#c3423f", "#a913e5", "#25ccc6", "#1567d3",
      "#fd6406", "#f109ed", "#a8bfc3", "#094eff", "#609098" };

  private boolean showTotal = false;
  private Label centerLabel = new Label();
  private HBox centerTotal = new HBox(centerLabel);
  private VBox centerAlignment = new VBox(centerTotal);

  private StackPane centerStack = new StackPane();

  /**
   * Creates a new donut chart, requires no parameters
   */
  public DoughnutChart() {
    super();

    // Default inner circle color: white
    innerCircle = new Circle();
    innerCircle.setFill(Color.web("#ffffff"));
    centerStack.getChildren().add(innerCircle);
    super.setLabelsVisible(false);
    super.setLegendVisible(false);
  }

  /**
   * Creates a new donut chart, requires observable list as parameter
   * @param chartData
   */
  public DoughnutChart(ObservableList<Data> chartData) {
    super(chartData);

    // Default inner circle color: white
    innerCircle = new Circle();
    innerCircle.setFill(Color.web("#00ffff"));

    super.setLabelsVisible(false);
    super.setLegendVisible(false);
  }

  /**
   * sets inner circle color in doughnutchart
   * @param color the color of the inner circle
   */
  public void setInnerCircleColor(String color) {
    innerCircle.setFill(Color.web(color));
  }

  /**
   * Sets size of the donut chart
   * @param size int size
   */
  public void setSize(int size) {
    super.setPrefSize(size, size);
    super.setMinSize(size, size);
    super.setMaxSize(size, size);
  }

  /**
   * Handles chart position parameters
   * @param top y coordinaes
   * @param left x coordinates
   * @param contentWidth width
   * @param contentHeight height
   */
  @Override
  protected void layoutChartChildren(double top, double left, double contentWidth, double contentHeight) {
    super.layoutChartChildren(top, left, contentWidth, contentHeight);

    // double sliceCenterEdgeX = calcX(labelAngles[index], pieRadius, centerX);
    // double sliceCenterEdgeY = calcY(labelAngles[index], pieRadius, centerY);

    addInnerStackIfNotPresent();
    updateInnerStackLayout(top, left, contentWidth, contentHeight);
    setColors();
  }

  /**
   * sets colors on slices in chart
   */
  private void setColors() {

    int i = 0;
    for (PieChart.Data dataSlice : super.getData()) {
      dataSlice.getNode().setStyle(
          "-fx-pie-color: " + colors[i % colors.length] + ";");
      i++;
    }
  }

  /**
   * Shows total value of the chart in the inner circle of the chart
   * @param show boolean on if the total is to be visible or not
   */
  public void showTotal(boolean show) {
    this.showTotal = show;
    if (!centerStack.getChildren().contains(centerAlignment) && show) {
      centerStack.getChildren().add(centerAlignment);
    }
  }

  /**
   * Calculates the total value of a chart
   * @return a double representing the total value the chart holds
   */
  private double calcTotal() {
    double total = 0d;
    for (PieChart.Data p : getData()) {
      total += p.getPieValue();
    }
    return total;
  }

  /**
   * adds center total and inner circle if not present
   */
  private void addInnerStackIfNotPresent() {
    if (getData().size() > 0) {
      Node pie = getData().get(0).getNode();
      if (pie.getParent() instanceof Pane) {
        Pane parent = (Pane) pie.getParent();

        if (!parent.getChildren().contains(centerStack)) {
          parent.getChildren().add(centerStack);
        }
        if (showTotal && !centerStack.getChildren().contains(centerAlignment)) {
          centerStack.getChildren().add(centerAlignment);
        }
        centerStack.toFront();
      }
    }
  }

  /**
   * ensures positioning and order of center elements
   * @param top y value
   * @param left x value
   * @param contentWidth width
   * @param contentHeight height
   */
  public void updateInnerStackLayout(double top, double left, double contentWidth, double contentHeight) {
    centerStack.setMaxSize(contentWidth, contentHeight);
    centerStack.setMinSize(contentWidth, contentHeight);
    centerStack.setPrefSize(contentWidth, contentHeight);

    centerStack.toFront();

    if (centerStack.getChildren().contains(innerCircle)) {
      StackPane.setAlignment(innerCircle, Pos.CENTER);
    }
    if (centerStack.getChildren().contains(centerTotal)) {
      StackPane.setAlignment(centerTotal, Pos.CENTER);
    }

    double minX = Double.MAX_VALUE, minY = Double.MAX_VALUE;
    double maxX = Double.MIN_VALUE, maxY = Double.MIN_VALUE;
    for (PieChart.Data data : getData()) {
      Node node = data.getNode();

      Bounds bounds = node.getBoundsInParent();
      if (bounds.getMinX() < minX) {
        minX = bounds.getMinX();
      }
      if (bounds.getMinY() < minY) {
        minY = bounds.getMinY();
      }
      if (bounds.getMaxX() > maxX) {
        maxX = bounds.getMaxX();
      }
      if (bounds.getMaxY() > maxY) {
        maxY = bounds.getMaxY();
      }
    }

    double x = minX + (maxX - minX) / 2;
    double y = minY + (maxY - minY) / 2;

    centerStack.setTranslateX(x);
    centerStack.setTranslateY(y);

    x = (maxX - minX) / 2;
    y = (maxY - minY) / 2;

    updateCenterTotalLayout(x, y);
    updateInnerCircleLayout();
  }

  /**
   * Sets the layout of the center total
   * @param x x pos
   * @param y y pos
   */
  private void updateCenterTotalLayout(double x, double y) {

    centerLabel.setText(Double.toString(calcTotal()));
    centerLabel.setTextAlignment(TextAlignment.CENTER);
    centerTotal.setStyle("-fx-font-family: Arial; -fx-font-size: 2em; -fx-font-weight: bold; -fx-text-fill: #000000");
    centerAlignment.setMinSize(x, y);
    centerAlignment.setPrefSize(x, y);
    centerAlignment.setMaxSize(x, y);
    centerTotal.setAlignment(Pos.CENTER);
    centerAlignment.setAlignment(Pos.CENTER);
  }

  /**
   * Adds an inner circle if not present.
   */
  private void addInnerCircleIfNotPresent() {
    if (getData().size() > 0) {
      Node pie = getData().get(0).getNode();
      if (pie.getParent() instanceof Pane) {
        Pane parent = (Pane) pie.getParent();

        if (!parent.getChildren().contains(innerCircle)) {
          parent.getChildren().add(innerCircle);
        }
        // innerCircle.toFront();
        if (!parent.getChildren().contains(centerTotal)) {
          parent.getChildren().add(centerTotal);
        }
        // centerTotal.toFront();
      }
    }
  }

  /**
   * Updates the layout of the inner circle in the chart
   */
  private void updateInnerCircleLayout() {
    double minX = Double.MAX_VALUE, minY = Double.MAX_VALUE;
    double maxX = Double.MIN_VALUE, maxY = Double.MIN_VALUE;
    for (PieChart.Data data : getData()) {
      Node node = data.getNode();

      Bounds bounds = node.getBoundsInParent();
      if (bounds.getMinX() < minX) {
        minX = bounds.getMinX();
      }
      if (bounds.getMinY() < minY) {
        minY = bounds.getMinY();
      }
      if (bounds.getMaxX() > maxX) {
        maxX = bounds.getMaxX();
      }
      if (bounds.getMaxY() > maxY) {
        maxY = bounds.getMaxY();
      }
    }

    innerCircle.setCenterX(minX + (maxX - minX) / 2);
    innerCircle.setCenterY(minY + (maxY - minY) / 2);

    innerCircle.setRadius((maxX - minX) / 4);
  }

  /**
   * Displayes the slice names/kategories in the chart
   */
  private void displaySliceNames() {
    // innerCircle = getData().get(0)

    for (int i = 1; i <= getData().size(); i++) {
      getData().get(i).getNode();
    }
  }
}
