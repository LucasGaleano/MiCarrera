package com.example.lucasgaleano.micarrera.dialog;

import android.app.AlertDialog;
import android.app.Application;
import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.example.lucasgaleano.micarrera.R;
import com.example.lucasgaleano.micarrera.database.Repository;
import com.example.lucasgaleano.micarrera.view.SubjectTreeModel;

public class SubjectDialogFragment extends DialogFragment {

    public SubjectTreeModel treeModel;
    private String subjectName;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        Bundle argument = getArguments();
        treeModel = ViewModelProviders.of(this).get(SubjectTreeModel.class);
        subjectName = argument.getString("SubjectName");
        builder.setTitle(subjectName)
                .setItems(R.array.Subject_state_array, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {

                        treeModel.updateSubject(subjectName,which+1);
                    }
                });
        return builder.create();
    }
}
