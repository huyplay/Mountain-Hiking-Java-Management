package model;

import controller.InputValidator;

public class Mountain {

    private String id;
    private String name;
    private String province;
    private String description;

    public Mountain(String id, String name, String province, String description) {
        this.id = id;
        this.name = name;
        this.province = province;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public String getCode() {
        if (InputValidator.isPositiveInteger(id)) {
            int code = Integer.parseInt(id);
            if (code >= 0 && code <= 9) {
                return "MT0" + id;
            } else {
                return "MT" + id;
            }
        }
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

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return String.format("%-5s | %-20s | %-10s | %-30s", id, name, province, description);
    }

    public String toChoose() {
        return id + " - " + name + " - " + province + " - " + description;
    }

}
