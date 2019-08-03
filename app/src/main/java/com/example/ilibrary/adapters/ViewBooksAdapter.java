package com.example.ilibrary.adapters;

import android.content.DialogInterface;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.ilibrary.databinding.BookCardviewBinding;
import com.example.ilibrary.models.DatabaseHelper;
import com.example.ilibrary.models.data.Book;
import com.example.ilibrary.views.SignInActivity;

import java.util.List;

public class ViewBooksAdapter extends RecyclerView.Adapter<ViewBooksAdapter.BooksViewHolder> {

    private List<Book> books;
    private DatabaseHelper databaseHelper;
    private FragmentActivity activity;
    private int type;
    private Cursor cursor;

    public ViewBooksAdapter(List<Book> books, FragmentActivity activity, int type) {
        databaseHelper = new DatabaseHelper(activity);
        this.cursor = databaseHelper.getReservedBooks(SignInActivity.username);
        this.activity = activity;
        this.books = books;
        this.type = type;

    }


    @NonNull
    @Override
    public BooksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        BookCardviewBinding bookCardviewBinding = BookCardviewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        BooksViewHolder booksViewHolder = new BooksViewHolder(bookCardviewBinding);
        return booksViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BooksViewHolder holder, int position) {

        Book book = books.get(position);

        if (type != 1 && !book.getIsbn().equals("")) {

            holder.bookCardviewBinding.setBook(book);


            if (!databaseHelper.isReserved(book.getIsbn(), SignInActivity.username)) {

                initReserveButton(holder.bookCardviewBinding.reserve, book.getIsbn());

            } else {
                holder.bookCardviewBinding.reserve.setText("Reserved");
                holder.bookCardviewBinding.reserve.setEnabled(false);
            }

        } else if (!book.getIsbn().equals("")) {

            cursor.moveToNext();
            if (Long.parseLong(book.getIsbn()) == cursor.getLong(cursor.getColumnIndex("isbn"))) {
                holder.bookCardviewBinding.setBook(book);

                holder.bookCardviewBinding.reserve.setText("Cancel");
                holder.bookCardviewBinding.reserve.setEnabled(true);

                initCancelButton(holder.bookCardviewBinding.reserve, holder.bookCardviewBinding.layout, book.getIsbn());


            }


        } else {
            holder.bookCardviewBinding.reserve.setVisibility(View.GONE);
            holder.bookCardviewBinding.bookAuthor.setVisibility(View.GONE);
            holder.bookCardviewBinding.bookPages.setVisibility(View.GONE);
            holder.bookCardviewBinding.bookPublisher.setVisibility(View.GONE);
            holder.bookCardviewBinding.setBook(book);
        }


    }


    private void initCancelButton(final Button cancel, ConstraintLayout layout, String isbn) {

        cancel.setOnClickListener(e -> {

            databaseHelper.cancelReservation(isbn);

            YoYo.with(Techniques.FadeOut).duration(600).playOn(layout);


        });

    }

    private void initReserveButton(final Button reserve, String isbn) {

        reserve.setOnClickListener(view -> {

            if (databaseHelper.countReservations(SignInActivity.username) != 3) {

                if (databaseHelper.reserveABook(Long.parseLong(isbn), SignInActivity.username)) {

                    AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
                    alertDialog.setTitle("Success!");
                    alertDialog.setMessage("Book Reserved Successfully!");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            (dialog, which) -> dialog.dismiss());
                    alertDialog.show();

                    reserve.setText("Reserved");
                    reserve.setEnabled(false);

                }

            } else {
                AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
                alertDialog.setTitle("Error!");
                alertDialog.setMessage("you have Exceeded Number of reservations!");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        (dialog, which) -> dialog.dismiss());
                alertDialog.show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public class BooksViewHolder extends RecyclerView.ViewHolder {

        private BookCardviewBinding bookCardviewBinding;

        public BooksViewHolder(@NonNull BookCardviewBinding itemView) {
            super(itemView.getRoot());

            this.bookCardviewBinding = itemView;
        }
    }
}
