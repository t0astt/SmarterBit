package com.mikerinehart.smarterbit.generic;

public enum PackageName {
    SMARTERBIT("com.mikerinehart.smarterbit"),
    FITBIT("com.fitbit.FitbitMobile");

    public String packageName;

    PackageName(String name) {
        packageName = name;
    }

    public String getPackageName() {
        return this.packageName;
    }
}
