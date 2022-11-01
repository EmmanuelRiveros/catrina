/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.itson.negocio;

import java.util.Calendar;
import java.text.ParseException;
import mx.itson.negocio.Movimiento;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author emman
 */

public class Operacion {
    
    public List<Movimiento> ordenarMovimientosFecha(List<Movimiento> movimientos){
        
        try {
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
            
            for (int i = 0; i < movimientos.size()-1; i++){
                
                int min_indice = i;
                for (int j = i+1; j < movimientos.size(); j++){
                    Date fechaJ = formato.parse(movimientos.get(j).getFecha());
                    Date fechaMin = formato.parse(movimientos.get(min_indice).getFecha());
                    if (fechaJ.before(fechaMin)) 
                        min_indice = j;
                }
                
                Movimiento temp = movimientos.get(min_indice);
                movimientos.set(min_indice, movimientos.get(i));
                movimientos.set(i, temp);
                   
            }
            
        } catch (ParseException ex) {
            Logger.getLogger(Operacion.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return movimientos;
    }   
    
    public float calcularSaldoInicialMes (List<Movimiento> movimientos, String mes){
        float saldoInicial = 0;
        try{
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        
            switch(mes){
            case "Enero": mes = "01"; break;
             case "Febrero": mes = "02"; break;
             case "Marzo": mes = "03"; break;
             case "Abril": mes = "04"; break;
             case "Mayo": mes = "05"; break;
             case "Junio": mes = "06"; break;
             case "Julio": mes = "07"; break;
             case "Agosto": mes = "08"; break;
             case "Septiembre": mes = "09"; break;
             case "Octubre": mes = "10"; break;
             case "Noviembre": mes = "11"; break;
             case "Diciembre": mes = "12"; break;
             }
            
            Date fechaMax = formato.parse("2022-" + mes + "-01");
            
            for (int i = 0; i < movimientos.size(); i++){
                if (formato.parse(movimientos.get(i).getFecha()).before(fechaMax)){
                    if(movimientos.get(i).getTipo() == 1){
                        saldoInicial = saldoInicial + movimientos.get(i).getCantidad();
                    } else {
                        saldoInicial = saldoInicial - movimientos.get(i).getCantidad();
                    }
                }
            }

        } catch (ParseException ex) {
            Logger.getLogger(Operacion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return saldoInicial;
    }
    
    public List<Movimiento> operacionMes(List<Movimiento> movimientos, String mes){
        try{
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
            switch(mes){
            case "Enero": mes = "01"; break;
             case "Febrero": mes = "02"; break;
             case "Marzo": mes = "03"; break;
             case "Abril": mes = "04"; break;
             case "Mayo": mes = "05"; break;
             case "Junio": mes = "06"; break;
             case "Julio": mes = "07"; break;
             case "Agosto": mes = "08"; break;
             case "Septiembre": mes = "09"; break;
             case "Octubre": mes = "10"; break;
             case "Noviembre": mes = "11"; break;
             case "Diciembre": mes = "12"; break;
            }
            
            Date fechaMin = formato.parse("2022-" + mes + "-01");
            
            Calendar cal = Calendar.getInstance();
            cal.setTime(fechaMin);
            cal.add(Calendar.MONTH, 1);
            
            Date fechaMax = cal.getTime();
            
            for (int i = 0; i < movimientos.size(); i++){
                if (formato.parse(movimientos.get(i).getFecha()).before(fechaMin) || formato.parse(movimientos.get(i).getFecha()).after(fechaMax) || formato.parse(movimientos.get(i).getFecha()).equals(fechaMax)){
                    movimientos.remove(i);
                    i--;
                }
            }
            
        } catch (ParseException ex) {
            Logger.getLogger(Operacion.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return movimientos;
    }
    
    public float sumarValoresFlujo(List<Movimiento> movimientos, int tipo){
        float resultado = 0;
        for (Movimiento i : movimientos){
            if (i.getTipo() == tipo){
                resultado = resultado + i.getCantidad();
            }  
        }
        return resultado;
    }
    
    public float calcularSubtotal(List<Movimiento> movimientos, float saldoInicial){
        float resultado = 0;
        for (Movimiento i : movimientos){
            if (i.getTipo() == 1){
                resultado = resultado + i.getCantidad();
            } else {
                resultado = resultado - i.getCantidad();
            }
        }
        return resultado;
    }
    
    public float calcularSaldoFinal(List<Movimiento> movimientos, float saldoInicial){
        float resultado = saldoInicial;
        for (Movimiento i : movimientos){
            if (i.getTipo() == 1){
                resultado = resultado + i.getCantidad();
            } else {
                resultado = resultado - i.getCantidad();
            }
        }
        return resultado;
    }
    
    
    
    
}
