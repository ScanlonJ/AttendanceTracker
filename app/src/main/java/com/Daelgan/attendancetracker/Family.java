package com.Daelgan.attendancetracker;

import java.util.ArrayList;

public class Family {

    private String familyName;

    private String ContactNumber;
    private String ContactEmail;
    private String ContactRelation;
    private String ContactName;

    private String ClassesPurchased;
    private String ClassesRemaining;

    private ArrayList<Member> familyMembers;

    public String getContactNumber() {
        return ContactNumber;
    }

    public void setContactNumber(String contactNumber) {
        ContactNumber = contactNumber;
    }

    public String getContactEmail() {
        return ContactEmail;
    }

    public void setContactEmail(String contactEmail) {
        ContactEmail = contactEmail;
    }

    public String getContactRelation() {
        return ContactRelation;
    }

    public void setContactRelation(String contactRelation) {
        ContactRelation = contactRelation;
    }

    public String getContactName() {
        return ContactName;
    }

    public void setContactName(String contactName) {
        ContactName = contactName;
    }

    public String getClassesPurchased() {
        return ClassesPurchased;
    }

    public void setClassesPurchased(String classesPurchased) {
        ClassesPurchased = classesPurchased;
    }

    public String getClassesRemaining() {
        return ClassesRemaining;
    }

    public void setClassesRemaining(String classesRemaining) {
        ClassesRemaining = classesRemaining;
    }
}
