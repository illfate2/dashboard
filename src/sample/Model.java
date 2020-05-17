package sample;

import java.util.ArrayList;
import java.util.List;

public class Model {
    private ArrayList<StudentInfo> studentInfos;

    public Model() {
        studentInfos = new ArrayList<>();
    }

    void addStudentInfos(StudentInfo info) {
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
            return studentInfos.subList(offset, studentInfos.size() );
        }
        return studentInfos.subList(studentInfos.size() - 1, studentInfos.size() - 1);
    }

    public void setStudentInfos(ArrayList<StudentInfo> studentInfos) {
        this.studentInfos = studentInfos;
        System.out.println(studentInfos.size());

    }
}

