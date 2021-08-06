package com.madhu.spark;


public class RentCalculator {

    public static void main(String[] args) {

        // variables
        String month = "JULY";
        String prevMonth = "06";
        String currMonth = "07";
        int numOfTenants = 2;

        int flat1Ebill = 600 ; // add 50 for solar rebate //669--382
        int flat2Ebill = 0;                           // 731--098
        int flat3Ebill = 637;                            //674--689
        int commonAreaEbill = 216; // round off to nearest number divisable by 3

        int flat1PrevBalance = 0;
        int flat2PrevBalance = 0;
        int flat3PrevBalance = 0;

        int flat1Rent = 12000;
        int flat2Rent = 11000;
        int flat3Rent = 8950;

        // Calculations
        int maintananceBill = Math.round(commonAreaEbill/numOfTenants);

        calculateRent(1,flat1Rent, flat1Ebill, maintananceBill, flat1PrevBalance, prevMonth, currMonth, month);
        calculateRent(2,flat2Rent, flat2Ebill, maintananceBill, flat2PrevBalance, prevMonth, currMonth, month);
        calculateRent(3,flat3Rent, flat3Ebill, maintananceBill, flat3PrevBalance, prevMonth, currMonth, month);

    }

    public static void calculateRent(int flatNum, int rent, int eBill, int maintainance, int prevBalance, String prevMonth, String currMonth, String month) {
        String newLine = System.getProperty("line.separator");

        System.out.println("FLAT" + flatNum +" BILL :");
        System.out.println("-------------");
        String finalFlat = "ELECTRICITY (15/" + prevMonth + " - 15/" + currMonth + "): "+  (eBill + maintainance) + "(" + eBill +"+"+ maintainance +")"
                .concat(newLine)
                .concat(month + " RENT: " + rent)
                .concat(newLine)
                .concat("PREVIOUS BALANCE: " + prevBalance)
                .concat(newLine)
                .concat("------------------------")
                .concat(newLine)
                .concat("TOTAL: " + (rent + eBill + maintainance + prevBalance));

        System.out.println(finalFlat);
        System.out.println();
        System.out.println();
    }
}
