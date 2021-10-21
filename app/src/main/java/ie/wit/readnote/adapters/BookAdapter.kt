package ie.wit.donationx.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ie.wit.readnote.databinding.CardBookBinding
import ie.wit.readnote.models.BookModel
import timber.log.Timber

interface BookListener {
    fun onBookClick(book: BookModel){
        Timber.i("Book Clicked")
    }
}

class BookAdapter constructor(private var books: List<BookModel>, private val listener: BookListener )
    : RecyclerView.Adapter<BookAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardBookBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val book = books[holder.adapterPosition]
        holder.bind(book, listener)
    }

    override fun getItemCount(): Int = books.size

    inner class MainHolder(val binding : CardBookBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(book: BookModel, listener: BookListener) {
            binding.bookTitle.text = book.title
            binding.root.setOnClickListener { listener.onBookClick(book) }
        }
    }
}
