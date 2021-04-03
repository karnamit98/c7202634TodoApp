package com.tbcmad.todoapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.EthiopicCalendar;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tbcmad.todoapp.model.ETodo;
import com.tbcmad.todoapp.viewModel.TodoViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ListTodoFragment extends Fragment {
    View rootView;
    RecyclerView rvListTodo;
    TodoViewModel viewModel;
    TodoAdaptor adaptor;
    SharedPreferences preferences;
    int userId = -1;
    public String TAG = "user test";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_list_todo, container, false);
        rvListTodo = rootView.findViewById(R.id.list_item_todo_rv_list_todo);
        viewModel = new ViewModelProvider(this).get(TodoViewModel.class);

        LinearLayoutManager manager= new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvListTodo.setLayoutManager(manager);


//        preferences = getContext().getSharedPreferences("todo_pref", 0);
//         userId = preferences.getInt("userId", -1);
        updateRV();


        new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                List<ETodo> todoList = viewModel.getAllTodos().getValue();
                TodoAdaptor adaptor = new TodoAdaptor(todoList);
                ETodo todo = adaptor.getTodoAt(viewHolder.getAdapterPosition());
                viewModel.deleteById(todo);
            }
        }).attachToRecyclerView(rvListTodo);

        return  rootView;
    }

    void updateRV(){
        viewModel.getAllTodos().observe(this, new Observer<List<ETodo>>() {
            @Override
            public void onChanged(List<ETodo> eTodos) {
                SharedPreferences preferences = getContext().getSharedPreferences("todo_pref", 0);
                 int userId = preferences.getInt("userId", -1);
                List<ETodo> eTodosUserSpecific = new ArrayList<>();int i=1;
                for (ETodo eTodo1: eTodos ) {
                    if(eTodo1.getUser_id() == userId){ eTodosUserSpecific.add(eTodo1);
                    Log.d(TAG, "Entered Condition; loopNo"+i); }
                    Log.d(TAG, "currentUserID:"+userId+ "totalTODOS:"+eTodos.size()+", specificTODOS:"+eTodosUserSpecific.size()+", loopNo:"
                            +(i++)+", todo User Id:"+eTodo1.getUser_id()+", todoTitle"+eTodo1.getTitle());
                }
                Log.d(TAG, "currentUserID:"+userId+ "totalTODOS:"+eTodos.size()+", specificTODOS:"+eTodosUserSpecific.size() );
                adaptor = new TodoAdaptor(eTodosUserSpecific);
               // TodoAdaptor adaptor = new TodoAdaptor(eTodos);

                rvListTodo.setAdapter(adaptor);
            }
        });

    }

    private class TodoHolder extends RecyclerView.ViewHolder{
        TextView title, date, description , isCompleted;

        public TodoHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.list_item_todo, parent, false));
            title = itemView.findViewById(R.id.list_item_tv_title);
            date = itemView.findViewById(R.id.list_item_tv_date);
            description = itemView.findViewById(R.id.list_item_tv_description);
            isCompleted = itemView.findViewById(R.id.list_item_tv_isCompleted);

            title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadUpdateItem();
                }
            });
            date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadUpdateItem();
                }
            });
            description.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) { loadUpdateItem();  }
            });
            isCompleted.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) { loadUpdateItem();  }
            });
        }

        void loadUpdateItem(){
            //TodoAdaptor adaptor = new TodoAdaptor(viewModel.getAllTodos().getValue());
            int j = getAdapterPosition();
           // int i =-1;
            //Log.d(TAG,"j:"+j+", j+1:"+(j+1));
            ETodo todo;
            if(j-1 >= 0)
                 todo = adaptor.getTodoAt(j);  //(i);
            else
                todo = adaptor.getTodoAt(0);
            Intent intent = new Intent(getActivity(), EditActivity.class);
            intent.putExtra("TodoId",todo.getId());
            startActivity(intent);
            Toast.makeText(getContext(),"Update Item: " + todo.getId(), Toast.LENGTH_LONG).show();
        }
        public void bind(ETodo todo){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            title.setText(todo.getTitle());
            date.setText(sdf.format(todo.getTodoDate()));
            description.setText(todo.getDescription());
            if(todo.isCompleted()) {
                isCompleted.setText("Completed");
                //isCompleted.setTextColor(getResources().getColor(R.color.completed));
                isCompleted.setBackgroundColor(getResources().getColor(R.color.completed));
            }
            else {
                isCompleted.setText("Not Completed");
               // isCompleted.setTextColor(getResources().getColor(R.color.not_completed));
                isCompleted.setBackgroundColor(getResources().getColor(R.color.not_completed));
            }
        }
    }

    private class TodoAdaptor extends RecyclerView.Adapter<TodoHolder>{
        List<ETodo> eTodoList;
        public TodoAdaptor(List<ETodo> todoList)
        {
            eTodoList = todoList;
        }

        @NonNull
        @Override
        public TodoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new TodoHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull TodoHolder holder, int position) {
            ETodo todo = eTodoList.get(position);
            LinearLayout layout =(LinearLayout)((ViewGroup)holder.title.getParent().getParent());

            switch (todo.getPriority()) {
                case 1:
                    layout.setBackgroundColor(getResources().getColor(R.color.color_high));
                    break;
                case 2:
                    layout.setBackgroundColor(getResources().getColor(R.color.color_medium));
                    break;
                case 3:
                    layout.setBackgroundColor(getResources().getColor(R.color.color_low));
                    break;
            }
            holder.bind(todo);
        }

        @Override
        public int getItemCount() {
            return eTodoList.size();
        }

        public ETodo getTodoAt(int position){
            return eTodoList.get(position);
        }
    }
}