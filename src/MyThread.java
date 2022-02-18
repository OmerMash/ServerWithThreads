import com.google.gson.Gson;
import com.hit.algorithm.Person;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


class MyThread implements Runnable {
    String myName;
    Socket mySocket;
    MyModel myModel;
    DataInputStream din;
    DataOutputStream dout;
    Gson gson = new Gson();

    MyThread(String name, Socket socket, MyModel model){
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
                PersonDTO personDTO = this.gson.fromJson(str, PersonDTO.class);
                Person person = this.gson.fromJson(str, Person.class);

                switch(personDTO.getOperation()){
                    case ADD -> this.myModel.searchControllerService.addPerson(personDTO.getName(), personDTO.getAge(), personDTO.getID(), personDTO.getProfession(), personDTO.getExperience_y());
                    case REMOVE -> this.myModel.searchControllerService.removePerson(person);
                    case SEARCH_BY_PROFESSION -> {
                        this.myModel.searchControllerService.searchByProfession(personDTO.getProfession());
                    }
                    case SEARCH_BY_MIN_YEARS_OF_EXPERIENCE -> this.myModel.searchControllerService.searchByMinYearsOfExperience(personDTO.getMin_years_of_experienc());
                    case SEARCH_BY_AGE -> this.myModel.searchControllerService.searchByAge(personDTO.getMin_age(), personDTO.getMax_age()== 0 ? 100 : personDTO.getMax_age());
                }

                System.out.println("client says: " + str);
                System.out.println(myName + ": " + myModel.GetData());
                dout.writeUTF(myName + ": " + myModel.GetData() + "\n");
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
