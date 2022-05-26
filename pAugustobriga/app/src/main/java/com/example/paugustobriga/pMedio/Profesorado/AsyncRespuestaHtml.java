package com.example.paugustobriga.pMedio.Profesorado;

import java.util.ArrayList;

public interface AsyncRespuestaHtml {
    void procesoFinalizado(ArrayList<Object[]> salida);

    void errorDeConexion();
}
