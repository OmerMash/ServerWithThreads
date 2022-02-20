package com.hit;

public class PersonDTO {
    private int personCounter = 0;
    private int age;
    private String ID;
    private String name;
    private String profession;
    private int experience_y;
    private Operation operation;
    private int min_age;
    private int max_age;
    private int min_years_of_experienc;

    public PersonDTO(int personCounter) {
        this.personCounter = personCounter;
    }

    public int getPersonCounter() {
        return personCounter;
    }

    public void setPersonCounter(int personCounter) {
        this.personCounter = personCounter;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public int getExperience_y() {
        return experience_y;
    }

    public void setExperience_y(int experience_y) {
        this.experience_y = experience_y;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public int getMin_age() {
        return min_age;
    }

    public void setMin_age(int min_age) {
        this.min_age = min_age;
    }

    public int getMax_age() {
        return max_age;
    }

    public void setMax_age(int max_age) {
        this.max_age = max_age;
    }

    public int getMin_years_of_experienc() {
        return min_years_of_experienc;
    }

    public void setMin_years_of_experienc(int min_years_of_experienc) {
        this.min_years_of_experienc = min_years_of_experienc;
    }
}
