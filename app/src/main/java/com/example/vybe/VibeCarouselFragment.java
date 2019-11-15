package com.example.vybe;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

import in.goodiebag.carouselpicker.CarouselPicker;

/**
 * VibeCarouselFragment extends a DialogFragment and serves as a custom
 * picker for a user to decide which Vibe they want to have added to a Vibe Event
 * Functionality is similar to that of a {@link android.app.DatePickerDialog}
 */
public class VibeCarouselFragment extends DialogFragment {

    private OnFragmentInteractionListener listener;
    private int selectedEmoticon = R.drawable.ic_no_vibe;

    // interface to listen for any user interaction on fragment
    public interface OnFragmentInteractionListener {
        void onOkPressed(int selectedVibeEmoticon);
    }

    // attach fragment to activity
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            listener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    // create the custom carousel picker within the dialog fragment
    // populates a list of carousel picker items and then displays them for user's choosing
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // inflate the layout for this fragment
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_vibe_carousel, null);

        CarouselPicker carouselPicker = view.findViewById(R.id.carousel_picker);
        List<CarouselPicker.PickerItem> imageItems = new ArrayList<>();
        imageItems.add(new CarouselPicker.DrawableItem(R.drawable.ic_no_vibe));
        imageItems.add(new CarouselPicker.DrawableItem(R.drawable.ic_angry));
        imageItems.add(new CarouselPicker.DrawableItem(R.drawable.ic_disgusted));
        imageItems.add(new CarouselPicker.DrawableItem(R.drawable.ic_happy));
        imageItems.add(new CarouselPicker.DrawableItem(R.drawable.ic_sad));
        imageItems.add(new CarouselPicker.DrawableItem(R.drawable.ic_scared));
        imageItems.add(new CarouselPicker.DrawableItem(R.drawable.ic_surprised));

        // Create an adapter
        CarouselPicker.CarouselViewAdapter imageAdapter = new CarouselPicker.CarouselViewAdapter(getActivity(), imageItems, 0);
        // Set the adapter
        carouselPicker.setAdapter(imageAdapter);

        carouselPicker.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            // on selection of an emoticon, set the vibe for a vibe event
            @Override
            public void onPageSelected(int position) {
                selectedEmoticon = imageItems.get(position).getDrawable();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("Select a Vibe")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // if no vibe selected just exit
                        if (selectedEmoticon != R.drawable.ic_no_vibe) {
                            listener.onOkPressed(selectedEmoticon);
                        }
                        else {
                            return;
                        }
                    }
                }).create();
    }

}
