package org.example.src_classes;

public class Multiplier extends Handler{
    @Override
    public void start(Command c, CPU cpu) throws Exception {
        switch (c.getTask()) {
            case "mult" -> cpu.mul();
            case "div" -> {
                try {
                    cpu.div();
                } catch (ArithmeticException e) {
                    System.out.println(e.getMessage());
                }
            }
            default -> super.start(c, cpu);
        }
    }
}
