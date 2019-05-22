package dataMining.model;


import model.entity.Student;

import java.util.ArrayList;
import java.util.List;


public class StatisticObject {

    private String name;
    private List<Student> studentList;
    private List<StatisticObject> branches;

    public StatisticObject(String name) {
        this.name = name;
        this.studentList = new ArrayList<>();
        this.branches = new ArrayList<>();
    }

    public void addStudent(Student student) {
        this.studentList.add(student);
    }

    public int getStudentsCount() {
        return this.studentList.size();
    }

    public void addBranch(StatisticObject statisticObject) {
        this.branches.add(statisticObject);
    }

    //@Nullable
    public StatisticObject getBranch(String name) {
        for (StatisticObject branch : branches) {
          if ( name.equals(branch.getName()) ) {
              return branch;
          }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }

    public List<StatisticObject> getBranches() {
        return branches;
    }

    public void setBranches(List<StatisticObject> branches) {
        this.branches = branches;
    }
}
