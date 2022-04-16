package src;

import java.io.*;
import java.time.LocalDate;
import java.util.*;


public class Simulador implements Serializable{
    public static LocalDate data = LocalDate.now();
    private List<CasaInteligente> casasInteligentes;
    private List<Comercializador> comercializadores;

    public Simulador() {
        this.casasInteligentes = new ArrayList<>();
        this.comercializadores = new ArrayList<>();
    }

    public Simulador(LocalDate date) {
        this.casasInteligentes = new ArrayList<>();
        this.comercializadores = new ArrayList<>();
        data = date;
    }

    public static Simulador construirSimulador(String caminhoFicheiro) {
        //Recebe o caminho para um ficheiro com um Simulador ja feito, e constroi-o
        Simulador simulador = null;
        try {
            FileInputStream fi = new FileInputStream(new File(caminhoFicheiro));
            ObjectInputStream oi = new ObjectInputStream(fi);

            simulador = (Simulador) oi.readObject();

            fi.close();
            oi.close();
        } catch (FileNotFoundException e) {
            System.out.println("Ficheiro nao encontrado");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Erro a inicializar a stream");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        if (simulador == null) System.out.println("Simulador e null");
        return simulador; //se devolver null, deu problema em cima
    }

    public void saltarDias(int daysToSkip) {
        data = data.plusDays(daysToSkip);
        for (CasaInteligente casaInteligente: this.casasInteligentes) {
            casaInteligente.saltarParaData(data);
        }
    }

    public static void startInterface() {
        Simulador simulador = construirSimulador("presets/simulador1.txt"); //recebe o caminho para um ficheiro onde esta escrito uma entidade da classe Simulador
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Data atual: " + Simulador.data.toString());
            System.out.println("Quantos dias queres saltar?");
            int diasParaSaltar = scanner.nextInt();
            simulador.saltarDias(diasParaSaltar);
            System.out.println("Data atual: " + Simulador.data.toString());
            System.out.println("A emitir faturas");
            simulador.printFaturas();
        }

    }

    public static void main(String[] args) {
        startInterface();
    }

    public void addCasa(CasaInteligente casaInteligente) {
        this.casasInteligentes.add(casaInteligente.clone());
    }

    public void addComercializador(Comercializador comercializador) {
        this.comercializadores.add(comercializador);
    }

    public void printFaturas() {
        for (CasaInteligente casaInteligente: this.casasInteligentes) {
            System.out.println(casaInteligente.getFaturas());
        }
    }
}