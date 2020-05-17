package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.swing.text.TabableView;
import java.util.ArrayList;

public class View {
    Group root;
    OptionView optionView;
    TableWithPagination tableWithPagination;

    public View() {
        root = new Group();
        VBox vbox = new VBox();
        optionView = new OptionView();
        tableWithPagination = new TableWithPagination(new Table(), new PaginationView());
        StackPane pane = new StackPane();
        pane.setPadding(new Insets(5));
        pane.getChildren().add(tableWithPagination.getHBox());

        vbox.getChildren().add(optionView.getHBox());
        vbox.getChildren().add(pane);
        root.getChildren().add(vbox);
    }


    public OptionView getOptionView() {
        return optionView;
    }

    public Group getRoot() {
        return root;
    }
}
