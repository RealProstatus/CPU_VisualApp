package org.example.src_classes;

public class Handler {
    private Handler next;

    public void start(Command c, CPU cpu) throws Exception {
        switch (c.getTask()) {
            case "ld" -> cpu.load(c.getSec(), c.getFst());
            case "st" -> cpu.store(c.getFst(), c.getSec());
            case "mv" -> cpu.move(c.getFst(), c.getSec());
            case "init" -> cpu.addToRam(c.getFst(), c.getSec());
            case "print" -> System.out.println(cpu);
            default -> {
                if (next != null) {
                    next.start(c, cpu);
                } else {
                    throw new Exception("Cannot solve");
                }
            }
        }
    }

    public Handler add(Handler h){
        next = h;
        return h;
    }
}
