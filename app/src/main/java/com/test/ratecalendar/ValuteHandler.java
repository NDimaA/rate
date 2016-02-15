package com.test.ratecalendar;

import android.annotation.TargetApi;
import android.os.Build;
import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * Created by Пользователь on 13.02.2016.
 */
public class ValuteHandler extends DefaultHandler {

    Boolean currentElement = false;
    String currentValue = "";
    Valute valute = null;

    private ArrayList<Valute> valList = new ArrayList<Valute>();

    public ArrayList<Valute> getValList() {
        return valList;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        //Log.e("start local name", "--> " + localName + "  q name -->" + qName + "--" + currentElement + "--" + currentValue);
        if (localName.equals("Valute")) {
            valute = new Valute();
        } else if (localName.equals("NumCode") || localName.equals("CharCode") ||
                localName.equals("Nominal") || localName.equals("Name") ||
                localName.equals("Value")) {
            currentElement = true;
            currentValue = "";
        }
        Log.e("start local name", "--> " + localName + "  q name -->" + qName + "--" + currentElement + "--" + currentValue);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        currentElement = false;
        Log.e("end local name", "--> " + localName + "  q name -->" + qName + "--" + currentElement + "--" + currentValue);
        /** set value */
        if (localName.equalsIgnoreCase("NumCode"))
            valute.setNumCode(currentValue);
        else if (localName.equalsIgnoreCase("CharCode"))
            valute.setCharCode(currentValue);
        else if (localName.equalsIgnoreCase("Nominal"))
            valute.setNominal(currentValue);
        else if (localName.equalsIgnoreCase("Name")) {
//            valute.setName(currentValue);
            valute.setName(new String(currentValue.getBytes(Charset.forName("UTF-8"))));
        }
        else if (localName.equalsIgnoreCase("Value"))
            valute.setValue(currentValue);
        else if (localName.equalsIgnoreCase("Valute"))
            valList.add(valute);
    }

    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {

        if (currentElement) {
            currentValue = currentValue +  new String(ch, start, length);
        }

    }
}
