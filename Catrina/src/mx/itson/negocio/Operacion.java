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
            
            // Metodo de ordenamiento por selección
            for (int i = 0; i < movimientos.size()-1; i++){
                
                // El indice de la fecha menor encontrada hasta el momento
                int min_indice = i;
                for (int j = i+1; j < movimientos.size(); j++){
                    Date fechaJ = formato.parse(movimientos.get(j).getFecha());
                    Date fechaMin = formato.parse(movimientos.get(min_indice).getFecha());
                    // Si la fecha es antes de la fecha mínima entonces el indice cambia a esta fecha
                    if (fechaJ.before(fechaMin)) 
                        min_indice = j;
                }
                
                // Variable temporal creada para que no se pierda el valor al momento de intercambiar fechas
                Movimiento temp = movimientos.get(min_indice);
                //Intercambio de fechas
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
            
            // Cambia el mes elegido por un formato númerico
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
            
            // Crea una variable que es la fecha maxima a calcular el saldo inicial
            Date fechaMax = formato.parse("2022-" + mes + "-01");
            
            // Bucle utilizado para analizar la fecha de cada movimiento
            for (int i = 0; i < movimientos.size(); i++){
                if (formato.parse(movimientos.get(i).getFecha()).before(fechaMax)){
                    // Dependiendo del tipo "1" para depositos y lo demas para retiros realizar una acción
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
            
            // Cambia el mes elegido por un formato númerico
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
            
            // Crea una variable que es la fecha mínima
            Date fechaMin = formato.parse("2022-" + mes + "-01");
            
            // Variable que utiliza la clase calendar para sumar un mes a la fecha
            Calendar cal = Calendar.getInstance();
            cal.setTime(fechaMin);
            cal.add(Calendar.MONTH, 1);
            
            // Crea una variable que es la fecha máxima
            Date fechaMax = cal.getTime();
            
            // Bucle creado para analizar cada fecha ingresada
            for (int i = 0; i < movimientos.size(); i++){
                // Si la fecha es menor que fecha minima, mayor o igual a la fecha máxima se eliminara de la lista de movimientos
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
            // Por cada elemento se seleccionaran solamente elementos de un tipo para sumarse entre si
            if (i.getTipo() == tipo){
                resultado = resultado + i.getCantidad();
            }  
        }
        return resultado;
    }
    
    public float calcularSubtotal(List<Movimiento> movimientos, float saldoInicial){
        float resultado = 0;
        for (Movimiento i : movimientos){
            // Si el valor es igual a "1" es equivalente a deposito por lo tanto se sumara de otra forma se restara del valor resultado ya que sera retiro
            if (i.getTipo() == 1){
                resultado = resultado + i.getCantidad();
            } else {
                resultado = resultado - i.getCantidad();
            }
        }
        return resultado;
    }
    
    public float calcularSaldoFinal(List<Movimiento> movimientos, float saldoInicial){
        // La variable resultado es igual al saldo inicial del mes
        float resultado = saldoInicial;
        
        for (Movimiento i : movimientos){
            // Si el valor es igual a "1" es equivalente a deposito por lo tanto se sumara de otra forma se restara del valor resultado ya que sera retiro
            if (i.getTipo() == 1){
                resultado = resultado + i.getCantidad();
            } else {
                resultado = resultado - i.getCantidad();
            }
        }
        return resultado;
    }
    
    
    
    
}
