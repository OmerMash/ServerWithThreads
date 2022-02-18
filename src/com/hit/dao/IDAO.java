//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.hit.dao;

import com.hit.algorithm.Person;
import java.util.List;

public interface IDAO {
    List<Person> getList();

    boolean writeListToFile(List<Person> var1, String var2);

    void add(Person var1);

    boolean remove(Person var1);
}
