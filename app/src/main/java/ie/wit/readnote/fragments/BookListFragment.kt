package ie.wit.readnote.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import ie.wit.donationx.adapters.BookAdapter
import ie.wit.donationx.adapters.BookListener
import ie.wit.readnote.R
import ie.wit.readnote.databinding.FragmentBookListBinding
import ie.wit.readnote.main.readNoteApp

class BookListFragment : Fragment(), BookListener {

    lateinit var app : readNoteApp
    private var _fragBinding: FragmentBookListBinding? = null
    private val fragBinding get() = _fragBinding!!

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
        fragBinding.recyclerView.adapter = BookAdapter(app.data.findBooks(),this)
        super.onCreate(savedInstanceState)

        return root
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            BookListFragment().apply {
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