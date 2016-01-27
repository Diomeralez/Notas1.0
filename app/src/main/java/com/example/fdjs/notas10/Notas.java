package com.example.fdjs.notas10;

/**
 * Created by fdjs on 03/10/2015.
 */
public class Notas {

    //atributos
    private  double dblNota;
    double dblPorcentaje;
    double dblPorcTranscu;
    double dblPorcFaltante;
    double dblNotaLleva;
    double dblAcomulado;
    double dblNotaSacar;
    double dblSumar;
    String strError;
    //fin atributos
    //-------------------------------------------------------------------------------------------------



    //constructor
    public Notas(){
        dblNota = 0;
        dblPorcentaje = 0;
        dblPorcTranscu = 0;
        dblPorcFaltante = 0;
        dblNotaLleva = 0;
        dblAcomulado = 0;
        dblNotaSacar = 0;
        dblSumar = 0;
        strError ="";
    }

    //propiedades
    public void setDblNota(double dblNota) {
        this.dblNota = dblNota;
    }

    public void setDblPorcentaje(double dblPorcentaje) {
        this.dblPorcentaje = dblPorcentaje;
    }

    public double getDblPorcTranscu() {
        return dblPorcTranscu;
    }

    public double getDblNotaLleva() {
        return dblNotaLleva;
    }

    public double getDblAcomulado() {
        return dblAcomulado;
    }

    public double getDblNotaSacar() {
        return dblNotaSacar;
    }

    public String getStrError() {
        return strError;
    }

    //fin propiedades
    //-----------------------------------------------------------------------------------------------

    private boolean Validar()
    {
        if (dblNota > 5 || dblNota < 0) {
            strError = "Nota no valida";
            return false;
        }

        if (dblPorcentaje > 100 || dblPorcentaje < 0) {
            strError = "Porcentaje no valido";
            return false;

        }

        if (dblPorcTranscu > 100) {
            strError = "no mas del 100%";
            return false;
        }

        return true;

    }

    //---------------------------------------------------------------------------------------------
    public boolean proceso()
    {
        try
        {
            if(!Validar()) {return false;}

            dblPorcTranscu=dblPorcTranscu+dblPorcentaje;
            dblAcomulado = dblNota*(dblPorcentaje / 100) + dblAcomulado;
            dblSumar = dblNota * dblPorcentaje + dblSumar;
            dblNotaLleva = dblSumar / dblPorcTranscu;
            dblPorcFaltante = 100 - dblPorcTranscu;
            if(dblPorcFaltante>0)
                dblNotaSacar = (3 - dblAcomulado) / (dblPorcFaltante/100);

            else    dblNotaSacar=0;



            return true;
        }
        catch (Exception ex)
        {

            strError="FALLO DESCONOCIDO";
            return false;
        }
    }
}
