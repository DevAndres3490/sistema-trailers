package com.sistema.trailers.servicio;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.sistema.trailers.excepciones.AlmacenException;
import com.sistema.trailers.excepciones.FileNotFoundException;

import jakarta.annotation.PostConstruct;

@Service
public class AlmacenServicioImpl implements AlmacenServicio {

    @Value("${storage.location}")
    private String storageLocation;

    @PostConstruct
    @Override
    public void iniciarAlmacenDeArchivos() {
        try {
            Files.createDirectories(Paths.get(storageLocation));
        } catch (IOException e) {
            throw new AlmacenException("Error al inicializar la ubicación en el almacenamiento de archivos", e);
        }
    }

    @Override
    public String almacenarArchivos(MultipartFile archivo) {
        String nombreArchivo = StringUtils.cleanPath(archivo.getOriginalFilename());

        if (archivo.isEmpty()) {
            throw new AlmacenException("No se puede almacenar un archivo vacío.");
        }

        try (InputStream inputStream = archivo.getInputStream()) {
            Files.copy(inputStream, Paths.get(storageLocation).resolve(nombreArchivo), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException exception) {
            throw new AlmacenException("Error al almacenar el archivo: " + nombreArchivo, exception);
        }

        return nombreArchivo;
    }

    @Override
    public Path cargarArchivo(String nombreArchivo) {
        return Paths.get(storageLocation).resolve(nombreArchivo);
    }

    @Override
    public Resource cargarComoRecurso(String nombreArchivo) {
        try {
            Path archivo = cargarArchivo(nombreArchivo);
            Resource recurso = new UrlResource(archivo.toUri());

            if (recurso.exists() || recurso.isReadable()) {
                return recurso;
            } else {
                throw new FileNotFoundException("No se pudo encontrar el archivo: " + nombreArchivo);
            }
        } catch (MalformedURLException e) {
            throw new FileNotFoundException("No se pudo encontrar el archivo: " + nombreArchivo, e);
        }
    }

    @Override
    public void eliminarArchivo(String nombreArchivo) {
        Path archivo = cargarArchivo(nombreArchivo);
        
        try {
        	FileSystemUtils.deleteRecursively(archivo);
			
		} catch (Exception exception) {
			
			System.out.println(exception);
		}
    }
}
