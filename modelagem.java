package projeto1;
import org.knowm.xchart.*;
import org.knowm.xchart.internal.chartpart.Chart;

public class Inicio {
	
	static double k = 1.0;
	static double g = 10.0;
	static double m = 3.0;
	static double delta = 0.01;
	static double alturaInicial = 1000.0;
	static double velocidadeInicial = 0.0;
	static int intervalo = 1000;
	
	public static void main(String[] args) throws Exception {
		velocidadeY();
		movimentoY();
		
	}
	
	static void movimentoY() {
		// Inicializando Vetores
		double[] datax = new double[intervalo];
		double[] datay = new double[intervalo];
		double[] velocidade = new double[intervalo];
		
		// Gerando data x
		double aux = 0.0;
		for(int i =0 ; i < intervalo; i++) {
			datax[i] = aux;
			aux += delta;
		}
		// gerando velocidade
		double vAnterior = velocidadeInicial;
		for(int i =0 ; i < intervalo; i++) {
			velocidade[i] = vAnterior = v(datax[i], vAnterior);
		}
		// gerando data y
		for(int i =0 ; i < intervalo; i++) {
			datay[i] = alturaInicial + -velocidade[i]*datax[i];
		}
	    // plotando grafico
	    XYChart chart = QuickChart.getChart("MOVIMENTO", 
	    		"Tempo", "Deslocamento", "y(x)", datax, datay);
	    new SwingWrapper(chart).displayChart();
	}
	
	
	static void velocidadeY() {
		// Inicializando Vetores
		double[] datax = new double[intervalo];
		double[] datay = new double[intervalo];
		
		// Gerando data x
		double aux = 0.0;
		for(int i =0 ; i < intervalo; i++) {
			datax[i] = aux;
			aux += delta;
		}
		// gerando data y
		double vAnterior = velocidadeInicial;
		for(int i =0 ; i < intervalo; i++) {
			datay[i] = vAnterior = v(datax[i], vAnterior);
		}
	    // plotando grafico
	    XYChart chart = QuickChart.getChart("Velocidade", 
	    		"Tempo", "Velocidade", "y(x)", datax, datay);
	    new SwingWrapper(chart).displayChart();
	 }
	
	
	static double v(double x, double vAnterior) {
		return vAnterior + (g -(k/m)*vAnterior)*delta;
	}
}
