package org.example.proyectotfg.modelo;

import java.util.List;

public class Historial {
    private int id_historial;
    private Usuario_normal paciente;
    private List<Psicologo> psicologos;
    private List<String> diagnosticos;

    public Historial(int id_historial, Usuario_normal paciente, List<Psicologo> psicologos, List<String> diagnosticos) {
        this.id_historial = id_historial;
        this.paciente = paciente;
        this.psicologos = psicologos;
        this.diagnosticos = diagnosticos;
    }

    public int getId_historial() {
        return id_historial;
    }

    public void setId_historial(int id_historial) {
        this.id_historial = id_historial;
    }

    public Usuario_normal getPaciente() {
        return paciente;
    }

    public void setPaciente(Usuario_normal paciente) {
        this.paciente = paciente;
    }

    @Override
    public String toString() {
        return "Historial{" +
                "id_historial=" + id_historial +
                ", paciente=" + paciente +
                '}';
    }
}
