package sample;

import javafx.scene.control.Button;

public class DeleteInfoView {
    SearchView view;

    public DeleteInfoView() {
        Button search = new Button("Delete");
        this.view = new SearchView(search);
    }
}
