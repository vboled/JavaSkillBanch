package ru.skillbench.tasks.text;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] argv) throws FileNotFoundException {
        ContactCard c = new ContactCardImpl();
        c.getInstance(new Scanner("BEGIN:VCARD\n" +
                "FN:Forrest Gump\n" +
                "ORG:Bubba Gump Shrimp Co.\n" +
                "GENDER:F\n" +
                "TEL;TYPE=WORK,VOICE:4951234567\n" +
                "TEL;TYPE=CELL,VOICE:9150123456\n" +
                "END:VCARD"));
        System.out.println(c.isWoman());
    }
}
