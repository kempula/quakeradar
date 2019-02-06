package fi.kempula.quakeradar.ui.map.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import fi.kempula.quakeradar.R;

public class SortingDialog extends DialogFragment {

    private static final String IS_MAGNITUDE  = "IS_MAGNITUDE";

    private SortingListener listener;
    private RadioGroup radioGroup;
    private SortType sortType;

    public enum SortType {
        MAGNITUDE,
        DATE
    }

    public static SortingDialog newInstance(Boolean isMagnitudeSortType) {
        SortingDialog myFragment = new SortingDialog();

        Bundle args = new Bundle();
        args.putBoolean(IS_MAGNITUDE, isMagnitudeSortType);
        myFragment.setArguments(args);

        return myFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() == null) {
            return;
        }

        if(getArguments().getBoolean(IS_MAGNITUDE)) {
            sortType = SortType.MAGNITUDE;
            return;
        }

        sortType = SortType.DATE;

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_sorting, null);

        radioGroup = view.findViewById(R.id.radioGroup);
        checkRadioButton();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view)
                .setTitle("Sort by")
                .setPositiveButton("OK", null)
                .setNegativeButton("CANCEL", null);

        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(true);

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {

                Button button = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if(radioGroup.getCheckedRadioButtonId() == R.id.magnitudeRadioButton) {
                            listener.onSort(SortType.MAGNITUDE);
                            return;
                        }

                        listener.onSort(SortType.DATE);

                    }
                });
            }
        });

        return dialog;
    }

    private void checkRadioButton() {
        if(sortType == SortType.MAGNITUDE) {
            radioGroup.check(R.id.magnitudeRadioButton);
            return;
        }

        radioGroup.check(R.id.dateRadioButton);
    }

    public void setSortingListener(SortingListener listener) {
        this.listener = listener;
    }

    public interface SortingListener {
        void onSort(SortType sortType);
    }
}
