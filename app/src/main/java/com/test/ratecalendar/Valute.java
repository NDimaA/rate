package com.test.ratecalendar;

/**
 * Created by Пользователь on 13.02.2016.
 */

public class Valute {

    public String ID;
    public String Name;
    public String NumCode;
    public String CharCode;
    public String Nominal;
    public String Value;

    public void setNumCode(final String NumCode) {
        this.NumCode = NumCode;
    }
    public void setCharCode(final String CharCode) {
        this.CharCode = CharCode;
    }
    public void setNominal(final String Nominal) {
        this.Nominal = Nominal;
    }
    public void setName(final String Name) {
        this.Name = Name;
    }
    public void setValue(final String Value) {
        this.Value = Value;
    }

    public String getID() {
        return ID;
    }

    public String getNumCode() {
        return NumCode;
    }

    public String getCharCode() {
        return CharCode;
    }

    public String getNominal() {
        return Nominal;
    }

    public String getName() {
        return Name;
    }

    public String getValue() {
        return Value;
    }

    @Override
    public String toString() {
        return "Valute{" +
                "ID='" + ID + '\'' +
                ", Name='" + Name + '\'' +
                ", NumCode='" + NumCode + '\'' +
                ", CharCode='" + CharCode + '\'' +
                ", Nominal='" + Nominal + '\'' +
                ", Value='" + Value + '\'' +
                '}';
    }
}
