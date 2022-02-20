package com.hit;

import com.hit.algorithm.Person;
import com.hit.algorithm.SearchByAgeImpl;
import com.hit.algorithm.SearchByMinYOEImpl;
import com.hit.algorithm.SearchByProfessionImpl;
import com.hit.dao.IDAO;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class MyDaoFileImpl implements IDAO {
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private final List < Person > personList = new ArrayList();
    Object p;
    private List<Person> searchResult;

    public MyDaoFileImpl(String fileName) throws IOException {
        try {
            this.input = new ObjectInputStream(Files.newInputStream(Paths.get(fileName)));

            while(true) {
                Person p = (Person)this.input.readObject();
                this.p = this.input.readObject();
                this.personList.add(p);
            }
        } catch (IOException | ClassNotFoundException var3) {
        }
    }

    @Override
    public String GetData() {
        //        this.searchControllerService.addPerson();
        return LocalTime.now().toString();
    }

    @Override
    public List < Person > getList() {
        return this.personList;
    }

    @Override
    public boolean writeListToFile(List<Person> personsList, String time) {
        try {
            this.output = new ObjectOutputStream(Files.newOutputStream(Paths.get("person_ser")));
        } catch (IOException var8) {
            var8.printStackTrace();
        }

        Iterator var3 = personsList.iterator();

        while(var3.hasNext()) {
            Person p = (Person)var3.next();

            try {
                this.output.writeObject(p);
                this.output.writeObject(time);
                System.out.println(p + " - time: " + time);
            } catch (IOException var7) {
                var7.printStackTrace();
                return false;
            }
        }

        try {
            this.output.close();
        } catch (IOException var6) {
            var6.printStackTrace();
        }

        return true;
    }

        @Override
        public void add(Person p) {
            this.personList.add(p);
        }

        @Override
        public boolean removePerson(String p) {
            for (Person person: personList) {
                if (person.getID() == p) {
                    personList.remove(person);
                    return true;
                }
            }
            return false;
        }

    public List < Person > searchByMinYearsOfExperience(int min_years_of_experience) {
        SearchByMinYOEImpl searchByMinYOE = new SearchByMinYOEImpl();
        this.searchResult = searchByMinYOE.search(personList, 5, 50, "DEV", min_years_of_experience);
        System.out.println("Search by profession results:" + this.searchResult);
        return searchResult;
    }

    public List < Person > searchByAge(int min_age, int max_age) {
        SearchByAgeImpl search_by_age = new SearchByAgeImpl();
        this.searchResult = search_by_age.search(personList, min_age, max_age, "", 0);
        System.out.println("Search by Age results:" + this.searchResult);
        return searchResult;
    }

    public List<Person> searchByProfession(String profession) {
        SearchByProfessionImpl searchByProfession = new SearchByProfessionImpl();
        this.searchResult = searchByProfession.search(personList, 5, 50, profession, 0);
        System.out.println("Search by profession results:" + this.searchResult);
        return searchResult;
    }

    public boolean save() {
        return writeListToFile(personList, "time");
    }

    public Person addPerson(String name, int age, String id, String profession, int years_of_experience) throws IOException {
        Person p = new Person(name, age, id, profession, years_of_experience);
        personList.add(p);
        System.out.println(personList);
        System.out.println("Done adding person");
        if(save()){
            return p;
        }
        return null;
    }
}