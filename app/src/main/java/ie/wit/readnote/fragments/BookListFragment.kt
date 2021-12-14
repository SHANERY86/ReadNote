package ie.wit.readnote.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ie.wit.readnote.R

class BookListFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_book_list, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            BookListFragment().apply {
                arguments = Bundle().apply {}
            }
    }
}