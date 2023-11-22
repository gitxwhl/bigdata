package com.mstdemo.mst;

import com.mstdemo.mst.service.PetInterface;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MstApplicationTests {

    @Autowired
    @Qualifier(value = "dogPetinterfaceImpl")
    private PetInterface petInterface;


    @Test
    void contextLoads() {
        petInterface.playBall();
    }

}
