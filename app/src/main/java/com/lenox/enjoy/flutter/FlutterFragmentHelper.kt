package com.lenox.enjoy.flutter

import android.content.Context
import io.flutter.embedding.android.FlutterFragment
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor

private const val ENGINE_ID = "enjoy_engine"

/**
 * By default, a FlutterFragment creates its own instance of a FlutterEngine, which requires non-trivial warm-up time
 */
fun warmFlutterEngine(context: Context) {
    // Somewhere in your app, before your FlutterFragment is needed,
    // like in the Application class ...
    // Instantiate a FlutterEngine.
    val flutterEngine = FlutterEngine(context)

    // Start executing Dart code in the FlutterEngine.
    flutterEngine.dartExecutor.executeDartEntrypoint(
        DartExecutor.DartEntrypoint.createDefault()
    )

    // Cache the pre-warmed FlutterEngine to be used later by FlutterFragment.
    FlutterEngineCache
        .getInstance()
        .put(ENGINE_ID, flutterEngine)

}

fun createFlutterFragment(): FlutterFragment {
    return FlutterFragment.withCachedEngine(ENGINE_ID).build()
}