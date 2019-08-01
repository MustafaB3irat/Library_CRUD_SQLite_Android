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
import com.example.ilibrary.adapters.ViewBooksAdapter;
import com.example.ilibrary.databinding.ReservationsBinding;
import com.example.ilibrary.interfaces.MyCart;
import com.example.ilibrary.models.data.Book;
import com.example.ilibrary.presetners.MyCartPresenter;

import java.util.List;

public class MyCartFragment extends Fragment implements MyCart.MyCartFragment {


    private ReservationsBinding reservationsBinding;
    private ViewBooksAdapter adapter;
    private MyCart.MyCartPresenter presenter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        reservationsBinding = DataBindingUtil.inflate(inflater, R.layout.reservations, container, false);
        presenter = new MyCartPresenter(this);
        presenter.getBooks();

        return reservationsBinding.getRoot();
    }

    @Override
    public void setViewBooksAdapter(List<Book> books) {

        reservationsBinding.reservationsRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        adapter = new ViewBooksAdapter(books, this.getActivity(), 1);

        reservationsBinding.reservationsRecyclerView.setAdapter(adapter);

    }
}
