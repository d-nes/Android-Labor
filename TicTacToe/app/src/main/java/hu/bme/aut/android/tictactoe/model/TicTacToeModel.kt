package hu.bme.aut.android.tictactoe.model

import android.util.Log
import java.sql.Types.NULL

object TicTacToeModel {

    const val EMPTY: Byte = 0
    const val CIRCLE: Byte = 1
    const val CROSS: Byte = 2

    var nextPlayer: Byte = CIRCLE

    private var model: Array<ByteArray> = arrayOf(
            byteArrayOf(EMPTY, EMPTY, EMPTY),
            byteArrayOf(EMPTY, EMPTY, EMPTY),
            byteArrayOf(EMPTY, EMPTY, EMPTY))

    fun resetModel() {
        for (i in 0 until 3) {
            for (j in 0 until 3) {
                model[i][j] = EMPTY
            }
        }
    }

    fun getFieldContent(x: Int, y: Int): Byte {
        return model[x][y]
    }

    fun changeNextPlayer() {
        if (nextPlayer == CIRCLE) {
            nextPlayer = CROSS
        } else {
            nextPlayer = CIRCLE
        }
    }

    fun setFieldContent(x: Int, y: Int, content: Byte): Byte {
        changeNextPlayer()
        model[x][y] = content
        return content
    }

    fun checkStatus() : Boolean {
        //Log.d("checkStatus()","call")

        //check rows
        for(i in 0..2){
            //Log.d("checkStatus()", "$i row")
            var player = getFieldContent(0, i)
            if(player != EMPTY){
                //Log.d("checkStatus()", "not empty: $player")
                if(getFieldContent(1, i) == player && getFieldContent(2, i) == player){
                    Log.i("checkStatus()", "row wins")
                    return true;
                }
            }
        }

        //check columns
        for(i in 0..2){
            //Log.d("checkStatus()", "$i column")
            var player = getFieldContent(i, 0)
            if(player != EMPTY){
                //Log.d("checkStatus()", "not empty: $player")
                if(getFieldContent(i, 1) == player && getFieldContent(i, 2) == player){
                    Log.i("checkStatus()", "column wins")
                    return true;
                }
            }
        }

        //check diagonals
        var player = getFieldContent(0,0)
        if(player != EMPTY && getFieldContent(1,1) == player && getFieldContent(2,2) == player){
            Log.i("checkStatus()", "diagonal 1 wins")
            return true
        }
        player = getFieldContent(2,0)
        if(player != EMPTY && getFieldContent(1,1) == player && getFieldContent(0,2) == player){
            Log.i("checkStatus()", "diagonal 2 wins")
            return true
        }


        //check even
        for(x in 0..2){
            for(y in 0..2){
                if(getFieldContent(x, y) == EMPTY){
                    return false
                }
                else if(x == 2 && y == 2){
                    Log.i("checkStatus()", "even")
                    return true
                }
            }
        }


        return false
    }

}