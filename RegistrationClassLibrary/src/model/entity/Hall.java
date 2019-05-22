package model.entity;

import java.util.Objects;

public class Hall {
   
    private String buildingName;
    private String hallName;
    private Integer size;
    
    public Hall(String buildingName, String hallName, Integer size) {
        this.buildingName = buildingName;
        this.hallName = hallName;
        this.size = size;
    }
    
    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getHallName() {
        return hallName;
    }

    public void setHallName(String hallName) {
        this.hallName = hallName;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
   
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.buildingName);
        hash = 23 * hash + Objects.hashCode(this.hallName);
        hash = 23 * hash + Objects.hashCode(this.size);
        return hash;
    }

    @Override
    public String toString() {
        return  buildingName + "/" + hallName;
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
        final Hall other = (Hall) obj;
        if (!(buildingName.equals(other.buildingName) && hallName.equals(other.hallName))) {
            return false;
        }    
        return true;
    }  
    
    public String[] createStringArray() {
        String [] data = new String[3];
        data[0] = buildingName;
        data[1] = hallName;
        data[2] = size.toString();
        return data;
    }
}
