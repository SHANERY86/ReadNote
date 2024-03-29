package ie.wit.donationx.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ie.wit.readnote.databinding.CardBookBinding
import ie.wit.readnote.models.BookModel

interface BookListener {
    fun onBookClick(book: BookModel){
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
            binding.book = book
            Picasso.get().load(book.image.toUri()).fit().into(binding.bookCover)
            binding.root.setOnClickListener { listener.onBookClick(book)
            binding.executePendingBindings()
            }
        }
    }
}
