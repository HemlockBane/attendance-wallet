package com.example.android.logger.models;

public class Employee {
    private String employeeName;
    private String attendanceDate;
    private String attendanceMonth;
    private String attendanceYear;
    private String dateString;
    private long attendanceTimeInMilliseconds;


    public Employee(){

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

    public void setGetAttendanceYear(String attendanceYear) {
        this.attendanceYear = attendanceYear;
    }

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }
}
