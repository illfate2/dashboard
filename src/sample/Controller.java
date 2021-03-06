package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.xml.sax.SAXException;
import sample.model.*;
import sample.view.*;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Controller {
    private View view;
    private Model model;

    public Controller(View view) {
        this.view = view;
        model = new Model();
        view.getOptionView().setOnAddButton(actionEvent -> {
            InfoView infoView = new InfoView();
            Stage stage = new Stage();
            Scene scene = new Scene((Parent) infoView.getNode(), 250, 250);
            stage.setScene(scene);
            stage.show();
            infoView.setOnSubmit(actionEvent1 -> {
                stage.close();
                StudentInfo info = infoView.getStudentInfo();
                model.addStudentInfos(info);
                renderTable();
            });
        });

        view.getOptionView().setOnDeleteButton(actionEvent -> {
            Stage stage = new Stage();
            VBox vbox = new VBox();
            DeleteInfoView searchView = new DeleteInfoView();
            vbox.getChildren().add(searchView.getView().getComboBox());
            Scene scene = new Scene(vbox, 700, 400);
            Label label = new Label("Deleted: -");
            vbox.getChildren().add(label);
            stage.setScene(scene);
            stage.show();
            searchView.getView().setOnSearch(actionEvent1 -> {
                SearchInfo info = searchView.getView().getSearchInfo();
                List<StudentInfo> infos = model.getStudentInfoViews();
                int counter = 0;
                if (info.group != null) {
                    counter = model.removeByNameAndGroup(info.name, info.group);
                } else if (info.lowAmountOfSkip != null && info.highAmountOfSkip != null) {
                    for (Iterator<StudentInfo> it = infos.iterator(); it.hasNext(); ) {
                        StudentInfo student = it.next();
                        if (student.getName().equals(info.name) &&
                                student.getSummary() < info.highAmountOfSkip &&
                                student.getSummary() > info.lowAmountOfSkip) {
                            it.remove();
                            counter++;
                        }
                    }
                } else if (!info.typeOfSkip.isEmpty()) {
                    counter = model.removeByNameAndTypeOfSkip(info.name, info.typeOfSkip);
                }
                label.setText("Deleted: " + counter);
                renderTable();
            });
        });

        view.getOptionView().setOnFindButton(actionEvent -> {
            TableWithPagination tableWithPagination = new TableWithPagination();
            Stage stage = new Stage();
            HBox hbox = new HBox();
            SearchView searchView = new SearchView();
            hbox.getChildren().add(searchView.getComboBox());
            hbox.getChildren().add(tableWithPagination.getHBox());
            Scene scene = new Scene(hbox, 700, 400);
            stage.setScene(scene);
            stage.show();
            searchView.setOnSearch(actionEvent1 -> {
                SearchInfo info = searchView.getSearchInfo();
                List<StudentInfo> infos = model.getStudentInfoViews();
                ArrayList<StudentInfo> result = new ArrayList<>();
                if (info.group != null) {
                    for (StudentInfo student :
                            infos) {
                        if (student.getName().equals(info.name) && student.getGroup().equals(info.group)) {
                            result.add(student);
                        }
                    }
                } else if (info.lowAmountOfSkip != null && info.highAmountOfSkip != null) {
                    for (StudentInfo student :
                            infos) {
                        if (student.getName().equals(info.name) &&
                                student.getSummary() < info.highAmountOfSkip &&
                                student.getSummary() > info.lowAmountOfSkip) {
                            result.add(student);
                        }
                    }
                } else if (!info.typeOfSkip.isEmpty()) {
                    for (StudentInfo student :
                            infos) {
                        if (student.getName().equals(info.name)) {
                            switch (info.typeOfSkip) {
                                case "illness":
                                    if (student.getByIllness() > 0) {
                                        result.add(student);
                                    }
                                    break;
                                case "without reason":
                                    if (student.getWithoutReason() > 0) {
                                        result.add(student);
                                    }
                                    break;
                                case "another reason":
                                    if (student.getAnotherReason() > 0) {
                                        result.add(student);
                                    }
                                    break;
                            }
                        }
                    }
                }
                renderSpecificTable(result, tableWithPagination);

            });
        });

        view.getOptionView().setOnOpenFile(actionEvent -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("XML", "*.xml")
            );
            File selectedFile = fileChooser.showOpenDialog(null);
            try {
                var info = getTableDataFromFile(selectedFile);
                model.setStudentInfoViews(info);
                renderTable();
            } catch (ParserConfigurationException | SAXException | IOException e) {
                e.printStackTrace();
            }
        });

        view.getOptionView().setOnSaveFile(actionEvent -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("XML", "*.xml")
            );
            File selectedFile = fileChooser.showSaveDialog(null);
            var infos = model.getStudentInfoViews();
            DOMParser parser = new DOMParser();
            try {
                parser.parse(infos, selectedFile);
            } catch (ParserConfigurationException | TransformerException e) {
                e.printStackTrace();
            }
        });

        view.getTableWithPagination().setRender(actionEvent -> renderTable());

    }

    public ArrayList<StudentInfo> getTableDataFromFile(File file) throws
            ParserConfigurationException, SAXException,
            IOException {
        SAXParserFactory factory = SAXParserFactory.newInstance();

        SAXParser saxParser = new SAXParser();
        var p = factory.newSAXParser();
        p.parse(file, saxParser);
        return saxParser.getStudents();
    }

    private void renderTable() {
        Integer size = view.getTableWithPagination().getPaginationView().getComboBoxSize();
        Integer currentPage = view.getTableWithPagination().getPaginationView().getCurrentPage();
        Integer pageNumber = roundUp(model.getStudentInfoViews().size(), size);
        view.getTableWithPagination().getPaginationView().setPagination(pageNumber);
        List<StudentInfo> studentList = model.getStudentInfos(size, size * currentPage);
        view.getTableWithPagination().setInfo(FXCollections.observableArrayList(studentList));
        view.getTableWithPagination().getPaginationView().renderLabel();
        view.getTableWithPagination().getPaginationView().renderInfoLabel(studentList.size(), model.getStudentInfoViews().size());
    }

    private void renderSpecificTable(ArrayList<StudentInfo> studentInfoViews, TableWithPagination
            tableWithPagination) {
        Integer size = tableWithPagination.getPaginationView().getComboBoxSize();
        Integer currentPage = tableWithPagination.getPaginationView().getCurrentPage();
        Integer pageNumber = roundUp(studentInfoViews.size(), size);
        tableWithPagination.getPaginationView().setPagination(pageNumber);
        ObservableList<StudentInfo> studentList = FXCollections.observableArrayList(studentInfoViews);
        tableWithPagination.setInfo(studentList);
        tableWithPagination.getPaginationView().renderLabel();
        tableWithPagination.getPaginationView().renderInfoLabel(studentList.size(), studentInfoViews.size());
    }

    private static Integer roundUp(Integer num, Integer divisor) {
        return (num + divisor - 1) / divisor;
    }
}
