#import "FlutterSmsRetrieverPlugin.h"
#import <flutter_sms_retriever/flutter_sms_retriever-Swift.h>

@implementation FlutterSmsRetrieverPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftFlutterSmsRetrieverPlugin registerWithRegistrar:registrar];
}
@end
