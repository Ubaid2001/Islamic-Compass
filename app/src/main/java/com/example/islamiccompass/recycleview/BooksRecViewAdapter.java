package com.example.islamiccompass.recycleview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.islamiccompass.R;
import com.example.islamiccompass.model.Book;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class BooksRecViewAdapter extends RecyclerView.Adapter<BooksRecViewAdapter.ViewHolder> {

    private Map<Integer, Book> books = new HashMap<>();
    onItemClickListener onItemClickListener;


    public BooksRecViewAdapter(BooksRecViewAdapter.onItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_list_item, parent, false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        for (Map.Entry<Integer, Book> entry : books.entrySet()) {
            Integer key = entry.getKey();
            Book value = entry.getValue();
            if(key == position){
                holder.bookName.setText(value.getName());
                holder.authorName.setText(value.getAuthor());
                String bookCover = value.getImagePath();

                Picasso.get().load(String.format("http://192.168.190.1:3001/bookImage/%s", bookCover)).into(holder.bookImage);

            } else System.out.println("NOT MATCH!!!!");

        }

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(view, holder.getAdapterPosition());
            }
        });

    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public void setBooks(Map<Integer, Book> books) {
        this.books = books;
        notifyDataSetChanged();
    }

    public Book getBookItem(int position){
        return books.get(position);
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView bookName;
        private TextView authorName;
        private ImageView bookImage;
        private CardView parent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bookName = itemView.findViewById(R.id.bookName);
            authorName = itemView.findViewById(R.id.authorName);
            bookImage = itemView.findViewById(R.id.bookImage);

            //Can use the parent to make toast and onclick actions
            parent = itemView.findViewById(R.id.parent);

        }
    }

    public interface onItemClickListener{
        void onItemClick(View view , int position);
    }
}

