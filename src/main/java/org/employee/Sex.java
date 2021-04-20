package org.employee;

public enum Sex {

    Kobieta("Woman"),
    Mężczyzna("Man");

    private String sex;

    public String getSex() {
        return sex;
    }

    Sex(String sex){
        this.sex=sex;
    }


    public static Sex fromString(String sex){
        for(Sex u : Sex.values()){
            if(u.sex.equalsIgnoreCase(sex)){
                return u;
            }
        }
        return null;
    }

}
