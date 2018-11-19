package com.example.android.logger.models;

public class Employee {
    private String employeeName;
    private String employeeUserName;
    private String password;
    private String attendanceDate;
    private String attendanceMonth;
    private String attendanceYear;
    private String dateString;
    private long attendanceTimeInMilliseconds;


    public Employee() {

    }

    public Employee(String employeeUserName, String password) {
        this.employeeUserName = employeeUserName;
        this.password = password;
    }

    public Employee(String employeeName, String attendanceDate, String attendanceMonth, String attendanceYear, String dateString, long attendanceTimeInMilliseconds) {
        this.employeeName = employeeName;
        this.attendanceDate = attendanceDate;
        this.attendanceMonth = attendanceMonth;
        this.attendanceYear = attendanceYear;
        this.dateString = dateString;
        this.attendanceTimeInMilliseconds = attendanceTimeInMilliseconds;
    }


    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getAttendanceDate() {
        return attendanceDate;
    }

    public void setAttendanceDate(String attendanceDate) {
        this.attendanceDate = attendanceDate;
    }

    public long getAttendanceTimeInMilliseconds() {
        return attendanceTimeInMilliseconds;
    }

    public void setAttendanceTimeInMilliseconds(long attendanceTimeInMilliseconds) {
        this.attendanceTimeInMilliseconds = attendanceTimeInMilliseconds;
    }

    public String getAttendanceMonth() {
        return attendanceMonth;
    }

    public void setAttendanceMonth(String attendanceMonth) {
        this.attendanceMonth = attendanceMonth;
    }

    public String getAttendanceYear() {
        return attendanceYear;
    }


    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

    public String getEmployeeUserName() {
        return employeeUserName;
    }
    public void setAttendanceYear(String attendanceYear) {
        this.attendanceYear = attendanceYear;
    }

    public void setEmployeeUserName(String employeeUserName) {
        this.employeeUserName = employeeUserName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
