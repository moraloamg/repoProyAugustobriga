package com.example.paugustobriga.pMedio.Profesorado;

import java.util.ArrayList;

public interface AsyncRespuesta {
    void procesoFinalizado(ArrayList<Object[]> salida);

    void errorDeConexion();
}
