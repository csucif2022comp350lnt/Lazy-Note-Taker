package edu.csuci.lazynotetaker.feature_note.domain.use_case

import edu.csuci.lazynotetaker.feature_note.domain.repository.NoteRepository
import edu.csuci.lazynotetaker.feature_note.domain.model.Page
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.Flow

class GetPagesUseCase(
    private val repository: NoteRepository
    ){
    operator fun invoke(): Flow<List<Page>> {
        return repository.getPages().map { pages ->
            pages.sortedBy {it.pageNumber}
        }


    }
}