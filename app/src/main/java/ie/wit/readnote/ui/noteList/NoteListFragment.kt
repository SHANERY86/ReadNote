package ie.wit.readnote.ui.noteList

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ie.wit.readnote.R
import ie.wit.readnote.databinding.FragmentBookBinding
import ie.wit.readnote.databinding.NoteListFragmentBinding
import ie.wit.readnote.ui.book.BookFragmentArgs
import ie.wit.readnote.ui.bookList.BookListFragmentDirections

class NoteListFragment : Fragment() {

    private lateinit var viewModel: NoteListViewModel
    private var _fragBinding: NoteListFragmentBinding? = null
    private val fragBinding get() = _fragBinding!!
    private val args by navArgs<BookFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragBinding = NoteListFragmentBinding.inflate(inflater, container, false)
        val root = fragBinding.root
        fragBinding.editBook.setOnClickListener() {
            val action = NoteListFragmentDirections.actionNoteListFragmentToBookFragment(args.bookid)
            findNavController().navigate(action)
        }
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(NoteListViewModel::class.java)
        // TODO: Use the ViewModel
    }

}