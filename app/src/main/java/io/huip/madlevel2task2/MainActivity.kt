package io.huip.madlevel2task2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import io.huip.madlevel2task2.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val questions = arrayListOf<Question>()

    private val questionAdapter = QuestionAdapter(questions)


    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // TODO: make adding questions neater
        questions.add(Question("macOS Big Sur looks neat", true))
        questions.add(Question("2 + 3 = 4", false))
        questions.add(Question("My opinions are fact", true))

        initViews()
    }

    private fun initViews() {
        binding.rvQuiz.layoutManager =
            LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
        binding.rvQuiz.adapter = questionAdapter
        binding.rvQuiz.addItemDecoration(DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL))
        createItemTouchHelper().attachToRecyclerView(binding.rvQuiz)
    }

    private fun createItemTouchHelper(): ItemTouchHelper {
        val callback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT
                or ItemTouchHelper.RIGHT) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }


            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                questionAdapter.notifyItemChanged(viewHolder.adapterPosition)
                val position = viewHolder.adapterPosition

                // Right swipe = 8, left = 4
                if(questions[position].answer && direction == 8 || !questions[position].answer && direction == 4) {
                    questions.removeAt(position) // Removes item from list if correct
                    questionAdapter.notifyDataSetChanged() // So the app doesn't crash if you answer top question first

                } else {
                    Snackbar.make(rvQuiz, getString(R.string.incorrect), Snackbar.LENGTH_SHORT).show()
                }
            }
        }
        return ItemTouchHelper(callback)
    }

}