//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.hit.dao;

import com.hit.algorithm.Person;

import java.io.IOException;
import java.util.List;

public interface IDAO {
    List<Person> getList();

    boolean writeListToFile(List<Person> var1, String var2);

    void add(Person var1);

    public Person addPerson(String name, int age, String id, String profession, int years_of_experience) throws IOException;

    public List<Person> searchByAge(int min_age, int max_age);

    public List<Person> searchByProfession(String profession);

    public List<Person> searchByMinYearsOfExperience(int min_years_of_experience);

    public boolean save();

    public boolean removePerson(String p);

    String GetData();
}
