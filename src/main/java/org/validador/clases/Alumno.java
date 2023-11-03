package org.validador.clases;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Alumno {

    private String nombre;
    private String apellido;
    private String idAlumno;
    private List<Materia> materiasAprobadas;

    public Alumno(String nombre, String apellido, String idAlumno) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.idAlumno = idAlumno;
    }

    public Alumno(String idAlumno) {
        this.idAlumno = idAlumno;
    }

    public Alumno(String nombre, String apellido, String idAlumno, List<Materia> materiasAprobadas) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.idAlumno = idAlumno;
        this.materiasAprobadas = materiasAprobadas;
    }

    public Alumno(String idAlumno, List<Materia> materiasAprobadas) {
        this.idAlumno = idAlumno;
        this.materiasAprobadas = materiasAprobadas;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(String idAlumno) {
        this.idAlumno = idAlumno;
    }

    public List<Materia> getMateriasAprobadas() {
        return materiasAprobadas;
    }

    public void setMateriasAprobadas(List<Materia> materiasAprobadas) {
        this.materiasAprobadas = materiasAprobadas;
    }

    public void agregarMateriaAprobada(Materia codigoMateria) {
        materiasAprobadas.add(codigoMateria);
    }

    public void eliminarMateriaAprobada(Materia codigoMateria) {
        materiasAprobadas.remove(codigoMateria);
    }
    public static Alumno buscarAlumno(String id, List<Alumno> alumnos) {
        Alumno existeAlumno = null;
        for (Alumno alumno : alumnos) {
            if (alumno.getIdAlumno().equals(id)) {
                existeAlumno = alumno;
            }
        }
        return existeAlumno;
    }
    public static List<Alumno> cargarAlumnosDesdeArchivo(String filePath, List<Materia> materias) {
        // Leer datos de alumnos
        List<Alumno> alumnos = new ArrayList<>();
        try (
                BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                String nombre = data[0];
                String apellido = data[1];
                String idAlumno = data[2];
                List<Materia> materiasAprobadas = new ArrayList<>();
                if (data.length > 3) {
                    String[] materiasAprobadasStr = data[3].split(" ");
                    for (String idMateria : materiasAprobadasStr) {
                        Materia materiaAprobada = Materia.buscarMateria(idMateria, materias);
                        materiasAprobadas.add(materiaAprobada);
                    }
                } else {
                    materiasAprobadas = null;
                }
                // Crear instancia de Alumno con los datos leídos y agregarla a la lista
                Alumno alumno = new Alumno(nombre, apellido, idAlumno, materiasAprobadas);
                alumnos.add(alumno);
            }
        } catch (
                IOException e) {
            e.printStackTrace();
        }

        return alumnos;
    }
}
