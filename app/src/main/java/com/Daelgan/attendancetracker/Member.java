package com.Daelgan.attendancetracker;

public class Member{

    private String Name;
    private String FamilyName;
    private String DoB;
    private String Age;
    private String JoinDate;
    private String BeltLevel;
    private String BeltRank;
    private String DateOfLastTest;
    private String ClassesSinceLastTest;
    private String ClassesAttended;
    private String id;

    public Member(){}

    public String getName()
    {
        return Name;
    }

    public void setName(String Name)
    {
        this.Name = Name;
    }

    public String getFamilyName() { return FamilyName; }

    public void setFamilyName(String FamilyName) { this.Name = Name; }

    public String getDoB() {
        return DoB;
    }

    public void setDoB(String doB) {
        DoB = doB;
    }

    public String getAge()
    {
        return Age;
    }

    public void setAge(String age)
    {
        this.Age = Age;
    }

    public String getJoinDate() {
        return JoinDate;
    }

    public void setJoinDate(String joinDate) {
        JoinDate = joinDate;
    }

    public String getBeltLevel() {
        return BeltLevel;
    }

    public void setBeltLevel(String beltLevel) {
        BeltLevel = beltLevel;
    }

    public String getBeltRank()
    {
        return BeltRank;
    }

    public void setBeltRank(String beltRank)
    {
        BeltRank = beltRank;
    }

    public String getDateOfLastTest() {
        return DateOfLastTest;
    }

    public void setDateOfLastTest(String dateOfLastTest) {
        DateOfLastTest = dateOfLastTest;
    }

    public String getClassesSinceLastTest() {
        return ClassesSinceLastTest;
    }

    public void setClassesSinceLastTest(String classesSinceLastTest) {
        ClassesSinceLastTest = classesSinceLastTest;
    }

    public String getClassesAttended() {
        return ClassesAttended;
    }

    public void setClassesAttended(String classesAttended) {
        ClassesAttended = classesAttended;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        id = id;
    }
}
