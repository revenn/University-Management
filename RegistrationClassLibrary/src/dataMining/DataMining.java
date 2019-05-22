package dataMining;

import dataMining.model.StatisticObject;
import model.entity.Student;

import java.util.List;

public class DataMining {

    public StatisticObject getGenderStudentStatistic(StatisticObject statisticObject) {
        List<Student> testingData = statisticObject.getStudentList();

        for (Student student : testingData) {
            StatisticObject branch = statisticObject.getBranch(student.getGender());
            if (branch == null) {
                branch = new StatisticObject(student.getGender());
                statisticObject.addBranch(branch);
            }
            branch.addStudent(student);
        }
        return statisticObject;
    }

    public StatisticObject getFieldOfStudyStudentStatistic(StatisticObject statisticObject) {
        List<Student> testingData = statisticObject.getStudentList();

        for (Student student : testingData) {
            StatisticObject branch = statisticObject.getBranch(student.getFieldOfStudy().getName());
            if (branch == null) {
                branch = new StatisticObject(student.getFieldOfStudy().getName());
                statisticObject.addBranch(branch);
            }
            branch.addStudent(student);
        }
        return statisticObject;
    }
 
    public StatisticObject getSpecialtyStudentStatistic(StatisticObject statisticObject) {
        List<Student> testingData = statisticObject.getStudentList();

        for (Student student : testingData) {
            StatisticObject branch = statisticObject.getBranch(student.getSpecialty().getName());
            if (branch == null) {
                branch = new StatisticObject(student.getSpecialty().getName());
                statisticObject.addBranch(branch);
            }
            branch.addStudent(student);
        }
        return statisticObject;
    }

    public StatisticObject getCoursesTermsStudentStatistic(StatisticObject statisticObject) {
        List<Student> testingData = statisticObject.getStudentList();

        for (Student student : testingData) {
            String value = student.getMostChosenClassesTerm();
            StatisticObject branch = statisticObject.getBranch(value);
            if (branch == null) {
                branch = new StatisticObject(value);
                statisticObject.addBranch(branch);
            }
            branch.addStudent(student);
        }
        return statisticObject;
    }
}

