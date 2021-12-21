package ie.wit.readnote.fragments

import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.NavigationUI
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import ie.wit.readnote.R
import ie.wit.readnote.databinding.FragmentBookBinding
import ie.wit.readnote.main.readNoteApp
import ie.wit.readnote.models.BookModel
import ie.wit.readnote.models.UserModel
import org.wit.placemark.helpers.showImagePicker
import timber.log.Timber

class BookFragment : Fragment() {
    private var _fragBinding: FragmentBookBinding? = null
    private val fragBinding get() = _fragBinding!!
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var bookViewModel: BookViewModel
    private val args by navArgs<BookFragmentArgs>()
    lateinit var app : readNoteApp
    var book = BookModel()
    var user = UserModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as readNoteApp
//        user = app.loggedInUser
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragBinding = FragmentBookBinding.inflate(inflater, container, false)
        val root = fragBinding.root

        bookViewModel = ViewModelProvider(this).get(BookViewModel::class.java)
        bookViewModel.observableStatus.observe(viewLifecycleOwner, Observer {
            status -> status?.let { render(status) }
        })
        bookViewModel.observableBook.observe(viewLifecycleOwner, Observer {
            book -> book?.let { renderBook() }
        })
        activity?.title = getString(R.string.action_addbook)
        setButtonListener(fragBinding)
        return root
    }

    private fun render(status: Boolean) {
        when (status) {
            true -> {
                view?.let {
                    findNavController().popBackStack()
                }
            }
            false -> Toast.makeText(context,getString(R.string.addBookError),Toast.LENGTH_LONG).show()
        }
    }

    private fun renderBook() {
        fragBinding.book = bookViewModel
        fragBinding.addBook.setText(R.string.button_editBook)
        fragBinding.chooseImage.setText(R.string.button_changeCover)
    }

    fun setButtonListener(layout: FragmentBookBinding) {
        layout.addBook.setOnClickListener() {
            Timber.i("Add Book button pressed")
            book.title = layout.bookTitle.text.toString()
//            book.userId = user.id
            if(book.title.isNotEmpty()) {
                if(args.bookid != -1L){
                    book.id = args.bookid
                    bookViewModel.updateBook(book)
                }
                else {
                    bookViewModel.addBook(book)
                }
                }
            else {
                Snackbar
                    .make(it,R.string.snackbar_EmptyBookTitle, Snackbar.LENGTH_LONG)
                    .show()
            }
        }

        layout.chooseImage.setOnClickListener {
            showImagePicker(imageIntentLauncher)
        }

        layout.deleteBook.setOnClickListener() {
            app.data.deleteBook(book)
        }

        registerImagePickerCallback()

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

    override fun onResume() {
        super.onResume()
        bookViewModel.getBook(args.bookid)
        Timber.i("BOOK ID ${args.bookid}")
    }

    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when(result.resultCode){
                    RESULT_OK -> {
                        if (result.data != null) {
                            Timber.i("Got Result ${result.data!!.data}")
                            book.image = result.data!!.data!!
                            Picasso.get()
                                .load(book.image)
                                .into(fragBinding.bookCover)
                        } // end of if
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
    }


}