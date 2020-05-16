package com.my.todoboom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TodoAdapter extends RecyclerView.Adapter<TodoHolder> {

    public interface TodoClickListener {
        public void onTodoClicked(int id);
    }

    public interface TodoLongClickListener {
        public void onTodoLongClickListener(int id);
    }

    private TodoListInfoManager todos;
    private TodoClickListener todoClickListener;
    private TodoLongClickListener todoLongClickListener;

    public TodoAdapter(TodoListInfoManager todos) {
        this.todos = todos;
    }

    public void addTodo(TodoItem todo) {
        this.todos.add(todo);
        notifyDataSetChanged();
    }

    public void clearTodos() {
        this.todos.clear();
        notifyDataSetChanged();
    }

    public void deleteTodo(int id) {
        this.todos.delete(id);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TodoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_one_todo, parent, false);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return new TodoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoHolder holder, final int position) {
        final TodoItem todo = todos.get(position);
        holder.todoText.setText(todo.getDescription());
        holder.todoText.setAlpha(todo.isDone() ? 0.5f : 1.0f);
        holder.tickBox.setImageResource(todo.isDone() ?
                R.drawable.ticked_box_icon : R.drawable.empty_box_icon);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (todoClickListener != null) {
                    todoClickListener.onTodoClicked(position);
                    notifyDataSetChanged();
                }
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (todoLongClickListener != null) {
                    todoLongClickListener.onTodoLongClickListener(position);
                    notifyDataSetChanged();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return todos.size();
    }

    public void setTodoClickListener(TodoClickListener todoClickListener) {
        this.todoClickListener = todoClickListener;
    }
    public void setTodoLongClickListener(TodoLongClickListener todoLongClickListener) {
        this.todoLongClickListener = todoLongClickListener;
    }
}
