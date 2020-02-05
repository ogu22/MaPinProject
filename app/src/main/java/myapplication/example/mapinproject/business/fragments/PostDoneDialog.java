package myapplication.example.mapinproject.business.fragments;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import myapplication.example.mapinproject.business.activities.HomeActivity;
import myapplication.example.mapinproject.business.activities.Pin_infoActivity;

public class PostDoneDialog extends DialogFragment {

    private DialogFragmentListener dialogFragmentListener;

    public interface DialogFragmentListener {
        void onDialogPositiveButtonClicked();
    }

    public PostDoneDialog newInstance(DialogFragmentListener dialogFragmentListener) {
        this.dialogFragmentListener = dialogFragmentListener;
        return new PostDoneDialog();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity())
                .setTitle("投稿完了")
                .setMessage("投稿が完了しました")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getActivity(), HomeActivity.class );
                        startActivity(intent);
                    }
                });

        return dialogBuilder.create();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Fragment targetFragment = this.getTargetFragment();
        try{
            if(targetFragment != null){
                dialogFragmentListener = (DialogFragmentListener)targetFragment;
            }
        }catch (ClassCastException e){
            throw new ClassCastException("DialogFragmentListenerをimplementしていません");
        }
    }
    public PostDoneDialog newInstance(Fragment fragment){
        PostDoneDialog instance = new PostDoneDialog();
        instance.setTargetFragment(fragment, 0);
        return instance;
    }
}