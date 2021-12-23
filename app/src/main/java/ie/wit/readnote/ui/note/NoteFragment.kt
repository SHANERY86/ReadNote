package ie.wit.readnote.ui.note

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ie.wit.donationx.ui.auth.LoggedInViewModel
import ie.wit.readnote.R
import ie.wit.readnote.databinding.FragmentBookBinding
import ie.wit.readnote.databinding.NoteFragmentBinding
import ie.wit.readnote.models.NoteModel
import ie.wit.readnote.ui.book.BookFragmentArgs
import ie.wit.readnote.ui.bookList.BookListViewModel
import ie.wit.readnote.ui.noteList.NoteListFragmentDirections
import timber.log.Timber

class NoteFragment : Fragment() {

    private lateinit var noteViewModel: NoteViewModel
    private var _fragBinding: NoteFragmentBinding? = null
    private val fragBinding get() = _fragBinding!!
    private val args by navArgs<BookFragmentArgs>()
    private val loggedInViewModel: LoggedInViewModel by activityViewModels()
    private val bookListViewModel : BookListViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragBinding = NoteFragmentBinding.inflate(inflater, container, false)
        val root = fragBinding.root

        noteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
       noteViewModel.observableNote.observe(viewLifecycleOwner, Observer {
           note -> note.let { render() }
      })

        fragBinding.addNote.setOnClickListener() {
            if (fragBinding.addContent.text.isNotEmpty()) {
                val content = fragBinding.addContent.text.toString()
                val pageNumber = fragBinding.addPageNumber.text.toString()
                noteViewModel.makeNote(
                    loggedInViewModel.liveFirebaseUser.value?.uid!!, args.bookid,
                    NoteModel(content = content, pageNumber = pageNumber)
                )
                bookListViewModel.load()
                val action = NoteFragmentDirections.actionNoteFragmentToNoteListFragment(args.bookid)
                findNavController().navigate(action)
            }
        }
        return root
    }

    private fun render() {
        fragBinding.notevm = noteViewModel
    }




}