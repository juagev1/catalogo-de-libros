package com.literalura.literalura.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.literalura.literalura.api.GutendexCliente;
import com.literalura.literalura.modelo.Autor;
import com.literalura.literalura.modelo.Libro;
import com.literalura.literalura.repository.AutorRepository;
import com.literalura.literalura.repository.LibroRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LibroService {

    private final LibroRepository libroRepo;
    private final AutorRepository autorRepo;
    private final GutendexCliente cliente;

    public LibroService(LibroRepository libroRepo, AutorRepository autorRepo, GutendexCliente cliente) {
        this.libroRepo = libroRepo;
        this.autorRepo = autorRepo;
        this.cliente = cliente;
    }

    public void buscarYGuardarLibro(String titulo) {
        Optional<Libro> existente = libroRepo.findByTituloIgnoreCase(titulo);
        if (existente.isPresent()) {
            System.out.println("⚠️ El libro ya está registrado.");
            return;
        }

        JsonNode libroJson = cliente.buscarLibro(titulo);
        if (libroJson == null) {
            System.out.println("❌ Libro no encontrado.");
            return;
        }

        String nombre = libroJson.get("title").asText();
        String idioma = libroJson.get("languages").get(0).asText();
        int descargas = libroJson.get("download_count").asInt();

        JsonNode autorJson = libroJson.get("authors").get(0);
        String nombreAutor = autorJson.get("name").asText();
        int nacimiento = autorJson.get("birth_year").asInt();
        int fallecimiento = autorJson.get("death_year").asInt();

        Autor autor = new Autor(nombreAutor, nacimiento, fallecimiento);
        autor = autorRepo.save(autor);

        Libro libro = new Libro(nombre, idioma, descargas, autor);
        libroRepo.save(libro);

        System.out.println("✅ Libro guardado: " + nombre);
    }

    public void listarLibros() {
        libroRepo.findAll().forEach(l -> {
            System.out.println("📖 Título: " + l.getTitulo());
            System.out.println("✍ Autor: " + l.getAutor().getNombre());
            System.out.println("🌐 Idioma: " + l.getIdioma());
            System.out.println("⬇ Descargas: " + l.getDescargas());
            System.out.println("---------------------------");
        });
    }


    public void listarAutores() {
        autorRepo.findAll().forEach(a -> {
            System.out.println(" " + a.getNombre());
        });
    }

    public void autoresVivosEn(int año) {
        List<Autor> autores = autorRepo.findByNacimientoBeforeAndFallecimientoAfter(año, año);
        autores.forEach(a -> System.out.println("👤 " + a.getNombre()));
    }

    public void librosPorIdioma(String idioma) {
        libroRepo.findByIdioma(idioma).forEach(l -> {
            System.out.println("📘 " + l.getTitulo());
        });
    }
}
