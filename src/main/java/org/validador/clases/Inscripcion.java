package org.validador.clases;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Inscripcion {

    private Alumno alumno;
    private Materia materia;

    public Inscripcion(Alumno alumno, Materia materia) {
        this.alumno = alumno;
        this.materia = materia;
    }

    public Inscripcion() {
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public Materia getMateria() {
        return materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }

    public boolean aprobada() {
        return materia.puedeCursar(getAlumno(), getMateria());
    }

    public static List<Inscripcion> cargarInscripcionesDesdeArchivo(String filePath, List<Alumno> alumnos, List<Materia> materias) {
        // Leer datos de solicitudes desde el archive CSV
        List<Inscripcion> solicitudes = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                Alumno idAlumno = Alumno.buscarAlumno(data[0], alumnos);
                Materia materia = Materia.buscarMateria(data[1], materias);

                // Crear una nueva instancia de SolicitudInscripcion y agregarla a la lista
                Inscripcion solicitud = new Inscripcion(idAlumno, materia);
                solicitudes.add(solicitud);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return solicitudes;
    }

}

