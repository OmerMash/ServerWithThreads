package com.hit;

import com.google.gson.Gson;
import com.hit.algorithm.IAlgoSearch;
import com.hit.algorithm.Person;
import com.hit.algorithm.SearchByAgeImpl;
import com.hit.dao.IDAO;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


class HandleRequest implements Runnable {
    String myName;
    Socket mySocket;
    IDAO myModel;
    DataInputStream din;
    DataOutputStream dout;
    Gson gson = new Gson();
    IAlgoSearch algoSearch;

    HandleRequest(String name, Socket socket, IDAO model){
        myName=name;
        mySocket=socket;
        myModel = model;
        try{
            din=new DataInputStream(socket.getInputStream());
            dout=new DataOutputStream(socket.getOutputStream());
        }
        catch(IOException exception){}
    }

    public void run(){
        String str="";
        while(!str.equals("stop")){
            try {
                str = din.readUTF();
                if(str.startsWith("ADD")){
                    str = str.substring(3);
                    str = str.replace("{", "");
                    str = str.replace("}", "");
                    str = str.replace("_", " ");
                    str = str.replace("name:", "");
                    str = str.replace("age:", "");
                    str = str.replace("ID:", "");
                    str = str.replace("profession:", "");
                    str = str.replace("experience y:", "");
                    str = str.replace("operation:", "");
                    str = str.replace("min", "");
                    str = str.replace("max", "");
                    str = str.replace("years of experience:", "");
                    System.out.println("STR content: " + str);
                    String[] t = str.split(",");
                    System.out.println("t length: " + t.length);
                    Person p = new Person(t[0],100,t[2],t[3],200);
                    myModel.add(p);
                    myModel.save();
                }
                else if(str.startsWith("REMOVE")){
                    str = str.replace("REMOVE", "");
                    str = str.replace("\n", "");
                    System.out.println("str content: " + str);
                    myModel.removePerson(str);
                }
                algoSearch = new SearchByAgeImpl();
                List<Person> list = algoSearch.search(myModel.getList(),0,1000,"",0);
                String s = "";
                for(Person p : list){
                    s += ("Name: " + p.getName() + ", Age:" + p.getAge() + ", Profession: " + p.getProfession() + ", Experience: " + p.getExperience_y() + "\n");
                }
                dout.writeUTF(s);
                dout.flush();

                List<Person> personList = new ArrayList<Person>();
//                switch(person.getOperation()){
////                    case ADD -> personList.add(this.myModel.searchControllerService.addPerson(person.getName(), person.getAge(), person.getID(), person.getProfession(), person.getExperience_y()));
//                    case ADD -> this.myModel.addPerson(person.getName(), person.getAge(), person.getID(), person.getProfession(), person.getExperience_y());
//                    case REMOVE -> this.myModel.removePerson(person);
//                    case SEARCH_ALL -> {
//                        personList = this.myModel.searchByProfession(person.getProfession());
//                        List<Person> personList1 = this.myModel.searchByMinYearsOfExperience(person.getMin_years_of_experienc());
//                        personList = personList.stream().filter(a -> personList1.contains(a)).collect(Collectors.toList());
//                        List<Person> personList2 = this.myModel.searchByAge(person.getMin_age(), person.getMax_age() == 0 ? 100 : person.getMax_age());
//                        personList = personList.stream().filter(b -> personList2.contains(b)).collect(Collectors.toList());
//                    }
//                    case SEARCH_BY_PROFESSION -> {
//                        personList = this.myModel.searchByProfession(person.getProfession());
//                        if(person.getMin_years_of_experienc() != 0){
//                            List<Person> collect = personList.stream().filter(a -> this.myModel.searchByMinYearsOfExperience(person.getMin_years_of_experienc()).contains(a)).collect(Collectors.toList());
//                            personList = collect;
//                        }
//                        if(person.getMin_age() != 0){
//                            personList = personList.stream().filter(a -> this.myModel.searchByAge(person.getMin_age(), person.getMax_age() == 0 ? 100 : person.getMax_age()).contains(a)).collect(Collectors.toList());
//                        }
//                    }
//                    case SEARCH_BY_MIN_YEARS_OF_EXPERIENCE ->{
//                        personList = this.myModel.searchByMinYearsOfExperience(person.getMin_years_of_experienc());
//                        if(person.getMin_age() != 0){
//                            personList = personList.stream().filter(a -> this.myModel.searchByAge(person.getMin_age(), person.getMax_age() == 0 ? 100 : person.getMax_age()).contains(a)).collect(Collectors.toList());
//                        }
//                    }
//                    case SEARCH_BY_AGE -> {
//                        personList = this.myModel.searchByAge(person.getMin_age(), person.getMax_age()== 0 ? 100 : person.getMax_age());
//                    }
//                }
                System.out.println("client says: " + str);
                System.out.println(myName + ": " + myModel.GetData());
                dout.writeUTF(myName + ": " + Arrays.toString(personList.toArray()) + "\n");
                dout.flush();
            }
            catch(IOException exception){}
        }
        try {
            din.close();
            dout.close();
            mySocket.close();
        }
        catch(IOException exception){}
    }
}
