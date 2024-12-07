mod client; // includes a module
mod image; 
mod complex; 
mod mandelbrot; 

use client::parse_args;
use mandelbrot::generate_image;

fn main() {
    // uncomment and implement argument parsing, priting an error message in case of a parsing error
    // call client::parse_args() to get width, height and max_iterations from the input arguments
    let (width, height, max_iterations) = parse_args().expect("Failed to parse arguments");
    // if the above was implemented correctly you can uncomment this line
     println!("Generating Mandelbrot for {}x{} image (max_iterations: {})", width, height, max_iterations);

    // call mandelbrot::generate_image(width, height, max_iterations) and save the result to an image
    let image = generate_image(width, height, max_iterations);
    // call the get_mandelbrot_pixels() method on the image struct and save the result in mandelbrot_pixel_count
    // if the above line worked you should be able to uncomment this line
     println!("Pixels in the set: {}",image.get_mandelbrot_pixels());
    
    // uncomment and call after you implement the mandelbrot functions, and handle the possible error
     client::save_to_file(&image, "mandelbrot.ppm");
    
}
