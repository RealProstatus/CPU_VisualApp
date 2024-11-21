package org.example.src_classes;

public class Summator extends Handler {
    @Override
    public void start(Command c, CPU cpu) throws Exception {
        switch (c.getTask()) {
            case "add" -> cpu.add();
            case "sub" -> cpu.sub();
            default -> super.start(c, cpu);
        }
    }

}
