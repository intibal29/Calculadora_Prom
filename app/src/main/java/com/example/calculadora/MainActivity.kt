package com.example.calculadora

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import es.israeldelamo.openwebinar2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        /**
         * Etiqueta de depuración
         */
        const val TAG = "DEPURANDO"
    }

    private var primerNumero = 0.0
    private var segundoNumero = 0.0
    private var operacion: String? = null


    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inflamos el layout con binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicialización de variables
        operacion = null

        // Configuración del OnClickListener para cada botón
        listOf(
            binding.boton0, binding.boton1, binding.boton2, binding.boton3,
            binding.boton4, binding.boton5, binding.boton6, binding.boton7,
            binding.boton8, binding.boton9, binding.botonMas, binding.botonMenos,
            binding.botonPor, binding.botonDividir, binding.botonComa,
            binding.botonLimpiar, binding.botonIgual
        ).forEach { it.setOnClickListener(this) }
    }

    override fun onClick(view: View?) {
        when (view) {
            // Zona de números
            binding.boton0 -> onNumeroPulsado("0")
            binding.boton1 -> onNumeroPulsado("1")
            binding.boton2 -> onNumeroPulsado("2")
            binding.boton3 -> onNumeroPulsado("3")
            binding.boton4 -> onNumeroPulsado("4")
            binding.boton5 -> onNumeroPulsado("5")
            binding.boton6 -> onNumeroPulsado("6")
            binding.boton7 -> onNumeroPulsado("7")
            binding.boton8 -> onNumeroPulsado("8")
            binding.boton9 -> onNumeroPulsado("9")
            binding.botonComa -> onNumeroPulsado(",")

            // Zona de operadores
            binding.botonMas -> onOperadorPulsado("+")
            binding.botonMenos -> onOperadorPulsado("-")
            binding.botonPor -> onOperadorPulsado("*")
            binding.botonDividir -> onOperadorPulsado("/")
            binding.botonLimpiar -> onCEPulsado()
            binding.botonIgual -> onIgualPulsado()
        }
    }

    /**
     * Dibuja el número pulsado en la pantalla de la calculadora
     */
    private fun onNumeroPulsado(numero: String) {
        dibujarPantalla(numero)
        actualizarOperandos()
    }

    /**
     * Refresca el campo de texto de la pantalla de la calculadora
     */
    private fun dibujarPantalla(numero: String) {
        val nuevoTexto = if (binding.pantallaCalculadora.text == "0" && numero != ",") {
            numero
        } else {
            "${binding.pantallaCalculadora.text}$numero"
        }
        binding.pantallaCalculadora.text = nuevoTexto
    }

    /**
     * Actualiza el primer o segundo número en función de si hay una operación en curso
     */
    private fun actualizarOperandos() {
        if (operacion == null) {
            primerNumero = binding.pantallaCalculadora.text.toString().toDouble()
        } else {
            segundoNumero = binding.pantallaCalculadora.text.toString().toDouble()
        }
    }

    /**
     * Maneja la operación seleccionada y prepara la pantalla para recibir el siguiente operando
     */
    private fun onOperadorPulsado(operacion: String) {
        this.operacion = operacion
        primerNumero = binding.pantallaCalculadora.text.toString().toDouble()
        binding.pantallaCalculadora.text = "0"
    }

    /**
     * Calcula el resultado de la operación seleccionada y lo muestra en pantalla
     */
    private fun onIgualPulsado() {
        val resultado = when (operacion) {
            "+" -> primerNumero + segundoNumero
            "-" -> primerNumero - segundoNumero
            "*" -> primerNumero * segundoNumero
            "/" -> if (segundoNumero != 0.0) primerNumero / segundoNumero else Double.NaN
            else -> 0.0
        }

        operacion = null
        primerNumero = resultado

        try {
            binding.pantallaCalculadora.text = if (resultado.toString().endsWith(".0")) {
                resultado.toInt().toString()
            } else {
                "%.2f".format(resultado)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error al mostrar el resultado", e)
        }
    }

    /**
     * Restablece la calculadora a su estado inicial
     */
    private fun onCEPulsado() {
        binding.pantallaCalculadora.text = "0"
        primerNumero = 0.0
        segundoNumero = 0.0
        operacion = null
    }
}
