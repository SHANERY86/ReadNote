package ie.wit.donationx.adapters



import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ie.wit.readnote.R
import ie.wit.readnote.databinding.CardNoteBinding
import ie.wit.readnote.main.readNoteApp
import ie.wit.readnote.models.BookModel
import ie.wit.readnote.models.NoteModel
import timber.log.Timber

interface NoteListener {
    fun onNoteClick(note: NoteModel){
    }
}

class NoteAdapter constructor(private var notes: List<NoteModel>, private val listener: NoteListener)
    : RecyclerView.Adapter<NoteAdapter.MainHolder>() {
    lateinit var app : readNoteApp
    val resources: Resources = Resources.getSystem()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardNoteBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val note = notes[holder.adapterPosition]
        holder.bind(note, listener)
    }

    override fun getItemCount(): Int = notes.size

    inner class MainHolder(val binding : CardNoteBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(note: NoteModel, listener: NoteListener) {
            binding.noteContent.text = note.content
            binding.pageNumber.text = note.pageNumber
            binding.root.setOnClickListener { listener.onNoteClick(note) }
        }
    }
}
