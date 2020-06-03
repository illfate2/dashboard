package sample.model;

import sample.view.StudentInfoView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Model {
    private List<StudentInfo> studentInfoViews;

    public Model() {
        studentInfoViews = new ArrayList<>();
    }

    public void addStudentInfos(StudentInfo info) {
        studentInfoViews.add(info);
        System.out.println(studentInfoViews.size());
    }

    public List<StudentInfo> getStudentInfoViews() {
        return studentInfoViews;
    }

    public List<StudentInfo> getStudentInfos(int limit, int offset) {
        if ((limit + offset) < studentInfoViews.size()) {
            return studentInfoViews.subList(offset, offset + limit);
        }
        if (studentInfoViews.size() == 0) {
            return studentInfoViews;
        }
        if (offset < studentInfoViews.size()) {
            return studentInfoViews.subList(offset, studentInfoViews.size());
        }
        return studentInfoViews.subList(studentInfoViews.size() - 1, studentInfoViews.size() - 1);
    }

    public void setStudentInfoViews(ArrayList<StudentInfo> studentInfoViews) {
        this.studentInfoViews = studentInfoViews;
        System.out.println(studentInfoViews.size());

    }

    public Integer removeByNameAndGroup(String name, Integer group) {
        int counter = 0;
        for (Iterator<StudentInfo> it = studentInfoViews.iterator(); it.hasNext(); ) {
            StudentInfo student = it.next();
            if (student.getName().equals(name) && student.getGroup().equals(group)) {
                it.remove();
                counter++;
            }
        }
        return counter;
    }

    public Integer removeByNameAndTypeOfSkip(String name, String typeOfSkip) {
        int counter = 0;
        for (Iterator<StudentInfo> it = studentInfoViews.iterator(); it.hasNext(); ) {
            StudentInfo student = it.next();
            if (student.getName().equals(name)) {
                switch (typeOfSkip) {
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
        return counter;
    }
}

