package ie.wit.readnote.ui.detail

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.squareup.picasso.Picasso
import ie.wit.donationx.ui.auth.LoggedInViewModel
import ie.wit.readnote.R
import ie.wit.readnote.databinding.BookDetailFragmentBinding
import ie.wit.readnote.models.BookModel
import ie.wit.readnote.ui.bookList.BookListViewModel
import org.wit.placemark.helpers.showImagePicker
import timber.log.Timber

class BookDetailFragment : Fragment() {

    private lateinit var detailViewModel: BookDetailViewModel
    private val args by navArgs<BookDetailFragmentArgs>()
    private var _fragBinding: BookDetailFragmentBinding? = null
    private val fragBinding get() = _fragBinding!!
    private val loggedInViewModel : LoggedInViewModel by activityViewModels()
    private val bookListViewModel : BookListViewModel by activityViewModels()
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>
    var imageChange = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragBinding = BookDetailFragmentBinding.inflate(inflater, container, false)
        val root = fragBinding.root
        detailViewModel = ViewModelProvider(this).get(BookDetailViewModel::class.java)
        detailViewModel.observableBook.observe(viewLifecycleOwner, Observer { render() })

        fragBinding.editBook.setOnClickListener {
            detailViewModel.updateBook(loggedInViewModel.liveFirebaseUser.value?.uid!!,
                args.bookid, fragBinding.bookvm?.observableBook!!.value!!)
            bookListViewModel.load()
            findNavController().navigateUp()
        }

        fragBinding.changeImage.setOnClickListener {
            showImagePicker(imageIntentLauncher)
        }

        fragBinding.deleteBook.setOnClickListener() {
//            app.data.deleteBook(book)
        }

        registerImagePickerCallback()

        return root
    }

    private fun render() {
        fragBinding.bookvm = detailViewModel
        Timber.i("IM E+BEING A PRICK")
        Timber.i("Retrofit fragBinding.donationvm == $fragBinding.donationvm")
    }

    override fun onResume() {
        super.onResume()
        Timber.i("IM E+BEING A PRICK")
        if(!imageChange)
        detailViewModel.getBook(loggedInViewModel.liveFirebaseUser.value?.uid!!,
            args.bookid)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }


    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when(result.resultCode){
                    Activity.RESULT_OK -> {
                        if (result.data != null) {
                            imageChange = true
                            Timber.i("Got Result ${result.data!!.data}")
                            val uri = result.data!!.data!!
                            val uriString = uri.toString()
                            fragBinding.imageHolder.setText(uriString)
                            Timber.i("TEST URI STRING $uriString")
                            Timber.i("BIG TEST 2 ${fragBinding.imageHolder.text}")
//                            fragBinding.imageHolder.setText(uriString)
/*                            Picasso.get()
                                .load(book.image)
                                .into(fragBinding.bookCover) */
                        } // end of if
                    }
                    Activity.RESULT_CANCELED -> { } else -> { }
                }
            }
    }

}