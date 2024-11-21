package org.example.src_classes;

import java.util.HashMap;
import java.util.Map;

public class CPU implements ICPU{

    public static final int RAM_CAPACITY = 1024;
    public static final int REGISTER_CAPACITY = 4;

    private final int[] register;                                                  // регистр процессора
    private final int[] ram;                                                       // имитация оперативной памяти
    private Handler hndlr;

    public CPU(Handler h){
        register = new int[REGISTER_CAPACITY];
        ram = new int[RAM_CAPACITY];
        hndlr = h;
    }


    private boolean checkIndRam(int indRam){
        return !((indRam<0)||(indRam>RAM_CAPACITY-1));
    }
    private boolean checkIndReg(int indReg){
        return !((indReg<0)||(indReg>REGISTER_CAPACITY-1));
    }


    public void addToRam(int ramAddr, int value){          //загрузка значений в оперативную память
        if(!checkIndRam(ramAddr))
            throw new IndexOutOfBoundsException("RAM address error");

        ram[ramAddr] = value;
    }
    public void load(int ramAddr, int registerNum){        // загрузка данных в регистр из адреса оперативной памяти
        if(!checkIndReg(registerNum))
            throw new IndexOutOfBoundsException("Register address error");
        if(!checkIndRam(ramAddr))
            throw new IndexOutOfBoundsException("RAM address error");

        register[registerNum] = ram[ramAddr];
    }
    public void store(int registerNum, int ramAddr){       // выгрузка данных из регистра в оперативную память по адресу
        if(!checkIndReg(registerNum))
            throw new IndexOutOfBoundsException("Register address error");
        if(!checkIndRam(ramAddr))
            throw new IndexOutOfBoundsException("RAM address error");

        ram[ramAddr] = register[registerNum];
    }
    public void add() {                                    //сложение значений по адресам в регистрах
        register[3] = register[0] + register[1];
    }
    public void sub(){                                     //вычитание значений по адресам в регистрах
        register[3] = register[0] - register[1];
    }
    public void mul(){                                     //перемножение значений по адресам в регистрах
        register[3] = register[0] * register[1];
    }
    public void div(){                                     //деление значений по адресам в регистрах
        if (register[1]==0) throw new ArithmeticException("Divide by zero");
        register[3] = register[0] / register[1];
    }
    public void move(int regAddr1, int regAddr2){          //перемещение значений в регистрах
        if(checkIndReg(regAddr1)&&checkIndReg(regAddr2)){
            register[regAddr1] = register[regAddr2];
        }else{
            throw new IndexOutOfBoundsException("Register address error");
        }
    }

    @Override
    public String toString() {
        StringBuilder tmp = new StringBuilder();
        for(int i=0;i<REGISTER_CAPACITY;i++){
            tmp.append("R" + i + ":" + register[i]+'\n');
        }
        return tmp.toString();
    }

    @Override
    public void run(Command c) {
        try{
            hndlr.start(c,this);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Map<Integer, Integer> getRegisterInfo() {
        Map<Integer, Integer> res = new HashMap<Integer, Integer>();
        res.put(0,register[0]); res.put(1,register[1]);
        res.put(2,register[2]); res.put(3,register[3]);
        return res;
    }

    @Override
    public Map<Integer, Integer> getRamInfo() {
        Map<Integer, Integer> res = new HashMap<Integer, Integer>();
        for(int i = 0; i < 25; i++){
            res.put(i, ram[i]);
        }
        return res;
    }
}
