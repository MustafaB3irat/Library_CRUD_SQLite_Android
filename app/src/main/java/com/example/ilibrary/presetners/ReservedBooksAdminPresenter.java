package com.example.ilibrary.presetners;

import androidx.fragment.app.Fragment;

import com.example.ilibrary.interfaces.ReservedBooksAdmin;
import com.example.ilibrary.models.ReservedBooksAdminModel;
import com.example.ilibrary.models.data.ReservedBook;
import com.example.ilibrary.views.fragments.ReservedBooksAdminFragment;

import java.util.List;

public class ReservedBooksAdminPresenter implements ReservedBooksAdmin.ReservedBooksAdminPresenter {


    private ReservedBooksAdmin.ReservedBooksAdminModel model;

    private ReservedBooksAdmin.ReservedBooksAdminFragment fragment;

    public ReservedBooksAdminPresenter(ReservedBooksAdminFragment fragment) {

        this.fragment = fragment;
        model = new ReservedBooksAdminModel(this, ((Fragment) fragment).getActivity());
    }

    @Override
    public void setBooks(List<ReservedBook> books) {

        fragment.setReservedBooksAdapter(books);

    }

    @Override
    public void getBooks() {

        model.getReservedBooks();
    }
}
