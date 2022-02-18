import com.hit.controller.SearchControllerService;

import java.time.LocalTime;

class MyModel {

    SearchControllerService searchControllerService = new SearchControllerService();

    String add(){
        return null;
    }


    String GetData(){
//        this.searchControllerService.addPerson();


        return LocalTime.now().toString();
    }

}
