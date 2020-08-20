package com.todo.app.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.*;

public class Fragmenter {
    public static String[] fragmentSimple(String args) {
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
        //System.out.println("frag" + Arrays.toString(argsArray));
        return argsArray;
    }

    public static String[] fragment(String args) {
        String[] res = new String[1];
        res[0] = "Invalid";
        String regex = "";
        if(args.contains("add"))
        {
            regex = "todo\\s(add)\\s\"([a-zA-Z0-9\\s]+)\\\"( priority\\s?:\\s?)?(L?M?H?)?";
            return commandAddPattern(regex, args);
        }
        if(args.contains("todo list"))
        {
            if(args.contains("tag"))
            {
                int point = args.indexOf(':') + 1;
                return fragmentSimple("list " + args.substring(point));
            }
            regex = "";
            return fragmentSimple(args.substring(5));

        }
        if(args.contains("todo done"))
        {
            if(args.contains("tag"))
            {
                int point = args.indexOf(':') + 1;
                return fragmentSimple("done " + args.substring(point));
            }
            return fragmentSimple(args.substring(5));
        }
        if(args.contains("todo modify"))
        {
            if(args.contains("tag"))
            {
                int tagName = args.indexOf(':') + 1;
                //int newDescription = args.indexOf(' ')+1;
                //System.out.println(args.substring(newDescription));
                return fragmentSimple("modify " + args.substring(tagName));
            }
            regex = "";
            return fragmentSimple(args.substring(5));
        }

        return res;
    }

    private static String[] commandAddPattern(String regex, String args)
    {
        String[] res = new String[3];
        Pattern p = Pattern.compile(regex);
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
            //System.out.println(Arrays.toString(argsArray));
            return argsArray;

        }
        return res;
    }

    private static String[] commandListPattern(String regex, String args)
    {
        String[] res = new String[3];
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(args);

        List<String> tokens = new ArrayList<String>();

        int i = 1;
        while (m.find()) {
            tokens.add(m.group(i));
            tokens.add(m.group(i));
            System.out.println(m.group(1));
            System.out.println(m.group(2));
        }
        //System.out.println("list " + tokens.toString());

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