package com.teste.rodrigo.inlocoweather.util;

import android.app.AlertDialog;
import android.content.Context;

import com.teste.rodrigo.inlocoweather.R;

public class Util {

    public static void showWarning(Context context, int warningMessage)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(warningMessage);
        builder.setPositiveButton(R.string.geral_ok, null);
        builder.show();
    }

}
