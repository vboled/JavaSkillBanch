package ru.skillbench.tasks.text;

import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.util.*;

public class ContactCardImpl implements ContactCard {
    private String fullName = null;
    private String orgName = null;
    private Calendar birthDay = null;
    private String gender = null;
    private Map<String, String> phones = new HashMap<String, String>();

    private void parseBirthDay(String str) {
        String[] sp = str.split("-");
        if (sp.length != 3 || sp[0].length() != 2 || sp[1].length() != 2 || sp[2].length() != 4)
            throw new InputMismatchException();
        int day, month, year;
        try {
            day = Integer.parseInt(sp[0]);
            month = Integer.parseInt(sp[1]) - 1;
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
        } else if (sp[0].equals("GENDER")) {
            if (!sp[1].equals("M") && !sp[1].equals("F"))
                throw new InputMismatchException();
            else
                gender = sp[1];
        } else if (sp[1].length() == 10) {
            parsePhoneNumber(sp);
        } else {
            throw new InputMismatchException();
        }
    }

    private void parsePhoneNumber(String[] sp) {
        try {
            Long.parseLong(sp[1]);
        } catch (Exception e) {
            throw new InputMismatchException();
        }
        String[] split1 = sp[0].split("=");
        if (split1.length != 2)
            throw new InputMismatchException();
        String[] split11 = split1[0].split(";");
        String[] split12 = split1[1].split(",");
        if (split11.length != 2 || split12.length != 2 || !split11[0].equals("TEL") ||
            !split11[1].equals("TYPE") || !split12[1].equals("VOICE")) {
            throw new InputMismatchException();
        }
        phones.put(split12[0], sp[1]);
    }

    @Override
    public ContactCard getInstance(Scanner scanner) {
        String line = scanner.nextLine();
        line = scanner.nextLine();
        while (!line.equals("END:VCARD")) {
            parseLine(line.split(":"));
            line = scanner.nextLine();
        }
        if (fullName == null || orgName == null)
            throw new InputMismatchException();
        return new ContactCardImpl();
    }

    @Override
    public ContactCard getInstance(String data) {
        return getInstance(new Scanner(data));
    }

    @Override
    public String getFullName() {
        return fullName;
    }

    @Override
    public String getOrganization() {
        return orgName;
    }

    @Override
    public boolean isWoman() {
        if (gender == null || gender.equals("M"))
            return false;
        return true;
    }

    @Override
    public Calendar getBirthday() {
        if (birthDay == null)
            throw new NoSuchElementException();
        return birthDay;
    }

    @Override
    public Period getAge() {
        if (birthDay == null)
            throw new NoSuchElementException();
        Calendar now = Calendar.getInstance();
        LocalDate start = LocalDate.of(birthDay.get(Calendar.YEAR), birthDay.get(Calendar.MONTH),
                birthDay.get(Calendar.DAY_OF_MONTH));
        LocalDate end = LocalDate.of(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
        return Period.between(start, end);
    }

    @Override
    public int getAgeYears() {
        if (birthDay == null)
            throw new NoSuchElementException();
        return getAge().getYears();
    }

    @Override
    public String getPhone(String type) {
        if (phones.get(type) == null)
            throw new NoSuchElementException();
        return phones.get(type);
    }
}
