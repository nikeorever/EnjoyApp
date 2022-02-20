
import 'dart:async';
import 'dart:ffi';
import 'dart:io';

import 'package:ffi/ffi.dart';
import 'package:flutter/services.dart';

class NativeSupport {
  static const MethodChannel _channel = MethodChannel('native_support');

  static Future<String?> get platformVersion async {
    final String? version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }

  static int add(int lhs, int rhs) {
    if (Platform.isAndroid) {
      final DynamicLibrary nativeLib = DynamicLibrary.open("libmath.so");
      if (nativeLib.providesSymbol('add')) {
        final add = nativeLib.lookupFunction<Int32 Function(Int32, Int32), int Function(int, int)>('add');
        return add(lhs, rhs);
      }
    }
    return lhs + rhs;
  }

  static String greeting() {
    if (Platform.isAndroid) {
      final DynamicLibrary nativeLib = DynamicLibrary.open("libgreeting.so");
      if (nativeLib.providesSymbol('greeting')) {
        final greeting = nativeLib.lookupFunction<Pointer<Utf8> Function(Pointer<Utf8>), Pointer<Utf8> Function(Pointer<Utf8>)>('greeting');
        return greeting("Flutter".toNativeUtf8()).toDartString();
      }
    }
    return 'Hello Flutter';
  }

  static List<int>? fibonacciUntil(int n) {
    if (Platform.isAndroid) {
      final DynamicLibrary nativeLib = DynamicLibrary.open("libfibonacci.so");
      if (nativeLib.providesSymbol('fibonacci_until')) {
        final fibonacciUntil = nativeLib.lookupFunction<Pointer<Uint32> Function(Uint32), Pointer<Uint32> Function(int)>('fibonacci_until');
        final ptr = fibonacciUntil(n);
        return [for(int i = 0; i < n; i++) ptr[i]];
      }
    }
    return null;
  }
}
