package steven.flores.stevenpalacios

import RecyclerViewHelper.Adaptador
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import modelo.ClaseConexion
import modelo.dataClassTicket
import java.util.UUID

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //1- Mando a llamar a todos los elementos de la vista
        val txtTituloTicket = findViewById<EditText>(R.id.txtTituloTicket)
        val txtDescripcionTicket = findViewById<EditText>(R.id.txtDescripcionTicket)
        val txtAutorTicket = findViewById<EditText>(R.id.txtAutorTicket)
        val txtCorreoAutor = findViewById<EditText>(R.id.txtCorreoAutor)
        val rcvTickets = findViewById<RecyclerView>(R.id.rcvTickets)


        //2- Programo el boton de enviar ticket
        btnEnviar.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {

                //3- Creo un objeto de la clase conexion

                val objConexion = ClaseConexion().cadenaConexion()

                //4- Creo una variable que contenga un prepare statement

                val addTicket =
                    objConexion?.prepareStatement("insert into tbTickets (numeroTicket, tituloTicket, descripcionTicket, Autor, correoAutor, EstadoTicket) values (?,?,?,?,?,)")!!
                addTicket.setString(1, UUID.randomUUID().toString())
                addTicket.setString(2, txtTituloTicket.text.toString())
                addTicket.setString(3, txtDescripcionTicket.text.toString())
                addTicket.setString(4, txtAutorTicket.text.toString())
                addTicket.setString(5, txtCorreoAutor.text.toString())
                addTicket.executeUpdate()
            }
            rcvTickets.layoutManager = LinearLayoutManager(this)

            //Mostrar datos
            fun obtenerDatos(): List<dataClassTicket>{
                val objConexion = ClaseConexion().cadenaConexion()

                val statement =objConexion?.createStatement()
                val resulSet = statement?.executeQuery("select * from tbTickets")!!

                val tickets = mutableListOf<dataClassTicket>()


                while (resulSet.next()){
                    val numeroTicket = resulSet.getString("NumeroTicket")
                    val tituloTicket = resulSet.getString("TituloTicket")
                    val descripcionTicket = resulSet.getString("descripcionTicket")
                    val autor = resulSet.getString("autor")
                    val correoAutor = resulSet.getString("correoAutor")
                    val estadoTicket = resulSet.getString("estadoTicket")


                    val ticket =dataClassTicket(numeroTicket, tituloTicket, descripcionTicket, autor, correoAutor, estadoTicket)
                    tickets.add(ticket)
                }
                return tickets
            }
            //Asignar el adaptador al recyclerView
            CoroutineScope(Dispatchers.IO).launch {
                val bdTickets = obtenerDatos()
                withContext(Dispatchers.Main){
                    val adapter = Adaptador(bdTickets)
                    rcvTickets.adapter =adapter
                }
            }
        }


        }
    }
}