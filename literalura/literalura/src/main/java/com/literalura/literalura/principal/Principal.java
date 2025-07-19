package com.literalura.literalura.principal;

import com.literalura.literalura.service.LibroService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class Principal implements CommandLineRunner {

    private final LibroService servicio;

    public Principal(LibroService servicio) {
        this.servicio = servicio;
    }

    @Override
    public void run(String... args) {
        Scanner teclado = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n📚 Menú:");
            System.out.println("1 - Buscar libro por título");
            System.out.println("2 - Listar libros registrados");
            System.out.println("3 - Listar autores registrados");
            System.out.println("4 - Listar autores vivos en un año");
            System.out.println("5 - Listar libros por idioma (ES, EN, FR, PT)");
            System.out.println("0 - Salir");
            System.out.print("Opción: ");
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1 -> {
                    System.out.print("Título del libro: ");
                    String titulo = teclado.nextLine();
                    servicio.buscarYGuardarLibro(titulo);
                }
                case 2 -> servicio.listarLibros();
                case 3 -> servicio.listarAutores();
                case 4 -> {
                    System.out.print("Año: ");
                    int año = teclado.nextInt();
                    servicio.autoresVivosEn(año);
                }
                case 5 -> {
                    System.out.print("Idioma (ES, EN, FR, PT): ");
                    String idioma = teclado.nextLine().toLowerCase();
                    servicio.librosPorIdioma(idioma);
                }
                case 0 -> System.out.println("👋 Adiós!");
                default -> System.out.println("❌ Opción inválida.");
            }

        } while (opcion != 0);
    }
}
