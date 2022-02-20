#[no_mangle]
pub extern "C" fn add(lhs: i32, rhs: i32) -> i32 {
    lhs.wrapping_add(rhs)
}

#[cfg(test)]
mod tests {
    #[test]
    fn it_works() {
        assert_eq!(super::add(1, 2), 4);
    }
}
