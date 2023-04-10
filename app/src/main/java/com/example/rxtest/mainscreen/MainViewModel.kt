package com.example.rxtest.mainscreen

import androidx.lifecycle.ViewModel
import com.example.rxtest.networking.model.Todo
import com.example.rxtest.repository.Repository
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val result = BehaviorSubject.create<List<Todo>>()

    fun addOrEdit(todo: Todo) {
        val current = result.value ?: listOf()
        val mutableCopy = mutableListOf<Todo>()
        mutableCopy.addAll(current)
        if (todo.id == -1) {
            todo.id = mutableCopy.size
            mutableCopy.add(todo)
        } else {
            mutableCopy.forEach { t ->
                if (t.id == todo.id) {
                    t.apply {
                        text = todo.text
                        isDone = todo.isDone
                    }
                }
            }
        }
        result.onNext(mutableCopy)
    }

    fun delete(todo: Todo) {
        val current = result.value ?: listOf()
        val mutableCopy = mutableListOf<Todo>()
        mutableCopy.addAll(current)
        mutableCopy.remove(todo)
        result.onNext(mutableCopy)
    }

    fun addToComposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    fun clearComposable() {
        compositeDisposable.clear()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}