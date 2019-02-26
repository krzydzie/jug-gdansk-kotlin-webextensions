package pl.jug.lib

typealias ActionWithId = (String) -> Unit
typealias Action = () -> Unit
typealias AsyncAction = suspend () -> Unit
typealias AsyncResultAction<T> = suspend () -> Result<T>