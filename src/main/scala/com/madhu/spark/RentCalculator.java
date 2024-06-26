package com.madhu.spark;


import java.time.LocalDate;

public class RentCalculator {

    public static void main(String[] args) {

        // variables
        int flat1Ebill = 1161; // add 50 for solar rebate // 75303xxxx
        int flat2Ebill = 0;                           // 73166xxxx
        int flat3Ebill = 422;                            // 67450xxxx
        int commonAreaEbill = 303; // round off to nearest number divisable by 3 // 95880xxxx
        int roomEbill = 285;  //66967xxxx
        //105 room

        
        String month = LocalDate.now().minusMonths(1).getMonth().toString();
        int prevMonth = LocalDate.now().minusMonths(2).getMonthValue();
        int currMonth = LocalDate.now().minusMonths(1).getMonthValue();
        int numOfTenants = 3;

        int flat1PrevBalance = -280;
        int flat2PrevBalance = 0;
        int flat3PrevBalance = 120;
        int roomPrevBalance = 40;


        int flat1Rent = 10000; // Jan15, 2024
        int flat2Rent = 9500; // September 12, 2021
        int flat3Rent = 9500; // April 27, 2023
        int roomRent = 3000; // May 1, 2023

        // Calculations
        int maintananceBill = Math.round(commonAreaEbill/numOfTenants);

        calculateRent(1,flat1Rent, flat1Ebill, maintananceBill, flat1PrevBalance, prevMonth, currMonth, month);
        calculateRent(2,flat2Rent, flat2Ebill, maintananceBill, flat2PrevBalance, prevMonth, currMonth, month);
        calculateRent(3,flat3Rent, flat3Ebill, maintananceBill, flat3PrevBalance, prevMonth, currMonth, month);
        calculateRent(4,roomRent, roomEbill, maintananceBill - 60, roomPrevBalance, prevMonth, currMonth, month);

    }

    public static void calculateRent(int flatNum, int rent, int eBill, int maintainance, int prevBalance, int prevMonth, int currMonth, String month) {
        String newLine = System.getProperty("line.separator");

        System.out.println("FLAT" + flatNum +" BILL :");
        System.out.println("------------------------");
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
