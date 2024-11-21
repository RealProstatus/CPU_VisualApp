package org.example.src_classes;

public class App
{
    public static void main( String[] args ){
        ICPU cpu = BCpu.build();
        Executor exec = new Executor(cpu);

        Program prog = new Program();
        prog.add(new Command("init 10 20"));
        prog.add(new Command("init 11 25"));
        prog.add(new Command("ld 0 10"));
        prog.add(new Command("ld 1 11"));
        prog.add(new Command("ld 2 11"));
        prog.add(new Command("st 0 1000"));
        prog.add(new Command("add"));
        prog.add(new Command("mv 0 3"));
        prog.add(new Command("add"));
        prog.add(new Command("print"));

        System.out.println("Вывод программы:");
        for(Command c: prog) System.out.println(c);
        System.out.println();


        prog.rangeOfAddresses();

        System.out.println(prog.mostPopCommand());
        System.out.println();

        System.out.println(prog.sortedListOfCommands());

        System.out.println("Выполнение программы:");
        for (Command c : prog) exec.execute(c);
    }



}
