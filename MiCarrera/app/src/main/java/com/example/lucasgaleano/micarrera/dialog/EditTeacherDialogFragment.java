package com.example.lucasgaleano.micarrera.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.EditText;

import com.example.lucasgaleano.micarrera.activities.TeacherInfoActivity;

public class EditTeacherDialogFragment extends DialogFragment {
    private String title;
    private String content;
    Bundle argument;
    private EditText edittext;
    private TeacherInfoActivity self;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        argument = getArguments();
        edittext = new EditText(getContext());
        title = argument.getString("EXTRA_TITLE");
        builder.setTitle(title);
        content = argument.getString("EXTRA_CONTENT");
        edittext.setText(content);
        builder.setView(edittext);

        self = ((TeacherInfoActivity) getActivity());

        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(title.equals("name")) {
                    self.teacher.setName(edittext.getText().toString());
                }
                if(title.equals("email")) {
                    self.teacher.setEmail(edittext.getText().toString());
                }
                if(title.equals("web")) {
                    self.teacher.setWebSite(edittext.getText().toString());
                }

                self.repo.update(self.teacher);

                self.recreate();
                dismiss();
            }
        });
        return builder.create();
    }


}
