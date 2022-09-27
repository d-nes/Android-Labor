package hu.bme.aut.android.todo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.todo.R
import hu.bme.aut.android.todo.databinding.RowTodoBinding
import hu.bme.aut.android.todo.model.Todo

class SimpleItemRecyclerViewAdapter : RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>() {

    private val todoList = mutableListOf<Todo>()

    var itemClickListener: TodoItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        RowTodoBinding.inflate(
        LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val todo = todoList[position]
        
        holder.todo = todo

        holder.binding.tvTitle.text = todo.title
        holder.binding.tvDueDate.text = todo.dueDate

        val resource = when (todo.priority) {
            Todo.Priority.LOW -> R.drawable.ic_low
            Todo.Priority.MEDIUM -> R.drawable.ic_medium
            Todo.Priority.HIGH -> R.drawable.ic_high
        }
        holder.binding.ivPriority.setImageResource(resource)
    }

    fun addItem(todo: Todo) {
        val size = todoList.size
        todoList.add(todo)
        notifyItemInserted(size)
    }

    fun addAll(todos: List<Todo>) {
        val size = todoList.size
        todoList += todos
        notifyItemRangeInserted(size, todos.size)
    }

    fun deleteRow(position: Int) {
        todoList.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun getItemCount() = todoList.size

    inner class ViewHolder(val binding: RowTodoBinding) : RecyclerView.ViewHolder(binding.root) {
        var todo: Todo? = null

        init {
            itemView.setOnClickListener {
                todo?.let { todo -> itemClickListener?.onItemClick(todo) }
            }

            itemView.setOnLongClickListener { view ->
                itemClickListener?.onItemLongClick(adapterPosition, view)
                true
            }
        }
    }

    interface TodoItemClickListener {
        fun onItemClick(todo: Todo)
        fun onItemLongClick(position: Int, view: View): Boolean
    }
}