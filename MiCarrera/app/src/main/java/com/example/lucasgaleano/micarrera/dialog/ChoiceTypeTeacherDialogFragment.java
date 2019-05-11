package com.example.lucasgaleano.micarrera.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import com.example.lucasgaleano.micarrera.activities.TeacherInfoActivity;
import com.example.lucasgaleano.micarrera.database.Teacher;

public class ChoiceTypeTeacherDialogFragment extends DialogFragment {

    private TeacherInfoActivity self;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        Bundle argument = getArguments();
        final int id = argument.getInt("TEACHER_ID",-1);


        builder.setTitle("Cargo")
                .setItems(Teacher.getTypes(), new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        self = ((TeacherInfoActivity) getActivity());

                        Teacher teacher = self.repo.getTeacherById(id);
                        teacher.setType(which);
                        self.repo.update(teacher);
                        self.recreate();
                        dismiss();
                    }
                });
        return builder.create();
    }
}
