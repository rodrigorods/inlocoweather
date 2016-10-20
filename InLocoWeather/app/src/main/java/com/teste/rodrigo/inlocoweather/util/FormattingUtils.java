package com.teste.rodrigo.inlocoweather.util;

public class FormattingUtils {

    private static final double KELVIN_CELSIUS_CONVERTION_RATE = 273.15;

    public static double convertKelvinToCelsius(double kelvinTemp){
        return kelvinTemp - KELVIN_CELSIUS_CONVERTION_RATE;
    }

}
