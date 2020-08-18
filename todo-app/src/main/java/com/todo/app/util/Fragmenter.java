package com.todo.app.util;

import java.util.ArrayList;
import java.util.List;

public class Fragmenter {
    public static String[] fragment(String args) {
        List<String> tokens = new ArrayList<String>();
        char[] charArray = args.toCharArray();

        String contact = "";
        boolean inText = false;
        for (char current : charArray) {
            if (current == ' ' && !inText) {
                if (contact.length() != 0) {
                    tokens.add(contact);
                    contact = "";
                }
            } else if (current == '"') {
                if (inText) {
                    tokens.add(contact);
                    contact = "";
                    inText = false;
                } else {
                    inText = true;
                }
            } else {
                contact += current;
            }
        }
        if (contact.trim().length() != 0) {
            tokens.add(contact.trim());
        }

        String[] argsArray = new String[tokens.size()];
        argsArray = tokens.toArray(argsArray);
        return argsArray;
    }
}
