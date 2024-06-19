package modelo

import android.telecom.Connection
import java.sql.DriverManager

class ClaseConexion {

    fun cadenaConexion(): java.sql.Connection?{
        try {
            val ip = "jdbc:oracle:thin:@192.168.100.201:1521:xe"
            val usuario = "STEVEN_PALACIOS"
            val contrasena = "2006"

            val conexion = DriverManager.getConnection(ip, usuario, contrasena)
            return conexion
        }catch (e: Exception){
            println("Este es el error: $e")
            return null
        }
    }
}