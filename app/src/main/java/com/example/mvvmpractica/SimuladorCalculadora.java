package com.example.mvvmpractica;

public class SimuladorCalculadora {
    public static class Calculator{
        public float numero1;
        public String operador;
        public float numero2;

        public Calculator(float numero1, String operador, float numero2) {
            this.numero1 = numero1;
            this.operador = operador;
            this.numero2 = numero2;
        }
    }

    interface Callback {
        void cuandoEsteCalculadoElResultado(double resultado);
        void cuandoHayaErrorDeDivisionEntreCero(double numeroCero);
        void cuandoElOperadorSeaErroneo(String operadorErroneo);
        void cuandoEmpieceElCalculo();
        void cuandoFinaliceElCalculo();
    }

    public void calcular(Calculator calculator, Callback callback) {
        double numeroCero = 0;
        String operadorErroneo = "";
        callback.cuandoEmpieceElCalculo();
        try {
            Thread.sleep(2500);  // long run operation
        } catch (InterruptedException e) {
        }

        boolean error = false;
        if (calculator.numero2 == 0 && calculator.operador.equals("/")) {
            callback.cuandoHayaErrorDeDivisionEntreCero(numeroCero);
            error = true;
        }

        if (!calculator.operador.equals("/") && !calculator.operador.equals("*") &&
        !calculator.operador.equals("-") && !calculator.operador.equals("+")) {
            callback.cuandoElOperadorSeaErroneo(operadorErroneo);
            error = true;
        }

        if(!error) {
            if(calculator.operador.equals("+")){
                callback.cuandoEsteCalculadoElResultado(calculator.numero1 + calculator.numero2);
            }
            else if(calculator.operador.equals("-")){
                callback.cuandoEsteCalculadoElResultado(calculator.numero1 - calculator.numero2);
            }
            else if(calculator.operador.equals("*")){
                callback.cuandoEsteCalculadoElResultado(calculator.numero1 * calculator.numero2);
            }
            else{
                callback.cuandoEsteCalculadoElResultado(calculator.numero1 / calculator.numero2);
            }
        }
        callback.cuandoFinaliceElCalculo();
    }

    public float calcular(Calculator calculator) {
        if(calculator.operador.equals("+")){
            return calculator.numero1 + calculator.numero2;
        }
        else if(calculator.operador.equals("-")){
            return calculator.numero1 - calculator.numero2;
        }
        else if(calculator.operador.equals("*")){
            return calculator.numero1 * calculator.numero2;
        }
        else{
            return calculator.numero1 / calculator.numero2;
        }
    }
}
