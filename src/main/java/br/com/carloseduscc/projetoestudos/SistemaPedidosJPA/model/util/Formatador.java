package br.com.carloseduscc.projetoestudos.SistemaPedidosJPA.model.util;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class Formatador {
    private static final NumberFormat numberFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));;

    public static String formatarDinheiro(double valor){
        return numberFormat.format(valor);
    }

    public static String formatarDinheiro(BigDecimal valor){
        return numberFormat.format(valor.doubleValue());
    }
}
