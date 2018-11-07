package sample;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.BoxBlur;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.stage.Stage;
import javafx.util.Duration;
import static java.lang.Math.random;


public class Main extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {
    Group root = new Group();
    Scene scene = new Scene(root, 800, 600, Color.BLACK);
    primaryStage.setScene(scene);

    // uses a for loop to add 30 white colored
    // circles with a radius of 150
    Group circles = new Group();
    for (int i = 0; i < 30; i++) {
      Circle circle = new Circle(150, Color.web("white", 0.05));

      // adds a white border to the circle
      circle.setStrokeType(StrokeType.OUTSIDE);
      circle.setStroke(Color.web("white", 0.16));
      circle.setStrokeWidth(4);

      // adds a blur to the border of the box
      circles.setEffect(new BoxBlur(10, 10, 3));

      circles.getChildren().add(circle);
    }
    // creates a rectangle named colors
    // the Stop[] sequence shows what gradient color should be at that spot
    // NO_CYCLE makes the color cycle not repeat
    Rectangle colors = new Rectangle(scene.getWidth(), scene.getHeight(),
        new LinearGradient(0f, 1f, 1f, 0f, true, CycleMethod.NO_CYCLE, new
            Stop[]{
            new Stop(0, Color.web("#f8bd55")),
            new Stop(0.14, Color.web("#c0fe56")),
            new Stop(0.28, Color.web("#5dfbc1")),
            new Stop(0.43, Color.web("#64c2f8")),
            new Stop(0.57, Color.web("#be4af7")),
            new Stop(0.71, Color.web("#ed5fc2")),
            new Stop(0.85, Color.web("#ef504c")),
            new Stop(1, Color.web("#f2660f")),}));
    colors.widthProperty().bind(scene.widthProperty());
    colors.heightProperty().bind(scene.heightProperty());

    // setBlendMode() applies blending to the overlay
    // it can darken images, or add highlights
    Group blendModeGroup =
        new Group(new Group(new Rectangle(scene.getWidth(), scene.getHeight(),
            Color.BLACK), circles), colors);
    colors.setBlendMode(BlendMode.OVERLAY);
    root.getChildren().add(blendModeGroup);

    // creates a timeline for the circles
    Timeline timeline = new Timeline();
    for (Node circle: circles.getChildren()) {
      timeline.getKeyFrames().addAll(
          // sets random positions for each circle at 0 seconds
          new KeyFrame(Duration.ZERO, // set start position at 0
              new KeyValue(circle.translateXProperty(), random() * 800),
              new KeyValue(circle.translateYProperty(), random() * 600)
          ),
          // sets random positions for the circles at 40 seconds
          new KeyFrame(new Duration(40000), // set end position at 40s
              new KeyValue(circle.translateXProperty(), random() * 800),
              new KeyValue(circle.translateYProperty(), random() * 600)
          )
      );
    }
// play 40s of animation
    timeline.play();

    primaryStage.show();
  }
}
