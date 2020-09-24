package com.auto.stdinfo.lib;

public class StudentInfoVO {
	
	private int code;
	private String course;
	private String name;
	private int marks;
	
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public int getMarks() {
        return marks;
    }

    public void setMarks(int marks) {
        this.marks = marks;
    }	
	@Override
    public String toString() {
        return 
                "code=" + code +
                ", name='" + name + '\'' +
                ", course='" + course + '\'' +
                ", marks=" + marks + "\n"
                ;
    }

}
