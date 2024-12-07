 use std::fs;
 use std::num::ParseIntError;
 use crate::image::Image; 
 use std::env;
// uncomment and implement: 
 pub fn parse_args() -> Result<(usize, usize, usize), ParseIntError> {
    // parse input arguments here, and return their values
    let args: Vec<String> = env::args().collect();

    if args.len() < 3 || args.len() > 4 {
        println!("Usage: {} <width> <height> <max_iterations>", args[0]);
        std::process::exit(1);
    }

    let width = args[1].parse::<usize>()?;
    let height = args[2].parse::<usize>()?;
    let max_iterations = if args.len() == 4 {
        args[3].parse::<usize>()?
    } else {
        1024
    };

    Ok((width, height, max_iterations))
 }


pub fn save_to_file(image: &Image, filename: &str) {
    let mut s = String::new();
    s.push_str(&format!("P3\n{} {}\n255\n", image.width, image.height));
    
    for y in 0..image.height {
        for x in 0..image.width {
            let pixel = image.get(x, y).unwrap();
            s.push_str(&format!("{}\n", pixel));
        }
    }

    fs::write(filename, s).expect("Error writing to disk!");
}
