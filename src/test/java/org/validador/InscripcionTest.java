package org.validador;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.validador.clases.Alumno;
import org.validador.clases.Inscripcion;
import org.validador.clases.Materia;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InscripcionTest {
    @DisplayName("Inscripciones")
    @ParameterizedTest
    @CsvSource({
            // Datos de entrada: idAlumno, materiasAprobadas[], idMateria, correlativas[]
            "1, 2 3 4, 5, ",   // El alumno tiene materias aprobadas y la materia no tiene correlativas. T
            "2, 7 8 9, 6, 7 8 9", // Las materias aprobadas coinciden con las correlativas. T
            "3, 4 5 , 7, 4 5 6", // Le falta una correlativa. F
            "4, , 8, ", // T
            "5, , 9, 1" // F
    })
    public void testAlumnoAprobada(String idAlumno, String materiasAprobadasStr, String idMateria, String correlativasStr) {
        List<Materia> materiasAp;
        if (materiasAprobadasStr != null) {
            String[] materiasAprobadas = materiasAprobadasStr.split(" ");
            materiasAp = new ArrayList<>();
            for (String matAp : materiasAprobadas) {
                Materia materia = new Materia(matAp);
                materiasAp.add(materia);
            }
        } else {
            materiasAp = null;
        }
        List<Materia> materiasCorr = new ArrayList<>();
        if (correlativasStr!=null) {
            String[] correlativas = correlativasStr.split(" ");

            for (String matC : correlativas) {
                if (materiasAprobadasStr != null && materiasAprobadasStr.contains(matC)) {
                    Materia materia = Materia.buscarMateria(matC, materiasAp);
                    materiasCorr.add(materia);
                } else {
                    Materia materia = new Materia(matC);
                    materiasCorr.add(materia);
                }
            }
        } else {
            materiasCorr = null;
        }
        Alumno alumno = new Alumno(idAlumno, materiasAp);
        Materia materia = new Materia(idMateria, materiasCorr);
        Inscripcion solicitud = new Inscripcion(alumno, materia);

        boolean resultado = solicitud.aprobada();
        if (Materia.puedeCursar(alumno, materia)) {
            assertTrue(resultado, "El alumno " + alumno.getIdAlumno() + " no deberia poder cursar la materia " + materia.getIdMateria() + ".");
        } else {
            assertFalse(resultado, "El alumno " + alumno.getIdAlumno() + " deberia poder cursar la materia " + materia.getIdMateria() + ".");
        }


    }
}
