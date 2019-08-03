package com.example.ilibrary.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.ilibrary.R;
import com.example.ilibrary.adapters.ReservedBookAdapter;
import com.example.ilibrary.databinding.ReservationsBinding;
import com.example.ilibrary.interfaces.ReservedBooksAdmin;
import com.example.ilibrary.models.data.ReservedBook;
import com.example.ilibrary.presetners.ReservedBooksAdminPresenter;

import java.util.List;

public class ReservedBooksAdminFragment extends Fragment implements ReservedBooksAdmin.ReservedBooksAdminFragment {


    private ReservationsBinding reservationsBinding;
    private ReservedBookAdapter adapter;
    private ReservedBooksAdmin.ReservedBooksAdminPresenter presenter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        reservationsBinding = DataBindingUtil.inflate(inflater, R.layout.reservations, container, false);
        presenter = new ReservedBooksAdminPresenter(this);
        presenter.getBooks();

        return reservationsBinding.getRoot();
    }

    @Override
    public void setReservedBooksAdapter(List<ReservedBook> books) {

        reservationsBinding.reservationsRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        adapter = new ReservedBookAdapter(books, this.getActivity());

        reservationsBinding.reservationsRecyclerView.setAdapter(adapter);

    }
}
