package com.example.lucasgaleano.buttonexample.dialog;

import android.app.AlertDialog;
import android.app.Application;
import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.example.lucasgaleano.buttonexample.R;
import com.example.lucasgaleano.buttonexample.database.Repository;
import com.example.lucasgaleano.buttonexample.view.SubjectTreeModel;

public class SubjectDialogFragment extends DialogFragment {

    public SubjectTreeModel treeModel;
    private String title;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        Bundle argument = getArguments();
        treeModel = ViewModelProviders.of(this).get(SubjectTreeModel.class);
        title = argument.getString("SubjectName");
        builder.setTitle(title)
                .setItems(R.array.Subject_state_array, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        treeModel.updateSubject(title,which+1);
                    }
                });
        return builder.create();
    }
}
