package ie.wit.readnote.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.material.snackbar.Snackbar
import ie.wit.readnote.R
import ie.wit.readnote.databinding.FragmentBookBinding
import ie.wit.readnote.main.readNoteApp
import ie.wit.readnote.models.BookModel
import ie.wit.readnote.models.UserModel
import timber.log.Timber

class BookFragment : Fragment() {
    private var _fragBinding: FragmentBookBinding? = null
    private val fragBinding get() = _fragBinding!!
//    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>
    lateinit var app : readNoteApp
    var book = BookModel()
    var user = UserModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as readNoteApp
        user = app.loggedInUser
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragBinding = FragmentBookBinding.inflate(inflater, container, false)
        val root = fragBinding.root
        activity?.title = getString(R.string.action_addbook)
        setButtonListener(fragBinding)
        return root
    }

    fun setButtonListener(layout: FragmentBookBinding) {
        layout.addBook.setOnClickListener() {
            Timber.i("Add Book button pressed")
            book.title = layout.bookTitle.text.toString()
//            book.userId = user.id
            if(book.title.isNotEmpty()) {
                app.data.createBook(book.copy())
                }
            else {
                Snackbar
                    .make(it,R.string.snackbar_EmptyBookTitle, Snackbar.LENGTH_LONG)
                    .show()
            }
        }

        layout.deleteBook.setOnClickListener() {
            app.data.deleteBook(book)
        }

    }

    companion object {
        @JvmStatic
        fun newInstance() =
            BookFragment().apply {
                arguments = Bundle().apply {}
            }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item,
            requireView().findNavController()) || super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }


}