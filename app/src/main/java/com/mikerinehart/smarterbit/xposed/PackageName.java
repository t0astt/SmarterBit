package com.mikerinehart.smarterbit.xposed;

public enum PackageName {
    SMARTERBIT("com.mikerinehart.smarterbit"),
    FITBIT("com.fitbit.FitbitMobile");

    private String packageName;

    PackageName(String name) {
        packageName = name;
    }

    public String getPackageName() {
        return this.packageName;
    }
}
