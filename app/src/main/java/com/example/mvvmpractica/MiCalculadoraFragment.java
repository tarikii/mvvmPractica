package com.example.mvvmpractica;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.mvvmpractica.databinding.FragmentMiCalculadoraBinding;

public class MiCalculadoraFragment extends Fragment {
    private FragmentMiCalculadoraBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentMiCalculadoraBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.calcular.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onClick(View view) {

                SimuladorCalculadora calculadora = new SimuladorCalculadora();

                float numero1 = Float.parseFloat(binding.numero1.getText().toString());
                String operador = binding.operador.getText().toString();
                float numero2 = Float.parseFloat(binding.numero2.getText().toString());

                SimuladorCalculadora.Calculator calc = new SimuladorCalculadora.Calculator(numero1,operador,numero2);

                float resultado = calculadora.calcular(calc);

                if(resultado % 1 == 0){
                    binding.resultado.setText(String.format("%.0f",resultado));
                }
                else{
                    binding.resultado.setText(String.format("%.2f",resultado));
                }
            }
        });
        final MiCalculadoraViewModel miCalculadoraViewModel = new ViewModelProvider(this).get(MiCalculadoraViewModel.class);

        binding.calcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                float numero1 = Float.parseFloat(binding.numero1.getText().toString());
                String operador = binding.operador.getText().toString();
                float numero2 = Float.parseFloat(binding.numero2.getText().toString());

                miCalculadoraViewModel.calcular(numero1, operador, (int) numero2);
            }
        });

        miCalculadoraViewModel.resultado.observe(getViewLifecycleOwner(), new Observer<Double>() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onChanged(Double resultado) {
                if(resultado % 1 == 0){
                    binding.resultado.setText(String.format("%.0f",resultado));
                }
                else{
                    binding.resultado.setText(String.format("%.2f",resultado));
                }
            }
        });

        miCalculadoraViewModel.numeroCero.observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double cero) {
                if (cero == 0) {
                    binding.numero2.setError("NO PUEDES DIVIDIR ENTRE " + cero + "!");
                } else {
                    binding.numero2.setError(null);
                }
            }
        });

        miCalculadoraViewModel.operadorErroneo.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String errorOperador) {
                if (!errorOperador.equals("/") && !errorOperador.equals("*") &&
                        !errorOperador.equals("-") && !errorOperador.equals("+")) {
                    binding.operador.setError("El operador " + errorOperador + " no es un " +
                            "operador válido!!");
                } else {
                    binding.operador.setError("");
                }
            }
        });

        miCalculadoraViewModel.calculando.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean calculando) {
                if (calculando) {
                    binding.calculando.setVisibility(View.VISIBLE);
                    binding.resultado.setVisibility(View.GONE);
                } else {
                    binding.calculando.setVisibility(View.GONE);
                    binding.resultado.setVisibility(View.VISIBLE);
                }
            }
        });

    }
}