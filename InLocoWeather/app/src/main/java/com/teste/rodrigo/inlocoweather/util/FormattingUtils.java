package com.teste.rodrigo.inlocoweather.util;

import java.text.DecimalFormat;

public class FormattingUtils {

    private static final double KELVIN_CELSIUS_CONVERTION_RATE = 273.15;

    public static String convertKelvinToCelsius(double kelvinTemp){
        double convertedTemp = kelvinTemp - KELVIN_CELSIUS_CONVERTION_RATE;
        DecimalFormat decimalFormat = new DecimalFormat("00.00");
        return decimalFormat.format(convertedTemp);
    }

}
