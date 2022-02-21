package com.hit;

import com.google.gson.Gson;
import com.hit.algorithm.*;
import com.hit.dao.IDAO;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
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

    private String jsonToString(String jsonToClean){
        jsonToClean = jsonToClean.replace("{", "");
        jsonToClean = jsonToClean.replace("}", "");
        jsonToClean = jsonToClean.replace("_", " ");
        jsonToClean = jsonToClean.replace("name:", "");
        jsonToClean = jsonToClean.replace("age:", "");
        jsonToClean = jsonToClean.replace("ID:", "");
        jsonToClean = jsonToClean.replace("profession:", "");
        jsonToClean = jsonToClean.replace("experience y:", "");
        jsonToClean = jsonToClean.replace("operation:", "");
        jsonToClean = jsonToClean.replace("min", "");
        jsonToClean = jsonToClean.replace("max", "");
        jsonToClean = jsonToClean.replace("years of experience:", "");
        return jsonToClean;
    }

    public void run(){
        String[] splited_str = null;
        String str="";
        while(!str.equals("stop")){
            try {
                str = din.readUTF();
                if(str.startsWith("ADD")){
                    str = str.substring(3);
                    str = jsonToString(str);
//                    str = str.replace("{", "");
//                    str = str.replace("}", "");
//                    str = str.replace("_", " ");
//                    str = str.replace("name:", "");
//                    str = str.replace("age:", "");
//                    str = str.replace("ID:", "");
//                    str = str.replace("profession:", "");
//                    str = str.replace("experience y:", "");
//                    str = str.replace("operation:", "");
//                    str = str.replace("min", "");
//                    str = str.replace("max", "");
//                    str = str.replace("years of experience:", "");
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
                else if(str.startsWith("SEARCH")){
                    str = str.replace("SEARCH", "");
                    str = str.replace("\n", "");
                    splited_str = str.split(",");
//                    if(splited_str[2] == ""){
//                        this.algoSearch = new SearchByProfessionImpl();
//                    }
                    if(splited_str.length == 1){
                        this.algoSearch = new SearchByProfessionImpl();
                    }
                    else if(splited_str.length == 2) {
                        this.algoSearch = new SearchByMinYOEImpl();
                    }
                    else if(splited_str.length == 4){
                        this.algoSearch = new SearchByAgeImpl();
                    }
                }
                String profession = "";
                Integer experience = 0;
                Integer min_age = 0;
                Integer max_age = 100;
                if(splited_str.length == 1){
                    profession = splited_str[0];
                    experience = 0;
                    min_age = 0;
                    max_age = 100;
                }
                else if(splited_str.length == 2){
                    profession = "";
                    experience = Integer.parseInt(splited_str[1]);
                    min_age = 0;
                    max_age = 100;
                }
                else if(splited_str.length == 4){
                    profession = "";
                    experience = 0;
                    min_age = Integer.parseInt(splited_str[2]);
                    max_age = Integer.parseInt(splited_str[3]);
                }
                List<Person> list = algoSearch.search(myModel.getList(),min_age,max_age,profession,experience);
                String s = "";
                for(Person p : list){
                    s += ("Name: " + p.getName() + ", Age:" + p.getAge() + ", Profession: " + p.getProfession() + ", Experience: " + p.getExperience_y() + "\n");
                }
                dout.writeUTF(s);
                dout.flush();

//                List<Person> personList = new ArrayList<Person>();
//                System.out.println("client says: " + str);
//                System.out.println(myName + ": " + myModel.GetData());
//                dout.writeUTF(myName + ": " + Arrays.toString(personList.toArray()) + "\n");
//                dout.flush();
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
