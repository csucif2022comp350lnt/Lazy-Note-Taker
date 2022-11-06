package edu.csuci.lazynotetaker.feature_note.domain.use_case

import edu.csuci.lazynotetaker.feature_note.domain.model.Page
import edu.csuci.lazynotetaker.feature_note.domain.repository.NoteRepository

class GetPageUseCase(private val repository: NoteRepository) {
    suspend operator fun invoke (id: Int): Page?{
        return repository.getPageById(id)
    }
}