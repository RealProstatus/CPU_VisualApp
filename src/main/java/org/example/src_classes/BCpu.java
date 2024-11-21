package org.example.src_classes;

public class BCpu {
    public static ICPU build(){
        Handler h = new Handler();
        h.add(new Summator()).add(new Multiplier());

        return new CPU(h);
    }
}