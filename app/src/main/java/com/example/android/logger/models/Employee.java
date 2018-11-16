package com.example.android.logger.models;

public class Employee {
    private String employeeName;
    private String attendanceDate;
    private String attendanceMonth;
    private String getAttendanceYear;
    private long attendanceTimeInMilliseconds;


    public Employee(){

    }

    public Employee(String employeeName, String attendanceDate, String attendanceMonth, String getAttendanceYear, long attendanceTimeInMilliseconds) {
        this.employeeName = employeeName;
        this.attendanceDate = attendanceDate;
        this.attendanceMonth = attendanceMonth;
        this.getAttendanceYear = getAttendanceYear;
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

    public String getGetAttendanceYear() {
        return getAttendanceYear;
    }

    public void setGetAttendanceYear(String getAttendanceYear) {
        this.getAttendanceYear = getAttendanceYear;
    }
}
