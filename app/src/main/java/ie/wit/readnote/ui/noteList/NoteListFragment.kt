package ie.wit.readnote.ui.noteList

import SwipeToDeleteCallback
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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ie.wit.donationx.adapters.BookAdapter
import ie.wit.donationx.adapters.NoteAdapter
import ie.wit.donationx.adapters.NoteListener
import ie.wit.donationx.ui.auth.LoggedInViewModel
import ie.wit.readnote.R
import ie.wit.readnote.databinding.FragmentBookBinding
import ie.wit.readnote.databinding.NoteListFragmentBinding
import ie.wit.readnote.models.BookModel
import ie.wit.readnote.models.FirebaseDBManager
import ie.wit.readnote.models.NoteModel
import ie.wit.readnote.ui.book.BookFragmentArgs
import ie.wit.readnote.ui.book.BookViewModel
import ie.wit.readnote.ui.bookList.BookListFragmentDirections

class NoteListFragment : Fragment(), NoteListener {

    private lateinit var noteListViewModel: NoteListViewModel
    private var _fragBinding: NoteListFragmentBinding? = null
    private val fragBinding get() = _fragBinding!!
    private val args by navArgs<BookFragmentArgs>()
    private val loggedInViewModel : LoggedInViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragBinding = NoteListFragmentBinding.inflate(inflater, container, false)
        val root = fragBinding.root

        noteListViewModel = ViewModelProvider(this).get(NoteListViewModel::class.java)
        fragBinding.recyclerView.setLayoutManager(LinearLayoutManager(activity))
        noteListViewModel.observableNotesList.observe(viewLifecycleOwner, Observer {
                notes -> notes?.let { render(notes) }
        })

        fragBinding.editBook.setOnClickListener() {
            val action = NoteListFragmentDirections.actionNoteListFragmentToBookFragment(args.bookid)
            findNavController().navigate(action)
        }

        fragBinding.newNote.setOnClickListener() {
            val action = NoteListFragmentDirections.actionNoteListFragmentToNoteFragment(args.bookid)
            findNavController().navigate(action)
        }

        val swipeDeleteHandler = object : SwipeToDeleteCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = fragBinding.recyclerView.adapter as NoteAdapter
                adapter.removeAt(viewHolder.adapterPosition)
                FirebaseDBManager.deleteNote(loggedInViewModel.liveFirebaseUser?.value!!.uid!!, args.bookid, viewHolder.itemView.tag as String)
            }
        }
        val itemTouchDeleteHelper = ItemTouchHelper(swipeDeleteHandler)
        itemTouchDeleteHelper.attachToRecyclerView(fragBinding.recyclerView)
        return root
    }

    private fun render(notes: ArrayList<NoteModel>) {
        fragBinding.recyclerView.adapter = NoteAdapter(notes, this)
    }

    override fun onResume() {
        super.onResume()
            noteListViewModel.getNotes(loggedInViewModel.liveFirebaseUser?.value!!.uid!!,args.bookid)
    }


}