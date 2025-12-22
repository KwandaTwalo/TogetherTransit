package com.kwndtwalo.TogetherTransit.util;

import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Helper {

    //It returns true if the phone meet the requirements.
    public static boolean isValidPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {return false;}

        //remove empty spaces and hyphens in between digits.
        phoneNumber = phoneNumber.replaceAll("[\\s-]","");

        //converts local format to international format.
        if (phoneNumber.startsWith("0")){
            phoneNumber = "+27" + phoneNumber.substring(1);
        }

        /*Regex validation Breakdown:
        - +27 country code
        - Mobile numbers: start with 6, 7, or 8
                - Landlines:
        01 → Johannesburg
        02 → Cape Town
        03 → Durban
        04 → Port Elizabeth
        05 → Bloemfontein
                - Followed by 8 digits
                */
        String regex = "^\\+27([1-5][0-9]|[6-8])[0-9]{7}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }

    //Returns true if the string is not empty.
    public static boolean isValidString(String str) {return str != null && !str.trim().isEmpty();}

    //Returns true if the postal code meets the requirements.
    public static boolean isValidPostalCode(int postalCode) {return postalCode >= 0 && postalCode <= 9999;}

    /**
     * This checks format only (not whether the email exists)
     * return true if email format is valid
     */
    public static boolean isValidEmailAddress(String emailAddress) {

        // Reject null or empty input
        if (emailAddress == null || emailAddress.trim().isEmpty()) {
            return false;
        }

        // Normalize input
        emailAddress = emailAddress.trim().toLowerCase();

    /*
     Regex explanation:
     - Local part: letters, digits, dots, underscores, +, -, *
     - @ symbol
     - Domain name with one or more dots
     - TLD: minimum 2 characters, no hard upper limit
    */
        String regex = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,}$";

        return emailAddress.matches(regex);
    }

    /**
     * Validates password strength.
     * Rules:
     * - Minimum 8 characters
     * - At least 1 uppercase letter
     * - At least 1 lowercase letter
     * - At least 1 digit
     * - At least 1 special character
     * - No spaces

     * NOTE:
     * - This checks strength only
     */
    public static boolean isValidPassword(String password) {

        // Null or empty check
        if (password == null || password.trim().isEmpty()) {
            return false;
        }

        // No spaces allowed
        if (password.contains(" ")) {
            return false;
        }
    /*
     Regex explanation:
     (?=.*[a-z])      → at least one lowercase letter
     (?=.*[A-Z])      → at least one uppercase letter
     (?=.*\\d)        → at least one digit
     (?=.*[@$!%*?&])  → at least one special character
     .{8,}            → minimum 8 characters
    */
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&]).{8,}$";

        return password.matches(regex);
    }

    public static boolean isValidScore(int score) {
        return score >= 1 && score <=5;
    }

    public static boolean isValidFeedback (String feedback) {
        if (feedback == null || feedback.trim().isEmpty()) {return true; /*note it is not mandatory to add feedback*/}

        int length = feedback.trim().length();
        return length >= 5 && length <= 500;
    }

    public static boolean isValidDateOfBirth(LocalDate dateOfBirth) {

        if (dateOfBirth == null) {return false; }

        LocalDate today = LocalDate.now();
        int age = Period.between(dateOfBirth, today).getYears();

        return !dateOfBirth.isAfter(today) && age >= 3 && age <= 20;
    }

    public static boolean isValidGrade(String grade) {
        // Check if grade is null or empty
        if (grade == null || grade.trim().isEmpty()) {
            return false; // invalid if null or empty
        }

        // Normalize the string (remove leading/trailing spaces)
        grade = grade.trim();

        // Check if it starts with "Grade " (case-insensitive)
        if (!grade.toLowerCase().startsWith("grade ")) {
            return false; // invalid if it does not start with "Grade "
        }

        try {
            // Extract the numeric part after "Grade "
            String numberPart = grade.substring(6).trim(); // "Grade 1" -> "1"
            int gradeNumber = Integer.parseInt(numberPart); // convert to integer

            // Validate number is between 1 and 12
            return gradeNumber >= 0 && gradeNumber <= 12;
        } catch (NumberFormatException e) {
            // If parsing fails, it's invalid
            return false;
        }
    }


}
