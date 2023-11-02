package org.validador.clases;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Materia {

    private String nombreMateria;
    private String idMateria;
    private List<Materia> correlativas;

    public Materia(String idMateria, String nombreMateria, List<Materia> correlativas) {
        this.nombreMateria = nombreMateria;
        this.idMateria = idMateria;
        this.correlativas = correlativas;
    }

    public Materia(String idMateria, String nombreMateria) {
        this.nombreMateria = nombreMateria;
        this.idMateria = idMateria;
    }

    public Materia(String nombreMateria, List<Materia> correlativas) {
        this.nombreMateria = nombreMateria;
        this.correlativas = correlativas;
    }

    public Materia(String idMateria) {
        this.idMateria = idMateria;
    }

    public String getNombreMateria() {
        return nombreMateria;
    }

    public void setNombreMateria(String nombreMateria) {
        this.nombreMateria = nombreMateria;
    }

    public String getIdMateria() {
        return idMateria;
    }

    public void setIdMateria(String idMateria) {
        this.idMateria = idMateria;
    }

    public List<Materia> getCorrelativas() {
        return correlativas;
    }

    public void setCorrelativas(List<Materia> correlativas) {
        this.correlativas = correlativas;
    }

    public static List<Materia> cargarMateriasDesdeArchivo(String filePath) {

        List<Materia> materias = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                String idMateria = data[0];
                String nombreMateria = data[1];
                List<Materia> correlatividades = new ArrayList<>();

                if (data.length > 2) {
                    // Si hay correlatividades en el archivo, las divido
                    String[] correlatividadesArray = data[2].split(" ");
                    for (String correlatividad : correlatividadesArray) {
                        Materia correlativa = buscarMateria(correlatividad, materias);
                        correlatividades.add(correlativa);
                    }
                } else {
                    correlatividades = null;
                }
                // Crear instancia de Materia con los datos leídos y agregarla a la lista
                Materia materia = new Materia(idMateria, nombreMateria, correlatividades);
                materias.add(materia);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return materias;
    }

    public static boolean puedeCursar(Alumno alumno, Materia materia) {
        boolean puedeCursar = true;
        if (materia.getCorrelativas() != null && alumno.getMateriasAprobadas() != null) {
            for (Materia correlativa : materia.getCorrelativas()) {
                if (!alumno.getMateriasAprobadas().contains(correlativa)) {
                    puedeCursar = false;
                    break;
                }
            }
        } else if(alumno.getMateriasAprobadas() == null && materia.getCorrelativas()!= null) {
            puedeCursar = false;
        }
        return puedeCursar;
    }

    public static Materia buscarMateria(String idM, List<Materia> materias) {
        Materia existeMateria = null;
        for (Materia materia : materias) {
            if (materia.getIdMateria().equals(idM)) {
                existeMateria = materia;
            }
        }
        return existeMateria;
    }

}




