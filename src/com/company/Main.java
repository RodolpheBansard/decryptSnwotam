package com.company;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        String snowtam = "SWEN0311 ENSB 10130958\n" +
                "(SNOWTAM 0311\n" +
                "A) ENSB\n" +
                "B) 10130958 C) 10\n" +
                "F) 7/7/7 G) XX/XX/XX H) 4/4/3\n" +
                "N) ALL REPORTED TWYS/2\n" +
                "R) ALL REPORTED APRONS/2\n" +
                "T) CONTAMINATION/100/100/100/PERCENT.\n" +
                ")";


        Map<Character,String> snowtamDictionnary = getSnowtamDictionnary(snowtam);

        Iterator it = snowtamDictionnary.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            switch(pair.getKey().toString().charAt(0)){
                case 'A':

                    String airportName = getAirportName(pair.getValue().toString().replace(" ",""));
                    System.out.println(airportName);
                    break;
                case 'B':
                    String dateHour = getDateHour(pair.getValue().toString().replace(" ",""));
                    System.out.println(dateHour);
                    break;
                case 'C':

                    break;
                case 'D':

                    break;
                case 'E':

                    break;
                case 'F':

                    break;
                case 'G':

                    break;
                case 'H':

                    break;
                case 'I':

                    break;
                case 'K':

                    break;
                case 'L':

                    break;
                case 'M':

                    break;
                case 'N':

                    break;
                case 'O':

                    break;
                case 'Q':

                    break;
                case 'R':

                    break;
                case 'S':

                    break;
                case 'T':

                    break;
                default:

            }
            it.remove();
        }
    }






    public static Map<Character,String> getSnowtamDictionnary(String snowtam){
        // Expressions régulières pour le parse de la snowtam
        String delimiterLetter = "\\)";
        String delimiterContent = ".\\)";

        // Création d'un tableau pour les lettres et un tableau pour le contenu
        String[] tokensContent = snowtam.split(delimiterLetter);
        char[] tokensLetter = new char[tokensContent.length-1];
        for(int i=0; i <tokensLetter.length; i++) {
            tokensLetter[i] = tokensContent[i].charAt(tokensContent[i].length()-1);
        }
        tokensContent = snowtam.split(delimiterContent);
        tokensContent = Arrays.copyOfRange(tokensContent, 1, tokensContent.length);

        // Création d'un dictionnaire pour simplifier le traitement qui va suivre
        Map<Character,String> dictionnary = new LinkedHashMap<Character, String>();
        for(int i=0; i <tokensLetter.length; i++){
            dictionnary.put(tokensLetter[i],tokensContent[i].replace("\n","").replace(".","").replace(")",""));
        }
        return dictionnary;
    }


    public static String getAirportName(String OACI) throws IOException {
        File file = new File("D:\\Repos\\5A\\Android\\decryptSnowtam\\src\\com\\company\\OACI codes");

        BufferedReader br = new BufferedReader(new FileReader(file));

        String st;
        while ((st = br.readLine()) != null) {
            String[] liste = st.split(",");
            for(String element : liste){
                if(element.length() == 6){
                    if(element.equals("\"" + OACI + "\"")){
                        return liste[1].replace("\"","");
                    }
                }
            }
        }
        return "can't find airport";
    }


    public static String getDateHour(String data){
        String day = data.substring(2,4);
        String month = "";
        String heure = data.substring(4,6) + "h" + data.substring(6,8) + " UTC";

        switch(data.substring(0,2))
        {
            case "01":
                month = "January";
                break;
            case "02":
                month = "February";
                break;
            case "03":
                month = "March";
                break;
            case "04":
                month = "April";
                break;
            case "05":
                month = "May";
                break;
            case "06":
                month = "June";
                break;
            case "07":
                month = "July";
                break;
            case "08":
                month = "August";
                break;
            case "09":
                month = "September";
                break;
            case "10":
                month = "October";
                break;
            case "11":
                month = "November";
                break;
            case "12":
                month = "December";
                break;
            default:
                month = "error";
        }

        return day + " " + month + " " + heure;
    }



}
