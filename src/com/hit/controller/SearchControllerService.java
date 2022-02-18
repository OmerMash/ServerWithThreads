package com.hit.controller;

import com.hit.algorithm.IAlgoSearch;
import com.hit.algorithm.Person;
import com.hit.algorithm.SearchByAgeImpl;
import com.hit.algorithm.SearchByMinYOEImpl;
import com.hit.algorithm.SearchByProfessionImpl;
import com.hit.dao.IDAO;
import com.hit.dao.MyDaoFileImpl;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class SearchControllerService {
    private static ObjectOutputStream output;
    private static ObjectInputStream input;
    private IAlgoSearch algoSearch;
    private IDAO dao;
    private List<Person> searchResult;

    public IDAO getDao() {
        return this.dao;
    }

    public SearchControllerService() {
        try {
            this.dao = new MyDaoFileImpl("person_ser");
        } catch (IOException var2) {
            var2.printStackTrace();
        }

    }

    public Person addPerson(String name, int age, String id, String profession, int years_of_experience) throws IOException {
        Person p = new Person(name, age, id, profession, years_of_experience);
        this.dao.add(p);
        System.out.println(this.dao.getList());
        System.out.println("Done adding person");
        return p;
    }

    public void searchByAge(int min_age, int max_age) {
        SearchByAgeImpl search_by_age = new SearchByAgeImpl();
        this.searchResult = search_by_age.search(this.dao.getList(), min_age, max_age, "", 0);
        System.out.println("Search by Age results:" + this.searchResult);
    }

    public void searchByProfession(String profession) {
        SearchByProfessionImpl searchByProfession = new SearchByProfessionImpl();
        this.searchResult = searchByProfession.search(this.dao.getList(), 5, 50, profession, 0);
        System.out.println("Search by profession results:" + this.searchResult);
    }

    public void searchByMinYearsOfExperience(int min_years_of_experience) {
        SearchByMinYOEImpl searchByMinYOE = new SearchByMinYOEImpl();
        this.searchResult = searchByMinYOE.search(this.dao.getList(), 5, 50, "DEV", min_years_of_experience);
        System.out.println("Search by profession results:" + this.searchResult);
    }

    public boolean save() {
        return this.dao.writeListToFile(this.dao.getList(), "time");
    }

    public void removePerson(Person p) {
        this.dao.remove(p);
    }

//    public void removePerson(String name, int age, String id, String profession, int experience_y) {
//    }
}