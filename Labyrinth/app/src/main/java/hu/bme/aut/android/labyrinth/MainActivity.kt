package hu.bme.aut.android.labyrinth

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import hu.bme.aut.android.labyrinth.databinding.ActivityMainBinding
import hu.bme.aut.android.labyrinth.network.LabyrinthAPI
import kotlin.system.measureTimeMillis

class MainActivity : AppCompatActivity() {

    companion object {
        private const val MOVE_LEFT = 1
        private const val MOVE_UP = 2
        private const val MOVE_RIGHT = 3
        private const val MOVE_DOWN = 4
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var labyrinthAPI: LabyrinthAPI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        labyrinthAPI = LabyrinthAPI()

        var responseTime : Long = 0
        var networkState : Boolean = false
        binding.btnDown.setOnClickListener {
            coroutine {
                networkState = netWorkState()
                responseTime = measureTimeMillis {
                    labyrinthAPI.moveUser(binding.etUsername.text.toString(), MOVE_DOWN)
                }
            }
            if(!networkState)
                Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(this, responseTime.toString(), Toast.LENGTH_SHORT).show()
        }

        binding.btnUp.setOnClickListener {
            coroutine {
                networkState = netWorkState()
                responseTime = measureTimeMillis {
                    labyrinthAPI.moveUser(binding.etUsername.text.toString(), MOVE_UP)
                }
            }
            if(!networkState)
                Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(this, responseTime.toString(), Toast.LENGTH_SHORT).show()
        }

        binding.btnLeft.setOnClickListener {
            coroutine {
                networkState = netWorkState()
                responseTime = measureTimeMillis {
                    labyrinthAPI.moveUser(binding.etUsername.text.toString(), MOVE_LEFT)
                }
            }
            if(!networkState)
                Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(this, responseTime.toString(), Toast.LENGTH_SHORT).show()
        }

        binding.btnRight.setOnClickListener {
            coroutine {
                networkState = netWorkState()
                responseTime = measureTimeMillis {
                    labyrinthAPI.moveUser(binding.etUsername.text.toString(), MOVE_RIGHT)
                }
            }
            if(!networkState)
                Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(this, responseTime.toString(), Toast.LENGTH_SHORT).show()
        }
        binding.btnSend.setOnClickListener {
            coroutine {
                networkState = netWorkState()
                responseTime = measureTimeMillis {
                    labyrinthAPI.writeMessage(binding.etUsername.text.toString(), binding.etMessage.text.toString())
                }
            }
            if(!networkState)
                Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(this, responseTime.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun showResponse(response: String) {
        binding.tvResponse.text = response
    }

    private fun coroutine(call: () -> Unit) = Thread { call() }.start()

    private fun netWorkState() : Boolean{
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
}