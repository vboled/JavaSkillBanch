package ru.skillbench.tasks.text;

import java.time.Period;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ContactCardImpl implements ContactCard {
    private String fullName = null;
    private String orgName = null;
    private Calendar birthDay = null;
    private String phoneType = null;
    private String phoneNumber = null;

    private void parseBirthDay(String str) {
        String[] sp = str.split("-");
        if (sp.length != 3 || sp[0].length() != 2 || sp[1].length() != 2 || sp[2].length() != 4)
            throw new InputMismatchException();
        int day, month, year;
        try {
            day = Integer.parseInt(sp[0]);
            month = Integer.parseInt(sp[1]);
            year = Integer.parseInt(sp[2]);
        } catch (Exception e) {
            throw new InputMismatchException();
        }
        birthDay = new GregorianCalendar(year, month, day);
    }

    private void parseLine(String[] sp) {
        if (sp.length != 2)
            throw new InputMismatchException();
        if (sp[0].equals("FN")) {
            fullName = sp[1];
        } else if (sp[0].equals("ORG")) {
            orgName = sp[1];
        } else if (sp[0].equals("BDAY")) {
            parseBirthDay(sp[1]);
        } else if (sp[1].length() == 10) {
            parsePhoneNumber(sp);
        }
    }

    private void parsePhoneNumber(String[] sp) {
        try {
            Integer.parseInt(sp[1]);
        } catch (Exception e) {
            throw new InputMismatchException("!!!!");
        }
//        phoneNumber = sp[1];
        System.out.println(sp[1]);
    }

    @Override
    public ContactCard getInstance(Scanner scanner) {
        String line = scanner.nextLine();
        line = scanner.nextLine();
        while (!line.equals("END:VCARD")) {
            parseLine(line.split(":"));
            line = scanner.nextLine();
        }
        return new ContactCardImpl();
    }

    @Override
    public ContactCard getInstance(String data) {
        return null;
    }

    @Override
    public String getFullName() {
        return null;
    }

    @Override
    public String getOrganization() {
        return null;
    }

    @Override
    public boolean isWoman() {
        return false;
    }

    @Override
    public Calendar getBirthday() {
        return null;
    }

    @Override
    public Period getAge() {
        return null;
    }

    @Override
    public int getAgeYears() {
        return 0;
    }

    @Override
    public String getPhone(String type) {
        return null;
    }
}
