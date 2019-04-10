import Flutter
import UIKit

public class SwiftFlutterSmsRetrieverPlugin: NSObject, FlutterPlugin {
  public static func register(with registrar: FlutterPluginRegistrar) {
    let channel = FlutterMethodChannel(name: "flutter_sms_retriever", binaryMessenger: registrar.messenger())
    let instance = SwiftFlutterSmsRetrieverPlugin()
    registrar.addMethodCallDelegate(instance, channel: channel)
  }

  public func handle(_ call: FlutterMethodCall, result: @escaping FlutterResult) {
    result("iOS " + UIDevice.current.systemVersion)
  }
}
