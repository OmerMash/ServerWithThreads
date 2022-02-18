
package com.hit.dao;

import com.hit.algorithm.Person;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MyDaoFileImpl implements IDAO {
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private final List<Person> personList = new ArrayList();
    Object p;

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

    public List<Person> getList() {
        return this.personList;
    }

    public void add(Person p) {
        this.personList.add(p);
    }

    public boolean remove(Person p) {
        for(Person person : personList){
            if(person.getID() == p.getID()){
                personList.remove(person);
                return true;
            }
        }
        return false;
    }


}
