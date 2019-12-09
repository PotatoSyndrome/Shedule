package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.LinkedList;


public class RozkladWindow {

    public static final String [] WEEK = {"Понеділок", "Вівторок", "Середа", "Четвер", "П'ятниця"};

    private Stage stage;
    private Scene scene;
    private FlowPane root;
    private DayTable [] days;
    private Button add;
    private Button delete;
    private Button sort;
    private Button search;

    public RozkladWindow() {
        stage = new Stage();
        stage.setMinWidth(500);
        stage.setMinHeight(500);
        root = new FlowPane(Orientation.VERTICAL, 10, 10);

        days = new DayTable[5];
        for (int i = 0; i < days.length; i++) {
            LinkedList<Rozklad> r = new LinkedList<>();
            r.add(new Rozklad("2PE", "Матьоша", 2205, new Time(8, 15)));
            days[i] = new DayTable(r, WEEK[i]);
            root.getChildren().addAll(days[i].all());
            days[i].relocate(200 * i, 0);
        }
        add = new Button("Додати");
        add.setOnAction(event -> {
            Rozklad toAdd = RozkladChanger.addRozklad();
            for (DayTable dt: days) {
                if (dt.getName().equals(RozkladChanger.getDayToAdd())) {
                    dt.add(toAdd);
                }
            }
        });

        delete = new Button("Видалити");
        delete.setOnAction(event -> {
            for (DayTable dt: days) {
                dt.delete();
            }
        });

        sort = new Button(" Сортувати");
        sort.setOnAction(event -> {
            for (DayTable dt: days) {
                dt.sort();
            }
        });

        search = new Button("Знайти");
        search.setOnAction(event -> {
            ArrayList<Rozklad> list = new ArrayList<>();
            for (DayTable dt: days) {
                list.addAll(dt.getSubjects().getItems());
            }
            search();
        });

        root.getChildren().addAll(add, delete, sort, search);
        scene = new Scene(root, 1000, 900);
        stage.setScene(scene);
    }

    public void show() {
        stage.show();
    }

    public void search() {
        Stage searchWindow = new Stage();
        GridPane root = new GridPane();
        Scene scene = new Scene(root);

        Label dayLabel = new Label("Виберіть день");
        root.add(dayLabel, 0, 0);

        ChoiceBox<String> dayBox = new ChoiceBox<String>(FXCollections.observableArrayList(RozkladWindow.WEEK));
        root.add(dayBox, 0, 1);

        Label nameLabel = new Label("Введіть назву предмету");
        root.add(nameLabel, 0, 2);

        TextField predField = new TextField();
        root.add(predField, 0, 3);

        Button confirm = new Button("Знайти");
        root.add(confirm, 0, 4);
        confirm.setOnAction(event -> {
            for (DayTable dt: days) {
                if (dayBox.getValue().equals(dt.getName())) {
                    RozkladChanger.show(dt.search(predField.getText()));
                }
            }
        });



        searchWindow.setScene(scene);
        searchWindow.showAndWait();
    }
}
