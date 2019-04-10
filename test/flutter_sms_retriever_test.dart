import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:flutter_sms_retriever/flutter_sms_retriever.dart';

void main() {
  const MethodChannel channel = MethodChannel('flutter_sms_retriever');

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('getPlatformVersion', () async {
    expect(await FlutterSmsRetriever.platformVersion, '42');
  });
}
