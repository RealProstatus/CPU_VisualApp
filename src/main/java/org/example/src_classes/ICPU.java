package org.example.src_classes;

import java.util.Map;

public interface ICPU {
    void run(Command c);
    Map<Integer,Integer> getRegisterInfo();
    Map<Integer,Integer> getRamInfo();
}
