package com.example.pyrca.micarrera.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.EditText;

import com.example.pyrca.micarrera.activities.ExamInfoActivity;

public class EditExamDialogFragment extends DialogFragment {
    private String title;
    private String content;
    Bundle argument;
    private EditText edittext;
    private ExamInfoActivity self;

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

        self = ((ExamInfoActivity) getActivity());

        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(title.equals("title")) {
                    //self.exam.setTitle(exam); TODO choice title from dialog
                }
                if(title.equals("description")) {
                    self.exam.setDescription(edittext.getText().toString());
                }

                self.repo.update(self.exam);

                self.recreate();
                dismiss();
            }
        });
        return builder.create();
    }


}