package klp.com.animationdemo;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by klp13115 on 2016/3/11.
 */
public class SharedElementFragment1 extends Fragment {

    public static SharedElementFragment1 newInstance (){
        SharedElementFragment1 fragment = new SharedElementFragment1();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_sharedelement_fragment1, container, false);
        final ImageView squareBlue = (ImageView) view.findViewById(R.id.square_blue);
        view.findViewById(R.id.sample2_button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                addNextFragment(squareBlue, false);
            }
        });

        view.findViewById(R.id.sample2_button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                addNextFragment(squareBlue, true);
            }
        });
        return view;
    }

//    private void addNextFragment(ImageView squareBlue, boolean overlap) {
//        SharedElementFragment2 sharedElementFragment2 = SharedElementFragment2.newInstance(sample);
//
//        Slide slideTransition = new Slide(Gravity.RIGHT);
//        slideTransition.setDuration(getResources().getInteger(R.integer.anim_duration_medium));
//
//        ChangeBounds changeBoundsTransition = new ChangeBounds();
//        changeBoundsTransition.setDuration(getResources().getInteger(R.integer.anim_duration_medium));
//
//        sharedElementFragment2.setEnterTransition(slideTransition);
//        sharedElementFragment2.setAllowEnterTransitionOverlap(overlap);
//        sharedElementFragment2.setAllowReturnTransitionOverlap(overlap);
////        sharedElementFragment2.setSharedElementEnterTransition(changeBoundsTransition);
//
//        getFragmentManager().beginTransaction()
//                .replace(R.id.sample2_content, sharedElementFragment2)
//                .addToBackStack(null)
//                .addSharedElement(squareBlue, getString(R.string.square_blue_name))
//                .commit();
//    }
}
