package tmjee.uts.coursework.atm;

import java.io.*;
import java.util.Arrays;
import java.util.List;

import static java.lang.String.format;

/**
 * @author tmjee
 */
public class Main {

    public static void main(String[] args) throws Exception {
        Console console = new Console();
        console.writeline("creating ATM ...");

        Atm.Builder atmBuilder = new Atm.Builder();

        for(Note note : Note.values()) {
            int q = console.readAsInt(format("quantities of %s (enter an integer followed by enter): ", note));
            atmBuilder.addNotes(note).withQuantityOf(q);
        }

        char multithread = console.readAsChar("multithread (y/n followed by enter)? ", Arrays.asList('y','n'));
        switch(multithread) {
            case 'y':
                atmBuilder.multiThreadCapable();
                break;
            case 'n':
                atmBuilder.singleThreadCapable();
                break;
        }

        Atm atm = atmBuilder.build();
        console.writeline("created atm ... ok");

        boolean end = false;
        do {
            char option = console.readAsChar("s for atm summary, w for withdrawal, e to end followed by enter: ", Arrays.asList('s', 'w', 'e'));
            switch (option) {
                case 's':
                    Summary summary = atm.summary();
                    console.write(summary.toString());
                    console.writeline("");
                    break;
                case 'w':
                    int sum = console.readAsInt("sum to withdraw (an integer followed by enter)? ");
                    Withdrawal withdrawal = atm.withdraw(sum);
                    console.write(withdrawal.toString());
                    console.writeline("");
                    break;
                case 'e':
                    console.writeline("Bye! ...");
                    end = true;
            }
        } while (!end);
    }



    static class Console {
        PrintWriter writer;
        BufferedReader reader;
        Console() {
            reader = new BufferedReader(new InputStreamReader(System.in));
            writer = new PrintWriter(System.out);
        }
        void write(String s) {
            writer.print(s);
            writer.flush();
        }
        void writeline(String s) {
            writer.println(s);
            writer.flush();
        }
        String read(String s) throws IOException {
            writer.println();
            write(s);
            return reader.readLine();
        }
        char readAsChar(String s, List<Character> c) throws IOException {
            boolean valid = false;
            String str = null;
            do {
                write(s);
                str = reader.readLine();
                final String iStr = str;
                valid = c.stream().anyMatch((i)->Character.toString(i).equalsIgnoreCase(iStr));
                if (! valid) {
                    writeline(format("invalid input must be either %s", c));
                }
            } while(!valid);
            return str.charAt(0);
        }
        int readAsInt(String s) throws IOException {
            int i = 0;
            boolean valid = false;
            do {
                write(s);
                String str = reader.readLine();
                try {
                    i = Integer.parseInt(str);
                    if (i < 0) {
                        valid = false;
                        writeline(format("Invalid input %s is not a positive integer", s));
                    }
                    valid = true;
                } catch (NumberFormatException e) {
                    valid = false;
                    writeline(format("Invalid input %s is not a positive integer", s));
                }
            } while (!valid);
            return i;
        }
    }
}
