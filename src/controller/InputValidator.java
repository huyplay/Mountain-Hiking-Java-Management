package controller;

import java.text.DecimalFormat;
import java.util.Arrays;


public class InputValidator {

    public static boolean isPositiveInteger(String str) {
        try {
            int value = Integer.parseInt(str);
            return value >= 0; // chỉ nhận số nguyên không âm
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    public static boolean isValidName(String str) {
    for (char c : str.toCharArray()) {
        if (!(Character.isLetter(c) || c == ' ')) {
            return false;
        }
    }
    return true;
}

    public static boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }
       return email.matches("^[A-Za-z0-9+_-]+(\\.[A-Za-z0-9+_-]+)*@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }

    public static boolean isPhoneVN(String phoneNumber) {

        String phoneCode = phoneNumber.substring(0, 3);
        String[] phonePrefixes = {
            // Viettel
            "096", "097", "098", "086", "032", "033", "034", "035", "036", "037", "038", "039",
            // MobiFone
            "090", "093", "089", "070", "079", "077", "076", "078",
            // VinaPhone
            "091", "094", "088", "083", "084", "085", "081", "082",
            // Vietnamobile
            "092", "052", "056", "058",
            // Gmobile
            "099", "059",
            // iTel
            "087",
            // Wintel
            "055"
        };
        if (Arrays.asList(phonePrefixes).contains(phoneCode)) {
            return true;
        }

        return false;
    }

    public static boolean isPhoneVTandVNPT(String phoneNumber) {

        String phoneCode = phoneNumber.substring(0, 3);
        String[] phonePrefixes = {
            // Viettel
            "096", "097", "098", "086", "032", "033", "034", "035", "036", "037", "038", "039",
            // VinaPhone
            "091", "094", "088", "083", "084", "085", "081", "082"
        };
        if (Arrays.asList(phonePrefixes).contains(phoneCode)) {
            return true;
        }

        return false;
    }

    public static boolean isCampusFPTU(String campusCode) {

        String validCampusCode[] = {"SE", "HE", "DE", "QE", "CE"};

        if (Arrays.asList(validCampusCode).contains(campusCode)) {
            return true;
        }
        return false;

    }
    
        public static String formatFee(double fee){
        DecimalFormat df = new DecimalFormat("#,###");
        return df.format(fee);
    }

}
