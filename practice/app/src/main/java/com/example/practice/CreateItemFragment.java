package com.example.practice;

import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.practice.databinding.FragmentCreateItemBinding;
import com.example.practice.models.Subtask;
import com.example.practice.models.Task;
import com.example.practice.models.TasksViewModel;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class CreateItemFragment extends Fragment {
    private static final String TAG = "CreateItemFragment";

    private FragmentCreateItemBinding binding;
    private TasksViewModel viewModel;

    ActivityResultLauncher<PickVisualMediaRequest> pickMedia =
        registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
            // Викликається в результаті вибору зображення
            if (uri != null) {
                Log.d(TAG, "Selected URI: " + uri);

                ImageSpan img = new ImageSpan(getActivity(), uri);

                SpannableStringBuilder builder = new SpannableStringBuilder();
                builder.append(binding.textDescription.getText())
                        .append("\n")
                        .append(" ", img, 0);

                binding.textDescription.setText(builder);
            } else {
                Log.d(TAG, "No media selected");
            }
        });

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCreateItemBinding.inflate(getLayoutInflater(), container, false);

        viewModel = new ViewModelProvider(getActivity()).get(TasksViewModel.class);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.chipAddImage.setOnClickListener(v -> {
            // Запуск діалогу вибору зображення
            pickMedia.launch(new PickVisualMediaRequest.Builder()
                    .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                    .build());
        });

        binding.btnCreate.setOnClickListener(v -> createTask());

        binding.chipRemindMe.setOnClickListener(v -> {

            // Дана функція викликається в результаті діалогу
            Consumer<LocalDateTime> callback = time -> {
                binding.chipRemindMe.setText(
                    time != null ?  time.format(DateTimeFormatter.ofPattern("HH:mm")) : "Never"
                );
            };

            // Запуск діалогу вибору часу
            new TimePickerFragment(callback).show(getActivity().getSupportFragmentManager(), "timePicker");
        });
    }

    private void createTask() {
        if (binding.textTitle.getText().isEmpty()) {
            Toast.makeText(getContext(), "Title text has to be specified", Toast.LENGTH_LONG).show();
        }

        Task task = new Task();
        task.setId(UUID.randomUUID().toString());
        task.setTitle(binding.textTitle.getText().toString());
        task.setDescription(binding.textDescription.getText().toString());
        task.setCompleted(false);

        // Comma separated to make UI easier
        String subtasksStr = binding.editAddSubItem.getText().toString();
        List<Subtask> subtasks = Arrays.stream(subtasksStr.split(","))
                .filter(String::isBlank)
                .map(name -> new Subtask(name, false))
                .collect(Collectors.toList());

        task.setSubtasks(subtasks);

        Log.d(TAG, "Creating a task: " + task);
        viewModel.addTask(task);

        Navigation.findNavController(binding.getRoot()).navigate(
            R.id.action_createItemFragment_to_homeFragment
        );
    }
}