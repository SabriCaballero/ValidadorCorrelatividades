package org.validador;
import org.validador.clases.Alumno;
import org.validador.clases.Inscripcion;
import org.validador.clases.Materia;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        String filePathMaterias = Main.class.getClassLoader().getResource("materias.csv").getPath();
        List<Materia> materias = Materia.cargarMateriasDesdeArchivo(filePathMaterias);

        String filePathAlumnos = Main.class.getClassLoader().getResource("alumnos.csv").getPath();
        List<Alumno> alumnos = Alumno.cargarAlumnosDesdeArchivo(filePathAlumnos, materias);

        String filePathInscripciones = Main.class.getClassLoader().getResource("solicitudInscripcion.csv").getPath();
        List<Inscripcion> solicitudes = Inscripcion.cargarInscripcionesDesdeArchivo(filePathInscripciones, alumnos, materias);

        for (Inscripcion solicitud : solicitudes) {

            String estadoInscripcion = null;

            if (Alumno.buscarAlumno(solicitud.getAlumno().getIdAlumno(), alumnos) == null) {
                estadoInscripcion = "El alumno no existe";
            } else {
                if (Materia.buscarMateria(solicitud.getMateria().getIdMateria(), materias) == null) {
                    estadoInscripcion = "La materia no existe";
                } else {
                    estadoInscripcion = solicitud.aprobada() ? "Aprobada" : "Rechazada";
                }
            }
            System.out.println(estadoInscripcion);
        }

    }

}


