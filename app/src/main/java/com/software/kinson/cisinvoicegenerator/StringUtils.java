package com.software.kinson.cisinvoicegenerator;

public class StringUtils {
    private boolean maxlength = false;
    private int modified = 0;

    public static String replaceText(String text) {
        boolean maxlength = false;

        StringBuilder sb = new StringBuilder();
        String tmpStr;
        sb.append(text);
        text = sb.toString();

        if(!maxlength){
            if(text.length() > 8){
                maxlength = true;
            }
            if (!maxlength) {
                text.toUpperCase();

                if(text.length() == 1){
                    if (text.matches("[Q,V,X,q,v,x]")) {
                        removeLastChar(text);
                    }
                    if (text.length() == 1 && !Character.isLetter(text.charAt(text.length() - 1))) {
                        removeLastChar(text);
                    }
                }
                if(text.length() == 2) {
                    if (text.matches("[I,J,Z,i,j,z]")) {
                        removeLastChar(text);
                    }
                }
                if(text.length() == 3) {
                    if (text.matches("[^A,B,C,D,E,F,G,H,J,K,S,T,U,W,a,b,c,d,e,f,g,h,j,k,s,t,u,w]")
                            && Character.isLetter(text.charAt(text.length() - 1))) {
                        removeLastChar(text);
                    }
                    if (Character.isLetter(text.charAt(text.length() - 2))
                            && Character.isLetter(text.charAt(text.length() - 1))) {
                        removeLastChar(text);
                    }
                }
                if(text.length() == 4) {
                    if (Character.isDigit(text.charAt(text.length() - 3))
                            && Character.isLetter(text.charAt(text.length() - 2))
                            && Character.isLetter(text.charAt(text.length() - 1))) {
                        removeLastChar(text);
                    }
                    if (Character.isSpaceChar(text.charAt(text.length() - 2))
                            && Character.isLetter(text.charAt(text.length() - 1))) {
                        removeLastChar(text);
                    }
                    if (Character.isDigit(text.charAt(text.length() - 2))
                            && Character.isLetter(text.charAt(text.length() - 1))
                            && !Character.isLetter(text.charAt(text.length() - 3))) {
                        insertSpace(1, text, ' ');
                    }
                    if (Character.isDigit(text.charAt(text.length() - 2))
                            && Character.isDigit(text.charAt(text.length() - 3))
                            && Character.isLetter(text.charAt(text.length() - 1))) {
                        insertSpace(1, text, ' ');
                    }
                    if (Character.isDigit(text.charAt(text.length() - 3))
                            && Character.isDigit(text.charAt(text.length() - 2))
                            && Character.isDigit(text.charAt(text.length() - 1))) {
                        insertSpace(1, text, ' ');
                    }
                    if (Character.isDigit(text.charAt(text.length() - 1))
                            && Character.isLetter(text.charAt(text.length() - 2))
                            && Character.isDigit(text.charAt(text.length() - 3))) {
                        insertSpace(1, text, ' ');
                    }
                }
                if(text.length() == 5) {
                    if (text.matches("[C,I,K,M,O,V,c,i,k,m,o,v]")) {
                        removeLastChar(text);
                    }
                    if (Character.isLetter(text.charAt(text.length() - 1))
                            && Character.isDigit(text.charAt(text.length() - 2))
                            && Character.isDigit(text.charAt(text.length() - 3))){
                        insertSpace(1, text, ' ');
                    }
                    if (Character.isDigit(text.charAt(text.length() - 1))
                            && Character.isLetter(text.charAt(text.length() - 2))
                            && Character.isDigit(text.charAt(text.length() - 3))){
                        insertSpace(1, text, ' ');
                    }
                    if (Character.isLetter(text.charAt(text.length() - 1))
                            && Character.isLetter(text.charAt(text.length() - 3))
                            && Character.isLetter(text.charAt(text.length() - 4))) {
                        insertSpace(1, text, ' ');
                    }
                    if (Character.isDigit(text.charAt(text.length() - 3))
                            && Character.isDigit(text.charAt(text.length() - 2))
                            && Character.isDigit(text.charAt(text.length() - 1))) {
                        insertSpace(1, text, ' ');
                    }
                    if (Character.isDigit(text.charAt(text.length() - 3))
                            && Character.isLetter(text.charAt(text.length()-2))){
                        removeLastChar(text);
                    }
                }
                if(text.length() == 6) {
                    if (text.matches("[C,I,K,M,O,V,c,i,k,m,o,v]")) {
                        removeLastChar(text);
                    }
                    if (Character.isDigit(text.charAt(text.length() - 2))
                            && Character.isSpaceChar(text.charAt(text.length() - 3))
                            && Character.isDigit(text.charAt(text.length() - 1))) {
                        removeLastChar(text);
                    }
                    if (Character.isSpaceChar(text.charAt(text.length() - 3))
                            && Character.isDigit(text.charAt(text.length() - 1))
                            && Globals.modified == 0) {
                        removeLastChar(text);
                        maxlength = true;
                    }
                    if (Character.isDigit(text.charAt(text.length() - 3))
                            && Character.isDigit(text.charAt(text.length() - 2))
                            && Character.isDigit(text.charAt(text.length() - 1))) {
                        removeLastChar(text);
                    }
                    if(Character.isLetter(text.charAt(text.length() - 1))
                            && Character.isLetter(text.charAt(text.length() - 2))){
                        maxlength = true;
                    }
                }
                if(text.length() == 7) {
                    if (text.matches("[C,I,K,M,O,V,c,i,k,m,o,v]")) {
                        removeLastChar(text);
                    }
                    if (Character.isDigit(text.charAt(text.length() - 1))) {
                        removeLastChar(text);
                    }
                    else{
                        if(Character.isLetter(text.charAt(text.length() - 1))
                                && Character.isLetter(text.charAt(text.length() - 2))){
                            maxlength = true;
                        }
                    }
                }
            }
            if(text.length() == 8) {
                if (text.matches("[C,I,K,M,O,V,c,i,k,m,o,v]")) {
                    removeLastChar(text);
                }
                if (Character.isDigit(text.charAt(text.length() - 1))) {
                    removeLastChar(text);
                }else {
                    maxlength = true;
                }
            }
            if(text.length() == 9) {
                maxlength = true;
            }
            text.toUpperCase();
        }
        return text.toUpperCase();
    }


    private static String removeLastChar(String str) {
        return str.substring(0, str.length() - 1);
    }

    private static String insertSpace(int distance, String original, char c){
        StringBuilder sb = new StringBuilder();
        char[] charArrayOfOriginal = original.toCharArray();
        for(int ch = 0 ; ch < charArrayOfOriginal.length ; ch++){
            if(ch % distance == 0)
                sb.append(c).append(charArrayOfOriginal[ch]);
            else
                sb.append(charArrayOfOriginal[ch]);
        }
        return sb.toString();
    }

    public String capitalise(String word){
        StringBuffer sb = new StringBuffer();
        System.out.println("Into capitalise");
        if(word.length() < 1){
            return "";
        }

        if(word == null || word == "")  {
            return "";
        }
        String[] arr = word.split(" ");

        for (int i = 0; i < arr.length; i++) {
            sb.append(Character.toUpperCase(arr[i].charAt(0)))
                    .append(arr[i].substring(1).toLowerCase()).append(" ");
        }

        return sb.toString();
    }
}
