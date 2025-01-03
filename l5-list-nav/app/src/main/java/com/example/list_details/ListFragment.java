package com.example.list_details;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.list_details.databinding.FragmentListBinding;
import com.example.list_details.models.Book;

import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment {

    private FragmentListBinding binding;

    private List<Book> books = new ArrayList<>(List.of(
            new Book("To Kill a Mockingbird",
                    "Harper Lee",
                    "The unforgettable novel of a childhood in a sleepy Southern town and the crisis of conscience that rocked it. \"To Kill A Mockingbird\" became both an instant bestseller and a critical success when it was first published in 1960. It went on to win the Pulitzer Prize in 1961 and was later made into an Academy Award-winning film, also a classic."),
            new Book("The Great Gatsby",
                    "F. Scott Fitzgerald",
                    "The Great Gatsby, F. Scott Fitzgerald's third book, stands as the supreme achievement of his career. This exemplary novel of the Jazz Age has been acclaimed by generations of readers. The story of the fabulously wealthy Jay Gatsby and his love for the beautiful Daisy Buchanan, of lavish parties on Long Island at a time when The New York Times noted \"gin was the national drink and sex the national obsession,\" it is an exquisitely crafted tale of America in the 1920s."),
            new Book("Harry Potter and the Sorcerer’s Stone",
                    "J.K. Rowling",
                    "Turning the envelope over, his hand trembling, Harry saw a purple wax seal bearing a coat of arms; a lion, an eagle, a badger and a snake surrounding a large letter 'H'.")
    ));

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.rvBooks.setAdapter(new BookViewAdapter(books, idx -> {
            Log.i("ListFragment", "View clicked: " + idx);

            Book b = books.get(idx);
            Bundle args = new Bundle();
            args.putString("author", b.getAuthor());
            args.putString("title", b.getName());
            args.putString("details", b.getDescription());

            Navigation.findNavController(view).navigate(R.id.action_openDetails, args);

            return null;
        }));
    }
}