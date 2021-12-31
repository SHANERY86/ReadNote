package ie.wit.readnote.ui.note

import android.app.Application
import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
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
    private val args by navArgs<NoteFragmentArgs>()
    private val loggedInViewModel: LoggedInViewModel by activityViewModels()
    private val bookListViewModel : BookListViewModel by activityViewModels()
    lateinit var imm: InputMethodManager


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragBinding = NoteFragmentBinding.inflate(inflater, container, false)
        val root = fragBinding.root

        noteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
       noteViewModel.observableNote.observe(viewLifecycleOwner, Observer {
           note -> note?.let { render() }
      })

        fragBinding.pagePicker.maxValue = 1000
        fragBinding.pagePicker.minValue = 1
        fragBinding.pagePicker.setOnValueChangedListener { _, _, newVal ->
            fragBinding.addPageNumber.setText("$newVal") }

        fragBinding.addNote.setOnClickListener() {
            var note = NoteModel()
            if (fragBinding.addContent.text.isNotEmpty()) {
                val content = fragBinding.addContent.text.toString()
                val pageNumber = fragBinding.addPageNumber.text.toString()
                if(args.noteid != "-") {
                    noteViewModel.updateNote(loggedInViewModel.liveFirebaseUser.value!!.uid!!,args.bookid,args.noteid,noteViewModel.observableNote.value!!)
                    note = noteViewModel.observableNote.value!!
                }
                else {
                    note = NoteModel(content = content, pageNumber = pageNumber, nb = fragBinding.nbToggle.isChecked)
                noteViewModel.makeNote(
                    loggedInViewModel.liveFirebaseUser.value?.uid!!, args.bookid,
                    note
                )}
                bookListViewModel.load()
                imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(it.getWindowToken(), 0)
                val action = NoteFragmentDirections.actionNoteFragmentToNoteListFragment(args.bookid)
                findNavController().navigate(action)
            }
        }

        return root
    }

    private fun render() {
        fragBinding.notevm = noteViewModel
    }

    override fun onResume() {
        super.onResume()
        noteViewModel.getNote(loggedInViewModel.liveFirebaseUser.value!!.uid,args.bookid,args.noteid)
        if (args.noteid != "-") {
            fragBinding.addNote.setText(R.string.button_saveNote)
        }
    }

}