package sample;


import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.LinkedList;

public class DayTable {
    private TableView<Rozklad> subjects;
    private String name;
    private Label nameLabel;

    public DayTable(LinkedList<Rozklad> subjects, String name) {
        this.subjects = new TableView<>(FXCollections.observableArrayList(subjects));
        this.name = name;
        nameLabel = new Label(this.name);
        init();
    }

    public void sort() {
        ObservableList<Rozklad> rozklads = subjects.getItems();
        subSort(rozklads, 0, rozklads.size() - 1);
        subjects.setItems(rozklads);
    }

    private void subSort(ObservableList<Rozklad> arr, int start, int end) { // швидке сортування за часом
        if (start >= end)
            return;
        int i = start, j = end;
        int cur = i - (i - j) / 2;
        while (i < j) {
            while (i < cur && (arr.get(i).getTime().compareTo(arr.get(cur).getTime()) <= 0)) {
                i++;
            }
            while (j > cur && (arr.get(cur).getTime().compareTo(arr.get(j).getTime()) <= 0)) {
                j--;
            }
            if (i < j) {
                Rozklad temp = arr.get(i);
                arr.set(i, arr.get(j));
                arr.set(j, temp);
                if (i == cur)
                    cur = j;
                else if (j == cur)
                    cur = i;
            }
        }
        subSort(arr, start, cur);
        subSort(arr,cur+1, end);
    }

    public LinkedList<Node> all() {
        LinkedList<Node> arrayList = new LinkedList<>();
        arrayList.add(nameLabel);
        arrayList.add(subjects);
        return arrayList;
    }

    private void init() {
        TableColumn<Rozklad, String> nameColumn = new TableColumn<Rozklad, String>("Група");
        nameColumn.setCellValueFactory(new PropertyValueFactory<Rozklad, String>("gruppa"));
        subjects.getColumns().add(nameColumn);

        TableColumn<Rozklad, String> ageColumn = new TableColumn<Rozklad, String>("Предмет");
        ageColumn.setCellValueFactory(new PropertyValueFactory<Rozklad, String>("predm"));
        subjects.getColumns().add(ageColumn);

        TableColumn<Rozklad, Integer> gruppaColumn = new TableColumn<Rozklad, Integer>("Аудиторія");
        gruppaColumn.setCellValueFactory(new PropertyValueFactory<Rozklad, Integer>("aud"));
        subjects.getColumns().add(gruppaColumn);

        TableColumn<Rozklad, String> kursColumn = new TableColumn<Rozklad, String>("Час");
        kursColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<String>(
                param.getValue().getTime().toString()));
        subjects.getColumns().add(kursColumn);
    }

    public void add(Rozklad rozklad) {
        ObservableList<Rozklad> rozklads = subjects.getItems();
        rozklads.add(rozklad);
        subjects.setItems(rozklads);
    }

    public Rozklad search(String name) {
        ArrayList<Rozklad> rozklads = new ArrayList<>(subjects.getItems());

        for (int i = 1; i < rozklads.size(); ++i) { // сортування включенням
            Rozklad key = rozklads.get(i);
            int j = i;
            while (j > 0 && rozklads.get(j - 1).getPredm().compareTo(key.getPredm()) > 0) {
                Rozklad temp = rozklads.get(j);
                rozklads.set(j, rozklads.get(j - 1));
                rozklads.set(j - 1, temp);
                --j;
            }
            rozklads.set(j, key);
        }
        return binarySearch(rozklads, name, 0, rozklads.size() - 1);
    }

    private Rozklad binarySearch(ArrayList<Rozklad> list, String name, int low, int high) {
        if(high < low)
            return null;
        int mid = (low + high) / 2;
        if(list.get(mid).getPredm().compareTo(name) > 0)
            return binarySearch(list, name, low, mid - 1);
        else if (list.get(mid).getPredm().compareTo(name) < 0)
            return binarySearch(list, name, mid + 1, high);
        else return list.get(mid);
    }

    public void relocate(int x, int y) {
        nameLabel.relocate(x, y);
        subjects.relocate(x, y + nameLabel.getHeight() + 10);
    }

    public void delete() {
        ObservableList<Rozklad> rozklads = subjects.getItems();
        rozklads.remove(getSelected());
        subjects.setItems(rozklads);
    }

    public TableView<Rozklad> getSubjects() {
        return subjects;
    }

    public Rozklad getSelected() {
        return subjects.getSelectionModel().getSelectedItem();
    }

    public String getName() {
        return name;
    }
}
