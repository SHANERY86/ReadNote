package ie.wit.readnote.ui.book

import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.NavigationUI
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import ie.wit.donationx.ui.auth.LoggedInViewModel
import ie.wit.readnote.R
import ie.wit.readnote.databinding.FragmentBookBinding
import ie.wit.readnote.main.readNoteApp
import ie.wit.readnote.models.BookModel
import ie.wit.readnote.models.NoteModel
import ie.wit.readnote.ui.bookList.BookListViewModel
import org.wit.placemark.helpers.showImagePicker
import timber.log.Timber

class BookFragment : Fragment() {
    private var _fragBinding: FragmentBookBinding? = null
    private val fragBinding get() = _fragBinding!!
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var bookViewModel: BookViewModel
    private val loggedInViewModel: LoggedInViewModel by activityViewModels()
    private val bookListViewModel : BookListViewModel by activityViewModels()
    private val args by navArgs<BookFragmentArgs>()
    lateinit var app : readNoteApp
    var notes = ArrayList<NoteModel>()
    var book = BookModel()
    var imageChange = false


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
        fragBinding.bookvm = bookViewModel
        fragBinding.addBook.setText(R.string.button_saveBook)
        fragBinding.chooseImage.setText(R.string.button_changeCover)
        val uriString = bookViewModel.observableBook.value!!.image
        fragBinding.bookCover.setImageURI(uriString.toUri())
        fragBinding.deleteBook.visibility = View.VISIBLE
    }

    fun setButtonListener(layout: FragmentBookBinding) {
        layout.addBook.setOnClickListener() {
            book.title = layout.bookTitle.text.toString()
            if(layout.bookTitle.text.toString().isNotEmpty()) {
                if(args.bookid != "-"){
                    bookViewModel.updateBook(loggedInViewModel.liveFirebaseUser.value?.uid!!,
                        args.bookid, fragBinding.bookvm?.observableBook!!.value!!)
                    bookListViewModel.load()
                    val action = BookFragmentDirections.actionBookFragmentToBookListFragment()
                    findNavController().navigate(action)
                }
                else {
                    bookViewModel.addBook(loggedInViewModel.liveFirebaseUser,BookModel(title = book.title, image = book.image, notes = notes, email = loggedInViewModel.liveFirebaseUser.value?.email!!))
                   var imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(it.getWindowToken(), 0)
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
            bookViewModel.deleteBook(loggedInViewModel.liveFirebaseUser.value?.uid!!,fragBinding.bookvm?.observableBook!!.value!!.uid!!)
            bookListViewModel.load()
            val action = BookFragmentDirections.actionBookFragmentToBookListFragment()
            findNavController().navigate(action)
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
        if(!imageChange)
        bookViewModel.getBook(loggedInViewModel.liveFirebaseUser.value?.uid!!,args.bookid)
    }

    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when(result.resultCode){
                    RESULT_OK -> {
                        if (result.data != null) {
                            imageChange = true
                            Timber.i("Got Result ${result.data!!.data}")
                            val uri = result.data!!.data!!
                            val uriString = uri.toString()
                            book.image = uriString
                            fragBinding.imageHolder.setText(uriString)
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