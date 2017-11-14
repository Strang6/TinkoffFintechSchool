package com.strang6.tinkofffintechschool;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

/**
 * Created by Strang6 on 15.11.2017.
 */

public class AlertDialogFragment extends DialogFragment {
    public static final int TYPE_ADD = 0, TYPE_DELETE = 1;
    private static final String TYPE = "type", PARENT_ID = "parentId", CHILD_ID = "childId";

    public static AlertDialogFragment newInstance(int type, long parentId, long childId) {
        AlertDialogFragment fragment = new AlertDialogFragment();
        Bundle args = new Bundle();
        args.putInt(TYPE, type);
        args.putLong(PARENT_ID, parentId);
        args.putLong(CHILD_ID, childId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int type = getArguments().getInt(TYPE);
        final long parentId = getArguments().getLong(PARENT_ID);
        final long childId = getArguments().getLong(CHILD_ID);
        String title = type == TYPE_ADD ? "Добавить связь" : "Удалить связь";
        title = title + parentId + " - " + childId;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);
        builder.setNegativeButton("Нет", null);
        DialogInterface.OnClickListener onClickListener;
        if (type == TYPE_ADD) {
            onClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    NodeDatabase db = new NodeDatabase(getActivity());
                    db.addRelation(parentId, childId);
                }
            };
        } else {
            onClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    NodeDatabase db = new NodeDatabase(getActivity());
                    db.deleteRelation(parentId, childId);
                }
            };
        }
        builder.setPositiveButton("Да",onClickListener);
        return builder.create();
    }
}
