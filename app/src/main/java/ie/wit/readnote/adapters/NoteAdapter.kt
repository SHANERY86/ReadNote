package ie.wit.donationx.adapters



import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ie.wit.readnote.databinding.CardNoteBinding
import ie.wit.readnote.models.NoteModel

interface NoteListener {
    fun onNoteClick(noteid: NoteModel){
    }
}

class NoteAdapter constructor(private var notes: ArrayList<NoteModel>, private val listener: NoteListener)
    : RecyclerView.Adapter<NoteAdapter.MainHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardNoteBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val note = notes[holder.adapterPosition]
        holder.bind(note, listener)
    }

    fun removeAt(position: Int) {
        notes.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun getItemCount(): Int = notes.size

    inner class MainHolder(val binding : CardNoteBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(note: NoteModel, listener: NoteListener) {
            binding.root.tag = note
            binding.noteContent.text = note.content
            binding.pageNumber.text = note.pageNumber
            if (note.pageNumber.isNotEmpty()){
                binding.pageNumber.setVisibility(View.VISIBLE)
            }
            binding.root.setOnClickListener { listener.onNoteClick(note) }
        }
    }
}
