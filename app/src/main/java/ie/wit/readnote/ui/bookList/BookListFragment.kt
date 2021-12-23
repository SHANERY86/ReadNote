package ie.wit.readnote.ui.bookList

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.GridLayoutManager
import ie.wit.donationx.adapters.BookAdapter
import ie.wit.donationx.adapters.BookListener
import ie.wit.donationx.ui.auth.LoggedInViewModel
import ie.wit.readnote.R
import ie.wit.readnote.databinding.FragmentBookListBinding
import ie.wit.readnote.main.readNoteApp
import ie.wit.readnote.models.BookModel
import timber.log.Timber

class BookListFragment : Fragment(), BookListener {

    lateinit var app : readNoteApp
    private var _fragBinding: FragmentBookListBinding? = null
    private val fragBinding get() = _fragBinding!!
    private lateinit var bookListViewModel: BookListViewModel
    private val loggedInViewModel : LoggedInViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as readNoteApp
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragBinding = FragmentBookListBinding.inflate(inflater, container, false)
        val root = fragBinding.root
        activity?.title = getString(R.string.action_booklist)

        fragBinding.recyclerView.setLayoutManager(GridLayoutManager(activity,3))
        bookListViewModel = ViewModelProvider(this).get(BookListViewModel::class.java)
        bookListViewModel.observableBookList.observe(viewLifecycleOwner, Observer {
            books ->
            books?.let { render(books)}
        })
//        fragBinding.recyclerView.adapter = BookAdapter(app.data.findBooks(),this)
        super.onCreate(savedInstanceState)

        return root
    }

    private fun render(bookList: List<BookModel>) {
        fragBinding.recyclerView.adapter = BookAdapter(bookList, this)
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

    override fun onBookClick(book: BookModel) {
        Timber.i("TEST USER: ${loggedInViewModel.liveFirebaseUser.value?.uid!!}")
        Timber.i("TEST BOOK ID: ${book.uid!!}")
        val action = BookListFragmentDirections.actionBookListFragmentToBookFragment2(book.uid!!)
        findNavController().navigate(action)
    }

    override fun onResume() {
        super.onResume()
        loggedInViewModel.liveFirebaseUser.observe(viewLifecycleOwner, Observer { firebaseUser ->
            if (firebaseUser != null) {
                bookListViewModel.liveFirebaseUser.value = firebaseUser
                bookListViewModel.load()
            }
        })
        //hideLoader(loader)
    }
}