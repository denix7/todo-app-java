package com.todo.app.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.*;

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

    public static String[] fragment2(String args) {
        String[] res = new String[3];
        res[0] = "Invalid";
        //Pattern p = Pattern.compile("todo\\sadd\\s\\\"([a-zA-Z0-9\\s]+)\\\"( priority : (L?M?H?))?");
        Pattern p = Pattern.compile("todo\\s(add)\\s\"([a-zA-Z0-9\\s]+)\\\"( priority\\s?:\\s?)?(L?M?H?)?");
        Matcher m = p.matcher(args);

        List<String> tokens = new ArrayList<String>();
        if(args.contains("priority"))
        {

            int i = 1;
            while(m.find()){
                tokens.add(m.group(1));
                tokens.add(m.group(2));
                tokens.add(m.group(4));
            }
        }
        else {
            int i = 1;
            while (m.find()) {
                tokens.add(m.group(1));
                tokens.add(m.group(2));
            }
        }

        if(tokens.size()>0)
        {
            String[] argsArray = new String[tokens.size()];
            argsArray = tokens.toArray(argsArray);
            System.out.println(Arrays.toString(argsArray));
            return argsArray;

        }
        return res;
    }
}