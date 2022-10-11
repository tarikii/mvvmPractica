package com.example.mvvmpractica;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MiCalculadoraViewModel extends AndroidViewModel {

    Executor executor;

    SimuladorCalculadora simulador;

    MutableLiveData<Double> resultado = new MutableLiveData<>();
    MutableLiveData<Double> numeroCero = new MutableLiveData<>();
    MutableLiveData<String> operadorErroneo = new MutableLiveData<>();
    MutableLiveData<Boolean> calculando = new MutableLiveData<>();

    public MiCalculadoraViewModel(@NonNull Application application) {
        super(application);

        executor = Executors.newSingleThreadExecutor();
        simulador = new SimuladorCalculadora();
    }

    public void calcular(float numero1, String operador, int numero2) {

        final SimuladorCalculadora.Calculator calc = new SimuladorCalculadora.Calculator(numero1,operador,numero2);

        executor.execute(new Runnable() {
            @Override
            public void run() {
                simulador.calcular(calc, new SimuladorCalculadora.Callback() {
                    @Override
                    public void cuandoEsteCalculadoElResultado(double resultadoOperacion) {
                        resultado.postValue((double)resultadoOperacion);
                    }

                    @Override
                    public void cuandoHayaErrorDeDivisionEntreCero(double cero) {
                        numeroCero.postValue(cero);
                    }

                    @Override
                    public void cuandoElOperadorSeaErroneo(String errorOperador) {
                        operadorErroneo.postValue(operador);
                    }

                    @Override
                    public void cuandoEmpieceElCalculo() {
                        calculando.postValue(true);
                    }

                    @Override
                    public void cuandoFinaliceElCalculo() {
                        calculando.postValue(false);
                    }

                });
            }
        });
    }
}
