use std::os::raw::c_char;
use std::ffi::{CStr, CString};

#[no_mangle]
pub extern "C" fn greeting(you: *const c_char) -> *mut c_char {
    let you_prt = unsafe { CStr::from_ptr(you) };
    let recipient = match you_prt.to_str() {
        Err(_) => "World",
        Ok(str) => str,
    };
    let s = CString::new("Hello ".to_owned() + &recipient.to_owned() + ", I'm Rust").unwrap();
    s.into_raw()
}

/// Expose the JNI interface for android below
#[cfg(target_os="android")]
#[allow(non_snake_case)]
pub mod android {
    extern crate jni;

    use super::*;
    use self::jni::JNIEnv;
    use self::jni::objects::{JClass, JString};
    use self::jni::sys::{jstring};

    #[no_mangle]
    pub unsafe extern fn Java_com_lenox_enjoy_RustNative_greeting(env: JNIEnv, _: JClass, java_pattern: JString) -> jstring {
        // Our Java companion code might pass-in "world" as a string, hence the name.
        let greeting = greeting(env.get_string(java_pattern).expect("invalid pattern string").as_ptr());
        // Retake pointer so that we can use it below and allow memory to be freed when it goes out of scope.
        let greeting_ptr = CString::from_raw(greeting);
        let output = env.new_string(greeting_ptr.to_str().unwrap()).expect("Couldn't create java string!");

        output.into_inner()
    }
}

#[cfg(test)]
mod tests {
    #[test]
    fn it_works() {
        let result = 2 + 2;
        assert_eq!(result, 4);
    }
}
