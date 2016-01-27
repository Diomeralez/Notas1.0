package com.example.fdjs.notas10;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.Objects;


public class MainActivity extends ActionBarActivity {


    private EditText Not,Porc;
    private TextView Trans,Acomu,Prom,NotS;
    private Button procesar,reiniciar,limp;
    MediaPlayer mp=new MediaPlayer();
    private double ac=0,ac2=0;
    Notas n=new Notas();
    private MediaPlayer mediaPlayer;

    //**********************************************Mis metodos*****************************************
    private void SonidoExit(){
        mp.stop();
        mp.reset();
        mp.release();
        mp= MediaPlayer.create(this, R.raw.exit);
        mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mp.seekTo(0);
        mp.start();
    }
    private void  SonidoClic(){
        mp.stop();
        mp.reset();
        mp.release();
        mp= MediaPlayer.create(this, R.raw.clic);
        mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mp.seekTo(0);
        mp.start();

    }

    private void SonidoTup(){
        mp.stop();
        mp.reset();
        mp.release();
        mp= MediaPlayer.create(this, R.raw.tup);
        mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mp.seekTo(0);
        mp.start();

    }

    public void Limpiar(){
        Not.setText("");
        Porc.setText("");
        Not.requestFocus();
    }

    public void Mensaje(String a){
        Toast.makeText(getApplicationContext(), a, Toast.LENGTH_SHORT).show();
    }

    public void Limpiar2(){

        Not.requestFocus();
        Acomu.setText("");
        Prom.setText("");
        NotS.setText("");
        Trans.setText("");

    }

    private  void Inicialisar(){
        Not=(EditText) findViewById(R.id.etNota);
        Porc=(EditText) findViewById(R.id.etPorc);

        Trans=(TextView) findViewById(R.id.tvTransc);
        Acomu=(TextView) findViewById(R.id.tvAcomulado);
        Prom=(TextView) findViewById(R.id.tvPromedio);
        NotS=(TextView) findViewById(R.id.tvNotaSa);

        procesar=(Button) findViewById(R.id.btnProcesar);
        reiniciar=(Button) findViewById(R.id.btnReiniciar);
        limp=(Button) findViewById(R.id.btnLimpiar);
    }

    //**********************************************************************************************


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Inicialisar();
    }

    public void OneclickProceso(View view){
        SonidoClic();
        try
        {
            if(ac==100){
                Limpiar();
                Not.setEnabled(false);
                Porc.setEnabled(false);
            }
            String a=Not.getText().toString();
            String b=Porc.getText().toString();
            ac=Double.parseDouble(b)+ac;
            double nota=Double.parseDouble(a);







            if(!a.equals("") && !b.equals("")){
                n.setDblNota(Double.parseDouble(a));
                n.setDblPorcentaje(Double.parseDouble(b));}
            else {



                Mensaje("!Pueden faltar datos¡");

                SonidoExit();

                return;
            }


            if((nota>5 || nota<0 )&& ac>100){
                ac=n.getDblPorcTranscu();
                Mensaje("Nota y porcentaje no valido");
                Limpiar();
                SonidoExit();
                return;
            }

            if(ac>100 || ac<0){
                ac=n.getDblPorcTranscu();
                String j=Double.toString((100-ac));
                Mensaje("Porcentaje no valido porcentaje faltante : " + j+"%");

                Porc.setText("");
                Porc.requestFocus();
                SonidoExit();
                return;

            }

            if(nota>5 || nota<0 ){
                Mensaje("Nota no valida");
                SonidoExit();
                Not.setText("");
                Not.requestFocus();
                return;
            }

            if (!n.proceso()) {
                SonidoExit();

                Mensaje(n.getStrError());
                n = null;
                return;
            }
            Limpiar();

            Acomu.setText(formatNumber(n.getDblAcomulado()));
            //Acomu.setText(Double.toString(n.getDblAcomulado()));

            //Prom.setText(Double.toString(n.getDblNotaLleva()));
            Prom.setText(formatNumber(n.getDblNotaLleva()));

            //NotS.setText(Double.toString(n.getDblNotaSacar()));
            NotS.setText(formatNumber(n.dblNotaSacar));
            Trans.setText(Double.toString(n.getDblPorcTranscu())+"%");


            if(n.getDblPorcTranscu()==100){
                procesar.setEnabled(false);
                Not.setEnabled(false);
                Porc.setEnabled(false);
                Mensaje("porcentae completo");
            }
        }
        catch (Exception ex)
        {
            SonidoExit();
            Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
        }




    }

    public void OneclickReiniciar (View view){
        SonidoTup();
        n=null;
        n=new Notas();
        ac=0;
        Not.setEnabled(true);
        Porc.setEnabled(true);
        procesar.setEnabled(true);
        Limpiar();
        Limpiar2();
    }

    public void OneClickLimpiar(View view){

        SonidoTup();
        Intent i=new Intent(this,Ayuda.class);
        startActivity(i);
    }



    //Formatear Números
    public String formatNumber(double n){
        try {
            DecimalFormat f = new DecimalFormat("#.##");;
            return f.format(n);

        }catch (Exception ex){
            Log.e("error formatNumber", ex.getMessage());
            return "NaN";
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_help:
                Toast.makeText(getApplicationContext(),"sadf",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item_acercade:
                Toast.makeText(getApplicationContext(),"mensaje simple",Toast.LENGTH_SHORT).show();
                return  true;
            case R.id.item_salir:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
