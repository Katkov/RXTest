package com.example.rxtest.mainscreen

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.rxtest.networking.model.Todo
import com.google.common.truth.Truth
import org.junit.Rule

import org.junit.Test

class MainViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val viewModel = MainViewModel()

    @Test
    fun addOrEdit() {
        val todo = Todo(-1, "Test",false)
        viewModel.addOrEdit(todo)
        Truth.assertThat(viewModel.result.value?.second).isNotEmpty()
    }

    @Test
    fun edit() {
        val todo = Todo(1, "Test",false)
        viewModel.add(todo)
        val newText = "Test2"
        todo.text = newText
        viewModel.edit(todo)
        Truth.assertThat(viewModel.result.value?.second).isNotEmpty()
        Truth.assertThat(viewModel.result.value?.second?.get(0)?.text).isEqualTo(newText)
    }

    @Test
    fun add() {
        val todo = Todo(1, "Test",false)
        viewModel.add(todo)
        Truth.assertThat(viewModel.result.value?.second).contains(todo)
    }

    @Test
    fun delete() {
        val todo = Todo(1, "Test",false)
        viewModel.add(todo)
        Truth.assertThat(viewModel.result.value?.second).contains(todo)
        viewModel.delete(todo)
        Truth.assertThat(viewModel.result.value?.second).isEmpty()
    }

    @Test
    fun setStyle() {
        viewModel.setStyle(Style.SHOW_DONE)
        Truth.assertThat(viewModel.result.value?.first).isEqualTo(Style.SHOW_DONE)
    }

}