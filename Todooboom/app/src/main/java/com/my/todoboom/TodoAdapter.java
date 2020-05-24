package com.my.todoboom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TodoAdapter extends RecyclerView.Adapter<TodoHolder> {

    private TodoboomApp app;

    public TodoAdapter(TodoboomApp app) {
        this.app = app;
    }

    public interface TodoClickListener {
        void onTodoClicked(int id);
    }

    private TodoClickListener todoClickListener;
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
        final TodoItem todo = app.todoListInfo.get(position);
        holder.todoText.setText(todo.getContent());
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
    }

    @Override
    public int getItemCount() {
        return app.todoListInfo.size();
    }

    public void setTodoClickListener(TodoClickListener todoClickListener) {
        this.todoClickListener = todoClickListener;
    }
}
