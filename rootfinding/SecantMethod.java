/**
* Implementation of the Newton-Raphson Method to find the roots 
* of non-linear equations
*
* @author Kevin Scaccia
*/
import org.knowm.xchart.*;
import java.awt.*;

public class SecantMethod {
    /*
     *   Secant Method (double precision)
     *   f: function
     *   x0 and x1: initial guesses
     *   epsilon: precision of the method
     */
    static double secant_method(Function f, double x0, double x1, double epsilon) {
        // Aproximate tangent by secant
        int iteration = 1, max_iterations = 100;// limit iterations
        while ( (Math.abs(x1-x0) > epsilon) && (iteration < max_iterations) ) {
            double tmp = x1;
            x1 = x1 - (f.calc(x1)*(x1-x0)) / (f.calc(x1)-f.calc(x0));
            x0 = tmp;
            iteration++;
        }
        return x1;
    }
    /*  Interface that represents a function
     *  just implement the calc method with your function
     */
    interface Function {
        double calc(double x);
    }
    /*****************************************************
     *
     *   Aditional code to test method, plot function and the root found
     *   There are 3 function examples
     *   The method code is the same
     *   Using Xchart free plot library
     *
     */
    public static void main(String[] args) {
        example_1();// Run example 1
        example_2();// Run example 2
        example_3();// Run example 3
    }
    // Example with Function 1: f(x)= x^3 - x^2 + 2
    static void example_1() {
        Function func = (double x) -> (x*x*x - x*x + 2);
        secant_method_with_plot(func, 5, 9,-5, 5, 0.001);// Plot example
    }
    // Example with Function 2: f(x)= sin(x^2) -x+1
    static void example_2() {
        Function func = (double x)-> (Math.sin(x*x)-x+1);
        secant_method_with_plot(func, -2, 2, -3, 4, 0.001);// Plot example
    }
    // Example with Function 2: f(x)= sin( x/(x^3) ) *x;
    static void example_3() {
        Function func = (double x)-> (Math.sin((x)/(x*x*x))*x);
         secant_method_with_plot(func, 0.09, 1, 0.05, 2, 0.001);// Plot example
    }
    // Modified Method
    static void secant_method_with_plot(Function f, double x1, double x0,
                                        double a, double b, double epsilon) {
        // Aditional Code to plot f(x) and root aproximation
        double plot_interval = 0.001f;// Plot 'quality'
        int interval = (int)((b-a)/plot_interval) +2;// Plot interval
        double step = a;
        // generate (x,y) data to plot points
        double[] x_data = new double[interval], y_data = new double[interval];
        for(int i = 0; i < interval; i++){
            x_data[i] = step;// x
            y_data[i] = f.calc((float) step);// f(x)
            step += plot_interval;
        }
        // Plot f(x)
        XYChart chart = QuickChart.getChart("f(x)", "X",
                "Y", "f(x)", x_data, y_data);
        // Plot x axis
        chart.addSeries("x axis",
                new double[]{a, b}, new double[]{0, 0});
        // Method itself
        int iteration = 1, max_iterations = 100;// limit iterations
        while ( (Math.abs(x1-x0) > epsilon) && (iteration < max_iterations) ) {
            double tmp = x1;
            x1 = x1 - (f.calc(x1)*(x1-x0)) / (f.calc(x1)-f.calc(x0));
            x0 = tmp;
            iteration++;
        }
        // Plot root found:
        chart.addSeries("root", new double[]{x1},
                new double[]{f.calc(x1)}).setMarkerColor(Color.RED);
        new SwingWrapper(chart).displayChart();// Plot itself
        System.out.printf("Root is at x= %.8f with f(x)= %.8f on %d iterations\n", x1, f.calc(x1), iteration);
    }
}
