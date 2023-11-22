package com.mstdemo.mst.service.impl;

import com.mstdemo.mst.service.PetInterface;
import org.springframework.stereotype.Service;

@Service
public class DogPetinterfaceImpl implements PetInterface {
    @Override
    public void playBall() {
        System.out.println("狗玩球");
    }
}
