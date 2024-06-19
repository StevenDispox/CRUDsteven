package steven.flores.stevenpalacios

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textview.MaterialTextView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import modelo.ClaseConexion
import steven.flores.stevenpalacios.R.id.btnRegresarLogin
import java.util.UUID

class Register : AppCompatActivity() {
    @SuppressLint("WrongViewCast", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //1- Mando a llamar a todos los elemntos
        val txtCorreoRegistro = findViewById<EditText>(R.id.txtCorreoRegistro)
        val txtPasswordRegistro = findViewById<EditText>(R.id.txtPasswordRegistro)
        val txtConfirmarPassword = findViewById<EditText>(R.id.txtConfirmarPassword)
        val btnCrearCuenta = findViewById<Button>(R.id.btnCrearCuenta)
        val btnRegresarLogin = findViewById<Button>(R.id.btnRegresarLogin)

        //2- Prograar botones
        //TODO: Boton para crear la cuenta
        //Al darle clic al boton se hace un insert a la base con los valores que escribe el usuario
        btnCrearCuenta.setOnClickListener{
            GlobalScope.launch(Dispatchers.IO) {
                //Creo obj clase conexion
                val objConexion = ClaseConexion().cadenaConexion()

                //Creo una variable que contenga un PrepareStatement
                val crearUsuario =
                    objConexion?.prepareStatement("INSERT INTO tbUsuarios(UUID_usuario, correoElectronico, clave) VALUES (?, ?, ?)")!!
                crearUsuario.setString(1, UUID.randomUUID().toString())
                crearUsuario.setString(2, txtCorreoRegistro.text.toString())
                crearUsuario.setString(3, txtPasswordRegistro.text.toString())
                crearUsuario.executeUpdate()
                withContext(Dispatchers.Main) {
                    //Abro otro corrutina o "Hilo" para mostrar un mensaje y limpiar los campos
                    //Lo hago en el Hilo Main por que en el hilo IO no permite mostrar nada en pantalla
                    Toast.makeText(this@Register, "Usuario creado", Toast.LENGTH_SHORT).show()
                    txtCorreoRegistro.setText("")
                    txtPasswordRegistro.setText("")
                    txtConfirmarPassword.setText("")

                }
            }
        }
        //Regresar al login al darle ya tengo cuenta
        btnRegresarLogin.setOnClickListener{
            val pantallaLogin = Intent(this, Login::class.java)
            startActivity(pantallaLogin)
        }
    }
}