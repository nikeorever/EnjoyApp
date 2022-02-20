#import "NativeSupportPlugin.h"
#if __has_include(<native_support/native_support-Swift.h>)
#import <native_support/native_support-Swift.h>
#else
// Support project import fallback if the generated compatibility header
// is not copied when this plugin is created as a library.
// https://forums.swift.org/t/swift-static-libraries-dont-copy-generated-objective-c-header/19816
#import "native_support-Swift.h"
#endif

@implementation NativeSupportPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftNativeSupportPlugin registerWithRegistrar:registrar];
}
@end
