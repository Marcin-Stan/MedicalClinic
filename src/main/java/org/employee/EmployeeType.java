package org.employee;

public enum EmployeeType {

    Administrator("Administrator"),
    Lekarz("Doctor"),
    Pielęgniarka("nurse"),
    Recepcjonistka("Recepcionist");

    private String employeeType;

    EmployeeType(String employeeType) {
        this.employeeType = employeeType;
    }

    public String getEmployeeType(){
        return this.employeeType;

    }

    public static EmployeeType fromString(String typeUser){
        for(EmployeeType u : EmployeeType.values()){
            if(u.employeeType.equalsIgnoreCase(typeUser)){
                return u;
            }
        }
        return null;
    }
}
