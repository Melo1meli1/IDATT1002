package no.idatt1002.controller.Assistent;

import javafx.animation.FillTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/*
 * This class is a controller for the assistent page
 * This is currenty Inactive
 */
public class AssistentController {
  // private Pane assistentButton1 = newToggleSwitch("S", 100, 100);

  public Pane newToggleSwitch(String S_M_L, int buttonPositionX, int buttonPositionY) {
    int costumSize = 0;
    String backgroundcolorButtonBody = "#E7EDF2";
    String bordercolorButtonBody = "#212529";
    double buttonBodySizeH = 0;
    double buttonBodySizeV = 0;
    String transitionBackgroundcolorButtonBody = "#ca0728";
    double backgroundTransitionTime = 0.25;

    String backgroundcolorButtonCircle = "#212529";
    String bordercolorButtonCircle = "#212529";
    double circleTransitionTime = 0.25;

    switch (S_M_L.toUpperCase()) {
      case "S":
        costumSize = 15;
        buttonBodySizeH = 60;
        buttonBodySizeV = 30;
        break;

      case "M":
        costumSize = 20;
        buttonBodySizeH = 80;
        buttonBodySizeV = 40;
        break;

      case "L":
        costumSize = 25;
        buttonBodySizeH = 100;
        buttonBodySizeV = 50;
        break;

      default:
        throw new IllegalArgumentException("Invalid button size (S, M, L)");
    }

    BooleanProperty switchedOn = new SimpleBooleanProperty(false);
    TranslateTransition translateAnimation = new TranslateTransition(Duration.seconds(circleTransitionTime));
    FillTransition fillAnimation = new FillTransition(Duration.seconds(backgroundTransitionTime));
    ParallelTransition animation = new ParallelTransition(translateAnimation, fillAnimation);

    Rectangle background = new Rectangle(buttonBodySizeH, buttonBodySizeV);
    background.setX(buttonPositionX);
    background.setY(buttonPositionY);
    background.setArcWidth(buttonBodySizeV);
    background.setArcHeight(buttonBodySizeV);
    background.setFill(Paint.valueOf(backgroundcolorButtonBody));
    background.setStroke(Paint.valueOf(bordercolorButtonBody));

    double circleRadius = (buttonBodySizeV / 2) - 1;
    Circle trigger = new Circle((circleRadius));
    trigger.setCenterX(buttonPositionX + costumSize);// plassering på rundingen, denne må være på samme sted som starten
                                                     // av rektangelet
    trigger.setCenterY(buttonPositionY + costumSize);// +25 skal gjøre opp for radiusen til sirkelen
    trigger.setFill(Paint.valueOf(backgroundcolorButtonCircle));
    trigger.setStroke(Paint.valueOf(bordercolorButtonCircle));

    translateAnimation.setNode(trigger);
    fillAnimation.setShape(background);

    trigger.setOnMouseClicked(event -> {
      switchedOn.set(!switchedOn.get());
    });

    double finalButtonBodySizeH = buttonBodySizeH;

    switchedOn.addListener((obs, oldState, newState) -> {
      boolean isOn = newState.booleanValue();
      translateAnimation.setToX(isOn ? finalButtonBodySizeH - circleRadius * 2 : 0);// avstanden knappen beveger seg.
                                                                                    // denne burde være lengden av
                                                                                    // rektangelet minus diameteren til
                                                                                    // sirkelen.
      fillAnimation.setFromValue(isOn ? (Color) Paint.valueOf(backgroundcolorButtonBody)
          : (Color) Paint.valueOf(transitionBackgroundcolorButtonBody));
      fillAnimation.setFromValue(isOn ? (Color) Paint.valueOf(transitionBackgroundcolorButtonBody)
          : (Color) Paint.valueOf(backgroundcolorButtonBody));
      trigger.setStroke(
          isOn ? Paint.valueOf(backgroundcolorButtonCircle) : Paint.valueOf(transitionBackgroundcolorButtonBody));
      trigger.setStroke(
          isOn ? Paint.valueOf(transitionBackgroundcolorButtonBody) : Paint.valueOf(backgroundcolorButtonCircle));
      background.setStroke(isOn ? Paint.valueOf(bordercolorButtonBody) : Paint.valueOf(backgroundcolorButtonBody));
      background.setStroke(isOn ? Paint.valueOf(backgroundcolorButtonBody) : Paint.valueOf(bordercolorButtonBody));
      animation.play();
    });

    return new Pane(background, trigger);
  }
}
