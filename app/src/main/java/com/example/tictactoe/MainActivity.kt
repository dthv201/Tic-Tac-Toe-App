package com.example.tictactoe
import android.util.Log
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.tictactoe.databinding.ActivityMainBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class MainActivity : AppCompatActivity()
{
    enum class Turn
    {
        X,
        O
    }
    private var firstTurn = Turn.X
    private var currentTurn = Turn.X

    private var boardList = mutableListOf<Button>()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBoard()
        binding.playAgainButton.setOnClickListener {
            resetBoard()
        }

    }

    private fun initBoard() {
        boardList.add(binding.b00)
        boardList.add(binding.b01)
        boardList.add(binding.b02)
        boardList.add(binding.b10)
        boardList.add(binding.b11)
        boardList.add(binding.b12)
        boardList.add(binding.b20)
        boardList.add(binding.b21)
        boardList.add(binding.b22)

    }

    fun boardTapped(view: View) {
        var flag = false
        if (view !is Button) {
            return
        }
        addToBoard(view)
        if(checkForVictory("O"))
        {
            result("Noughts Win!")
        }
        else if(checkForVictory("X"))
        {
            result("Crosses Win!")
        }

        else if (fullBoard())
        {
            result("Draw")
        }
    }
    private fun match(button: Button, symbol : String): Boolean = button.text == symbol
    private fun checkForVictory(s: String): Boolean
    {
        //Horizontal Victory
        if(match(binding.b00,s) && match(binding.b01,s) && match(binding.b02,s))
            return true
        if(match(binding.b10,s) && match(binding.b11,s) && match(binding.b12,s))
            return true
        if(match(binding.b20,s) && match(binding.b21,s) && match(binding.b22,s))
            return true

        //Vertical Victory
        if(match(binding.b00,s) && match(binding.b10,s) && match(binding.b20,s))
            return true
        if(match(binding.b01,s) && match(binding.b11,s) && match(binding.b21,s))
            return true
        if(match(binding.b02,s) && match(binding.b12,s) && match(binding.b22,s))
            return true

        //Diagonal Victory
        if(match(binding.b00,s) && match(binding.b11,s) && match(binding.b22,s))
            return true
        if(match(binding.b02,s) && match(binding.b11,s) && match(binding.b20,s))
            return true

        return false
    }

    private fun result(title: String) {

        binding.gameResultTV.text = title
        binding.gameResultTV.visibility = View.VISIBLE

        binding.playAgainButton.visibility = View.VISIBLE

        for (button in boardList)
        {
            button.isEnabled = false
        }
    }

    private fun resetBoard() {
        for (button in boardList)
        {
            button.text = ""
            button.isEnabled = true
        }
        binding.gameResultTV.visibility = View.GONE
        binding.playAgainButton.visibility = View.GONE

        currentTurn = firstTurn
        setTurnLabel()
    }

    private fun fullBoard(): Boolean
    {
        for (button in boardList)
        {
            Log.d("FullBoardCheck", "Button ${button.id}: ${button.text}")
            if (button.text == null || button.text.isEmpty())
                return false
        }
        return true

    }

    private fun addToBoard(button: Button)
    {
        if(button.text != "")
        {
            return
        }
        if (currentTurn == Turn.O)
        {
            button.text = "O"
            button.setTextColor(resources.getColor(R.color.blue, null))
            currentTurn = Turn.X
        }
        else
        {
            button.text = "X"
            button.setTextColor(resources.getColor(R.color.red, null))

            currentTurn = Turn.O
        }
        setTurnLabel()
    }

    private fun setTurnLabel() {
        var turnText = ""
        if(currentTurn == Turn.O)
        {
            turnText = "O's Turn"
        }
        else
        {
            turnText = "X's Turn"
        }
        binding.turnTV.text = turnText
    }


}