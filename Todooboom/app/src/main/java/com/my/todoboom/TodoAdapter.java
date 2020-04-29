package com.my.todoboom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoHolder> {

    public interface TodoClickListener {
        public void onTodoClicked(TodoItem todo);
    }

    private List<TodoItem> todos = new ArrayList<>();
    private TodoClickListener todoClickListener;

    public void setTodos(List<TodoItem> todos) {
        this.todos.clear();
        this.todos.addAll(todos);
        notifyDataSetChanged();
    }

    public void addTodo(TodoItem todo) {
        this.todos.add(todo);
        notifyDataSetChanged();
    }

    public void clearTodo() {
        this.todos.clear();
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
    public void onBindViewHolder(@NonNull TodoHolder holder, int position) {
        final TodoItem todo = todos.get(position);
        holder.todoText.setText(todo.getDescription());
        holder.todoText.setAlpha(todo.isDone()? 0.5f : 1.0f);
        holder.tickBox.setImageResource(todo.isDone()?
                R.drawable.ticked_box_icon : R.drawable.empty_box_icon);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (todoClickListener != null) {
                    todoClickListener.onTodoClicked(todo);
                    notifyDataSetChanged();
                }
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

    public ArrayList<String> getTodosAsStrings() {
        ArrayList<String> todoStrings = new ArrayList<>();
        for (TodoItem todo: todos) {
            todoStrings.add(todo.toString());
        }
        return todoStrings;
    }
}
