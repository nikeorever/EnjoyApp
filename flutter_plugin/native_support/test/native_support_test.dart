import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:native_support/native_support.dart';

void main() {
  const MethodChannel channel = MethodChannel('native_support');

  TestWidgetsFlutterBinding.ensureInitialized();

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('getPlatformVersion', () async {
    expect(await NativeSupport.platformVersion, '42');
  });
}
