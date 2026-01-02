package com.kwndtwalo.TogetherTransit.util;

import com.kwndtwalo.TogetherTransit.domain.route.RouteTimeSlot;

import java.time.*;
import java.util.Set;

public class Helper {

    //It returns true if the phone meet the requirements.
    public static boolean isValidPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            return false;
        }

        // Remove spaces and hyphens
        phoneNumber = phoneNumber.replaceAll("[\\s-]", "");

        // Convert local format (0xxxxxxxxx) to international (+27xxxxxxxxx)
        if (phoneNumber.startsWith("0")) {
            phoneNumber = "+27" + phoneNumber.substring(1);
        }

    /**
     Regex explanation:
     - Must start with +27
     - Mobile numbers: 6, 7, or 8 followed by 8 digits
     - Landlines: 1–5 followed by 8 digits
     - Total digits after +27 = 9
    */
        String regex = "^\\+27([6-8][0-9]{8}|[1-5][0-9]{8})$";

        return phoneNumber.matches(regex);
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

    // Validates South African driver's license number (format only)
    public static boolean isValidLicenseNumber(String licenseNumber) {

        // Null or empty check
        if (licenseNumber == null || licenseNumber.trim().isEmpty()) {
            return false;
        }

        // Remove spaces just in case user entered "1234 5678"
        licenseNumber = licenseNumber.replaceAll("\\s", "");

    /*
     Regex explanation:
     ^[0-9]{8,12}$
     - Only digits
     - Minimum 8 digits
     - Maximum 12 digits
    */
        String regex = "^[0-9]{8,12}$";

        return licenseNumber.matches(regex);
    }

    //Description must be readable and meaningful.
    public static boolean isValidPermissionDescription(String permissionDescription) {
        return permissionDescription != null
        && !permissionDescription.trim().isEmpty()
        && permissionDescription.length() >= 10
        && permissionDescription.length() <= 255;
    }

    //validates the entire action set.
    public static boolean isValidActionSet(Set<String> actions) {
        if (actions == null || actions.isEmpty()) {
            return false;
        }

        for (String action : actions) {
            if (!isValidAction(action)){
                return false;
            }
        }
        return true;
    }

    // Each action must follow a standard format.
    public static boolean isValidAction(String action) {
        if (action == null || action.trim().isEmpty()) {
            return false;
        }

        /*
         Action format rules:
         - UPPERCASE letters
         - Underscores allowed
         - No spaces
         - Example: VIEW_PAYMENTS
         */

        return action.matches("^[A-Z_]{3,50}$");
    }

    // Validates enum
    public static boolean isValidPermissionType(Enum<?> type) {
        return type != null;
    }



    /*==============================================================================================================*/
    /**----------------------------------VEHICLE VALIDATION METHODS------------------------------------------------**/
    /*==============================================================================================================*/

    public static boolean isValidPlateNumber(String plateNumber) {

        // Reject null input
        if (plateNumber == null) {
            return false;
        }

        // Remove spaces and hyphens to normalize input
        plateNumber = plateNumber.replaceAll("[\\s-]", "").toUpperCase();

        /*
         Regex explanation:
         ^[A-Z]{2}        → Province code (CA, GP, ND, etc.)
         [0-9]{3,6}       → Registration digits
         ([A-Z]{0,3})$    → Optional trailing letters
         */
        String regex = "^[A-Z]{2}[0-9]{3,6}[A-Z]{0,3}$";

        return plateNumber.matches(regex);
    }

    /**
     * Validates capacity of vehicle.
     */
    public static boolean isValidVehicleCapacity(int vehicleCapacity) {
        //Capacity must always be positive.
        return vehicleCapacity > 0;
    }

    /**
     * Validates vehicle manufacturing year.
     * Rules:
     * - Cannot be in the future
     * - Not older than 30 years (common insurance rule)
     */
    public static boolean isValidVehicleYear(int year) {

        int currentYear = LocalDate.now().getYear();

        return year >= (currentYear - 30) && year <= currentYear;
    }

    /**
     * Validates roadworthy status.
     *
     * Rules:
     * - If roadworthy is TRUE → expiry date is required and must be future
     * - If FALSE → expiry date may be null
     */
    public static boolean isValidRoadworthy(boolean status, LocalDate expiryDate) {

        // If roadworthy is true, expiry date must exist and be valid
        if (status) {
            return expiryDate != null && !expiryDate.isBefore(LocalDate.now());
        }

        // If not roadworthy, expiry date is optional
        return true;
    }

    /**
     * Validates South African vehicle license disk details.
     */
    public static boolean isValidLicenseDisk(String diskNumber, LocalDate expiryDate) {

        // Disk number must exist
        if (diskNumber == null || diskNumber.trim().isEmpty()) {
            return false;
        }

        /*
         Regex explanation:
         ^[0-9]{8,12}$
         - License disk numbers are numeric
         - Typically 8–12 digits
         */
        if (!diskNumber.matches("^[0-9]{8,12}$")) {
            return false;
        }

        // Expiry date must exist and be in the future
        return expiryDate != null && !expiryDate.isBefore(LocalDate.now());
    }

    /**
     * Validates insurance provider and expiry date.
     */
    public static boolean isValidInsurance(String provider, LocalDate expiryDate) {

        // Provider name must exist
        if (provider == null || provider.trim().isEmpty()) {
            return false;
        }

        /*
         Provider name rules:
         - Letters, spaces and hyphens
         - Example: OUT-insurance, Discovery Insure
         */
        if (!provider.matches("^[A-Za-z\\- ]{2,50}$")) {
            return false;
        }

        // Insurance must be valid today or later
        return expiryDate != null && !expiryDate.isBefore(LocalDate.now());
    }




    /*==============================================================================================================*/
    /**----------------------------------PAYMENT VALIDATION METHODS------------------------------------------------**/
    /*==============================================================================================================*/

    /*
     * Prevents free or negative payments.
     */
    public static boolean isValidPaymentAmount(double amount) {
        // Amount must be greater than zero.
        return amount > 0;
    }

    /*
     * Payment date must not be in the future.
     */
    public static boolean isValidPaymentDate(LocalDateTime paymentDate) {
        return paymentDate != null && !paymentDate.isAfter(LocalDateTime.now());
    }

    /*
     * Validates service period.
     * Start date must exist and end must be after start.
     */
    public static boolean isValidPaymentPeriod(LocalDateTime start, LocalDateTime end) {

        if (start == null || end == null) {
            return false;
        }

        return end.isAfter(start);
    }

    /*
     * Failure reason is required only if payment failed.
     */
    public static boolean isValidFailureReason(String reason, Enum<?> status) {

        if (status == null) return false;

        if (status.name().equals("FAILED")) {
            return reason != null && !reason.trim().isEmpty()
                    && reason.length() >= 5
                    && reason.length() <= 255;
        }

        // Not required for successful payments
        return true;
    }

    // Validates card brand
    public static boolean isValidCardBrand(String brand) {
        if (brand == null || brand.trim().isEmpty()) {
            return false;
        }

    /*
     Allowed brands
     */
        return brand.equalsIgnoreCase("Visa")
                || brand.equalsIgnoreCase("MasterCard")
                || brand.equalsIgnoreCase("American Express");
    }

    //  Validates last 4 digits of card.
    public static boolean isValidLastFourDigits(String digits) {
        if (digits == null || digits.trim().isEmpty()) {return false;}

        /*
     Rules:
     - Exactly 4 digits
     */
        return digits.matches("^[0-9]{4}$");
    }

    // Validates created date.
    public static boolean isValidCreatedAt(LocalDateTime createdAt) {
        if (createdAt == null) return false;

        // Cannot be in the future.
        return !createdAt.isAfter(LocalDateTime.now());
    }

    /*==============================================================================================================*/
    /**----------------------------------BOOKING VALIDATION METHODS------------------------------------------------**/
    /*==============================================================================================================*/

    public static boolean isValidBookingDate(LocalDate bookingDate) {
        // Cannot be in the future.
        return bookingDate != null && !bookingDate.isAfter(LocalDate.now());
    }

    public static boolean isValidContractPeriod(LocalDate startDate, LocalDate endDate) {
        return startDate != null
                && endDate != null
                && !endDate.isBefore(startDate);
    }

    /**
     * Validates the rules for custom schedules.
     *
     * Business rules:
     * - If usesCustomSchedule = true:
     *      → customDays MUST exist and must NOT be empty
     * - If usesCustomSchedule = false:
     *      → customDays MUST be empty or null
     */
    public static boolean isValidCustomSchedule(boolean usesCustomSchedule, Set<?> days) {

        // Case 1: Child uses a custom schedule
        if (usesCustomSchedule) {

            // customDays must be provided and contain at least one day
            // Example: MONDAY, SATURDAY
            return days != null && !days.isEmpty();
        }

        // Case 2: Child uses default booking schedule
        // customDays should NOT exist because booking schedule applies
        return days == null || days.isEmpty();
    }


    /*==============================================================================================================*/
    /**----------------------------------ROUTE VALIDATION METHODS------------------------------------------------**/
    /*==============================================================================================================*/

    /**
     * Validates monthly contract logic.
     * Rule:
     * - If route is monthly → fee must be > 0
     * - If not monthly → fee must be 0
     */
    public static boolean isValidMonthlyFee(boolean isMonthly, double fee) {
        if (isMonthly) {
            return fee > 0;
        }
        return fee == 0;
    }

    /**
     * Validates GPS coordinates.
     * Latitude:  -90 to 90
     * Longitude: -180 to 180
     */
    public static boolean isValidCoordinates(Double latitude, Double longitude) {

        // Coordinates must not be null
        if (latitude == null || longitude == null) {
            return false;
        }

        // Validate latitude range
        if (latitude < -90 || latitude > 90) {
            return false;
        }

        // Validate longitude range
        return longitude >= -180 && longitude <= 180;
    }

    /**
     * Ensures numeric values like distance or duration are positive.
     */
    public static boolean isValidPositiveNumber(double value) {
        return value > 0;
    }

    /**
     * Validates service days.
     * Rule:
     * - Must not be null
     * - Must contain at least one day
     */
    public static boolean isValidServiceDays(Set<DayOfWeek> days) {
        return days != null && !days.isEmpty();
    }

    /**
     * Validates a date range.
     * Rules:
     * - Dates must not be null
     * - Start date must be before or equal to end date
     */
    public static boolean isValidDateRange(LocalDate start, LocalDate end) {

        // Prevent NullPointerException
        if (start == null || end == null) {
            return false;
        }

        // Start date must be before or equal to end date
        return !start.isAfter(end);
    }

    // Validates exam date exists
    public static boolean isValidExamDate(LocalDate examDate) {
        // Exam date cannot be null
        return examDate != null;
    }

    // Ensures pickup & drop-off times are valid and logical
    public static boolean isValidExamTimes(LocalTime pickupTime, LocalTime dropOffTime) {

        // Both times must exist
        if (pickupTime == null || dropOffTime == null) {
            return false;
        }

        // Drop-off must be AFTER pickup
        return dropOffTime.isAfter(pickupTime);
    }

    // Ensures exam session belongs to a valid period
    public static boolean isExamDateWithinPeriod(
            LocalDate examDate,
            LocalDate startDate,
            LocalDate endDate
    ) {
        // All dates must exist
        if (examDate == null || startDate == null || endDate == null) {
            return false;
        }

        // Exam date must fall within the exam period range (inclusive)
        return !examDate.isBefore(startDate) && !examDate.isAfter(endDate);
    }

    // Ensures time range is logical
    public static boolean isValidTimeRange(LocalTime startTime, LocalTime endTime) {

        // Both times must exist
        if (startTime == null || endTime == null) {
            return false;
        }

        // End time must be AFTER start time
        return endTime.isAfter(startTime);
    }

    // Validates time ranges based on TimeType
    public static boolean isValidTimeTypeRange(
            RouteTimeSlot.TimeType timeType,
            LocalTime startTime,
            LocalTime endTime
    ) {

        // CUSTOM allows any valid time range
        if (timeType == RouteTimeSlot.TimeType.CUSTOM) {
            return true;
        }

        // MORNING: 04:00 – 11:00
        if (timeType == RouteTimeSlot.TimeType.MORNING) {
            return !startTime.isBefore(LocalTime.of(4, 0))
                    && !endTime.isAfter(LocalTime.of(11, 0));
        }

        // AFTERNOON: 11:00 – 16:00
        if (timeType == RouteTimeSlot.TimeType.AFTERNOON) {
            return !startTime.isBefore(LocalTime.of(11, 0))
                    && !endTime.isAfter(LocalTime.of(16, 0));
        }

        // EVENING: 16:00 – 22:00
        if (timeType == RouteTimeSlot.TimeType.EVENING) {
            return !startTime.isBefore(LocalTime.of(16, 0))
                    && !endTime.isAfter(LocalTime.of(22, 0));
        }

        return false;
    }

    // Ensures time type is provided
    public static boolean isValidTimeType(Enum<?> timeType) {
        return timeType != null;
    }

}
