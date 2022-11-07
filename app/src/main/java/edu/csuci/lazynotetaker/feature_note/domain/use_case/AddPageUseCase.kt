package edu.csuci.lazynotetaker.feature_note.domain.use_case

import edu.csuci.lazynotetaker.feature_note.domain.model.InvalidPageException
import edu.csuci.lazynotetaker.feature_note.domain.model.Page
import edu.csuci.lazynotetaker.feature_note.domain.repository.NoteRepository

class AddPageUseCase(private val repository: NoteRepository) {
    @Throws(InvalidPageException::class)
    suspend operator fun invoke(page: Page){
        repository.insertPage(page)
    }
}