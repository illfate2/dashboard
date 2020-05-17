package sample.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Model {
    private ArrayList<StudentInfo> studentInfos;

    public Model() {
        studentInfos = new ArrayList<>();
    }

    public void addStudentInfos(StudentInfo info) {
        studentInfos.add(info);
        System.out.println(studentInfos.size());
    }

    public ArrayList<StudentInfo> getStudentInfos() {
        System.out.println(studentInfos.size());
        return studentInfos;
    }

    public List<StudentInfo> getStudentInfos(int limit, int offset) {
        if ((limit + offset) < studentInfos.size()) {
            return studentInfos.subList(offset, offset + limit);
        }
        if (studentInfos.size() == 0) {
            return studentInfos;
        }
        if (offset < studentInfos.size()) {
            return studentInfos.subList(offset, studentInfos.size());
        }
        return studentInfos.subList(studentInfos.size() - 1, studentInfos.size() - 1);
    }

    public void setStudentInfos(ArrayList<StudentInfo> studentInfos) {
        this.studentInfos = studentInfos;
        System.out.println(studentInfos.size());

    }

    public Integer removeByNameAndGroup(String name, Integer group) {
        int counter = 0;
        for (Iterator<StudentInfo> it = studentInfos.iterator(); it.hasNext(); ) {
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
        for (Iterator<StudentInfo> it = studentInfos.iterator(); it.hasNext(); ) {
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

