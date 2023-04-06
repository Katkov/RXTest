package com.example.rxtest

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.observers.TestObserver
import io.reactivex.rxjava3.subscribers.TestSubscriber
import org.junit.Assert.*
import org.junit.Test
import java.lang.Exception

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun whenChangeStateOnError_thenErrorThrown() {
        val testObserver = TestObserver<Int>()
        Observable
            .just(10, 20, 30)
            .map {
                if (it == 20) throw NullPointerException()
                it
            }
            .onErrorResumeNext {
                Observable.just(20)
            }
            .subscribe(testObserver)
        testObserver.assertValueCount(2)
        testObserver.assertValues(10,20)
        testObserver.assertComplete()
    }
}