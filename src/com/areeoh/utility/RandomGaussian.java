package com.areeoh.utility;

import java.util.Random;

public class RandomGaussian {

    private final Random fRandom = new Random();

    public int getGaussian(double aMean, double aVariance){
        return (int) (aMean + fRandom.nextGaussian() * aVariance);
    }
}
