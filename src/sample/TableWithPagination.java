package sample;


import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import sample.model.StudentInfo;
import sample.view.PaginationView;
import sample.view.Table;


public class TableWithPagination {
    Table table;
    PaginationView paginationView;
    private VBox vBox;

    public TableWithPagination(Table table, PaginationView paginationView) {
        this.table = table;
        this.paginationView = paginationView;
        vBox = new VBox();
        vBox.getChildren().add(table.getTableView());
        vBox.getChildren().add(paginationView.getPagination());

    }

    public TableWithPagination() {
        this.table = new Table();
        this.paginationView = new PaginationView();
        vBox = new VBox();
        vBox.getChildren().add(table.getTableView());
        vBox.getChildren().add(paginationView.getPagination());
    }

    public void setRender(EventHandler<ActionEvent> eventEventHandler) {
        paginationView.setOnComboBox(eventEventHandler);

        paginationView.setOnFirst(eventEventHandler);

        paginationView.setOnLast(eventEventHandler);

        paginationView.setOnPrev(eventEventHandler);

        paginationView.setOnNext(eventEventHandler);
    }

    public Node getHBox() {
        return vBox;
    }


    public void setInfo(ObservableList<StudentInfo> infos) {
        this.table.setInfo(infos);
    }

}
