package sample.view;


import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.model.StudentInfo;


public class Table {
    private TableView<StudentInfo> tableView;

    public Table() {
        this.tableView = new TableView<StudentInfo>();
        tableView.setPrefSize(750, 400);
        tableView.setColumnResizePolicy(p -> true);
        TableColumn<StudentInfo, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableView.getColumns().add(nameColumn);

        TableColumn<StudentInfo, Integer> groupColumn = new TableColumn<>("Group");
        groupColumn.setCellValueFactory(new PropertyValueFactory<>("group"));
        tableView.getColumns().add(groupColumn);

        TableColumn<StudentInfo, Integer> byIllnessColumn = new TableColumn<>("By Illness");
        byIllnessColumn.setPrefWidth(150);
        byIllnessColumn.setCellValueFactory(new PropertyValueFactory<>("byIllness"));
        tableView.getColumns().add(byIllnessColumn);

        TableColumn<StudentInfo, Integer> anotherReasonColumn = new TableColumn<>("Another Reason");
        anotherReasonColumn.setCellValueFactory(new PropertyValueFactory<>("anotherReason"));
        anotherReasonColumn.setPrefWidth(150);
        tableView.getColumns().add(anotherReasonColumn);

        TableColumn<StudentInfo, Integer> withoutReasonColumn = new TableColumn<>("Without Reason");
        withoutReasonColumn.setCellValueFactory(new PropertyValueFactory<>("withoutReason"));
        withoutReasonColumn.setPrefWidth(150);
        tableView.getColumns().add(withoutReasonColumn);

        TableColumn<StudentInfo, Integer> summaryColumn = new TableColumn<>("Summary");
        summaryColumn.setCellValueFactory(new PropertyValueFactory<>("summary"));
        summaryColumn.setPrefWidth(150);
        tableView.getColumns().add(summaryColumn);
    }

    public Node getTableView() {
        return tableView;
    }

    public void setInfo(ObservableList<StudentInfo> infos) {
        this.tableView.setItems(infos);
    }


}
