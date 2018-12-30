/**
* Implementation of the Newton-Raphson Method to find the roots 
* of non-linear equations
*
* @author Kevin Scaccia
*/
import org.knowm.xchart.*;
import java.awt.*;

public class NewtonRaphsonMethod {
    /*
     *   Newton Raphson Method (double precision)
     *   f: function that we know its derivative
     *   x: initial guess to root
     *   epsilon: precision of the method
     */
    static double newtonraphson_method(Function f, double x, double epsilon) {
        double h = f.calc(x)/f.derivative(x);// h = f(x0)/f'(x0)
        x = x - h;// x1 = x0 - f(x0)/f'(x0)
        int iteration = 1, max_iterations = 100;// limit iterations
        //
        while ( (Math.abs(h) > epsilon) && (iteration < max_iterations) ) {
            h = f.calc(x)/f.derivative(x);// h = f(x0)/f'(x0)
            x = x - h;// x1 = x0 - f(x0)/f'(x0)
            iteration++;
        }
        return x;
    }
    /*  Interface that represents a function
     *  just implement the calc and derivative method with your function
     */
    interface Function {
        double calc(double x);
        double derivative(double x);
    }
    /*****************************************************
     *
     *   Aditional code to test method, plot function and the root found
     *   There are 2 function examples
     *   The method code is the same
     *   Using Xchart free plot library
     *
     */
    public static void main(String[] args) {
        example_1();// Run example 1
        example_2();// Run example 2
    }

    // Example with Function 1: f(x)= x^3 - x^2 + 2
    static void example_1() {
        Function func = new Function() {
            public double calc(double x) { return x*x*x - x*x + 2;};
            public double derivative(double x) { return 3*x*x - 2*x; }
        };
        double root = newtonraphson_method_with_plot(func, 5, -5, 5, 0.001);// Plot example
        System.out.println("Root is at x= "+root);
    }

    // Example with Function 2: f(x)= sin(x^2) -x +1
    static void example_2() {
        Function func = new Function() {
            public double calc(double x) { return Math.sin(x*x) -x +1;};
            public double derivative(double x) { return Math.cos(x*x)*2*x -1; }
        };
        double root = newtonraphson_method_with_plot(func, -0.8, -1, 4, 0.001);// Plot example
        System.out.println("Root is at x= "+root);
    }

    // Modified Method
    static double newtonraphson_method_with_plot(Function f, double x, double a, double b, double epsilon) {
        // Aditional Code to plot f(x) and root aproximation
        double plot_interval = 0.01f;// Plot 'quality'
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
        double h = f.calc(x)/f.derivative(x);// h = f(x0)/f'(x0)
        x = x - h;// x1 = x0 - f(x0)/f'(x0)
        int iteration = 1, max_iterations = 100;// limit iterations
        // plot first tangent
        chart.addSeries("tg"+0, new double[]{x+h, x},
                new double[]{f.calc(x+h), 0});
        while ( (Math.abs(h) > epsilon) && (iteration < max_iterations) ) {
            h = f.calc(x)/f.derivative(x);// h = f(x0)/f'(x0)
            x = x - h;// x1 = x0 - f(x0)/f'(x0)
            iteration++;
            // Plot tangent
            chart.addSeries("tg"+iteration, new double[]{x+h, x},
                    new double[]{f.calc(x+h), 0});

        }
        // Plot root found:
        chart.addSeries("root", new double[]{x},
                new double[]{f.calc(x)}).setMarkerColor(Color.RED);
        new SwingWrapper(chart).displayChart();// Plot itself
        return x;
    }
}
