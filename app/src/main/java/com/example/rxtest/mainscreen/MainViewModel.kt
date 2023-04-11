package com.example.rxtest.mainscreen

import androidx.lifecycle.ViewModel
import com.example.rxtest.networking.model.Todo
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import javax.inject.Inject

class MainViewModel @Inject constructor() : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val result = BehaviorSubject.create<Pair<Style, List<Todo>>>()

    fun addOrEdit(todo: Todo) {
        if (todo.id == -1) add(todo) else edit(todo)
    }

    fun edit(todo: Todo) {
        val current = result.value ?: Pair(Style.SHOW_ALL, listOf())
        val currentStyle = current.first
        val currentList = current.second
        val mutableListCopy = mutableListOf<Todo>()
        mutableListCopy.addAll(currentList)
        mutableListCopy.find { it.id == todo.id }?.apply {
            text = todo.text
            isDone = todo.isDone
        }
        result.onNext(currentStyle to mutableListCopy)
    }

    fun add(todo: Todo) {
        val current = result.value ?: Pair(Style.SHOW_ALL, listOf())
        val currentStyle = current.first
        val currentList = current.second
        val mutableListCopy = mutableListOf<Todo>()
        mutableListCopy.addAll(currentList)
        todo.id = mutableListCopy.size
        mutableListCopy.add(todo)
        result.onNext(currentStyle to mutableListCopy)
    }

    fun delete(todo: Todo) {
        val current = result.value ?: Pair(Style.SHOW_ALL, listOf())
        val currentStyle = current.first
        val currentList = current.second
        val mutableListCopy = mutableListOf<Todo>()
        mutableListCopy.addAll(currentList)
        mutableListCopy.remove(todo)
        result.onNext(currentStyle to mutableListCopy)
    }

    fun setStyle(style: Style) {
        val current = result.value ?: Pair(Style.SHOW_ALL, listOf())
        val currentList = current.second
        val mutableListCopy = mutableListOf<Todo>()
        mutableListCopy.addAll(currentList)
        result.onNext(style to mutableListCopy)
    }

    fun addToComposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    fun clearComposable() {
        compositeDisposable.dispose()
    }

}

enum class Style {
    SHOW_ALL, SHOW_DONE, SHOW_UNDONE
}