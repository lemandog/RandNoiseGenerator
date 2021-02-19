package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Vector;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("? MAP SIZE = ");
        System.out.print("? >");
        int size = in.nextInt();
        System.out.println("? RANDOM NOISE SCALE = ");
        System.out.print("? >");
        int density = in.nextInt();
        double[][] Mymatrix = RandomNoiseGen(size);
        double maximum = maximumFound(Mymatrix);

        graphicsAdvance(Mymatrix, maximum, density, size);
        graphics(Mymatrix, maximum);
        linearOutput(Mymatrix, maximum);

    }

    static double[][] RandomNoiseGen(int size){

        double[][] tanMat = new double[size][size];
        Vector scaleVec = new Vector(0);

        //ROTA TE BANANA [X, Y]
        for(int x=0;x<tanMat.length;x++){
            for(int y=0;y<tanMat.length;y++){
                tanMat[x][y] = (Math.random()*256); //RANDOM X COORD
            }}
        return tanMat;
    }

    static double fade(double t){
        return t * t * t * (t * (t * 6 - 15) + 10);
    }
    static double interpol(double a, double b, double t){
        return a + t * (b - a);
    }
    static double scalar(double[] a, double[] b){
        return a[0] * b[0] + a[1] * b[1];
    }

    static void graphics(double[][] Mymatrix, double multi){
        for(int y=0;y<Mymatrix.length;y++){
            System.out.println();
            for(int x=0;x<Mymatrix.length;x++) {
                if (Mymatrix[x][y]/multi > 0.75)     {System.out.print(" ");}// 1/4
                else if (Mymatrix[x][y]/multi > 0.50){System.out.print("░");}// 2/4
                else if (Mymatrix[x][y]/multi > 0.25){System.out.print("▒");}// 3/4
                else if (Mymatrix[x][y]/multi > 0)   {System.out.print("▓");}// 4/4
            }}

    }

    private static void linearOutput(double[][] mymatrix, double maximum) {
        try {
            PrintWriter out = new PrintWriter("nums.txt");
            for(int y=0;y<mymatrix.length;y++){
                out.println();
                for(int x=0;x<mymatrix.length;x++) {
                    //out.print(" " + String.format("%1$,.3f", mymatrix[x][y]/(maximum)) + " ");
                    out.print(" " + (int) ((Math.abs(mymatrix[x][y]*256))/maximum) + " ");
                }}
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static double maximumFound(double[][] Mymatrix) {
        double maximum = 0;
        for(int y=0;y<Mymatrix.length;y++){
            for(int x=0;x<Mymatrix.length;x++) {
                if (Mymatrix[x][y] > maximum) {
                    maximum = Mymatrix[x][y];
                }
            }}
        return maximum;
    }

    private static void graphicsAdvance(double[][] mymatrix, double maximum, int size, int density) {
        int type = BufferedImage.TYPE_INT_ARGB;
        BufferedImage image = new BufferedImage(mymatrix.length, mymatrix.length, type);

        for(int y=0;y<mymatrix.length;y++){
            for(int x=0;x<mymatrix.length;x++) {
                Color rightNow = new Color((int) mymatrix[x][y],(int) mymatrix[x][y],(int) mymatrix[x][y]);
                image.setRGB(x, y, rightNow.getRGB());
            }
        }
        JFrame frame = new JFrame();
        Image tmp = image.getScaledInstance(800,800, Image.SCALE_SMOOTH);
        frame.getContentPane().add(new JLabel(new ImageIcon(tmp)));
        frame.pack();
        frame.setVisible(true);
        frame.setSize(800,800);
        File outputfile = new File("image.png");
        try {
            ImageIO.write(image, "png", outputfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}