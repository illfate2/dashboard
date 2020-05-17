package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;

import java.io.File;


public class OptionView {
    HBox hBox;
    Button add;
    Button find;
    Button delete;
    MenuItem fileSave;
    MenuItem fileOpen;

    public OptionView() {
        hBox = new HBox();
        fileSave = new MenuItem("Save");
        fileOpen = new MenuItem("Open");
        MenuItem[] items = new MenuItem[]{fileSave, fileOpen};
        MenuButton fileButton = new MenuButton("File", null, items);
        add = new Button("Add");
        find = new Button("Find");
        delete = new Button("Delete");
        hBox.getChildren().add(fileButton);
        hBox.getChildren().add(add);
        hBox.getChildren().add(find);
        hBox.getChildren().add(delete);
    }

    void setOnAddButton(EventHandler<ActionEvent> event) {
        add.setOnAction(event);
    }

    void setOnFindButton(EventHandler<ActionEvent> event) {
        find.setOnAction(event);
    }

    void setOnDeleteButton(EventHandler<ActionEvent> event) {
        delete.setOnAction(event);
    }




    void setOnOpenFile(EventHandler<ActionEvent> eventHandler) {
        fileOpen.setOnAction(eventHandler);
    }

    void setOnSaveFile(EventHandler<ActionEvent> eventHandler) {
        fileSave.setOnAction(eventHandler);
    }

    public Node getHBox() {
        return hBox;
    }
}
