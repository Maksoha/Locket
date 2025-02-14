package ru.chads.data

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.TimeoutCancellationException

@Suppress("RethrowCaughtException", "TooGenericExceptionCaught", "RedundantSuspendModifier")
suspend inline fun <R> runSuspendCatching(block: () -> R): Result<R> =
    try {
        Result.success(block())
    } catch (t: TimeoutCancellationException) {
        Result.failure(t)
    } catch (c: CancellationException) {
        throw c
    } catch (e: Throwable) {
        Result.failure(e)
    }