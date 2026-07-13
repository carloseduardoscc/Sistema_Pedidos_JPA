package br.com.carloseduscc.resource_server.model.util;

import java.text.NumberFormat;
import java.util.Locale;

public class Formatador {
    private static final NumberFormat numberFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));;

    public static String formatarDinheiro(double valor){
        return numberFormat.format(valor);
    }
}
