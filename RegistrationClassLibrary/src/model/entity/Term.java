package model.entity;

//Term class holding informations about day, start time and place where classes take place

import java.util.Arrays;
import java.util.Objects;

public class Term {
    private Hall hall;
    private String weekParity;
    private String dayOfTheWeek;
    private Integer timeTable[] = new Integer[2];

    public Term(Hall hall, String weekParity, String dayOfTheWeek, Integer h, Integer m){
        this.hall = hall;
        this.weekParity = weekParity;
        this.dayOfTheWeek = dayOfTheWeek;
        this.timeTable[0] = h;
        this.timeTable[1] = m;
    }

    public Term() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public String getWeekParity() {
        return weekParity;
    }

    public void setWeekParity(String weekParity) {
        this.weekParity = weekParity;
    }

    public String getDayOfTheWeek() {
        return dayOfTheWeek;
    }

    public void setDayOfTheWeek(String dayOfTheWeek) {
        this.dayOfTheWeek = dayOfTheWeek;
    }

    public Integer[] getTimeTable() {
        return timeTable;
    }

    public void setTimeTable(Integer[] timeTable) {
        this.timeTable = timeTable;
    }
    
    public Hall getHall() {
        return this.hall;
    }



    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Term other = (Term) obj;
        if (!(this.weekParity.equals("-") || other.weekParity.equals("-"))) {
            if (!Objects.equals(this.weekParity, other.weekParity)) {
                return false;
            }
        }
        if (!Objects.equals(this.dayOfTheWeek, other.dayOfTheWeek)) {
            return false;
        }
        if (!Objects.equals(this.hall, other.hall)) {
            return false;
        }
        if (!Arrays.deepEquals(this.timeTable, other.timeTable)) {
            return false;
        }

        return true;
    }
    

    
    public String toString(){
        return   weekParity + " " + dayOfTheWeek + 
                " " + timeTable[0].toString() + ":" + timeTable[1].toString();
    }

    boolean checkTerm(Term term) {
        if (!(this.weekParity.equals("-") || term.weekParity.equals("-"))) {
            if (!Objects.equals(this.weekParity, term.weekParity)) {
                return true;
            }
        }
        if (!Objects.equals(this.dayOfTheWeek, term.dayOfTheWeek)) {
            return true;
        }
        if (!Arrays.deepEquals(this.timeTable, term.timeTable)) {
            return true;
        }
        return false;
    }
}
