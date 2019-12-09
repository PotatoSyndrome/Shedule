package sample;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;

public class RozkladChanger {
    private static String dayToAdd;

    public static Rozklad addRozklad() {
        Stage createWindow = new Stage();
        GridPane root = new GridPane();
        Scene scene = new Scene(root);

        root.setPadding(new Insets(25));
        root.setVgap(10);
        root.setHgap(10);

        Text title = new Text("Додати розклад");
        title.setFont(Font.font(20));
        HBox titleContainer = new HBox(title);
        titleContainer.setAlignment(Pos.TOP_CENTER);
        root.add(titleContainer, 0, 0, 2, 1);

        Label groupLabel = new Label("Група:");
        root.add(groupLabel, 0, 1);
        TextField groupField = new TextField();
        root.add(groupField, 1, 1);

        Label subjectLabel = new Label("Предмет:");
        root.add(subjectLabel, 0, 2);
        TextField subjectField = new TextField();
        root.add(subjectField, 1, 2);

        Label auditoryLabel = new Label("Аудиторія:");
        root.add(auditoryLabel, 0, 3);
        TextField auditoryField = new TextField();
        root.add(auditoryField, 1, 3);

        Label timeLabel = new Label("Час:");
        root.add(timeLabel, 2, 2);
        Spinner<Integer> hourSpinner = new Spinner<>(8, 18, 8);
        root.add(hourSpinner, 3, 2);
        Spinner<Integer> minuteSpinner = new Spinner<>(0, 59, 0);
        minuteSpinner.setEditable(true);
        root.add(minuteSpinner,3, 3);

        Label dayLabel = new Label("День:");
        root.add(dayLabel, 0, 4);
        ChoiceBox<String> dayBox = new ChoiceBox<String>(FXCollections.observableArrayList(RozkladWindow.WEEK));
        root.add(dayBox,1, 4);

        Button confirmButton = new Button("Додати");
        confirmButton.getStyleClass().add("success");
        VBox bottomContainer = new VBox(10, confirmButton);
        bottomContainer.setAlignment(Pos.BOTTOM_CENTER);
        root.add(bottomContainer, 0, 10, 2, 1);
        final Rozklad[] rozklad = new Rozklad[1];
        confirmButton.setOnAction(event -> {
            if( auditoryField.getText().isEmpty() || subjectField.getText().isEmpty() ||
                    groupField.getText().isEmpty() || dayBox.getSelectionModel().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Помилка");
                alert.setHeaderText("Помилка при додаванні розкладу до бази даних");
                alert.setContentText("Потрібно заповнити всі поля");
                alert.showAndWait();
            } else {
                String gruppa = groupField.getText();
                String predm = subjectField.getText();
                int aud = Integer.parseInt(auditoryField.getText());
                Time time = new Time(hourSpinner.getValue(),minuteSpinner.getValue());
                dayToAdd = dayBox.getValue();
                rozklad[0] = new Rozklad(gruppa, predm, aud, time);
                createWindow.close();
            }
        });
        createWindow.setTitle("Додати розклад");
        createWindow.setResizable(false);
        createWindow.setScene(scene);
        createWindow.showAndWait();
        return rozklad[0];
    }

    public static void show(Rozklad rozklad) {
        if (rozklad == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Помилка");
            alert.setHeaderText("Помилка при знаходженні предмета");
            alert.setContentText("Предмет не знайдено");
            alert.showAndWait();
            return;
        }
        Stage createWindow = new Stage();
        Pane root = new Pane();
        Scene scene = new Scene(root);

        Text title = new Text("Цей предмет відбудеться о " + rozklad.getTime().toString());
        title.setFont(Font.font(16));
        HBox titleContainer = new HBox(title);
        titleContainer.setAlignment(Pos.TOP_CENTER);
        title.setLayoutY(30);

        root.getChildren().addAll(title);

        createWindow.setScene(scene);
        createWindow.showAndWait();
    }

    public static String getDayToAdd() {
        return dayToAdd;
    }
}
