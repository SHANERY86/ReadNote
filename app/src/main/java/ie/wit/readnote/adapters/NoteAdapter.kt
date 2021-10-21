package ie.wit.donationx.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ie.wit.readnote.databinding.CardNoteBinding
import ie.wit.readnote.models.NoteModel

class NoteAdapter constructor(private var notes: List<NoteModel>)
    : RecyclerView.Adapter<NoteAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardNoteBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val donation = notes[holder.adapterPosition]
        holder.bind(donation)
    }

    override fun getItemCount(): Int = notes.size

    inner class MainHolder(val binding : CardNoteBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(note: NoteModel) {
            binding.noteContent.text = note.content
        }
    }
}
