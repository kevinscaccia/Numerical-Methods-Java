import org.knowm.xchart.*;
import java.awt.*;

public class BisectionMethod {
    /*
     *   Bisection Method (double precision)
     *   [a,b]: interval that must contains a root
     *   epsilon: precision of the method
     *
     */
    static double bisection_method(Function f, double a, double b, double epsilon) {
        double midpoint = (a + b) / 2;// calc midpoint
        int iteration = 1, max_iterations = 100;// limit iterations
        //
        while ((b - a > epsilon) && (iteration < max_iterations)) {
            if (midpoint == 0)// Root
                return midpoint;
            if (f.calc(a) * f.calc(midpoint) < 0)// root in left interval
                b = midpoint;
            else
                a = midpoint;// root in right interval
            midpoint = (a + b) / 2;// recalc midpoint
            iteration++;
        }
        return midpoint;
    }
    /*  Interface that represents a function
     *  just implement the calc method with your function
     */
    interface Function {
        double calc(double x);
    }
    /*****************************************************
     *
     *   Aditional code to test method, plot function and root found
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
    // Example with Function 1: f(x)= x^2 -2
    static void example_1() {
        Function func = (double x)-> (x*x -2);
        bisection_method_with_plot(func, 0.0, 5.0, 0.001);// Plot example
    }
    // Example with Function 2: f(x)= sin(x^2) -x+1
    static void example_2() {
        Function func = (double x)-> (Math.sin(x*x)-x+1);
         bisection_method_with_plot(func, -3, 4, 0.001);// Plot example
    }
    // Example with Function 2: f(x)= sin( x/(x^3) ) *x;
    static void example_3() {
        Function func = (double x)-> (Math.sin((x)/(x*x*x))*x);
        bisection_method_with_plot(func, 0.05, 2, 0.001);// Plot example
    }
    static void bisection_method_with_plot(Function f, double a, double b, double epsilon){
        // Aditional Code to plot f(x) and root aproximation
        double plot_interval = 0.001f;// Plot 'quality'
        int interval = (int)((b-a)/plot_interval) +2;// Plot interval
        double x = a;
        // (x,y) data to plot points
        double[] x_data = new double[interval], y_data = new double[interval];
        for(int i = 0; i < interval; i++){
            x_data[i] = x;// x
            y_data[i] = f.calc((float) x);// f(x)
            x += plot_interval;
        }
        // Plot f(x)
        XYChart chart = QuickChart.getChart("f(x)", "X",
                "Y", "f(x)", x_data, y_data);
        // Plot x axis
        chart.addSeries("x axis",
                new double[]{a, b}, new double[]{0, 0});

        // Method itself
        int iteration = 1, max_iterations = 100;// limit iterations
        double midpoint = (a+b)/2;
        //
        while((b-a > epsilon) && (iteration < max_iterations)) {
            if(midpoint == 0)// Root
                break;
            if(f.calc(a)*f.calc(midpoint) < 0)
                b = midpoint;
            else
                a = midpoint;
            //
            midpoint = (a+b)/2;
            iteration++;
        }
        // Plot root found:
        chart.addSeries("root", new double[]{midpoint},
                new double[]{f.calc(midpoint)}).setMarkerColor(Color.RED);
        new SwingWrapper(chart).displayChart();// Plot itself
        System.out.printf("Root is at x= %.8f with f(x)= %.8f on %d iterations\n", midpoint, f.calc(midpoint), iteration);
    }
}
