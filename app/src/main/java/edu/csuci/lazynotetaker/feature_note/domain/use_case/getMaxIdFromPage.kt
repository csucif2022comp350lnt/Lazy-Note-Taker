package edu.csuci.lazynotetaker.feature_note.domain.use_case

import edu.csuci.lazynotetaker.feature_note.domain.model.Note
import edu.csuci.lazynotetaker.feature_note.domain.repository.NoteRepository

class getMaxIdFromPage(
    private val repository: NoteRepository
) {
    operator fun invoke (): Int? {
        return repository.getMaxIdFromPage()
    }
}