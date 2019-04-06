package com.example.lucasgaleano.micarrera.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.EditText;

import com.example.lucasgaleano.micarrera.activities.AssignmentInfoActivity;

public class EditAssigmentDialogFragment extends DialogFragment {
    private String title;
    private String content;
    Bundle argument;
    private EditText edittext;
    private AssignmentInfoActivity self;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        argument = getArguments();
        edittext = new EditText(getContext());
        title = argument.getString("EXTRA_TITLE").toString();
        builder.setTitle(title);
        content = argument.getString("EXTRA_CONTENT");
        edittext.setText(content);
        builder.setView(edittext);

        self = ((AssignmentInfoActivity) getActivity());

        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(title.equals("title")) {
                    self.assignment.setTitle(edittext.getText().toString());
                }
                if(title.equals("description")) {
                    self.assignment.setDescription(edittext.getText().toString());
                }

                   self.repo.update(self.assignment);

                   self.recreate();
                dismiss();
            }
        });
        return builder.create();
    }


}