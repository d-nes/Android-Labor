package hu.bme.aut.android.labyrinth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hu.bme.aut.android.labyrinth.databinding.ActivityMainBinding
import hu.bme.aut.android.labyrinth.events.MoveUserResponseEvent
import hu.bme.aut.android.labyrinth.events.WriteMessageResponseEvent
import hu.bme.aut.android.labyrinth.network.LabyrinthAPI
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

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

        binding.btnDown.setOnClickListener {
            coroutine {
                labyrinthAPI.moveUser(binding.etUsername.text.toString(), MOVE_DOWN)
            }
        }

        binding.btnUp.setOnClickListener {
            coroutine {
               labyrinthAPI.moveUser(binding.etUsername.text.toString(), MOVE_UP)
            }
        }

        binding.btnLeft.setOnClickListener {
            coroutine {
                labyrinthAPI.moveUser(binding.etUsername.text.toString(), MOVE_LEFT)
            }
        }

        binding.btnRight.setOnClickListener {
            coroutine {
                labyrinthAPI.moveUser(binding.etUsername.text.toString(), MOVE_RIGHT)
            }
        }
        binding.btnSend.setOnClickListener {
            coroutine {
                labyrinthAPI.writeMessage(binding.etUsername.text.toString(), binding.etMessage.text.toString())
            }
        }
    }

    private fun showResponse(response: String) {
        binding.tvResponse.text = response
    }

    private fun coroutine(call: () -> Unit) = Thread { call() }.start()

}