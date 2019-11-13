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
                    System.out.print(pair.getKey().toString() + "  ");
                    String airportName = getAirportName(pair.getValue().toString().replace(" ",""));
                    System.out.println(airportName);
                    break;
                case 'B':
                    System.out.print(pair.getKey().toString() + "  ");
                    String dateHour = getDateHour(pair.getValue().toString().replace(" ",""));
                    System.out.println(dateHour);
                    break;
                case 'C':
                    System.out.print(pair.getKey().toString() + "  ");
                    String runwayDesignator = getRunwayDesignator(pair.getValue().toString().replace(" ",""));
                    System.out.println(runwayDesignator);
                    break;
                case 'D':
                    System.out.print(pair.getKey().toString() + "  ");
                    String clearedRunwayLength = getClearedRunwayLength(pair.getValue().toString().replace(" ",""));
                    System.out.println(clearedRunwayLength);
                    break;
                case 'E':
                    System.out.print(pair.getKey().toString() + "  ");
                    String clearedRunwayWidth = getClearedRunwayWidth(pair.getValue().toString().replace(" ",""));
                    System.out.println(clearedRunwayWidth);
                    break;
                case 'F':
                    System.out.print(pair.getKey().toString() + "  ");
                    String depositsOverTotalRunwayLength = getDepositsOverTotalRunwayLength(pair.getValue().toString().replace(" ",""));
                    System.out.println(depositsOverTotalRunwayLength);
                    break;
                case 'G':
                    System.out.print(pair.getKey().toString() + "  ");
                    String meanDepthDeposit = getMeanDepthDeposit(pair.getValue().toString().replace(" ",""));
                    System.out.println(meanDepthDeposit);
                    break;
                case 'H':
                    System.out.print(pair.getKey().toString() + "  ");
                    String frictionMeasurements = getFrictionMeasurements(pair.getValue().toString().replace(" ",""));
                    System.out.println(frictionMeasurements);
                    break;
                case 'J':
                    System.out.print(pair.getKey().toString() + "  ");
                    String criticalSnowbanks = getCriticalSnowbanks(pair.getValue().toString().replace(" ",""));
                    System.out.println(criticalSnowbanks);
                    break;
                case 'K':
                    System.out.print(pair.getKey().toString() + "  ");
                    String runwayLight = getRunwayLight(pair.getValue().toString().replace(" ",""));
                    System.out.println(runwayLight);
                    break;
                case 'L':
                    System.out.print(pair.getKey().toString() + "  ");
                    String furtherClearance = getFurtherClearance(pair.getValue().toString().replace(" ",""));
                    System.out.println(furtherClearance);
                    break;
                case 'M':
                    System.out.print(pair.getKey().toString() + "  ");
                    String furtherClearanceCompletionTime = getFurtherClearanceCompletionTime(pair.getValue().toString().replace(" ",""));
                    System.out.println(furtherClearanceCompletionTime);
                    break;
                case 'N':
                    System.out.print(pair.getKey().toString() + "  ");
                    String taxiway = getTaxiway(pair.getValue().toString().replace(" ",""));
                    System.out.println(taxiway);
                    break;
                case 'P':
                    System.out.print(pair.getKey().toString() + "  ");
                    String taxiwaySnowbanks = getTaxiwaySnowbanks(pair.getValue().toString().replace(" ",""));
                    System.out.println(taxiwaySnowbanks);
                    break;
                case 'R':
                    System.out.print(pair.getKey().toString() + "  ");
                    String apron = getApron(pair.getValue().toString().replace(" ",""));
                    System.out.println(apron);
                    break;
                case 'S':
                    System.out.print(pair.getKey().toString() + "  ");
                    String nextPlannedMeasurementTime = getNextPlannedMeasurementTime(pair.getValue().toString().replace(" ",""));
                    System.out.println(nextPlannedMeasurementTime);
                    break;
                case 'T':
                    System.out.print(pair.getKey().toString() + "  ");
                    String plainLanguageRemarks = getPlainLanguageRemarks(pair.getValue().toString().replace(" ",""));
                    System.out.println(plainLanguageRemarks);
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

    public static String getAirportName(String data) throws IOException {
        File file = new File("D:\\Repos\\5A\\Android\\decryptSnowtam\\src\\com\\company\\OACI codes");

        BufferedReader br = new BufferedReader(new FileReader(file));

        String st;
        while ((st = br.readLine()) != null) {
            String[] liste = st.split(",");
            for(String element : liste){
                if(element.length() == 6){
                    if(element.equals("\"" + data + "\"")){
                        return liste[1].replace("\"","");
                    }
                }
            }
        }
        return "can't find airport";
    }

    public static String getDateHour(String data){
        String[] months = {"January","February","March","April","May","June","July","August","September","October","November","December"};
        String day = data.substring(2,4);
        String month = "";
        String heure = data.substring(4,6) + "h" + data.substring(6,8) + " UTC";

        int index = Integer.parseInt(data.substring(0,2));
        month = months[index-1];

        return day + " " + month + " " + heure;
    }

    public static String getRunwayDesignator(String data){
        return "RUNWAY " + data;
    }

    public static String getClearedRunwayLength(String data){
        return "CLEARED RUNWAY LENGTH " + data + "M";
    }

    public static String getClearedRunwayWidth(String data){
        String width;

        if(data.length() == 1)
           width = data;
        else
            width = data.substring(0,data.length()-1);

        if(data.charAt(data.length()-1) == 'R')
            return "CLEARED RUNWAY WIDTH " + width + "M RIGHT";
        else if(data.charAt(data.length()-1) == 'L')
            return "CLEARED RUNWAY WIDTH " + width + "M LEFT";
        else{
            return "CLEARED RUNWAY WIDTH " + width + "M";
        }
    }

    public static String getDepositsOverTotalRunwayLength(String data){
        String[] weathers = {"CLEAR AND DRY","DAMP","WATER PATCHES","RIME OR FROST COVERED","DRY SNOW","WET SNOW","SLUSH","ICE","COMPACTED OR ROLLED SNOW","FROZEN RUTS OR RIDGES"};
        String[] tab = data.split("/");
        int[] indexes = {Integer.parseInt(tab[0]),Integer.parseInt(tab[1]),Integer.parseInt(tab[2])};

        return "Threshold: " + weathers[indexes[0]] + " / Mid runway: " + weathers[indexes[1]] + " / Roll out: " + weathers[indexes[2]] ;
    }

    public static String getMeanDepthDeposit(String data){
        String[] tab = data.split("/");

        for(int i=0; i<3 ; i++){
            if(tab[i].equals("XX")){
                tab[i] = "not significant";
            }
            else{
                tab[i] += "mm";
            }
        }
        return "MEAN DEPTH Threshold: " + tab[0] + " / Mid runway: " + tab[1] + " / Roll out: " + tab[2];
    }

    public static String getFrictionMeasurements(String data){
        String[] instrumentsAbbreviation = {"BRD","GRT","MUM","RFT","SFH","SFL","SKH","SKL","TAP"};
        String[] instruments = {"Brakemeter-Dynometer","Grip tester","Mu-meter","Runway frition tester","Surface friction tester (high-pressure tire)","Surface friction tester (low-pressure tire)","Skiddometer (high-pressure tire)","Skiddometer (low-pressure tire)","Tapley meter"};

        String instrument = "";
        for(int i=0; i<instruments.length; i++){
            if(instrumentsAbbreviation[i].equals(data.substring(data.length()-3,data.length()))){
                instrument = instruments[i];
                break;
            }
        }
        String[] numbers;
        if(instrument.equals("")){
            numbers = data.substring(0,data.length()).split("/");
            instrument = "¯\\_(ツ)_/¯";
        }
        else{
            numbers = data.substring(0,data.length()-3).split("/");
        }


        for(int i=0; i<numbers.length ;i++){
            int number = Integer.parseInt(numbers[i]);
            if(number >= 40 || number == 5)
                numbers[i] = "GOOD";
            else if((number <= 39 && number >= 36) || number == 4)
                numbers[i] = "MEDIUM TO GOOD";
            else if((number <= 35 && number >= 30) || number == 3)
                numbers[i] = "MEDIUM";
            else if((number <= 29 && number >= 26) || number == 2)
                numbers[i] = "MEDIUM TO POOR";
            else if((number <= 25 && number >= 10) || number == 1)
                numbers[i] = "POOR";
            else if(number == 9){
                numbers[i] = "impossible to measure";
            }
        }

        return "BRAKING ACTION Threshold: " + numbers[0] + " / Mid runway: " + numbers[1] + " / Roll Out: " + numbers[2] + " Instrument: " + instrument;
    }

    public static String getCriticalSnowbanks(String data){
        String[] tab = data.split("/");
        String height = tab[0];
        tab = tab[1].split("");

        String distance = "";
        String side = "";
        int i = 0;
        while(!(tab[i].equals("R") || tab[i].equals("L"))){
            distance += tab[i];
            i++;
        }
        if(tab.length - i == 2)
            side = "LEFT AND RIGHT";
        else if(tab[i].equals("L"))
            side = "LEFT";
        else if(tab[i].equals("R"))
            side = "RIGHT";

        return "CRITICAL SNOW BANK " + height + "cm / " + distance + "m " + side + " of Runway";


    }

    public static String getRunwayLight(String data){
        data = data.substring(3,data.length());
        String side = "";
        if(data.length() == 1){
            if(data.equals("L"))
                side = "LEFT";
            else if(data.equals("R"))
                side = "RIGHT";
        }
        else
            side = "LEFT AND RIGHT";

        return "Lights obscured : YES " + side + " of RUNWAY";

    }

    public static String getFurtherClearance(String data){
        if(data.equals("TOTAL"))
            return "FURTHER CLEARANCE TOTAL";
        else{
            String[] HeightAndWidth = data.split("/");
            return "FURTHER CLEARANCE " + HeightAndWidth[0] + "m / " + HeightAndWidth[1] + "m";
        }
    }

    public static String getFurtherClearanceCompletionTime(String data){
        String hh = data.substring(0,2);
        String mm = data.substring(2,4);

        return "Anticipated time of completion " + hh + "h" + mm + " UTC";
    }

    public static String getTaxiway(String data){
        return data;
    }

    public static String getTaxiwaySnowbanks(String data){
        String distance  = data.substring(3,data.length());
        return "SNOW BANKS: YES SPACE " + distance + "m";
    }

    public static String getApron(String data){
        return data;
    }

    public static String getNextPlannedMeasurementTime(String data){
        return "NEXT OBSERVATION " + getDateHour(data);
    }

    public static String getPlainLanguageRemarks(String data){
        return data;
    }
}
