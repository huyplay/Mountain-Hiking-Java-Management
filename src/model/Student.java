package model;

import java.io.Serializable;
import java.text.DecimalFormat;
import controller.InputValidator;


public class Student implements Serializable {

    private String id;
    private String name;
    private String phone;
    private String email;
    private String codeMountain;
    private double feeTuition;

    public Student(String id, String name, String phone, String email, String codeMountain, double feeTuition) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.codeMountain = codeMountain;
        this.feeTuition = feeTuition;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCodeMountain() {
        return codeMountain;
    }

    public String getCodeMountainMT() {
        if (InputValidator.isPositiveInteger(codeMountain)) {
            int code = Integer.parseInt(codeMountain);
            if (code >= 0 && code <= 9) {
                return "MT0"+code;
            } else {
                return "MT"+code;
            }
        }
        return codeMountain;
    }
    
//    
//        public int getCodeMountainINT() {
//        if (InputValidator.isPositiveInteger(codeMountain)) {
//            int code = Integer.parseInt(codeMountain);
//                return code;
//        }
//        return 0;
//    }
        

    public void setCodeMountain(String codeMountain) {
        this.codeMountain = codeMountain;
    }

    public double getFeeTuition() {
        return feeTuition;
    }

    public void setFeeTuition(double feeTuition) {
        this.feeTuition = feeTuition;
    }

    public String formatFee() {
        DecimalFormat df = new DecimalFormat("#,###");
        return df.format(feeTuition);
    }

    @Override
    public String toString() {
        return String.format("%-10s | %-20s | %-10s | %-10s | %-10s", id, name, phone, getCodeMountainMT(), formatFee());
    }
}
