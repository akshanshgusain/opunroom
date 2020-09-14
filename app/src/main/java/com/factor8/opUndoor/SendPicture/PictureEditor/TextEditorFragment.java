package com.factor8.opUndoor.SendPicture.PictureEditor;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import com.factor8.opUndoor.R;
import com.github.naz013.colorslider.ColorSlider;

public class TextEditorFragment extends DialogFragment {

    private EditText mEditText;
    private ColorSlider colorSlider;
    public static final String EXTRA_INPUT_TEXT = "extra_input_text";
    public static final String EXTRA_COLOR_CODE = "extra_color_code";
    private static final String TAG = "TextEditorFragment";


    private TextEditor mTextEditor;
    private int currentSelectedColor = 0;

    //Show dialog with provide text and text color
    public static TextEditorFragment show(@NonNull AppCompatActivity appCompatActivity, @NonNull String inputText, @ColorInt int colorCode) {
        Bundle args = new Bundle();
        args.putString(EXTRA_INPUT_TEXT, inputText);
        args.putInt(EXTRA_COLOR_CODE, colorCode);
        TextEditorFragment fragment = new TextEditorFragment();
        fragment.setArguments(args);
        fragment.show(appCompatActivity.getSupportFragmentManager(), TAG);
        return fragment;
    }

    //Show dialog with default text input as empty and text color white
    public static TextEditorFragment show(@NonNull AppCompatActivity appCompatActivity) {
        return show(appCompatActivity, null, ContextCompat.getColor(appCompatActivity, R.color.colorWhite));
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_text_editor,container,false);
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        //Make dialog full screen with transparent background
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mEditText = view.findViewById(R.id.editText_input_text);
        colorSlider = view.findViewById(R.id.color_slider);
        setUpHueSeekBar();

        final Dialog dialog = getDialog();

        String inputText = getArguments().getString(EXTRA_INPUT_TEXT,"");
        mEditText.setText(inputText);
        mEditText.setSelection(inputText.length());
        mEditText.setTextColor(getArguments().getInt(EXTRA_COLOR_CODE));
        currentSelectedColor = getArguments().getInt(EXTRA_COLOR_CODE);

        //dismiss the Dialog when clicked in the empty area
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });
    }

    private void setUpHueSeekBar() {
            colorSlider.setListener(new ColorSlider.OnColorSelectedListener() {
                @Override
                public void onColorChanged(int position, int color) {
                    mEditText.setTextColor(color);
                    currentSelectedColor  =  color;
                }
            });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

    }

    @Override
    public void onPause() {
        super.onPause();
        String inputText = mEditText.getText().toString();
        if (!TextUtils.isEmpty(inputText) && mTextEditor != null) {
            mTextEditor.onDone(inputText,currentSelectedColor);
        }
    }

    //Callback to listener if user is done with text editing
    public void setOnTextEditorListener(TextEditor textEditor) {
        mTextEditor = textEditor;
    }

    public interface TextEditor {
        void onDone(String inputText, int colorCode);
    }


}