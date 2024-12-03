package org.example.src_classes;

public class Command {
    private int id =-1;
    private String task;
    private int fst, sec;

    public Command(){
        task = null;
    }

    public Command(String inp){
        inp = inp.trim();
        if (inp.length()>5){
            String[] tmp = inp.split(" ");
            task = tmp[0];
            fst = Integer.parseInt(tmp[1]);
            sec = Integer.parseInt(tmp[2]);
        }
        else{
            task = inp;
            fst = sec = -1;
        }
    }

    public Command(String s1, String s2, String s3){
        task = s1;
        fst = Integer.parseInt(s2);
        sec = Integer.parseInt(s3);
    }

    public Command(String s1, String s2, String s3,int id){
        this.id = id;
        task = s1;
        fst = Integer.parseInt(s2);
        sec = Integer.parseInt(s3);
    }

    public String getTask() {
        return task;
    }
    public void setTask(String task) { this.task = task; }
    public void setFst(int fst) { this.fst = fst; }
    public void setFst(String fst) { this.fst = Integer.parseInt(fst); }
    public void setSec(int sec) { this.sec = sec; }
    public void setSec(String sec) { this.sec = Integer.parseInt(sec); }
    public int getFst() {
        return fst;
    }
    public int getSec() {
        return sec;
    }

    @Override
    public String toString() {
        return switch (task) {
            case "print" -> task;
            case "add" -> task;
            case "sub" -> task;
            case "mul" -> task;
            case "div" -> task;
            case "ld" -> task + " " + fst + " " + sec;
            case "st" -> task + " " + fst + " " + sec;
            case "mv" -> task + " " + fst + " " + sec;
            case "init" -> task + " " + fst + " " + sec;
            default -> "";
        };
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
