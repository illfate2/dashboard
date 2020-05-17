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

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class Controller {
    View view;
    Model model;

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
            vbox.getChildren().add(searchView.view.getComboBox());
            Scene scene = new Scene(vbox, 700, 400);
            Label label = new Label("Deleted: -");
            vbox.getChildren().add(label);
            stage.setScene(scene);
            stage.show();
            searchView.view.setOnSearch(actionEvent1 -> {
                SearchInfo info = searchView.view.getSearchInfo();
                ArrayList<StudentInfo> infos = model.getStudentInfos();
                int counter = 0;
                if (info.group != null) {
                    for (Iterator<StudentInfo> it = infos.iterator(); it.hasNext(); ) {
                        StudentInfo student = it.next();
                        if (student.getName().equals(info.name) && student.getGroup().equals(info.group)) {
                            it.remove();
                            counter++;
                        }
                    }
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
                    for (Iterator<StudentInfo> it = infos.iterator(); it.hasNext(); ) {
                        StudentInfo student = it.next();
                        if (student.getName().equals(info.name)) {
                            switch (info.typeOfSkip) {
                                case "illness":
                                    if (student.getByIllness() > 0) {
                                        it.remove();
                                        counter++;
                                    }
                                    break;
                                case "without reason":
                                    if (student.getWithoutReason() > 0) {
                                        it.remove();
                                        counter++;
                                    }
                                    break;
                                case "another reason":
                                    if (student.getAnotherReason() > 0) {
                                        it.remove();
                                        counter++;
                                    }
                                    break;
                            }
                        }
                    }
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
                ArrayList<StudentInfo> infos = model.getStudentInfos();
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

        view.optionView.setOnOpenFile(actionEvent -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("XML", "*.xml")
            );
            File selectedFile = fileChooser.showOpenDialog(null);
            try {
                var info = getTableDataFromFile(selectedFile);
                model.setStudentInfos(info);
                renderTable();
            } catch (ParserConfigurationException | SAXException | IOException e) {
                e.printStackTrace();
            }
        });

        view.optionView.setOnSaveFile(actionEvent -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("XML", "*.xml")
            );
            File selectedFile = fileChooser.showSaveDialog(null);
            var infos = model.getStudentInfos();
            DOMParser parser = new DOMParser();
            try {
                parser.parse(infos, selectedFile);
            } catch (ParserConfigurationException | TransformerException e) {
                e.printStackTrace();
            }
        });

        view.tableWithPagination.setRender(actionEvent -> renderTable());

    }

    public ArrayList<StudentInfo> getTableDataFromFile(File file) throws ParserConfigurationException, SAXException,
            IOException {
        SAXParserFactory factory = SAXParserFactory.newInstance();

        SAXParser saxParser = new SAXParser();
        var p = factory.newSAXParser();
        p.parse(file, saxParser);
        return saxParser.getStudents();
    }

    private void renderTable() {
        Integer size = view.tableWithPagination.paginationView.getComboBoxSize();
        Integer currentPage = view.tableWithPagination.paginationView.getCurrentPage();
        Integer pageNumber = roundUp(model.getStudentInfos().size(), size);
        view.tableWithPagination.paginationView.setPagination(pageNumber);
        ObservableList<StudentInfo> studentList = FXCollections.observableArrayList(model.getStudentInfos(size, size * currentPage));
        view.tableWithPagination.setInfo(studentList);
        view.tableWithPagination.paginationView.renderLabel();
        view.tableWithPagination.paginationView.renderInfoLabel(studentList.size(), model.getStudentInfos().size());
    }

    private void renderSpecificTable(ArrayList<StudentInfo> studentInfos, TableWithPagination tableWithPagination) {
        Integer size = tableWithPagination.paginationView.getComboBoxSize();
        Integer currentPage = tableWithPagination.paginationView.getCurrentPage();
        Integer pageNumber = roundUp(studentInfos.size(), size);
        tableWithPagination.paginationView.setPagination(pageNumber);
        ObservableList<StudentInfo> studentList = FXCollections.observableArrayList(studentInfos);
        tableWithPagination.setInfo(studentList);
        tableWithPagination.paginationView.renderLabel();
        tableWithPagination.paginationView.renderInfoLabel(studentList.size(), studentInfos.size());
    }

    private static Integer roundUp(Integer num, Integer divisor) {
        return (num + divisor - 1) / divisor;
    }
}
