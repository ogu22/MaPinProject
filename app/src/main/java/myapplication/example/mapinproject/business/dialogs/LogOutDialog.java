package myapplication.example.mapinproject.business.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import myapplication.example.mapinproject.business.login.LoginMenuActivity;

public class LogOutDialog extends DialogFragment {

    // ダイアログが生成された時に呼ばれるメソッド ※必須
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // ダイアログ生成  AlertDialogのBuilderクラスを指定してインスタンス化します
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        // タイトル設定
        dialogBuilder.setTitle("ログアウト");
        // 表示する文章設定
        dialogBuilder.setMessage("ログアウトしますか？");

        // OKボタン作成
        dialogBuilder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // post_changeに遷移
                Activity parent = getActivity();
                Intent intent = new Intent(parent, LoginMenuActivity.class);
                startActivity(intent);
            }
        });

        // NGボタン作成
        dialogBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 何もしないで閉じる
            }
        });

        // dialogBulderを返す
        return dialogBuilder.create();
    }

}
