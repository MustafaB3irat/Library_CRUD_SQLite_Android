package com.example.ilibrary.adapters;

import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.ilibrary.databinding.AdminReservedBookCardviewBinding;
import com.example.ilibrary.models.DatabaseHelper;
import com.example.ilibrary.models.data.ReservedBook;
import com.example.ilibrary.views.SignInActivity;

import java.util.List;

public class ReservedBookAdapter extends RecyclerView.Adapter<ReservedBookAdapter.ReservedBookViewHolder> {

    private List<ReservedBook> books;
    private DatabaseHelper databaseHelper;
    private FragmentActivity activity;
    private Cursor cursor;

    public ReservedBookAdapter(List<ReservedBook> books, FragmentActivity activity) {
        databaseHelper = new DatabaseHelper(activity);
        this.cursor = databaseHelper.getReservedBooks(SignInActivity.username);
        this.activity = activity;
        this.books = books;

    }

    @NonNull
    @Override
    public ReservedBookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        AdminReservedBookCardviewBinding adminReservedBookCardviewBinding = AdminReservedBookCardviewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        ReservedBookViewHolder reservedBookViewHolder = new ReservedBookViewHolder(adminReservedBookCardviewBinding);
        return reservedBookViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ReservedBookViewHolder holder, int position) {

        ReservedBook reservedBook = books.get(position);
        holder.adminReservedBookCardviewBinding.setBook(reservedBook);

        initiateCancelButton(holder, reservedBook.getIsbn(), reservedBook.getReserved_by());


    }

    private void initiateCancelButton(ReservedBookViewHolder holder, String isbn, String reserved_by) {

        holder.adminReservedBookCardviewBinding.cancel.setOnClickListener(e -> {

            if (databaseHelper.cancelReservationByAdmin(isbn, reserved_by)) {
                YoYo.with(Techniques.FadeOut).duration(700).playOn(holder.adminReservedBookCardviewBinding.layout);
                holder.adminReservedBookCardviewBinding.layout.setVisibility(View.GONE);
            } else
                Toast.makeText(activity.getBaseContext(), "Error!", Toast.LENGTH_SHORT).show();

        });

    }

    @Override
    public int getItemCount() {
        return books.size();
    }


    public class ReservedBookViewHolder extends RecyclerView.ViewHolder {

        private AdminReservedBookCardviewBinding adminReservedBookCardviewBinding;

        public ReservedBookViewHolder(@NonNull AdminReservedBookCardviewBinding itemView) {
            super(itemView.getRoot());
            this.adminReservedBookCardviewBinding = itemView;
        }
    }
}
