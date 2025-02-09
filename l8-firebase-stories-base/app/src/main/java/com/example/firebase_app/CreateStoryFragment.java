package com.example.firebase_app;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.Toast;

import com.example.firebase_app.databinding.FragmentCreateStoryBinding;
import com.example.firebase_app.models.Stories;
import com.example.firebase_app.models.Story;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CreateStoryFragment extends Fragment {

    public CreateStoryFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentCreateStoryBinding binding = FragmentCreateStoryBinding.inflate(getLayoutInflater(), container, false);

        Stories stories = new ViewModelProvider(getActivity()).get(Stories.class);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        binding.btnCreate.setOnClickListener(v -> {
            String title = binding.teTitle.getText().toString();
            String url = binding.tePhotoUrl.getText().toString();

            if (validate(title, url)) {
                stories.addStory(new Story(title, user.getDisplayName(), url));

                Navigation.findNavController(getView())
                        .navigate(R.id.action_createStoryFragment_to_storiesFragment);
            }
        });

        return binding.getRoot();
    }

    private boolean validate(String title, String url) {
        if (title.isBlank()) {
            Toast.makeText(getContext(), "Title can not be blank", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (url.isBlank()) {
            Toast.makeText(getContext(), "Image URL can not be blank", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!URLUtil.isValidUrl(url)) {
            Toast.makeText(getContext(), "Image URL is not valid", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}